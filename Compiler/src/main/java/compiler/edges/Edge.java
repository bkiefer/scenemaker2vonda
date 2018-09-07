package compiler.edges;

import compiler.automaton.Node;

/**
 * Superclass for scenemaker edges.
 * @author Max Depenbrock
 */
public abstract class Edge {

  /** 
   * {@code Node} at which the edge starts.
   */
  private Node startNode;
  /** 
   * {@code Node} at which the edge ends. 
   */
  private Node endNode;
  
  /**
   * @return the startNode
   */
  public Node getStartNode() {
	  return startNode;
  }

  /**
   * @param startNode the startNode to set
   */
  public void setStartNode(Node startNode) {
	  this.startNode = startNode;
  }

  /**
   * @return the endNode
   */
  public Node getEndNode() {
	  return endNode;
  }

  /**
   * @param endNode the endNode to set
   */
  public void setEndNode(Node endNode) {
	  this.endNode = endNode;
  }

  /**
   * Creates a new {@code Edge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public Edge(Node start, Node end) {
	  this.startNode = start;
	  this.endNode = end;
  }
  
  /**
   * Creates the rudi-code fragment that imitates the functionality of the {@code Edge}.
   * @return the rudi-code fragment that imitates the functionality of the {@code Edge} as a String.
   */
  public String getRudiCode() {
	  String rudiCode = "\t\t";

	  String startNodeString = this.startNode.getName();
	  String startNodeParentString = this.startNode.getParent().getName();
	  String targetNodeIsSupernode = "false";
	  
	  if (this.startNode.isSupernode()) {
		  startNodeString += "_out";
		  startNodeParentString = this.startNode.getName();
	  }
	  
	  if (this.endNode.isSupernode()) {
		  targetNodeIsSupernode = "true";
	  }
	  
	  rudiCode += "transition(\"" + startNodeString + "\", \"" + this.endNode.getName() + "\", ";
	  rudiCode += startNodeParentString + ", " + this.endNode.getParent().getName(); 
	  rudiCode += ", " + targetNodeIsSupernode + ");\n";
	  
	  return rudiCode;
  }
  
  /**
   * Default constructor in oder to support subclasses.
   */
  public Edge() {}
  
}