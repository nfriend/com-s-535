import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TermExtractor {

  /** The list of filters to apply to the file's text */
  public List<IFilter> filters;

  /** Creates a new TermExtractor instance */
  public TermExtractor() {
    // set up a default set of filters.  These
    // can be changed by the user of this class by
    // modifying the "filters" public property
    filters = new ArrayList<IFilter>();
    filters.add(new PatternExtractorFilter("(\\S+)"));
    filters.add(new LowercaseFilter());
    filters.add(new RemovePatternFilter("\\.|,|:|;|'"));
    filters.add(new WordLengthFilter(Arrays.asList(1, 2)));
    filters.add(new StopFilter(Arrays.asList("the")));
  }

  /**
   * Extracts terms from a file
   *
   * @param file The file to load
   * @return A list of terms
   */
  public List<String> extractFromFile(File file) {

    ArrayList<String> terms = new ArrayList<String>();
    try {
      Stream<String> lines =
          Files.lines(Paths.get(file.getAbsolutePath()), StandardCharsets.ISO_8859_1);
      lines.forEachOrdered(
          l -> {
            terms.addAll(processLine(l));
          });
      lines.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return terms;
  }

  /**
   * Runs a line from a file through all of the filters
   *
   * @param line The line to process
   * @return A list of terms from the line
   */
  private List<String> processLine(String line) {
    List<String> terms = Arrays.asList(line);

    for (IFilter filter : filters) {
      terms = filter.process(terms);
    }

    return terms;
  }
}
