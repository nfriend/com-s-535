import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** An IFilter implementation that strips characters from the terms based on a regular expression */
public class RemovePatternFilter implements IFilter {

  /** The regular expression that is used to remove characters from the terms */
  private String regex;

  public RemovePatternFilter(String regex) {
    this.regex = regex;
  }

  @Override
  public List<String> process(List<String> terms) {
    return terms.stream().map(s -> s.replaceAll(regex, "")).collect(Collectors.toList());
  }
}
