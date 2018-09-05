package edges;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
  
  public String getCondition() {
	return condition;
  }

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

  public String getRudiCode() {
	  
	  String rudiCode = "\t\tif " + this.convertConditionToRudi() + " {\n\t";
	  
	  rudiCode += super.getRudiCode();
	  
	  /*String startNodeString = this.startNode.name;
	  String startNodeParentString = this.startNode.parent.name;
	  String targetNodeIsSupernode = "false";
	  
	  if (this.startNode.isSupernode) {
		  startNodeString += "_out";
		  startNodeParentString = this.startNode.name;
	  }
	  
	  if (this.endNode.isSupernode) {
		  targetNodeIsSupernode = "true";
	  }
	  
	  rudiCode += "transition(\"" + startNodeString + "\", \"" + this.endNode.name + "\", ";
	  rudiCode += startNodeParentString + ", " + this.endNode.parent.name; 
	  rudiCode += ", " + targetNodeIsSupernode + ");\n";
	  
	  */
	  
	  rudiCode += "\t\t}\n\n";
	  
	  return rudiCode;
  }
  
  
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
