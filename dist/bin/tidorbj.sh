#!/bin/sh
set +u

JVM=$JDK_HOME/bin/java

$JVM -classpath .:$TIDORBJ_HOME/lib/tidorbj.jar $*
