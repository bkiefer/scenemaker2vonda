package edges;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import compiler.automaton.Node;

/**
 * An interruptive edge.
 * @author Max Depenbrock
 */
public class InterruptiveEdge extends Edge {
  
  /**
   * Condition of the interruptive edge.
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
   * Creates a new {@code InterruptiveEdge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public InterruptiveEdge(Node start, Node end) {
	  super(start, end);
  }
  
  /**
   * Creates the rudi-code fragment that imitates the functionality of the {@code InterruptiveEdge}.
   * @return the rudi-code fragment that imitates the functionality of the {@code InterruptiveEdge} as a String.
   */
  public String convertConditionToRudi() { 
	    String rudiCondition = this.condition;
	    
		Pattern VAR_TAG_PATTERN = Pattern.compile("<v>(.*?)</v>");
		Matcher m = VAR_TAG_PATTERN.matcher(rudiCondition);
		
		while (true) {
			if(m.find()) {
				String varName = m.group(1);			
				String extendedVarName = this.getStartNode().replaceVarName(varName);
				String stringToReplace = "<v>" + varName + "</v>";
				
				rudiCondition = rudiCondition.replace(stringToReplace, extendedVarName);
				m = VAR_TAG_PATTERN.matcher(rudiCondition);
			}
			else {
				break;
			}
		}
	  
	  return rudiCondition;
  }

}
