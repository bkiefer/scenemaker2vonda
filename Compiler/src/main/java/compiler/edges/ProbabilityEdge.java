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
    super(start, end);
  }

  public String getRudiCode() {
	  
	  String rudiCode = "\t\t";

	  String startNodeString = this.getStartNode().getName();
	  String startNodeParentString = this.getStartNode().getParent().getName();
	  String targetNodeIsSupernode = "false";
	  
	  if (this.getStartNode().isSupernode()) {
		  startNodeString += "_out";
		  startNodeParentString = this.getStartNode().getName();
	  }
	  
	  if (this.getEndNode().isSupernode()) {
		  targetNodeIsSupernode = "true";
	  }
	  
	  rudiCode += "probability_transition(\"" + startNodeString + "\", \"" + this.getEndNode().getName() + "\", ";
	  rudiCode += startNodeParentString + ", " + this.getEndNode().getParent().getName(); 
	  rudiCode += ", " + targetNodeIsSupernode + ");\n";
	  
	  return rudiCode;
  }
  
}
