import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import java.util.*;

import org.junit.jupiter.api.Test;

public class PositionalIndexTest {
  @Test
  void testPositionalIndex() {
    String rootFolder = System.getProperty("user.dir") + "/src/test-input";
    //    String rootFolder = "C:\\Users\\Nathan\\Downloads\\IR";
    PositionalIndex pi = new PositionalIndex(rootFolder);

    List<String> queries =
        Arrays.asList(
            "quick fox",
            "fox quick",
            "fox",
            "quick",
            "blue",
            "black",
            "blue black",
            "black blue",
            "blue brown");

    for (String query : queries) {
      System.out.println(
          "Score for query \"" + query + "\": " + pi.Relevance(query, "example1.txt"));
    }
  }
}
