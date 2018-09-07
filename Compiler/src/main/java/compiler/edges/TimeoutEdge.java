package compiler.edges;

import compiler.automaton.Node;

/**
 * A timeout edge.
 * @author Max Depenbrock
 */
public class TimeoutEdge extends Edge {

  /**
   * Timeout of the timeout edge in milliseconds.
   */
  private int timeout;
  
  /**
   * @return the timeout
   */
  public int getTimeout() {
	  return timeout;
  }

  /**
   * @param timeout the timeout to set
   */
  public void setTimeout(int timeout) {
	  this.timeout = timeout;
  }

  /**
   * Creates a new {@code TimeoutEdge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public TimeoutEdge(Node start, Node end) {
	  super(start, end);
  }

  /**
   * Creates the rudi-code fragment that imitates the functionality of the {@code TimeoutEdge}.
   * @return the rudi-code fragment that imitates the functionality of the {@code TimeoutEdge} as a String.
   */
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
	  rudiCode += "timeout_transition(\"" + startNodeString + "\", \"" + this.getEndNode().getName() + "\", ";
	  rudiCode += startNodeParentString + ", " + this.getEndNode().getParent().getName(); 
	  rudiCode += ", " + targetNodeIsSupernode;
	  rudiCode += ", " + Integer.toString(this.timeout) + ");\n";
	  
	  return rudiCode;
  }
  
}
