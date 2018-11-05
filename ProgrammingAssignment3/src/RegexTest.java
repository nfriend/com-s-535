import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class RegexTest {
  @Test
  void test() {
    Pattern testPattern = Pattern.compile("^s");

    String testString = "sdfs";
    Matcher testMatcher = testPattern.matcher(testString);

    assertEquals(true, testMatcher.find());
  }
}
