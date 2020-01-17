build:
	echo 'Compiling DRV2605MidiC...'
	#javac -d out -cp src src/Main.java
	./mvnw clean install
start-midi:
	# java -classpath ./target/classes Main -L
	./mvnw exec:java -Dexec.mainClass="Main" -Dexec.args="-L"