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
   * Calculates the exact Jaccard similarity between two String links
   *
   * @param link1 The first link
   * @param link2 The second link
   * @return The Jaccard similarity
   */
  public static double betweenLinks(String link1, String link2) {
    Set<String> set1 = new HashSet<String>(Arrays.asList(link1.toLowerCase().split("/")));
    Set<String> set2 = new HashSet<String>(Arrays.asList(link2.toLowerCase().split("/")));

    return Jaccard.calculate(set1, set2);
  }

  /**
   * Returns a String that contains the exact Jaccard similarity between all pairs of links in the
   * list
   *
   * @param links The list of links to compare
   * @param indent The indentation string to prefix to each line
   * @return A string detailing the similarities
   */
  public static String toString(List<String> links, String indent) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < links.size(); i++) {
      for (int j = i; j < links.size(); j++) {
        double similarity = Jaccard.betweenLinks(links.get(i), links.get(j));
        sb.append(String.format("%s%s %s: %.4f\n", indent, links.get(i), links.get(j), similarity));
      }
    }

    return sb.toString();
  }
}
