package de.dfki.introduction;
import java.util.*;

import de.dfki.mlt.rudimant.agent.DialogueAct;
import de.dfki.lt.hfc.db.rdfProxy.*;
import de.dfki.lt.hfc.types.*;
import de.dfki.introduction.MainAgent;
public class Hello_node {
private final MainAgent mainAgent;

public Hello_node(MainAgent mainAgent)
{
  super();
  this.mainAgent = mainAgent;
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
  res = hello_node_interruptive_edge_2();
  if (res != 0)
    return res - 1;
  res = hello_node_in();
  if (res != 0)
    return res - 1;
  res = hello_node_out();
  if (res != 0)
    return res - 1;
  res = something_else_node();
  if (res != 0)
    return res - 1;
  res = what_node();
  if (res != 0)
    return res - 1;
  res = nevermind_node();
  if (res != 0)
    return res - 1;
  res = mean_leave_node();
  if (res != 0)
    return res - 1;
  res = timed_out_node();
  if (res != 0)
    return res - 1;
  res = nice_leave_node();
  if (res != 0)
    return res - 1;
  res = answer_node();
  if (res != 0)
    return res - 1;
  res = choose_valediction_node();
  if (res != 0)
    return res - 1;
  res = thanks_node();
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
  res = new de.dfki.introduction.Pos_node(this, mainAgent).process();
  if (res < 0)
    return res - 1;
  res = new de.dfki.introduction.Neg_node(this, mainAgent).process();
  if (res < 0)
    return res - 1;
  return 0;
}

/*END_PROCESS*/ public int setup_hello_node()
{
  boolean[] __x5 = new boolean[2];
  __x5[0]        = (__x5[1] = mainAgent.mainAgent != null && mainAgent.exists(((Set<Object>)mainAgent.mainAgent.getValue("<cat:initiated>"))) && ((Set<Object>)mainAgent.mainAgent.getValue("<cat:initiated>")).contains("hello_node"));
  mainAgent.logRule(5, __x5);
setup_hello_node:
  if (__x5[0]) {
    if (!(mainAgent.hello_node != null && ((Boolean)mainAgent.hello_node.getSingleValue("<cat:active>")))) {
      mainAgent.hello_node.setValue("<cat:active>", true);
      ((Set<Object>)mainAgent.mainAgent.getValue("<cat:super_children>")).add(mainAgent.hello_node);
      mainAgent.hello_node.setValue("<cat:timedOut>", true);
    }

    ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).add("hello_node_in");
    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).add("hello_node_in");
    ((Set<Object>)mainAgent.mainAgent.getValue("<cat:initiated>")).remove("hello_node");
  }

  return 0;
}

public int pass_by_hello_node()
{
  boolean[] __x6 = new boolean[2];
  __x6[0]        = !((__x6[1] = mainAgent.hello_node != null && ((Boolean)mainAgent.hello_node.getSingleValue("<cat:active>"))));
  mainAgent.logRule(6, __x6);
pass_by_hello_node:
  if (__x6[0]) {
    return 1;
  }

  return 0;
}

public int hello_node_interruptive_edge_1()
{
  boolean[] __x7 = new boolean[2];
  __x7[0]        = (__x7[1] = mainAgent.lastDA().isSubsumedBy(new DialogueAct("InitialGoodbye", "Bye")));
  mainAgent.logRule(7, __x7);
hello_node_interruptive_edge_1:
  if (__x7[0]) {
    mainAgent.interruptive_transition(mainAgent.hello_node, mainAgent.mainAgent, "bye_node", false);
    return 1;
  }

  return 0;
}

public int hello_node_interruptive_edge_2()
{
  boolean[] __x8 = new boolean[2];
  __x8[0]        = (__x8[1] = ((Integer)mainAgent.mainAgent.getSingleValue("<cat:nice>")) < -(1));
  mainAgent.logRule(8, __x8);
hello_node_interruptive_edge_2:
  if (__x8[0]) {
    mainAgent.interruptive_transition(mainAgent.hello_node, mainAgent.mainAgent, "excuse_node", false);
    return 1;
  }

  return 0;
}

