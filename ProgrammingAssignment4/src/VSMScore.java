import java.util.*;

public class VSMScore {

  /**
   * Returns the vector space vector for a document (or query)
   *
   * @param index The inverted index
   * @param allTerms The complete list of terms, sorted in the appropriate order
   * @param docTerms The terms (and their positions) in the document (or query)
   * @param docCount The total number of documents in the index
   * @return The vector
   */
  public static double[] getVector(
      Map<String, Map<String, List<Integer>>> index,
      List<String> allTerms,
      Map<String, List<Integer>> docTerms,
      int docCount) {
    double[] vector = new double[allTerms.size()];

    for (int i = 0; i < allTerms.size(); i++) {
      String term = allTerms.get(i);

      // the number of documents in which the term appears
      int termDocCount = 0;
      if (index.containsKey(term)) {
        termDocCount = index.get(term).size();
      }

      if (docTerms.containsKey(term)) {
        vector[i] =
            Math.sqrt(docTerms.get(term).size()) * Math.log10((double) docCount / termDocCount);
      }
    }

    return vector;
  }
}
