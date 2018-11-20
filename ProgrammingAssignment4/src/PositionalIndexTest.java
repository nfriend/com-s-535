import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import java.util.*;

import org.junit.jupiter.api.Test;

public class PositionalIndexTest {
  @Test
  void testPositionalIndex() {
    //    String rootFolder = System.getProperty("user.dir") + "/src/test-input";
    String rootFolder = "C:\\Users\\Nathan\\Downloads\\IR";
    PositionalIndex pi = new PositionalIndex(rootFolder);

    System.out.println(pi.postingsList("quickly"));
    System.out.println(pi.termFrequency("quickly", "Fielding_(cricket).txt"));
    System.out.println(pi.termFrequency("the", "Fielding_(cricket).txt"));
    System.out.println(pi.docFrequency("quick"));
  }
}
