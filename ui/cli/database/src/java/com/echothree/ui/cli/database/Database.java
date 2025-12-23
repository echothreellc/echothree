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

package com.echothree.ui.cli.database;

import com.echothree.ui.cli.database.util.DatabaseDefinitionParser;
import com.echothree.ui.cli.database.util.DatabaseUtilitiesFactory;
import com.echothree.ui.cli.database.util.Databases;
import java.io.File;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Database {
    
    private static Log log = LogFactory.getLog(Database.class);
    private static Configuration configuration = loadConfiguration();

    private static CommandLine getCommandLine(String args[])
            throws ParseException {
        return new DefaultParser().parse(new Options()
                .addOption("c", "charset", false, "set character set and collation for database, tables and columns")
                .addOption("s", "structure", false, "check and update (if necessary) database structure")
                .addOption("j", "java", false, "export Java CMP entity beans")
                .addOption("e", "empty", false, "empty database")
                .addOption("r", "reporting", false, "create reporting views")
                .addOption("v", "verbose", false, "verbose debugging messages")
                .addOption("g", "generated", true, "target directory for generated code"), args);
    }

    private static Configuration loadConfiguration() {
        Configuration result = null;

        try {
            result = new DefaultConfigurationBuilder(Database.class.getResource("/config.xml")).getConfiguration();
        } catch(ConfigurationException ex) {
            log.error(ex);
        }

        return result;
    }
    
    public static void main(String args[])
            throws Exception {
        var line = getCommandLine(args);
        var doCharacterSetAndCollation = line.hasOption("c");
        var doStructure = line.hasOption("s");
        var doJava = line.hasOption("j");
        var doEmpty = line.hasOption("e");
        var doReporting = line.hasOption("r");
        var doVerbose = line.hasOption("v");
        var doGenerated = line.hasOption("g");

        if(doCharacterSetAndCollation || doStructure || doJava || doEmpty || doReporting) {
            var theDatabases = new Databases();

            var databaseDefinitionParser = new DatabaseDefinitionParser(theDatabases);
            databaseDefinitionParser.parse("/DatabaseDefinition.xml");

            var echothreeDatabase = theDatabases.getDatabase("echothree");

            if(doCharacterSetAndCollation || doStructure || doEmpty) {
                var myUtilities = DatabaseUtilitiesFactory.getInstance().getDatabaseUtilities(configuration, doVerbose, echothreeDatabase);

                if(doEmpty) {
                    System.out.println("Emptying database...");
                    myUtilities.emptyDatabase();
                    System.out.println("   ...done.");
                }

                if(doCharacterSetAndCollation) {
                    System.out.println("Setting character set and collation for database, tables and columns...");
                    myUtilities.setCharacterSetAndCollection();
                    System.out.println("   ...done.");
                }
                
                if(doStructure) {
                    System.out.println("Checking database structure...");
                    myUtilities.checkDatabase();
                    System.out.println("   ...done.");
                }
            }
            
            if(doReporting) {
                var databaseViewUtilities = DatabaseUtilitiesFactory.getInstance().getDatabaseViewUtilities(configuration, doVerbose, echothreeDatabase);
                
                databaseViewUtilities.execute();
            }

            String generatedDirectory = null;
            if(doJava) {
                if(doGenerated) {
                    generatedDirectory = line.getOptionValue("g");
                }

                if(generatedDirectory == null) {
                    generatedDirectory = "generated";
                }

            }

            if(doJava) {
                System.out.println("Exporting Java...");
                var myJavaUtilities = DatabaseUtilitiesFactory.getInstance().getJavaUtilities(doVerbose, echothreeDatabase);
                myJavaUtilities.exportJava(generatedDirectory + File.separatorChar + "java");
                System.out.println("   ...done.");
            }
        }
    }
    
}
