/** A class used to hold the results of the timer() method */
public class TimeReport {

  /** The time taken (in seconds) to construct a MinHashSimilarities instance */
  public double minHashSimilaritiesConstruction;
  /** The time taken (in seconds) to compute the exact Jaccard similarity between all documents */
  public double exactJaccard;
  /**
   * The time taken (in seconds) to compute the approximate Jaccard similarity between all documents
   */
  public double approximateJaccard;

  /** Prints this report in a human-readable fashion */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(
        String.format(
            "Number of seconds to construct a MinHashSimilarities instance: %.3f\n",
            minHashSimilaritiesConstruction));
    sb.append(
        String.format(
            "Number of seconds to compute the exact Jaccard similarity between all documents: %.3f\n",
            exactJaccard));
    sb.append(
        String.format(
            "Number of seconds to compute the approximate Jaccard similarity between all documents: %.3f",
            approximateJaccard));
    return sb.toString();
  }
}
