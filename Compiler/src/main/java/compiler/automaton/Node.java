package compiler.automaton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import compiler.RudiFileGenerator;
import compiler.edges.*;

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
  private Set<Edge> epsilonEdges;
  private Set<Edge> forkEdges;
  private Set<Edge> probabilityEdges;
  private Set<Edge> timeoutEdges;
  private Set<Edge> conditionalEdges;
  private Set<Edge> interruptiveEdges;

  private boolean isSupernode;
  
  public String getName() {
	  return name;
  }
	
  public void setName(String name) {
	  this.name = name;
  }
	
  public Supernode getParent() {
	  return parent;
  }
	
  public void setParent(Supernode parent) {
	  this.parent = parent;
  }
	
  public String getCode() {
	return code;
  }
	
  public void setCode(String code) {
	this.code = code;
  }
	
  public Set<Variable> getVariables() {
	return variables;
  }
	
  public void setVariables(Set<Variable> variables) {
	this.variables = variables;
  }
	
  public Set<Edge> getOutgoingEdges() {
	return outgoingEdges;
  }
	
  public void setOutgoingEdges(Set<Edge> outgoingEdges) {
	this.outgoingEdges = outgoingEdges;
  }
  
  public Set<Edge> getEpsilonEdges() {
		return epsilonEdges;
  }
  
  public Set<Edge> getForkEdges() {
	return forkEdges;
  }
  
  public Set<Edge> getProbabilityEdges() {
		return probabilityEdges;
  }
  
  public Set<Edge> getTimeoutEdges() {
	return timeoutEdges;
  }
  
  public Set<Edge> getConditionalEdges() {
		return conditionalEdges;
  }
  
  public Set<Edge> getInterruptiveEdges() {
	return interruptiveEdges;
  }
	
	
  /**
   * Whether the {@code Node} is a {@code Supernode}.
   */

  public boolean isSupernode() {
	  return isSupernode;
  }

  public void setSupernode(boolean isSupernode) {
	  this.isSupernode = isSupernode;
  }
  
  public boolean processCanDieHere() {
	  return ((this.getEpsilonEdges().isEmpty()) && (this.getForkEdges().isEmpty()) && (this.getProbabilityEdges().isEmpty()) && (this.getTimeoutEdges().isEmpty()));
  }
  
  public String getEdgeCode(Set<Edge> edges) {
	  String outString = "";
	  for (Edge e : edges) {
		  outString += e.getRudiCode(2);
	  }
	  return outString;
  }
  
  public String getInterruptiveEdgesCode(Set<Edge> interruptiveEdges, int numLeadingTabs) {
	  String outString = "";
	  int index = 0;
	  for (Edge e : interruptiveEdges) {
		  index += 1;
		  outString += RudiFileGenerator.formattedRuleLabel(this.getName() + "_interruptive_edge_" + Integer.toString(index), 0, 0, 1);			  
		  outString += e.getRudiCode(numLeadingTabs);
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

  public String getNodeCode() {

	  String outString = this.getInterruptiveEdgesCode(this.getInterruptiveEdges(), 1);
	  
	  outString += RudiFileGenerator.formattedRuleLabel(this.name, 0, 0, 0);
	  outString += RudiFileGenerator.formattedIfOpening(this.parent.getName() + ".simple_children.contains(\"" + this.name + "\")", 1, 1, 2);
	  
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
