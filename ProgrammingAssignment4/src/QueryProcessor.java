import java.util.*;
import java.util.stream.Collectors;

/** Uses the PositionalIndex class to return relevant documents based on a query */
public class QueryProcessor {

  /** The PositionIndex used for searching */
  private PositionalIndex pi;

  /** The list of all indexed documents */
  private List<String> allDocs;

  /**
   * Instantiates a new QueryProcessor object
   *
   * @param rootFolder The folder containing the files to index
   */
  public QueryProcessor(String rootFolder) {
    pi = new PositionalIndex(rootFolder);
    allDocs = pi.getAllDocuments();
  }

  /**
   * Returns the top <tt>k</tt> documents relevant to <tt>query</tt>
   *
   * @param query The search query
   * @param k The number of documents to retrieve
   * @return The top K documents
   */
  public List<String> topKDocs(String query, int k) {
    List<String> result =
        allDocs
            .stream()
            .map(
                d -> {
                  return new DocumentAndScore(d, pi.Relevance(query, d));
                })
            .sorted()
            .limit(k)
            .map(
                ds -> {
                  System.out.println(ds.toString());
                  return ds.document;
                })
            .collect(Collectors.toList());

    return result;
  }

  /** A container class that holds a document and its score */
  class DocumentAndScore implements Comparable<DocumentAndScore> {

    /** The document */
    public String document;

    /** The document's score */
    public double score;

    /**
     * Constructs a new DocumentAndStore.
     *
     * @param document The document
     * @param score The document's score
     */
    public DocumentAndScore(String document, double score) {
      this.document = document;
      this.score = score;
    }

    @Override
    public int compareTo(DocumentAndScore other) {
      return Double.compare(other.score, score);
    }

    @Override
    public String toString() {
      return "DocumentAndScore [document=" + document + ", score=" + score + "]";
    }
  }
}
