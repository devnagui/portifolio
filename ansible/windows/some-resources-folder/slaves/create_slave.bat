@echo off
rem This script creates a folder and launch script
rem The user provides the name of the slave they defined in Jenkins Node->Name
rem The user provides the secret key provided in jenkins

if "%JENKINS_TOOLS%" == "" (
   echo Please set the Jenkins_tools sys env var
   echo Run the setup_env_var.bat in the setup folder to set default values
   EXIT /B 
)

echo Please enter the Slave Name as set in Jenkins
set /p id=Enter Slave Id: 

IF EXIST %id% (
echo Directory already exists, unable to overwrite
echo Please delete the slave directory before retrying
EXIT /B
)

mkdir %id%
set file=%id%\launchSlave.bat

echo @echo off > %file%
echo rem Launch script for %id% >> %file%
echo mode con: cols=160 lines=15 >> %file%
echo TITLE slave %id% >> %file%

set /p key=Enter Slave secret key: 
echo java -jar %%JENKINS_TOOLS%%\slave.jar -jnlpUrl https://dummy-jenkins-url:port/jenkins/computer/%id%/slave-agent.jnlp -secret %key% >> %file%