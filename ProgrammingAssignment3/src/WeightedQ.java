import java.util.*;
import java.util.stream.Collectors;

/** A weighted queue */
public class WeightedQ<T> {

  /** The internal list of items */
  private ArrayList<WeightedItem<T>> queue = new ArrayList<WeightedItem<T>>();

  /**
   * Adds an item to this queue.
   *
   * @param wi The WeightedItem to add
   */
  public void add(WeightedItem<T> wi) {

    // get all items where its T matches the one provided
    List<WeightedItem<T>> existingItems =
        queue.stream().filter(i -> i.item.equals(wi.item)).collect(Collectors.toList());

    if (existingItems.size() == 0) {
      // no weighted item was previously added that contains this T
      addAndSort(wi);
    } else if (existingItems.size() == 1) {
      // a weighted item was previously added that contains this T
      WeightedItem<T> existingItem = existingItems.get(0);

      // if the provided item has a greater weight than the previously-added item,
      // replace the old
      // item with the new
      if (wi.weight > existingItem.weight) {
        queue.remove(existingItem);
        addAndSort(wi);
      }
    } else {
      // we should never be in a situation where we have multiple weighted items that have the
      // same T
      throw new IllegalStateException("Multiple items were found for the item: " + wi.item);
    }
  }

  /**
   * Provides an alternate syntax for adding items to the queue.
   *
   * @param item The item to add to the queue
   * @param weight The weight of the item
   */
  public void add(T item, int weight) {
    add(new WeightedItem<T>(item, weight));
  }

  /**
   * Adds an item to the queue with a weight of 0
   *
   * @param item The item to add to the queue
   */
  public void add(T item) {
    add(new WeightedItem<T>(item, 0));
  }

  /**
   * Adds an item to the queue and resorts the queue. This method assumes the WeightedItem's T has
   * already been checked to make sure it's valid to add it to the queue.
   *
   * @param wt The WeightedItem<T> to add to the queue
   */
  private void addAndSort(WeightedItem<T> wt) {
    queue.add(wt);
    Collections.sort(queue);
  }

  /**
   * Extracts an item from the queue based on the weight and order of addition
   *
   * @return The item with the highest weight, or if multiple elements tie for the highest weight,
   *     the item with the highest weight and the earliest addition to this queue
   */
  public WeightedItem<T> extract() {
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
