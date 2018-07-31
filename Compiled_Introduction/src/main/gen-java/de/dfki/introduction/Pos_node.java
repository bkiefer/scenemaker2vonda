package de.dfki.introduction;
import java.util.*;

import de.dfki.mlt.rudimant.agent.DialogueAct;
import de.dfki.lt.hfc.db.rdfProxy.*;
import de.dfki.lt.hfc.types.*;
import de.dfki.introduction.IntroAgentMain;
public class Pos_node {
private final Hello_node hello_node;
private final IntroAgentMain introAgentMain;

public Pos_node(Hello_node hello_node, IntroAgentMain introAgentMain)
{
  super();
  this.hello_node     = hello_node;
  this.introAgentMain = introAgentMain;
}

/*START_PROCESS*/ public int process()
{
  int res = 0;
  res = setup_pos_node();
  if (res != 0)
    return res - 1;
  res = pass_by_pos_node();
  if (res != 0)
    return res - 1;
  res = pos_node_interruptive_edge_1();
  if (res != 0)
    return res - 1;
  res = pos_node_in();
  if (res != 0)
    return res - 1;
  res = pos_node_out();
  if (res != 0)
    return res - 1;
  res = cool_node();
  if (res != 0)
    return res - 1;
  res = why_node();
  if (res != 0)
    return res - 1;
  res = demand_answer_node();
  if (res != 0)
    return res - 1;
  res = great_node();
  if (res != 0)
    return res - 1;
  return 0;
}

/*END_PROCESS*/ public int setup_pos_node()
{
  boolean[] __x23 = new boolean[2];
  __x23[0]        = (__x23[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:initiated>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:initiated>")).contains("pos_node"));
  introAgentMain.logRule(23, __x23);
setup_pos_node:
  if (__x23[0]) {
    if (!(introAgentMain.pos_node != null && ((Boolean)introAgentMain.pos_node.getSingleValue("<cat:active>")))) {
      introAgentMain.pos_node.setValue("<cat:active>", true);
      ((Set<Object>)introAgentMain.hello_node.getValue("<cat:super_children>")).add(introAgentMain.pos_node);
      introAgentMain.pos_node.setValue("<cat:parent>", introAgentMain.hello_node);
    }

    ((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>")).add("pos_node_in");
    ((Set<Object>)introAgentMain.hello_node.getValue("<cat:initiated>")).remove("pos_node");
  }

  return 0;
}

public int pass_by_pos_node()
{
  boolean[] __x24 = new boolean[2];
  __x24[0]        = !((__x24[1] = introAgentMain.pos_node != null && ((Boolean)introAgentMain.pos_node.getSingleValue("<cat:active>"))));
  introAgentMain.logRule(24, __x24);
pass_by_pos_node:
  if (__x24[0]) {
    return 1;
  }

  return 0;
}

public int pos_node_interruptive_edge_1()
{
  boolean[] __x25 = new boolean[2];
  __x25[0]        = (__x25[1] = introAgentMain.lastDA().isSubsumedBy(new DialogueAct("Question", "Mood")));
  introAgentMain.logRule(25, __x25);
pos_node_interruptive_edge_1:
  if (__x25[0]) {
    introAgentMain.interruptive_transition(introAgentMain.pos_node, introAgentMain.hello_node, "answer_node");
    return 1;
  }

  return 0;
}

public int pos_node_in()
{
  boolean[] __x26 = new boolean[2];
  __x26[0]        = (__x26[1] = introAgentMain.pos_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>")).contains("pos_node_in"));
  introAgentMain.logRule(26, __x26);
pos_node_in:
  if (__x26[0]) {
    introAgentMain.transition("pos_node_in", "cool_node", introAgentMain.pos_node, introAgentMain.pos_node);
  }

  return 0;
}

public int pos_node_out()
{
  boolean[] __x27 = new boolean[2];
  __x27[0]        = (__x27[1] = introAgentMain.pos_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>")).contains("pos_node_out"));
  introAgentMain.logRule(27, __x27);
pos_node_out:
  if (__x27[0]) {
// Bei der Transition sollten die Variablen-Bindings aus pos_node (nicht dem parent) benutzt werden

    introAgentMain.transition("pos_node_out", "something_else_node", introAgentMain.pos_node, introAgentMain.hello_node);
    if (introAgentMain.test_inactive(introAgentMain.pos_node)) {
      ((Set<Object>)introAgentMain.hello_node.getValue("<cat:super_children>")).remove(introAgentMain.pos_node);
      introAgentMain.set_inactive(introAgentMain.pos_node);
    }

    introAgentMain.check_out_transition("pos_node_out", "hello_node_out", introAgentMain.pos_node);
  }

  return 0;
}

public int cool_node()
{
  boolean[] __x28 = new boolean[2];
  __x28[0]        = (__x28[1] = introAgentMain.pos_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>")).contains("cool_node"));
  introAgentMain.logRule(28, __x28);
cool_node:
  if (__x28[0]) {
    introAgentMain.emitDA(new DialogueAct("Connecting", "PositiveFeeling"));
    introAgentMain.lastDAprocessed();
    introAgentMain.transition("cool_node", "why_node", introAgentMain.pos_node, introAgentMain.pos_node);
    introAgentMain.check_out_transition("cool_node", "pos_node_out", introAgentMain.pos_node);
  }

  return 0;
}

public int why_node()
{
  boolean[] __x29 = new boolean[2];
  __x29[0]        = (__x29[1] = introAgentMain.pos_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>")).contains("why_node"));
  introAgentMain.logRule(29, __x29);
why_node:
  if (__x29[0]) {
    if (!(introAgentMain.hasActiveTimeout("why_node"))) {
      introAgentMain.emitDA(new DialogueAct("WHQuestion", "PositiveMood"));
      introAgentMain.lastDAprocessed();
    }

    if (((Boolean)introAgentMain.hello_node.getSingleValue("<cat:timedOut>")) == true) {
      introAgentMain.transition("why_node", "demand_answer_node", introAgentMain.pos_node, introAgentMain.pos_node);
    }

    introAgentMain.timeout_transition("why_node", "great_node", introAgentMain.pos_node, introAgentMain.pos_node, 10000);
    introAgentMain.check_out_transition("why_node", "pos_node_out", introAgentMain.pos_node);
  }

  return 0;
}

public int demand_answer_node()
{
  boolean[] __x30 = new boolean[2];
  __x30[0]        = (__x30[1] = introAgentMain.pos_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>")).contains("demand_answer_node"));
  introAgentMain.logRule(30, __x30);
demand_answer_node:
  if (__x30[0]) {
    introAgentMain.emitDA(new DialogueAct("Urge", "Answer"));
    introAgentMain.timeout_transition("demand_answer_node", "great_node", introAgentMain.pos_node, introAgentMain.pos_node, 12000);
    introAgentMain.check_out_transition("demand_answer_node", "pos_node_out", introAgentMain.pos_node);
  }

  return 0;
}

public int great_node()
{
  boolean[] __x31 = new boolean[2];
  __x31[0]        = (__x31[1] = introAgentMain.pos_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>")).contains("great_node"));
  introAgentMain.logRule(31, __x31);
great_node:
  if (__x31[0]) {
    introAgentMain.emitDA(new DialogueAct("Connecting", "Enthusiastic"));
    introAgentMain.lastDAprocessed();
    introAgentMain.check_out_transition("great_node", "pos_node_out", introAgentMain.pos_node);
  }

  return 0;
}
}
