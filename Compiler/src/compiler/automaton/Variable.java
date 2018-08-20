package compiler.automaton;

public class Variable {
  
  /**
   * The name of the {@code Variable}.
   */
  public String name;
  /**
   * The value of the {@code Variable}.
   */
  public String value;
  /**
   * The domain of the {@code Variable}, i.e. which {@code Node} it belongs to.
   */
  public Node domain;
  /**
   * The range (= type) of the {@code Variable}.
   */
  public Type range;

}
