@echo off
set CP=c:\projects\deors.demos\annotations\deors.demos.annotations.beaninfo\target\deors.demos.annotations.beaninfo-1.0-SNAPSHOT.jar
set PCP=%CP%;c:\projects\deors.demos\annotations\deors.demos.annotations.beaninfo.processors\target\deors.demos.annotations.beaninfo.processors-1.0-SNAPSHOT.jar
set S=src\main\java\deors\demos\annotations\beaninfo\client\Article.java
javac -classpath %CP% -processorpath %PCP% %S%
