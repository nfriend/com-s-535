package io.nathanfriend.coms535.pa1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DatabaseEntry {
  public String phrase;
  public ArrayList<Appearance> appearances;

  /** A regular express that matches four words separated by white space */
  private static Pattern pattern = Pattern.compile("(\\S+\\s+\\S+\\s+\\S+\\s+\\S+)\\s+(.*)");

  /**
   * Parses a line of text into a DatabaseEntry object
   *
   * @param line The line to parse
   * @return A DatabaseEntry object that represents the provided line
   * @throws Exception if something goes wrong while parsing the line of text
   */
  public static DatabaseEntry parse(String line) throws Exception {
    String phrase;
    String appearances;
    try {
      Matcher matcher = pattern.matcher(line);
      matcher.find();
      phrase = matcher.group(1);
      appearances = matcher.group(2);
    } catch (Exception e) {
      throw new Exception("Could not parse the following line: \"" + line + "\"", e);
    }

    DatabaseEntry entry = new DatabaseEntry();
    entry.phrase = phrase;
    entry.appearances = Appearance.parse(appearances);

    return entry;
  }

  @Override
  public String toString() {
    List<String> appearancesAsStrings =
        appearances.stream().map(a -> a.toString()).collect(Collectors.toList());
    return String.format("%s %s", phrase, String.join(" ", appearancesAsStrings));
  }
}
