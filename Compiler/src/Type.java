
/**
 * Enum for variable's ranges, e.g. their type.
 * @author Max Depenbrock
 */
public enum Type {
  BOOL {
    @Override
    public String toString() {
      return "boolean";
    }
  }, INT {
    @Override
    public String toString() {
      return "int";
    }
  }, STRING {
    @Override
    public String toString() {
      return "string";
    }
  }, FLOAT {
    @Override
    public String toString() {
      return "float";
    }
  }
}
