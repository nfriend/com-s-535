import java.util.*;

public class TermDocumentMatrix {

  /**
   * Keeps track of all of the terms in each of the documents while importing terms from the
   * documents
   */
  private HashMap<String, HashMap<String, Integer>> matrix =
      new HashMap<String, HashMap<String, Integer>>();

  /** The complete list of all terms. Each term only appears once in this list. */
  private LinkedHashSet<String> allTerms = new LinkedHashSet<String>();

  /** The list of all documents used in this term document matrix */
  private List<String> allDocuments = new ArrayList<String>();

  /**
   * Loads a list of terms into this term document matrix for the provided document
   *
   * @param documentName The name of the document containing the terms
   * @param terms The terms from the document
   */
  public void loadTerms(String documentName, List<String> terms) {

    allDocuments.add(documentName);

    HashMap<String, Integer> document = new HashMap<String, Integer>();

    for (String term : terms) {

      allTerms.add(term);

      if (document.containsKey(term)) {
        Integer count = document.get(term);
        document.put(term, count + 1);
      } else {
        document.put(term, 1);
      }
    }

    matrix.put(documentName, document);
  }

  /**
   * Returns the int[][] representation of this term document matrix
   *
   * @return The int[][] matrix
   */
  public int[][] getMatrix() {

    Logger.log("Generating int[][] term document matrix");

    int documentCount = allDocuments.size();
    int termCount = allTerms.size();

    int[][] intMatrix = new int[documentCount][termCount];

    int documentIndex = 0;
    for (String documentName : allDocuments) {
      Logger.log("Creating column for file " + documentName);
      HashMap<String, Integer> terms = matrix.get(documentName);
      int termIndex = 0;
      for (String term : allTerms) {

        if (termIndex % 1000 == 0) {
          Logger.log("Processing term #" + termIndex + "...");
        }

        if (terms.containsKey(term)) {
          intMatrix[documentIndex][termIndex] = terms.get(term).intValue();
        }
        termIndex++;
      }
      documentIndex++;
    }

    return intMatrix;
  }

  /**
   * Returns all terms included in this term document matrix
   *
   * @return A list of all terms
   */
  public List<String> getAllTerms() {
    return new ArrayList<String>(allTerms);
  }

  /**
   * Returns all documents included in this term document matrix
   *
   * @return The list of all documents
   */
  public List<String> getAllDocuments() {
    return allDocuments;
  }

  /**
   * Pretty-prints this term document matrix for debugging purposes. Note: do not call this method
   * on large matrices.
   */
  @Override
  public String toString() {
    int[][] intMatrix = getMatrix();
    StringBuilder sb = new StringBuilder();

    // create the table headers - each column
    // represents a document
    int docIndex = 0;
    sb.append(String.format("%1$15s", ""));
    for (String documentName : allDocuments) {
      sb.append(String.format("%1$4s", "D" + docIndex));
      docIndex++;
    }
    sb.append("\n");

    // generate a row for each term
    int termIndex = 0;
    for (String term : allTerms) {
      sb.append(String.format("%1$15s", term));
      for (int j = 0; j < allDocuments.size(); j++) {
        sb.append(String.format("%1$4s", Integer.toString(intMatrix[j][termIndex])));
      }

      sb.append("\n");
      termIndex++;
    }

    return sb.toString();
  }
}
