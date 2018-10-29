import java.util.List;
import java.util.stream.Collectors;

/** An IFilter implementation that removes terms based on term length */
public class WordLengthFilter implements IFilter {

  /** The list of all term lengths to remove */
  private List<Integer> lengthsToRemove;

  public WordLengthFilter(List<Integer> lengthsToRemove) {
    this.lengthsToRemove = lengthsToRemove;
  }

  @Override
  public List<String> process(List<String> terms) {
    return terms
        .stream()
        .filter(t -> !lengthsToRemove.stream().anyMatch(l -> l.intValue() == t.length()))
        .collect(Collectors.toList());
  }
}
