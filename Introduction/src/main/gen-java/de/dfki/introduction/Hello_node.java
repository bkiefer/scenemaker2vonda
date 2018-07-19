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
  res = hello_node_interruptive_edge_2();
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
  res = timed_out_node();
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
  res = something_else_node();
  if (res != 0)
    return res - 1;
  res = what_node();
  if (res != 0)
    return res - 1;
  res = thanks_node();
  if (res != 0)
    return res - 1;
  res = nevermind_node();
  if (res != 0)
    return res - 1;
  res = choose_valediction_node();
  if (res != 0)
    return res - 1;
  res = nice_leave_node();
  if (res != 0)
    return res - 1;
  res = mean_leave_node();
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
  boolean[] __x5 = new boolean[2];
  __x5[0]        = (__x5[1] = introAgentMain.global != null && introAgentMain.exists(((Set<Object>)introAgentMain.global.getValue("<cat:initiated>"))) && ((Set<Object>)introAgentMain.global.getValue("<cat:initiated>")).contains("hello_node"));
  introAgentMain.logRule(5, __x5);
setup_hello_node:
  if (__x5[0]) {
    if (!(introAgentMain.hello_node != null && ((Boolean)introAgentMain.hello_node.getSingleValue("<cat:active>")))) {
      introAgentMain.hello_node.setValue("<cat:active>", true);
      ((Set<Object>)introAgentMain.global.getValue("<cat:super_children>")).add(introAgentMain.hello_node);
      introAgentMain.hello_node.setValue("<cat:parent>", introAgentMain.global);
// var_timedOut = new Variable;
// var_timedOut.name = "timedOut";
// var_timedOut.valueBool = false;
// hello_node.variables += var_timedOut;

      introAgentMain.hello_node.setValue("<cat:timedOut>", true);
    }

    ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).add("hello_node_in");
    ((Set<Object>)introAgentMain.global.getValue("<cat:initiated>")).remove("hello_node");
  }

  return 0;
}

public int pass_by_hello_node()
{
  boolean[] __x6 = new boolean[2];
  __x6[0]        = !((__x6[1] = introAgentMain.hello_node != null && ((Boolean)introAgentMain.hello_node.getSingleValue("<cat:active>"))));
  introAgentMain.logRule(6, __x6);
pass_by_hello_node:
  if (__x6[0]) {
    return 1;
  }

  return 0;
}

public int hello_node_interruptive_edge_1()
{
  boolean[] __x7 = new boolean[2];
  __x7[0]        = (__x7[1] = introAgentMain.lastDA().isSubsumedBy(new DialogueAct("InitialGoodbye", "Bye")));
  introAgentMain.logRule(7, __x7);
hello_node_interruptive_edge_1:
  if (__x7[0]) {
    introAgentMain.interruptive_transition(introAgentMain.hello_node, introAgentMain.global, "bye_node");
    return 1;
  }

  return 0;
}

public int hello_node_interruptive_edge_2()
{
  boolean[] __x8 = new boolean[2];
  __x8[0]        = (__x8[1] = ((Integer)introAgentMain.global.getSingleValue("<cat:nice>")) <= -(2));
  introAgentMain.logRule(8, __x8);
hello_node_interruptive_edge_2:
  if (__x8[0]) {
    introAgentMain.interruptive_transition(introAgentMain.hello_node, introAgentMain.global, "excuse_node");
    return 1;
  }

  return 0;
}

