package edges;

import compiler.automaton.Node;

/**
 * A fork edge.
 * @author Max Depenbrock
 */
public class ForkEdge extends Edge {

  /**
   * Creates a new {@code ForkEdge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public ForkEdge(Node start, Node end) {
    super(start, end);
  }

}
