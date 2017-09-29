@echo off
set OPT=-XprintProcessorInfo -XprintRounds
set ROOT_FOLDER=c:\code
set CP=%ROOT_FOLDER%\deors.demos\annotations\deors.demos.annotations.beaninfo\target\deors.demos.annotations.beaninfo-1.0-SNAPSHOT.jar
set PROC=%CP%;%ROOT_FOLDER%\deors.demos\annotations\deors.demos.annotations.beaninfo.processors\target\deors.demos.annotations.beaninfo.processors-1.0-SNAPSHOT.jar
set SRC=src\main\java\deors\demos\annotations\beaninfo\client\Article.java
set GEN_SRC=target\generated-sources\annotations
set GEN_CLS=target\classes
mkdir %GEN_SRC%
mkdir %GEN_CLS%
javac %OPT% -classpath %CP% -processorpath %PROC% %SRC% -s %GEN_SRC% -d %GEN_CLS%
