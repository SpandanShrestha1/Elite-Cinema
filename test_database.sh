#!/bin/bash

# Compile the test class
javac -cp "target/classes:target/dependency/*" src/main/java/com/elitecinema/util/TestDatabaseConnection.java -d target/classes

# Run the test
java -cp "target/classes:target/dependency/*" com.elitecinema.util.TestDatabaseConnection
