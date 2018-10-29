import java.util.ArrayList;
import java.util.List;

public class MinHashAccuracyRunner {

  /**
   * Runs the MinHashAccuracy program
   *
   * @param args The command-line arguments (no arguments are used for this program)
   */
  public static void main(String[] args) {
    String folder = System.getProperty("user.dir") + "\\src\\space";
    MinHashAccuracy mha = new MinHashAccuracy();

    List<Integer> permCounts = new ArrayList<Integer>();
    permCounts.add(400);
    permCounts.add(600);
    permCounts.add(800);

    List<Double> epsilons = new ArrayList<Double>();
    epsilons.add(.04);
    epsilons.add(.07);
    epsilons.add(.09);

    // compute the MinHash accuracy for each combination of
    // permutation count and epsilon
    for (double epsilon : epsilons) {
      for (int numPermutations : permCounts) {

        int differCount = mha.accuracy(folder, numPermutations, epsilon);

        System.out.println(
            String.format(
                "Number of bad approximations with numPermutations=%d and epsilon=%f: %d",
                numPermutations, epsilon, differCount));
      }
    }
  }
}
