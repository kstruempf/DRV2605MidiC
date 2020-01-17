build:
	echo 'Compiling DRV2605MidiC...'
	javac -d out -cp src src/Main.java
start-midi:
	java -classpath ./target/classes Main -L