cd compiler
mvn -U clean install
cd ..
compiler/start.sh scenemaker_projects/introduction/sceneflow.xml vonda_example_project/ -b
cd vonda_example_project
vondac -c introcompile.yml
mvn -U clean install
