import java.text.DecimalFormat;

/** A container class that holds a document and its score */
public class DocumentAndScores implements Comparable<DocumentAndScores> {

  /** The document */
  public String document;

  /** The document's relevance score */
  public double relevance;

  /** The document's TPScore */
  public double tpScore;

  /** The document's VSMScore */
  public double vsmScore;

  /**
   * Constructs a new DocumentAndStores.
   *
   * @param document The document
   * @param relevant The document's relevance score
   */
  public DocumentAndScores(String document, double relevance) {
    this.document = document;
    this.relevance = relevance;
  }

  /**
   * Constructs a new DocumentAndScores
   *
   * @param document The document
   * @param relevance The document's relevance score
   * @param tpScore The document's TPScore
   * @param vsmScore The document's VSMScore
   */
  public DocumentAndScores(String document, double relevance, double tpScore, double vsmScore) {
    this.document = document;
    this.relevance = relevance;
    this.tpScore = tpScore;
    this.vsmScore = vsmScore;
  }

  @Override
  public int compareTo(DocumentAndScores other) {
    return Double.compare(other.relevance, relevance);
  }

  /**
   * Prints the table header for the toString() method;
   *
   * @return A header string
   */
  public static String getTableHeader() {
    return String.format("%-60s %8s %8s %16s", "Document", "TPScore", "VSScore", "Relevance Score")
        + "\n-----------------------------------------------------------------------------------------------";
  }

  @Override
  public String toString() {
    DecimalFormat df = new DecimalFormat("#0.00000");
    return String.format(
        "%-60s %8s %8s %16s",
        document, df.format(tpScore), df.format(vsmScore), df.format(relevance));
  }
}
