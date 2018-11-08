import java.util.*;

/** Contains information about a specific node in a graph */
public class NodeInfo<T> {

  /** This node */
  public T node;

  /** The rank of this node */
  public double rank;

  /** All outgoing links from this node */
  public Set<T> outgoingLinks = new HashSet<T>();

  /** All incoming links to this node */
  public Set<T> incomingLinks = new HashSet<T>();

  /**
   * Gets the in degree of this node
   *
   * @return The in degree
   */
  public int getInDegree() {
    return incomingLinks.size();
  }

  /**
   * Gets the out degree of this node
   *
   * @return The out degree
   */
  public int getOutDegree() {
    return outgoingLinks.size();
  }
}
