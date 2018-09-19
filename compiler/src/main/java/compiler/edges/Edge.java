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
  
  /**
   * The String that is used to translate this edge into VOnDA syntax (the VOnDA function used to implement the node transition)
   */
  private String transitionString;

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
   * Creates a new {@code Edge} starting at {@code start} and ending at {@code end}, with the specified {@code transitionString}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   * @param transitionString A String containing the name of the VOnDA function used to translate this edge into VOnDA syntax
   */
  public Edge(Node start, Node end, String transitionString) {
	    this.startNode = start;
	    this.endNode = end;
	    this.transitionString = transitionString;
	  }
  
  /**
   * Creates a new {@code Edge} starting at {@code start} and ending at {@code end}, with the default {@code transitionString} "transition".
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public Edge(Node start, Node end) {
    this(start, end, "transition");
  }
  
  /**
   * Creates the VOnDA-code fragment that imitates the functionality of the {@code Edge}.
   * @param numLeadingTabs number of leading tabs
   * @return A String containing the VOnDA-code fragment that imitates the functionality of the {@code Edge} as a String
   */
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
  
}