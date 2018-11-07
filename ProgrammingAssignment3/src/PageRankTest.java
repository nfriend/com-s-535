import java.io.IOException;

import org.junit.jupiter.api.Test;

public class PageRankTest {

  @Test
  void testPageRank() throws IOException {

    String file = "2018-11-07-06-19-45.txt";
    String filePath = System.getProperty("user.dir") + "/src/test-output/" + file;
    PageRank pr = new PageRank(filePath, .85, .1);
  }
}
