import java.io.IOException;

import org.junit.jupiter.api.Test;

public class WikiCrawlerTest {
  @Test
  void testCrawler() throws IOException, InterruptedException {

    String[] keywords =
        new String[] {
          "tennis",
          "grand slam",
          "french",
          "open",
          "australian open",
          "wimbledon",
          "US open",
          "masters"
        };

    WikiCrawler crawler = new WikiCrawler("/wiki/Tennis", keywords, 100, "testfile.txt", true);

    crawler.crawl();
  }
}
