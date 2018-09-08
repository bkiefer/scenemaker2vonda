package compiler.edges;

import compiler.RudiFileGenerator;
import compiler.automaton.Node;

/**
 * A timeout edge.
 * @author Max Depenbrock
 */
public class TimeoutEdge extends Edge {

  /**
   * Timeout of the timeout edge in milliseconds.
   */
  private int timeout;
  
  
  public int getTimeout() {
	return timeout;
  }

  public void setTimeout(int timeout) {
	this.timeout = timeout;
  }


/**
   * Creates a new {@code TimeoutEdge} starting at {@code start} and ending at {@code end}.
   * @param start {@code Node} at which the edge starts
   * @param end {@code Node} at which the edge ends
   */
  public TimeoutEdge(Node start, Node end) {
    super(start, end, "timeout_transition");
  }


  public String getRudiCode(int numLeadingTabs) {
	  
	  String rudiCode = super.getRudiCode(numLeadingTabs);
	  rudiCode = rudiCode.substring(0, rudiCode.length() - 3) + ", " + Integer.toString(this.timeout) + ")" ;
	 
	  return RudiFileGenerator.formattedLine(rudiCode, 0, 0, 1);
  }
  
}
