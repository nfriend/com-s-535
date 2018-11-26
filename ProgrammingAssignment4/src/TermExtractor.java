import java.util.*;

/** Extracts terms from a document */
public class TermExtractor {

  /**
   * Extracts terms from a list of lines
   *
   * @param lines The lines to extract terms from
   * @return A list of terms and their positions
   */
  public static LinkedHashMap<String, List<Integer>> extract(List<String> lines) {
    LinkedHashMap<String, List<Integer>> terms = new LinkedHashMap<>();

    int termPosition = 0;
    for (String line : lines) {
      String[] words = line.split(splitRegex);

      for (String word : words) {
        String term = word.toLowerCase();

        if (term.trim().length() == 0) {
          // the term was all whitespace,
          // so skip this term
          continue;
        }

        if (!terms.containsKey(term)) {
          terms.put(term, new ArrayList<Integer>());
        }

        terms.get(term).add(termPosition);

        termPosition++;
      }
    }

    return terms;
  }

  /**
   * Convenience method to call <tt>extract()</tt> with a single string instead of a List
   *
   * @param line The line to extract terms from
   * @return The list of terms and their positions
   */
  public static LinkedHashMap<String, List<Integer>> extract(String line) {
    return extract(Arrays.asList(line));
  }

  /** A pattern that matches all characters that should be removed */
  private static String splitRegex = "[\\s,”“?\\[\\]'{}:;()]+|((?<![0-9])\\.(?![0-9]))";
}
