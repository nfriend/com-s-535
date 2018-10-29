import java.util.*;
import java.util.regex.*;

/** An IFilter implementation that extracts terms based on a regular expression */
public class PatternExtractorFilter implements IFilter {

  /** The pattern that will be used to extract terms from the provided strings */
  private Pattern pattern;

  public PatternExtractorFilter(String regex) {
    this.pattern = Pattern.compile(regex);
  }

  @Override
  public List<String> process(List<String> terms) {
    List<String> processed = new ArrayList<String>();

    for (String term : terms) {
      Matcher m = pattern.matcher(term);
      while (m.find()) {
        processed.add(m.group());
      }
    }

    return processed;
  }
}
