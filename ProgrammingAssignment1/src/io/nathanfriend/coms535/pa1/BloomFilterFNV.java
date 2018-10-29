package io.nathanfriend.coms535.pa1;

public class BloomFilterFNV extends BloomFilter {

  private final long BASIS = Long.parseUnsignedLong("cbf29ce484222325", 16);
  private final long PRIME = Long.parseUnsignedLong("100000001b3", 16);
  private final String[] salts;

  /**
   * Initializes a new BloomFilterFNV instance
   *
   * @param setSize The size of the set
   * @param bitsPerElement The number of bits per element to allocate
   */
  public BloomFilterFNV(int setSize, int bitsPerElement) {
    super(setSize, bitsPerElement);

    // generate a salt for each hash function
    salts = new String[numHashes()];
    String lastSalt = "This is the starting value.";
    for (int i = 0; i < numHashes(); i++) {
      salts[i] = Long.toString(hash(lastSalt));
      lastSalt = salts[i];
    }
  }

  @Override
  protected int[] getBloomFilterIndexes(String s) {
    int[] indexes = new int[numHashes()];
    for (int i = 0; i < numHashes(); i++) {
      long h = hash(s + salts[i]);
      indexes[i] = (int) (Math.floorMod(h, filterSize()));
    }

    return indexes;
  }

  /**
   * Generates a hash using the FNV hashing strategy
   *
   * @param s The string to hash
   * @return A FNV hash value
   */
  private long hash(String s) {
    long hash = BASIS;
    byte[] bytes = s.getBytes();
    for (byte b : bytes) {
      hash *= PRIME;
      hash ^= b;
    }

    return hash;
  }
}
