import java.io.*;
import java.util.*;

/**
 * A class to compute the page rank of node/pages of a web graph. Due to the specification of the
 * assignment, this class deals with the web graph as Strings. The real PageRank class simply
 * proxies the methods exposed here and translated the results into the appropriate types.
 */
public class PageRankString {

  /** The list of all vertices and all of the incoming and outgoing links to/from each link */
  private HashMap<String, NodeInfo<String>> vertices;

  /** The list of all vertex names in the appropriate order */
  private List<String> vertexNames;

  /** The web graph matrix */
  private double[][] O;

  /** The number of vertices as specified in the input file */
  private int vertexCount;

  /** The page rank approximation parameter */
  private double approximation;

  /** The teleportation parameter */
  private double teleportation;

  /**
   * Constructs a new PageRankString instance
   *
   * @param edgeFile The file containing all of the edges
   * @param approximation The page rank approximation parameter
   * @param teleportation The teleportation parameter
   * @throws IOException
   */
  public PageRankString(String edgeFile, double approximation, double teleportation)
      throws IOException {

    this.approximation = approximation;
    this.teleportation = teleportation;

    buildVerticesFromFile(edgeFile);
    buildO();
    buildPageRank();
  }

  /**
   * Returns the page rank of a vertex
   *
   * @param vertexName The name of the vertex
   * @return The page rank of the vertex
   */
  public double pageRankOf(String vertexName) {
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
    Arrays.sort(allLinks, Comparator.comparing((String l) -> -1 * vertices.get(l).rank));
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

    vertexNames = new ArrayList<String>(vertices.keySet());
    vertexNames.sort(String::compareTo);
  }

  /** Builds the web graph (O) using the vertices data structure */
  private void buildO() {

    O = new double[vertexCount][vertexCount];
    for (int i = 0; i < vertexCount; i++) {
      String currentPage = vertexNames.get(i);
      Set<String> currentLinks = vertices.get(currentPage).outgoingLinks;

      for (int j = 0; j < vertexCount; j++) {
        String linkedPage = vertexNames.get(j);
        int outDegree = currentLinks.size();
        if (outDegree == 0) {
          // avoid sink holes
          O[i][j] = 1.0 / vertexCount;
        } else {
          if (currentLinks.contains(linkedPage)) {
            O[i][j] = 1.0 / outDegree;
          } else {
            O[i][j] = 0.0;
          }
        }

        // perform dampening
        O[i][j] = teleportation * O[i][j] + (1.0 - teleportation) * (1.0 / vertexCount);
      }
    }
  }

  /** Computes the page rank for each page (vertex) using the web matrix O */
  private void buildPageRank() {
    double[] rank = new double[vertexCount];

    // initialize all ranks to 1 to start
    // TODO: what should this be?
    for (int i = 0; i < rank.length; i++) {
      rank[i] = 1.0;
    }

    // initialize a previousRank vector for tracking purposes
    double[] previousRank = new double[vertexCount];
    for (int i = 0; i < previousRank.length; i++) {
      previousRank[i] = Double.MAX_VALUE;
    }

    // refine the rank vector until it's close enough
    while (Vector.norm(Vector.subtract(previousRank, rank)) > approximation) {
      previousRank = rank;
      rank = Matrix.multiply(O, rank);
    }

    // update each NodeInfo with the appropriate rank
    for (int i = 0; i < vertexNames.size(); i++) {
      vertices.get(vertexNames.get(i)).rank = rank[i];
    }
  }
}
