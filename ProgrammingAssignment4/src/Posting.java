import java.util.*;
import java.util.stream.Collectors;

/** Represents one posting (a document name and all positions of a specific term) */
public class Posting {

  /**
   * Constructs a new Posting
   *
   * @param documentName The name of the document
   * @param positions The positions of the terms
   */
  public Posting(String documentName, List<Integer> positions) {
    this.documentName = documentName;
    this.positions = positions;
  }

  /** The name of the document */
  public String documentName;

  /** The positions of the term */
  public List<Integer> positions;

  @Override
  public String toString() {
    return "<"
        + documentName
        + ":"
        + String.join(",", positions.stream().map(i -> i.toString()).collect(Collectors.toList()))
        + ">";
  }
}
