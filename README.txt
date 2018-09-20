
Um diese Übersetzung als Vonda-Dialog testen zu können, müssen die folgenden Schritte befolgt werden:

- Im compile-Skript den Namen der Main-Klasse (MainAgent.rudi) einstellen
- Im Ontology-Ordner in der Datei "introduction.ini" den Default-Namespace ("cat =") an die uri aus ontology.nt anpassen
- In der Java-Datei "ChatAgent" alle Supernode-Objekte einmal als Variablen definieren (oben) und in der init-Methode die Variablen mit dem entsprechenden Datenbankobjekt verknüpfen
- In der Java-Datei "StubClient" das Attribut "_agent" als Instanz der Klasse "MainAgent" deklarieren und in der init-Methode die Zeile "_agent = new MainAgent()" hinzufügen

- rudi-Dateien nach Compiled_Introduction/src/main/rudi verschieben (ggf. sonstigen Inhalt des Ordners löschen)
- Das ontology.nt-file nach Compiled_Introduction/src/main/resources/ontology verschieben und in introduction.nt umbenennen (ggf. vorhandenes introduction.nt löschen)
- ggf. die Datei Compiled_Introduction/src/main/resources/ontology/persistent.nt löschen
- Aufgrund von Scenemaker-Eigenartigkeiten kann ein Ausdruck nicht im Sceneflow spezifiziert werden. Deshalb in der Datei "Hello_node.rudi" in der Regel "wait_node" folgende Ausdrücke händisch ergänzen: 
	-> Den Ausdruck "lastDA() == "negative"" zu "lastDA().what == "negative"" ändern
	-> Den Ausdruck "lastDA() == "positive"" zu "lastDA().what == "positive"" ändern 
- Die Ordner Compiled_Introduction/target und Compiled_Introduction/src/gen-java löschen, um ggf Überhängsel früherer Versionen loszuwerden
- Den Ordner Compiled_Introduction kopieren und in eine laufendende Vonda-Version in den Unterordner vonda/examples kopieren 
- Dann innerhalb von vonda/examples/Compiled_Introduction eine Konsole öffnen und nacheinander "./compile", "mvn install" und "./openGui" ausführen. Jetzt sollte der Dialog starten :)
