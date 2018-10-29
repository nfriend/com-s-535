import java.util.List;
import java.util.stream.Collectors;

/** An IFilter implementation that transforms all terms to lower case */
public class LowercaseFilter implements IFilter {

  @Override
  public List<String> process(List<String> terms) {
    return terms.stream().map(s -> s.toLowerCase()).collect(Collectors.toList());
  }
}
