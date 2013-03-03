#!/bin/sh
JAVA_HOME=/opt/jdk1.7.0_06
PATH=$JAVA_HOME/bin:$PATH
cd ../hw7; sbt assembly
