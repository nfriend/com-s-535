import java.util.*;

/**
 * A class that stores all the parameters needed to compute the permutations for a MinHash matrix
 */
public class PermutationParams {

  /** The prime used for all of the hash functions */
  public int prime;

  /** A list of constants (a and b) used for the hash functions */
  public List<AandB> constants = new ArrayList<AandB>();

  /**
   * Constructs a new PermutationParams instance
   *
   * @param prime The prime number used for all hash functions
   */
  public PermutationParams(int prime) {
    this.prime = prime;
  }

  /**
   * Generates random constants (a and b) for each hash function
   *
   * @param numPermutations The number of constants (a and b) to generate
   */
  public void generate(int numPermutations) {
    Random rand = new Random();

    while (constants.size() < numPermutations) {
      int a = rand.nextInt(prime);
      int b = rand.nextInt(prime);
      AandB ab = new AandB(a, b);

      // ensure that a and b are unique
      if (constants.stream().allMatch(c -> !ab.equals(c))) {
        constants.add(ab);
      }
    }
  }
}
