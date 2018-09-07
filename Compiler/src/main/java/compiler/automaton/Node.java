package compiler.automaton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import compiler.edges.ConditionalEdge;
import compiler.edges.Edge;
import compiler.edges.InterruptiveEdge;
import compiler.edges.TimeoutEdge;

/**
 * A scenemaker node.
 * @author Max Depenbrock
 */
public class Node {
	
	/**
	 * The name of the {@code Node}.
	 */
	private String name;
	/**
	 * The parent {@code Supernode} of the {@code Node}.
	 */
	private Supernode parent;
	/**
	 * The code that is executed when reaching the {@code Node}.
	 */
	private String code;
	/**
	 * A set of variables defined in the {@code Node}.}
	*/
	private Set<Variable> variables;
	/**
	 * A set of outgoing edges.
	 */
	private Set<Edge> outgoingEdges;
	/**
	 * A boolean indicating if the {@code Node} is a {@code Supernode}.
	 */
	private boolean isSupernode;
	  
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the parent
	 */
	public Supernode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Supernode parent) {
		this.parent = parent;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return the variables
	 */
	public Set<Variable> getVariables() {
		return variables;
	}
	
	/**
	 * @param variables the variables to set
	 */
	public void setVariables(Set<Variable> variables) {
		this.variables = variables;
	}
	
	/**
	 * @return the outgoingEdges
	 */
	public Set<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}
	
	/**
	 * @param outgoingEdges the outgoingEdges to set
	 */
	public void setOutgoingEdges(Set<Edge> outgoingEdges) {
		this.outgoingEdges = outgoingEdges;
	}
	
	/**
	 * @return the isSupernode
	 */
	public boolean isSupernode() {
		return isSupernode;
	}
	
	/**
	 * @param isSupernode the isSupernode to set
	 */
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
	
	/**
	 * Creates the rudi-code fragment that imitates the functionality of the {@code Node}.
	 * @return the rudi-code that imitates the functionality of the {@code Node} as a String.
	 */
	public String getNodeCode() {

	  String outString = "";
	  boolean canDieHere = true;
	  int index = 0;
	  
	  for (Edge e : this.outgoingEdges) {
		  if (e instanceof InterruptiveEdge) {
			  index += 1;
			  outString +=  this.name + "_interruptive_edge_" + Integer.toString(index) + ":\n";
			  outString += e.getRudiCode();
		  }
	  }
	  
	  outString += this.name + ":\n";
	  
	  outString += "\tif("+ this.parent.getName() + ".simple_children.contains(\"" + this.name + "\")) {\n\n";
	  
	  if (!this.code.isEmpty()) {
		  
		  outString += "\t\tif("+ this.parent.getName() + ".imminent_simple_children.contains(\"" + this.name + "\")) {\n\n";
		  outString += this.convertCodeToRudi();
		  outString += "\t\t}\n\n";
	  }
	  
	  for (Edge e : this.outgoingEdges) {
		  if (e instanceof TimeoutEdge) {
			  outString += e.getRudiCode() + "\n";
			  canDieHere = false;
		  }
	  }
	  
	  for (Edge e : this.outgoingEdges) {
		  if (!(e instanceof TimeoutEdge) && !(e instanceof ConditionalEdge) && !(e instanceof InterruptiveEdge)) {
			  outString += e.getRudiCode();
			  canDieHere = false;
		  }
	  }
	  
	  if(canDieHere) {
		  outString += "\n\t\tcheck_out_transition(\"" + this.name + "\", \"" + this.parent.getName() + "_out\", " + this.parent.getName() + ", " + this.parent.getName() + ");\n";			  		  
	  }
	  
	  outString += "\t}\n\n";
	  return outString;
  }
  
	/**
	 * Changes the name of the given {@code Variable} to rudi syntax.
	 * @param varName The name of the {@code Variable} that is to be replaced in the code.
	 * @return String The variable call in rudi syntax.
	 */
	public String replaceVarName(String varName) {
	  
	  for (Variable v : this.variables) {
			if (v.getName().equals(varName)) {
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
  
	/**
	 * Converts the code that is executed when reaching the {@code Node} into rudi syntax.
	 * @return String the code that is executed when reaching the {@code Node} in rudi syntax.
	 */
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
