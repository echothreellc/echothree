// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.ui.cli.database.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class DatabaseUtilitiesFactory {

    private static final String BASE = "com.echothree.ui.cli.database.db.";
    private static final String DEFAULT = "default";
    
    private static final String DB_ECHO_THREE = "echothree";
    private static final String DB_REPORTING = "reporting";
    
    private static final Log log = LogFactory.getLog(DatabaseUtilitiesFactory.class);
    
    private DatabaseUtilitiesFactory() {
        super();
    }
    
    private static class DatabaseUtilitiesFactoryHolder {
        static DatabaseUtilitiesFactory instance = new DatabaseUtilitiesFactory();
    }
    
    public static DatabaseUtilitiesFactory getInstance() {
        return DatabaseUtilitiesFactoryHolder.instance;
    }
    
    /** Take a string and trim all leading and trailing spaces from it.
     * If the resulting length is zero, return null. Duplicated from StringUtils
     * since StringUtils may not have been built at this time.
     */
    public String trimToNull(String string) {
        String result = null;

        if(string != null) {
            result = string.trim();

            if(result.isEmpty()) {
                result = null;
            }
        }

        return result;
    }
    
    private String getProperty(Configuration configuration, String database, String databaseProperty, boolean required) {
        var defaultProperty = BASE + DEFAULT + "." + databaseProperty;
        var specificProperty = BASE + database + "." + databaseProperty;
        var defaultValue = trimToNull(configuration.getString(defaultProperty));
        var specificValue = trimToNull(configuration.getString(specificProperty));
        var value = specificValue == null ? defaultValue : specificValue;
        
        if(value == null && required) {
            log.fatal(defaultProperty + " is a required property");
        }
        
        return value;
    }
    
    public DatabaseUtilities getDatabaseUtilities(Configuration configuration, boolean verbose, Database database)
            throws Exception {
        DatabaseUtilities result;
        var connectionClass = getProperty(configuration, DB_ECHO_THREE, "class", true);
        var connectionUrl  = getProperty(configuration, DB_ECHO_THREE, "url", true);
        var connectionUser = getProperty(configuration, DB_ECHO_THREE, "user", true);
        var connectionPassword = getProperty(configuration, DB_ECHO_THREE, "password", true);
        var connectionCharacterSet = getProperty(configuration, DB_ECHO_THREE, "characterSet", true);
        var connectionCollation = getProperty(configuration, DB_ECHO_THREE, "collation", true);
        
        if(connectionClass.equals("com.mysql.cj.jdbc.Driver")) {
            result = new DatabaseUtilitiesForMySQL(verbose, database, connectionClass, connectionUrl, connectionUser,
                    connectionPassword, connectionCharacterSet, connectionCollation);
        } else {
            throw new Exception("unknown driverClassName \"" + connectionClass + "\"");
        }
        
        return result;
    }

    public DatabaseViewUtilities getDatabaseViewUtilities(Configuration configuration, boolean verbose, Database database) {
        var connectionClass = getProperty(configuration, DB_REPORTING, "class", true);
        var connectionUrl  = getProperty(configuration, DB_REPORTING, "url", true);
        var connectionUser = getProperty(configuration, DB_REPORTING, "user", true);
        var connectionPassword = getProperty(configuration, DB_REPORTING, "password", true);
        
        return new DatabaseViewUtilities(verbose, database, connectionClass, connectionUrl, connectionUser, connectionPassword);
    }

    public DatabaseUtilitiesForJava getJavaUtilities(boolean verbose, Database theDatabase) {
        return new DatabaseUtilitiesForJava(verbose, theDatabase);
    }

    public DatabaseUtilitiesForJooq getJooqUtilities(boolean verbose, Database theDatabase)
            throws Exception {
        return new DatabaseUtilitiesForJooq(verbose, theDatabase);
    }

}
