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
  
  /**
   * @return the timeout
   */
  public int getTimeout() {
	  return timeout;
  }

  /**
   * @param timeout the timeout to set
   */
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

  /**
   * Creates the VOnDA-code fragment that imitates the functionality of the {@code TimeoutEdge}.
   * @return A String containing the VOnDA-code fragment that imitates the functionality of the {@code TimeoutEdge}
   */
  public String getRudiCode(int numLeadingTabs) {
	  
	  String rudiCode = super.getRudiCode(numLeadingTabs);
	  rudiCode = rudiCode.substring(0, rudiCode.length() - 3) + ", " + Integer.toString(this.timeout) + ")" ;
	 
	  return RudiFileGenerator.formattedLine(rudiCode, 0, 0, 1);
  }
  
}
