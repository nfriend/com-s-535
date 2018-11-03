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
   */
  public void crawl() throws IOException {

    // load the site's robots.txt
    Robots bots = new Robots();
    bots.loadRobotsTxt(BASE_URL);

    // initialize our weighted queue and add the root URL
    WeightedQ<String> wq = new WeightedQ<String>();
    wq.add(seedUrl);

    // a list of URLs that we have visited
    ArrayList<String> visited = new ArrayList<String>();

    // the final list of graph edges that we will write to the file
    List<Tuple<String, String>> result = new ArrayList<Tuple<String, String>>();

    // crawl!
    int visitedCount = 0;
    while (wq.size() > 0 && visitedCount < max) {}

    if (bots.isDisallowed(seedUrl)) {}
  }
}
