#!/bin/sh
BASEDIR=$(dirname $0)
/opt/jdk1.7.0_06/bin/java -classpath $BASEDIR/../hw7/target/hw7.jar ling572.Driver  $@
