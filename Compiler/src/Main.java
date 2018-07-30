import java.io.File;

public class Main {

  /**
   * For test purposes only.
   * @param args Not used here anyway ;)
   */
  public static void main(String[] args) {
    XmlParser parser = new XmlParser(new File("sceneflow.xml"));
    
    @SuppressWarnings("unused")
    SceneMakerAutomaton a = parser.getSceneMakerAutomaton();
    
    RudiFileGenerator g = new RudiFileGenerator(a, "./rudi/");

    g.generateRudiFiles();
    
    System.out.println("Done");
  }
}
