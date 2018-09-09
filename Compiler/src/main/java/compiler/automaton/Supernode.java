package compiler.automaton;
import java.util.HashSet;
import java.util.Set;

import com.sun.xml.internal.ws.util.StringUtils;

import compiler.RudiFileGenerator;
import compiler.edges.Edge;
import compiler.edges.InterruptiveEdge;

/**
 * A scenemaker supernode.
 * @author Max Depenbrock
 */
public class Supernode extends Node {
  
  /**
   * A set of (normal and super) nodes contained in the {@code Supernode}.
   */
  private Set<Node> nodes;
  
  /**
   * A set of the start nodes.
   */
  private Set<Node> startNodes;
 
  /**
   * @return the nodes
   */
  public Set<Node> getNodes() {
	  return nodes;
  }

  /**
   * @param nodes the nodes to set
   */
  public void setNodes(Set<Node> nodes) {
	  this.nodes = nodes;
  }

  /**
   * @return the startNodes
   */
  public Set<Node> getStartNodes() {
	  return startNodes;
  }

  /**
   * @param startNodes the startNodes to set
   */
  public void setStartNodes(Set<Node> startNodes) {
	  this.startNodes = startNodes;
  }

  /**
   * Creates a new {@code Supernode}.
   */
  public Supernode() {
	  this.setSupernode(true);
	  this.nodes = new HashSet<>();
  }
  
  /**
   * Creates the VOnDA-code fragment that initializes the variables belonging to the this {@code Supernode}.
   * @return A String containing the VOnDA-code fragment that initializes the variables of this {@code Supernode}.
   */
  public String getVariableSetupCode() {
	  String outString = "";
	  for (Variable v : this.getVariables()) {
		  outString += RudiFileGenerator.formattedLine(this.getName() + "." + v.getName() + " = " + v.getValue(), 0, 3, 1);
	  }
	  
	  return outString;
  }

/**
 * Creates the VOnDA-code fragment that sets up the {@code Supernode}.
 * @return The VOnDA-code fragment that sets up the {@code Supernode} as a String.
 */
  public String getSetupCode() {
	  
	  String outString = RudiFileGenerator.formattedRuleLabel("setup_" + this.getName(), 1, 0, 1);	  
	  
	  outString += RudiFileGenerator.formattedIfOpening(this.getParent().getName() + ".initiated.contains(\"" + this.getName() + "\")", 0, 1, 1);
	  outString += RudiFileGenerator.formattedIfOpening("!" + this.getName() + ".active", 0, 2, 2);
	  
	  outString += RudiFileGenerator.formattedLine(this.getName() + ".active = true", 0, 3, 1);
	  outString += RudiFileGenerator.formattedLine(this.getParent().getName() + ".super_children += " + this.getName(), 0, 3, 2);	  
	  outString += this.getVariableSetupCode();
	  
	  outString += RudiFileGenerator.formattedIfClosing(0, 2, 2);	  
	  
	  outString += RudiFileGenerator.formattedLine(this.getName() + ".simple_children += \"" + this.getName() + "_in\"", 0, 2, 1);
	  outString += RudiFileGenerator.formattedLine(this.getName() + ".imminent_simple_children += \"" + this.getName() + "_in\"", 0, 2, 1);
	  outString += RudiFileGenerator.formattedLine(this.getParent().getName() + ".initiated -= \"" + this.getName() + "\"", 0, 2, 1);
	  
	  outString += RudiFileGenerator.formattedIfClosing(0, 1, 2);

	  return outString;
  }
  
  /**
   * Creates the VOnDA-code fragment that allows to ignore this {@code Supernode} if it is not active.
   * @return A String containing the VOnDA-code fragment that allows to ignore this {@code Supernode} if it is not active.
   */
  public String getPassByCode() {
	  
	  String outString = RudiFileGenerator.formattedRuleLabel("pass_by_" + this.getName(), 1, 0, 1);	  
	  
	  outString += RudiFileGenerator.formattedIfOpening("!" + this.getName() + ".active", 0, 1, 1);
	  
	  outString += RudiFileGenerator.formattedLine("cancel", 0, 2, 1);
	  
	  outString += RudiFileGenerator.formattedIfClosing(0, 1, 2);
	  
	  return outString;
  }

  /**
   * Creates the VOnDA-code fragment that imitates the functionality of the outgoing {@code InterruptiveEdges} of this {@code Supernode}.
   * @return A String containing the VOnDA-code fragment that imitates the functionality of the outgoing {@code InterruptiveEdges} of this {@code Supernode}.
   */
  public String getSuperInterruptiveEdgesCode() {

	  int interruptiveEdgeIndex = 1;
	  String outString = "";
	  
	  for (Edge e : this.getInterruptiveEdges()) {
			 
		  InterruptiveEdge edge = (InterruptiveEdge) e;
		  String targetNodeIsSupernode = "false";
		  Node n = edge.getEndNode();
		  if(n.isSupernode()) {
			  targetNodeIsSupernode = "true";
		  }
		  
		  outString += RudiFileGenerator.formattedRuleLabel(this.getName() + "_interruptive_edge_" + Integer.toString(interruptiveEdgeIndex), 0, 0, 0);			 
		  
		  outString += RudiFileGenerator.formattedIfOpening(edge.replaceVarNamesInCondition(), 1, 1, 2);
		  
		  String transitionString = "interruptive_transition(" + this.getName() + ", " + this.getParent().getName() + ", \"";
		  transitionString += n.getName() + "\", " + targetNodeIsSupernode  + ")";
		  outString += RudiFileGenerator.formattedLine(transitionString, 0, 2, 1);
		  outString += RudiFileGenerator.formattedLine("cancel", 1, 2, 1); 
		  
		  outString += RudiFileGenerator.formattedIfClosing(0, 1, 2);
		  
		  interruptiveEdgeIndex += 1;
	  }
	  
	  return outString;
  }
  
