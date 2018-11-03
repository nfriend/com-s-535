import org.junit.jupiter.api.Test;

public class LinkExtractorTest {
  @Test
  void testExtraction() {
    LinkExtractor extractor = new LinkExtractor();

    String html = "<p>HTML here!</p>";

    extractor.fetcher = new MockPageFetcher(html);

    // TODO: test extractor here
  }
}
