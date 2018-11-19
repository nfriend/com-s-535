import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import java.util.*;

import org.junit.jupiter.api.Test;

public class PositionalIndexTest {
  @Test
  void testPositionalIndex() {
    String rootFolder = System.getProperty("user.dir") + "/src/test-input";
    PositionalIndex pi = new PositionalIndex(rootFolder);
  }
}
