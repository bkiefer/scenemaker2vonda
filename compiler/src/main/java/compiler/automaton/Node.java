package compiler.automaton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import compiler.RudiFileGenerator;
import compiler.edges.Edge;


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
   * A set of variables defined in the {@code Node}.
   */
  private Set<Variable> variables;
  
  /**
   * The set of all outgoing edges at this {@code Node}.
   */
  private Set<Edge> outgoingEdges;
  
  /**
   * The set of outgoing epsilon edges (empty or a single member).
   */
  private Set<Edge> epsilonEdges;
  
  /**
   * The set of outgoing fork edges.
   */
  private Set<Edge> forkEdges;
  
  /**
   * The set of outgoing probability edges.
   */
  private Set<Edge> probabilityEdges;
  
  /**
   * The set of outgoing timeout edges (empty or a single member).
   */
  private Set<Edge> timeoutEdges;
  
  /**
   * The set of outgoing (non-interruptive) conditional edges.
   */
  private Set<Edge> conditionalEdges;
  
  /**
   * The set of outgoing interruptive edges.
   */
  private Set<Edge> interruptiveEdges;

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
   * @return all outgoing edges
   */
  public Set<Edge> getOutgoingEdges() {
	  return outgoingEdges;
  }
	
  /**
   * @param outgoingEdges the set of outgoing edges that should be attached to this node.
   */
  public void setOutgoingEdges(Set<Edge> outgoingEdges) {
	this.outgoingEdges = outgoingEdges;
  }
  
  /**
   * @return all outgoing epsilon edges (max 1)
   */
  public Set<Edge> getEpsilonEdges() {
		return epsilonEdges;
  }
  
  /**
   * @return all outgoing fork edges
   */
  public Set<Edge> getForkEdges() {
	return forkEdges;
  }
  
  /**
   * @return all outgoing probability edges
   */
  public Set<Edge> getProbabilityEdges() {
		return probabilityEdges;
  }
  
  /**
   * @return all outgoing timeout edges (max 1)
   */
  public Set<Edge> getTimeoutEdges() {
	return timeoutEdges;
  }
  
  /**
   * @return all outgoing (non-interruptive) conditional edges
   */
  public Set<Edge> getConditionalEdges() {
		return conditionalEdges;
  }
  
  /**
   * @return all outgoing interruptive edges
   */
  public Set<Edge> getInterruptiveEdges() {
	return interruptiveEdges;
  }
	
  /**
   * @return A boolean indicating whether the {@code Node} is a {@code Supernode}
   */
  public boolean isSupernode() {
	  return isSupernode;
  }

  /**
   * @param isSupernode A boolean indicating whether this {@code Node} should be set as a {@code Supernode}
   */
  public void setSupernode(boolean isSupernode) {
	  this.isSupernode = isSupernode;
  }
  
  /**
   * @return A boolean indicating if a process can die at this {@Node} (if it has only conditional/interruptive outgoing edges, or none at all)
   */
  public boolean processCanDieHere() {
	  return ((this.getEpsilonEdges().isEmpty()) && (this.getForkEdges().isEmpty()) && (this.getProbabilityEdges().isEmpty()) && (this.getTimeoutEdges().isEmpty()));
  }
  
  /**
   * @param edges A set of edges
   * @return A string containing the VOnDA-code fragment that imitates the functionality of the set of edges
   */
  public String getEdgeCode(Set<Edge> edges) {
	  String outString = "";
	  for (Edge e : edges) {
		  outString += e.getRudiCode(2);
	  }
	  return outString;
  }
  
  /**
   * This method should only be called on a simple {@code Node} (not on a {@code Supernode}) for proper functionality.
   * For Supernodes there exists a corresponding method {@code getSuperInterruptiveEdgesCode}.
   * @param interruptiveEdges A set of interruptive edges
   * @param numLeadingTabs Integer indicating how indented (number of tabs) the code should be 
   * @return A String containing the VOnDA-code fragment that imitates the functionality of the set of interruptive edges attached to this node
   */
  public String getInterruptiveEdgesCode(Set<Edge> interruptiveEdges, int numLeadingTabs) {
	  String outString = "";
	  int index = 0;
	  for (Edge e : interruptiveEdges) {
		  index += 1;
		  outString += RudiFileGenerator.formattedRuleLabel(this.getName() + "_interruptive_edge_" + Integer.toString(index), 0, numLeadingTabs, 1);			  
		  outString += e.getRudiCode(numLeadingTabs+1);
	  }
	  
	  return outString;
  }

  /**
   * Creates a new {@code Node}.
   */
  public Node() {
	  this.outgoingEdges = new HashSet<>();
	  this.epsilonEdges = new HashSet<>();
	  this.forkEdges = new HashSet<>();
	  this.probabilityEdges = new HashSet<>();
	  this.timeoutEdges = new HashSet<>();
	  this.conditionalEdges = new HashSet<>();
	  this.interruptiveEdges = new HashSet<>();
	  this.variables = new HashSet<>();
  }
	
	/**
	 * Creates the VOnDA-code fragment that imitates the functionality of the {@code Node}.
	 * @return A String containing the VOnDA-code that imitates the functionality of the {@code Node} 
	 */
	public String getNodeCode() {
	  
	  String outString = RudiFileGenerator.formattedRuleLabel(this.name, 0, 0, 0);
	  outString += RudiFileGenerator.formattedIfOpening(this.parent.getName() + ".simple_children.contains(\"" + this.name + "\")", 1, 1, 2);
	  
	  outString += this.getInterruptiveEdgesCode(this.getInterruptiveEdges(), 2);
	  
	  if (!this.code.isEmpty()) {
		  
		  outString += RudiFileGenerator.formattedIfOpening(this.parent.getName() + ".imminent_simple_children.contains(\"" + this.name + "\")", 0, 2, 2);
		  outString += this.convertCodeToRudi();
		  outString += RudiFileGenerator.formattedIfClosing(0, 2, 2);		  
	  }
	  
	  outString += this.getEdgeCode(this.getTimeoutEdges());
	  outString += RudiFileGenerator.formattedLine(this.parent.getName() + ".imminent_simple_children -= \"" + this.name + "\"", 1, 2, 2);
	  outString += this.getEdgeCode(this.getConditionalEdges());
	  outString += this.getEdgeCode(this.getEpsilonEdges());
	  outString += this.getEdgeCode(this.getForkEdges());
	  outString += this.getEdgeCode(this.getProbabilityEdges());
	  
	  if(this.processCanDieHere()) {
		  outString +=  RudiFileGenerator.formattedLine("check_out_transition(\"" + this.name + "\", \"" + this.parent.getName() + "_out\", " + this.parent.getName() + ", " + this.parent.getName() + ")", 1, 2, 1);			  		  
	  }
	  
	  outString += RudiFileGenerator.formattedIfClosing(0, 1, 2);		  	  

	  return outString;
  }
  
	/**
	 * Changes the name of the given {@code Variable} such that it is a field of the correct Supernode object.
	 * If no {@code Supernode} of which the variable is a field can be found, the name is left unchanged.
	 * @param varName The name of the {@code Variable} that is to be replaced in the code.
	 * @return A String containing the variable call in VOnDA syntax, as a field of the appropriate Supernode object
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
 * Converts the code that is executed when reaching the {@code Node} into VOnDA syntax, 
 * replacing variables with fields of the appropriate Supernodes.
 * @return A String containing the code that is executed when reaching the {@code Node} in VOnDA syntax
 */
  public String convertCodeToRudi() {
	    
	  BufferedReader bufReader = new BufferedReader(new StringReader(this.code));
	  String rudiCode = "";
	  
	  String line = null;
	  try {
		while( (line=bufReader.readLine()) != null )
		  {
			rudiCode += "\t\t\t" + RudiFileGenerator.replaceVarNamesInString(line, this) + "\n";
	  }
	
	  } catch (IOException e) {
		  e.printStackTrace();
	  }					
	  
	  return rudiCode;
  }
}
