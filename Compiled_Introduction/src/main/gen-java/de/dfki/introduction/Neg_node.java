package de.dfki.introduction;
import java.util.*;

import de.dfki.mlt.rudimant.agent.DialogueAct;
import de.dfki.lt.hfc.db.rdfProxy.*;
import de.dfki.lt.hfc.types.*;
import de.dfki.introduction.MainAgent;
public class Neg_node {
private final Hello_node hello_node;
private final MainAgent mainAgent;

public Neg_node(Hello_node hello_node, MainAgent mainAgent)
{
  super();
  this.hello_node = hello_node;
  this.mainAgent  = mainAgent;
}

/*START_PROCESS*/ public int process()
{
  int res = 0;
  res = setup_neg_node();
  if (res != 0)
    return res - 1;
  res = pass_by_neg_node();
  if (res != 0)
    return res - 1;
  res = neg_node_interruptive_edge_1();
  if (res != 0)
    return res - 1;
  res = neg_node_in();
  if (res != 0)
    return res - 1;
  res = neg_node_out();
  if (res != 0)
    return res - 1;
  res = no_node();
  if (res != 0)
    return res - 1;
  res = sorry_node();
  if (res != 0)
    return res - 1;
  res = joke_node();
  if (res != 0)
    return res - 1;
  res = happy_node();
  if (res != 0)
    return res - 1;
  return 0;
}

/*END_PROCESS*/ public int setup_neg_node()
{
  boolean[] __x32 = new boolean[2];
  __x32[0]        = (__x32[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:initiated>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:initiated>")).contains("neg_node"));
  mainAgent.logRule(32, __x32);
setup_neg_node:
  if (__x32[0]) {
    if (!(mainAgent.neg_node != null && ((Boolean)mainAgent.neg_node.getSingleValue("<cat:active>")))) {
      mainAgent.neg_node.setValue("<cat:active>", true);
      ((Set<Object>)mainAgent.hello_node.getValue("<cat:super_children>")).add(mainAgent.neg_node);
    }

    ((Set<Object>)mainAgent.neg_node.getValue("<cat:simple_children>")).add("neg_node_in");
    ((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>")).add("neg_node_in");
    ((Set<Object>)mainAgent.hello_node.getValue("<cat:initiated>")).remove("neg_node");
  }

  return 0;
}

public int pass_by_neg_node()
{
  boolean[] __x33 = new boolean[2];
  __x33[0]        = !((__x33[1] = mainAgent.neg_node != null && ((Boolean)mainAgent.neg_node.getSingleValue("<cat:active>"))));
  mainAgent.logRule(33, __x33);
pass_by_neg_node:
  if (__x33[0]) {
    return 1;
  }

  return 0;
}

public int neg_node_interruptive_edge_1()
{
  boolean[] __x34 = new boolean[2];
  __x34[0]        = (__x34[1] = mainAgent.lastDA().isSubsumedBy(new DialogueAct("Question", "Mood")));
  mainAgent.logRule(34, __x34);
neg_node_interruptive_edge_1:
  if (__x34[0]) {
    mainAgent.interruptive_transition(mainAgent.neg_node, mainAgent.hello_node, "answer_node", false);
    return 1;
  }

  return 0;
}

public int neg_node_in()
{
  boolean[] __x35 = new boolean[2];
  __x35[0]        = (__x35[1] = mainAgent.neg_node != null && mainAgent.exists(((Set<Object>)mainAgent.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.neg_node.getValue("<cat:simple_children>")).contains("neg_node_in"));
  mainAgent.logRule(35, __x35);
neg_node_in:
  if (__x35[0]) {
    ((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>")).remove("neg_node_in");
    mainAgent.transition("neg_node_in", "no_node", mainAgent.neg_node, mainAgent.neg_node, false);
    mainAgent.check_out_transition("neg_node_in", "neg_node_out", mainAgent.neg_node, mainAgent.neg_node);
  }

  return 0;
}

public int neg_node_out()
{
  boolean[] __x36 = new boolean[2];
  __x36[0]        = (__x36[1] = mainAgent.neg_node != null && mainAgent.exists(((Set<Object>)mainAgent.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.neg_node.getValue("<cat:simple_children>")).contains("neg_node_out"));
  mainAgent.logRule(36, __x36);
neg_node_out:
  if (__x36[0]) {
    ((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>")).remove("neg_node_out");
    mainAgent.transition("neg_node_out", "something_else_node", mainAgent.neg_node, mainAgent.hello_node, false);
    if (mainAgent.test_inactive(mainAgent.neg_node)) {
      ((Set<Object>)mainAgent.hello_node.getValue("<cat:super_children>")).remove(mainAgent.neg_node);
      mainAgent.set_inactive(mainAgent.neg_node);
    }

    mainAgent.check_out_transition("neg_node_out", "hello_node_out", mainAgent.neg_node, mainAgent.hello_node);
  }

  return 0;
}

public int no_node()
{
  boolean[] __x37 = new boolean[2];
  __x37[0]        = (__x37[1] = mainAgent.neg_node != null && mainAgent.exists(((Set<Object>)mainAgent.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.neg_node.getValue("<cat:simple_children>")).contains("no_node"));
  mainAgent.logRule(37, __x37);
no_node:
  if (__x37[0]) {
    if (mainAgent.neg_node != null && mainAgent.exists(((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>")).contains("no_node")) {
      mainAgent.emitDA(new DialogueAct("Connecting", "NegativeFeeling"));
      mainAgent.lastDAprocessed();
    }

    mainAgent.probability_transition("no_node", "happy_node", mainAgent.neg_node, mainAgent.neg_node, false);
    mainAgent.probability_transition("no_node", "joke_node", mainAgent.neg_node, mainAgent.neg_node, false);
    mainAgent.probability_transition("no_node", "sorry_node", mainAgent.neg_node, mainAgent.neg_node, false);
    ((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>")).remove("no_node");
    mainAgent.probability_transition("no_node", "happy_node", mainAgent.neg_node, mainAgent.neg_node, false);
    mainAgent.probability_transition("no_node", "joke_node", mainAgent.neg_node, mainAgent.neg_node, false);
    mainAgent.probability_transition("no_node", "sorry_node", mainAgent.neg_node, mainAgent.neg_node, false);
    mainAgent.check_out_transition("no_node", "neg_node_out", mainAgent.neg_node, mainAgent.neg_node);
  }

  return 0;
}

public int sorry_node()
{
  boolean[] __x38 = new boolean[2];
  __x38[0]        = (__x38[1] = mainAgent.neg_node != null && mainAgent.exists(((Set<Object>)mainAgent.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.neg_node.getValue("<cat:simple_children>")).contains("sorry_node"));
  mainAgent.logRule(38, __x38);
sorry_node:
  if (__x38[0]) {
    if (mainAgent.neg_node != null && mainAgent.exists(((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>")).contains("sorry_node")) {
      mainAgent.emitDA(new DialogueAct("Connecting", "Sorry"));
      mainAgent.lastDAprocessed();
    }

    ((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>")).remove("sorry_node");
    mainAgent.check_out_transition("sorry_node", "neg_node_out", mainAgent.neg_node, mainAgent.neg_node);
  }

  return 0;
}

public int joke_node()
{
  boolean[] __x39 = new boolean[2];
  __x39[0]        = (__x39[1] = mainAgent.neg_node != null && mainAgent.exists(((Set<Object>)mainAgent.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.neg_node.getValue("<cat:simple_children>")).contains("joke_node"));
  mainAgent.logRule(39, __x39);
joke_node:
  if (__x39[0]) {
    if (mainAgent.neg_node != null && mainAgent.exists(((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>")).contains("joke_node")) {
      mainAgent.emitDA(new DialogueAct("Encouragement", "Joke"));
      mainAgent.lastDAprocessed();
    }

    ((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>")).remove("joke_node");
    mainAgent.check_out_transition("joke_node", "neg_node_out", mainAgent.neg_node, mainAgent.neg_node);
  }

  return 0;
}

public int happy_node()
{
  boolean[] __x40 = new boolean[2];
  __x40[0]        = (__x40[1] = mainAgent.neg_node != null && mainAgent.exists(((Set<Object>)mainAgent.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.neg_node.getValue("<cat:simple_children>")).contains("happy_node"));
  mainAgent.logRule(40, __x40);
happy_node:
  if (__x40[0]) {
    if (mainAgent.neg_node != null && mainAgent.exists(((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>")).contains("happy_node")) {
      mainAgent.emitDA(new DialogueAct("Encouragement", "Tomorrow"));
      mainAgent.lastDAprocessed();
    }

    ((Set<Object>)mainAgent.neg_node.getValue("<cat:imminent_simple_children>")).remove("happy_node");
    mainAgent.check_out_transition("happy_node", "neg_node_out", mainAgent.neg_node, mainAgent.neg_node);
  }

  return 0;
}
}
