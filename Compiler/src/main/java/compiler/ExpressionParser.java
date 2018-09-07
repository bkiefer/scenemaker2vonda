package compiler;
import java.util.List;

import org.jdom2.Element;

/** 
 * Expression parser for seceneflow expressions.
 * @author Max Depenbrock
 */
public class ExpressionParser {
  
  /**
   * Parses an expression in sceneflow.xml to a string.
   * @param e {@codeElement} to parse
   * @return {@code String} representation of parsed element
   */
  public static String parse(Element e) {
    switch (e.getName()) {
      case "CallingExpression":
        String arguments = "";
        List<Element> children = e.getChildren();
        
        if (!children.isEmpty()) {
          for (Element child : children) {
            arguments += parse(child);
            arguments += ", ";
          }
          arguments = arguments.substring(0, arguments.length() - 2);
        }

        return e.getAttributeValue("name") + "(" + arguments + ")";
        
      case "Assignment":
        return "<v>" + e.getChild("SimpleVariable").getAttributeValue("name") + "</v> = "
          + parse(e.getChild("Expression"));
        
      case "Expression":
        return parse(e.getChildren().get(0));
        
      case "ParenExpression":
        return "( " + parse(e.getChildren().get(0)) + " )";
        
      case "UserCommand":
        return e.getAttributeValue("name") + "(" + parse(e.getChildren().get(0)) + ")";
        
      case "Variable":
        return e.getAttributeValue("name");
        
      // LOGICAL OPERATORS
      case "Not":
        return "!" + parse(e.getChildren().get(0));
        
      case "AndAnd":
        return parse(e.getChildren().get(0)) + " && " + parse(e.getChildren().get(1));
        
      case "OrOr":
        return parse(e.getChildren().get(0)) + " || " + parse(e.getChildren().get(1));
        
      case "Xor":
        return parse(e.getChildren().get(0)) + " ^ " + parse(e.getChildren().get(1));
        
      // ARITHMETIC OPERATORS
      case "Neg":
        return "-" + parse(e.getChildren().get(0));
        
      case "Add":
        return parse(e.getChildren().get(0)) + " + " + parse(e.getChildren().get(1));
        
      case "Div":
        return parse(e.getChildren().get(0)) + " / " + parse(e.getChildren().get(1));
        
      case "Mul":
        return parse(e.getChildren().get(0)) + " * " + parse(e.getChildren().get(1));
        
      case "Mod":
        return parse(e.getChildren().get(0)) + " % " + parse(e.getChildren().get(1));
        
      case "Sub":
        return parse(e.getChildren().get(0)) + " - " + parse(e.getChildren().get(1));
        
      // RELATIONS
      case "Eq":
        return parse(e.getChildren().get(0)) + " == " + parse(e.getChildren().get(1));
        
      case "Ge":
        return parse(e.getChildren().get(0)) + " >= " + parse(e.getChildren().get(1));
        
      case "Gt":
        return parse(e.getChildren().get(0)) + " > " + parse(e.getChildren().get(1));
        
      case "Le":
        return parse(e.getChildren().get(0)) + " <= " + parse(e.getChildren().get(1));
        
      case "Lt":
        return parse(e.getChildren().get(0)) + " < " + parse(e.getChildren().get(1));
        
      case "Neq":
        return parse(e.getChildren().get(0)) + " != " + parse(e.getChildren().get(1));
        
      // LITERALS
      case "IntLiteral":
        return e.getAttributeValue("value");
        
      case "FloatLiteral":
        return e.getAttributeValue("value");
        
      case "StringLiteral":
        return "\"" + e.getText() + "\"";
        
      case "BoolLiteral":
        return e.getAttributeValue("value");
        
      // VARIABLES
      case "SimpleVariable":
        return "<v>" + e.getAttributeValue("name") + "</v>";
        
      default:
        System.err.println("Cannot parse " + e.getName());
        return "";
    }
  }
}
