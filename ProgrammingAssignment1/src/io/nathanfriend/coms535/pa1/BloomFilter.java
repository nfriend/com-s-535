package io.nathanfriend.coms535.pa1;

import java.util.BitSet;

public abstract class BloomFilter implements IBloomFilter {
  private BitSet filter;
  private final int filterSize;
  private final int numHashes;
  private int dataSize = 0;

  /**
   * Initializes a new BloomFilter
   *
   * @param setSize The expected size of the set
   * @param bitsPerElement The number of bits per element to allocate
   */
  public BloomFilter(int setSize, int bitsPerElement) {
    if (setSize <= 0) {
      throw new IllegalArgumentException("setSize must be greater than 0");
    }
    if (bitsPerElement <= 0) {
      throw new IllegalArgumentException("bitsPerElement must be greater than 0");
    }

    this.filterSize = setSize * bitsPerElement;
    this.numHashes = (int) Math.ceil(Math.log(2) * filterSize / setSize);
    filter = new BitSet(this.filterSize);
  }

  public void add(String s) {
    if (s == null)
      throw new IllegalArgumentException("The string being added to the filter must not be null.");

    int[] indexesToFlip = getBloomFilterIndexes(s.toLowerCase());
    for (int i : indexesToFlip) {
      filter.set(i);
    }

    this.dataSize++;
  }

  public boolean appears(String s) {
    if (s == null)
      throw new IllegalArgumentException(
          "The string being tested against the filter must not be null.");

    int[] indexesToCheck = getBloomFilterIndexes(s.toLowerCase());
    for (int i : indexesToCheck) {
      if (!filter.get(i)) {
        return false;
      }
    }

    return true;
  }

  public int filterSize() {
    return this.filterSize;
  }

  public int dataSize() {
    return this.dataSize;
  }

  public int numHashes() {
    return this.numHashes;
  }

  protected abstract int[] getBloomFilterIndexes(String s);

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < filterSize(); i++) {
      sb.append(filter.get(i) ? "X" : "O");
    }
    return sb.toString();
  }
}
