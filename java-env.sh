#!/bin/bash

: ${JDK:="8"}

#sudo update-java-alternatives -s java-${JDK}-oracle;
#export JAVA_HOME="/usr/lib/jvm/java-${JDK}-oracle"

sudo apt-get -y install oracle-java${JDK}-set-default
export JAVA_HOME="/usr/lib/jvm/java-${JDK}-oracle"

echo Setting JAVA_HOME to: ${JAVA_HOME}


