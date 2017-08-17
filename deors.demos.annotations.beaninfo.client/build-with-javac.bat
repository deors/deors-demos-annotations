@echo off
set BASE_ROOT=c:\code\deors.demos\annotations-jdk9\deors.demos.annotations.beaninfo
set PROC_ROOT=c:\code\deors.demos\annotations-jdk9\deors.demos.annotations.beaninfo.processors
set OPT=-XprintProcessorInfo -XprintRounds
set CP=%BASE_ROOT%\target\deors.demos.annotations.beaninfo-1.0-SNAPSHOT.jar
set PRP=%CP%;%PROC_ROOT%\target\deors.demos.annotations.beaninfo.processors-1.0-SNAPSHOT.jar
set SRC=src\main\java\deors\demos\annotations\beaninfo\client\Article.java
set GEN_SRC=target\generated-sources\annotations
set GEN_CLS=target\classes
mkdir %GEN_SRC%
mkdir %GEN_CLS%
javac %OPT% -classpath %CP% -processorpath %PRP% %SRC% -s %GEN_SRC% -d %GEN_CLS%
