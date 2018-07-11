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
  res = great_node();
  if (res != 0)
    return res - 1;
  return 0;
}

/*END_PROCESS*/ public int setup_pos_node()
{
  boolean[] __x11 = new boolean[2];
  __x11[0]        = (__x11[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:initiated>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:initiated>")).contains("pos_node"));
  introAgentMain.logRule(11, __x11);
setup_pos_node:
  if (__x11[0]) {
    if (!(introAgentMain.pos_node != null && ((Boolean)introAgentMain.pos_node.getSingleValue("<cat:active>")))) {
// pos_node = new Pos_node;

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
  boolean[] __x12 = new boolean[2];
  __x12[0]        = !((__x12[1] = introAgentMain.pos_node != null && ((Boolean)introAgentMain.pos_node.getSingleValue("<cat:active>"))));
  introAgentMain.logRule(12, __x12);
pass_by_pos_node:
  if (__x12[0]) {
    return 1;
  }

  return 0;
}

public int pos_node_interruptive_edge_1()
{
  boolean[] __x13 = new boolean[2];
  __x13[0]        = (__x13[1] = introAgentMain.lastDA().isSubsumedBy(new DialogueAct("Question", "Mood")));
  introAgentMain.logRule(13, __x13);
pos_node_interruptive_edge_1:
  if (__x13[0]) {
    introAgentMain.interruptive_transition(introAgentMain.pos_node, introAgentMain.hello_node, "answer_node");
    return 1;
  }

  return 0;
}

public int pos_node_in()
{
  boolean[] __x14 = new boolean[2];
  __x14[0]        = (__x14[1] = introAgentMain.pos_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>")).contains("pos_node_in"));
  introAgentMain.logRule(14, __x14);
pos_node_in:
  if (__x14[0]) {
    introAgentMain.transition("pos_node_in", "cool_node", introAgentMain.pos_node, introAgentMain.pos_node);
  }

  return 0;
}

public int pos_node_out()
{
  boolean[] __x15 = new boolean[2];
  __x15[0]        = (__x15[1] = introAgentMain.pos_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>")).contains("pos_node_out"));
  introAgentMain.logRule(15, __x15);
pos_node_out:
  if (__x15[0]) {
// Bei der Transition sollten die Variablen-Bindings aus pos_node (nicht dem parent) benutzt werden

    introAgentMain.transition("pos_node_out", "excuse_node", introAgentMain.pos_node, introAgentMain.hello_node);
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
  boolean[] __x16 = new boolean[2];
  __x16[0]        = (__x16[1] = introAgentMain.pos_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>")).contains("cool_node"));
  introAgentMain.logRule(16, __x16);
cool_node:
  if (__x16[0]) {
    introAgentMain.emitDA(new DialogueAct("Connecting", "PositiveFeeling"));
    introAgentMain.transition("cool_node", "why_node", introAgentMain.pos_node, introAgentMain.pos_node);
    introAgentMain.check_out_transition("cool_node", "pos_node_out", introAgentMain.pos_node);
  }

  return 0;
}

public int why_node()
{
  boolean[] __x17 = new boolean[2];
  __x17[0]        = (__x17[1] = introAgentMain.pos_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>")).contains("why_node"));
  introAgentMain.logRule(17, __x17);
why_node:
  if (__x17[0]) {
    introAgentMain.emitDA(new DialogueAct("WHQuestion", "PositiveMood"));
    introAgentMain.timeout_transition("why_node", "great_node", introAgentMain.pos_node, introAgentMain.pos_node, 10000);
    introAgentMain.check_out_transition("why_node", "pos_node_out", introAgentMain.pos_node);
  }

  return 0;
}

public int great_node()
{
  boolean[] __x18 = new boolean[2];
  __x18[0]        = (__x18[1] = introAgentMain.pos_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.pos_node.getValue("<cat:simple_children>")).contains("great_node"));
  introAgentMain.logRule(18, __x18);
great_node:
  if (__x18[0]) {
    introAgentMain.emitDA(new DialogueAct("Connecting", "Enthusiastic"));
    introAgentMain.check_out_transition("great_node", "pos_node_out", introAgentMain.pos_node);
  }

  return 0;
}
}
