@echo off

echo Build Process
echo -------------

set JAVA_HOME=C:\Progra~1\Java\jdk1.5.0_06

if "%JAVA_HOME%" == "" goto error_no_java

rem set ANT_HOME=.\lib\ant
set ANT_HOME=.\lib\ant
set ANT_LIB=%ANT_HOME%\lib
set ANT_PROJECTS_HOME=.\dev\ant

set LOCALCLASSPATH=%JAVA_HOME%\lib\tools.jar;%ANT_LIB%\ant.jar;%ANT_LIB%\ant-ant2svg.jar;%ANT_LIB%\ant-nodeps.jar;.\lib\proguard\proguard.jar;%ANT_LIB%\ant-launcher.jar;%ANT_LIB%\junit.jar;%ANT_LIB%\jaxp.jar;%ANT_LIB%\xercesImpl.jar;%ANT_LIB%\xalan.jar;%ANT_LIB%\bsf.jar

echo Building with classpath %LOCALCLASSPATH%
echo Starting Ant...

%JAVA_HOME%\bin\java -classpath %LOCALCLASSPATH% -Dant.home=%ANT_HOME% -DJAVA_HOME.dir=%JAVA_HOME% org.apache.tools.ant.Main -buildfile %ANT_PROJECTS_HOME%\%2\build.xml %1 -Dbuild.what=%2 -Dbuild.param1=%3 -Dbuild.param2=%4 %5 %6

goto end

:error_no_java
echo "ERROR: JAVA_HOME not found in your environment."
echo "Please, set the JAVA_HOME variable in your environment to match the location of the Java Virtual Machine you want to use."

:end