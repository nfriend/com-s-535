import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** A class that manages the extraction of links from a URL */
public class LinkExtractor {

  /** The class responsible for returning the webpage as an HTML string */
  public IPageFetcher fetcher = new PageFetcher();

  /**
   * Extracts links from given page that match the provided keywords (if <tt>isTopicSensitive</tt>
   * is <tt>true</tt>) and includes the weight of each link. This method assumes that the URL has
   * already been validated against the robots.txt.
   *
   * @param url The URL to extract links from
   * @param keywords The list of keywords to use when extracting links
   * @param isTopicSensitive Whether or not this extraction should be topic sensitive
   * @return A list of all relevant links from the provided page
   * @throws IOException
   */
  public List<WeightedItem<String>> extractLinks(
      String url, List<String> keywords, boolean isTopicSensitive) throws IOException {

    // get the HTML of the provided URL
    String html = fetcher.fetchPage(url);

    // operate only on lowercased keywords
    keywords = keywords.stream().map(k -> k.toLowerCase()).collect(Collectors.toList());

    // for the sake of the assignment, we can assume the content of the document
    // always starts at the first "<p>" element.
    Pattern firstPPattern = Pattern.compile("<p>", Pattern.CASE_INSENSITIVE);
    Matcher firstPMatcher = firstPPattern.matcher(html);
    if (firstPMatcher.find()) {
      int startIndex = firstPMatcher.start();
      html = html.substring(startIndex);
    } else {
      throw new IllegalArgumentException(
          "The HTML found at url " + url + "does not include an opening <p> tag");
    }

    // Matches a starting <a> tag.  Note that this pattern will *not* match an empty
    // <a> tag, i.e. "<a>".  But this is okay because we only care about <a> tags that
    // have href attributes, i.e. "<a href="link/here">.
    Pattern aLinkStartPattern = Pattern.compile("^(<a\\s+[^>]*>)", Pattern.CASE_INSENSITIVE);

    // Matches the text inside an <a> link's href attribute. For simplicity, only matches href
    // attributes that use double quotes, not single quotes.
    Pattern aHrefPattern = Pattern.compile("^<a.*href=\"([^\"]*)\".*>", Pattern.CASE_INSENSITIVE);

    // Matches a closing </a> tag
    Pattern aLinkEndPattern = Pattern.compile("^(</a>)", Pattern.CASE_INSENSITIVE);

    // Matches a word
    Pattern wordPattern = Pattern.compile("^([a-z0-9-_]+)", Pattern.CASE_INSENSITIVE);

    // Matches any HTML starting or ending tag, which as well all know
    // you're not supposed to do: https://stackoverflow.com/a/1732454/1063392.
    // But it will do for this simple project.
    Pattern htmlTagPattern = Pattern.compile("^(<[^>]*>)", Pattern.CASE_INSENSITIVE);

    // the current word count
    int currentPosition = 0;

    // keeps track of the current link, if we are currently scanning tokens inside of an <a> tag
    LinkAndRange currentLink = null;

    // the positions of all keyword matches in the document
    ArrayList<Integer> keywordPositions = new ArrayList<Integer>();

    // all links that we find in the document and the word ranges that each includes inside the tag
    ArrayList<LinkAndRange> links = new ArrayList<LinkAndRange>();

    // General approach: walk through the string word by word.  Ignore all HTML tags
    // except for opening <a> tags - for these tags, extract the href attribute and record
    // this as a link.  While working through all of the words, record if any of the
    // words are a keyword.  If so, record the position of the word so that we can measure
    // the distance to from these words later.
    while (html.length() > 0) {
      Matcher aLinkStartMatcher = aLinkStartPattern.matcher(html);
      Matcher aLinkEndMatcher = aLinkEndPattern.matcher(html);
      Matcher wordMatcher = wordPattern.matcher(html);
      Matcher htmlTagMatcher = htmlTagPattern.matcher(html);

      String token;
      if (aLinkStartMatcher.find()) {
        // if current token is an opening <a> tag
        token = aLinkStartMatcher.group(1);

        // get this link's href
        Matcher aHrefMatcher = aHrefPattern.matcher(token);
        if (aHrefMatcher.find()) {
          // if this link has an href, record this link.
          String href = aHrefMatcher.group(1);

          // as stated in the spec, links that contain "#" or ":" must be ignored.
          // Also, ignore any URLs that begin with //
          if (!href.contains("#") && !href.contains(":") && !href.startsWith("//")) {
            currentLink = new LinkAndRange(href);
          }
        }
      } else if (aLinkEndMatcher.find()) {
        // if current token is an closing <a> tag
        token = aLinkEndMatcher.group(1);

        // add the link object to the list and null out the currentLink pointer
        if (currentLink != null) {
          links.add(currentLink);
          currentLink = null;
        }
      } else if (wordMatcher.find()) {
        // if the current token is a plain text word
        token = wordMatcher.group(1);

        // if we're inside of an <a> tag, record this position
        // against the current link
        if (currentLink != null) {
          currentLink.addPosition(currentPosition);
        }

        // this word is a keyword, record its position
        if (keywords.stream().anyMatch(k -> k.equalsIgnoreCase(token))) {
          keywordPositions.add(currentPosition);
        }

        currentPosition++;
      } else if (htmlTagMatcher.find()) {
        // if the current token is an HTML start or end tag
        token = htmlTagMatcher.group(0);
      } else {
        // otherwise, we're not sure what the current token is.
        // In this case, remove the first character of the string
        // and try again in the next loop.
        token = html.substring(0, 1);
      }

      // remove the processed token from the HTML string
      html = html.substring(token.length());

      // remove any whitespace that might be at the start of the string
      html = html.trim();
    }

    // compute each link's weight and return it.
    // We'll use the WeightedQ class because it will
    // automatically replace lighter entries with heavier ones.
    WeightedQ<String> q = new WeightedQ<String>();
    for (LinkAndRange link : links) {
      q.add(link.link, getLinkWeight(link, keywords, keywordPositions, isTopicSensitive));
    }

    // get the links as a list
    List<WeightedItem<String>> result = q.asList();

    // remove any links that point back to the original page
    return result.stream().filter(l -> !l.item.equalsIgnoreCase(url)).collect(Collectors.toList());
  }

