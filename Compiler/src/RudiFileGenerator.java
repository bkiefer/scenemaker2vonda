// TODO: Wir müssen noch sicherstellen, dass der Name des Automaten (also auf dem Top-Level) "MainAgent" lautet!

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
	
	public void writeSupernodeToFile(BufferedWriter fw, Supernode m) {
		try {
			fw.write(m.getSetupCode());
			fw.write(m.getPassByCode());
			fw.write(m.getInterruptiveEdgesCode());
			fw.write(m.getPseudoInCode());
			fw.write(m.getPseudoOutCode());
			
			for (Node n : m.nodes) {
				fw.write(n.getNodeCode());
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
		
		String preface = "void set_inactive(Supernode m) {\n" + 
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
				"}\n" + 
				"";
		
		String filePath = this.outPath + "/Functions.rudi";
		BufferedWriter fw = this.getFileWriter(filePath);
		try {
			fw.write(preface);
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
		
		String filePath = this.outPath + m.name + ".rudi";
		BufferedWriter fw = this.getFileWriter(filePath);
		this.writeSupernodeToFile(fw, m);
	}
	
	public void generateRudiFiles() {
		
		this.generateFunctionsFile();
		
		for (Supernode m : this.automaton.allSupernodes) {
			generateSupernodeFile(m);
		}
		
	}
	
	public RudiFileGenerator(SceneMakerAutomaton automaton, String outPath) {
		this.automaton = automaton;
		this.outPath = outPath;
	}  
	
	
}