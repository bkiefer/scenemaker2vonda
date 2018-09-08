package compiler.edges;

import compiler.automaton.Node;

/**
 * An interruptive edge.
 * @author Max Depenbrock
 */
public class InterruptiveEdge extends ConditionalEdge {

  /**
   * Creates a new {@code InterruptiveEdge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public InterruptiveEdge(Node start, Node end) {
    super(start, end);
    this.setInterruptive();
  }
 
}
