package compiler;

public interface StringConstants {
	
	public static final String FUNCTIONS = "\n" + 
			"void set_inactive(Supernode m) {\n" + 
			"\n" + 
			"	for (String x : m.simple_children) {removeTimeout(x);}\n" + 
			"	for (Supernode y : m.super_children) {set_inactive(y);}\n" + 
			"	\n" + 
			"	m.active = false;\n" + 
			"	m.simple_children = {};\n" + 
			"	m.imminent_simple_children = {};\n" + 
			"	m.super_children = {};\n" + 
			"	m.initiated = {};\n" + 
			"}\n" + 
			"\n" + 
			"boolean test_inactive(Supernode m) {\n" + 
			" \n" + 
			"	return ((!m.super_children) && (!m.simple_children) && (!m.initiated));\n" + 
			"\n" + 
			"}\n" + 
			"\n" + 
			"void transition (String node_a, String node_b, Supernode a_parent, Supernode b_parent, boolean targetNodeIsSupernode) {\n" + 
			"\n" + 
			"	cancelTimeout(node_a);\n" + 
			"\n" + 
			"	if(a_parent.simple_children.contains(node_a)) {\n" + 
			"		a_parent.simple_children -= node_a;\n" + 
			"	}\n" + 
			"\n" + 
			"	if(targetNodeIsSupernode) {\n" + 
			"		b_parent.initiated += node_b;\n" + 
			"	} else {\n" + 
			"		b_parent.simple_children += node_b;\n" + 
			"		b_parent.imminent_simple_children += node_b;\n" + 
			"	}\n" + 
			"}\n" + 
			"\n" + 
			"void check_out_transition(String a, String b, Supernode a_parent, Supernode b_parent) {\n" + 
			"\n" + 
			"	if (!hasActiveTimeout(a) && a_parent.simple_children.contains(a)) { \n" + 
			"	\n" + 
			"		transition(a, b, a_parent, b_parent, false);\n" + 
			"	}\n" + 
			"}\n" + 
			"\n" + 
			"void timeout_transition(String node_a, String node_b, Supernode a_parent, Supernode b_parent, boolean targetNodeIsSupernode, int duration) {\n" + 
			"\n" + 
			"	timeout(node_a, duration) {\n" + 
			"\n" + 
			"		transition(node_a, node_b, a_parent, b_parent, targetNodeIsSupernode);\n" + 
			"	}\n" + 
			"}\n" + 
			"\n" + 
			"void probability_transition(String node_a, String node_b, Supernode a_parent, Supernode b_parent, boolean targetNodeIsSupernode) {\n" + 
			"\n" + 
			"	propose_id = \"propose_\"+ node_a;\n" + 
			"\n" + 
			"	propose(propose_id) {					\n" + 
			"		transition(node_a, node_b, a_parent, b_parent, targetNodeIsSupernode);\n" + 
			"	}\n" + 
			"}\n" + 
			"\n" + 
			"void interruptive_transition(Supernode m, Supernode parent, String target_node, boolean targetNodeIsSupernode) {\n" + 
			"						\n" + 
			"	set_inactive(m);	\n" + 
			"	if(targetNodeIsSupernode) {\n" + 
			"		parent.initiated += target_node;\n" + 
			"	} else {\n" + 
			"		parent.simple_children += target_node;\n" + 
			"	}\n" + 
			"}\n" + 
			"";
}
