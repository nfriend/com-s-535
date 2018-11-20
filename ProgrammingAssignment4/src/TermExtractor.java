import java.util.*;
import java.util.regex.*;

/** Extracts terms from a document */
public class TermExtractor {

  /**
   * Extracts terms from a list of lines
   *
   * @param lines The lines to extract terms from
   * @return A list of terms and their positions
   */
  public static HashMap<String, List<Integer>> extract(List<String> lines) {
    HashMap<String, List<Integer>> terms = new HashMap<String, List<Integer>>();

    int termPosition = 0;
    for (String line : lines) {
      String[] words = line.split("\\s+");

      for (String word : words) {
        String term = clean(word).toLowerCase();

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

  /** A pattern that matches all characters that should be removed */
  private static String charactersToRemove = "[.,”“?\\[\\]'{}:;()]";

  /** Matches a decimal number, i.e. 12.34 or 1,234.56 */
  private static Pattern decimalPattern = Pattern.compile("^[\\d,]*\\.[\\d,]*$");

  /**
   * Removes the characters specified in the programming assignment
   *
   * @param word The word to clean
   * @return The word without any of the specified characters
   */
  private static String clean(String word) {

    Matcher decimalMatcher = decimalPattern.matcher(word);
    if (decimalMatcher.find()) {
      return word;
    } else {
      return word.replaceAll(charactersToRemove, "");
    }
  }
}
