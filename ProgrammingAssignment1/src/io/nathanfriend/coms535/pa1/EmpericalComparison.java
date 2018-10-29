package io.nathanfriend.coms535.pa1;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help.Visibility;
import picocli.CommandLine.Option;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

@Command(
    name = "EmpericalComparison",
    mixinStandardHelpOptions = true,
    sortOptions = false,
    headerHeading = "@|bold,underline Usage:|@%n%n",
    synopsisHeading = "%n",
    descriptionHeading = "%n@|bold,underline Description:|@%n%n",
    parameterListHeading = "%n@|bold,underline Parameters:|@%n",
    optionListHeading = "%n@|bold,underline Options:|@%n",
    version = "Emperical Comparison 1.0",
    header = "Compares NaiveDifferential and BloomDifferential",
    description =
        "Runs the database/differential file simulation using "
            + "both a Bloom filter implementation and a non-Bloom filter implementation "
            + "and compares the performance of both approaches.")
public class EmpericalComparison implements Runnable {

  @Option(
      names = {"-b", "--databaseFile"},
      description = "The database file",
      required = true)
  private File databaseFile;

  @Option(
      names = {"-d", "--diffFile"},
      description = "The differential file",
      required = true)
  private File diffFile;

  @Option(
      names = {"-g", "--gramsFile"},
      description = "The file containing all keys in --databaseFile",
      required = true)
  private File gramsFile;

  @Option(
      names = {"-i", "--iterations"},
      description = "The number of test retrievals to perform",
      defaultValue = "100",
      showDefaultValue = Visibility.ALWAYS,
      required = true)
  private int iterations;

  public static void main(String[] args) {
    CommandLine.run(new EmpericalComparison(), System.out, args);
  }

  /**
   * Runs the simulation using both NaiveDifferential and BloomDifferential
   * and report back on the performance comparison
   */
  @Override
  public void run() {

    // test to make sure all file inputs are valid
    if (!databaseFile.exists()) {
      System.out.println(String.format("Database file %s was not found!", databaseFile));
      System.exit(1);
    }
    if (!diffFile.exists()) {
      System.out.println(String.format("Differential file %s was not found!", diffFile));
      System.exit(1);
    }
    if (!gramsFile.exists()) {
      System.out.println(String.format("Key file %s was not found!", gramsFile));
      System.exit(1);
    }

    // create the two competing programs
    Differential naive = new NaiveDifferential(databaseFile, diffFile);
    Differential bloom = new BloomDifferential(databaseFile, diffFile);

    // read all of the possible database keys into memory
    ArrayList<String> allKeys = new ArrayList<String>();
    try {
      Stream<String> lines = Files.lines(Paths.get(gramsFile.getAbsolutePath()));
      lines.forEachOrdered(
          l -> {
            allKeys.add(l);
          });
      lines.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // run <iterations> retrievals, randomly picking
    // from the list of keys loaded above
    Random random = new Random();
    for (int i = 0; i < iterations; i++) {
      int keyIndex = random.nextInt(allKeys.size());
      String keyToFetch = allKeys.get(keyIndex);

      naive.retrieveRecord(keyToFetch);
      bloom.retrieveRecord(keyToFetch);
    }

    // print the results
    System.out.println(naive.getResults());
    System.out.println(bloom.getResults());
    System.out.println();

    NumberFormat formatter = new DecimalFormat("#0.00");

    String fileAccesses =
        formatter.format(
            ((1.0 * naive.fileAccessCount - bloom.fileAccessCount) / naive.fileAccessCount)
                * 100.0);

    String lineReads =
        formatter.format(
            ((1.0 * naive.lineReadCount - bloom.lineReadCount) / naive.lineReadCount) * 100.0);

    String speed =
        formatter.format(
            ((1.0 * naive.stopWatch.getNanoTime() - bloom.stopWatch.getNanoTime())
                    / naive.stopWatch.getNanoTime())
                * 100.0);

    System.out.println(
        String.format(
            "Bloom Differential performed %s%% fewer file accesses, "
                + "read %s%% fewer lines, "
                + "and performed %s%% faster.",
            fileAccesses, lineReads, speed));
  }
}
