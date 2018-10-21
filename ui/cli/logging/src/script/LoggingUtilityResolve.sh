#! /bin/bash
if [ ! -f /tmp/LoggingUtilityResolve.lock ]; then
  touch /tmp/LoggingUtilityResolve.lock

  ECHOTHREE_HOME=/usr/local/echothree
  JAVA_HOME=/usr/java/latest

  PATH=$JAVA_HOME/bin:$PATH

  export PATH
  export JAVA_HOME
  export ECHOTHREE_HOME

  java -cp $ECHOTHREE_HOME/lib/LoggingUtility.jar:$ECHOTHREE_HOME/lib/echothree-client.jar:$ECHOTHREE_HOME/lib/mysql-connector-java-bin.jar:$ECHOTHREE_HOME/lib/commons-cli.jar LoggingUtility -r

  rm -f /tmp/LoggingUtilityResolve.lock
fi
