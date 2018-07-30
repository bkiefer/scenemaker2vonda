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
   * Name of the node's parent.
   */
  public String parentName = "";
  
  /**
   * Creates a new {@code Supernode}.
   */
  public Supernode() {
    this.isSupernode = true;
    this.nodes = new HashSet<>();
  }
  
  public String getSetupCode() {
	  
	  String outString = "\nsetup_" + this.name + ": \n";
	  outString += "\tif("+ this.parentName + ".initiated.contains(\"" + this.name + "\")) {\n";
	  outString += "\t\tif(!" + this.name + ".active) {\n\n";
	  outString += "\t\t\t" + this.name + ".active = true;\n";
	  outString += "\t\t\t" + this.parentName + ".super_children += " + this.name + ";\n\n";
	  
	  for (Variable v : this.variables) {
		  outString += "\t\t\t" + this.name + "." + v.name + " = " + v.value + ";\n";
	  }
	  
	  outString += "\t\t}\n\n";
	  outString += "\t\t" + this.name + ".simple_children += \"" + this.name + "_in\";\n";
	  outString += "\t\t" + this.parentName + ".initiated -= \"" + this.name + "\";\n";
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
	  
	  String outString = "";
	  
	  return outString;
  }

  public String getPseudoInCode() {
	  
	  String outString = this.name + "_in: \n";
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
	  outString += "\t\t\t" + this.parentName + ".super_children -= " + this.name + ";\n";
	  outString += "\t\t\tset_inactive("+ this.name + ");\n";
	  outString += "\t\t}\n\n";

	  outString += "\t\tcheck_out_transition(\"" + this.name + "_out\", \"" + this.parentName + "_out\", " + this.name + ", " + this.parentName + ");\n";			  
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
