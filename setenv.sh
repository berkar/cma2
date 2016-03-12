#!/bin/bash

MAVEN_BUILD=3.3.9
JDK=8
export PROJ_SHORTNAME=cma2
export PROJ_HOME="$(pwd)"
echo Setting PROJ_HOME to: ${PROJ_HOME}

echo "========================================================================="
echo ""
echo "  Project Environment"
echo ""
echo "  MAVEN_BUILD: ${MAVEN_BUILD}"
echo ""
echo "  JDK: ${JDK}"
echo ""
echo "  PROJ_SHORTNAME: ${PROJ_SHORTNAME}"
echo ""
echo "  PROJ_HOME: ${PROJ_HOME}"
echo ""
echo "========================================================================="
echo

# Define this file to override default java handling, e.g. setting JAVA_HOME and JDK to use
JAVA_ENV="java-env.sh"
if [ -f "$JAVA_ENV" ]
then
	. ${JAVA_ENV}
fi

# Define this file to override default maven handling, e.g. setting MAVEN to use
MAVEN_ENV="maven-env.sh"
if [ -f "$MAVEN_ENV" ]
then
	. ${MAVEN_ENV}
fi

echo "========================================================================="
echo "Found build environment:"
echo ""
mvn --version
echo "========================================================================="
echo

