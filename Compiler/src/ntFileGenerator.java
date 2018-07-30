

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ntFileGenerator {
	
	private SceneMakerAutomaton automat;
	private String uri_own;
	private String uri_for_type_relation = "http://www.w3.org/1999/02/22-rdf-syntax-ns";
	private String uri_for_domain_range_subClass = "http://www.w3.org/2000/01/rdf-schema";
	private String uri_for_types = "http://www.w3.org/2001/XMLSchema";
	private String uri_for_owl_objects = "http://www.w3.org/2002/07/owl";
	private List<String> owl_objects = new ArrayList<>(Arrays.asList("Class","DatatypeProperty", "FunctionalProperty", "NamedIndividual", "ObjectProperty", "Ontology"));
	private List<String> types = new ArrayList<>(Arrays.asList("boolean", "int", "string", "float"));
	private List<String> relations = new ArrayList<>(Arrays.asList("domain", "range", "subClassOf"));
	private String file_path;
	
	private String find_uri(String string){
		if (owl_objects.contains(string)) {
			return this.uri_for_owl_objects;
		}
		if (this.types.contains(string)) {
			return this.uri_for_types;
		}
		return this.uri_own;
	}
	
	
	private String find_uri_relation(String string) {
		if (relations.contains(string)) {
			return this.uri_for_domain_range_subClass;
		}
		if (string=="type"){
			return this.uri_for_type_relation;
		}
		return this.uri_own;
	}
	
	
	private String create_brackets(String uri, String string) {
		if (!(string=="")) {
			string += "#";
		}
		String new_string = "<" + uri + string + ">";
		return new_string;
	}
	
	
	private void write_line(String first, String relation, String second, BufferedWriter bw) throws IOException {
		String line = this.create_brackets(this.find_uri(first), first) + this.create_brackets(this.find_uri_relation(relation), relation) + this.create_brackets(this.find_uri(second), second)+ ".\n";
		bw.write(line);
		}
	
	
	private void write_property(String name, String type, String domain, String range, BufferedWriter bw, boolean functional) throws IOException {
		this.write_line(name, "type", type, bw);
		if (functional) {
			this.write_line(name, "type", "FunctionalProperty", bw);
		}
		this.write_line(name, "domain", domain, bw);
		this.write_line(name, "range", range, bw);
	}
	
	
	private void write_class(String name, BufferedWriter bw) throws IOException {
		this.write_line(name, "type", "Class", bw);
		this.write_line(name, "subClassOf", "Supernode", bw);
	}
	
	
	private void write_instance(String name, String parent, BufferedWriter bw) throws IOException {
		this.write_line(name, "type", "NamedIndividual", bw);
		this.write_line(name, "type", name, bw);
		if (parent!="") {
			this.write_line(name, "parent", parent, bw);
		}
		String line = this.create_brackets(this.find_uri(name), name) + this.create_brackets(this.find_uri_relation("active"),"active")  + "\"false\"^^" + this.create_brackets(this.find_uri("boolean"), "boolean");
		bw.write(line);
	}
	
	
	private void write_file(BufferedWriter bw) throws IOException {
		// establish ontology
		this.write_line("","type","Ontology", bw);
		// create Object Properties
		this.write_property("parent", "ObjectProperty", "Supernode", "Supernode", bw, true);
		this.write_property("super_children", "ObjectProperty", "Supernode", "Supernode", bw, false);
		// create Data Properties
		this.write_property("active", "DatatypeProperty", "Supernode", "boolean", bw, true);
		this.write_property("initiated", "DatatypeProperty", "Supernode", "string", bw, false);
		this.write_property("simple_children", "DatatypeProperty", "Supernode", "string", bw, false);
		// create Data Properties from variables
		for (Variable variable : this.automat.allVariables) {
			this.write_property(variable.name, "DataTypeProperty", variable.domain.name, variable.range.toString(), bw, true);
		}
		// create Classes
		this.write_line("Supernode", "type", "Class", bw);
		for (Supernode supernode : this.automat.allSupernodes) {
			this.write_class(supernode.name, bw);
		}
		// create NamedIndividuals
		for (Supernode supernode: this.automat.allSupernodes) {
			if (supernode.parent != null){
				this.write_instance(supernode.name, supernode.parent.name, bw);
			}else{
				this.write_instance(supernode.name, "", bw);
			}
		}
	}
	
	
	public void generateNtFile() {
		BufferedWriter writer = null;
	    try {
	    	File file = new File(this.file_path);
	    	if (!file.exists()) {
	    		file.createNewFile();
	    	}

		  FileWriter fw = new FileWriter(file);
		  writer = new BufferedWriter(fw);
		  this.write_file(writer);
	    } catch (IOException ioe) {
			   ioe.printStackTrace();
			}
			finally
			{ 
			   try{
			      if(writer!=null)
				 writer.close();
			   }catch(Exception ex){
			       System.out.println("Error in closing the BufferedWriter"+ex);
			    }
			} 
	}
	
	
	public ntFileGenerator(SceneMakerAutomaton automat, String uri, String filepath) {
		this.automat = automat;
		this.uri_own = uri;
		this.file_path = filepath;
	}  
	
}
