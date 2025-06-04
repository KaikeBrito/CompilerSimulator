@echo off
call cleanup.bat

REM ← Caminho (relativo) para os jars do JFlex e do CUP
set "flex=lib\jflex-full-1.9.1.jar"
set "cup=lib\java-cup-11b.jar"

REM ← Bibliotecas necessárias em tempo de compilação e execução
set "libs=.;lib\java-cup-11b.jar;lib\java-cup-11b-runtime.jar;lib\jflex-1.8.2.jar"

REM Garante que a pasta 'compiler' existe (lá é onde vão ser gerados .java com package compiler;)
if not exist compiler (
    mkdir compiler
)

REM 1) Gerar o Scanner a partir do Scanner.flex (gera compiler\Scanner.java)
java -jar %flex% -d . compiler\Scanner.flex

REM 2) Gerar o parser + sym a partir do parser.cup (gera compiler\parser.java e compiler\sym.java)
java -jar %cup% -destdir . -parser parser -symbols sym compiler\parser.cup

REM 3) Compilar TODOS os .java dentro de compiler, incluindo os gerados pelo JFlex/CUP
javac -cp "%libs%" compiler\*.java

REM 4) Executar a classe Main (assumindo que Main.java esteja em package compiler e que tenha método 'public static void main')
java -cp "%libs%" compiler.Main
