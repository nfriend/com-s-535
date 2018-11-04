import java.util.*;

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
   */
  public List<WeightedItem<String>> extractLinks(
      String url, List<String> keywords, boolean isTopicSensitive) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
