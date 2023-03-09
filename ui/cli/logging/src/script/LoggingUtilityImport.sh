#!/bin/bash

ECHOTHREE_HOME=/usr/local/echothree
#JAVA_HOME=/usr/java/latest
JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/1.6.0/Home

PATH=$JAVA_HOME/bin:$PATH

export PATH
export JAVA_HOME
export ECHOTHREE_HOME

java -cp $ECHOTHREE_HOME/lib/LoggingUtility.jar:$ECHOTHREE_HOME/lib/echothree-client.jar:$ECHOTHREE_HOME/lib/mysql-connector-j-bin.jar:$ECHOTHREE_HOME/lib/commons-cli.jar LoggingUtility -i

