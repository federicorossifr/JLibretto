cd src
c:\prg\jdk8\bin\javac -classpath ..\libs\xstream-1.4.7.jar;..\libs\xmlpull-1.1.3.1.jar; jlibretto\backend\ServerDiLog.java -d ..\build\classes\
cd ..\build\classes
c:\prg\jdk8\bin\java -classpath ..\..\libs\xstream-1.4.7.jar;..\..\libs\xmlpull-1.1.3.1.jar;..\..\libs\xpp3_min-1.1.4c.jar; jlibretto.backend.ServerDiLog 127.0.0.1 8080
pause