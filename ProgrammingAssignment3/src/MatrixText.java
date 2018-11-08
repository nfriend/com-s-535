import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class MatrixText {
  @Test
  void testMatrixMultiplication() {
    double[][] matrix1 = {{1, 4}, {2, 5}, {3, 6}};
    double[][] matrix2 = {{7, 9, 11}, {8, 10, 12}};

    double[][] expected = {{58, 139}, {64, 154}};

    double[][] actual = Matrix.multiply(matrix1, matrix2);

    assertArrayEquals(expected, actual);
  }

  @Test
  void testVectorMultipliction() {
    double[][] matrix1 = {{1, 4}, {2, 5}, {3, 6}};
    double[] vector = {7, 8, 9};

    double[] expected = {50, 122};

    double[] actual = Matrix.multiply(matrix1, vector);

    System.out.println(actual[0]);

    assertArrayEquals(expected, actual);
  }
}
