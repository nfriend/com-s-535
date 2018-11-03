import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/** Handles getting an HTML via the network */
public class PageFetcher implements IPageFetcher {

  @Override
  public String fetchPage(String url) throws MalformedURLException, IOException {
    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
    connection.setRequestMethod("GET");
    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      sb.append(line);
    }
    br.close();

    return sb.toString();
  }
}
