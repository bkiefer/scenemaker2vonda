
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

  public String getRudiCode() {
	  
	  String rudiCode = "\t\tif " + this.convertConditionToRudi() + " {\n\t\t\t";
	  
	  String startNodeString = this.startNode.name;
	  String startNodeParentString = this.startNode.parent.name;
	  
	  if (this.startNode.isSupernode) {
		  startNodeString += "_out";
		  startNodeParentString = this.startNode.name;
	  }
	  
	  if (this.endNode.isSupernode) {
		  
		  rudiCode += "super_transition(\"" + startNodeString + "\", ";
		  rudiCode += startNodeParentString;
		  rudiCode += ", \"" + this.endNode.name + "\");\n";	  
	  } else {
		  
		  rudiCode += "transition(\"" + startNodeString + "\", \"" + this.endNode.name + "\", ";
		  rudiCode += startNodeParentString + ", " + this.endNode.parent.name + ");\n";
	  }
	  
	  rudiCode += "\t\t}\n\n";
	  
	  return rudiCode;
  }
  
  
  public String convertConditionToRudi() {
	  
	  String rudiCondition = this.condition;
	  
	  return rudiCondition;
	  
  }

}
