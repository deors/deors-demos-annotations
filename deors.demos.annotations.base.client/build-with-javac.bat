@echo off
set OPT=-XprintProcessorInfo -XprintRounds
set ROOT_FOLDER=c:\code
set CP=%ROOT_FOLDER%\deors.demos\annotations\deors.demos.annotations.base\target\deors.demos.annotations.base-1.0-SNAPSHOT.jar
set PROC=%CP%;%ROOT_FOLDER%\deors.demos\annotations\deors.demos.annotations.base.processors\target\deors.demos.annotations.base.processors-1.0-SNAPSHOT.jar
set SRC=src\main\java\deors\demos\annotations\base\client\SimpleAnnotationsTest.java
set GEN_SRC=target\generated-sources\annotations
set GEN_CLS=target\classes
mkdir %GEN_SRC%
mkdir %GEN_CLS%
javac %OPT% -classpath %CP% -processorpath %PROC% %SRC% -s %GEN_SRC% -d %GEN_CLS%
