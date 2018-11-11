import java.util.*;

/** Provides utilities for calculating Jaccard similarity */
public class Jaccard {

  /**
   * Calculates the exact Jaccard similarity between two sets
   *
   * @param set1 The first set
   * @param set2 The second set
   * @return The Jaccard similarity
   */
  public static <T> double calculate(Set<T> set1, Set<T> set2) {
    Set<T> intersection = new HashSet<T>(set1);
    intersection.retainAll(set2);

    Set<T> union = new HashSet<T>(set1);
    union.addAll(set2);

    return (double) intersection.size() / union.size();
  }

  /**
   * Calculate the exact Jaccard similarity between two arrays. The arrays are converted into
   * Set<T>s before the calculation is performed.
   *
   * @param array1 The first list
   * @param array2 The second list
   * @return The Jaccard similarity
   */
  public static <T> double calculate(T[] array1, T[] array2) {
    Set<T> set1 = new HashSet<T>(Arrays.asList(array1));
    Set<T> set2 = new HashSet<T>(Arrays.asList(array2));
    return Jaccard.calculate(set1, set2);
  }
}