  private double getLinkWeight(
      LinkAndRange link,
      List<String> keywords,
      List<Integer> keywordPositions,
      boolean isTopicSensitive) {

    // non-topic sensitive extractions place all links with a weight of 0.0
    if (!isTopicSensitive) {
      return 0.0;
    }

    // if a keyword appears inside the <a></a> tags, the weight is 1.0
    if (keywordPositions
        .stream()
        .anyMatch(p -> link.beginningPosition <= p && link.endPosition >= p)) {
      return 1.0;
    }

    // if a keyword appears inside the <a>'s href, the weight is 1.0
    if (keywords.stream().anyMatch(k -> link.link.toLowerCase().contains(k.toLowerCase()))) {
      return 1.0;
    }

    // find the distance between the closest keyword token and this link
    int closestDistance = Integer.MAX_VALUE;
    for (int p : keywordPositions) {

      int beginningDistance = Math.abs(link.beginningPosition - p);
      if (beginningDistance < closestDistance) {
        closestDistance = beginningDistance;
      }

      int endDistance = Math.abs(link.endPosition - p);
      if (endDistance < closestDistance) {
        closestDistance = endDistance;
      }
    }

    // distances farther than 17 are considered to have a weight of 0.0,
    // as specified in the assignment
    if (closestDistance > 17) {
      return 0.0;
    }

    // the closest token was within 18 words of distance from the link.
    // in this case, compute the weight using the provided formula.
    return 1.0 / (closestDistance + 2);
  }
}
