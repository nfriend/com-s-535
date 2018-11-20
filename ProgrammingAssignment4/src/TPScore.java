import java.util.*;

/** A utility class to help compute TP score */
public class TPScore {

  /**
   * Computes the TPScore as described in the assignment description
   *
   * @param positions A list of lists of positions. Each list represents the positions of a term in
   *     the query. If a particular term did not have any matches, an empty List should be passed.
   * @return The TPScore
   */
  public static double compute(List<List<Integer>> positions) {
    if (positions.size() == 0) {
      throw new IllegalArgumentException("Cannot compute TPScore on query with no terms");
    }
    if (positions.size() == 1) {
      return 0;
    }

    int distancesSum = 0;
    for (int i = 0; i < positions.size() - 1; i++) {
      distancesSum += distanceBetween(positions.get(i), positions.get(i + 1));
    }

    return ((double) positions.size()) / distancesSum;
  }

  /**
   * Computes the distance between two terms as defined in the assignment description
   *
   * @param list1 The positions of the first term
   * @param list2 The positions of the second term
   * @return The distance between the terms
   */
  public static int distanceBetween(List<Integer> list1, List<Integer> list2) {

    int distance = Integer.MAX_VALUE;
    for (int i2 : list2) {
      for (int i1 : list1) {
        if (i2 > i1 && i2 - i1 < distance) {
          distance = i2 - i1;
        }
      }
    }

    return Math.min(distance, 17);
  }
}
