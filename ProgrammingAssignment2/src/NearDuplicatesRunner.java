import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NearDuplicatesRunner {

  /**
   * Runs the NearDuplicates program
   *
   * @param args The command-line arguments (no arguments are used for this program)
   */
  public static void main(String[] args) {
    String folder = System.getProperty("user.dir") + "\\src\\F17PA2";

    // test the LSH class using two different thresholds
    List<Double> thresholdsToTest = new ArrayList<Double>();
    thresholdsToTest.add(0.85);
    thresholdsToTest.add(0.4);

    // the number of permutations to use in the MinHash matrix
    int numPermutations = 600;

    for (double threshold : thresholdsToTest) {

      NearDuplicates nd = new NearDuplicates(folder, numPermutations, threshold);

      // test each of these documents using the current threshold value
      List<String> docsToTest = new ArrayList<String>();
      docsToTest.add("space-0.txt");
      docsToTest.add("baseball0.txt");
      docsToTest.add("hockey0.txt");
      docsToTest.add("space-91.txt");
      docsToTest.add("hockey53.txt");

      for (String doc : docsToTest) {

        // find near duplicate documents to the current document
        List<String> similarDocs = nd.nearDuplicateDetector(doc);

        // sort the matched documents alphabetically
        Collections.sort(similarDocs);

        // print the result
        System.out.println(
            String.format(
                "Found the following near duplicates of file %s using %d permutations and threshold value %.2f: %s\n",
                doc, numPermutations, threshold, String.join(", ", similarDocs)));
      }
    }
  }
}