  /**
   * 
   * @return
   */
  public String getStartNodeTransitionCode() {
	  
	  String outString = "";
	  for (Node n : this.getStartNodes()) {
		  
		  String targetNodeIsSupernode = "false";
		  if(n.isSupernode()) {
			  targetNodeIsSupernode = "true";
		  } 
		  
		  String transitionString = "transition(\"" + this.getName() + "_in\", \"" + n.getName() + "\", " + this.getName() + ", ";
		  transitionString += this.getName() + ", " + targetNodeIsSupernode + ")";
		  
		  outString += RudiFileGenerator.formattedLine(transitionString, 0, 3, 1);
	  }
	  
	  return outString;
  }
	  
  /**
   * Creates the rudi-code fragment that imitates entering the {@code Supernode}.
   * @return the rudi-code fragment that imitates entering the {@code Supernode} as a String.
   */
  public String getPseudoInCode() {
	  
	  String outString = RudiFileGenerator.formattedRuleLabel(this.getName() + "_in", 0, 0, 1);	  
	  
	  outString += RudiFileGenerator.formattedIfOpening(this.getName() + ".simple_children.contains(\"" + this.getName() + "_in\")", 0, 1, 2);
	  
	  outString += this.convertCodeToRudi();
	  outString += RudiFileGenerator.formattedLine(this.getName() + ".imminent_simple_children -= \"" + this.getName() + "_in\"", 2, 2, 2);
	  outString += this.getStartNodeTransitionCode();
	  
	  String transitionString = "check_out_transition(\"" + this.getName() + "_in\", \"" + this.getName() + "_out\", ";
	  transitionString += this.getName() + ", " + this.getName() + ")";
	  outString += RudiFileGenerator.formattedLine(transitionString, 1, 3, 1);	  
	  
	  outString += RudiFileGenerator.formattedIfClosing(0, 1, 2);

	  return outString;
  }
  
  /**
   * Creates the rudi-code fragment that imitates leaving the {@code Supernode}.
   * @return the rudi-code fragment that imitates leaving the {@code Supernode} as a String.
   */
  public String getPseudoOutCode() {
	  
	  String outString = RudiFileGenerator.formattedRuleLabel(this.getName() + "_out", 0, 0, 1);	  
	  
	  outString += RudiFileGenerator.formattedIfOpening(this.getName() + ".simple_children.contains(\"" + this.getName() + "_out\")", 0, 1, 1);
	  
	  if(this.getParent() != null) {
		  outString += this.getEdgeCode(this.getTimeoutEdges());
		  outString += RudiFileGenerator.formattedLine(this.getName() + ".imminent_simple_children -= \"" + this.getName() + "_out\"", 1, 2, 2);
		  outString += this.getEdgeCode(this.getConditionalEdges());
		  outString += this.getEdgeCode(this.getEpsilonEdges());
		  outString += this.getEdgeCode(this.getForkEdges());
		  outString += this.getEdgeCode(this.getProbabilityEdges());
	  }

	  outString += RudiFileGenerator.formattedIfOpening("test_inactive("+ this.getName() + ")", 1, 2, 2);
	  
	  outString += RudiFileGenerator.formattedLine("set_inactive(" + this.getName() + ")", 0, 3, 1);
	  
	  if(this.getParent() != null) {
		  outString += RudiFileGenerator.formattedLine(this.getParent().getName() + ".super_children -= " + this.getName(), 0, 3, 1);
	  }
	  else {
		  outString += RudiFileGenerator.formattedLine("shutdown()", 0, 3, 1); 
	  }
	  
	  outString += RudiFileGenerator.formattedIfClosing(0, 2, 1);
	  
	  if(this.getParent() != null) {
		  
		  String transitionString = "check_out_transition(\"" + this.getName() + "_out\", \"" + this.getParent().getName() + "_out\", ";
		  transitionString += this.getName() + ", " + this.getParent().getName() + ")";  
		  outString += RudiFileGenerator.formattedLine(transitionString, 1, 2, 1);
	  }
	  
	  outString += RudiFileGenerator.formattedIfClosing(0, 1, 2);

	  return outString;
  }
  
  /**
   * Creates the rudi-code fragment that imports the code for the children of the {@code Supernode}.
   * @return the rudi-code fragment that imports the code for the children of the {@code Supernode} as a String.
   */
  public String getImportCode() {
	  String outString = "";
	  for (Node n : this.nodes) {
		  if(n.isSupernode()) {
			  outString += RudiFileGenerator.formattedLine("import " + n.getName().substring(0,1).toUpperCase() + n.getName().substring(1), 0, 0, 1);
		  }
	  }	  
	  
	  return outString;
  }

  /**
   * Ensures that the name of the {@code Supernode} and its children are lower case.
   */
  public void ensureNodeNamesAreLowerCase() {
	for (Node n: this.nodes) {
		n.setName(StringUtils.decapitalize(n.getName()));
	
		if(n.isSupernode()) {
			Supernode m = (Supernode) n;
			m.ensureNodeNamesAreLowerCase();
		}
	}	
  }

}
