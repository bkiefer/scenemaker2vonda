package compiler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import compiler.automaton.SceneMakerAutomaton;

public class Main {
  
  public static String getUriFromIniFile(String pathToOntologyDir) {
	  String uri = "";
	  
	  File ontologyDir = new File(pathToOntologyDir);
	  for (File f : ontologyDir.listFiles()) {
		  if(f.toString().endsWith(".ini")) {  
			  
			try {
				BufferedReader reader = new BufferedReader(new FileReader(f));
				String         line = null;
				
				while((line = reader.readLine()) != null) {
			          if(line.startsWith("cat") && line.contains("#")) {
			        	  uri = line.substring(line.lastIndexOf("=")+1).trim();
			          }
			    }			
				reader.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}   
		  }
	  }
	  
	  System.out.println(uri);
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
  		boolean buildVondaProject = true;
  		
  		if(args[2] == "false") {
	  		buildVondaProject = false;
	  	}
  		
  		boolean postProcessDialogueActs = true;
  		
  		if(args[3] == "false") {
	  		postProcessDialogueActs = false;
	  	}
  		
  		String outPathOntologyFile = "./ontology/";
  		String outPathRudiFiles = "./rudi/";
  		
  		File vondaProject = new File(pathToVondaProject);
  		String projectName = vondaProject.getName();
  		String uri = "http://www.semanticweb.org/robotstalkingsocial/ontologies/default-ontology";
  		
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
  			
  			String uriFromFile = getUriFromIniFile(outPathOntologyFile);
  			if (!uriFromFile.isEmpty()) {
  			  uri = uriFromFile;
  			}
  		}
  		
	    System.out.println("Parsing Sceneflow...");

	  	XmlParser parser = new XmlParser(new File(pathToScenemakerProject + "/sceneflow.xml"));
	    
	    SceneMakerAutomaton a = parser.getSceneMakerAutomaton();
	    a.ensureNodeNamesAreLowerCase();
	    
	    System.out.println("Generating ontology file...");
	    
	    OntologyFileGenerator ntg = new OntologyFileGenerator(a, uri, outPathOntologyFile + projectName + ".nt");
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
