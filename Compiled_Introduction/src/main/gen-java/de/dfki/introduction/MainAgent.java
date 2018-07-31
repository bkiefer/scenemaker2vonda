package de.dfki.introduction;
import java.util.*;

import de.dfki.mlt.rudimant.agent.DialogueAct;
import de.dfki.lt.hfc.db.rdfProxy.*;
import de.dfki.lt.hfc.types.*;
import de.dfki.introduction.ChatAgent;
public class MainAgent extends de.dfki.introduction.ChatAgent {
public MainAgent()
{
  super();
}

/*START_PROCESS*/ public int process()
{
  int res = 0;
  res = setup_mainAgent();
  if (res != 0)
    return res - 1;
  res = mainAgent_in();
  if (res != 0)
    return res - 1;
  res = mainAgent_out();
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

/*END_PROCESS*/ public void set_inactive(Rdf m)
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
  if (a_parent != null && exists(((Set<Object>)a_parent.getValue("<cat:simple_children>"))) && ((Set<Object>)a_parent.getValue("<cat:simple_children>")).contains(node_a)) {
    ((Set<Object>)a_parent.getValue("<cat:simple_children>")).remove(node_a);
  }

  ((Set<Object>)a_parent.getValue("<cat:initiated>")).add(supernode_b);
}

public void transition(String node_a, String node_b, Rdf a_parent, Rdf b_parent)
{
  cancelTimeout(node_a);
  if (a_parent != null && exists(((Set<Object>)a_parent.getValue("<cat:simple_children>"))) && ((Set<Object>)a_parent.getValue("<cat:simple_children>")).contains(node_a)) {
    ((Set<Object>)a_parent.getValue("<cat:simple_children>")).remove(node_a);
  }

  ((Set<Object>)b_parent.getValue("<cat:simple_children>")).add(node_b);
}

public void check_out_transition(String a, String b, Rdf a_parent, Rdf b_parent)
{
  if (!(hasActiveTimeout(a)) && a_parent != null && exists(((Set<Object>)a_parent.getValue("<cat:simple_children>"))) && ((Set<Object>)a_parent.getValue("<cat:simple_children>")).contains(a)) {
    transition(a, b, a_parent, b_parent);
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

public void timeout_super_transition(String node_a, Rdf a_parent, String supernode_b, int duration)
{
  newTimeout(node_a, duration, new Proposal() {
               public void run()
               {
                 super_transition(node_a, a_parent, supernode_b);
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

public void probability_super_transition(String node_a, Rdf a_parent, String node_b)
{
  String propose_id = ("propose_"+node_a);
  if (a_parent != null && exists(((Set<Object>)a_parent.getValue("<cat:simple_children>"))) && ((Set<Object>)a_parent.getValue("<cat:simple_children>")).contains(node_a)) {
    ((Set<Object>)a_parent.getValue("<cat:simple_children>")).remove(node_a);
  }

  propose(propose_id, new Proposal() {
            public void run()
            {
              super_transition(node_a, a_parent, node_b);
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

public int setup_mainAgent()
{
  boolean[] __x0 = new boolean[2];
  __x0[0]        = !((__x0[1] = mainAgent != null && ((Boolean)mainAgent.getSingleValue("<cat:active>"))));
  logRule(0, __x0);
setup_mainAgent:
  if (__x0[0]) {
    newTimeout("MainAgentStart", 1400, new Proposal() {
                 public void run()
                 {
                   mainAgent.setValue("<cat:active>", true);
                   ((Set<Object>)mainAgent.getValue("<cat:simple_children>")).add("mainAgent_in");
                   mainAgent.setValue("<cat:nice>", 0);
                 }
               });
  }

  return 0;
}

public int mainAgent_in()
{
  boolean[] __x1 = new boolean[2];
  __x1[0]        = (__x1[1] = mainAgent != null && exists(((Set<Object>)mainAgent.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.getValue("<cat:simple_children>")).contains("mainAgent_in"));
  logRule(1, __x1);
mainAgent_in:
  if (__x1[0]) {
    super_transition("mainAgent_in", mainAgent, "hello_node");
    check_out_transition("mainAgent_in", "mainAgent_out", mainAgent, mainAgent);
  }

  return 0;
}

public int mainAgent_out()
{
  boolean[] __x2 = new boolean[2];
  __x2[0]        = (__x2[1] = mainAgent != null && exists(((Set<Object>)mainAgent.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.getValue("<cat:simple_children>")).contains("mainAgent_out"));
  logRule(2, __x2);
mainAgent_out:
  if (__x2[0]) {
    set_inactive(mainAgent);
    shutdown();
  }

  return 0;
}

public int excuse_node()
{
  boolean[] __x3 = new boolean[2];
  __x3[0]        = (__x3[1] = mainAgent != null && exists(((Set<Object>)mainAgent.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.getValue("<cat:simple_children>")).contains("excuse_node"));
  logRule(3, __x3);
excuse_node:
  if (__x3[0]) {
    if (!(hasActiveTimeout("excuse_node"))) {
      emitDA(new DialogueAct("InitialGoodbye", "Leave"));
      lastDAprocessed();
    }

    transition("excuse_node", "bye_node", mainAgent, mainAgent);
    check_out_transition("excuse_node", "mainAgent_out", mainAgent, mainAgent);
  }

  return 0;
}

public int bye_node()
{
  boolean[] __x4 = new boolean[2];
  __x4[0]        = (__x4[1] = mainAgent != null && exists(((Set<Object>)mainAgent.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.getValue("<cat:simple_children>")).contains("bye_node"));
  logRule(4, __x4);
bye_node:
  if (__x4[0]) {
    if (!(hasActiveTimeout("bye_node"))) {
      emitDA(new DialogueAct("Valediction", "Bye"));
    }

    check_out_transition("bye_node", "mainAgent_out", mainAgent, mainAgent);
  }

  return 0;
}
}
