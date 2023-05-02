#! /bin/bash
if [ ! -f /tmp/LoggingUtilityResolve.lock ]; then
  touch /tmp/LoggingUtilityResolve.lock

  ECHO_THREE_HOME=/usr/local/echothree
  JAVA_HOME=/usr/java/latest

  PATH=$JAVA_HOME/bin:$PATH

  export PATH
  export JAVA_HOME
  export ECHO_THREE_HOME

  java -cp $ECHO_THREE_HOME/lib/LoggingUtility.jar:$ECHO_THREE_HOME/lib/echothree-client.jar:$ECHO_THREE_HOME/lib/mysql-connector-j-bin.jar:$ECHO_THREE_HOME/lib/commons-cli.jar LoggingUtility -r

  rm -f /tmp/LoggingUtilityResolve.lock
fi
