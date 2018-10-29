import java.util.Arrays;
import java.util.List;

public class MinHashSimilarities {

  /** The MinHash instance */
  private MinHash mh;

  /** The list of all documents built into the MinHash matrix */
  private List<String> allDocs;

  /** The term document matrix used to build the MinHash matrix */
  private int[][] termDocumentMatrix;

  /** The int[][] representation of the MinHash matrix */
  private int[][] minHashMatrix;

  /**
   * Creates an instance of MinHash, and calls the methods termDocumentMatrix and minHashMatrix to
   * store the respective matrices.
   *
   * @param folder THe folder containing the documents to process
   * @param numPermutations The number of permutations to use when creating the MinHash matrix
   */
  public MinHashSimilarities(String folder, int numPermutations) {
    mh = new MinHash(folder, numPermutations);
    allDocs = Arrays.asList(mh.allDocs());
    termDocumentMatrix = mh.termDocumentMatrix();
    minHashMatrix = mh.minHashMatrix();
  }

  /**
   * Gets names of two files (in the document collection) file1 and file2 as parameters and returns
   * the exact Jaccard Similarity of the files.
   *
   * @param file1 The first file to compare
   * @param file2 The second file to compare
   * @return The exact Jaccard similarity between the two files
   */
  public double exactJaccard(String file1, String file2) {
    List<String> allDocs = Arrays.asList(mh.allDocs());
    int file1Index = allDocs.indexOf(file1);
    int file2Index = allDocs.indexOf(file2);

    // validate that we were passed valid document names
    if (file1Index < 0) {
      throw new IllegalArgumentException(
          String.format(
              "The file '%s' was not found in the list this MinHash matrix's documents", file1));
    }
    if (file2Index < 0) {
      throw new IllegalArgumentException(
          String.format(
              "The file '%s' was not found in the list this MinHash matrix's documents", file2));
    }

    int intersectionSum = 0;
    int unionSum = 0;
    for (int termIndex = 0; termIndex < mh.numTerms(); termIndex++) {
      intersectionSum +=
          Math.min(
              termDocumentMatrix[file1Index][termIndex], termDocumentMatrix[file2Index][termIndex]);
      unionSum +=
          Math.max(
              termDocumentMatrix[file1Index][termIndex], termDocumentMatrix[file2Index][termIndex]);
    }

    return (double) intersectionSum / unionSum;
  }

  /**
   * Estimates and returns the Jaccard similarity of documents file1 and file2 by comparing the
   * MinHash signatures of file1 and file2
   *
   * @param file1 The first file to compare
   * @param file2 The second file to compare
   * @return The estimated Jaccard similarity using the MinHash matrix
   */
  public double approximateJaccard(String file1, String file2) {
    int[] file1MinHashSig = minHashSig(file1);
    int[] file2MinHashSig = minHashSig(file2);

    int agreementCount = 0;
    for (int i = 0; i < file1MinHashSig.length; i++) {
      if (file1MinHashSig[i] == file2MinHashSig[i]) {
        agreementCount++;
      }
    }

    return (double) agreementCount / file1MinHashSig.length;
  }

  /**
   * Returns the MinHash signature of a document
   *
   * @param fileName The name of the file
   * @return The MinHash signature
   */
  public int[] minHashSig(String fileName) {
    int fileIndex = allDocs.indexOf(fileName);
    return minHashMatrix[fileIndex];
  }
}
