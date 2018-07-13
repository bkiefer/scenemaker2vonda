package de.dfki.introduction;
import java.util.*;

import de.dfki.mlt.rudimant.agent.DialogueAct;
import de.dfki.lt.hfc.db.rdfProxy.*;
import de.dfki.lt.hfc.types.*;
import de.dfki.introduction.ChatAgent;
public class IntroAgentMain extends de.dfki.introduction.ChatAgent {
public IntroAgentMain()
{
  super();
}

/*START_PROCESS*/ public int process()
{
  int res = 0;
  res = setup_main();
  if (res != 0)
    return res - 1;
  if (global != null && exists(((Set<Object>)global.getValue("<cat:simple_children>"))) && ((Set<Object>)global.getValue("<cat:simple_children>")).contains("main_in")) {
    super_transition("main_in", global, "hello_node");
  }

  if (global != null && exists(((Set<Object>)global.getValue("<cat:simple_children>"))) && ((Set<Object>)global.getValue("<cat:simple_children>")).contains("main_out")) {
    set_inactive(global);
    clearBehavioursAndProposals();
    shutdown();
  }

  if (global != null && exists(((Set<Object>)global.getValue("<cat:simple_children>"))) && ((Set<Object>)global.getValue("<cat:simple_children>")).contains("bye_node")) {
    emitDA(new DialogueAct("Valediction", "Bye"));
    check_out_transition("bye_node", "main_out", global);
  }

  res = new de.dfki.introduction.Hello_node(this).process();
  if (res < 0)
    return res - 1;
  return 0;
}

/*END_PROCESS*/ public int setup_main()
{
  boolean[] __x0 = new boolean[2];
  __x0[0]        = !((__x0[1] = global != null && ((Boolean)global.getSingleValue("<cat:active>"))));
  logRule(0, __x0);
setup_main:
  if (__x0[0]) {
    global.setValue("<cat:active>", true);
    ((Set<Object>)global.getValue("<cat:simple_children>")).add("main_in"); // var_nice = new Variable;
// var_nice.name = "nice";
// var_nice.valueInt = 0;
// myBindings.variables += var_nice;

    global.setValue("<cat:nice>", 0);
  }

  return 0;
}

public void set_inactive(Rdf m)
{
  for (Object x_outer : ((Set<Object>)m.getValue("<cat:simple_children>"))) {
    String x = (String)x_outer;
    {
      removeTimeout(x);
    }
  }
  for (Object y_outer : ((Set<Object>)m.getValue("<cat:super_children>"))) {
    Rdf y = (Rdf)y_outer;
    {
      set_inactive(y);
    }
  }
  m.clearValue("<cat:simple_children>");
  m.clearValue("<cat:super_children>");
  m.clearValue("<cat:initiated>");
  m.setValue("<cat:active>", false);
}

public boolean test_inactive(Rdf m)
{
  return (!(m != null && exists(((Set<Object>)m.getValue("<cat:super_children>"))))) && (!(m != null && exists(((Set<Object>)m.getValue("<cat:simple_children>"))))) && (!(m != null && exists(((Set<Object>)m.getValue("<cat:initiated>")))));
}

// Variable getVariable(Supernode m, String var_name) {
//
//	for (Variable x: m.variables) {
//		if (x.name == var_name) {
//			return x;
//		}
//	}
//
//	if(!m.parent) {
//		return none;
//	}
//
//	else {
//		return getVariable(m.parent, var_name);
//	}
// }

public void super_transition(String node_a, Rdf a_parent, String supernode_b)
{
  cancelTimeout(node_a);
// Im Falle von Fork Edges wird der Knoten ja vielleicht mehrfach "verlassen"

  if (a_parent != null && exists(((Set<Object>)a_parent.getValue("<cat:simple_children>"))) && ((Set<Object>)a_parent.getValue("<cat:simple_children>")).contains(node_a)) {
    ((Set<Object>)a_parent.getValue("<cat:simple_children>")).remove(node_a);
  }

  ((Set<Object>)a_parent.getValue("<cat:initiated>")).add(supernode_b);
}

public void transition(String node_a, String node_b, Rdf a_parent, Rdf b_parent)
{
  cancelTimeout(node_a);
// Im Falle von Fork Edges wird der Knoten ja vielleicht mehrfach "verlassen", deshalb löschen wir nur, wenn es das erste Mal ist

  if (a_parent != null && exists(((Set<Object>)a_parent.getValue("<cat:simple_children>"))) && ((Set<Object>)a_parent.getValue("<cat:simple_children>")).contains(node_a)) {
    ((Set<Object>)a_parent.getValue("<cat:simple_children>")).remove(node_a);
  }

  ((Set<Object>)b_parent.getValue("<cat:simple_children>")).add(node_b);
}

public void check_out_transition(String node, String out_node, Rdf parent)
{
  if (!(hasActiveTimeout(node)) && parent != null && exists(((Set<Object>)parent.getValue("<cat:simple_children>"))) && ((Set<Object>)parent.getValue("<cat:simple_children>")).contains(node)) {
    transition(node, out_node, parent, parent);
  }
}

public void timeout_transition(String node_a, String node_b, Rdf a_parent, Rdf b_parent, int duration)
{
  newTimeout(node_a, duration, new Proposal() {
               public void run()
               {
                 transition(node_a, node_b, a_parent, b_parent);
               }
             });
}

public void probability_transition(String node_a, String node_b, Rdf a_parent, Rdf b_parent)
{
  String propose_id = ("propose_"+node_a);
  propose(propose_id, new Proposal() {
            public void run()
            {
              transition(node_a, node_b, a_parent, b_parent);
            }
          });
}

// TODO: Wie kann man die condition in VonDa evaluieren???
// void conditional_transition(String node_a, String node_b, Supernode a_parent, Supernode b_parent, String condition) {
//
//	if(eval(condition) {
//		transition(node_a, node_b, a_parent, b_parent);
//	}
// }

public void interruptive_transition(Rdf m, Rdf parent, String target_node)
{
  set_inactive(m);
  ((Set<Object>)parent.getValue("<cat:simple_children>")).add(target_node);
}

public void interruptive_super_transition(Rdf m, Rdf parent, String target_supernode)
{
  set_inactive(m);
  ((Set<Object>)parent.getValue("<cat:initiated>")).add(target_supernode);
}
}
