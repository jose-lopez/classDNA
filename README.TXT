========================
BUILD OUTPUT DESCRIPTION
========================

When you build an Java application project that has a main class, the IDE
automatically copies all of the JAR
files on the projects classpath to your projects dist/lib folder. The IDE
also adds each of the JAR files to the Class-Path element in the application
JAR files manifest file (MANIFEST.MF).

To run the project from the command line, go to the dist folder and
type the following:

Arff files mode:

java -cp clasificador.jar generador.GeneradorARFF <0|1|2|3> <B|W> <cei|cie|cez|cze> <<5 5>|<100 5>|<50 500>|<500 50>>

java -cp clasificador.jar generador.GeneradorARFF 0 B cze 5 5

java -cp clasificador.jar generador.GeneradorARFF 1 B cze 100 5

java -cp clasificador.jar generador.GeneradorARFF 2 B cze 50 500

java -cp clasificador.jar generador.GeneradorARFF 3 B cez 500 50


Classifier mode:

java -cp clasificador.jar clasificador.Clasificador  <0|1|2|3> <0|1|2|3> test/gen_SST.txt <<5 5>|<5 5>|<50 500>|<500 50>> < mayor o igual a 0.85> <true|false>

0 1 test/gen_SST.txt 5 5 0.75 false

1 1 test/gen_SST.txt 5 5 0.75 false

2 1 test/gen_SST.txt 50 500 0.998147 false

3 1 test/gen_SST.txt 500 50 0.9999404840371321 false 

To distribute this project, zip up the dist folder (including the lib folder)
and distribute the ZIP file.

Notes:

* If two JAR files on the project classpath have the same name, only the first
JAR file is copied to the lib folder.
* Only JAR files are copied to the lib folder.
If the classpath contains other types of files or folders, these files (folders)
are not copied.
* If a library on the projects classpath also has a Class-Path element
specified in the manifest,the content of the Class-Path element has to be on
the projects runtime path.
* To set a main class in a standard Java project, right-click the project node
in the Projects window and choose Properties. Then click Run and enter the
class name in the Main Class field. Alternatively, you can manually type the
class name in the manifest Main-Class element.