public int hello_node_in()
{
  boolean[] __x9 = new boolean[2];
  __x9[0]        = (__x9[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("hello_node_in"));
  introAgentMain.logRule(9, __x9);
hello_node_in:
  if (__x9[0]) {
    introAgentMain.transition("hello_node_in", "hi_node", introAgentMain.hello_node, introAgentMain.hello_node);
  }

  return 0;
}

public int hello_node_out()
{
  boolean[] __x10 = new boolean[2];
  __x10[0]        = (__x10[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("hello_node_out"));
  introAgentMain.logRule(10, __x10);
hello_node_out:
  if (__x10[0]) {
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
  boolean[] __x11 = new boolean[2];
  __x11[0]        = (__x11[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("hi_node"));
  introAgentMain.logRule(11, __x11);
hi_node:
  if (__x11[0]) {
    if (!(introAgentMain.hasActiveTimeout("hi_node"))) {
      introAgentMain.emitDA(new DialogueAct("InitialGreeting", "Greet"));
      introAgentMain.hello_node.setValue("<cat:timedOut>", false);
    }

    introAgentMain.timeout_transition("hi_node", "how_node", introAgentMain.hello_node, introAgentMain.hello_node, 5000);
    if (introAgentMain.lastDA().isSubsumedBy(new DialogueAct("InitialGreeting", "Greet"))) {
      introAgentMain.transition("hi_node", "how_node", introAgentMain.hello_node, introAgentMain.hello_node);
    }

    introAgentMain.check_out_transition("hi_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int timed_out_node()
{
  boolean[] __x12 = new boolean[2];
  __x12[0]        = (__x12[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("timed_out_node"));
  introAgentMain.logRule(12, __x12);
timed_out_node:
  if (__x12[0]) {
    if (!(introAgentMain.hasActiveTimeout("timed_out_node"))) {
      introAgentMain.hello_node.setValue("<cat:timedOut>", true);
      introAgentMain.global.setValue("<cat:nice>", (((Integer)introAgentMain.global.getSingleValue("<cat:nice>"))-1));
    }

    introAgentMain.transition("timed_out_node", "how_node", introAgentMain.hello_node, introAgentMain.hello_node);
    introAgentMain.check_out_transition("timed_out_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int how_node()
{
  boolean[] __x13 = new boolean[2];
  __x13[0]        = (__x13[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("how_node"));
  introAgentMain.logRule(13, __x13);
how_node:
  if (__x13[0]) {
    introAgentMain.emitDA(new DialogueAct("ChoiceQuestion", "Mood"));
    introAgentMain.transition("how_node", "wait_node", introAgentMain.hello_node, introAgentMain.hello_node);
    introAgentMain.check_out_transition("how_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int wait_node()
{
  boolean[] __x14 = new boolean[2];
  __x14[0]        = (__x14[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("wait_node"));
  introAgentMain.logRule(14, __x14);
wait_node:
  if (__x14[0]) {
    introAgentMain.timeout_transition("wait_node", "timed_out_node", introAgentMain.hello_node, introAgentMain.hello_node, 20000);
    if (introAgentMain.lastDA().isSubsumedBy(new DialogueAct("Question", "Mood"))) {
      introAgentMain.transition("wait_node", "answer_node", introAgentMain.hello_node, introAgentMain.hello_node);
    }

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
  boolean[] __x15 = new boolean[2];
  __x15[0]        = (__x15[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("answer_node"));
  introAgentMain.logRule(15, __x15);
answer_node:
  if (__x15[0]) {
    if (!(introAgentMain.hasActiveTimeout("answer_node"))) {
      introAgentMain.emitDA(new DialogueAct("Inform", "Mood"));
      introAgentMain.global.setValue("<cat:nice>", (((Integer)introAgentMain.global.getSingleValue("<cat:nice>"))+2));
    }

    introAgentMain.transition("answer_node", "something_else_node", introAgentMain.hello_node, introAgentMain.hello_node);
    introAgentMain.check_out_transition("answer_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int something_else_node()
{
  boolean[] __x16 = new boolean[2];
  __x16[0]        = (__x16[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("something_else_node"));
  introAgentMain.logRule(16, __x16);
something_else_node:
  if (__x16[0]) {
    if (!(introAgentMain.hasActiveTimeout("something_else_node"))) {
      introAgentMain.emitDA(new DialogueAct("Question", "MoreTalking"));
    }

    introAgentMain.timeout_transition("something_else_node", "choose_valediction_node", introAgentMain.hello_node, introAgentMain.hello_node, 20000);
    if (introAgentMain.lastDA().isSubsumedBy(new DialogueAct("Question", "Mood"))) {
      introAgentMain.transition("wait_node", "answer_node", introAgentMain.hello_node, introAgentMain.hello_node);
    }

    if (introAgentMain.lastDA().isSubsumedBy(new DialogueAct("Disconfirm", "MoreTalking"))) {
      introAgentMain.transition("something_else_node", "choose_valediction_node", introAgentMain.hello_node, introAgentMain.hello_node);
    }

    if (introAgentMain.lastDA().isSubsumedBy(new DialogueAct("Confirm", "MoreTalking"))) {
      introAgentMain.transition("something_else_node", "what_node", introAgentMain.hello_node, introAgentMain.hello_node);
    }

    introAgentMain.check_out_transition("something_else_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int what_node()
{
  boolean[] __x17 = new boolean[2];
  __x17[0]        = (__x17[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("what_node"));
  introAgentMain.logRule(17, __x17);
what_node:
  if (__x17[0]) {
    if (!(introAgentMain.hasActiveTimeout("what_node"))) {
      introAgentMain.emitDA(new DialogueAct("Suggestion", "MoreTalking"));
    }

    introAgentMain.timeout_transition("what_node", "thanks_node", introAgentMain.hello_node, introAgentMain.hello_node, 15000);
    if (introAgentMain.lastDA().isSubsumedBy(new DialogueAct("DeclineSuggestion", "MoreTalking"))) {
      introAgentMain.transition("what_node", "nevermind_node", introAgentMain.hello_node, introAgentMain.hello_node);
    }

    introAgentMain.check_out_transition("what_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int thanks_node()
{
  boolean[] __x18 = new boolean[2];
  __x18[0]        = (__x18[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("thanks_node"));
  introAgentMain.logRule(18, __x18);
thanks_node:
  if (__x18[0]) {
    if (!(introAgentMain.hasActiveTimeout("thanks_node"))) {
      introAgentMain.emitDA(new DialogueAct("Thanking", "Sharing"));
      introAgentMain.global.setValue("<cat:nice>", (((Integer)introAgentMain.global.getSingleValue("<cat:nice>"))+1));
    }

    introAgentMain.transition("thanks_node", "something_else_node", introAgentMain.hello_node, introAgentMain.hello_node);
    introAgentMain.check_out_transition("thanks_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int nevermind_node()
{
  boolean[] __x19 = new boolean[2];
  __x19[0]        = (__x19[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("nevermind_node"));
  introAgentMain.logRule(19, __x19);
nevermind_node:
  if (__x19[0]) {
    if (!(introAgentMain.hasActiveTimeout("nevermind_node"))) {
      introAgentMain.emitDA(new DialogueAct("SocialFunction", "NoTrust"));
      introAgentMain.global.setValue("<cat:nice>", (((Integer)introAgentMain.global.getSingleValue("<cat:nice>"))-1));
    }

    introAgentMain.transition("nevermind_node", "something_else_node", introAgentMain.hello_node, introAgentMain.hello_node);
    introAgentMain.check_out_transition("nevermind_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int choose_valediction_node()
{
  boolean[] __x20 = new boolean[2];
  __x20[0]        = (__x20[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("choose_valediction_node"));
  introAgentMain.logRule(20, __x20);
choose_valediction_node:
  if (__x20[0]) {
    if (((Integer)introAgentMain.global.getSingleValue("<cat:nice>")) > 0) {
      introAgentMain.transition("choose_valediction_node", "nice_leave_node", introAgentMain.hello_node, introAgentMain.hello_node);
    }

    if (((Integer)introAgentMain.global.getSingleValue("<cat:nice>")) <= 0) {
      introAgentMain.transition("choose_valediction_node", "mean_leave_node", introAgentMain.hello_node, introAgentMain.hello_node);
    }

    introAgentMain.check_out_transition("choose_valediction_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int nice_leave_node()
{
  boolean[] __x21 = new boolean[2];
  __x21[0]        = (__x21[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("nice_leave_node"));
  introAgentMain.logRule(21, __x21);
nice_leave_node:
  if (__x21[0]) {
    if (!(introAgentMain.hasActiveTimeout("nice_leave_node"))) {
      introAgentMain.emitDA(new DialogueAct("InitialGoodbye", "Nice"));
    }

    introAgentMain.check_out_transition("nice_leave_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}

public int mean_leave_node()
{
  boolean[] __x22 = new boolean[2];
  __x22[0]        = (__x22[1] = introAgentMain.hello_node != null && introAgentMain.exists(((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>"))) && ((Set<Object>)introAgentMain.hello_node.getValue("<cat:simple_children>")).contains("mean_leave_node"));
  introAgentMain.logRule(22, __x22);
mean_leave_node:
  if (__x22[0]) {
    if (!(introAgentMain.hasActiveTimeout("mean_leave_node"))) {
      introAgentMain.emitDA(new DialogueAct("InitialGoodbye", "Mean"));
    }

    introAgentMain.check_out_transition("mean_leave_node", "hello_node_out", introAgentMain.hello_node);
  }

  return 0;
}
}
