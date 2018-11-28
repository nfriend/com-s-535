import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

public class VSMScoreTest {
  @Test
  void testGetVector() {
    Map<String, Map<String, List<Integer>>> index = new HashMap<>();

    index.put("the", new HashMap<>());
    index.get("the").put("doc1", Arrays.asList(4, 8, 23));
    index.get("the").put("doc2", Arrays.asList(5, 12, 78, 91));

    index.put("and", new HashMap<>());
    index.get("and").put("doc1", Arrays.asList(191, 231, 912));
    index.get("and").put("doc2", Arrays.asList(91, 3));

    index.put("canada", new HashMap<>());
    index.get("canada").put("doc2", Arrays.asList(4));

    index.put("england", new HashMap<>());
    index.get("england").put("doc3", Arrays.asList(18, 19));

    List<String> allTerms = new ArrayList<>(index.keySet());
    allTerms.sort(String::compareTo);
    int docCount = 3;

    HashMap<String, List<Integer>> doc1Terms = new HashMap<>();
    HashMap<String, List<Integer>> doc2Terms = new HashMap<>();
    HashMap<String, List<Integer>> doc3Terms = new HashMap<>();
    index
        .keySet()
        .forEach(
            term -> {
              if (index.get(term).containsKey("doc1")) {
                doc1Terms.put(term, index.get(term).get("doc1"));
              }
              if (index.get(term).containsKey("doc2")) {
                doc2Terms.put(term, index.get(term).get("doc2"));
              }
              if (index.get(term).containsKey("doc3")) {
                doc3Terms.put(term, index.get(term).get("doc3"));
              }
            });

    float[] actual, expected;

    actual = VSMScore.getVector(index, allTerms, doc1Terms, docCount);
    expected = new float[] {0.304999f, 0, 0, 0.304999f};
    assertArrayEquals(expected, actual, 0.00001f);

    actual = VSMScore.getVector(index, allTerms, doc2Terms, docCount);
    expected = new float[] {0.249030f, 0.477121f, 0, 0.352182f};
    assertArrayEquals(expected, actual, 0.00001f);

    actual = VSMScore.getVector(index, allTerms, doc3Terms, docCount);
    expected = new float[] {0, 0, 0.674751f, 0};
    assertArrayEquals(expected, actual, 0.00001f);
  }

  @Test
  void testCosSim() {
    float[] vector1 = new float[] {1, 2, 3};
    float[] vector2 = new float[] {4, 5, 6};

    float expected = 0.974631f;
    float actual = VSMScore.cosSim(vector1, vector2);

    assertEquals(expected, actual, 0.00001);
  }
}
