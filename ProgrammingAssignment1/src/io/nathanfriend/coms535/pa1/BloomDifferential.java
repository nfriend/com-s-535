package io.nathanfriend.coms535.pa1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class BloomDifferential extends Differential {

  /**
   * Initializes a new BloomDifferential
   *
   * @param databaseFile The full database file
   * @param diffFile The file containing the differences
   */
  public BloomDifferential(File databaseFile, File diffFile) {
    super(databaseFile, diffFile, "BloomDifferential");
  }

  private IBloomFilter filter;

  /**
   * Instantiates and populates a Bloom filter using the values found in DiffFile.txt
   *
   * @return A ready-to-use Bloom filter
   */
  public IBloomFilter createFilter() {

    // cache the filter we create so that we're not recreating
    // it every time we call retrieveRecord()
    if (filter == null) {

      fileAccessCount++;

      filter = new BloomFilterMurmur(12000000, 16);

      try {
        Stream<String> lines = Files.lines(Paths.get(diffFile.getAbsolutePath()));
        lines.forEachOrdered(
            l -> {
              lineReadCount++;

              DatabaseEntry dbe = null;
              try {
                dbe = DatabaseEntry.parse(l);
              } catch (Exception e) {
                e.printStackTrace();
              }

              filter.add(dbe.phrase);
            });
        lines.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return filter;
  }

  @Override
  public DatabaseEntry retrieveRecord(String key) {

    stopWatch.start();

    if (key == null) {
      throw new UnsupportedOperationException("The key to retrieve must not be null.");
    }

    // match on the key case-insensitively
    key = key.toLowerCase();

    // first, check the Bloom filter to see if the key
    // (probably) exists in the filter
    IBloomFilter filter = createFilter();
    if (filter.appears(key)) {
      // there's a very good chance that the record appears in the diff file

      // first, search through the differential file to
      DatabaseEntry diffResult = searchForRecord(key, diffFile);

      if (diffResult != null) {
        // the record was found in the diff file, so return it
        stopWatch.pause();
        return diffResult;
      } else {
        // the record was not found in the diff file. This must
        // have been a false positive.

        // since the key was not found in the diff file, now
        // search the full database file for the key
        DatabaseEntry dbResult = searchForRecord(key, databaseFile);

        // return the result of this search, whether it was found or not.
        stopWatch.pause();
        return dbResult;
      }

    } else {
      // the record is definitely not in the diff file.

      // in this case, we can safely go straight to the database file
      DatabaseEntry dbResult = searchForRecord(key, databaseFile);

      stopWatch.pause();
      return dbResult;
    }
  }
}
