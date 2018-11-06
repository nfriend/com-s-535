import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

import org.junit.jupiter.api.Test;

public class LinkExtractorTest {
  @Test
  void testSimpleExtraction() throws IOException {
    LinkExtractor extractor = new LinkExtractor();

    String filePath = System.getProperty("user.dir") + "/src/test-html/simple.html";
    String testHtml = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

    extractor.fetcher = new MockPageFetcher(testHtml);

    ArrayList<String> keywords = new ArrayList<String>();
    keywords.add("tennis");
    keywords.add("grand slam");
    keywords.add("french");
    keywords.add("open");
    keywords.add("australian open");
    keywords.add("wimbledon");
    keywords.add("US open");
    keywords.add("masters");

    List<WeightedItem<String>> links = extractor.extractLinks("any/url/here", keywords, true);

    assertEquals(5, links.size());
    assertEquals(new WeightedItem<String>("/test/link/1.html", 1.0), links.get(0));
    assertEquals(new WeightedItem<String>("/test/link/4.html", 1.0 / (4 + 2)), links.get(1));
    assertEquals(new WeightedItem<String>("/test/link/3.html", 1.0 / (5 + 2)), links.get(2));
    assertEquals(new WeightedItem<String>("/test/link/2.html", 0.0), links.get(3));
    assertEquals(new WeightedItem<String>("/test/link/5.html", 0.0), links.get(4));
  }
}
