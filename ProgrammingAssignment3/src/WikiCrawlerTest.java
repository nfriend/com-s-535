import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String dateString = format.format(new Date());
    String filePath = System.getProperty("user.dir") + "/src/test-output/" + dateString + ".txt";

    WikiCrawler crawler = new WikiCrawler("/wiki/Tennis", keywords, 100, filePath, true);

    crawler.crawl();
  }
}
