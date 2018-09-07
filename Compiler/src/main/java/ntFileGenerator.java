

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.sun.xml.internal.ws.util.StringUtils;

import compiler.automaton.SceneMakerAutomaton;
import compiler.automaton.Supernode;
import compiler.automaton.Variable;

/**
 * Generates the ontology in form of an nt-File for a given SceneMakerAutomaton.
 * @author Jana Jungbluth
 *
 */
public class ntFileGenerator {
	
	/**
	 * The {@code SceneMakerautomaton} for which the ontology is generated.
	 */
	private SceneMakerAutomaton automat;
	/**
	 * The URI to be used for nodes and variables in the ontology (given as input).
	 */
	private String uri_own;
	/**
	 * The file_path where the generated ontology is stored (given as input).
	 */
	private String file_path;
	/** 
	 * The URI used for type relations in the ontology.
	 */
	private String uri_for_type_relation = "http://www.w3.org/1999/02/22-rdf-syntax-ns";
	/** 
	 * The URI used for the relations 'domain', 'range' and 'subclass' in the ontology.
	 */
	private String uri_for_domain_range_subClass = "http://www.w3.org/2000/01/rdf-schema";
	/**
	 * The URI used for type definitions in the ontology.
	 */
	private String uri_for_types = "http://www.w3.org/2001/XMLSchema";
	/**
	 * The URI used for owl objects in the ontology.
	 */
	private String uri_for_owl_objects = "http://www.w3.org/2002/07/owl";
	/**
	 * A list of owl objects used in the ontology.
	 */
	private List<String> owl_objects = new ArrayList<>(Arrays.asList("Class","DatatypeProperty", "FunctionalProperty", "NamedIndividual", "ObjectProperty", "Ontology"));
	/**
	 * A list of type definitions used in the ontology.
	 */
	private List<String> types = new ArrayList<>(Arrays.asList("boolean", "int", "string", "float"));
	/**
	 * A list of relations used in the ontology.
	 */
	private List<String> relations = new ArrayList<>(Arrays.asList("domain", "range", "subClassOf"));
	
	/**
	 * Returns the correct URI needed for the given definition.
	 * @param string The definition the matching URI has to be returned for.
	 * @return the matching URI for the given definition.
	 */
	private String find_uri(String string){
		if (owl_objects.contains(string)) {
			return this.uri_for_owl_objects;
		}
		if (this.types.contains(string)) {
			return this.uri_for_types;
		}
		return this.uri_own;
	}
	
	/**
	 * Returns the correct URI needed for the given relation.
	 * @param string The relation the matching URI has to be returned for.
	 * @return the matching URI for the given relation.
	 */
	private String find_uri_relation(String string) {
		if (relations.contains(string)) {
			return this.uri_for_domain_range_subClass;
		}
		if (string=="type"){
			return this.uri_for_type_relation;
		}
		return this.uri_own;
	}
	
	/**
	 * Adds the correct syntax to create an nt-element.
	 * @param uri The URI of the nt-element.
	 * @param string The name of the object of the nt-element.
	 * @return A complete nt-element.
	 */
	private String create_brackets(String uri, String string) {
		if (!(string=="")) {
			uri += "#";
		}
		String new_string = "<" + uri + string + "> ";
		return new_string;
	}
	
	/**
	 * Writes one line of the ontology into the given file. This contains the definition of the relation between two objects.
	 * @param first The first object of the relation.
	 * @param relation The relation between the two objects.
	 * @param second The second object of the relation.
	 * @param bw The buffered writer the line is written into.
	 * @throws IOException
	 */
	private void write_line(String first, String relation, String second, BufferedWriter bw) throws IOException {
		String line = this.create_brackets(this.find_uri(first), first) + this.create_brackets(this.find_uri_relation(relation), relation) + this.create_brackets(this.find_uri(second), second)+ ".\n";
		bw.write(line);
	}
	
