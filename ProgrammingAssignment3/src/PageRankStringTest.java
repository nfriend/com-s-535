import java.io.IOException;

import org.junit.jupiter.api.Test;

public class PageRankStringTest {

  @Test
  void testPageRanString() throws IOException {

    String file = "2018-11-07-06-19-45.txt";
    //    String file = "simple.txt";
    String filePath = System.getProperty("user.dir") + "/src/test-output/" + file;
    PageRankString pr = new PageRankString(filePath, .01, .85);

    String[] topByRank = pr.topKPageRank(100);
    System.out.println("Top by rank:");
    System.out.println(String.join(", ", topByRank));
  }
}
