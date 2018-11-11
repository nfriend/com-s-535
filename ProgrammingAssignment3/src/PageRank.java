import java.io.IOException;
import java.util.*;

/**
 * A proxy class that simply returns the results of the <tt>PageRankString</tt> class, but
 * translates the return types into the types in the assignment specification. This class is not
 * used by any other class in this project, but it is assumed that it will be used for grading
 * purposes (based on the assignment description).
 *
 * <p>Note that this class relies on the assumption specified in the assignment - that the file
 * defines all edges using only integers. This class will <em>not</em> work on the output of the
 * <tt>WikiCrawler</tt> class - use the <tt>PageRankString</tt> class directly for this purpose.
 */
public class PageRank {

  private PageRankString prs;

  /**
   * Constructs a new PageRank instance
   *
   * @param edgeFile The file containing all of the edges
   * @param approximation The page rank approximation parameter
   * @param teleportation The teleportation parameter
   * @throws IOException
   */
  public PageRank(String edgeFile, double approximation, double teleportation) throws IOException {
    this.prs = new PageRankString(edgeFile, approximation, teleportation);
  }

  /**
   * Returns the page rank of a vertex
   *
   * @param vertexName The name of the vertex
   * @return The page rank of the vertex
   */
  public double pageRankOf(int vertexNum) {
    return prs.pageRankOf(Integer.toString(vertexNum));
  }

  /**
   * Returns the out degree of the vertex
   *
   * @param vertexName The name of the vertex
   * @return The out degree of the vertex
   */
  public int outDegreeOf(int vertexNum) {
    return prs.outDegreeOf(Integer.toString(vertexNum));
  }

  /**
   * Returns the in degree of the vertex
   *
   * @param vertexName The name of the vertex
   * @return The in degree of the vertex
   */
  public int inDegreeOf(int vertexNum) {
    return prs.inDegreeOf(Integer.toString(vertexNum));
  }

  /**
   * Gets the total number of edges in the graph
   *
   * @return The number of edges in the graph
   */
  public int numEdges() {
    return prs.numEdges();
  }

  /**
   * Gets the top <tt>k</tt> pages by rank
   *
   * @param k The number of pages to get
   * @return The top pages by rank
   */
  public int[] topKPageRank(int k) {
    return Arrays.asList(prs.topKPageRank(k))
        .stream()
        .map(str -> Integer.parseInt(str))
        .mapToInt(i -> i)
        .toArray();
  }

  /**
   * Gets the top <tt>k</tt> pages by in degree
   *
   * @param k The number of pages to get
   * @return The top pages by in degree
   */
  public int[] topKInDegree(int k) {
    return Arrays.asList(prs.topKInDegree(k))
        .stream()
        .map(str -> Integer.parseInt(str))
        .mapToInt(i -> i)
        .toArray();
  }

  /**
   * Gets the top <tt>k</tt> pages by out degree
   *
   * @param k The number of pages to get
   * @return The top pages by out degree
   */
  public int[] topKOutDegree(int k) {
    return Arrays.asList(prs.topKOutDegree(k))
        .stream()
        .map(str -> Integer.parseInt(str))
        .mapToInt(i -> i)
        .toArray();
  }
}
