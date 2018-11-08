import java.text.DecimalFormat;

/** A utility class to help in dealing with matrices */
public class Matrix {

  /**
   * Returns a string representation of the matrix.
   *
   * @param matrix The matrix to print
   * @return A string representation of the matrix
   */
  public static String toString(double[][] matrix) {
    DecimalFormat df = new DecimalFormat("0.00000");

    int width = matrix.length;
    if (width == 0) {
      return "[]";
    }
    int height = matrix[0].length;

    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < height; i++) {
      sb.append("[");
      for (int j = 0; j < width; j++) {
        sb.append(df.format(matrix[j][i]));
        if (j != width - 1) {
          sb.append(", ");
        }
      }
      sb.append("]");
      if (i != height - 1) {
        sb.append("\n");
      }
    }

    return sb.toString();
  }

  /**
   * Multiplies two matrices together
   *
   * @param matrix1 The first matrix
   * @param matrix2 The second matrix
   * @return The result of the multiplication
   */
  public static double[][] multiply(double[][] matrix1, double[][] matrix2) {

    if (matrix1.length == 0 && matrix2.length == 0) {
      return new double[0][0];
    }
    if (matrix2.length == 0 || matrix1.length != matrix2[0].length) {
      throw new IllegalArgumentException(
          "Cannot multiply matrices: matrix1 must have the same number of columns as matrix2 has rows");
    }

    int columns = matrix2.length;
    int rows = matrix1[0].length;

    double[][] result = new double[columns][rows];

    for (int c = 0; c < columns; c++) {
      for (int r = 0; r < rows; r++) {
        for (int i = 0; i < matrix1.length; i++) {
          result[c][r] += matrix1[i][r] * matrix2[c][i];
        }
      }
    }

    return result;
  }

  /**
   * Multiplies a matrix with a vector
   *
   * @param matrix The matrix
   * @param vector The vector
   * @return The result of the multiplication
   */
  public static double[] multiply(double[][] matrix, double[] vector) {
    double[][] vectorAsMatrix = new double[1][vector.length];
    vectorAsMatrix[0] = vector;
    double[][] result = Matrix.multiply(matrix, vectorAsMatrix);
    return result[0];
  }
}
