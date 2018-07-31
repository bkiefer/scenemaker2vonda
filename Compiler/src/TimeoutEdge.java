
/**
 * A timeout edge.
 * @author Max Depenbrock
 */
public class TimeoutEdge extends Edge {

  /**
   * Timeout of the timeout edge in milliseconds.
   */
  public int timeout;
  
  /**
   * Creates a new {@code TimeoutEdge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public TimeoutEdge(Node start, Node end) {
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
	  
	  rudiCode += "timeout_transition(\"" + startNodeString + "\", \"" + this.endNode.name + "\", ";
	  rudiCode += startNodeParentString + ", " + this.endNode.parent.name; 
	  rudiCode += ", " + targetNodeIsSupernode;
	  rudiCode += ", " + Integer.toString(this.timeout) + ");\n";
	  
	  return rudiCode;
  }
  
}
