import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    // the list of links (along with each link's weight) that we will return
    List<WeightedItem<String>> result = new ArrayList<WeightedItem<String>>();

    // get the HTML of the provided URL
    String html = fetcher.fetchPage(url);

    // operate on a lowercased version of the HTML
    html = html.toLowerCase();

    // for the sake of the assignment, we can assume the content of the document
    // always starts at the first "<p>" element.
    int startIndex = html.indexOf("<p>");
    html = html.substring(startIndex);

    // Matches a starting <a> tag.  Note that this pattern will *not* match an empty
    // <a> tag, i.e. "<a>".  But this is okay because we only care about <a> tags that
    // have href attributes, i.e. "<a href="link/here">.
    Pattern aLinkPattern = Pattern.compile("^(<a\\s+[^>]*>)", Pattern.CASE_INSENSITIVE);

    // Matches a word
    Pattern wordPattern = Pattern.compile("^([a-z0-9-.]+)", Pattern.CASE_INSENSITIVE);

    // Matches any HTML starting or ending tag, which as well all know
    // you're not supposed to do: https://stackoverflow.com/a/1732454/1063392.
    // But it will do for this simple project.
    Pattern htmlTagPattern = Pattern.compile("^(<[^>]*>)", Pattern.CASE_INSENSITIVE);

    // General approach: walk through the string word by word.  Ignore all HTML tags
    // except for opening <a> tags - for these tags, extract the href attribute and record
    // this as a link.  While working through all of the words, record if any of the
    // words are a keyword.  If so, record the position of the word so that we can measure
    // the distance to from these words later.
    while (html.length() > 0) {
      Matcher aLinkMatcher = aLinkPattern.matcher(html);
      Matcher wordMatcher = wordPattern.matcher(html);
      Matcher htmlTagMatcher = htmlTagPattern.matcher(html);

      String token;
      if (aLinkMatcher.find()) {
        // if current token is an opening <a> tag
        token = aLinkMatcher.group(0);
      } else if (wordMatcher.find()) {
        // if the current token is a plain text word
        token = wordMatcher.group(0);
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

    return result;
  }
}
