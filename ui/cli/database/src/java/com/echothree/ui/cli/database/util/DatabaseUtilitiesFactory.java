// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

    private static String BASE = "com.echothree.ui.cli.database.db.";
    private static String DEFAULT = "default";
    
    private static String DB_ECHOTHREE = "echothree";
    private static String DB_REPORTING = "reporting";
    
    private static Log log = LogFactory.getLog(DatabaseUtilitiesFactory.class);
    
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

            if(result.length() == 0) {
                result = null;
            }
        }

        return result;
    }
    
    private String getProperty(Configuration configuration, String database, String databaseProperty, boolean required) {
        String defaultProperty = BASE + DEFAULT + "." + databaseProperty;
        String specificProperty = BASE + database + "." + databaseProperty;
        String defaultValue = trimToNull(configuration.getString(defaultProperty));
        String specificValue = trimToNull(configuration.getString(specificProperty));
        String value = specificValue == null ? defaultValue : specificValue;
        
        if(value == null && required) {
            log.fatal(defaultProperty + " is a required property");
        }
        
        return value;
    }
    
    public DatabaseUtilities getDatabaseUtilities(Configuration configuration, boolean verbose, Database database)
            throws Exception {
        DatabaseUtilities result = null;
        String connectionClass = getProperty(configuration, DB_ECHOTHREE, "class", true);
        String connectionUrl  = getProperty(configuration, DB_ECHOTHREE, "url", true);
        String connectionUser = getProperty(configuration, DB_ECHOTHREE, "user", true);
        String connectionPassword = getProperty(configuration, DB_ECHOTHREE, "password", true);
        String connectionCharacterSet = getProperty(configuration, DB_ECHOTHREE, "characterSet", true);
        String connectionCollation = getProperty(configuration, DB_ECHOTHREE, "collation", true);
        
        if(connectionClass.equals("com.mysql.cj.jdbc.Driver")) {
            result = new DatabaseUtilitiesForMySQL(verbose, database, connectionClass, connectionUrl, connectionUser,
                    connectionPassword, connectionCharacterSet, connectionCollation);
        } else {
            throw new Exception("unknown driverClassName \"" + connectionClass + "\"");
        }
        
        return result;
    }

    public DatabaseViewUtilities getDatabaseViewUtilities(Configuration configuration, boolean verbose, Database database)
            throws Exception {
        String connectionClass = getProperty(configuration, DB_REPORTING, "class", true);
        String connectionUrl  = getProperty(configuration, DB_REPORTING, "url", true);
        String connectionUser = getProperty(configuration, DB_REPORTING, "user", true);
        String connectionPassword = getProperty(configuration, DB_REPORTING, "password", true);
        
        return new DatabaseViewUtilities(verbose, database, connectionClass, connectionUrl, connectionUser, connectionPassword);
    }

    public DatabaseUtilitiesForJava getJavaUtilities(boolean verbose, Database theDatabase)
            throws Exception {
        return new DatabaseUtilitiesForJava(verbose, theDatabase);
    }

    public DatabaseUtilitiesForJooq getJooqUtilities(boolean verbose, Database theDatabase)
            throws Exception {
        return new DatabaseUtilitiesForJooq(verbose, theDatabase);
    }

}
