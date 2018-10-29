import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.*;

public class MinHashAccuracy {

  /**
   * Tests the how accurately a MinHash matrix can be used to estimate Jaccard Similarity
   *
   * @param folder The folder containing the files to compare
   * @param numPermutations The number of permutations to use when MinHashing
   * @param epsilon The error threshold
   * @return The number of pairs for which exact and approximate similarities differ by more then
   *     epsilon.
   */
  public int accuracy(String folder, int numPermutations, double epsilon) {
    MinHashSimilarities mhs = new MinHashSimilarities(folder, numPermutations);

    // get a list of all the filenames in the folder
    File docFolder = new File(folder);
    List<String> allDocs =
        Arrays.asList(docFolder.listFiles())
            .stream()
            .map(f -> f.getName())
            .collect(Collectors.toList());

    // differCount is the number of pairs for which the exact and the approximate Jaccard differ by
    // more than epsilon
    int differCount = 0;

    // compare each file to every other file
    for (int i = 0; i < allDocs.size(); i++) {
      String doc1 = allDocs.get(i);
      Logger.log("Computing exact and approximate Jaccard for file " + doc1);
      for (int j = i + 1; j < allDocs.size(); j++) {
        String doc2 = allDocs.get(j);

        double exact = mhs.exactJaccard(doc1, doc2);
        double approx = mhs.approximateJaccard(doc1, doc2);

        if (Math.abs(exact - approx) > epsilon) {
          differCount++;
        }
      }
    }

    // return the number of file that differed
    return differCount;
  }
}
