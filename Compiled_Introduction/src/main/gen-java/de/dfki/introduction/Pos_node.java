package de.dfki.introduction;
import java.util.*;

import de.dfki.mlt.rudimant.agent.DialogueAct;
import de.dfki.lt.hfc.db.rdfProxy.*;
import de.dfki.lt.hfc.types.*;
import de.dfki.introduction.MainAgent;
public class Pos_node {
private final Hello_node hello_node;
private final MainAgent mainAgent;

public Pos_node(Hello_node hello_node, MainAgent mainAgent)
{
  super();
  this.hello_node = hello_node;
  this.mainAgent  = mainAgent;
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
  res = great_node();
  if (res != 0)
    return res - 1;
  res = demand_answer_node();
  if (res != 0)
    return res - 1;
  res = why_node();
  if (res != 0)
    return res - 1;
  res = cool_node();
  if (res != 0)
    return res - 1;
  return 0;
}

/*END_PROCESS*/ public int setup_pos_node()
{
  boolean[] __x23 = new boolean[2];
  __x23[0]        = (__x23[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:initiated>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:initiated>")).contains("pos_node"));
  mainAgent.logRule(23, __x23);
setup_pos_node:
  if (__x23[0]) {
    if (!(mainAgent.pos_node != null && ((Boolean)mainAgent.pos_node.getSingleValue("<cat:active>")))) {
      mainAgent.pos_node.setValue("<cat:active>", true);
      ((Set<Object>)mainAgent.hello_node.getValue("<cat:super_children>")).add(mainAgent.pos_node);
    }

    ((Set<Object>)mainAgent.pos_node.getValue("<cat:simple_children>")).add("pos_node_in");
    ((Set<Object>)mainAgent.hello_node.getValue("<cat:initiated>")).remove("pos_node");
  }

  return 0;
}

public int pass_by_pos_node()
{
  boolean[] __x24 = new boolean[2];
  __x24[0]        = !((__x24[1] = mainAgent.pos_node != null && ((Boolean)mainAgent.pos_node.getSingleValue("<cat:active>"))));
  mainAgent.logRule(24, __x24);
pass_by_pos_node:
  if (__x24[0]) {
    return 1;
  }

  return 0;
}

public int pos_node_interruptive_edge_1()
{
  boolean[] __x25 = new boolean[2];
  __x25[0]        = (__x25[1] = mainAgent.lastDA().isSubsumedBy(new DialogueAct("Question", "Mood")));
  mainAgent.logRule(25, __x25);
pos_node_interruptive_edge_1:
  if (__x25[0]) {
    mainAgent.interruptive_transition(mainAgent.pos_node, mainAgent.hello_node, "answer_node");
    return 1;
  }

  return 0;
}

public int pos_node_in()
{
  boolean[] __x26 = new boolean[2];
  __x26[0]        = (__x26[1] = mainAgent.pos_node != null && mainAgent.exists(((Set<Object>)mainAgent.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.pos_node.getValue("<cat:simple_children>")).contains("pos_node_in"));
  mainAgent.logRule(26, __x26);
pos_node_in:
  if (__x26[0]) {
    mainAgent.transition("pos_node_in", "cool_node", mainAgent.pos_node, mainAgent.pos_node);
    mainAgent.check_out_transition("pos_node_in", "pos_node_out", mainAgent.pos_node, mainAgent.pos_node);
  }

  return 0;
}

public int pos_node_out()
{
  boolean[] __x27 = new boolean[2];
  __x27[0]        = (__x27[1] = mainAgent.pos_node != null && mainAgent.exists(((Set<Object>)mainAgent.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.pos_node.getValue("<cat:simple_children>")).contains("pos_node_out"));
  mainAgent.logRule(27, __x27);
pos_node_out:
  if (__x27[0]) {
    mainAgent.transition("pos_node_out", "something_else_node", mainAgent.pos_node, mainAgent.hello_node);
    if (mainAgent.test_inactive(mainAgent.pos_node)) {
      ((Set<Object>)mainAgent.hello_node.getValue("<cat:super_children>")).remove(mainAgent.pos_node);
      mainAgent.set_inactive(mainAgent.pos_node);
    }

    mainAgent.check_out_transition("pos_node_out", "hello_node_out", mainAgent.pos_node, mainAgent.hello_node);
  }

  return 0;
}

public int great_node()
{
  boolean[] __x28 = new boolean[2];
  __x28[0]        = (__x28[1] = mainAgent.pos_node != null && mainAgent.exists(((Set<Object>)mainAgent.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.pos_node.getValue("<cat:simple_children>")).contains("great_node"));
  mainAgent.logRule(28, __x28);
great_node:
  if (__x28[0]) {
    if (!(mainAgent.hasActiveTimeout("great_node"))) {
      mainAgent.emitDA(new DialogueAct("Connecting", "Enthusiastic"));
      mainAgent.lastDAprocessed();
    }

    mainAgent.check_out_transition("great_node", "pos_node_out", mainAgent.pos_node, mainAgent.pos_node);
  }

  return 0;
}

public int demand_answer_node()
{
  boolean[] __x29 = new boolean[2];
  __x29[0]        = (__x29[1] = mainAgent.pos_node != null && mainAgent.exists(((Set<Object>)mainAgent.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.pos_node.getValue("<cat:simple_children>")).contains("demand_answer_node"));
  mainAgent.logRule(29, __x29);
demand_answer_node:
  if (__x29[0]) {
    if (!(mainAgent.hasActiveTimeout("demand_answer_node"))) {
      mainAgent.emitDA(new DialogueAct("Urge", "Answer"));
      mainAgent.lastDAprocessed();
    }

    mainAgent.timeout_transition("demand_answer_node", "great_node", mainAgent.pos_node, mainAgent.pos_node, 12000);
    mainAgent.check_out_transition("demand_answer_node", "pos_node_out", mainAgent.pos_node, mainAgent.pos_node);
  }

  return 0;
}

public int why_node()
{
  boolean[] __x30 = new boolean[2];
  __x30[0]        = (__x30[1] = mainAgent.pos_node != null && mainAgent.exists(((Set<Object>)mainAgent.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.pos_node.getValue("<cat:simple_children>")).contains("why_node"));
  mainAgent.logRule(30, __x30);
why_node:
  if (__x30[0]) {
    if (!(mainAgent.hasActiveTimeout("why_node"))) {
      mainAgent.emitDA(new DialogueAct("WHQuestion", "PositiveMood"));
      mainAgent.lastDAprocessed();
    }

    mainAgent.timeout_transition("why_node", "great_node", mainAgent.pos_node, mainAgent.pos_node, 10000);
    if (((Boolean)mainAgent.hello_node.getSingleValue("<cat:timedOut>")) == true) {
      mainAgent.transition("why_node", "demand_answer_node", mainAgent.pos_node, mainAgent.pos_node);
    }

    mainAgent.check_out_transition("why_node", "pos_node_out", mainAgent.pos_node, mainAgent.pos_node);
  }

  return 0;
}

public int cool_node()
{
  boolean[] __x31 = new boolean[2];
  __x31[0]        = (__x31[1] = mainAgent.pos_node != null && mainAgent.exists(((Set<Object>)mainAgent.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.pos_node.getValue("<cat:simple_children>")).contains("cool_node"));
  mainAgent.logRule(31, __x31);
cool_node:
  if (__x31[0]) {
    if (!(mainAgent.hasActiveTimeout("cool_node"))) {
      mainAgent.emitDA(new DialogueAct("Connecting", "PositiveFeeling"));
      mainAgent.lastDAprocessed();
    }

    mainAgent.transition("cool_node", "why_node", mainAgent.pos_node, mainAgent.pos_node);
    mainAgent.check_out_transition("cool_node", "pos_node_out", mainAgent.pos_node, mainAgent.pos_node);
  }

  return 0;
}
}
