/** A class to hold the two constants - a and b */
public class AandB {

  /** The "a" constant */
  public int a;

  /** The "b" constant */
  public int b;

  /**
   * Constructs a new AandB instance
   *
   * @param a The "a" constant
   * @param b The "b" constant
   */
  public AandB(int a, int b) {
    this.a = a;
    this.b = b;
  }

  /** Compares on AandB object to another */
  @Override
  public boolean equals(Object other) {
    if (other == null) return false;
    if (other == this) return true;
    if (!(other instanceof AandB)) return false;
    AandB otherAandB = (AandB) other;

    return this.a == otherAandB.a && this.b == otherAandB.b;
  }
}
