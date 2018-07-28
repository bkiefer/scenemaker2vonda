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

}
