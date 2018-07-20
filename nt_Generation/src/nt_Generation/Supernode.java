package nt_Generation;
import java.util.Set;

public class Supernode extends Node {
	
	public Set<Node> nodes;
	public Set<Variable> variables;
	public Set<Node> start_nodes;
	public String parent_name = "";
}
