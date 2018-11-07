import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/** A class to compute the page rank of node/pages of a web graph */
public class PageRank {

  /** The list of all vertices and all of the incoming and outgoing links to/from each link */
  private HashMap<String, NodeInfo<String>> vertices;

  /** The web graph matrix */
  private double[][] M̃;

  /** The number of vertices as specified in the input file */
  private int vertexCount;

  /**
   * Constructs a new PageRank instance
   *
   * @param edgeFile The file containing all of the edges
   * @param approximation The page rank approximation parameter
   * @param teleportation The teleportation parameter
   * @throws IOException
   */
  public PageRank(String edgeFile, double approximation, double teleportation) throws IOException {

    buildVerticesFromFile(edgeFile);
    buildM̃();
  }

  /**
   * Returns the page rank of a vertex
   *
   * @param vertexName The name of the vertex
   * @return The page rank of the vertex
   */
  public int pageRankOf(String vertexName) {
    return vertices.get(vertexName).rank;
  }

  /**
   * Returns the out degree of the vertex
   *
   * @param vertexName The name of the vertex
   * @return The out degree of the vertex
   */
  public int outDegreeOf(String vertexName) {
    return vertices.get(vertexName).getOutDegree();
  }

  /**
   * Returns the in degree of the vertex
   *
   * @param vertexName The name of the vertex
   * @return The in degree of the vertex
   */
  public int inDegreeOf(String vertexName) {
    return vertices.get(vertexName).getInDegree();
  }

  /**
   * Gets the total number of edges in the graph
   *
   * @return The number of edges in the graph
   */
  public int numEdges() {
    int count = 0;
    for (String link : this.vertices.keySet()) {
      count += vertices.get(link).getOutDegree();
    }

    return count;
  }

  /**
   * Gets the top <tt>k</tt> pages by rank
   *
   * @param k The number of pages to get
   * @return The top pages by rank
   */
  public String[] topKPageRank(int k) {
    String[] allLinks = vertices.keySet().toArray(new String[vertices.size()]);
    Arrays.sort(allLinks, Comparator.comparing((String l) -> vertices.get(l).rank));
    return Arrays.copyOfRange(allLinks, 0, k);
  }

  /**
   * Gets the top <tt>k</tt> pages by in degree
   *
   * @param k The number of pages to get
   * @return The top pages by in degree
   */
  public String[] topKInDegree(int k) {
    String[] allLinks = vertices.keySet().toArray(new String[vertices.size()]);
    Arrays.sort(allLinks, Comparator.comparing((String l) -> vertices.get(l).getInDegree()));
    return Arrays.copyOfRange(allLinks, 0, k);
  }

  /**
   * Gets the top <tt>k</tt> pages by out degree
   *
   * @param k The number of pages to get
   * @return The top pages by out degree
   */
  public String[] topKOutDegree(int k) {
    String[] allLinks = vertices.keySet().toArray(new String[vertices.size()]);
    Arrays.sort(allLinks, Comparator.comparing((String l) -> vertices.get(l).getOutDegree()));
    return Arrays.copyOfRange(allLinks, 0, k);
  }

  /**
   * Builds the local vertices data structure from the provided file
   *
   * @param edgeFile The file to load
   * @throws FileNotFoundException
   * @throws IOException
   */
  private void buildVerticesFromFile(String edgeFile) throws FileNotFoundException, IOException {

    vertices = new HashMap<String, NodeInfo<String>>();

    // read all of the lines of the file
    boolean first = true;
    try (BufferedReader br = new BufferedReader(new FileReader(edgeFile))) {
      String line;
      while ((line = br.readLine()) != null) {
        if (first) {
          vertexCount = Integer.parseInt(line);
          first = false;
          continue;
        }

        String[] edge = line.split("\\s+");
        String page1 = edge[0];
        String page2 = edge[1];

        if (!vertices.containsKey(page1)) {
          NodeInfo<String> info = new NodeInfo<String>();
          info.node = page1;
          vertices.put(page1, info);
        }

        if (!vertices.containsKey(page2)) {
          NodeInfo<String> info = new NodeInfo<String>();
          info.node = page2;
          vertices.put(page2, info);
        }

        vertices.get(page1).outgoingLinks.add(page2);
        vertices.get(page2).incomingLinks.add(page1);
      }
    }
  }

  /** Builds the web graph (M̃) using the vertices data structure */
  private void buildM̃() {
    ArrayList<String> allPages = new ArrayList<String>(vertices.keySet());
    M̃ = new double[vertexCount][vertexCount];
    for (int i = 0; i < vertexCount; i++) {
      String currentPage = allPages.get(i);
      Set<String> currentLinks = vertices.get(currentPage).outgoingLinks;

      for (int j = 0; j < vertexCount; j++) {
        String linkedPage = allPages.get(j);
        int outDegree = currentLinks.size();
        if (currentLinks.contains(linkedPage)) {
          M̃[i][j] = 1.0 / outDegree;
        } else {
          M̃[i][j] = 0.0;
        }
      }
    }
  }
}
