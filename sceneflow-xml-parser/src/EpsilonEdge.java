
/**
 * An epsilon edge.
 * @author Max Depenbrock
 */
public class EpsilonEdge extends Edge {

  /**
   * Creates a new {@code EpsilonEdge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public EpsilonEdge(Node start, Node end) {
    super(start, end);
  }

}
