import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;
import org.junit.jupiter.api.Test;

public class TPScoreTest {
  @Test
  void testDistance() {
    List<Integer> list1 = Arrays.asList(6, 18, 21, 46);
    List<Integer> list2 = Arrays.asList(5, 9, 11, 20, 34);
    int actual = TPScore.distanceBetween(list1, list2);
    int expected = 2;
    assertEquals(expected, actual);
  }

  @Test
  void testOneMissingTermDistance() {
    List<Integer> list1 = Arrays.asList();
    List<Integer> list2 = Arrays.asList(5, 9, 11, 20, 34);
    int actual = TPScore.distanceBetween(list1, list2);
    int expected = 17;
    assertEquals(expected, actual);
  }

  @Test
  void testBothMissingTermsDistance() {
    List<Integer> list1 = Arrays.asList();
    List<Integer> list2 = Arrays.asList();
    int actual = TPScore.distanceBetween(list1, list2);
    int expected = 17;
    assertEquals(expected, actual);
  }

  @Test
  void testTermOrderDistance() {
    List<Integer> list1 = Arrays.asList(7, 8, 9);
    List<Integer> list2 = Arrays.asList(1, 2, 3);
    int actual = TPScore.distanceBetween(list1, list2);
    int expected = 17;
    assertEquals(expected, actual);
  }

  @Test
  void testTPScore() {
    List<List<Integer>> lists =
        Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(5, 6, 7),
            Arrays.asList(10, 11, 12),
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5, 6));

    double actual = TPScore.compute(lists);
    double expected = ((double) lists.size()) / (2 + 3 + 17 + 1);

    assertEquals(expected, actual, 0.000001);
  }

  @Test
  void testTPScoreWith1Term() {
    List<List<Integer>> lists = Arrays.asList(Arrays.asList(1, 2, 3));

    double actual = TPScore.compute(lists);
    double expected = 0;

    assertEquals(expected, actual, 0.000001);
  }
}
