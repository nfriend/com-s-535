/**
 * Holds two items
 *
 * @param <T1> The type of the first item
 * @param <T2> The type of the second item
 */
public class Tuple<T1, T2> {

  /** The first item */
  public T1 item1;

  /** The second item */
  public T2 item2;

  /**
   * Instantiates a new Tuple
   *
   * @param item1 The first item
   * @param item2 The second item
   */
  public Tuple(T1 item1, T2 item2) {
    this.item1 = item1;
    this.item2 = item2;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((item1 == null) ? 0 : item1.hashCode());
    result = prime * result + ((item2 == null) ? 0 : item2.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    @SuppressWarnings("unchecked")
    Tuple<T1, T2> other = (Tuple<T1, T2>) obj;
    if (item1 == null) {
      if (other.item1 != null) return false;
    } else if (!item1.equals(other.item1)) return false;
    if (item2 == null) {
      if (other.item2 != null) return false;
    } else if (!item2.equals(other.item2)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "Tuple [item1=" + item1 + ", item2=" + item2 + "]";
  }
}
