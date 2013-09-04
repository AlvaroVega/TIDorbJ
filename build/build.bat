@echo off

rem Variables que deben revisarse segun el entorno de compilacion
SET JAVA_HOME=C:\jdk1.3.1_01
SET ORACLE_HOME=C:\Oracle8i
SET WORKAREA_HOME=C:\workspace

rem Variables del entorno de integracion
SET BASEPJ_HOME=I:\scib12\bases\mesa\SCIB12.pj
SET PLATFORM_HOME=I:\scib12\platform_v12.0.0
SET TOOLS_HOME=I:\scib12\tools_v12.0.0

SET JWSDP_HOME=%TOOLS_HOME%\jwsdp
SET JAXP_LIB=%JWSDP_HOME%\jaxp\lib
SET ANT_HOME=%TOOLS_HOME%\apache-ant-1.6.1
SET ANT_CLASSPATH=%ANT_HOME%\lib\ant.jar;%ANT_HOME%\lib\ant-launcher.jar
SET JAXP_CLASSPATH=%JAXP_LIB%\jaxp-api.jar;%JAXP_LIB%\endorsed\dom.jar;%JAXP_LIB%\endorsed\sax.jar;%JAXP_LIB%\endorsed\xalan.jar;%JAXP_LIB%\endorsed\xsltc.jar;%JAXP_LIB%\endorsed\xercesImpl.jar
SET JAVA_TOOLS=%JAVA_HOME%\lib\tools.jar
"%JAVA_HOME%\bin\java" -Dant.home="%ANT_HOME%" -Dtools.home="%TOOLS_HOME%" -Dplatform.home="%PLATFORM_HOME%" -Dbasepj.home="%BASEPJ_HOME%" -Dworkarea.home="%WORKAREA_HOME%" -Doracle.home="%ORACLE_HOME%" -classpath "%JAVA_TOOLS%;%JAXP_CLASSPATH%;%ANT_CLASSPATH%" -Dbasedir=. org.apache.tools.ant.Main -buildfile "%WORKAREA_HOME%\Build\main.xml" %1 %2 %3 %4 %5 %6 %7 %8 %9
