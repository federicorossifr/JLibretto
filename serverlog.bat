@echo off
mkdir build\classes
cd src
@echo Compilazione...
javac -classpath ..\libs\xstream-1.4.7.jar;..\libs\xmlpull-1.1.3.1.jar; jlibretto\backend\ServerDiLog.java -d ..\build\classes\
cd ..\build\classes
@echo Esecuzione...
color 2f
java -classpath ..\..\libs\xstream-1.4.7.jar;..\..\libs\xmlpull-1.1.3.1.jar;..\..\libs\xpp3_min-1.1.4c.jar; jlibretto.backend.ServerDiLog 8080
pause