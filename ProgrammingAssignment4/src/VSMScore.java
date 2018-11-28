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
  public static float[] getVector(
      Map<String, Map<String, List<Integer>>> index,
      List<String> allTerms,
      Map<String, List<Integer>> docTerms,
      int docCount) {
    float[] vector = new float[allTerms.size()];

    for (int i = 0; i < allTerms.size(); i++) {
      String term = allTerms.get(i);

      // the number of documents in which the term appears
      int termDocCount = 0;
      if (index.containsKey(term)) {
        termDocCount = index.get(term).size();
      }

      if (docTerms.containsKey(term)) {
        vector[i] =
            (float)
                (Math.sqrt(docTerms.get(term).size())
                    * Math.log10((double) docCount / termDocCount));
      }
    }

    return vector;
  }

  /**
   * Computes the cosine similarity between two vectors of equal dimensionality as defined here
   * https://en.wikipedia.org/wiki/Cosine_similarity#Definition
   *
   * @param vector1 The first vector
   * @param vector2 The second vector
   * @return The cosine similarity
   */
  public static float cosSim(float[] vector1, float[] vector2) {
    if (vector1.length != vector2.length) {
      throw new IllegalArgumentException(
          "Vectors must have the same dimensionality to compute cosine similarity");
    }
    if (vector1.length == 0) {
      throw new IllegalArgumentException("Vector dimensionality must be greater than 0");
    }

    float numerator = 0;
    float a2Sum = 0;
    float b2Sum = 0;

    for (int i = 0; i < vector1.length; i++) {
      numerator += vector1[i] * vector2[i];
      a2Sum += Math.pow(vector1[i], 2);
      b2Sum += Math.pow(vector2[i], 2);
    }

    float denominator = (float) (Math.sqrt(a2Sum) * Math.sqrt(b2Sum));

    if (denominator == 0) {
      return 0;
    }

    return numerator / denominator;
  }
}
