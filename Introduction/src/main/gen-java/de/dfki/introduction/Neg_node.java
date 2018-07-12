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
  boolean[] __x19 = new boolean[2];
  __x19[0]        = (__x19[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:initiated>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:initiated>")).contains("neg_node"));
  introAgentMain.logRule(19, __x19);
setup_neg_node:
  if (__x19[0]) {
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
  boolean[] __x20 = new boolean[2];
  __x20[0]        = !((__x20[1] = introAgentMain.neg_node != null && ((Boolean)introAgentMain.neg_node.getSingleValue("<cat:active>"))));
  introAgentMain.logRule(20, __x20);
pass_by_neg_node:
  if (__x20[0]) {
    return 1;
  }

  return 0;
}

public int neg_node_interruptive_edge_1()
{
  boolean[] __x21 = new boolean[2];
  __x21[0]        = (__x21[1] = introAgentMain.lastDA().isSubsumedBy(new DialogueAct("Question", "Mood")));
  introAgentMain.logRule(21, __x21);
neg_node_interruptive_edge_1:
  if (__x21[0]) {
    introAgentMain.interruptive_transition(introAgentMain.neg_node, introAgentMain.hello_node, "answer_node");
    return 1;
  }

  return 0;
}

public int neg_node_in()
{
  boolean[] __x22 = new boolean[2];
  __x22[0]        = (__x22[1] = introAgentMain.neg_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>")).contains("neg_node_in"));
  introAgentMain.logRule(22, __x22);
neg_node_in:
  if (__x22[0]) {
    introAgentMain.transition("neg_node_in", "no_node", introAgentMain.neg_node, introAgentMain.neg_node);
  }

  return 0;
}

public int neg_node_out()
{
  boolean[] __x23 = new boolean[2];
  __x23[0]        = (__x23[1] = introAgentMain.neg_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>")).contains("neg_node_out"));
  introAgentMain.logRule(23, __x23);
neg_node_out:
  if (__x23[0]) {
// Bei der Transition sollten die Variablen-Bindings aus neg_node (nicht dem parent) benutzt werden

    introAgentMain.transition("neg_node_out", "excuse_node", introAgentMain.neg_node, introAgentMain.hello_node);
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
  boolean[] __x24 = new boolean[3];
  __x24[0]        = (__x24[1] = introAgentMain.neg_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>")).contains("no_node")) && !((__x24[2] = (introAgentMain.myLastDA().isSubsumedBy(new DialogueAct("Connecting", "NegativeFeeling")))));
  introAgentMain.logRule(24, __x24);
no_node:
  if (__x24[0]) {
    introAgentMain.emitDA(new DialogueAct("Connecting", "NegativeFeeling"));
    introAgentMain.probability_transition("no_node", "joke_node", introAgentMain.neg_node, introAgentMain.neg_node);
    introAgentMain.probability_transition("no_node", "sorry_node", introAgentMain.neg_node, introAgentMain.neg_node);
    introAgentMain.probability_transition("no_node", "happy_node", introAgentMain.neg_node, introAgentMain.neg_node);
  }

  return 0;
}

public int joke_node()
{
  boolean[] __x25 = new boolean[2];
  __x25[0]        = (__x25[1] = introAgentMain.neg_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>")).contains("joke_node"));
  introAgentMain.logRule(25, __x25);
joke_node:
  if (__x25[0]) {
    introAgentMain.emitDA(new DialogueAct("Encouragement", "Joke"));
    introAgentMain.check_out_transition("joke_node", "neg_node_out", introAgentMain.neg_node);
  }

  return 0;
}

public int sorry_node()
{
  boolean[] __x26 = new boolean[2];
  __x26[0]        = (__x26[1] = introAgentMain.neg_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>")).contains("sorry_node"));
  introAgentMain.logRule(26, __x26);
sorry_node:
  if (__x26[0]) {
    introAgentMain.emitDA(new DialogueAct("Connecting", "Sorry"));
    introAgentMain.check_out_transition("sorry_node", "neg_node_out", introAgentMain.neg_node);
  }

  return 0;
}

public int happy_node()
{
  boolean[] __x27 = new boolean[2];
  __x27[0]        = (__x27[1] = introAgentMain.neg_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.neg_node.getValue("<cat:simple_children>")).contains("happy_node"));
  introAgentMain.logRule(27, __x27);
happy_node:
  if (__x27[0]) {
    introAgentMain.emitDA(new DialogueAct("Encouragement", "Tomorrow"));
    introAgentMain.check_out_transition("happy_node", "neg_node_out", introAgentMain.neg_node);
  }

  return 0;
}
}
