import java.util.List;

/** Describes the interface of a term processor */
public interface IFilter {

  /**
   * Processes a set of terms and returns the terms with modifications applied
   *
   * @param terms The terms to process
   * @return A list of modified terms
   */
  List<String> process(List<String> terms);
}
