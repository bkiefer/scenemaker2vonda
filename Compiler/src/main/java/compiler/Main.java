package compiler;
import java.io.File;
import java.io.IOException;

import compiler.automaton.SceneMakerAutomaton;

public class Main {

  public static void changeCompileScript(File vondaProject) {
	  
  }
  
  public static String getUriFromIniFile(File vondaProject) {
	  String uri = "";
	  return uri;
  }
  
  public static void delete(File f) throws IOException {
	  
	  if (f.isDirectory()) {
		    for (File c : f.listFiles())
		      delete(c);
		  }
	  
	  f.delete();
  }
  
  public static void main(String[] args) {
	    
	  	String pathToScenemakerProject = args[0];
	  	String pathToVondaProject = args[1];
  		boolean postProcessDialogueActs = true;
  		
  		if(args[2] == "false") {
	  		postProcessDialogueActs = false;
	  	}
  		
  		boolean buildVondaProject = true;
  		
  		if(args[3] == "false") {
	  		buildVondaProject = false;
	  	}
  		
  		String outPathOntologyFile = "./ontology/";
  		String outPathRudiFiles = "./rudi/";
  		
  		File vondaProject = new File(pathToVondaProject);
  		String projectName = vondaProject.getName();
  		String uri = "http://www.semanticweb.org/jana/ontologies/2018/6/untitled-ontology-6";
  		
  		if(buildVondaProject) {
  			outPathOntologyFile = pathToVondaProject + "./src/main/resources/ontology/";
  			outPathRudiFiles = pathToVondaProject + "./src/main/rudi/";
  			File persistent = new File(pathToVondaProject + "./src/main/resources/ontology/persistent.nt");
  			persistent.delete();
  			
  			try {
				delete(new File(pathToVondaProject + "./target"));
				delete(new File(pathToVondaProject + "./src/main/gen-java"));
			} catch (IOException e) {
				e.printStackTrace();
			}
  			
  			changeCompileScript(vondaProject);
  			uri = getUriFromIniFile(vondaProject);
  		}
  		
	    System.out.println("Parsing Sceneflow...");

	  	XmlParser parser = new XmlParser(new File(pathToScenemakerProject + "/sceneflow.xml"));
	    
	    SceneMakerAutomaton a = parser.getSceneMakerAutomaton();
	    a.ensureNodeNamesAreLowerCase();
	    
	    System.out.println("Generating ontology file...");
	    
	    ntFileGenerator ntg = new ntFileGenerator(a, uri, outPathOntologyFile + projectName + ".nt");
	    ntg.generateNtFile();
	    
	    System.out.println("Generating rudi files...");

	    RudiFileGenerator g = new RudiFileGenerator(a, outPathRudiFiles, postProcessDialogueActs);
	    g.generateRudiFiles();
	    
	    if(buildVondaProject) {
	    	try {
	    		
	    	    System.out.println("Compiling VonDa project...");

	    		String[] compileCmd = new String[1];
		    	compileCmd[0] = "./compile";
		    	Runtime.getRuntime().exec(compileCmd, null, vondaProject);
		    	
	    	    System.out.println("Building VonDa project...");
		    	
		    	String[] installCmd = new String[2];
		    	installCmd[0] = "mvn";
		    	installCmd[1] = "install";
		    	
				Runtime.getRuntime().exec(installCmd, null, vondaProject);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    System.out.println("Done");
	  }
  
}
