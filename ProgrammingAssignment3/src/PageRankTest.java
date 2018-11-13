import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class PageRankTest {
  @Test
  void testPageRank() throws IOException {

    String file = "simple.txt";
    String filePath = System.getProperty("user.dir") + "/src/test-output/" + file;
    PageRank pr = new PageRank(filePath, .01, .85);

    System.out.println("Top by rank:");
    System.out.println(Arrays.toString(pr.topKPageRank(4)));
    System.out.println("Top by in degree:");
    System.out.println(Arrays.toString(pr.topKInDegree(4)));
    System.out.println("Top by out degree:");
    System.out.println(Arrays.toString(pr.topKOutDegree(4)));
    System.out.println("Page rank of vertex 2: " + pr.inDegreeOf(2));
    System.out.println("In degree of vertex 2: " + pr.inDegreeOf(2));
    System.out.println("Out degree of vertex 2: " + pr.outDegreeOf(2));
  }
}
