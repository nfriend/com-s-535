public class Utility {
  /**
   * Returns the smallest prime that is greater than num. Borrowed from PA1.
   *
   * @param num The lower bound for the algorithm
   * @return The smallest prime that is greater than num
   */
  public static int findNextPrime(int num) {

    // while (true) is safe because there's
    // always a prime number greater than "start"
    while (true) {
      if (isPrime(++num)) {
        return num;
      }
    }
  }

  /**
   * Determines if a number is prime. Based on https://stackoverflow.com/a/1801446/1063392 Borrowed
   * from PA1.
   *
   * @param num The number to test
   * @return true if the number is prime, else false
   */
  public static boolean isPrime(long num) {
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
