import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class VectorTest {
  @Test
  void testVectorNorm() {
    double[] vector = new double[] {1, 2, -5, 2};

    double expected = 10;
    double actual = Vector.norm(vector);

    assertEquals(expected, actual);
  }

  @Test
  void testVectorSubtract() {
    double[] vector1 = new double[] {3, 4, -5};
    double[] vector2 = new double[] {8, -1, -2};

    double[] expected = new double[] {-5, 5, -3};
    double[] actual = Vector.subtract(vector1, vector2);

    assertArrayEquals(expected, actual);
  }
}
