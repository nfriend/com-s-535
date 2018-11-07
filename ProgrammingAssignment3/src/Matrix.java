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
}
