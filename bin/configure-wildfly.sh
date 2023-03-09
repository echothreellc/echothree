#!/bin/sh
$JBOSS_HOME/bin/jboss-cli.sh --connect --file="doc/configuration/wildfly/infinispan-cli.txt"
$JBOSS_HOME/bin/jboss-cli.sh --connect --command="reload"

$JBOSS_HOME/bin/jboss-cli.sh --connect --command="deploy build/lib/mysql-connector-j.jar"
$JBOSS_HOME/bin/jboss-cli.sh --connect --file="doc/configuration/wildfly/datasources-cli.txt"

$JBOSS_HOME/bin/jboss-cli.sh --connect --command="deploy build/ears/echothree.ear

$JBOSS_HOME/bin/jboss-cli.sh --connect --command="deploy build/service/job/jar/echothree-job.jar

$JBOSS_HOME/bin/jboss-cli.sh --connect --command="deploy build/ui/web/cms/war/cms.war
$JBOSS_HOME/bin/jboss-cli.sh --connect --command="deploy build/service/graphql/war/graphql.war
$JBOSS_HOME/bin/jboss-cli.sh --connect --command="deploy build/ui/web/letter/war/letter.war
$JBOSS_HOME/bin/jboss-cli.sh --connect --command="deploy build/ui/web/main/war/main.war

