package io.nathanfriend.coms535.pa1;

import ie.ucd.murmur.MurmurHash;

public class BloomFilterMurmur extends BloomFilter {

  /**
   * Initializes a new BloomFilterMurmur instsance
   *
   * @param setSize The size of the set
   * @param bitsPerElement The number of bits per element to allocate
   */
  public BloomFilterMurmur(int setSize, int bitsPerElement) {
    super(setSize, bitsPerElement);
  }

  @Override
  protected int[] getBloomFilterIndexes(String s) {

    int seed = 0xe17a1465;
    final byte[] bytes = s.getBytes();
    int[] indexes = new int[numHashes()];

    for (int i = 0; i < numHashes(); i++) {
      long hash = MurmurHash.hash64(bytes, bytes.length, seed);
      indexes[i] = (int) (Math.floorMod(hash, filterSize()));
      seed = (int) hash;
    }

    return indexes;
  }
}
