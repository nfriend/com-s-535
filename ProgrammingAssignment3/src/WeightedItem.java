public class WeightedItem<T> implements Comparable<WeightedItem<T>> {

  /** The data item */
  public T item;

  /** The weight of the data item */
  public int weight;

  /**
   * Instantiates a new WeightedItem
   *
   * @param item The data item
   * @param weight The weight of the data item
   */
  public WeightedItem(T item, int weight) {
    this.item = item;
    this.weight = weight;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((item == null) ? 0 : item.hashCode());
    result = prime * result + weight;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    @SuppressWarnings("unchecked")
    WeightedItem<T> other = (WeightedItem<T>) obj;
    if (item == null) {
      if (other.item != null) return false;
    } else if (!item.equals(other.item)) return false;
    if (weight != other.weight) return false;
    return true;
  }

  @Override
  public String toString() {
    return "WeightedItem [item=" + item + ", weight=" + weight + "]";
  }

  @Override
  public int compareTo(WeightedItem<T> other) {
    return other.weight - this.weight;
  }
}
