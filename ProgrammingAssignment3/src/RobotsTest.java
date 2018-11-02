import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class RobotsTest {
  @Test
  void testRobotsTxt() {
    Robots robots = new Robots();
    String baseUrl = "https://en.wikipedia.org";
    try {
      robots.loadRobotsTxt(baseUrl);
    } catch (IOException e) { // TODO Auto-generated catch block
      fail("An exception was thrown while loading robots.txt from " + baseUrl);
    }

    assertEquals(true, robots.isAllowed("/wiki/Tennis"));
    assertEquals(false, robots.isAllowed("/wiki/Wikipedia:Articles_for_deletion/"));
    assertEquals(false, robots.isAllowed("/wiki/Wikipedia:Articles_for_deletion/some/other/path"));

//    List<String> allUrls = robots.getAllDisallowedUrls();
//    for (String url : allUrls) {
//      System.out.println("\"" + url + "\"");
//    }
  }
}
