package compiler;

public class Utils {
  public static String capitalize(String s) {
    if (s == null || s.isEmpty() || Character.isUpperCase(s.charAt(0)))
      return s;
    return s.substring(0,1).toUpperCase() + s.substring(1);
  }

  public static String decapitalize(String s) {
    if (s == null || s.isEmpty() || Character.isLowerCase(s.charAt(0)))
      return s;
    return s.substring(0,1).toLowerCase() + s.substring(1);
  }
}
