@echo off
set PATH=%JAVA_HOME%\bin;%PATH%
for /D %%a in (*) do (
  if exist "%%a\launchSlave.bat" (
    echo Launching %%a
    start %%a\launchSlave.bat
  )
) 
pause