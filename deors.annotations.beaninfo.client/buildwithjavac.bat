@echo off
set CP=c:\projects\deors.demos\annotations\deors.annotations.beaninfo\target\deors.annotations.beaninfo-1.0-SNAPSHOT.jar
set PCP=%CP%;c:\projects\deors.demos\annotations\deors.annotations.beaninfo.processors\target\deors.annotations.beaninfo.processors-1.0-SNAPSHOT.jar
set S=src\main\java\deors\annotations\beaninfo\client\Article.java
javac -classpath %CP% -processorpath %PCP% %S%
