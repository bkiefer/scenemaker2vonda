package compiler.automaton;
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
  private Set<Variable> allVariables;
  /**
   * A set of all super nodes used in this {@code SceneMakerAutomaton} and all of its sub nodes.
   */
  private Set<Supernode> allSupernodes;
  
  /**
   * @return the set of variables used in this {@code SceneMakerAutomaton} and all of its sub nodes.
   */
  public Set<Variable> getAllVariables() {
	  return allVariables;
  }

  /**
   * @param allVariables the allVariables to set
   */
  public void setAllVariables(Set<Variable> allVariables) {
	  this.allVariables = allVariables;
  }

  /**
   * @return the set of  super nodes used in this {@code SceneMakerAutomaton} and all of its sub nodes.
   */
  public Set<Supernode> getAllSupernodes() {
	  return allSupernodes;
  }

  /**
   * @param allSupernodes the allSupernodes to set
   */
  public void setAllSupernodes(Set<Supernode> allSupernodes) {
	  this.allSupernodes = allSupernodes;
  }

  /**
   * Creates a new SceneMakerAutomaton.
   */
  public SceneMakerAutomaton() {
	  this.allVariables = new HashSet<>();
	  this.allSupernodes = new HashSet<>();
  }
  
  /**
   * Creates the rudi-code fragment that sets up the {@code SceneMakerAutomaton}.
   * @return the rudi-code fragment that sets up the {@code SceneMakerAutomaton} as a String.
   */
  public String getSetupCode() {
	  String outString = "\nsetup_" + this.getName() + ": \n";
	  outString += "\tif(!" + this.getName() + ".active) {\n\n";
	  outString += "\t\ttimeout(\"MainAgentStart\", 1400) {\n\n";
	  outString += "\t\t\t" + this.getName() + ".active = true;\n";
	  outString += "\t\t\t" + this.getName() + ".simple_children += \"" + this.getName() + "_in\";\n\n";
	  
	  for (Variable v : this.getVariables()) {
		  outString += "\t\t\t" + this.getName() + "." + v.getName() + " = " + v.getValue() + ";\n";
	  }
	  
	  outString += "\t\t}\n";
	  outString += "\t}\n\n";

	  return outString;
  }

  /**
   * Creates the rudi-code fragment that shuts down the {@code SceneMakerAutomaton}.
   * @return the rudi-code fragment that shuts down the {@code SceneMakerAutomaton} as a String.
   */
  public String getPseudoOutCode() {
	  
	  String outString = this.getName() + "_out: \n";
	  outString += "\tif("+ this.getName() + ".simple_children.contains(\"" + this.getName() + "_out\")) {\n\n";
	  
	  
	  outString += "\t\tset_inactive("+ this.getName() + ");\n";
	  outString += "\t\tshutdown();\n";

	  outString += "\t}\n\n";

	  return outString;
  }
  
}
