package compiler.edges;

import compiler.automaton.Node;

/**
 * A probability edge.
 * @author Max Depenbrock
 */
public class ProbabilityEdge extends Edge {

  /**
   * Probability of the probability edge.
   */
  private float probability;
  
  public float getProbability() {
	return probability;
  }

  public void setProbability(float probability) {
	this.probability = probability;
  }

/**
   * Creates a new {@code ProbabilityEdge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public ProbabilityEdge(Node start, Node end) {
    super(start, end, "probability_transition");
  }
  
}
