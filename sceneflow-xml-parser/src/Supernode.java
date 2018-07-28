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
}
