package compiler.edges;

import compiler.RudiFileGenerator;
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
  
  private String transitionString;
  private boolean isInterruptive;

  
  public Node getStartNode() {
	return startNode;
  }

  public void setStartNode(Node startNode) {
	this.startNode = startNode;
  }

  public Node getEndNode() {
	return endNode;
  }

  public void setEndNode(Node endNode) {
	this.endNode = endNode;
  }
  
  public boolean isInterruptive() {
	return this.isInterruptive;
  }

  public void setInterruptive() {
	this.isInterruptive = true;
  }
  
  public Edge(Node start, Node end, String transitionString) {
	    this.startNode = start;
	    this.endNode = end;
	    this.transitionString = transitionString;
	    this.isInterruptive = false;
	  }
  
  /**
   * Creates a new {@code Edge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public Edge(Node start, Node end) {
    this(start, end, "transition");
  }
  
  public String getRudiCode(int numLeadingTabs) {
	  
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
	  
	  String transitionCode = this.transitionString + "(\"" + startNodeString + "\", \"" + this.endNode.getName() + "\", ";
	  transitionCode += startNodeParentString + ", " + this.endNode.getParent().getName() + ", " + targetNodeIsSupernode + ")"; 
	  
	  return RudiFileGenerator.formattedLine(transitionCode, 0, numLeadingTabs, 1);
  }
  
  /**
   * Default constructor in oder to support subclasses.
   */
  public Edge() {}
  
}