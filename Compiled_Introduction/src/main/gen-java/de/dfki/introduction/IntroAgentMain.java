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
  res = main_in_node();
  if (res != 0)
    return res - 1;
  res = main_out_node();
  if (res != 0)
    return res - 1;
  res = excuse_node();
  if (res != 0)
    return res - 1;
  res = bye_node();
  if (res != 0)
    return res - 1;
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
    newTimeout("globalStart", 1600, new Proposal() {
                 public void run()
                 {
                   ((Set<Object>)global.getValue("<cat:simple_children>")).add("main_in");
                   global.setValue("<cat:nice>", 0);
                   global.setValue("<cat:active>", true);
                 }
               });
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
  m.setValue("<cat:active>", false);
  m.setValue("<cat:simple_children>", new LinkedHashSet<>());
  m.setValue("<cat:super_children>", new LinkedHashSet<>());
  m.setValue("<cat:initiated>", new LinkedHashSet<>());
}

public boolean test_inactive(Rdf m)
{
  return (!(m != null && exists(((Set<Object>)m.getValue("<cat:super_children>"))))) && (!(m != null && exists(((Set<Object>)m.getValue("<cat:simple_children>"))))) && (!(m != null && exists(((Set<Object>)m.getValue("<cat:initiated>")))));
}

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
  if (a_parent != null && exists(((Set<Object>)a_parent.getValue("<cat:simple_children>"))) && ((Set<Object>)a_parent.getValue("<cat:simple_children>")).contains(node_a)) {
    ((Set<Object>)a_parent.getValue("<cat:simple_children>")).remove(node_a);
  }

  propose(propose_id, new Proposal() {
            public void run()
            {
              transition(node_a, node_b, a_parent, b_parent);
            }
          });
}

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

public int main_in_node()
{
  boolean[] __x1 = new boolean[2];
  __x1[0]        = (__x1[1] = global != null && exists(((Set<Object>)global.getValue("<cat:simple_children>"))) && ((Set<Object>)global.getValue("<cat:simple_children>")).contains("main_in"));
  logRule(1, __x1);
main_in_node:
  if (__x1[0]) {
    super_transition("main_in", global, "hello_node");
  }

  return 0;
}

public int main_out_node()
{
  boolean[] __x2 = new boolean[2];
  __x2[0]        = (__x2[1] = global != null && exists(((Set<Object>)global.getValue("<cat:simple_children>"))) && ((Set<Object>)global.getValue("<cat:simple_children>")).contains("main_out"));
  logRule(2, __x2);
main_out_node:
  if (__x2[0]) {
    set_inactive(global);
    shutdown();
  }

  return 0;
}

public int excuse_node()
{
  boolean[] __x3 = new boolean[2];
  __x3[0]        = (__x3[1] = global != null && exists(((Set<Object>)global.getValue("<cat:simple_children>"))) && ((Set<Object>)global.getValue("<cat:simple_children>")).contains("excuse_node"));
  logRule(3, __x3);
excuse_node:
  if (__x3[0]) {
    emitDA(new DialogueAct("InitialGoodbye", "Leave"));
    lastDAprocessed();
    transition("excuse_node", "bye_node", global, global);
  }

  return 0;
}

public int bye_node()
{
  boolean[] __x4 = new boolean[2];
  __x4[0]        = (__x4[1] = global != null && exists(((Set<Object>)global.getValue("<cat:simple_children>"))) && ((Set<Object>)global.getValue("<cat:simple_children>")).contains("bye_node"));
  logRule(4, __x4);
bye_node:
  if (__x4[0]) {
    emitDA(new DialogueAct("Valediction", "Bye"));
    lastDAprocessed();
    check_out_transition("bye_node", "main_out", global);
  }

  return 0;
}
}
