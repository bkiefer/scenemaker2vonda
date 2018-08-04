
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.xml.internal.ws.util.StringUtils;

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
			"	m.imminent_simple_children = {};\n" + 
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
			"void transition (String node_a, String node_b, Supernode a_parent, Supernode b_parent, boolean targetNodeIsSupernode) {\n" + 
			"\n" + 
			"	cancelTimeout(node_a);\n" + 
			"\n" + 
			"	if(a_parent.simple_children.contains(node_a)) {\n" + 
			"		a_parent.simple_children -= node_a;\n" + 
			"	}\n" + 
			"\n" + 
			"	if(targetNodeIsSupernode) {\n" + 
			"		b_parent.initiated += node_b;\n" + 
			"	} else {\n" + 
			"		b_parent.simple_children += node_b;\n" + 
			"		b_parent.imminent_simple_children += node_b;\n" + 
			"	}\n" + 
			"}\n" + 
			"\n" + 
			"void check_out_transition(String a, String b, Supernode a_parent, Supernode b_parent) {\n" + 
			"\n" + 
			"	if (!hasActiveTimeout(a) && a_parent.simple_children.contains(a)) { \n" + 
			"	\n" + 
			"		transition(a, b, a_parent, b_parent, false);\n" + 
			"	}\n" + 
			"}\n" + 
			"\n" + 
			"void timeout_transition(String node_a, String node_b, Supernode a_parent, Supernode b_parent, boolean targetNodeIsSupernode, int duration) {\n" + 
			"\n" + 
			"	timeout(node_a, duration) {\n" + 
			"\n" + 
			"		transition(node_a, node_b, a_parent, b_parent, targetNodeIsSupernode);\n" + 
			"	}\n" + 
			"}\n" + 
			"\n" + 
			"void probability_transition(String node_a, String node_b, Supernode a_parent, Supernode b_parent, boolean targetNodeIsSupernode) {\n" + 
			"\n" + 
			"	propose_id = \"propose_\"+ node_a;\n" + 
			"\n" + 
			"	propose(propose_id) {					\n" + 
			"		transition(node_a, node_b, a_parent, b_parent, targetNodeIsSupernode);\n" + 
			"	}\n" + 
			"}\n" + 
			"\n" + 
			"void interruptive_transition(Supernode m, Supernode parent, String target_node, boolean targetNodeIsSupernode) {\n" + 
			"						\n" + 
			"	set_inactive(m);	\n" + 
			"	if(targetNodeIsSupernode) {\n" + 
			"		parent.initiated += target_node;\n" + 
			"	} else {\n" + 
			"		parent.simple_children += target_node;\n" + 
			"	}\n" + 
			"}\n" + 
			"";
	
	public void writeSupernodeToFile(BufferedWriter fw, Supernode m) {
		try {
			if(m.parent == null) {
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
	    
	    return writer;
	}

	public void generateChatAgentFile() {
		
		String filePath = this.outPath + "ChatAgent.rudi";
		BufferedWriter fw = this.getFileWriter(filePath);
		
		try {
			
			for (Supernode m : this.automaton.allSupernodes) {
				fw.write(StringUtils.capitalize(m.name) + " " + m.name + ";\n");
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
		
		String filePath = this.outPath + StringUtils.capitalize(m.name) + ".rudi";
		BufferedWriter fw = this.getFileWriter(filePath);
		this.writeSupernodeToFile(fw, m);
	}
	
	public void generateRudiFiles() {
		
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
