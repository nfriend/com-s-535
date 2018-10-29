import java.util.*;

public class LSH {

  /** Holds the results of LSH */
  private List<HashMap<Integer, List<String>>> buckets =
      new ArrayList<HashMap<Integer, List<String>>>();

  /** The number of LSH bands to use */
  private int bands;

  /** The number of permutations used in the MinHash matrix */
  private int k;

  /** The number of rows in each band */
  private int r;

  /** The MinHash matrix */
  private int[][] minHashMatrix;

  /** The list of document names in the MinHash matrix */
  private String[] docNames;

  /**
   * Constructs an instance of LSH
   *
   * @param minHashMatrix The MinHash matrix containing the signatures to compare
   * @param docNames An array of Strings consisting of names of documents/files in the document
   *     collection
   * @param bands The number of bands to be used to perform locality sensitive hashing.
   */
  public LSH(int[][] minHashMatrix, String[] docNames, int bands) {

    this.minHashMatrix = minHashMatrix;
    this.bands = bands;
    this.docNames = docNames;

    // ensure that we can divide the minHashMatrix evenly into <bands> bands
    k = minHashMatrix[0].length;
    if (((double) k / bands) % 1 != 0) {
      throw new IllegalArgumentException(
          String.format(
              "Invalid input for bands: %d.  A MinHash table with %d hash function cannot be evenly divided into %d bands.",
              bands, k, bands));
    }

    // safe to do integer division here because of the check above
    r = k / bands;

    // initialize the bucket list
    for (int i = 0; i < bands; i++) {
      buckets.add(new HashMap<Integer, List<String>>());
    }

    // perform LSH
    for (int docIndex = 0; docIndex < docNames.length; docIndex++) {
      for (int bandIndex = 0; bandIndex < bands; bandIndex++) {
        HashMap<Integer, List<String>> bucket = buckets.get(bandIndex);
        int hashValue =
            hashBand(Arrays.copyOfRange(minHashMatrix[docIndex], r * bandIndex, r * bandIndex + r));

        if (!bucket.containsKey(hashValue)) {
          bucket.put(hashValue, new ArrayList<String>());
        }

        bucket.get(hashValue).add(docNames[docIndex]);
      }
    }
  }

  /**
   * Hashes a MinHash band
   *
   * @param band The band to hash
   * @return The hash value of the band
   */
  private int hashBand(int[] band) {
    return Arrays.hashCode(band);
  }

  /**
   * Takes name of a document docName as parameter and returns an array list of names of the near
   * duplicate documents
   *
   * @param docName The name of a document
   * @return A list of names of near duplicate documents
   */
  public ArrayList<String> nearDuplicatesOf(String docName) {
    int docIndex = Arrays.asList(docNames).indexOf(docName);

    // validate that we were passed a valid document
    if (docIndex == -1) {
      throw new IllegalArgumentException(
          String.format("The document '%s' does not exist in the MinHash matrix", docName));
    }

    // store the document names in a Set to remove duplicates
    Set<String> nearDupsSet = new HashSet<String>();

    // perform a LSH on the document's MinHash signature
    // to retrieve all similar documents
    for (int bandIndex = 0; bandIndex < bands; bandIndex++) {
      HashMap<Integer, List<String>> bucket = buckets.get(bandIndex);
      int hashValue =
          hashBand(Arrays.copyOfRange(minHashMatrix[docIndex], r * bandIndex, r * bandIndex + r));
      List<String> similarDocs = bucket.get(hashValue);
      for (String doc : similarDocs) {
        nearDupsSet.add(doc);
      }
    }

    // remove the document itself from the set
    nearDupsSet.remove(docName);

    // convert the set into an ArrayList and return
    ArrayList<String> nearDups = new ArrayList<String>();
    nearDups.addAll(nearDupsSet);
    return nearDups;
  }
}
