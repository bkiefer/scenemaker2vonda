
/**
 * A probability edge.
 * @author Max Depenbrock
 */
public class ProbabilityEdge extends Edge {

  /**
   * Probability of the probability edge.
   */
  public float probability;
  
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

	  String startNodeString = this.startNode.name;
	  String startNodeParentString = this.startNode.parent.name;
	  String targetNodeIsSupernode = "false";
	  
	  if (this.startNode.isSupernode) {
		  startNodeString += "_out";
		  startNodeParentString = this.startNode.name;
	  }
	  
	  if (this.endNode.isSupernode) {
		  targetNodeIsSupernode = "true";
	  }
	  
	  rudiCode += "probability_transition(\"" + startNodeString + "\", \"" + this.endNode.name + "\", ";
	  rudiCode += startNodeParentString + ", " + this.endNode.parent.name; 
	  rudiCode += ", " + targetNodeIsSupernode + ");\n";
	  
	  return rudiCode;
  }
  
}
