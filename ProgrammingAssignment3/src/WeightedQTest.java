import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class WeightedQTest {

  @Test
  void testWeightedQ() {
    WeightedQ<Integer> wq = new WeightedQ<Integer>();
    wq.add(1, 5);
    wq.add(2, 3);
    wq.add(5, 7);
    wq.add(21, 5);
    wq.add(36, 4);

    assertEquals(new WeightedTuple<Integer>(5, 7), wq.extract());
    assertEquals(new WeightedTuple<Integer>(1, 5), wq.extract());

    assertEquals(3, wq.size());
    wq.add(21, 9);
    assertEquals(3, wq.size());

    assertEquals(new WeightedTuple<Integer>(21, 9), wq.extract());
    assertEquals(new WeightedTuple<Integer>(36, 4), wq.extract());
    assertEquals(new WeightedTuple<Integer>(2, 3), wq.extract());
  }

  @Test
  void testSameWeights() {
    WeightedQ<Integer> wq = new WeightedQ<Integer>();
    wq.add(1, 5);
    wq.add(2, 5);
    wq.add(21, 5);
    wq.add(16, 5);
    wq.add(5, 5);

    assertEquals(new WeightedTuple<Integer>(1, 5), wq.extract());
    assertEquals(new WeightedTuple<Integer>(2, 5), wq.extract());
    assertEquals(new WeightedTuple<Integer>(21, 5), wq.extract());
    assertEquals(new WeightedTuple<Integer>(16, 5), wq.extract());
    assertEquals(new WeightedTuple<Integer>(5, 5), wq.extract());
  }
}
