import java.io.IOException;
import java.net.MalformedURLException;

/** A type that is responsible for returning the HTML as a String from a URL */
public interface IPageFetcher {

  /**
   * Gets a webpage and returns its HTML as a String
   *
   * @param url The webpage to fetch
   * @return The HTML of the webpage as a string
   * @throws MalformedURLException
   * @throws IOException
   */
  String fetchPage(String url) throws IOException;
}
