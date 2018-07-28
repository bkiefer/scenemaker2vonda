
import java.util.HashSet;
import java.util.Set;

/**
 * A scenemaker node.
 * @author Max Depenbrock
 */
public class Node {
  
  /**
   * The name of the {@code Node}.
   */
  public String name;
  /**
   * The code that is executed when reaching the {@code Node}.
   */
  public String code;
  /**
   * A set of variables defined in the {@code Node}.}
   */
  public Set<Variable> variables;
  /**
   * A set of outgoing edges.
   */
  public Set<Edge> outgoingEdges;
  /**
   * Whether the {@code Node} is a {@code Supernode}.
   */
  boolean isSupernode;

  /**
   * Creates a new {@code Node}.
   */
  public Node() {
    this.outgoingEdges = new HashSet<>();
    this.variables = new HashSet<>();
  }

}
