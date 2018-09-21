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
  res = n4();
  if (res != 0)
    return res - 1;
  res = n1();
  if (res != 0)
    return res - 1;
  res = n5();
  if (res != 0)
    return res - 1;
  res = n3();
  if (res != 0)
    return res - 1;
  res = n2();
  if (res != 0)
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
  m.setValue("<cat:imminent_simple_children>", new LinkedHashSet<>());
  m.setValue("<cat:super_children>", new LinkedHashSet<>());
  m.setValue("<cat:initiated>", new LinkedHashSet<>());
}

public boolean test_inactive(Rdf m)
{
  return (!(m != null && exists(((Set<Object>)m.getValue("<cat:super_children>"))))) && (!(m != null && exists(((Set<Object>)m.getValue("<cat:simple_children>"))))) && (!(m != null && exists(((Set<Object>)m.getValue("<cat:initiated>")))));
}

public void transition(String node_a, String node_b, Rdf a_parent, Rdf b_parent, boolean targetNodeIsSupernode)
{
  cancelTimeout(node_a);
  if (a_parent != null && exists(((Set<Object>)a_parent.getValue("<cat:simple_children>"))) && ((Set<Object>)a_parent.getValue("<cat:simple_children>")).contains(node_a)) {
    ((Set<Object>)a_parent.getValue("<cat:simple_children>")).remove(node_a);
  }

  if (targetNodeIsSupernode) {
    ((Set<Object>)b_parent.getValue("<cat:initiated>")).add(node_b);
  } else {
    ((Set<Object>)b_parent.getValue("<cat:simple_children>")).add(node_b);
    ((Set<Object>)b_parent.getValue("<cat:imminent_simple_children>")).add(node_b);
  }
}

public void check_out_transition(String a, String b, Rdf a_parent, Rdf b_parent)
{
  if (!(hasActiveTimeout(a)) && a_parent != null && exists(((Set<Object>)a_parent.getValue("<cat:simple_children>"))) && ((Set<Object>)a_parent.getValue("<cat:simple_children>")).contains(a)) {
    transition(a, b, a_parent, b_parent, false);
  }
}

public void timeout_transition(String node_a, String node_b, Rdf a_parent, Rdf b_parent, boolean targetNodeIsSupernode, int duration)
{
  newTimeout(node_a, duration, new Proposal() {
               public void run()
               {
                 transition(node_a, node_b, a_parent, b_parent, targetNodeIsSupernode);
               }
             });
}

public void probability_transition(String node_a, String node_b, Rdf a_parent, Rdf b_parent, boolean targetNodeIsSupernode)
{
  String propose_id = ("propose_"+node_a);
  propose(propose_id, new Proposal() {
            public void run()
            {
              transition(node_a, node_b, a_parent, b_parent, targetNodeIsSupernode);
            }
          });
}

public void interruptive_transition(Rdf m, Rdf parent, String target_node, boolean targetNodeIsSupernode)
{
  set_inactive(m);
  if (targetNodeIsSupernode) {
    ((Set<Object>)parent.getValue("<cat:initiated>")).add(target_node);
  } else {
    ((Set<Object>)parent.getValue("<cat:simple_children>")).add(target_node);
  }
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
                   ((Set<Object>)mainAgent.getValue("<cat:imminent_simple_children>")).add("mainAgent_in");
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
    ((Set<Object>)mainAgent.getValue("<cat:imminent_simple_children>")).remove("mainAgent_in");
    transition("mainAgent_in", "n1", mainAgent, mainAgent, false);
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
    if (test_inactive(mainAgent)) {
      set_inactive(mainAgent);
      shutdown();
    }
  }

  return 0;
}

public int n4()
{
  boolean[] __x3 = new boolean[2];
  __x3[0]        = (__x3[1] = mainAgent != null && exists(((Set<Object>)mainAgent.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.getValue("<cat:simple_children>")).contains("n4"));
  logRule(3, __x3);
n4:
  if (__x3[0]) {
    if (mainAgent != null && exists(((Set<Object>)mainAgent.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.getValue("<cat:imminent_simple_children>")).contains("n4")) {
      emitDA(new DialogueAct("Inform", "Mood"));
      lastDAprocessed();
    }

    ((Set<Object>)mainAgent.getValue("<cat:imminent_simple_children>")).remove("n4");
    check_out_transition("n4", "mainAgent_out", mainAgent, mainAgent);
  }

  return 0;
}

public int n1()
{
  boolean[] __x4 = new boolean[2];
  __x4[0]        = (__x4[1] = mainAgent != null && exists(((Set<Object>)mainAgent.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.getValue("<cat:simple_children>")).contains("n1"));
  logRule(4, __x4);
n1:
  if (__x4[0]) {
    ((Set<Object>)mainAgent.getValue("<cat:imminent_simple_children>")).remove("n1");
    transition("n1", "n2", mainAgent, mainAgent, false);
    transition("n1", "n3", mainAgent, mainAgent, false);
  }

  return 0;
}

public int n5()
{
  boolean[] __x5 = new boolean[2];
  __x5[0]        = (__x5[1] = mainAgent != null && exists(((Set<Object>)mainAgent.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.getValue("<cat:simple_children>")).contains("n5"));
  logRule(5, __x5);
n5:
  if (__x5[0]) {
    if (mainAgent != null && exists(((Set<Object>)mainAgent.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.getValue("<cat:imminent_simple_children>")).contains("n5")) {
      emitDA(new DialogueAct("Question", "MoreTalking"));
      lastDAprocessed();
    }

    ((Set<Object>)mainAgent.getValue("<cat:imminent_simple_children>")).remove("n5");
    check_out_transition("n5", "mainAgent_out", mainAgent, mainAgent);
  }

  return 0;
}

public int n3()
{
  boolean[] __x6 = new boolean[2];
  __x6[0]        = (__x6[1] = mainAgent != null && exists(((Set<Object>)mainAgent.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.getValue("<cat:simple_children>")).contains("n3"));
  logRule(6, __x6);
n3:
  if (__x6[0]) {
    ((Set<Object>)mainAgent.getValue("<cat:imminent_simple_children>")).remove("n3");
    probability_transition("n3", "n5", mainAgent, mainAgent, false);
  }

  return 0;
}

public int n2()
{
  boolean[] __x7 = new boolean[2];
  __x7[0]        = (__x7[1] = mainAgent != null && exists(((Set<Object>)mainAgent.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.getValue("<cat:simple_children>")).contains("n2"));
  logRule(7, __x7);
n2:
  if (__x7[0]) {
    ((Set<Object>)mainAgent.getValue("<cat:imminent_simple_children>")).remove("n2");
    probability_transition("n2", "n4", mainAgent, mainAgent, false);
  }

  return 0;
}
}
