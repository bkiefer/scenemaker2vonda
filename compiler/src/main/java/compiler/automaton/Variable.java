package compiler.automaton;

public class Variable {
  
  /**
   * The name of the {@code Variable}.
   */
  private String name;
  /**
   * The value of the {@code Variable}.
   */
  private String value;
  /**
   * The domain of the {@code Variable}, i.e. which {@code Node} it belongs to.
   */
  private Node domain;
  /**
   * The range (= type) of the {@code Variable}.
   */
  private Type range;

  /**
   * @return the name
   */
  public String getName() {
	  return name;
  }
  
  /**
   * @param name the name to set
   */
  public void setName(String name) {
	  this.name = name;
  }
  
  /**
   * @return the value
   */
  public String getValue() {
	  return value;
  }
  
  /**
   * @param value the value to set
   */
  public void setValue(String value) {
	  this.value = value;
  }
  
  /**
   * @return the domain
   */
  public Node getDomain() {
	  return domain;
  }
  
  /**
   * @param domain the domain to set
   */
  public void setDomain(Node domain) {
	  this.domain = domain;
  }
  
  /**
   * @return the range
   */
  public Type getRange() {
	  return range;
  }
  
  /**
   * @param range the range to set
   */
  public void setRange(Type range) {
	  this.range = range;
  }
  
}
