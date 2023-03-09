#! /bin/bash
if [ ! -f /tmp/LoggingUtilityExport.lock ]; then
  touch /tmp/LoggingUtilityExport.lock

  ECHOTHREE_HOME=/usr/local/echothree
  JAVA_HOME=/usr/java/latest

  PATH=$JAVA_HOME/bin:$PATH

  export PATH
  export JAVA_HOME
  export ECHOTHREE_HOME

  java -cp $ECHOTHREE_HOME/lib/LoggingUtility.jar:$ECHOTHREE_HOME/lib/echothree-client.jar:$ECHOTHREE_HOME/lib/mysql-connector-j-bin.jar:$ECHOTHREE_HOME/lib/commons-cli.jar LoggingUtility -e

  rm -f /tmp/LoggingUtilityExport.lock
fi
