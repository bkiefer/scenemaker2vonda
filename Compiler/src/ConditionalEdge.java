
/**
 * A conditional edge.
 * @author Max Depenbrock
 */
public class ConditionalEdge extends Edge {

  /**
   * Condition of the conditional edge.
   */
  public String condition;
  
  /**
   * Creates a new {@code ConditionalEdge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public ConditionalEdge(Node start, Node end) {
    super(start, end);
  }
  
  public String convertConditionToRudi() {
	  
	  String rudiCondition = this.condition;
	  
	  return rudiCondition;
	  
  }

}
