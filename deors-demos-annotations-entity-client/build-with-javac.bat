@echo off
set BASE_ROOT=..\deors-demos-annotations-entity
set PROC_ROOT=..\deors-demos-annotations-entity-processors
set M2_REPO=%USERPROFILE%\.m2\repository
set OPT=-XprintProcessorInfo -XprintRounds
set CP=%BASE_ROOT%\target\deors-demos-annotations-entity-1.0-SNAPSHOT.jar
set CP=%CP%;%M2_REPO%\javax\persistence\persistence-api\1.0\persistence-api-1.0.jar
set PRP=%CP%;%PROC_ROOT%\target\deors-demos-annotations-entity-processors-1.0-SNAPSHOT.jar
set PRP=%PRP%;%M2_REPO%\org\apache\velocity\velocity-engine-core\2.4\velocity-engine-core-2.4.jar
set PRP=%PRP%;%M2_REPO%\org\apache\velocity\tools\velocity-tools-generic\3.1\velocity-tools-generic-3.1.jar
set PRP=%PRP%;%M2_REPO%\org\apache\commons\commons-lang3\3.17.0\commons-lang3-3.17.0.jar
set PRP=%PRP%;%M2_REPO%\org\slf4j\slf4j-api\1.7.36\slf4j-api-1.7.36.jar
set SRC=src\main\java\deors\demos\annotations\entity\client\*.java
set GEN_SRC=target\generated-sources\annotations
set GEN_CLS=target\classes
mkdir %GEN_SRC%
mkdir %GEN_CLS%
javac %OPT% -classpath %CP% -processorpath %PRP% %SRC% -s %GEN_SRC% -d %GEN_CLS%
