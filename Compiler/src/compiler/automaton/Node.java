package compiler.automaton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edges.ConditionalEdge;
import edges.Edge;
import edges.ProbabilityEdge;
import edges.TimeoutEdge;

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
  private boolean isSupernode;

  public boolean isSupernode() {
	return isSupernode;
}

public void setSupernode(boolean isSupernode) {
	this.isSupernode = isSupernode;
}

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
	  
	  if (!this.code.isEmpty()) {
		  
		  outString += "\t\tif("+ this.parent.name + ".imminent_simple_children.contains(\"" + this.name + "\")) {\n\n";
		  outString += this.convertCodeToRudi();
		  outString += "\t\t}\n\n";
	  }
	  
	  for (Edge e : this.outgoingEdges) {
		  if (e instanceof TimeoutEdge) {
			  outString += e.getRudiCode() + "\n";
		  }
	  }
	  
	  for (Edge e : this.outgoingEdges) {
		  if (e instanceof ProbabilityEdge) {
			  outString += e.getRudiCode() + "\n";
		  }
	  }
	  
	  outString += "\t\t" + this.parent.name + ".imminent_simple_children -= \"" + this.name + "\";\n\n";

	  	  
	  for (Edge e : this.outgoingEdges) {
		  if (e instanceof ConditionalEdge) {
			  outString += e.getRudiCode();
		  }
	  }
	  
	  for (Edge e : this.outgoingEdges) {
		  if (!(e instanceof TimeoutEdge) && !(e instanceof ConditionalEdge)) {
			  outString += e.getRudiCode();
		  }
	  }
	  
	  outString += "\n\t\tcheck_out_transition(\"" + this.name + "\", \"" + this.parent.name + "_out\", " + this.parent.name + ", " + this.parent.name + ");\n";			  
	  outString += "\t}\n\n";

	  return outString;
  }
  
  public String replaceVarName(String varName) {
	  
	  for (Variable v : this.variables) {
			if (v.name.equals(varName)) {
				return this.name + "." + varName;
			}
	  }
	  
	  if (this.parent != null) {
		  return this.parent.replaceVarName(varName);
	  }
	  
	  else {
		  return varName;
	  }
	  
  }
  
  public String convertCodeToRudi() {
	    
	  BufferedReader bufReader = new BufferedReader(new StringReader(this.code));
	  String rudiCode = "";
	  
	  String line = null;
	  try {
		while( (line=bufReader.readLine()) != null )
		  {
			String cleanedLine = line;
			Pattern VAR_TAG_PATTERN = Pattern.compile("<v>(.*?)</v>");
			Matcher m = VAR_TAG_PATTERN.matcher(cleanedLine);
			
			while (m.find()) {
				String varName = m.group(1);			
				String extendedVarName = this.replaceVarName(varName);
				String stringToReplace = "<v>" + varName + "</v>";
				
				cleanedLine = cleanedLine.replace(stringToReplace, extendedVarName);
				m = VAR_TAG_PATTERN.matcher(cleanedLine);
			}
			
			rudiCode += "\t\t\t" + cleanedLine + "\n";
		  }
	
	} catch (IOException e) {
		e.printStackTrace();
	}
	  
	  return rudiCode;
  }
  
}
