import java.util.*;

public class MinHashMatrix {

  /** The number of permutations used to create this MinHash matrix */
  private int numPermutations;

  /** The parameters (constants and prime) used to generate this MinHash matrix */
  private PermutationParams permutationParams;

  /** The term document matrix used to generate this MinHash matrix */
  private TermDocumentMatrix tdMatrix;

  /** The int[][] representation of this MinHash matrix */
  private int[][] intMatrix;

  /**
   * Constructs a new MinHashMatrix instance
   *
   * @param tdMatrix The term document matrix
   * @param numPermutations The number of permutations to use when constructing the MinHash matrix
   */
  public MinHashMatrix(TermDocumentMatrix tdMatrix, int numPermutations) {

    this.numPermutations = numPermutations;
    this.tdMatrix = tdMatrix;
    List<String> allTerms = tdMatrix.getAllTerms();
    List<String> allDocs = tdMatrix.getAllDocuments();

    // generate the permutation parameters for this MinHash matrix
    int prime = Utility.findNextPrime(allTerms.size());
    permutationParams = new PermutationParams(prime);
    permutationParams.generate(numPermutations);

    // get the term document matrix as an int[][]
    intMatrix = new int[tdMatrix.getAllDocuments().size()][numPermutations];
    int[][] tdIntMatrix = tdMatrix.getMatrix();

    // loop over each document in the term document matrix
    for (int docIndex = 0; docIndex < tdIntMatrix.length; docIndex++) {

      Logger.log("Constructing MinHash signature for file " + allDocs.get(docIndex));

      // loop over every hash function
      for (int paramIndex = 0; paramIndex < permutationParams.constants.size(); paramIndex++) {
        AandB ab = permutationParams.constants.get(paramIndex);

        int min = Integer.MAX_VALUE;

        // loop over every term in the term document matrix
        for (int termIndex = 0; termIndex < tdIntMatrix[docIndex].length; termIndex++) {

          // get how many times the terms appeared in the current document
          int termFreq = tdIntMatrix[docIndex][termIndex];

          // if the term appeared more than once, hash the term using the current hash function
          if (termFreq > 0) {
            int termVal = allTerms.get(termIndex).hashCode();
            int hashed = (ab.a * termVal + ab.b) % permutationParams.prime;
            if (hashed < min) min = hashed;
          }
        }

        // set this minimum value in the appropriate spot in our MinHash matrix
        intMatrix[docIndex][paramIndex] = min;
      }
    }
  }

  /**
   * Gets the number of permutations used to construct this MinHash matrix
   *
   * @return The number of permutations
   */
  public int getNumPermutation() {
    return this.numPermutations;
  }

  /**
   * Gets the int[][] representation of this MinHash matrix
   *
   * @return The int[][] MinHash matrix
   */
  public int[][] getMatrix() {
    return intMatrix;
  }

  /**
   * Pretty-prints this MinHash matrix as a string. Note: don't use this method for large MinHash
   * tables.
   */
  @Override
  public String toString() {
    int[][] intMatrix = getMatrix();
    StringBuilder sb = new StringBuilder();

    // create the table headers - each column
    // represents a document
    int docIndex = 0;
    sb.append(String.format("%1$15s", ""));
    List<String> allDocuments = tdMatrix.getAllDocuments();
    for (String documentName : allDocuments) {
      sb.append(String.format("%1$11s", "D" + docIndex));
      docIndex++;
    }
    sb.append("\n");

    // generate a row for each hash function
    int paramIndex = 0;
    for (AandB ab : permutationParams.constants) {
      sb.append(String.format("%1$15s", "[" + ab.a + ", " + ab.b + "]"));
      for (int j = 0; j < allDocuments.size(); j++) {
        sb.append(String.format("%1$11s", Integer.toString(intMatrix[j][paramIndex])));
      }

      sb.append("\n");
      paramIndex++;
    }

    return sb.toString();
  }
}
