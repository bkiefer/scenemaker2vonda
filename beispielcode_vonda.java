//TODO vars initialisieren (aber wie?)
//TODO Superknoten-Objekte erstellen
states = {"hello_node_in"} // Globaler Startknoten --> Rekursiv
import("hello_node") // hello_nodes Superknoten werden rekursiv importiert

if ("bye_node" in states) {
	emitDA("Bye");
	cancel;
}

-------------------
|hello_node module (hello_node.rudi)|
-------------------
import pos_node;
import neg_node;

hello_node:
	Supernode this_node = ;//TODO: Datenbankanfrage(Superknoten mit Label "hello_node")
	if (this_node.oneChildActive()) {
		// Interruptive edges
		if (lastDA == "bye") { //TODO: lastDA
			for (String x : this_node.states) {
		   		states.remove(x);
		      	removeTimeout(x);
			}
		    for (Supernode x : this_node.superchildren) {
				x.remove_states(); // löscht auch deren Timeouts
		   	}
		    states.add("bye_node");
		    
		} else {
			// Knoten in hello_node
			// hello_node_in (Pseudo-Startknoten)
			hello_node_in:
				if (states.contains("hello_node_in")) {
					if (true) {
						states.remove("hello_node_in");
						states.add("hi_node");
					};
				};
			// hello_node_out (Pseudo-Endknoten)
			hello_node_out:
				if (states.contains("hello_node_out")) {
					if (true) {
						states.remove("hello_node_out");
						states.add("bye_node");
					}
				}
				// Gestrichelte Kante hier nicht notwendig, da die nächsthöhere Ebene Toplevel ist

			// hi_node
			hi_node:
				if (states.contains("hi_node")) {
					emitDA("hi");
					if (true) {
						timeout("hi_node", 5000) {
							states.remove("hi_node");
							states.add("how_node");
						}
					}
					if (lastDA == "hello") {
						removeTimeout("hi_node");
						states.remove("hi_node");
						states.add("how_node");
					}
					if (!hasActiveTimeout("hi_node") && states.contains("hi_node")) {
						states.add("hello_node_out");
						states.remove("hi_node");
					}
				}

			// how_node
			how_node:
				if (states.contains("how_node")) {
					emitDA("How are you?");
					if (mood == -1) {
						removeTimeout("how_node");
						states.remove("how_node");
						states.add("neg_node");
					}
					if (mood == 1) {
						removeTimeout("how_node");
						states.remove("how_node");
						states.add("pos_node");
					}
					if (!hasActiveTimeout("how_node") && states.contains("how_node")) {
						states.add("hello_node_out");
						states.remove("how_node");
					}
				}
				
			// answer_node
			answer_node:
				if (states.contains("anwser_node")) {
					emitDA("I'm fine, thanks");
					if (true) {
						states.remove("anwser_node");
						states.add("excuse_node");
					}
					if (!hasActiveTimeout("answer_node") && states.contains("answer_node")) { 
						states.add("hello_node_out")
						states.remove("answer_node")
					}
					// excuse_node
					if (states.contains("excuse_node")) {
						emitDA("I have to go now");
					}
					if (!hasActiveTimeout("excuse_node") && states.contains("excuse_node")) {
					  states.add("hello_node_out");
					  states.remove("excuse_node");
					}
				}
		}
	}
-----------------
|pos_node module (pos_node.rudi)|
-----------------

