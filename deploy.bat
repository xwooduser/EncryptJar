
@echo on
set source_=D:\study\java\jni\coar\test\WEB-INF\classes\junit

set target_=D:\study\java\jni\coar\test\WEB-INF\lib

echo y| xcopy /y %source_%  %target_% /s /a  /e /h /p /f  


@pause
