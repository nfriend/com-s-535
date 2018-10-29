package io.nathanfriend.coms535.pa1;

import java.io.File;

public class NaiveDifferential extends Differential {

  /**
   * Instantiates a new NaiveDifferential object
   *
   * @param databaseFile The full database file
   * @param diffFile The differences file
   */
  public NaiveDifferential(File databaseFile, File diffFile) {
    super(databaseFile, diffFile, "NaiveDifferential");
  }

  @Override
  public DatabaseEntry retrieveRecord(String key) {

    stopWatch.start();

    if (key == null) {
      throw new UnsupportedOperationException("The key to retrieve must not be null.");
    }

    // match on the key case-insensitively
    key = key.toLowerCase();

    // first, search through the differential file to
    // see if the key appears here
    DatabaseEntry diffResult = searchForRecord(key, diffFile);

    if (diffResult != null) {
      // the record was found in the diff file, so return it
      stopWatch.pause();
      return diffResult;
    } else {
      // the record was not found in the diff file

      // since the key was not found in the diff file, now
      // search the full database file for the key
      DatabaseEntry dbResult = searchForRecord(key, databaseFile);

      // return the result of this search, whether it was found or not.
      stopWatch.pause();
      return dbResult;
    }
  }
}
