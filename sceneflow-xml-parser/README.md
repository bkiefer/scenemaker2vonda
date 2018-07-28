## Dateien in diesem Verzeichnis

- diese README.txt ;)
- fragen.txt mit Fragen an Bernd, die den Parser betreffen
- JDOM-JAR (siehe unten)
- src
- bin
- die aktuelle sceneflow.xml zum Testen

## Dependencies

Damit der Parser funktioniert, wird JDOM benötigt. Version 2.0.6 sollte bereits in diesem Verzeichnis sein.

Unter Eclipse wird das Ganze wie folgt eingebunden:

1. Project -> Properties -> Java Build Path -> Add External JARs
2. Die JDOM-Jar in diesem Verzeichnis auswählen
3. Apply and Close

Jetzt sollten die jdom2-Imports funktionieren.

## Benutzen des Parsers

Relevant sind eigentlich nur 2 Methoden:
- der Konstruktor des Parsers, der auch gleichzeitig den Parse-Vorgang startet
- eine Methode, die das erstellte `SceneMakerAutomaton`-Objekt zurückliefert.

Das Ganze sieht dann zum Beispiel so aus:
```java
XmlParser parser = new XmlParser(new File("sceneflow.xml"));
SceneMakerAutomaton automaton = parser.getSceneMakerAutomaton();
```

## Änderungen an Janas Klassen

Ich habe das Ganze gemäß Googles Java-Style-Guidelines abgeändert. Wichtig für euch ist hauptsächlich, dass es jetzt keine Unterstriche mehr gibt. Aus `all_variables` ist also `allVariables` geworden.
Außerdem habe ich noch bei manchen Klassen einen Konstruktor hinzugefügt.

## Neue Klassen

Ich habe neue Sub-Klassen von Edge hinzugefügt. Um genau zu sein sind es

- EpsilonEdge
- ConditionalEdge
- ProbabilityEdge
- ForkEdge
- TimeoutEdge
- InterruptiveEdge

Außerdem hat mein Parser die Klassen

- XmlParser
- ExpressionParser
- CustomSaxBuilder

Es gibt noch eine `Main`-Klasse, aber die ist nur zu Testzwecken.

## Integration in eure Teilprogramme

Am besten nehmt ihr erst mal meine Edge- etc. Klassen und packt die in euer lokales Projekt rein. Mergen / Packagen können wir dann später.
