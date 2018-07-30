import java.util.HashSet;
import java.util.Set;

/**
 * A scenemaker supernode.
 * @author Max Depenbrock
 */
public class Supernode extends Node {
  
  /**
   * A set of (normal and super) nodes contained in the {@code Supernode}.
   */
  public Set<Node> nodes;
  /**
   * A set of the start nodes.
   */
  public Set<Node> startNodes;

  
  /**
   * Creates a new {@code Supernode}.
   */
  public Supernode() {
    this.isSupernode = true;
    this.nodes = new HashSet<>();
  }
  
  public String getSetupCode() {
	  
	  String outString = "\nsetup_" + this.name + ": \n";
	  outString += "\tif("+ this.parent.name + ".initiated.contains(\"" + this.name + "\")) {\n";
	  outString += "\t\tif(!" + this.name + ".active) {\n\n";
	  outString += "\t\t\t" + this.name + ".active = true;\n";
	  outString += "\t\t\t" + this.parent.name + ".super_children += " + this.name + ";\n\n";
	  
	  for (Variable v : this.variables) {
		  outString += "\t\t\t" + this.name + "." + v.name + " = " + v.value + ";\n";
	  }
	  
	  outString += "\t\t}\n\n";
	  outString += "\t\t" + this.name + ".simple_children += \"" + this.name + "_in\";\n";
	  outString += "\t\t" + this.parent.name + ".initiated -= \"" + this.name + "\";\n";
	  outString += "\t}\n\n";

	  return outString;
  }
  
  public String getPassByCode() {
	  
	  String outString = "\npass_by_" + this.name + ": \n";
	  outString += "\tif(!" + this.name + ".active) {\n";
	  outString += "\t\tcancel;\n";
	  outString += "\t}\n\n";
	  
	  return outString;
  }

  public String getInterruptiveEdgesCode() {
	  
	  // Set<InterruptiveEdge> outgoingInterruptiveEdges = new HashSet<InterruptiveEdge>();
	  int interruptiveEdgeIndex = 1;
	  String outString = "";

	  for (Edge e : this.outgoingEdges) {
		  if (e instanceof InterruptiveEdge) {
			  //outgoingInterruptiveEdges.add((InterruptiveEdge) e);
			 
			  InterruptiveEdge edge = (InterruptiveEdge) e;
			  
			  outString += this.name + "_interruptive_edge_" + Integer.toString(interruptiveEdgeIndex) + ":\n";
			  outString += "\tif" + edge.convertConditionToRudi() + " {\n\n";
			  
			  Node n = edge.endNode;
			  
			  if(n.isSupernode) {
				  outString += "\t\tinterruptive_super_transition(" + this.name + ", " + this.parent.name + ", \"" + n.name + "\");\n";
			  }
			  
			  else {
				  outString += "\t\tinterruptive_transition(" + this.name + ", " + this.parent.name + ", \"" + n.name + "\");\n";
			  }
			  
			  outString += "\n\t\tcancel;\n";
			  outString += "\t}\n\n";
			  
			  interruptiveEdgeIndex += 1;
		  }
	  }
	  
	  
	  
	  
	  return outString;
  }

  public String getPseudoInCode() {
	  
	  String outString = this.name + "_in:\n";
	  outString += "\tif("+ this.name + ".simple_children.contains(\"" + this.name + "_in\")) {\n\n";
	  outString += this.convertCodeToRudi() + "\n";
	  
	  for (Node n : this.startNodes) {
		  
		  if(n.isSupernode) {
			  outString += "\t\t\tsuper_transition(\"" + this.name + "_in\", " + this.name + ", \"" + n.name + "\");\n";
		  }
		  
		  else {
			  outString += "\t\t\ttransition(\"" + this.name + "_in\", \"" + n.name + "\", " + this.name + ", " + this.name + ");\n";			  
		  }
	  }
	  
	  outString += "\n\t\t\tcheck_out_transition(\"" + this.name + "_in\", \"" + this.name + "_out\", " + this.name + ", " + this.name + ");\n";			  
	  outString += "\t}\n\n";

	  return outString;
  }
  
  public String getPseudoOutCode() {
	  
	  String outString = this.name + "_out: \n";
	  outString += "\tif("+ this.name + ".simple_children.contains(\"" + this.name + "_out\")) {\n\n";
	  
	  for (Edge e : this.outgoingEdges) {
		  
		  outString += e.getRudiCode();
	  }
	  
	  outString += "\t\tif(test_inactive("+ this.name + ")) {\n\n";
	  outString += "\t\t\t" + this.parent.name + ".super_children -= " + this.name + ";\n";
	  outString += "\t\t\tset_inactive("+ this.name + ");\n";
	  outString += "\t\t}\n\n";

	  outString += "\t\tcheck_out_transition(\"" + this.name + "_out\", \"" + this.parent.name + "_out\", " + this.name + ", " + this.parent.name + ");\n";			  
	  outString += "\t}\n\n";

	  return outString;
  }
  
  public String getImportCode() {
	 
	  String outString = "";
	  for (Node n : this.nodes) {
		  if(n.isSupernode) {
			  outString += "import " + n.name + ";\n";
		  }
	  }
	  
	  return outString;
  }
}
