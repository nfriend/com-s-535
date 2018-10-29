/** An IFilter implementation that removes specific terms */
import java.util.List;
import java.util.stream.Collectors;

public class StopFilter implements IFilter {

  /** The list of terms to remove */
  private List<String> wordsToRemove;

  public StopFilter(List<String> wordsToRemove) {
    this.wordsToRemove = wordsToRemove;
  }

  @Override
  public List<String> process(List<String> terms) {
    return terms
        .stream()
        .filter(t -> !wordsToRemove.stream().anyMatch(t::equalsIgnoreCase))
        .collect(Collectors.toList());
  }
}
