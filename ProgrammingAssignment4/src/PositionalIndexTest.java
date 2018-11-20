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

    System.out.println(pi.postingsList("quick"));
    System.out.println(pi.postingsList("fox"));
    System.out.println(pi.TPScore("Quick Fox", "example1.txt"));
    System.out.println(pi.TPScore("Quick Fox", "example2.txt"));
    System.out.println(pi.TPScore("Quick Fox", "example3.txt"));
    System.out.println(pi.TPScore("Quick Fox", "example4.txt"));
  }
}
