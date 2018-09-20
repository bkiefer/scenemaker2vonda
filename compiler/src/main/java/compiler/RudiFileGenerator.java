package compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.xml.internal.ws.util.StringUtils;

import compiler.automaton.Node;
import compiler.automaton.SceneMakerAutomaton;
import compiler.automaton.Supernode;

public class RudiFileGenerator {

  private SceneMakerAutomaton automaton;
  private String outPath;
  private boolean postProcessDialogueActs;

  public static String replaceVarNamesInString(String str, Node n) {

    String cleanString = str;

    Pattern VAR_TAG_PATTERN = Pattern.compile("<v>(.*?)</v>");
    Matcher m = VAR_TAG_PATTERN.matcher(cleanString);

    while (m.find()) {

      String varName = m.group(1);
      String extendedVarName = n.replaceVarName(varName);
      String stringToReplace = "<v>" + varName + "</v>";

      cleanString = cleanString.replace(stringToReplace, extendedVarName);
      m = VAR_TAG_PATTERN.matcher(cleanString);
    }

    return cleanString;
  }

  public static String formattedExpression(String exp, int numLeadingNewlines, int numLeadingTabs,
      int numAppendedNewlines) {

    String formattedExpression = new String(new char[numLeadingNewlines]).replace("\0", "\n");
    formattedExpression += new String(new char[numLeadingTabs]).replace("\0", "\t");
    formattedExpression += exp;
    formattedExpression += new String(new char[numAppendedNewlines]).replace("\0", "\n");

    return formattedExpression;
  }

  public static String formattedLine(String line, int numLeadingNewlines, int numLeadingTabs, int numAppendedNewlines) {

    return formattedExpression(line + ";", numLeadingNewlines, numLeadingTabs, numAppendedNewlines);
  }

  public static String formattedIfOpening(String condition, int numLeadingNewlines, int numLeadingTabs,
      int numAppendedNewlines) {

    return formattedExpression("if(" + condition + ") {", numLeadingNewlines, numLeadingTabs, numAppendedNewlines);
  }

  public static String formattedIfClosing(int numLeadingNewlines, int numLeadingTabs, int numAppendedNewlines) {

    return formattedExpression("}", numLeadingNewlines, numLeadingTabs, numAppendedNewlines);
  }

  public static String formattedRuleLabel(String label, int numLeadingNewlines, int numLeadingTabs,
      int numAppendedNewlines) {

    return formattedExpression(label + ":", numLeadingNewlines, numLeadingTabs, numAppendedNewlines);
  }

  public void writeSupernodeToFile(BufferedWriter fw, Supernode m) throws IOException {

    // If m is the top-level supernode, define utility functions (transitions
    // etc...) in this file
    if (m.getParent() == null) {
      fw.write(StringConstants.FUNCTIONS);
    }

    fw.write(m.getSetupCode());

    // Unless m is the top-level supernode, skip it in case no corresponding
    // instance exists in the ontology
    if (m.getParent() != null) {
      fw.write(m.getPassByCode());
    }

    // Post-process node and edge code and write it to the file
    fw.write(this.postProcessCode(m.getSuperInterruptiveEdgesCode()));
    fw.write(this.postProcessCode(m.getPseudoInCode()));
    fw.write(this.postProcessCode(m.getPseudoOutCode()));

    for (Node n : m.getNodes()) {
      if (n.isSupernode() == false) {
        fw.write(this.postProcessCode(n.getNodeCode()));
      }
    }

    // Add import statements to end of file (importing all sub-supernodes)
    fw.write(m.getImportCode());

  }

  public BufferedWriter getFileWriter(String filePath) {
    BufferedWriter writer = null;
    try {
      File file = new File(filePath);
      if (file.exists()) {
        file.delete();
      }

      file.getParentFile().mkdirs();
      file.createNewFile();

      FileWriter fw = new FileWriter(file);
      writer = new BufferedWriter(fw);

    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

    return writer;
  }

  public void generateChatAgentFile() throws IOException {

    String filePath = this.outPath + "ChatAgent.rudi";
    BufferedWriter fw = this.getFileWriter(filePath);

    for (Supernode m : this.automaton.getAllSupernodes()) {
      fw.write(StringUtils.capitalize(m.getName()) + " " + m.getName() + ";\n");
    }

    fw.close();
  }

  public void generateSupernodeFile(Supernode m) throws IOException {

    String filePath = this.outPath + StringUtils.capitalize(m.getName()) + ".rudi";
    BufferedWriter fw = this.getFileWriter(filePath);
    this.writeSupernodeToFile(fw, m);
    fw.close();
  }

  public void clearOutputDirectory() {
    File outDir = new File(this.outPath);

    if (outDir.exists()) {
      for (File f : outDir.listFiles()) {
        f.delete();
      }
    }
  }

  public void generateRudiFiles() {

    this.clearOutputDirectory();

    try {
      this.generateChatAgentFile();

      for (Supernode m : this.automaton.getAllSupernodes()) {
        generateSupernodeFile(m);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // If compiling from Scenemaker sceneflow (which does not allow hashtags in
  // commands), post-process node or edge code to include hashtags
  public String postProcessCode(String str) {

    String cleanedString = str;

    if (this.postProcessDialogueActs == true) {
      cleanedString = cleanedString.replaceAll("emitDA\\(", "emitDA(#");
      cleanedString = cleanedString.replaceAll("lastDA\\(\\)\\s*>=\\s*", "lastDA() >= #");

      return cleanedString;
    }

    return cleanedString;
  }

  public RudiFileGenerator(SceneMakerAutomaton automaton, String outPath, boolean postProcessDialogueActs) {
    this.automaton = automaton;
    this.outPath = outPath;
    this.postProcessDialogueActs = postProcessDialogueActs;
  }

}
