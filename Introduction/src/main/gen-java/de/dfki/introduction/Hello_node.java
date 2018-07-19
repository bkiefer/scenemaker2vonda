package de.dfki.introduction;
import java.util.*;

import de.dfki.mlt.rudimant.agent.DialogueAct;
import de.dfki.lt.hfc.db.rdfProxy.*;
import de.dfki.lt.hfc.types.*;
import de.dfki.introduction.IntroAgentMain;
public class Hello_node {
private final IntroAgentMain introAgentMain;

public Hello_node(IntroAgentMain introAgentMain)
{
  super();
  this.introAgentMain = introAgentMain;
}

/*START_PROCESS*/ public int process()
{
  int res = 0;
  res = setup_hello_node();
  if (res != 0)
    return res - 1;
  res = pass_by_hello_node();
  if (res != 0)
    return res - 1;
  res = hello_node_interruptive_edge_1();
  if (res != 0)
    return res - 1;
  res = hello_node_in();
  if (res != 0)
    return res - 1;
  res = hello_node_out();
  if (res != 0)
    return res - 1;
  res = hi_node();
  if (res != 0)
    return res - 1;
  res = how_node();
  if (res != 0)
    return res - 1;
  res = wait_node();
  if (res != 0)
    return res - 1;
  res = answer_node();
  if (res != 0)
    return res - 1;
  res = excuse_node();
  if (res != 0)
    return res - 1;
  res = new de.dfki.introduction.Pos_node(this, introAgentMain).process();
  if (res < 0)
    return res - 1;
  res = new de.dfki.introduction.Neg_node(this, introAgentMain).process();
  if (res < 0)
    return res - 1;
  return 0;
}

/*END_PROCESS*/ public int setup_hello_node()
{
  boolean[] __x1 = new boolean[2];
  __x1[0]        = (__x1[1] = introAgentMain.global != null && introAgentMain.exists(((Set<Object>)introAgentMain.global.getValue("<cat:initiated>"))) && ((Set<Object>)introAgentMain.global.getValue("<cat:initiated>")).contains("hello_node"));
  introAgentMain.logRule(1, __x1);
setup_hello_node:
  if (__x1[0]) {
    if (!(introAgentMain.hello_node != null && ((Boolean)introAgentMain.hello_node.getSingleValue("<cat:active>")))) {
      introAgentMain.hello_node.setValue("<cat:active>", true);
      ((Set<Object>)introAgentMain.global.getValue("<cat:super_children>")).add(introAgentMain.hello_node);
      introAgentMain.hello_node.setValue("<cat:parent>", introAgentMain.global);
// var_timedOut = new Variable;
// var_timedOut.name = "timedOut";
// var_timedOut.valueBool = false;
// hello_node.variables += var_timedOut;

      introAgentMain.hello_node.setValue("<cat:timedOut>", false);
    }

    ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).add("hello_node_in");
    ((Set<Object>)introAgentMain.global.getValue("<cat:initiated>")).remove("hello_node");
  }

  return 0;
}

public int pass_by_hello_node()
{
  boolean[] __x2 = new boolean[2];
  __x2[0]        = !((__x2[1] = introAgentMain.hello_node != null && ((Boolean)introAgentMain.hello_node.getSingleValue("<cat:active>"))));
  introAgentMain.logRule(2, __x2);
pass_by_hello_node:
  if (__x2[0]) {
    return 1;
  }

  return 0;
}

public int hello_node_interruptive_edge_1()
{
  boolean[] __x3 = new boolean[2];
  __x3[0]        = (__x3[1] = introAgentMain.lastDA().isSubsumedBy(new DialogueAct("InitialGoodbye", "Bye")));
  introAgentMain.logRule(3, __x3);
hello_node_interruptive_edge_1:
  if (__x3[0]) {
    introAgentMain.interruptive_transition(introAgentMain.hello_node, introAgentMain.global, "bye_node");
    return 1;
  }

  return 0;
}

