import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import compiler.automaton.Node;
import compiler.automaton.SceneMakerAutomaton;
import compiler.automaton.Supernode;
import compiler.automaton.Type;
import compiler.automaton.Variable;
import edges.ConditionalEdge;
import edges.EpsilonEdge;
import edges.ForkEdge;
import edges.InterruptiveEdge;
import edges.ProbabilityEdge;
import edges.TimeoutEdge;

/**
 * A parser for sceneflow XML files.
 * @author Max Depenbrock
 */
public class XmlParser {
  
  private HashMap<String, Node> nodes;
  private HashMap<String, Element> nodeElements;
  private SceneMakerAutomaton topLevel;
  
  /**
   * Creates a new {@code XmlParser} that immediately parses {@code file}.
   * @param file the file to parse
   */
  public XmlParser(File file) {
    this.nodes = new HashMap<>();
    this.nodeElements = new HashMap<>();
    
    try {
      SAXBuilder builder = CustomSaxBuilder.getSaxBuilder();
      Document doc = builder.build(file);
      parse(doc);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the {@code SceneMakerAutomaton} obtained from parsing the sceneflow XML file.
   * @return the resulting {@code SceneMakerAutomaton}
   */
  public SceneMakerAutomaton getSceneMakerAutomaton() {
    return this.topLevel;
  }

  private void parse(Document doc) {
    this.topLevel = new SceneMakerAutomaton();
    doc.getRootElement().setAttribute("id", "mainAgent");
    this.nodes.put("mainAgent", this.topLevel);
    // NB: topLevel is not put in this.nodeElements
  
    // variables / declares
    parseDeclare(doc.getRootElement().getChild("Declare"), this.topLevel);
    
    // commands / code
    parseCommands(doc.getRootElement().getChild("Commands"), this.topLevel);
    
    // nodes and supernodes
    parseNodes(doc.getRootElement().getChildren("Node"));
    parseSupernodes(doc.getRootElement().getChildren("SuperNode"));
    
    this.topLevel.setStartNodes(parseStart(doc.getRootElement().getAttributeValue("start")));
    this.topLevel.setName("mainAgent");
    this.topLevel.getAllSupernodes().add(this.topLevel);
  
    // iterate over this.nodeElements to generate edges
    for (String key : this.nodeElements.keySet()) {
      Node node = this.nodes.get(key);
      Element element = this.nodeElements.get(key);
      
      parseEdges(element, node);
    }
  }

  private void parseSupernodes(List<Element> list) {
    for (Element e : list) {
      Supernode node = new Supernode();
  
      this.nodes.put(e.getAttributeValue("id"), node);
      this.nodeElements.put(e.getAttributeValue("id"), e);
      this.topLevel.getAllSupernodes().add(node);
  
      parseSupernode(e, node);
      Supernode parent = (Supernode) this.nodes.get(e.getParentElement().getAttributeValue("id"));
      node.setParent(parent);
      parent.getNodes().add(node);
    }
  }

  private void parseSupernode(Element e, Supernode node) {
    node.setSupernode(true);;
    node.setName(e.getAttributeValue("name"));
    
    parseDeclare(e.getChild("Declare"), node);
    parseCommands(e.getChild("Commands"), node);
    parseNodes(e.getChildren("Node"));
    parseSupernodes(e.getChildren("SuperNode"));
    
    node.setStartNodes(parseStart(e.getAttributeValue("start")));
  }

  private void parseNodes(List<Element> list) {
    for (Element e : list) {
      Node node = new Node();
      
      this.nodes.put(e.getAttributeValue("id"), node);
      this.nodeElements.put(e.getAttributeValue("id"), e);
      
      parseNode(e, node);
      Supernode parent = (Supernode) this.nodes.get(e.getParentElement().getAttributeValue("id"));
      node.setParent(parent);
      parent.getNodes().add(node);
    }
  }

  private void parseNode(Element e, Node node) {
    node.setSupernode(false);
    node.setName(e.getAttributeValue("name"));

    parseDeclare(e.getChild("Declare"), node);
    parseCommands(e.getChild("Commands"), node);
  }
  
  private void parseDeclare(Element e, Node n) {
    for (Element def : e.getChildren()) {
      Variable v = parseVariable(def);
      //FIXME Also variables of normal nodes are stored
      v.setDomain(n);
      n.getVariables().add(v);
      this.topLevel.getAllVariables().add(v);
    }
  }
  
  private void parseCommands(Element e, Node n) {
    String code = "";
    for (Element com : e.getChildren()) {
      code += ExpressionParser.parse(com) + ";\n";
    }
    n.setCode(code);
  }
  
  private Variable parseVariable(Element e) {
    Variable var = new Variable();
    
    var.setName(e.getAttributeValue("name"));
    var.setValue(ExpressionParser.parse(e.getChildren().get(0)));
    
    switch (e.getAttributeValue("type")) {
      case "Int":
        var.setRange(Type.INT);
        break;
      case "String":
        var.setRange(Type.STRING);
        break;
      case "Bool":
        var.setRange(Type.BOOL);
        break;
      case "Float":
        var.setRange(Type.FLOAT);
        break;
      default:
    }
    
    return var;
  }

  private void parseEdges(Element e, Node node) {
    // epsilon edges
    for (Element edgeElement : e.getChildren("EEdge")) {
      Node target = this.nodes.get(edgeElement.getAttributeValue("target"));
      EpsilonEdge edge = new EpsilonEdge(node, target);
      node.getOutgoingEdges().add(edge);
    }
    
    // probability edges
    for (Element edgeElement : e.getChildren("PEdge")) {
      Node target = this.nodes.get(edgeElement.getAttributeValue("target"));
      ProbabilityEdge edge = new ProbabilityEdge(node, target);
      edge.setProbability(Float.valueOf(edgeElement.getAttributeValue("probability")));
      node.getOutgoingEdges().add(edge);
    }
    
    // conditional edges
    for (Element edgeElement : e.getChildren("CEdge")) {
      Node target = this.nodes.get(edgeElement.getAttributeValue("target"));
      ConditionalEdge edge = new ConditionalEdge(node, target);
      edge.setCondition(ExpressionParser.parse(edgeElement.getChild("ParenExpression")));
      node.getOutgoingEdges().add(edge);
    }
    
    // timeout edges
    for (Element edgeElement : e.getChildren("TEdge")) {
      Node target = this.nodes.get(edgeElement.getAttributeValue("target"));
      TimeoutEdge edge = new TimeoutEdge(node, target);
      edge.setTimeout(Integer.valueOf(edgeElement.getAttributeValue("timeout")));
      node.getOutgoingEdges().add(edge);
    }
    
    // fork edges
    for (Element edgeElement : e.getChildren("FEdge")) {
      Node target = this.nodes.get(edgeElement.getAttributeValue("target"));
      ForkEdge edge = new ForkEdge(node, target);
      node.getOutgoingEdges().add(edge);
    }
    
    // interruptive edges
    for (Element edgeElement : e.getChildren("IEdge")) {
      Node target = this.nodes.get(edgeElement.getAttributeValue("target"));
      InterruptiveEdge edge = new InterruptiveEdge(node, target);
      edge.setCondition(ExpressionParser.parse(edgeElement.getChild("ParenExpression")));
      node.getOutgoingEdges().add(edge);
    }
  }

  private Set<Node> parseStart(String s) {
    HashSet<Node> startNodes = new HashSet<>();
    String[] nodeIDs = s.split(";");
  
    for (String id : nodeIDs) {
      startNodes.add(this.nodes.get(id));
    }
    
    return startNodes;
  }
}
