package io.nathanfriend.coms535.pa1.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import io.nathanfriend.coms535.pa1.Appearance;
import io.nathanfriend.coms535.pa1.DatabaseEntry;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Help.Visibility;

/**
 * Generates fake data used to test the EmpericalComparisons program
 *
 * @author Nathan Friend
 */
@Command(
    name = "FakeDataGenerator",
    mixinStandardHelpOptions = true,
    sortOptions = false,
    headerHeading = "@|bold,underline Usage:|@%n%n",
    synopsisHeading = "%n",
    descriptionHeading = "%n@|bold,underline Description:|@%n%n",
    parameterListHeading = "%n@|bold,underline Parameters:|@%n",
    optionListHeading = "%n@|bold,underline Options:|@%n",
    version = "Fake Data Generator 1.0",
    header = "Generate fake data for testing",
    description = "Creates fake versions of database.txt, DiffFile.txt, and grams.txt")
public class FakeDataGenerator implements Runnable {

  @Option(
      names = {"-r", "--recordCount"},
      description = "The number of fake records to generate in database.txt",
      defaultValue = "10000",
      showDefaultValue = Visibility.ALWAYS,
      required = true)
  private int recordCount;

  @Option(
      names = {"-d", "--directory"},
      description = "The directory to generate the files in",
      required = true)
  private File directory;

  private Random random = new Random();

  @Override
  public void run() {

    System.out.println(
        String.format(
            "Generating %d records in the directory \"%s\"...",
            recordCount, directory.getAbsolutePath()));

    PrintWriter databasePr = null, diffFilePr = null, gramsPr = null;

    try {
      databasePr =
          new PrintWriter(Paths.get(directory.getAbsolutePath(), "database.txt").toString());
      diffFilePr =
          new PrintWriter(Paths.get(directory.getAbsolutePath(), "DiffFile.txt").toString());
      gramsPr = new PrintWriter(Paths.get(directory.getAbsolutePath(), "grams.txt").toString());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    for (int i = 0; i < recordCount; i++) {
      DatabaseEntry dbe = getRandomDatabaseEntry();
      databasePr.println(dbe.toString());

      // 1/10 chance of also writing this file to DiffFile
      if (random.nextInt(10) == 5) {
        diffFilePr.println(dbe.toString());
      }

      gramsPr.println(dbe.phrase);
    }

    databasePr.close();
    diffFilePr.close();
    gramsPr.close();

    System.out.println("Done!");
  }

  private DatabaseEntry getRandomDatabaseEntry() {
    DatabaseEntry dbe = new DatabaseEntry();

    dbe.phrase = String.join(" ", generateRandomWords(4));
    dbe.appearances = generateRandomAppearances();

    return dbe;
  }

  // borrowed from https://stackoverflow.com/a/4952066/1063392
  private String[] generateRandomWords(int numberOfWords) {
    String[] randomStrings = new String[numberOfWords];
    for (int i = 0; i < numberOfWords; i++) {
      char[] word =
          new char
              [random.nextInt(8)
                  + 3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
      for (int j = 0; j < word.length; j++) {
        word[j] = (char) ('a' + random.nextInt(26));
      }
      randomStrings[i] = new String(word);
    }
    return randomStrings;
  }

  private ArrayList<Appearance> generateRandomAppearances() {
    ArrayList<Appearance> apps = new ArrayList<Appearance>();
    for (int i = 0; i < random.nextInt(10) + 2; i++) {
      int year = random.nextInt(500) + 1500;
      int times = random.nextInt(50) + 10;
      int books = random.nextInt(20) + 5;
      apps.add(new Appearance(year, times, books));
    }
    return apps;
  }

  public static void main(String[] args) {
    CommandLine.run(new FakeDataGenerator(), System.out, args);
  }
}
