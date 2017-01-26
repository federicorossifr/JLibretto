@echo off
mkdir build\classes
cd src
@echo Compilazione...
javac -classpath ..\libs\xstream-1.4.7.jar;..\libs\xmlpull-1.1.3.1.jar; jlibretto\frontend\AvvioJLibretto.java -d ..\build\classes\
cd ..\build\classes
@echo Esecuzione...
color 2f
java -classpath ..\..\libs\xstream-1.4.7.jar;..\..\libs\xmlpull-1.1.3.1.jar;..\..\libs\xpp3_min-1.1.4c.jar;..\..\libs\mysql-connector-java-5.1.34-bin.jar; jlibretto.frontend.AvvioJLibretto
pause
color
cd ..\..