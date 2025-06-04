@echo off

REM Apaga os arquivos gerados pelo JFlex/CUP (que sempre moram em compiler\)
if exist compiler\Scanner.java   del compiler\Scanner.java
if exist compiler\parser.java    del compiler\parser.java
if exist compiler\sym.java       del compiler\sym.java

REM Apaga todos os .class recursivamente (caso tenha sido gerado)
for /r compiler %%f in (*.class) do del "%%f"
for /r .        %%f in (*.class) do if not "%%~dpf"=="%CD%\compiler\" del "%%f"
