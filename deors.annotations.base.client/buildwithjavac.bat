@echo off
set CP=c:\projects\deors.demos\annotations\deors.annotations.base\target\deors.annotations.base-1.0-SNAPSHOT.jar
set PCP=%CP%;c:\projects\deors.demos\annotations\deors.annotations.base.processors\target\deors.annotations.base.processors-1.0-SNAPSHOT.jar
set S=src\main\java\deors\annotations\base\client\SimpleAnnotationsTest.java
javac -classpath %CP% -processorpath %PCP% %S%
