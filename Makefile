help:
	./mvnw exec:java -Dexec.mainClass="Main" -Dexec.args="-h"

build:
	./mvnw clean install

start-midi:
	./mvnw exec:java -Dexec.mainClass="Main"

start-log:
	./mvnw exec:java -Dexec.mainClass="Main" -Dexec.args="-L"

start-motor-only:
	./mvnw exec:java -Dexec.mainClass="Main" -Dexec.args="-w"
