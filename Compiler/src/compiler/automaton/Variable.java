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
  	
  public String getName() {
	return name;
  }
  
  public void setName(String name) {
	this.name = name;
  }
  
  public String getValue() {
	return value;
  }
  
  public void setValue(String value) {
	this.value = value;
  }
  
  public Node getDomain() {
	return domain;
  }
  
  public void setDomain(Node domain) {
	this.domain = domain;
  }
  
  public Type getRange() {
	return range;
  }
  
  public void setRange(Type range) {
	this.range = range;
  }
  
  
  
  

}
