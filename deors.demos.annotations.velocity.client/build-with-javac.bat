@echo off
set OPT=-XprintProcessorInfo -XprintRounds
set CP=c:\projects\deors.demos\annotations\deors.demos.annotations.beaninfo\target\deors.demos.annotations.beaninfo-1.0-SNAPSHOT.jar
set PROC=%CP%;c:\projects\deors.demos\annotations\deors.demos.annotations.velocity.processors\target\deors.demos.annotations.velocity.processors-1.0-SNAPSHOT.jar
set PROC=%PROC%;%HOME%\.m2\repository\org\apache\velocity\velocity\1.6.4\velocity-1.6.4.jar
set PROC=%PROC%;%HOME%\.m2\repository\commons-collections\commons-collections\3.2.1\commons-collections-3.2.1.jar
set PROC=%PROC%;%HOME%\.m2\repository\commons-lang\commons-lang\2.4\commons-lang-2.4.jar
set PROC=%PROC%;%HOME%\.m2\repository\org\apache\velocity\velocity-tools\2.0\velocity-tools-2.0.jar
set SRC=src\main\java\deors\demos\annotations\velocity\client\Article.java
set GEN_SRC=target\generated-sources\annotations
set GEN_CLS=target\classes
mkdir %GEN_SRC%
mkdir %GEN_CLS%
javac %OPT% -classpath %CP% -processorpath %PROC% %SRC% -s %GEN_SRC% -d %GEN_CLS%
