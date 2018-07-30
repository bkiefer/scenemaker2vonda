import java.util.HashSet;
import java.util.Set;

/**
 * A complete Scenemaker automaton. This class represents the "default" super node in Scenemaker.
 * @author Max Depenbrock
 *
 */
public class SceneMakerAutomaton extends Supernode {
  
  /**
   * A set of all variables used in this {@code SceneMakerAutomaton} and all of its sub nodes.
   */
  public Set<Variable> allVariables;
  /**
   * A set of all super nodes used in this {@code SceneMakerAutomaton} and all of its sub nodes.
   */
  public Set<Supernode> allSupernodes;
  
  /**
   * Creates a new SceneMakerAutomaton.
   */
  public SceneMakerAutomaton() {
    this.allVariables = new HashSet<>();
    this.allSupernodes = new HashSet<>();
  }
  
  public String getSetupCode() {
	  
	  String outString = "\nsetup_" + this.name + ": \n";
	  outString += "\tif(!" + this.name + ".active) {\n\n";
	  outString += "\t\ttimeout(\"MainAgentStart\", 1400) {\n\n";
	  outString += "\t\t\t" + this.name + ".active = true;\n";
	  outString += "\t\t\t" + this.name + ".simple_children += \"" + this.name + "_in\";\n\n";
	  
	  for (Variable v : this.variables) {
		  outString += "\t\t\t" + this.name + "." + v.name + " = " + v.value + ";\n";
	  }
	  
	  outString += "\t\t}\n";
	  outString += "\t}\n\n";

	  return outString;
  }

public String getPseudoOutCode() {
	  
	  String outString = this.name + "_out: \n";
	  outString += "\tif("+ this.name + ".simple_children.contains(\"" + this.name + "_out\")) {\n\n";
	  
	  
	  outString += "\t\tset_inactive("+ this.name + ");\n";
	  outString += "\t\tshutdown();\n";

	  outString += "\t}\n\n";

	  return outString;
  }
  
}
