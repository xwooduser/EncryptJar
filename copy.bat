@echo on
cd .
set APP_HOME=%cd%
echo y| xcopy /y %APP_HOME%\jar  %APP_HOME%\xxx  /s /a  /e /h /p /f  
@pause