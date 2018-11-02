import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Manages all interactions with the robots.txt file */
public class Robots {

  /** The list of URLs which are disallowed for robots */
  private List<String> disallowed;

  /**
   * Loads all the entries from a site's robots.txt
   *
   * @param baseUrl The base URL to fetch robots.txt from
   * @throws IOException
   */
  public void loadRobotsTxt(String baseUrl) throws IOException {

    // based on https://stackoverflow.com/a/1485730/1063392
    URL robotsUrl = new URL(baseUrl + "/robots.txt");
    HttpURLConnection connection = (HttpURLConnection) robotsUrl.openConnection();
    connection.setRequestMethod("GET");
    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

    ArrayList<String> lines = new ArrayList<String>();
    String line;
    while ((line = br.readLine()) != null) {
      lines.add(line);
    }
    br.close();

    disallowed = getAllDisallowedUrls(lines);
  }

  /**
   * Processes the lines of a robots.txt file and returns all the URLs that are disallowed for this
   * crawler. This is a naive implementation of a robots.txt parser and does not take into account
   * Allow: lines.
   *
   * @param lines The lines of the robots.txt
   * @return A list of disallowed URLs
   */
  private List<String> getAllDisallowedUrls(ArrayList<String> lines) {

    List<String> disallowedUrls = new ArrayList<String>();

    // a pattern that matches the "User-agent: agent-name" lines
    Pattern userAgentPattern = Pattern.compile("^user-agent:\\s*(.*)$", Pattern.CASE_INSENSITIVE);

    // a pattern that matches the "Disallow: url/here" lines
    Pattern disallowPattern = Pattern.compile("^disallow:\\s*(.*)$", Pattern.CASE_INSENSITIVE);

    // indicates whether or not we are reading rules that apply to us
    boolean doDisallowsApply = false;
    for (String line : lines) {
      // determine if this line is specifying a user-agent and
      // set soDisallowsApply accordingly
      Matcher userAgentMatcher = userAgentPattern.matcher(line);
      while (userAgentMatcher.find()) {
        String userAgent = userAgentMatcher.group(1);
        doDisallowsApply = userAgent.equals("*");
      }

      // if the lines are relevant to this bot, test the line to
      // see if it's a Disallow: line and if so, record it
      if (doDisallowsApply) {
        Matcher disallowMatcher = disallowPattern.matcher(line);
        while (disallowMatcher.find()) {
          String url = disallowMatcher.group(1);
          disallowedUrls.add(url);
        }
      }
    }

    return disallowedUrls;
  }

  /**
   * Determines if the URL is allowed to be crawled by robots based on the site's robots.txt
   *
   * @param url The URL to check
   * @return true if robots are allowed, otherwise false
   */
  public boolean isAllowed(String url) {
    return !isDisallowed(url);
  }

  /**
   * Determines if the URL is not allowed to be crawled by robots based on the site's robots.txt
   *
   * @param url The URL to check
   * @return true if robots are disallowed, otherwise false
   */
  public boolean isDisallowed(String url) {
    return disallowed.stream().anyMatch(d -> url.startsWith(d));
  }

  /**
   * Returns all disallowed URLs
   *
   * @return All disallowesd URLs
   */
  public List<String> getAllDisallowedUrls() {
    return this.disallowed;
  }
}
