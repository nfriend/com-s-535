import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class MinHashTime {

  /**
   * Tests whether it is faster to estimate Jaccard Similarities using a MinHash matrix
   *
   * @param folder The folder containing the files to compare
   * @param numPermutations The number of permutations to use when constructing the MinHash matrix
   * @return A report containing the timings of the 3 tasks
   */
  public TimeReport timer(String folder, int numPermutations) {
    TimeReport report = new TimeReport();
    Date start, end;

    // get a list of all the filenames in the folder
    File docFolder = new File(folder);
    List<String> allDocs =
        Arrays.asList(docFolder.listFiles())
            .stream()
            .map(f -> f.getName())
            .collect(Collectors.toList());

    // time the construction of the MinHashSimilarities instance
    start = new Date();
    MinHashSimilarities mhs = new MinHashSimilarities(folder, numPermutations);
    end = new Date();
    report.minHashSimilaritiesConstruction = (end.getTime() - start.getTime()) / 1000.0;

    // time the computation of the exact Jaccard between all files
    start = new Date();

    // compare each file to every other file
    for (int i = 0; i < allDocs.size(); i++) {
      String doc1 = allDocs.get(i);
      Logger.log("Computing exact Jaccard for file " + doc1);
      for (int j = i + 1; j < allDocs.size(); j++) {
        String doc2 = allDocs.get(j);

        mhs.exactJaccard(doc1, doc2);
      }
    }

    end = new Date();
    report.exactJaccard = (end.getTime() - start.getTime()) / 1000.0;

    // time the computation of the approximate Jaccard between all files
    start = new Date();

    // compare each file to every other file
    for (int i = 0; i < allDocs.size(); i++) {
      String doc1 = allDocs.get(i);
      Logger.log("Computing approximate Jaccard for file " + doc1);
      for (int j = i + 1; j < allDocs.size(); j++) {
        String doc2 = allDocs.get(j);

        mhs.approximateJaccard(doc1, doc2);
      }
    }

    end = new Date();
    report.approximateJaccard = (end.getTime() - start.getTime()) / 1000.0;

    return report;
  }
}
