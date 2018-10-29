package io.nathanfriend.coms535.pa1;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.UUID;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help.Visibility;
import picocli.CommandLine.Option;

@Command(
    name = "FalsePositives",
    mixinStandardHelpOptions = true,
    sortOptions = false,
    headerHeading = "@|bold,underline Usage:|@%n%n",
    synopsisHeading = "%n",
    descriptionHeading = "%n@|bold,underline Description:|@%n%n",
    parameterListHeading = "%n@|bold,underline Parameters:|@%n",
    optionListHeading = "%n@|bold,underline Options:|@%n",
    version = "False Positives 1.0",
    header = "Run Bloom filter tests",
    description =
        "Runs a number of tests against the 3 Bloom filter implementations to emperically determine their false positive rate.")
public class FalsePositives implements Runnable {

  @Option(
      names = {"-s", "--setSize"},
      description = "The size of the set that will be added to the Bloom filter.",
      defaultValue = "10000",
      showDefaultValue = Visibility.ALWAYS,
      required = true)
  private int setSize;

  @Option(
      names = {"-b", "--bitsPerElement"},
      description = "The number of bits to allocate per element in the set.",
      defaultValue = "16",
      showDefaultValue = Visibility.ALWAYS,
      required = true)
  private int bitsPerElement;

  @Option(
      names = {"-i", "--iterations"},
      description = "The number of tests to run to emperically determine Bloom filter statistics.",
      defaultValue = "100000",
      showDefaultValue = Visibility.ALWAYS,
      required = true)
  private int iterations;

  @Override
  public void run() {
    FalsePositives.estimateFalsePositives(setSize, bitsPerElement, iterations);
  }

  public static void main(String[] args) {
    CommandLine.run(new FalsePositives(), System.out, args);
  }

  /**
   * Runs the simulation and emperical determine the false
   * positive rates of the 3 different Bloom filter implementations
   * @param setSize The size of the set to insert into the filters
   * @param bitsPerElement The number of bits per element to allocate in the filter
   * @param iterations The number of times to run the test
   */
  public static void estimateFalsePositives(int setSize, int bitsPerElement, int iterations) {
    FalsePositivesResults results = new FalsePositivesResults(setSize, bitsPerElement, iterations);

    BloomFilterFNV fnv = new BloomFilterFNV(setSize, bitsPerElement);
    BloomFilterMurmur murmur = new BloomFilterMurmur(setSize, bitsPerElement);
    BloomFilterRan ran = new BloomFilterRan(setSize, bitsPerElement);

    IBloomFilter[] allFilters = new IBloomFilter[] {fnv, murmur, ran};

    // generate a bunch of random strings and add them to each filter
    for (int i = 0; i < setSize; i++) {
      String randomString = UUID.randomUUID().toString();
      for (IBloomFilter f : allFilters) {
        f.add(randomString);
      }
    }

    // record the false positive rate for each filter
    for (int i = 0; i < iterations; i++) {
      String randomString = UUID.randomUUID().toString();

      if (fnv.appears(randomString)) results.fnvNumOfFalsePositives++;
      if (murmur.appears(randomString)) results.murmurNumOfFalsePositives++;
      if (ran.appears(randomString)) results.ranNumOfFalsePositives++;
    }

    System.out.println(results.toString());
  }
}

/**
 * Holds the results of a FalsePositive test run and
 * pretty-prints the results.
 * @author Nathan Friend
 *
 */
class FalsePositivesResults {

  public int setSize;
  public int bitsPerElement;
  public int iterations;
  public int fnvNumOfFalsePositives;
  public int murmurNumOfFalsePositives;
  public int ranNumOfFalsePositives;

  public FalsePositivesResults(int setSize, int bitsPerElement, int iterations) {
    this.setSize = setSize;
    this.bitsPerElement = bitsPerElement;
    this.iterations = iterations;
  }

  @Override
  public String toString() {

    double fnvFalsePositivePercent = (1.0 * fnvNumOfFalsePositives / iterations) * 100;
    double murmurFalsePositivePercent = (1.0 * murmurNumOfFalsePositives / iterations) * 100;
    double ranFalsePositivePercent = (1.0 * ranNumOfFalsePositives / iterations) * 100;

    double theoreticalFalsePositivePercent = Math.pow(0.618, bitsPerElement) * 100;
    long theoreticalNumOfFalsePositives =
        Math.round(iterations * theoreticalFalsePositivePercent / 100);

    NumberFormat formatter = new DecimalFormat("#0.00");

    return (String.format(
            "False positive rates for setSize=%d, bitsPerElement=%d, and iterations=%d:\n",
            setSize, bitsPerElement, iterations)
        + String.format(
            "------------------------------------------------------------------------------\n")
        + String.format(
            "Theoretical:        %4s%% (%-5s out of %s iterations)\n",
            formatter.format(theoreticalFalsePositivePercent),
            theoreticalNumOfFalsePositives,
            iterations)
        + String.format(
            "BloomFilterFNV:     %4s%% (%-5s out of %s iterations)\n",
            formatter.format(fnvFalsePositivePercent), fnvNumOfFalsePositives, iterations)
        + String.format(
            "BloomFilterMurmur:  %4s%% (%-5s out of %s iterations)\n",
            formatter.format(murmurFalsePositivePercent), murmurNumOfFalsePositives, iterations)
        + String.format(
            "BloomFilterRan:     %4s%% (%-5s out of %s iterations)\n",
            formatter.format(ranFalsePositivePercent), ranNumOfFalsePositives, iterations));
  }
}