public int hello_node_in()
{
  boolean[] __x4 = new boolean[2];
  __x4[0]        = (__x4[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("hello_node_in"));
  introAgentMain.logRule(4, __x4);
hello_node_in:
  if (__x4[0]) {
    introAgentMain.transition("hello_node_in", "hi_node", introAgentMain.hello_node, introAgentMain.hello_node);
  }

  return 0;
}

public int hello_node_out()
{
  boolean[] __x5 = new boolean[2];
  __x5[0]        = (__x5[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("hello_node_out"));
  introAgentMain.logRule(5, __x5);
hello_node_out:
  if (__x5[0]) {
// Bei der Transition sollten die Variablen-Bindings aus hello_node (nicht global) benutzt werden (wichtig nur für Conditionals)

    introAgentMain.transition("hello_node_out", "bye_node", introAgentMain.hello_node, introAgentMain.global);
    if (introAgentMain.test_inactive(introAgentMain.hello_node)) {
      ((Set<Object>)introAgentMain.global.getValue("<cat:super_children>")).remove(introAgentMain.hello_node);
      introAgentMain.set_inactive(introAgentMain.hello_node);
    }

    introAgentMain.check_out_transition("hello_node_out", "main_out", introAgentMain.hello_node);
  }

  return 0;
}

public int hi_node()
{
  boolean[] __x6 = new boolean[2];
  __x6[0]        = (__x6[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("hi_node"));
  introAgentMain.logRule(6, __x6);
hi_node:
  if (__x6[0]) {
    if (!(introAgentMain.hasActiveTimeout("hi_node"))) {
      introAgentMain.emitDA(new DialogueAct("InitialGreeting", "Greet"));
    }

    introAgentMain.timeout_transition("hi_node", "how_node", introAgentMain.hello_node, introAgentMain.hello_node, 5000);
    if (introAgentMain.lastDA().isSubsumedBy(new DialogueAct("InitialGreeting", "Greet"))) {
      introAgentMain.transition("hi_node", "how_node", introAgentMain.hello_node, introAgentMain.hello_node);
    }

    introAgentMain.check_out_transition("hi_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int how_node()
{
  boolean[] __x7 = new boolean[2];
  __x7[0]        = (__x7[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("how_node"));
  introAgentMain.logRule(7, __x7);
how_node:
  if (__x7[0]) {
    introAgentMain.emitDA(new DialogueAct("ChoiceQuestion", "Mood"));
    introAgentMain.transition("how_node", "wait_node", introAgentMain.hello_node, introAgentMain.hello_node);
    introAgentMain.check_out_transition("how_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int wait_node()
{
  boolean[] __x8 = new boolean[2];
  __x8[0]        = (__x8[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("wait_node"));
  introAgentMain.logRule(8, __x8);
wait_node:
  if (__x8[0]) {
    introAgentMain.timeout_transition("wait_node", "excuse_node", introAgentMain.hello_node, introAgentMain.hello_node, 16000);
    if (introAgentMain.lastDA().isSubsumedBy(new DialogueAct("Inform", "Mood"))) {
      if (((String)introAgentMain.lastDA().getValue("what")).equals("negative")) {
        introAgentMain.super_transition("wait_node", introAgentMain.hello_node, "neg_node");
      }

      if (((String)introAgentMain.lastDA().getValue("what")).equals("positive")) {
        introAgentMain.super_transition("wait_node", introAgentMain.hello_node, "pos_node");
      }
    }

    introAgentMain.check_out_transition("wait_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int answer_node()
{
  boolean[] __x9 = new boolean[2];
  __x9[0]        = (__x9[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("answer_node"));
  introAgentMain.logRule(9, __x9);
answer_node:
  if (__x9[0]) {
    introAgentMain.emitDA(new DialogueAct("Inform", "Mood"));
    introAgentMain.transition("answer_node", "excuse_node", introAgentMain.hello_node, introAgentMain.hello_node);
    introAgentMain.check_out_transition("answer_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int excuse_node()
{
  boolean[] __x10 = new boolean[2];
  __x10[0]        = (__x10[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("excuse_node"));
  introAgentMain.logRule(10, __x10);
excuse_node:
  if (__x10[0]) {
    introAgentMain.emitDA(new DialogueAct("InitialGoodbye", "Leave"));
    introAgentMain.check_out_transition("excuse_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}
}
