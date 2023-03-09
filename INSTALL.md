# Installation

## IMPORTANT

All installation steps and example configuration files should be evaluated for security
and other aspects in your particular environment. What's included here is a very basic
configuration of a single server to be used for development purposes only. A production
environment will look much different.

A proper understanding of Linux system administration and security is critical to
deploying these applications.

## Prerequisites

* CentOS 6
* Amazon Corretto 17 (17.0.1.12.1)
* Apache Ant 1.10 (1.10.9)
* MySQL 8.0 (8.0.28 GA)
* WildFly 26 (26.1.3)

The full version numbers in parentheses are the most recent versions used for testing.

## Amazon Corretto 17

* [What Is Amazon Corretto 17?](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/what-is-corretto-17.html)
* [Downloads for Amazon Corretto 17](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)
* [Versioned Downloads of Corretto on GitHub](https://github.com/corretto/corretto-17/releases)

## MySQL 8.0

* [Using the MySQL Yum Repository](https://dev.mysql.com/doc/mysql-repo-excerpt/8.0/en/)

## Recommended Directory Structure

```
/usr/local/jboss
    ... EchoThree
    ... share
    ... wildfly-26.1.3.Final
    ... wildfly-latest -> wildfly-26.1.3.Final
```

`/usr/local/jboss` and all files below it are owned by the jboss user and group.

## jboss User's Login Script

In `~/.bash_profile` for jboss:

```
JAVA_HOME=/usr/java/latest

ANT_HOME=~jboss/Java/apache-ant-latest
ANT_OPTS="-Xmx3072m"

PATH=$HOME/bin:$JAVA_HOME/bin:$ANT_HOME/bin:$PATH

export PATH
export JAVA_HOME
export ANT_HOME
export ANT_OPTS
```

## Java JCE Jurisdiction Policy

In `$JAVA_HOME/jre/lib/security/java.security`, review the `crypto.policy` setting and if possible (and legal),
change the setting to `unlimited`:
```
crypto.policy=unlimited
```

If this is not available, the source code to the Echo Three application will need to be modified appropriately to
use algorithms available in your jurisdiction.

## Git Checkout

As jboss:
```
$ git checkout git@gitlab.com:echothree/echothree.git EchoThree
```

### MySQL Server Configuration

`EchoThree/EchoThree/doc/configuration/mysql/my.cnf` should be copied to `/etc/my.cnf`.

A few important configuration items from it include:
* Maximum packet size
* Recommended buffer sizes
* Default database character set
* Default database collation
* Transaction isolation level
* InnoDB configuration

Maximum packet size must be increased due to BLOBs and CLOBs being stored in the database.

### Databases

Two databases are required:
* echothree
* reporting

`echothree` is the primary database used by the application and contains many tables include versioning
information for the entities contained within them. The `reporting` database simplifies the database by
hiding the additional tables and columns used for versioning of data. `echothree` is the only database
that's strictly required, `reporting` simplifies ad hoc queries.

Connected to MySQL as root:
```
CREATE DATABASE echothree
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_0900_ai_ci;
CREATE DATABASE reporting
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_0900_ai_ci;
```

### Users

Creating the `echothree` user:
```
CREATE USER 'echothree'@'127.0.0.1' IDENTIFIED BY '$Exampl3Passw0rd#';
GRANT XA_RECOVER_ADMIN ON *.* TO 'echothree'@'127.0.0.1';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP,ALTER,INDEX,REFERENCES
    ON echothree.*
    TO 'echothree'@'127.0.0.1';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,CREATE VIEW,DROP,ALTER,INDEX,REFERENCES
    ON reporting.*
    TO 'echothree'@'127.0.0.1';
```

Optionally create a `backup` user with permission to dump the `echothree` database:
```
CREATE USER 'backup'@'localhost' IDENTIFIED BY '$Exampl3Passw0rd#';
GRANT SELECT,LOCK TABLES
    ON echothree.*
    TO 'backup'@'localhost';
```

## Apache Configuration

### https Configuration

Configure Apache with a self-signed SSL/TLS certificate. Let's Encrypt is another great option for
obtaining a certificate.

### Connecting Apache to WildFly

As root:
```
# cp EchoThree/EchoThree/doc/configuration/apache/wildfly.conf /etc/httpd/conf.d
```

Customize it as needed, the /main application should be protected from outside access.

## WildFly Configuration

### Add a Management User for WildFly

As jboss:
```
$ cd /usr/local/jboss/wildfly-latest/bin
$ ./add-user.sh

What type of user do you wish to add? 
 a) Management User (mgmt-users.properties) 
 b) Application User (application-users.properties)
(a): a

Enter the details of the new user to add.
Using realm 'ManagementRealm' as discovered from the existing property files.
Username : lukeskywalker
Password recommendations are listed below. To modify these restrictions edit the add-user.properties configuration file.
 - The password should be different from the username
 - The password should not be one of the following restricted values {root, admin, administrator}
 - The password should contain at least 8 characters, 1 alphabetic character(s), 1 digit(s), 1 non-alphanumeric symbol(s)
Password : 
Re-enter Password : 
What groups do you want this user to belong to? (Please enter a comma separated list, or leave blank for none)[  ]: 
About to add user 'lukeskywalker' for realm 'ManagementRealm'
Is this correct yes/no? yes
Added user 'lukeskywalker' to file '/usr/local/jboss/wildfly-26.1.3.Final/standalone/configuration/mgmt-users.properties'
Added user 'lukeskywalker' to file '/usr/local/jboss/wildfly-26.1.3.Final/domain/configuration/mgmt-users.properties'
Added user 'lukeskywalker' with groups  to file '/usr/local/jboss/wildfly-26.1.3.Final/standalone/configuration/mgmt-groups.properties'
Added user 'lukeskywalker' with groups  to file '/usr/local/jboss/wildfly-26.1.3.Final/domain/configuration/mgmt-groups.properties'
Is this new user going to be used for one AS process to connect to another AS process? 
e.g. for a slave host controller connecting to the master or for a Remoting connection for server to server EJB calls.
yes/no? no
```

### Configure CentOS to Start WildFly Automatically

`wildfly-latest/docs/contrib/scripts/init.d/wildfly-init-redhat.sh` should be copied
to `/etc/init.d/wildfly`.

`EchoThree/EchoThree/doc/configuration/centos/wildfly` should be copied
to `/etc/default/wildfly`.

As root:
```
# chkconfig --add wildfly
```

### Configure WildFly

WildFly requires that the Infinispan caches and datasource configurations be setup.

As jboss, edit `/usr/local/jboss/wildfly-latest/standalone/configuration/standalone-full-ha.xml`,
there are two subsystems that require additional configuration for the Echo Three applications:

In the datasources subsystem, add the contents of `EchoThree/doc/configuration/wildfly/datasources.txt`:
```
        <subsystem xmlns="urn:jboss:domain:datasources:6.0">
            <datasources>
                <!-- file contents added here -->
            </datasources>
        </subsystem>
```

In the infinispan subsystem, add the contents of `EchoThree/doc/configuration/wildfly/infinispan.txt`:
```
        <subsystem xmlns="urn:jboss:domain:infinispan:9.0">
            <!-- file contents added here -->
        </subsystem>
```

## Setting Up Ant

Echo Three uses Apache Ivy during the build process to retrieve dependencies. Apache Ivy
must be added to a directory that contains additional libraries utilized by Apache Ant.

As jboss:
```
$ cd EchoThree
$ ant setup
```

## Compiling Echo Three

As jboss:
```
$ cd EchoThree
$ ant compile
```

## Deploying Echo Three

As jboss:
```
$ cp EchoThree/EchoThree/build/lib/mysql-connector-j.jar wildfly-latest/standalone/deployments/mysql-connector-j.jar
$ cd EchoThree
$ ant deploy
```

## Initializing the Database

As jboss:
```
$ cd EchoThree/EchoThree/ui/cli/database
$ ant GenerateDatabase
$ ant RegenerateReporting
```

## Starting WildFly

As root:
```
# service start wildfly
```

To monitor WildFly, as jboss:
```
$ tail -f /usr/local/jboss/wildfly-latest/standalone/log/server.log
```

## Loading the Initial Data

As jboss:
```
$ cd EchoThree/EchoThree/ui/cli/dataloader
$ ant Load
```

## Go to the Application

<https://127.0.0.1/main/>

## Encryption Keys

After loading the initial data, in the jboss user's home directory there will be a `keys`
subdirectory created. Inside there, are three subdirectories, and inside them, a
subdirectory for the host the key was created on. These keys are used to encrypt and
decrypt certain data in the database. They are held in an Infinispan cache, and if all
instances of WildFly that contain the cache are terminated, the keys will need to be
reloaded from the files. Two of the three keys must be available to unlock the encrypted
data in the database.

After making two of the three keys available, as jboss:
```
$ cd EchoThree/EchoThree/ui/cli/dataloader
$ ant LoadKeys
```

Changing these encryption keys is possible as well when needed.

After making all three keys available, as jboss:
```
$ cd EchoThree/EchoThree/ui/cli/dataloader
$ ant ChangeKeys
```

It is recommended that in a production system, these three keys be distributed to three
separate people who are not under the same authority. As long as one person does not
control two or more keys, they cannot unlock the encrypted data in the database.
Procedures must be established around the production environment as well in order to
reduce the ability to obtain the keys from the cache.

In one implementation, we used USB flash memory drives that we would mount and unmount
as necessary, under the keys subdirectory, under the control of three separate employees.
