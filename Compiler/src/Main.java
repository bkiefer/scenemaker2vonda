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
    
    a.name = "MainAgent";
    
    ntFileGenerator ntg = new ntFileGenerator(a, "<http://www.semanticweb.org/ontologies/2018/6/untitled-ontology-6>", "./ontology/ontology.nt");
    
    ntg.generateNtFile();
    
    RudiFileGenerator g = new RudiFileGenerator(a, "./rudi/");

    g.generateRudiFiles();
    
    System.out.println("Done");
  }
}
