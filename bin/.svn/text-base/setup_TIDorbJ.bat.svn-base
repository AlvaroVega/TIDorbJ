@echo off

rem Comprueba si las variables TIDORBJ_HOME y JDK_HOME estan definidas
if "%TIDORBJ_HOME%"=="" goto error_HOME
if "%JAVA_HOME%"=="" goto error_JDK
	set SRV_DIR=%TIDORBJ_HOME%
	set SRV_BIN=%SRV_DIR%\bin
	set SRV_LIB=%SRV_DIR%\lib

	rem Configura el PATH para los procesos del servicio
	set PATH=%SRV_BIN%;%PATH%

	rem Configura el CLASSPATH con las librerias del servicio
	set CLASSPATH=%SRV_LIB%\tidorbj.jar;%CLASSPATH%

	set SRV_DIR=
	set SRV_BIN=
	set SRV_LIB=
	goto end

:error_HOME
	echo Debe configurar la variable de entorno TIDORBJ_HOME
	echo.
	goto end

:error_JDK
	echo Debe configurar la variable de entorno JAVA_HOME
	echo.

:end
