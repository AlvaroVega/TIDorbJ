#!/bin/sh
set +u

if [ -z "$JAVA_HOME" ]; then
	echo "Environment variable JAVA_HOME must be set"
	exit 1
fi

if [ -z "$TIDORBJ_HOME" ]; then
	echo "Environment variable TIDORBJ_HOME must be set"
	exit 1	
fi


JVM=$JDK_HOME/bin/java
PRINT_IOR_CLASSPATH=.:$TIDORBJ_HOME/lib/tidorbj.jar

$JVM -classpath $PRINT_IOR_CLASSPATH es.tid.TIDorbj.tools.PrintIOR $*
