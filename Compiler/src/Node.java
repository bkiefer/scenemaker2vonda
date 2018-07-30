
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
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
   * The parent {@code Supernode} of the {@code Node}.
   */
  public Supernode parent;
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

  public String getNodeCode() {
	  
	  String outString = this.name + ":\n";
	  outString += "\tif("+ this.parent.name + ".simple_children.contains(\"" + this.name + "\")) {\n\n";
	  outString += "\t\tif(!hasActiveTimeout("+ this.name + ")) {\n\n";

	  outString += this.convertCodeToRudi() + "\n";
	  
	  outString += "\t\t}\n\n";

	  for (Edge e : this.outgoingEdges) {
		  
		  outString += e.getRudiCode();
	  }
	  
	  outString += "\n\t\tcheck_out_transition(\"" + this.name + "\", \"" + this.parent.name + "_out\", " + this.parent.name + ", " + this.parent.name + ");\n";			  

	  outString += "\t}\n\n";

	  
	  return outString;
  }
  
  public String convertCodeToRudi() {
	  
	  
	  BufferedReader bufReader = new BufferedReader(new StringReader(this.code));
	  String rudiCode = "\t\t\t" + this.code + "\n";
	  
	  String line = null;
	  try {
		while( (line=bufReader.readLine()) != null )
		  {
			rudiCode += "\t\t\t" + line + "\n";
		  }
	} catch (IOException e) {
		e.printStackTrace();
	}
	  
	  return rudiCode;
	  
  }
  
}
