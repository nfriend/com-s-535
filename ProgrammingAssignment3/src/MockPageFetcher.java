import java.io.IOException;

/** A class that just returns the provided HTML string directly. Used for testing purposes. */
public class MockPageFetcher implements IPageFetcher {

  /** The HTML string to return */
  private String htmlToReturn;

  /**
   * Creates a new MockPageFetcher.
   *
   * @param htmlToReturn The HTML string to return when <tt>fetchPage</tt> is called.
   */
  public MockPageFetcher(String htmlToReturn) {
    this.htmlToReturn = htmlToReturn;
  }

  @Override
  public String fetchPage(String url) throws IOException {
    return htmlToReturn;
  }
}
