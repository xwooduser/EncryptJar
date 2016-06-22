@echo off
title JAR°ü»ìÏýÆ÷
cd .
set APP_HOME=%cd%
::del /f coar.dll 
::cd ../../../
::cd c++/coar/Debug
::copy coar.dll  %APP_HOME%
cd  %APP_HOME% 
set CLASSPATH=.;%APP_HOME%\commons-logging-1.0.4.jar;;%APP_HOME%\jre\jre\lib\rt.jar
::if %JAVA_HOME%="" goto SETJAVA
::SETJAVA
SET JAVA_HOME=%APP_HOME%\jre
::echo %JAVA_HOME%
set path=.;%JAVA_HOME%\bin;%path%
javac.exe -encoding utf-8  rname.java   
echo rname.java±àÒë³É¹¦£¡
java.exe  -Xms128m -Xmx800m   rname
@pause
