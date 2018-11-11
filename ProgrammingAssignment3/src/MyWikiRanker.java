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
    //    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    //    String fileName = format.format(new Date()) + ".txt";
    //    String filePath = System.getProperty("user.dir") + "/src/output/" + fileName;
    //
    //    // my topic information
    //    String seedUrl = "/wiki/Cello";
    //    String[] keywords =
    //        new String[] {
    //          "cello",
    //          "bow",
    //          "neck",
    //          "fingerboard",
    //          "pegbox",
    //          "scroll",
    //          "pegs",
    //          "endpin",
    //          "f-holes",
    //          "f-hole",
    //          "pizzicato",
    //          "vibrato",
    //          "thumb",
    //          "string",
    //          "strings",
    //          "rosin",
    //          "orchestra",
    //          "symphony",
    //          "Stradivarius",
    //          "Stradivari",
    //          "Yo-Yo",
    //          "Rostropovich",
    //          "Casals",
    //          "Maisky",
    //          "Isserlis",
    //          "Starker",
    //          "violoncello"
    //        };
    //
    //    // Generate the web graph by crawling Wikipedia
    //    WikiCrawler crawler = new WikiCrawler(seedUrl, keywords, 500, filePath, true);
    //    crawler.crawl();

    String filePath = System.getProperty("user.dir") + "/src/output/2018-11-10-11-32-51.txt";

    PageRank ranker = new PageRank(filePath, 0.01, 0.85);
    int k = 10;

    System.out.println("A: Top " + k + " links based on outdegree:");
    String[] top = ranker.topKOutDegree(k);
    for (int i = 0; i < top.length; i++) {
      System.out.println(String.format("    %2s. %s", Integer.toString(i + 1), top[i]));
    }
    System.out.println();
    System.out.println("   Jaccard Similarity between all pairs in the list above:");
    System.out.println(Jaccard.toString(Arrays.asList(top), "     -  "));

    System.out.println("B: Top " + k + " links based on indegree:");
    top = ranker.topKInDegree(k);
    for (int i = 0; i < top.length; i++) {
      System.out.println(String.format("    %2s. %s", Integer.toString(i + 1), top[i]));
    }
    System.out.println();

    System.out.println(
        "C: Top "
            + k
            + " links based on page rank with approximation = 0.01 and teleporation = 0.85:");
    top = ranker.topKPageRank(k);
    for (int i = 0; i < top.length; i++) {
      System.out.println(String.format("    %2s. %s", Integer.toString(i + 1), top[i]));
    }
    System.out.println();

    System.out.println(
        "D: Top "
            + k
            + " links based on page rank with approximation = 0.005 and teleporation = 0.85:");
    ranker = new PageRank(filePath, 0.005, 0.85);
    top = ranker.topKPageRank(k);
    for (int i = 0; i < top.length; i++) {
      System.out.println(String.format("    %2s. %s", Integer.toString(i + 1), top[i]));
    }
    System.out.println();

    System.out.println(
        "E: Top "
            + k
            + " links based on page rank with approximation = 0.001 and teleporation = 0.85:");
    ranker = new PageRank(filePath, 0.001, 0.85);
    top = ranker.topKPageRank(k);
    for (int i = 0; i < top.length; i++) {
      System.out.println(String.format("    %2s. %s", Integer.toString(i + 1), top[i]));
    }
    System.out.println();
  }
}
