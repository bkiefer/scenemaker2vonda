# SceneMaker2VOnDA compiler

This compiler converts graph-based SceneMaker dialog graphs to rule-based VOnDA code.

## Folder structure

This repository contains the following folders:

### compiler

`compiler` contains the compiler itself as well as a start script to start it (`start.sh`). The source code can be found in the `source` folder. `doc` contains the Javadoc of the project as HTML files (`index.html` is a good starting point).

### scenemaker_projects

`scenemaker_projects` contains a sample SceneMaker project that can be used to test the compiler.

### testbed

`testbed` is a VOnDA sample project to easily test the integration of the generated VOnDA code into an actual VOnDA project.

## Building SceneMaker2VOnDA

To build the project, just execute `mvn install`. This will create the folder `target` which contains the compiler as an executable JAR file as well as external libraries.

## Running SceneMaker2VOnDA

### Using the start script

To make executing the compiler a bit easier, we provide `start.sh`. It requires 2 arguments:

1. the path to the scene graph file (e.g. `sceneflow.xml`)
2. the path to an output folder (which has to exist at the point of execution)

The following options are supported:

```
-b, --build-vonda          build VOnDA project
-n, --no-post-processing   don't post process dialog acts
```
### Running the JAR directly

If you can't (or don't want to) use the start script, you can run the JAR directly (`java -jar target/scenemaker2vonda-1.0.jar`)
The JAR takes 4 arguments which are all mandatory:

1. the path to the scene graph file
2. the path to the output folder (must already exist)
3. build a VOnDA project? "true" / "false"
4. post process dialog acts? "true" / "false"

## Further reading

For details on how to use SceneMaker2VOnDA with an existing VOnDA project, refer to `kurzanleitung.pdf`
