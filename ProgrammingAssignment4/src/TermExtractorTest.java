import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import org.junit.jupiter.api.Test;

public class TermExtractorTest {
  @Test
  void testTermExtraction() {
    HashMap<String, List<String>> inputOutputs = new HashMap<>();

    inputOutputs.put("The quick brown fox", Arrays.asList("the", "quick", "brown", "fox"));
    inputOutputs.put("12.23 4567.89", Arrays.asList("12.23", "4567.89"));
    inputOutputs.put("the.and", Arrays.asList("the", "and"));
    inputOutputs.put("the,and", Arrays.asList("the", "and"));
    inputOutputs.put("the”and", Arrays.asList("the", "and"));
    inputOutputs.put("the“and", Arrays.asList("the", "and"));
    inputOutputs.put("the?and", Arrays.asList("the", "and"));
    inputOutputs.put("the[and", Arrays.asList("the", "and"));
    inputOutputs.put("the]and", Arrays.asList("the", "and"));
    inputOutputs.put("the'and", Arrays.asList("the", "and"));
    inputOutputs.put("the{and", Arrays.asList("the", "and"));
    inputOutputs.put("the}and", Arrays.asList("the", "and"));
    inputOutputs.put("the:and", Arrays.asList("the", "and"));
    inputOutputs.put("the;and", Arrays.asList("the", "and"));
    inputOutputs.put("the)and", Arrays.asList("the", "and"));
    inputOutputs.put("the(and", Arrays.asList("the", "and"));
    inputOutputs.put("new\nline", Arrays.asList("new", "line"));
    inputOutputs.put("some\ttabs", Arrays.asList("some", "tabs"));
    inputOutputs.put("career.[12][13][c]", Arrays.asList("career", "12", "13", "c"));

    inputOutputs
        .keySet()
        .forEach(
            key -> {
              List<String> expected = inputOutputs.get(key);
              List<String> actual =
                  new ArrayList<>(TermExtractor.extract(Arrays.asList(key)).keySet());

              expected.sort(String::compareTo);
              actual.sort(String::compareTo);

              assertEquals(expected, actual);
            });
  }

  @Test
  void testPositions() {
    List<String> input = Arrays.asList("The Quick quick brown fox", "jumps quick");
    LinkedHashMap<String, List<Integer>> actual = TermExtractor.extract(input);

    LinkedHashMap<String, List<Integer>> expected = new LinkedHashMap<>();
    expected.put("the", Arrays.asList(0));
    expected.put("quick", Arrays.asList(1, 2, 6));
    expected.put("brown", Arrays.asList(3));
    expected.put("fox", Arrays.asList(4));
    expected.put("jumps", Arrays.asList(5));

    assertEquals(expected, actual);
  }
}