	/**
	 * Writes the definition of a property into the ontology file.
	 * @param name The name of the property.
	 * @param type The type of the property.
	 * @param domain The domain of the property.
	 * @param range The range of the property.
	 * @param bw The buffered writer the output is written into.
	 * @param functional A Boolean that describes if the property is a functional property.
	 * @throws IOException
	 */
	private void write_property(String name, String type, String domain, String range, BufferedWriter bw, boolean functional) throws IOException {
		String class_domain = StringUtils.capitalize(domain);
		this.write_line(name, "type", type, bw);
		if (functional) {
			this.write_line(name, "type", "FunctionalProperty", bw);
		}
		this.write_line(name, "domain", class_domain, bw);
		this.write_line(name, "range", range, bw);
	}
	
	/**
	 * Writes the definition of a class into the ontology file.
	 * @param name The class name.
	 * @param bw The buffered writer the output is written into.
	 * @throws IOException
	 */
	private void write_class(String name, BufferedWriter bw) throws IOException {
		this.write_line(name, "type", "Class", bw);
		this.write_line(name, "subClassOf", "Supernode", bw);
	}
	
	/**
	 * Writes the definition of an instance into the ontology file.
	 * @param name The name of the instance.
	 * @param parent The parent of the instance.
	 * @param bw The buffered writer the output is written into.
	 * @throws IOException
	 */
	private void write_instance(String name, String parent, BufferedWriter bw) throws IOException {
		String class_name = StringUtils.capitalize(name);
		this.write_line(name, "type", "NamedIndividual", bw);
		this.write_line(name, "type", class_name, bw);
		if (parent!="") {
			this.write_line(name, "parent", parent, bw);
		}
		String line = this.create_brackets(this.find_uri(name), name) + this.create_brackets(this.find_uri_relation("active"),"active")  + "\"false\"^^" + this.create_brackets(this.find_uri("boolean"), "boolean")+ ".\n";
		bw.write(line);
	}
	
	/**
	 * Writes the ontology into the given nt-File.
	 * @param bw The buffered writer the ontology is written into.
	 * @throws IOException
	 */
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
		this.write_property("imminent_simple_children","DatatypeProperty","Supernode","string",bw,false);
		// create Data Properties from variables
		for (Variable variable : this.automat.getAllVariables()) {
			this.write_property(variable.getName(), "DatatypeProperty", variable.getDomain().getName(), variable.getRange().toString(), bw, true);
		}
		// create Classes
		this.write_line("Supernode", "type", "Class", bw);
		for (Supernode supernode : this.automat.getAllSupernodes()) {
			String class_name = StringUtils.capitalize(supernode.getName());
			this.write_class(class_name, bw);
		}
		// create NamedIndividuals
		for (Supernode supernode: this.automat.getAllSupernodes()) {
			if (supernode.getParent() != null){
				this.write_instance(supernode.getName(), supernode.getParent().getName(), bw);
			}else{
				this.write_instance(supernode.getName(), "", bw);
			}
		}
	}
	
	/**
	 * Creates a new ontology in form of a nt-File.
	 */
	public void generateNtFile() {
		BufferedWriter writer = null;
	    try {
	    	File file = new File(this.file_path);
	    	if (file.exists()) {
	    		file.delete();
	    	}
	      file.getParentFile().mkdirs();
    	  file.createNewFile();
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
	
	/**
	 * Creates a new {@code ntFileGenerator} based on {@code automat}. The generated file will use {@code uri} and be stored at {@code filepath}.
	 * @param automat The SceneMakerAutomaton the ontology is to be generated for.
	 * @param uri The URI used for nodes and variables in the ontology.
	 * @param filepath The path for the nt-File that will be generated.
	 */
	public ntFileGenerator(SceneMakerAutomaton automat, String uri, String filepath) {
		this.automat = automat;
		this.uri_own = uri;
		this.file_path = filepath;
	}  
	
}
