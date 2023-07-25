package compiler.automaton;

import static compiler.Utils.*;

import java.util.HashSet;
import java.util.Set;

import compiler.RudiFileGenerator;

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
   * @return the set of super nodes used in this {@code SceneMakerAutomaton} and all of its sub nodes.
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

  @Override
  public String getName() {
    // this is returning not the plain name, but a unique and compatible name
    // for all situations
    return decapitalize(name);
  }

  /**
   * Creates the VOnDA-code fragment that sets up the {@code SceneMakerAutomaton}.
   * @return A String containing the VOnDA-code fragment that sets up the {@code SceneMakerAutomaton}
   */
  @Override
  public String getSetupCode() {

	  String outString = RudiFileGenerator.formattedRuleLabel("setup_" + this.getName(), 1, 0, 1);

	  outString += RudiFileGenerator.formattedIfOpening("!" + this.getName() + ".active", 0, 1, 2);
	  outString += RudiFileGenerator.formattedExpression("timeout(\"MainAgentStart\", 1400) {", 0, 2, 2);

	  outString += RudiFileGenerator.formattedLine(this.getName() + ".active = true", 0, 3, 1);
	  outString += RudiFileGenerator.formattedLine(this.getName() + ".simple_children += \"" + this.getName() + "_in\"", 0, 3, 2);
	  outString += RudiFileGenerator.formattedLine(this.getName() + ".imminent_simple_children += \"" + this.getName() + "_in\"", 0, 3, 1);

	  outString += this.getVariableSetupCode();

	  outString += RudiFileGenerator.formattedIfClosing(0, 2, 1);
	  outString += RudiFileGenerator.formattedIfClosing(0, 1, 2);

	  return outString;
  }

}
