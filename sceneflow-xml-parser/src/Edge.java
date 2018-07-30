/**
 * Superclass for scenemaker edges.
 * @author Max Depenbrock
 */
public abstract class Edge {

  /** 
   * {@code Node} at which the edge starts.
   */
  public Node startNode;
  /** 
   * {@code Node} at which the edge ends. 
   */
  public Node endNode;
  
  /**
   * Creates a new {@code Edge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public Edge(Node start, Node end) {
    this.startNode = start;
    this.endNode = end;
  }
  
  /**
   * Default constructor in oder to support subclasses.
   */
  public Edge() {}
  
}