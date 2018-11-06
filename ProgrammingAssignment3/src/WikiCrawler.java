import java.io.IOException;
import java.util.*;

/** A class that crawls Wikipedia */
public class WikiCrawler {

  /** The base URL to crawl */
  public static final String BASE_URL = "https://en.wikipedia.org";

  /** The relative address of the seed URL */
  private String seedUrl;

  /** The list of keywords that describe the topic */
  private List<String> keywords;

  /** The maximum number of sites to be crawled */
  private int max;

  /** The name of the file to write the results to */
  private String fileName;

  /** Whether or not the crawling should be done in a topic-sensitive way */
  private boolean isTopicSensitive;

  /**
   * Instantiates a new WikiCrawler instance
   *
   * @param seedUrl The relative address of the seed URL
   * @param keywords The list of keywords that describe the topic
   * @param max The maximum number of sites to be crawled
   * @param fileName The name of the file to write the results to
   * @param isTopicSensitive Whether or not the crawling should be done in a topic-sensitive way
   */
  public WikiCrawler(
      String seedUrl, String[] keywords, int max, String fileName, boolean isTopicSensitive) {
    this.seedUrl = seedUrl;
    this.keywords = Arrays.asList(keywords);
    this.max = max;
    this.fileName = fileName;
    this.isTopicSensitive = isTopicSensitive;
  }

  /**
   * Crawls pages until <em>max</em> pages are found
   *
   * @throws IOException
   * @throws InterruptedException
   */
  public void crawl() throws IOException, InterruptedException {

    // load the site's robots.txt
    Robots bots = new Robots();
    bots.loadRobotsTxt(BASE_URL);

    // initialize our weighted queue and add the root URL
    WeightedQ<String> wq = new WeightedQ<String>();
    wq.add(seedUrl);

    // a list of URLs that we have visited
    ArrayList<String> visited = new ArrayList<String>();

    // the final list of graph edges that we will write to the file
    List<Tuple<String, String>> edges = new ArrayList<Tuple<String, String>>();

    // utility class that extracts links from a URL
    LinkExtractor extractor = new LinkExtractor();

    // used to add a timeout every 10 requests
    int timeoutCounter = 0;

    // crawl!
    int visitedCount = 0;
    while (wq.size() > 0 && visitedCount < max) {
      String url = wq.extract().item;

      // skip this URL if we've been there before
      if (visited.contains(url)) {
        continue;
      }

      // skip this URL if it's disallowed by robots.txt
      if (bots.isDisallowed(url)) {
        continue;
      }

      // mark this URL as visited
      visited.add(url);

      Logger.log("Making a request to " + BASE_URL + url);

      // get all the links (and their weights) in this page
      List<WeightedItem<String>> links =
          extractor.extractLinks(BASE_URL + url, keywords, isTopicSensitive);

      Logger.log(" - Found " + links.size() + " links on this page");

      for (WeightedItem<String> link : links) {
        // add all the links to our queue
        edges.add(new Tuple<String, String>(url, link.item));
        wq.add(link.item);
      }

      // every 10 requests, pause for 2 seconds to give Wikipedia a break
      timeoutCounter++;
      if (timeoutCounter == 10) {
        Thread.sleep(2000);
        timeoutCounter = 0;
      }
    }

    // write the output to the file
    for (Tuple<String, String> edge : edges) {
      System.out.println(edge.item1 + "  --->  " + edge.item2);
    }
  }
}
