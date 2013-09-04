@echo off

rem Obtiene la lista de argumentos de la linea de comandos
set ARGS=%1 %2 %3 %4 %5 %6 %7 %8 %9

if not "%OS%"=="Windows_NT" goto Win9x
	set ARGS=%*
	goto endif
:Win9x
	shift
	if "%9"=="" goto endif
	set ARGS=%ARGS% %9
	goto Win9x
:endif

set JVM=%JAVA_HOME%\bin\java
%JVM% -classpath .;%TIDORBJ_HOME%\lib\tidorbj.jar %ARGS%

set ARGS=
set JVM=
