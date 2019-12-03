To execute this project do: (at /BoardGameSolver/src)
find . -name "*.java" > sources.txt
javac @sources.txt
find . -name "*.class" > sourcesClass.txt
jar cfm BoardGameSolver.jar MANIFEST.MF @sourceClass.txt
java -jar BoardGameSolver.jar