package compiler.edges;

import compiler.RudiFileGenerator;
import compiler.automaton.Node;

/**
 * A conditional edge.
 * @author Max Depenbrock
 */
public class ConditionalEdge extends Edge {

  /**
   * Condition of the conditional edge.
   */
  private String condition;
  
  /**
   * @return the condition
   */
  public String getCondition() {
	  return condition;
  }

  /**
   * @param condition the condition to set
   */
  public void setCondition(String condition) {
	  this.condition = condition;
  }

  /**
   * Creates a new {@code ConditionalEdge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public ConditionalEdge(Node start, Node end) {
	  super(start, end);
  }

  /**
   * Creates the rudi-code fragment that imitates the functionality of the {@code ConditionalEdge}.
   * @param numLeadingTabs Integer indicating how indented (number of tabs) the code should be 
   * @return the rudi-code fragment that imitates the functionality of the {@code ConditionalEdge} as a String.
   */
  public String getRudiCode(int numLeadingTabs) {
	  
	  String rudiCode = RudiFileGenerator.formattedIfOpening(this.replaceVarNamesInCondition(), 0, numLeadingTabs, 1);	  
	  rudiCode += super.getRudiCode(numLeadingTabs + 1);
	  rudiCode += RudiFileGenerator.formattedIfClosing(0, numLeadingTabs, 2);
	  
	  return rudiCode;
  }
  
  /**
   * Replace variables in the {@code condition} of the {@code ConditionalEdge} with fields of supernodes.
   * @return The {@code condition} of the {@code ConditionalEdge} in rudi-syntax.
   */
  public String replaceVarNamesInCondition() {
	  return RudiFileGenerator.replaceVarNamesInString(this.getCondition(), this.getStartNode());
  }

}
