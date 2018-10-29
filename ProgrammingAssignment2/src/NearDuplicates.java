import java.util.*;

public class NearDuplicates {

  /** The MinHash instance */
  private MinHash mh;

  /** The LSH instance */
  private LSH lsh;

  /**
   * Creates an instance of NearDuplicates
   *
   * @param folder The folder containing the documents to compare
   * @param numPermutations The number of permutations to use when creating the MinHash matrix
   * @param threshold The similarity threshold to use when comparing documents
   */
  public NearDuplicates(String folder, int numPermutations, double threshold) {
    mh = new MinHash(folder, numPermutations);
    int bands = chooseBandCount(numPermutations, threshold);

    // log our choice of b and r for the given threshold
    int r = numPermutations / bands;
    Logger.log(
        String.format(
            "Decided to use b: %d and r: %d to best match similarity threshold %f",
            bands, r, threshold));

    lsh = new LSH(mh.minHashMatrix(), mh.allDocs(), bands);
  }

  /**
   * Returns a list of documents that are at least s-similar to docName
   *
   * @param docName The name of the document
   * @return A list of documents
   */
  public ArrayList<String> nearDuplicateDetector(String docName) {
    return lsh.nearDuplicatesOf(docName);
  }

  /**
   * Choose the most ideal band count based on the provided threshold value. There may be a better
   * way to compute this value.
   *
   * @param numPermutations The number of permutations used to build the MinHash matrix
   * @param threshold The similarity threshold
   * @return The ideal number of bands for the provided threshold
   */
  private int chooseBandCount(int numPermutations, double threshold) {
    int idealBandCount = 1;
    double bestYetDistanceFromThreshold = Double.MAX_VALUE;

    // loop through all possible band count options and
    // select the one that gets the closest threshold value
    // to the requested threshold value
    for (int b = 0; b < numPermutations; b++) {
      // only consider this band count if it's a valid option
      if (((double) numPermutations / b) % 1 == 0) {

        // compute the row count for the current b
        int r = numPermutations / b;

        // compute the theoretical s for b and r
        double s = Math.pow(1.0 / b, 1.0 / r);

        // compute the distance of this theoretical s
        // to the requested threshold value
        double distance = Math.abs(threshold - s);

        // if this is the best estimate we've seen so far, save it
        if (distance < bestYetDistanceFromThreshold) {
          bestYetDistanceFromThreshold = distance;
          idealBandCount = b;
        }
      }
    }

    return idealBandCount;
  }
}
