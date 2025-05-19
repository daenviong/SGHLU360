@echo off
echo Compilando SGHLU...
javac -cp "postgresql-42.7.5.jar" -d bin src\SGHLU.java src\gui\*.java src\modelo\*.java src\persistencia\*.java
pause