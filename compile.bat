@echo off
cd .
set APP_HOME=%cd%
set CLASSPATH=%APP_HOME%\commons-logging-1.0.4.jar;%APP_HOME%\jre\jre\lib\rt.jar
javac  -encoding utf-8  rname.java
javah -classpath . -jni rname 
javac  -encoding utf-8  rrname.java
javah -classpath . -jni rrname 
echo rname.java°¢rrname.java±‡“Î≥…π¶£°
@pause