pos_node:
	Supernode this_node = ;//TODO: Datenbankanfrage(Superknoten mit Label "pos_node")
	if (this_node.oneChildActive()) {

		// Interruptive Edges:
		if (req_mood == true) {
			for (String x : this_node.states) {
				states.remove(x);
				removeTimeout(x);
			}
			for (Supernode x : this_node.superchildren) {
				x.remove_states(); // löscht auch deren Timeouts
			}
			states.add("answer_node");
			
		} else {
			// pos_node_in
			pos_node_in:
				if (states.contains("pos_node_in") {
					if (true) {
						states.remove("pos_node_in");
						states.add("cool_node");
					}
				}

			// pos_node_out
			pos_node_out:
				if ("pos_node_out" in states) {
					if (true) {
						states.remove("pos_node_out");
						states.add("excuse_node");
					}
					// Gestrichelte Kante hier nicht notwendig, da Edge aus pos_node raus eine Epsilon-Kante ist
				}
				
			// cool_node
			cool_node:
				if (states.contains("cool_node")): {
					emitDA("Cool");
					if (true) {
						states.remove("cool_node");
						states.add("why_node");
					}
					if (!hasActiveTimeout("cool_node") && states.contains("cool_node")) {
						states.remove("cool_node");
						states.add("pos_node_out");
					}
				}

			// why node
			why_node:
				if (states.contains("why_node")) {
					emitDA("Why are you so happy today?");
					if (true) {
						timeout("why_node", 10000) {
							states.remove("why_node");
							states.add("great_node");
						}	
					}
					if (!hasActiveTimeout("why_node") && states.contains("why_node")) {
						states.remove("why_node");
						states.add("pos_node_out");
					}
				}

			// great_node
			great_node:
				if (states.contains("great_node")) {
					emitDA("That's great");
					states.remove("great_node");
					if ((!hasActiveTimeout("great_node") && states.contains("great_node")) {
						states.remove("great_node");
						states.add("pos_node_out");
					}
				}
		}
	}

-----------------
|neg_node module (neg_node.rudi) |
-----------------
neg_node:
	Supernode this_node = ; //TODO: Datenbankanfrage(Superknoten mit Label "neg_node")
	if (this_node.oneChildActive()) {
		// Interruptive Edges:
		if (req_mood == true) {
			for (String x : this_node.states) {
				states.remove(x);
				removeTimeout(x);
			}
			for (Supernode x : this_node.superchildren) {
				x.remove_states() // löscht auch deren Timeouts
			}
			states.add("answer_node");
			
		} else {
			//neg_node_in
			neg_node_in:
				if (states.contains("neg_node_in")) {
					if (true) {
						states.remove("neg_node_in");
						states.add("no_node");
					}
				}

			//neg_node_out
			neg_node_out:
				if (states.contains("neg_node_out")) {
					if (true) {
						states.remove("neg_node_out");
						states.add("excuse_node");
					}
				}
				// Gestrichelte Kante hier nicht notwendig, da Kante aus neg_node raus eine Epsilon-Kante ist

			// no_node
			no_node:
				if (states.contains("no_node")) {
					emitDA("Oh no!");
					propose("propose_joke_node") {
						states.add("joke_node");
						states.remove("no_node");
					}
					propose("propose_sorry_node") {
						states.add("sorry_node");
						states.remove("no_node");
					}
					propose("propose_happy_node") {
						states.add("happy_node");
						states.remove("no_node");
					}
				}

			// joke_node
			joke_node:
				if (states.contains("joke_node")) {
					emitDA("joke");
					states.remove("joke_node");
				}
				if (!hasActiveTimeout("joke_node") && states.contains("joke_node")) {
					states.remove("joke_node");
					states.add("neg_node_out");
				}

			// sorry_node
			sorry_node:
				if (states.contains("sorry_node")) {
					emitDA("I'm so sorry");
					states.remove("sorry_node");
				}
				if (!hasActiveTimeout("sorry_node") && states.contains("sorry_node")) {
					states.remove("sorry_node");
					states.add("neg_node_out");
				}

			// happy_node
			happy_node:
				if (states.contains("happy_node")) {
					emitDA("Tomorrow will be a happy day again");
					states.remove("happy_node");
				}
				if (!hasActiveTimeout("happy_node") && states.contains("happy_node")) {
					states.remove("happy_node");
					states.add("neg_node_out");
				}
		}
	}
