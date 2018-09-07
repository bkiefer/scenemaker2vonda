package compiler.automaton;
import java.util.HashSet;
import java.util.Set;

import com.sun.xml.internal.ws.util.StringUtils;

import compiler.edges.ConditionalEdge;
import compiler.edges.Edge;
import compiler.edges.InterruptiveEdge;
import compiler.edges.ProbabilityEdge;
import compiler.edges.TimeoutEdge;

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

  
  public Set<Node> getNodes() {
	  return nodes;
  }

  public void setNodes(Set<Node> nodes) {
	  this.nodes = nodes;
  }

  public Set<Node> getStartNodes() {
	  return startNodes;
  }

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
  
  public String getSetupCode() {
	  
	  String outString = "\nsetup_" + this.getName() + ": \n";
	  outString += "\tif("+ this.getParent().getName() + ".initiated.contains(\"" + this.getName() + "\")) {\n";
	  outString += "\t\tif(!" + this.getName() + ".active) {\n\n";
	  outString += "\t\t\t" + this.getName() + ".active = true;\n";
	  outString += "\t\t\t" + this.getParent().getName() + ".super_children += " + this.getName() + ";\n\n";
	  
	  for (Variable v : this.getVariables()) {
		  outString += "\t\t\t" + this.getName() + "." + v.getName() + " = " + v.getValue() + ";\n";
	  }
	  
	  outString += "\t\t}\n\n";
	  outString += "\t\t" + this.getName() + ".simple_children += \"" + this.getName() + "_in\";\n";
	  outString += "\t\t" + this.getName() + ".imminent_simple_children += \"" + this.getName() + "_in\";\n";
	  outString += "\t\t" + this.getParent().getName() + ".initiated -= \"" + this.getName() + "\";\n";
	  outString += "\t}\n\n";

	  return outString;
  }
  
  public String getPassByCode() {
	  
	  String outString = "\npass_by_" + this.getName() + ": \n";
	  outString += "\tif(!" + this.getName() + ".active) {\n";
	  outString += "\t\tcancel;\n";
	  outString += "\t}\n\n";
	  
	  return outString;
  }

  public String getInterruptiveEdgesCode() {
	  
	  int interruptiveEdgeIndex = 1;
	  String outString = "";

	  for (Edge e : this.getOutgoingEdges()) {
		  if (e instanceof InterruptiveEdge) {
			 
			  InterruptiveEdge edge = (InterruptiveEdge) e;
			  String targetNodeIsSupernode = "false";
			  
			  outString += this.getName() + "_interruptive_edge_" + Integer.toString(interruptiveEdgeIndex) + ":\n";
			  outString += "\tif" + edge.convertConditionToRudi() + " {\n\n";
			  
			  Node n = edge.getEndNode();
			  
			  if(n.isSupernode()) {
				  targetNodeIsSupernode = "true";
			  }
			 
			  outString += "\t\tinterruptive_transition(" + this.getName() + ", " + this.getParent().getName() + ", \""; 
			  outString += n.getName() + "\", " + targetNodeIsSupernode  + ");\n";
			  outString += "\n\t\tcancel;\n";
			  outString += "\t}\n\n";
			  
			  interruptiveEdgeIndex += 1;
		  }
	  }
	  
	  return outString;
  }

  public String getPseudoInCode() {
	  
	  String outString = this.getName() + "_in:\n";
	  outString += "\tif("+ this.getName() + ".simple_children.contains(\"" + this.getName() + "_in\")) {\n\n";
	  
	  outString += this.convertCodeToRudi() + "\n\n";
	  outString += "\t\t" + this.getName() + ".imminent_simple_children -= \"" + this.getName() + "_in\";\n\n";

	  for (Node n : this.startNodes) {
		  
		  String targetNodeIsSupernode = "false";
		  
		  if(n.isSupernode()) {
			  targetNodeIsSupernode = "true";
		  }
		  
		  outString += "\t\t\ttransition(\"" + this.getName() + "_in\", \"" + n.getName() + "\", " + this.getName() + ", ";
		  outString += this.getName() + ", " + targetNodeIsSupernode + ");\n";	
	  }
	  
	  outString += "\n\t\t\tcheck_out_transition(\"" + this.getName() + "_in\", \"" + this.getName() + "_out\", " + this.getName() + ", " + this.getName() + ");\n";			  
	  outString += "\t}\n\n";

	  return outString;
  }
  
  public String getPseudoOutCode() {
	  
	  String outString = this.getName() + "_out: \n";
	  outString += "\tif("+ this.getName() + ".simple_children.contains(\"" + this.getName() + "_out\")) {\n\n";
	  
	  for (Edge e : this.getOutgoingEdges()) {
		  if (e instanceof TimeoutEdge) {
			  outString += e.getRudiCode();			  
		  }
	  }
	  
	  for (Edge e : this.getOutgoingEdges()) {
		  if (e instanceof ProbabilityEdge) {
			  outString += e.getRudiCode() + "\n";
		  }
	  }
	  
	  outString += "\t\t" + this.getName() + ".imminent_simple_children -= \"" + this.getName() + "_out\";\n\n";
	  
	  for (Edge e : this.getOutgoingEdges()) {
		  if (e instanceof ConditionalEdge) {
			  outString += e.getRudiCode();			  
		  }
	  }
	  
	  for (Edge e : this.getOutgoingEdges()) {
		  if (!(e instanceof TimeoutEdge) && !(e instanceof ConditionalEdge) && !(e instanceof InterruptiveEdge)) {
			  outString += e.getRudiCode();			  
		  }
	  }
	  
	  outString += "\n\t\tif(test_inactive("+ this.getName() + ")) {\n\n";
	  outString += "\t\t\t" + this.getParent().getName() + ".super_children -= " + this.getName() + ";\n";
	  outString += "\t\t\tset_inactive("+ this.getName() + ");\n";
	  outString += "\t\t}\n\n";

	  outString += "\t\tcheck_out_transition(\"" + this.getName() + "_out\", \"" + this.getParent().getName() + "_out\", " + this.getName() + ", " + this.getParent().getName() + ");\n";			  
	  outString += "\t}\n\n";

	  return outString;
  }
  
  public String getImportCode() {
	 
	  String outString = "";
	  for (Node n : this.nodes) {
		  if(n.isSupernode()) {
			  outString += "import " + n.getName().substring(0,1).toUpperCase() + n.getName().substring(1) + ";\n";
		  }
	  }
	  
	  return outString;
  }

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