public int hello_node_in()
{
  boolean[] __x9 = new boolean[2];
  __x9[0]        = (__x9[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("hello_node_in"));
  mainAgent.logRule(9, __x9);
hello_node_in:
  if (__x9[0]) {
    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("hello_node_in");
    mainAgent.transition("hello_node_in", "hi_node", mainAgent.hello_node, mainAgent.hello_node, false);
    mainAgent.check_out_transition("hello_node_in", "hello_node_out", mainAgent.hello_node, mainAgent.hello_node);
  }

  return 0;
}

public int hello_node_out()
{
  boolean[] __x10 = new boolean[2];
  __x10[0]        = (__x10[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("hello_node_out"));
  mainAgent.logRule(10, __x10);
hello_node_out:
  if (__x10[0]) {
    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("hello_node_out");
    mainAgent.transition("hello_node_out", "bye_node", mainAgent.hello_node, mainAgent.mainAgent, false);
    if (mainAgent.test_inactive(mainAgent.hello_node)) {
      ((Set<Object>)mainAgent.mainAgent.getValue("<cat:super_children>")).remove(mainAgent.hello_node);
      mainAgent.set_inactive(mainAgent.hello_node);
    }

    mainAgent.check_out_transition("hello_node_out", "mainAgent_out", mainAgent.hello_node, mainAgent.mainAgent);
  }

  return 0;
}

public int something_else_node()
{
  boolean[] __x11 = new boolean[2];
  __x11[0]        = (__x11[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("something_else_node"));
  mainAgent.logRule(11, __x11);
something_else_node:
  if (__x11[0]) {
    if (mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).contains("something_else_node")) {
      mainAgent.emitDA(new DialogueAct("Question", "MoreTalking"));
      mainAgent.lastDAprocessed();
    }

    mainAgent.timeout_transition("something_else_node", "choose_valediction_node", mainAgent.hello_node, mainAgent.hello_node, false, 20000);
    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("something_else_node");
    if (mainAgent.lastDA().isSubsumedBy(new DialogueAct("Confirm", "MoreTalking"))) {
      mainAgent.transition("something_else_node", "what_node", mainAgent.hello_node, mainAgent.hello_node, false);
    }

    if (mainAgent.lastDA().isSubsumedBy(new DialogueAct("Disconfirm", "MoreTalking"))) {
      mainAgent.transition("something_else_node", "choose_valediction_node", mainAgent.hello_node, mainAgent.hello_node, false);
    }

    if (mainAgent.lastDA().isSubsumedBy(new DialogueAct("Question", "Mood"))) {
      mainAgent.transition("something_else_node", "answer_node", mainAgent.hello_node, mainAgent.hello_node, false);
    }

    mainAgent.check_out_transition("something_else_node", "hello_node_out", mainAgent.hello_node, mainAgent.hello_node);
  }

  return 0;
}

public int what_node()
{
  boolean[] __x12 = new boolean[2];
  __x12[0]        = (__x12[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("what_node"));
  mainAgent.logRule(12, __x12);
what_node:
  if (__x12[0]) {
    if (mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).contains("what_node")) {
      mainAgent.emitDA(new DialogueAct("Suggestion", "MoreTalking"));
      mainAgent.lastDAprocessed();
    }

    mainAgent.timeout_transition("what_node", "thanks_node", mainAgent.hello_node, mainAgent.hello_node, false, 20000);
    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("what_node");
    if (mainAgent.lastDA().isSubsumedBy(new DialogueAct("DeclineSuggestion", "MoreTalking"))) {
      mainAgent.transition("what_node", "nevermind_node", mainAgent.hello_node, mainAgent.hello_node, false);
    }

    mainAgent.check_out_transition("what_node", "hello_node_out", mainAgent.hello_node, mainAgent.hello_node);
  }

  return 0;
}

public int nevermind_node()
{
  boolean[] __x13 = new boolean[2];
  __x13[0]        = (__x13[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("nevermind_node"));
  mainAgent.logRule(13, __x13);
nevermind_node:
  if (__x13[0]) {
    if (mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).contains("nevermind_node")) {
      mainAgent.emitDA(new DialogueAct("SocialFunction", "NoTrust"));
      mainAgent.mainAgent.setValue("<cat:nice>", (((Integer)mainAgent.mainAgent.getSingleValue("<cat:nice>"))-1));
      mainAgent.lastDAprocessed();
    }

    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("nevermind_node");
    mainAgent.transition("nevermind_node", "something_else_node", mainAgent.hello_node, mainAgent.hello_node, false);
    mainAgent.check_out_transition("nevermind_node", "hello_node_out", mainAgent.hello_node, mainAgent.hello_node);
  }

  return 0;
}

public int mean_leave_node()
{
  boolean[] __x14 = new boolean[2];
  __x14[0]        = (__x14[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("mean_leave_node"));
  mainAgent.logRule(14, __x14);
mean_leave_node:
  if (__x14[0]) {
    if (mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).contains("mean_leave_node")) {
      mainAgent.emitDA(new DialogueAct("InitialGoodbye", "Mean"));
      mainAgent.lastDAprocessed();
    }

    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("mean_leave_node");
    mainAgent.check_out_transition("mean_leave_node", "hello_node_out", mainAgent.hello_node, mainAgent.hello_node);
  }

  return 0;
}

public int timed_out_node()
{
  boolean[] __x15 = new boolean[2];
  __x15[0]        = (__x15[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("timed_out_node"));
  mainAgent.logRule(15, __x15);
timed_out_node:
  if (__x15[0]) {
    if (mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).contains("timed_out_node")) {
      mainAgent.hello_node.setValue("<cat:timedOut>", true);
      mainAgent.mainAgent.setValue("<cat:nice>", (((Integer)mainAgent.mainAgent.getSingleValue("<cat:nice>"))-1));
      mainAgent.lastDAprocessed();
    }

    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("timed_out_node");
    mainAgent.transition("timed_out_node", "how_node", mainAgent.hello_node, mainAgent.hello_node, false);
    mainAgent.check_out_transition("timed_out_node", "hello_node_out", mainAgent.hello_node, mainAgent.hello_node);
  }

  return 0;
}

public int nice_leave_node()
{
  boolean[] __x16 = new boolean[2];
  __x16[0]        = (__x16[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("nice_leave_node"));
  mainAgent.logRule(16, __x16);
nice_leave_node:
  if (__x16[0]) {
    if (mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).contains("nice_leave_node")) {
      mainAgent.emitDA(new DialogueAct("InitialGoodbye", "Nice"));
      mainAgent.lastDAprocessed();
    }

    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("nice_leave_node");
    mainAgent.check_out_transition("nice_leave_node", "hello_node_out", mainAgent.hello_node, mainAgent.hello_node);
  }

  return 0;
}

public int answer_node()
{
  boolean[] __x17 = new boolean[2];
  __x17[0]        = (__x17[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("answer_node"));
  mainAgent.logRule(17, __x17);
answer_node:
  if (__x17[0]) {
    if (mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).contains("answer_node")) {
      mainAgent.emitDA(new DialogueAct("Inform", "Mood"));
      mainAgent.mainAgent.setValue("<cat:nice>", (((Integer)mainAgent.mainAgent.getSingleValue("<cat:nice>"))+2));
      mainAgent.lastDAprocessed();
    }

    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("answer_node");
    mainAgent.transition("answer_node", "something_else_node", mainAgent.hello_node, mainAgent.hello_node, false);
    mainAgent.check_out_transition("answer_node", "hello_node_out", mainAgent.hello_node, mainAgent.hello_node);
  }

  return 0;
}

public int choose_valediction_node()
{
  boolean[] __x18 = new boolean[2];
  __x18[0]        = (__x18[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("choose_valediction_node"));
  mainAgent.logRule(18, __x18);
choose_valediction_node:
  if (__x18[0]) {
    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("choose_valediction_node");
    if (((Integer)mainAgent.mainAgent.getSingleValue("<cat:nice>")) > 0) {
      mainAgent.transition("choose_valediction_node", "nice_leave_node", mainAgent.hello_node, mainAgent.hello_node, false);
    }

    if (((Integer)mainAgent.mainAgent.getSingleValue("<cat:nice>")) <= 0) {
      mainAgent.transition("choose_valediction_node", "mean_leave_node", mainAgent.hello_node, mainAgent.hello_node, false);
    }

    mainAgent.check_out_transition("choose_valediction_node", "hello_node_out", mainAgent.hello_node, mainAgent.hello_node);
  }

  return 0;
}

public int thanks_node()
{
  boolean[] __x19 = new boolean[2];
  __x19[0]        = (__x19[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("thanks_node"));
  mainAgent.logRule(19, __x19);
thanks_node:
  if (__x19[0]) {
    if (mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).contains("thanks_node")) {
      mainAgent.emitDA(new DialogueAct("Thanking", "Sharing"));
      mainAgent.mainAgent.setValue("<cat:nice>", (((Integer)mainAgent.mainAgent.getSingleValue("<cat:nice>"))+1));
      mainAgent.lastDAprocessed();
    }

    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("thanks_node");
    mainAgent.transition("thanks_node", "something_else_node", mainAgent.hello_node, mainAgent.hello_node, false);
    mainAgent.check_out_transition("thanks_node", "hello_node_out", mainAgent.hello_node, mainAgent.hello_node);
  }

  return 0;
}

public int hi_node()
{
  boolean[] __x20 = new boolean[2];
  __x20[0]        = (__x20[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("hi_node"));
  mainAgent.logRule(20, __x20);
hi_node:
  if (__x20[0]) {
    if (mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).contains("hi_node")) {
      mainAgent.emitDA(new DialogueAct("InitialGreeting", "Greet"));
      mainAgent.hello_node.setValue("<cat:timedOut>", false);
      mainAgent.lastDAprocessed();
    }

    mainAgent.timeout_transition("hi_node", "timed_out_node", mainAgent.hello_node, mainAgent.hello_node, false, 5000);
    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("hi_node");
    if (mainAgent.lastDA().isSubsumedBy(new DialogueAct("InitialGreeting", "Greet"))) {
      mainAgent.transition("hi_node", "how_node", mainAgent.hello_node, mainAgent.hello_node, false);
    }

    mainAgent.check_out_transition("hi_node", "hello_node_out", mainAgent.hello_node, mainAgent.hello_node);
  }

  return 0;
}

public int how_node()
{
  boolean[] __x21 = new boolean[2];
  __x21[0]        = (__x21[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("how_node"));
  mainAgent.logRule(21, __x21);
how_node:
  if (__x21[0]) {
    if (mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).contains("how_node")) {
      mainAgent.emitDA(new DialogueAct("ChoiceQuestion", "Mood"));
      mainAgent.lastDAprocessed();
    }

    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("how_node");
    mainAgent.transition("how_node", "wait_node", mainAgent.hello_node, mainAgent.hello_node, false);
    mainAgent.check_out_transition("how_node", "hello_node_out", mainAgent.hello_node, mainAgent.hello_node);
  }

  return 0;
}

public int wait_node()
{
  boolean[] __x22 = new boolean[2];
  __x22[0]        = (__x22[1] = mainAgent.hello_node != null && mainAgent.exists(((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)mainAgent.hello_node.getValue("<cat:simple_children>")).contains("wait_node"));
  mainAgent.logRule(22, __x22);
wait_node:
  if (__x22[0]) {
    mainAgent.timeout_transition("wait_node", "timed_out_node", mainAgent.hello_node, mainAgent.hello_node, false, 20000);
    ((Set<Object>)mainAgent.hello_node.getValue("<cat:imminent_simple_children>")).remove("wait_node");
    if (mainAgent.lastDA().isSubsumedBy(new DialogueAct("Question", "Mood"))) {
      mainAgent.transition("wait_node", "answer_node", mainAgent.hello_node, mainAgent.hello_node, false);
    }

    if ((mainAgent.lastDA().isSubsumedBy(new DialogueAct("Inform", "Mood"))) && (((String)mainAgent.lastDA().getValue("what")).equals("negative"))) {
      mainAgent.transition("wait_node", "neg_node", mainAgent.hello_node, mainAgent.hello_node, true);
    }

    if ((mainAgent.lastDA().isSubsumedBy(new DialogueAct("Inform", "Mood"))) && (((String)mainAgent.lastDA().getValue("what")).equals("positive"))) {
      mainAgent.transition("wait_node", "pos_node", mainAgent.hello_node, mainAgent.hello_node, true);
    }

    mainAgent.check_out_transition("wait_node", "hello_node_out", mainAgent.hello_node, mainAgent.hello_node);
  }

  return 0;
}
}
