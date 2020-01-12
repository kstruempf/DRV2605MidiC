build:
	echo 'Compiling DRV2605MidiC...'
	javac -d out -cp src src/Main.java
start-midi:
	java -classpath ./out Main midi