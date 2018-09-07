package compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.sun.xml.internal.ws.util.StringUtils;

import compiler.automaton.Node;
import compiler.automaton.SceneMakerAutomaton;
import compiler.automaton.Supernode;

public class RudiFileGenerator {

	private SceneMakerAutomaton automaton;
	private String outPath;
	private boolean postProcessDialogueActs;
	
	public void writeSupernodeToFile(BufferedWriter fw, Supernode m) {
		try {
			
			// If m is the top-level supernode, define utility functions (transitions etc...) in this file
			if(m.getParent() == null) {
				fw.write(StringConstants.FUNCTIONS);
			}
			
			fw.write(m.getSetupCode());
			
			// Unless m is the top-level supernode, skip it in case no corresponding instance exists in the ontology
			if(m.getParent() != null) {
				fw.write(m.getPassByCode());
			}
			
			// Post-process node and edge code and write it to the file
			fw.write(this.postProcessCode(m.getInterruptiveEdgesCode()));
			fw.write(this.postProcessCode(m.getPseudoInCode()));
			fw.write(this.postProcessCode(m.getPseudoOutCode()));
			
			for (Node n : m.getNodes()) {
				if(n.isSupernode() == false) {
					fw.write(this.postProcessCode(n.getNodeCode()));
				}
			}
			
			// Add import statements to end of file (importing all sub-supernodes)
			fw.write(m.getImportCode());
			
		} catch (IOException ioe) {
			   ioe.printStackTrace();
			}
			finally
			{ 
			   try{
			      if(fw!=null)
				 fw.close();
			   }catch(Exception ex){
			       System.out.println("Error in closing the BufferedWriter "+ex);
			    }
			} 
	}
	
	public BufferedWriter getFileWriter(String filePath) {
		BufferedWriter writer = null;
	    try {
	    	File file = new File(filePath);
	    	if (file.exists()) {
	    		file.delete();
	    	}
	    
	      file.getParentFile().mkdirs();
    	  file.createNewFile();

		  FileWriter fw = new FileWriter(file);
		  writer = new BufferedWriter(fw);
		 		  
	    } catch (IOException ioe) {
			   ioe.printStackTrace();
			}
	    
	    return writer;
	}

	public void generateChatAgentFile() {
		
		String filePath = this.outPath + "ChatAgent.rudi";
		BufferedWriter fw = this.getFileWriter(filePath);
		
		try {
			
			for (Supernode m : this.automaton.getAllSupernodes()) {
				fw.write(StringUtils.capitalize(m.getName()) + " " + m.getName() + ";\n");
			}
						
		} catch (IOException ioe) {
			   ioe.printStackTrace();
			}
			finally
			{ 
			   try{
			      if(fw!=null)
				 fw.close();
			   }catch(Exception ex){
			       System.out.println("Error in closing the BufferedWriter "+ex);
			    }
			} 
	}
	
	public void generateSupernodeFile(Supernode m) {
		
		String filePath = this.outPath + StringUtils.capitalize(m.getName()) + ".rudi";
		BufferedWriter fw = this.getFileWriter(filePath);
		this.writeSupernodeToFile(fw, m);
	}
	
	public void clearOutputDirectory() {
		File outDir = new File(this.outPath);
		
		for (File f : outDir.listFiles()) {
			f.delete();
		}
	}
	
	public void generateRudiFiles() {
		
		this.clearOutputDirectory();
		
		this.generateChatAgentFile();
		
		for (Supernode m : this.automaton.getAllSupernodes()) {
			generateSupernodeFile(m);
		}
		
	}
	
	// If compiling from Scenemaker sceneflow (which does not allow hashtags in commands), post-process node or edge code to include hashtags 
	public String postProcessCode(String str) {
		
		String cleanedString = str;
		
		if(this.postProcessDialogueActs == true) {
			cleanedString = cleanedString.replaceAll("emitDA\\(", "emitDA(#");
			cleanedString = cleanedString.replaceAll("lastDA\\(\\)\\s*>=\\s*", "lastDA() >= #");
			
			return cleanedString;
		}
		
		return cleanedString;
	}
	
	public RudiFileGenerator(SceneMakerAutomaton automaton, String outPath, boolean postProcessDialogueActs) {
		this.automaton = automaton;
		this.outPath = outPath;
		this.postProcessDialogueActs = postProcessDialogueActs;
	}  
	
	
}
