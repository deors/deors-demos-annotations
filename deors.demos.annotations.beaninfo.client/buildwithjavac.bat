@echo off
set CP=c:\projects\deors.demos\annotations\deors.demos.annotations.beaninfo\target\deors.demos.annotations.beaninfo-1.0-SNAPSHOT.jar
set PCP=%CP%;c:\projects\deors.demos\annotations\deors.demos.annotations.beaninfo.processors\target\deors.demos.annotations.beaninfo.processors-1.0-SNAPSHOT.jar
set S=src\main\java\deors\demos\annotations\beaninfo\client\Article.java
set GEN_SRC=target\generated-sources\annotations
set GEN_CLS=target\classes
javac -classpath %CP% -processorpath %PCP% %S% -s %GEN_SRC% -d %GEN_CLS%
