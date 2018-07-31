
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RudiFileGenerator {

	private SceneMakerAutomaton automaton;
	private String outPath;
	private String preface = "\n" + 
			"void set_inactive(Supernode m) {\n" + 
			"\n" + 
			"	for (String x : m.simple_children) {removeTimeout(x);}\n" + 
			"	for (Supernode y : m.super_children) {set_inactive(y);}\n" + 
			"	\n" + 
			"	m.active = false;\n" + 
			"	m.simple_children = {};\n" + 
			"	m.super_children = {};\n" + 
			"	m.initiated = {};\n" + 
			"}\n" + 
			"\n" + 
			"boolean test_inactive(Supernode m) {\n" + 
			" \n" + 
			"	return ((!m.super_children) && (!m.simple_children) && (!m.initiated));\n" + 
			"\n" + 
			"}\n" + 
			"\n" + 
			"void super_transition(String node_a, Supernode a_parent, String supernode_b) {\n" + 
			"\n" + 
			"	cancelTimeout(node_a);\n" + 
			"\n" + 
			"	if(a_parent.simple_children.contains(node_a)) {\n" + 
			"		a_parent.simple_children -= node_a;\n" + 
			"	}\n" + 
			"\n" + 
			"	a_parent.initiated += supernode_b;\n" + 
			"}\n" + 
			"\n" + 
			"void transition (String node_a, String node_b, Supernode a_parent, Supernode b_parent) {\n" + 
			"\n" + 
			"	cancelTimeout(node_a);\n" + 
			"\n" + 
			"	if(a_parent.simple_children.contains(node_a)) {\n" + 
			"		a_parent.simple_children -= node_a;\n" + 
			"	}\n" + 
			"\n" + 
			"	b_parent.simple_children += node_b;\n" + 
			"}\n" + 
			"\n" + 
			"void check_out_transition(String a, String b, Supernode a_parent, Supernode b_parent) {\n" + 
			"\n" + 
			"	if (!hasActiveTimeout(a) && a_parent.simple_children.contains(a)) { \n" + 
			"	\n" + 
			"		transition(a, b, a_parent, b_parent);\n" + 
			"	}\n" + 
			"}\n" + 
			"\n" + 
			"void timeout_transition(String node_a, String node_b, Supernode a_parent, Supernode b_parent, int duration) {\n" + 
			"\n" + 
			"	timeout(node_a, duration) {\n" + 
			"\n" + 
			"		transition(node_a, node_b, a_parent, b_parent);\n" + 
			"	}\n" + 
			"}\n" + 
			"\n" + 
			"void timeout_super_transition(String node_a, Supernode a_parent, String supernode_b, int duration) {\n" + 
			"\n" + 
			"	timeout(node_a, duration) {\n" + 
			"\n" + 
			"		super_transition(node_a, a_parent, supernode_b);\n" + 
			"	}\n" + 
			"}\n" + 
			"\n" + 
			"void probability_transition(String node_a, String node_b, Supernode a_parent, Supernode b_parent) {\n" + 
			"\n" + 
			"	propose_id = \"propose_\"+ node_a;\n" + 
			"	\n" + 
			"	if(a_parent.simple_children.contains(node_a)) {\n" + 
			"		a_parent.simple_children -= node_a;\n" + 
			"	}\n" + 
			"\n" + 
			"	propose(propose_id) {					\n" + 
			"		transition(node_a, node_b, a_parent, b_parent);\n" + 
			"	}\n" + 
			"}\n" + 
			"\n" + 
			"void probability_super_transition(String node_a, Supernode a_parent, String node_b) {\n" + 
			"\n" + 
			"	propose_id = \"propose_\"+ node_a;\n" + 
			"	\n" + 
			"	if(a_parent.simple_children.contains(node_a)) {\n" + 
			"		a_parent.simple_children -= node_a;\n" + 
			"	}\n" + 
			"\n" + 
			"	propose(propose_id) {					\n" + 
			"		super_transition(node_a, a_parent, node_b);\n" + 
			"	}\n" + 
			"}\n" + 
			"\n" + 
			"void interruptive_transition(Supernode m, Supernode parent, String target_node) {\n" + 
			"						\n" + 
			"	set_inactive(m);	\n" + 
			"	parent.simple_children += target_node;\n" + 
			"}\n" + 
			"\n" + 
			"void interruptive_super_transition(Supernode m, Supernode parent, String target_supernode) {\n" + 
			"						\n" + 
			"	set_inactive(m);	\n" + 
			"	parent.initiated += target_supernode;\n" + 
			"}\n\n";
	
	public void writeSupernodeToFile(BufferedWriter fw, Supernode m) {
		try {
			if(m.parent == null) {
				//fw.write("import Functions;\n");
				fw.write(this.preface);
			}
			fw.write(m.getSetupCode());
			if(m.parent != null) {
				fw.write(m.getPassByCode());
			}
			fw.write(this.postProcessDialogueActs(m.getInterruptiveEdgesCode()));
			fw.write(this.postProcessDialogueActs(m.getPseudoInCode()));
			fw.write(this.postProcessDialogueActs(m.getPseudoOutCode()));
			
			for (Node n : m.nodes) {
				if(n.isSupernode == false) {
					fw.write(this.postProcessDialogueActs(n.getNodeCode()));
				}
			}
			
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
			/*finally
			{ 
			   try{
			      if(writer!=null)
				 writer.close();
			   }catch(Exception ex){
			       System.out.println("Error in closing the BufferedWriter "+ex);
			    }
			}*/ 
	    
	    return writer;
	}
	
	public void generateFunctionsFile() {
		
		String filePath = this.outPath + "/Functions.rudi";
		BufferedWriter fw = this.getFileWriter(filePath);
		try {
			fw.write(this.preface);
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

	public void generateChatAgentFile() {
		
		String filePath = this.outPath + "ChatAgent.rudi";
		BufferedWriter fw = this.getFileWriter(filePath);
		
		try {
			
			for (Supernode m : this.automaton.allSupernodes) {
				fw.write(m.name.substring(0,1).toUpperCase() + m.name.substring(1) + " " + m.name + ";\n");
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
		
		String filePath = this.outPath + m.name.substring(0,1).toUpperCase() + m.name.substring(1) + ".rudi";
		BufferedWriter fw = this.getFileWriter(filePath);
		this.writeSupernodeToFile(fw, m);
	}
	
	public void generateRudiFiles() {
		
		//this.generateFunctionsFile();
		this.generateChatAgentFile();
		
		for (Supernode m : this.automaton.allSupernodes) {
			generateSupernodeFile(m);
		}
		
	}
	
	public String postProcessDialogueActs(String str) {
		
		String cleanedString = str.replaceAll("emitDA\\(", "emitDA(#");
		cleanedString = cleanedString.replaceAll("lastDA\\(\\)\\s*>=\\s*", "lastDA() >= #");
		
		return cleanedString;
	}
	
	public RudiFileGenerator(SceneMakerAutomaton automaton, String outPath) {
		this.automaton = automaton;
		this.outPath = outPath;
	}  
	
	
}
