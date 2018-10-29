package io.nathanfriend.coms535.pa1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public abstract class Differential {

  /** The number of times a file has been accessed by this class */
  public int fileAccessCount = 0;

  /** The number of lines read from a file by this class */
  public int lineReadCount = 0;

  protected StopWatch stopWatch = new StopWatch();

  /** Used when returning results */
  protected String differentialName;

  protected File databaseFile;
  protected File diffFile;

  /**
   * Initializes a new Differential class
   *
   * @param databaseFile The full database file
   * @param diffFile The file containing the differences
   * @param differentialName The name of this Differential (used for reporting results)
   */
  public Differential(File databaseFile, File diffFile, String differentialName) {
    this.databaseFile = databaseFile;
    this.diffFile = diffFile;
    this.differentialName = differentialName;
  }

  /**
   * Retrieves a record from the database/differential file
   *
   * @param key The key of the record to retrieve
   * @return The DatabaseEntry if the entry was found, otherwise null
   */
  public abstract DatabaseEntry retrieveRecord(String key);

  /**
   * Searches for the key in the provided file
   *
   * @param key The key to search for
   * @param file The file to search for the key in
   * @return The DatabaseEntry if the entry was found, otherwise null
   */
  protected DatabaseEntry searchForRecord(String key, File file) {

    fileAccessCount++;

    int lineNumber = 0;

    try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
      String line;
      while ((line = reader.readLine()) != null) {

        lineNumber++;
        lineReadCount++;

        DatabaseEntry dbe = null;
        try {
          dbe = DatabaseEntry.parse(line);
        } catch (Exception e) {
          Exception parseException =
              new Exception(
                  String.format(
                      "A parsing occured while attempting to retrieve key \"%s\". "
                          + "Parsing error occured on line %d in file \"%s\". Full line: \"%s\". "
                          + "Skipping this line; search for the key in this file will continue.",
                      key, lineNumber, file.getAbsolutePath(), line),
                  e);

          parseException.printStackTrace();
        }

        if (dbe != null && key.equals(dbe.phrase.toLowerCase())) {
          return dbe;
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Gets the performance results for this Differential object
   *
   * @return A string with the results of this Differential run
   */
  public String getResults() {

    NumberFormat formatter = new DecimalFormat("#0.000");

    return String.format(
        "%-18s performed %d file accesses and read %d total lines from the files. "
            + "Total time spent: %s seconds",
        differentialName,
        fileAccessCount,
        lineReadCount,
        formatter.format(stopWatch.getNanoTime() / 1000000000.0));
  }
}
