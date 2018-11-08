import java.text.DecimalFormat;

/** Utility class for dealing with vectors */
public class Vector {

  /**
   * Returns the norm of a vector
   *
   * @param vector The vector
   * @return The norm of the vector
   */
  public static double norm(double[] vector) {
    double sum = 0;
    for (double d : vector) {
      sum += Math.abs(d);
    }
    return sum;
  }

  /**
   * Subtracts two vectors
   *
   * @param vector1 The first vector
   * @param vector2 The second vector
   * @return The difference between the vectors
   */
  public static double[] subtract(double[] vector1, double[] vector2) {
    if (vector1.length != vector2.length) {
      throw new IllegalArgumentException("Vectors must be the same length");
    }

    double[] result = new double[vector1.length];
    for (int i = 0; i < vector1.length; i++) {
      result[i] = vector1[i] - vector2[i];
    }

    return result;
  }

  /**
   * Converts a vector to a String
   *
   * @param vector The vector
   * @return The string representation of the vector
   */
  public static String toString(double[] vector) {
    DecimalFormat df = new DecimalFormat("0.000");
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < vector.length; i++) {
      if (Double.isNaN(vector[i])) {
        sb.append("NaN");
      } else if (Double.isInfinite(vector[i])) {
        sb.append("Infinity");
      } else {
        sb.append(df.format(vector[i]));
      }
      if (i != vector.length - 1) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }
}
