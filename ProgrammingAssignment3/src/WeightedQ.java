import java.util.*;
import java.util.stream.Collectors;

/** A weighted queue */
public class WeightedQ<T> {

  /** The internal list of items */
  private ArrayList<WeightedTuple<T>> queue = new ArrayList<WeightedTuple<T>>();

  private Comparator<WeightedTuple<T>> comparator =
      new Comparator<WeightedTuple<T>>() {
        @Override
        public int compare(WeightedTuple<T> wt1, WeightedTuple<T> wt2) {
          return 1;
        }
      };

  /**
   * Adds an item to this queue.
   *
   * @param wt The WeightedTuple to add
   */
  public void add(WeightedTuple<T> wt) {

    // get all items where its T matches the one provided
    List<WeightedTuple<T>> existingItems =
        queue.stream().filter(i -> i.item.equals(wt.item)).collect(Collectors.toList());

    if (existingItems.size() == 0) {
      // no tuple was previously added that contains this T
      addAndSort(wt);
    } else if (existingItems.size() == 1) {
      // a tuple was previously added that contains this T
      WeightedTuple<T> existingItem = existingItems.get(0);

      // if the provided item has a greater weight than the previously-added item, replace the old
      // item with the new
      if (wt.weight > existingItem.weight) {
        queue.remove(existingItem);
        addAndSort(wt);
      }
    } else {
      // we should never be in a situation where we have multiple tuples that have the same T
      throw new IllegalStateException("Multiple items were found for the item: " + wt.item);
    }
  }

  /**
   * Provides an alternate syntax for adding items to the queue.
   *
   * @param item The item to add to the queue
   * @param weight The weight of the item
   */
  public void add(T item, int weight) {
    add(new WeightedTuple<T>(item, weight));
  }

  /**
   * Adds an item to the queue and resorts the queue. This method assumes the WeightedTuple item has
   * already been checked to make sure it's valid to add it to the queue.
   *
   * @param wt The WeightedTuple<T> to add to the queue
   */
  private void addAndSort(WeightedTuple<T> wt) {
    queue.add(wt);
    Collections.sort(queue);
  }

  /**
   * Extracts an item from the queue based on the weight and order of addition
   *
   * @return The item with the highest weight, or if multiple elements tie for the highest weight,
   *     the item with the highest weight and the earliest addition to this queue
   */
  public WeightedTuple<T> extract() {
    return queue.remove(0);
  }

  /**
   * Returns the number of items in this WeightedQ
   *
   * @return The size of the WeightedQ
   */
  public int size() {
    return queue.size();
  }
}
