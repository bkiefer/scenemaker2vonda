package edges;

import compiler.automaton.Node;

/**
 * Superclass for scenemaker edges.
 * @author Max Depenbrock
 */
public abstract class Edge {

  /** 
   * {@code Node} at which the edge starts.
   */
  public Node startNode;
  /** 
   * {@code Node} at which the edge ends. 
   */
  public Node endNode;
  
  /**
   * Creates a new {@code Edge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public Edge(Node start, Node end) {
    this.startNode = start;
    this.endNode = end;
  }
  
  public String getRudiCode() {
	  
	  String rudiCode = "\t\t";

	  String startNodeString = this.startNode.name;
	  String startNodeParentString = this.startNode.parent.name;
	  String targetNodeIsSupernode = "false";
	  
	  if (this.startNode.isSupernode()) {
		  startNodeString += "_out";
		  startNodeParentString = this.startNode.name;
	  }
	  
	  if (this.endNode.isSupernode()) {
		  targetNodeIsSupernode = "true";
	  }
	  
	  rudiCode += "transition(\"" + startNodeString + "\", \"" + this.endNode.name + "\", ";
	  rudiCode += startNodeParentString + ", " + this.endNode.parent.name; 
	  rudiCode += ", " + targetNodeIsSupernode + ");\n";
	  
	  return rudiCode;
  }
  
  /**
   * Default constructor in oder to support subclasses.
   */
  public Edge() {}
  
}