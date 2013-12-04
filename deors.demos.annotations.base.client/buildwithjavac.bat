@echo off
set CP=c:\projects\deors.demos\annotations\deors.demos.annotations.base\target\deors.demos.annotations.base-1.0-SNAPSHOT.jar
set PCP=%CP%;c:\projects\deors.demos\annotations\deors.demos.annotations.base.processors\target\deors.demos.annotations.base.processors-1.0-SNAPSHOT.jar
set S=src\main\java\deors\demos\annotations\base\client\SimpleAnnotationsTest.java
javac -classpath %CP% -processorpath %PCP% %S%
