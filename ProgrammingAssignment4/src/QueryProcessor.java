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
    return allDocs
        .parallelStream()
        .map(
            d -> {
              return new DocumentAndScores(d, pi.Relevance(query, d));
            })
        .sorted()
        .limit(k)
        .map(ds -> ds.document)
        .collect(Collectors.toList());
  }

  /**
   * Does the same thing as <tt>topKDocs</tt>, but returns the entire DocumentAndScore object for
   * reporting purposes
   *
   * @param query The search query
   * @param k The number of documents to retrieve
   * @return The top K documents
   */
  public List<DocumentAndScores> topKDocsForReport(String query, int k) {
    return allDocs
        .parallelStream()
        .map(
            d -> {
              return new DocumentAndScores(
                  d, pi.Relevance(query, d), pi.TPScore(query, d), pi.VSScore(query, d));
            })
        .sorted()
        .limit(k)
        .collect(Collectors.toList());
  }
}
