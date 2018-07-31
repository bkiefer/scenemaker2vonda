package de.dfki.introduction;
import java.util.*;

import de.dfki.mlt.rudimant.agent.DialogueAct;
import de.dfki.lt.hfc.db.rdfProxy.*;
import de.dfki.lt.hfc.types.*;
import de.dfki.introduction.IntroAgentMain;
public class Neg_node {
private final Hello_node hello_node;
private final IntroAgentMain introAgentMain;

public Neg_node(Hello_node hello_node, IntroAgentMain introAgentMain)
{
  super();
  this.hello_node     = hello_node;
  this.introAgentMain = introAgentMain;
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
  res = joke_node();
  if (res != 0)
    return res - 1;
  res = sorry_node();
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
  __x32[0]        = (__x32[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:initiated>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:initiated>")).contains("neg_node"));
  introAgentMain.logRule(32, __x32);
setup_neg_node:
  if (__x32[0]) {
    if (!(introAgentMain.neg_node != null && ((Boolean)introAgentMain.neg_node.getSingleValue("<cat:active>")))) {
      introAgentMain.neg_node.setValue("<cat:active>", true);
      ((Set<Object>)introAgentMain.hello_node.getValue("<cat:super_children>")).add(introAgentMain.neg_node);
      introAgentMain.neg_node.setValue("<cat:parent>", introAgentMain.hello_node);
    }

    ((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>")).add("neg_node_in");
    ((Set<Object>)introAgentMain.hello_node.getValue("<cat:initiated>")).remove("neg_node");
  }

  return 0;
}

public int pass_by_neg_node()
{
  boolean[] __x33 = new boolean[2];
  __x33[0]        = !((__x33[1] = introAgentMain.neg_node != null && ((Boolean)introAgentMain.neg_node.getSingleValue("<cat:active>"))));
  introAgentMain.logRule(33, __x33);
pass_by_neg_node:
  if (__x33[0]) {
    return 1;
  }

  return 0;
}

public int neg_node_interruptive_edge_1()
{
  boolean[] __x34 = new boolean[2];
  __x34[0]        = (__x34[1] = introAgentMain.lastDA().isSubsumedBy(new DialogueAct("Question", "Mood")));
  introAgentMain.logRule(34, __x34);
neg_node_interruptive_edge_1:
  if (__x34[0]) {
    introAgentMain.interruptive_transition(introAgentMain.neg_node, introAgentMain.hello_node, "answer_node");
    return 1;
  }

  return 0;
}

public int neg_node_in()
{
  boolean[] __x35 = new boolean[2];
  __x35[0]        = (__x35[1] = introAgentMain.neg_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>")).contains("neg_node_in"));
  introAgentMain.logRule(35, __x35);
neg_node_in:
  if (__x35[0]) {
    introAgentMain.transition("neg_node_in", "no_node", introAgentMain.neg_node, introAgentMain.neg_node);
  }

  return 0;
}

public int neg_node_out()
{
  boolean[] __x36 = new boolean[2];
  __x36[0]        = (__x36[1] = introAgentMain.neg_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>")).contains("neg_node_out"));
  introAgentMain.logRule(36, __x36);
neg_node_out:
  if (__x36[0]) {
// Bei der Transition sollten die Variablen-Bindings aus neg_node (nicht dem parent) benutzt werden

    introAgentMain.transition("neg_node_out", "something_else_node", introAgentMain.neg_node, introAgentMain.hello_node);
    if (introAgentMain.test_inactive(introAgentMain.neg_node)) {
      ((Set<Object>)introAgentMain.hello_node.getValue("<cat:super_children>")).remove(introAgentMain.neg_node);
      introAgentMain.set_inactive(introAgentMain.neg_node);
    }

    introAgentMain.check_out_transition("neg_node_out", "hello_node_out", introAgentMain.neg_node);
  }

  return 0;
}

public int no_node()
{
  boolean[] __x37 = new boolean[2];
  __x37[0]        = (__x37[1] = introAgentMain.neg_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>")).contains("no_node"));
  introAgentMain.logRule(37, __x37);
no_node:
  if (__x37[0]) {
    introAgentMain.emitDA(new DialogueAct("Connecting", "NegativeFeeling"));
    introAgentMain.lastDAprocessed();
    introAgentMain.probability_transition("no_node", "sorry_node", introAgentMain.neg_node, introAgentMain.neg_node);
    introAgentMain.probability_transition("no_node", "happy_node", introAgentMain.neg_node, introAgentMain.neg_node);
    introAgentMain.probability_transition("no_node", "joke_node", introAgentMain.neg_node, introAgentMain.neg_node);
  }

  return 0;
}

public int joke_node()
{
  boolean[] __x38 = new boolean[2];
  __x38[0]        = (__x38[1] = introAgentMain.neg_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>")).contains("joke_node"));
  introAgentMain.logRule(38, __x38);
joke_node:
  if (__x38[0]) {
    introAgentMain.emitDA(new DialogueAct("Encouragement", "Joke"));
    introAgentMain.check_out_transition("joke_node", "neg_node_out", introAgentMain.neg_node);
  }

  return 0;
}

public int sorry_node()
{
  boolean[] __x39 = new boolean[2];
  __x39[0]        = (__x39[1] = introAgentMain.neg_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>")).contains("sorry_node"));
  introAgentMain.logRule(39, __x39);
sorry_node:
  if (__x39[0]) {
    introAgentMain.emitDA(new DialogueAct("Connecting", "Sorry"));
    introAgentMain.check_out_transition("sorry_node", "neg_node_out", introAgentMain.neg_node);
  }

  return 0;
}

public int happy_node()
{
  boolean[] __x40 = new boolean[2];
  __x40[0]        = (__x40[1] = introAgentMain.neg_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>")).contains("happy_node"));
  introAgentMain.logRule(40, __x40);
happy_node:
  if (__x40[0]) {
    introAgentMain.emitDA(new DialogueAct("Encouragement", "Tomorrow"));
    introAgentMain.check_out_transition("happy_node", "neg_node_out", introAgentMain.neg_node);
  }

  return 0;
}
}
