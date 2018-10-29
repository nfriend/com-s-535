package io.nathanfriend.coms535.pa1;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents one appearance of a book in a DatabaseEntry object
 *
 * @author Nathan Friend
 */
public class Appearance {
  public int year;
  public int times;
  public int books;

  /** Matches three words in a row, separated by white space */
  private static Pattern pattern = Pattern.compile("(\\S+\\s+\\S+\\s+\\S+\\s+)");

  /**
   * Initializes a new Appearance object
   *
   * @param year The year of this appearance
   * @param times The number of time the phrase occurred
   * @param books How many books the phrase appeared in
   */
  public Appearance(int year, int times, int books) {
    this.year = year;
    this.times = times;
    this.books = books;
  }

  /**
   * Parses a line of text into an Appearance instance
   *
   * @param line The line of text to parse
   * @return an Appearance instsance
   * @throws Exception if something goes wrong while parsing the line of text
   */
  public static ArrayList<Appearance> parse(String line) throws Exception {
    ArrayList<Appearance> apps = new ArrayList<Appearance>();
    Matcher matcher = pattern.matcher(line);

    try {
      while (matcher.find()) {
        String[] dataPoints = matcher.group(1).split("\\s+");
        apps.add(
            new Appearance(
                Integer.parseInt(dataPoints[0]),
                Integer.parseInt(dataPoints[1]),
                Integer.parseInt(dataPoints[2])));
      }
    } catch (Exception e) {
      throw new Exception("Could not parse the following appearance: \"" + line + "\"", e);
    }

    return apps;
  }

  /**
   * Converts this Appearance object back into the line of text
   *
   * @return A line of text representing the Appearance object
   */
  @Override
  public String toString() {
    return String.format("%d %d %d", year, times, books);
  }
}
