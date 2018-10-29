package io.nathanfriend.coms535.pa1;

import java.util.Random;

/**
 * Holds the random numbers used in the hashing algorithm in BloomFilterRan
 *
 * @author Nathan Friend
 */
class RanHashParams {
  public final long a;
  public final long b;

  /**
   * Initializes a new RanHashParam instance
   *
   * @param a The first random integer in {1 .. prime-1}
   * @param b The second random integer in {1 .. prime-1}
   */
  public RanHashParams(long a, long b) {
    this.a = a;
    this.b = b;
  }
}

public class BloomFilterRan extends BloomFilter {

  private final long prime;
  private final RanHashParams[] hashParams;

  /**
   * Initializes a new BloomFilterRan instance
   *
   * @param setSize The size of the set
   * @param bitsPerElement The number of bits per element to allocate
   */
  public BloomFilterRan(int setSize, int bitsPerElement) {
    super(setSize, bitsPerElement);

    prime = findNextPrime(filterSize());

    hashParams = new RanHashParams[numHashes()];
    Random rand = new Random();
    for (int i = 0; i < numHashes(); i++) {
      hashParams[i] =
          new RanHashParams(Math.abs(rand.nextLong() % prime), Math.abs(rand.nextLong() % prime));
    }
  }

  @Override
  protected int[] getBloomFilterIndexes(String s) {
    int[] indexes = new int[numHashes()];
    for (int i = 0; i < numHashes(); i++) {
      long h = hash(s, hashParams[i]);
      indexes[i] = (int) (Math.floorMod(h, filterSize()));
    }

    return indexes;
  }

  private long hash(String s, RanHashParams params) {
    return (params.a * s.hashCode() + params.b) % prime;
  }

  /**
   * Returns the smallest prime that is greater than num
   *
   * @param num The lower bound for the algorithm
   * @return The smallest prime that is greater than num
   */
  private long findNextPrime(long num) {

    // while (true) is safe because there's
    // always a prime number greater than "start"
    while (true) {
      if (isPrime(++num)) {
        return num;
      }
    }
  }

  /**
   * Determines if a number is prime. Based on https://stackoverflow.com/a/1801446/1063392
   *
   * @param num The number to test
   * @return true if the number is prime, else false
   */
  private boolean isPrime(long num) {
    if (num == 2 || num == 3) return true;
    if (num % 2 == 0 || num % 3 == 0) return false;

    int i = 5, w = 2;

    while (i * i <= num) {
      if (num % i == 0) return false;

      i += w;
      w = 6 - w;
    }

    return true;
  }
}
