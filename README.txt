
Kleine Anleitung zum Testen des scenemaker2vonda-Compilers:

- Der eigentliche Compiler befindet sich im Ordner "Compiler". Das Ausführen der main.java übersetzt den in Compiler/sceneflow.xml spezifizierten Automaten nach Vonda.
	-> In Compiler/rudi werden die .rudi-Dateien generiert
	-> In Compiler/ontology wird eine Datei ontology.nt generiert


Um diese Übersetzung als Vonda-Dialog testen zu können, müssen die folgenden Schritte befolgt werden:

- rudi-Dateien nach Compiled_Introduction/src/main/rudi verschieben (ggf. sonstigen Inhalt des Ordners löschen)
- Das ontology.nt-file nach Compiled_Introduction/src/main/resources/ontology verschieben und in introduction.nt umbenennen (ggf. vorhandenes introduction.nt löschen)
- ggf. die Datei Compiled_Introduction/src/main/resources/ontology/persistent.nt löschen
- Aufgrund von Scenemaker-Eigenartigkeiten kann ein Ausdruck nicht im Sceneflow spezifiziert werden. Deshalb in der Datei "Hello_node.rudi" in der Regel "wait_node" folgende Ausdrücke händisch ergänzen: 
	-> Den Ausdruck "lastDA() == "negative"" zu "lastDA().what == "negative"" ändern
	-> Den Ausdruck "lastDA() == "positive"" zu "lastDA().what == "positive"" ändern 
- Den Ordner Compiled_Introduction/target löschen, um ggf Überhängsel früherer Versionen loszuwerden
- Den Ordner Compiled_Introduction kopieren und in eine laufendende Vonda-Version in den Unterordner vonda/examples kopieren 
- Dann innerhalb von vonda/examples/Compiled_Introduction eine Konsole öffnen und nacheinander "./compile", "mvn install" und "./openGui" ausführen. Jetzt sollte der Dialog starten :)
