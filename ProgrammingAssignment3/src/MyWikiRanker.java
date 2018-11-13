import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/** Crawls Wikipedia and using a topic of my choice and ranks the resulting graph */
public class MyWikiRanker {

  /**
   * Runs the program
   *
   * @param args The command line arguments (none are used in this program)
   * @throws InterruptedException
   * @throws IOException
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String fileName = format.format(new Date()) + ".txt";
    String filePath = System.getProperty("user.dir") + "/src/output/" + fileName;

    // my topic information
    String seedUrl = "/wiki/Cello";
    String[] keywords =
        new String[] {
          "cello",
          "bow",
          "neck",
          "fingerboard",
          "pegbox",
          "scroll",
          "pegs",
          "endpin",
          "f-holes",
          "f-hole",
          "pizzicato",
          "vibrato",
          "thumb",
          "string",
          "strings",
          "rosin",
          "orchestra",
          "symphony",
          "Stradivarius",
          "Stradivari",
          "Yo-Yo",
          "Rostropovich",
          "Casals",
          "Maisky",
          "Isserlis",
          "Starker",
          "violoncello"
        };

    // Generate the web graph by crawling Wikipedia
    WikiCrawler crawler = new WikiCrawler(seedUrl, keywords, 500, filePath, true);
    crawler.crawl();

    PageRankString ranker = new PageRankString(filePath, 0.01, 0.85);
    int k = 20;

    System.out.println("A: Top " + k + " links based on outdegree:");
    String[] a = ranker.topKOutDegree(k);
    for (int i = 0; i < a.length; i++) {
      System.out.println(String.format("    %2s. %s", Integer.toString(i + 1), a[i]));
    }
    System.out.println();

    System.out.println("B: Top " + k + " links based on indegree:");
    String[] b = ranker.topKInDegree(k);
    for (int i = 0; i < b.length; i++) {
      System.out.println(String.format("    %2s. %s", Integer.toString(i + 1), b[i]));
    }
    System.out.println();

    System.out.println(
        "C: Top "
            + k
            + " links based on page rank with approximation = 0.01 and teleportation = 0.85:");
    String[] c = ranker.topKPageRank(k);
    for (int i = 0; i < c.length; i++) {
      System.out.println(String.format("    %2s. %s", Integer.toString(i + 1), c[i]));
    }
    System.out.println();

    System.out.println(
        "D: Top "
            + k
            + " links based on page rank with approximation = 0.005 and teleportation = 0.85:");
    ranker = new PageRankString(filePath, 0.005, 0.85);
    String[] d = ranker.topKPageRank(k);
    for (int i = 0; i < d.length; i++) {
      System.out.println(String.format("    %2s. %s", Integer.toString(i + 1), d[i]));
    }
    System.out.println();

    System.out.println(
        "E: Top "
            + k
            + " links based on page rank with approximation = 0.001 and teleportation = 0.85:");
    ranker = new PageRankString(filePath, 0.001, 0.85);
    String[] e = ranker.topKPageRank(k);
    for (int i = 0; i < e.length; i++) {
      System.out.println(String.format("    %2s. %s", Integer.toString(i + 1), e[i]));
    }
    System.out.println();

    String[] letters = new String[] {"A", "B", "C", "D", "E"};
    String[][] lists = new String[][] {a, b, c, d, e};

    for (int i = 0; i < lists.length; i++) {
      for (int j = i + 1; j < lists.length; j++) {
        double jaccard = Jaccard.calculate(lists[i], lists[j]);
        System.out.println(
            String.format(
                "Exact Jaccard similarity between lists %s and %s: %.4f",
                letters[i], letters[j], jaccard));
      }
    }
  }
}
