/** A class that records a link and the token range it includes */
public class LinkAndRange {

  /** The link's href */
  public String link;

  /** The position of the first token included in this link's text */
  public int beginningPosition = -1;

  /** The position of the last token included in this link's text */
  public int endPosition = -1;

  /**
   * Creates a new LinkAndRange instance
   *
   * @param link The link's href
   * @param beginningPosition The position of the first token included in this link's text
   * @param endPosition The position of the last token included in this link's text
   */
  public LinkAndRange(String link, int beginningPosition, int endPosition) {
    this.link = link;
    this.beginningPosition = beginningPosition;
    this.endPosition = endPosition;
  }

  /**
   * Creates a new LinkAndRange instance without any positions
   *
   * @param link The link's href
   */
  public LinkAndRange(String link) {
    this.link = link;
  }

  /**
   * Records a token position that occurs inside of this <a> tag
   *
   * @param position The position to add
   */
  public void addPosition(int position) {
    if (beginningPosition == -1 || position < beginningPosition) {
      beginningPosition = position;
    }
    if (endPosition == -1 || position > endPosition) {
      endPosition = position;
    }
  }

  @Override
  public String toString() {
    return "LinkAndRange [link="
        + link
        + ", beginningPosition="
        + beginningPosition
        + ", endPosition="
        + endPosition
        + "]";
  }
}
