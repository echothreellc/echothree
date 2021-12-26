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

import static com.google.common.base.Charsets.UTF_8;
import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

public class DatabaseUtilitiesForJooq {

    boolean verbose;
    Database myDatabase;
    List<Component> myComponents;

    /** Creates a new instance of DatabaseUtilitiesForJava */
    public DatabaseUtilitiesForJooq(boolean verbose, Database theDatabase) {
        this.verbose = verbose;
        myDatabase = theDatabase;
        myComponents = theDatabase.getComponents();
    }

    public String createXmlDirectory(String baseDirectory) {
        var theDirectory = new File(baseDirectory);

        if(!theDirectory.exists()) {
            theDirectory.mkdirs();
        }

        return baseDirectory;
    }

    public void exportJooq(String baseDirectory)
    throws Exception {
        var directory = createXmlDirectory(baseDirectory);
        var xmlFileName = "XMLDatabase.xml";
        var f = new File(directory + File.separatorChar + xmlFileName);

        try (BufferedWriter bw = Files.newBufferedWriter(f.toPath(), UTF_8)) {
            var pw = new PrintWriter(bw);

            pw.print("""
                    <?xml version="1.0" encoding="UTF-8"?>
                    <information_schema xmlns="http://www.jooq.org/xsd/jooq-meta-3.15.0.xsd">
                        <schemata>
                            <schema>
                                <schema_name>%s</schema_name>
                            </schema>
                        </schemata>
                    """.formatted(myDatabase.getName()));

            // Test only.
            pw.print("""
                        <tables>
                            <table>
                                <table_schema>echothree</table_schema>
                                <table_name>PartyTypes</table_name>
                            </table>
                        </tables>
                        <columns>
                            <column>
                                <table_schema>echothree</table_schema>
                                <table_name>PartyTypes</table_name>
                                <column_name>ptyp_PartyTypeId</column_name>
                                <data_type>bigint</data_type>
                                <numeric_precision>19</numeric_precision>
                                <ordinal_position>1</ordinal_position>
                                <is_nullable>false</is_nullable>
                            </column>
                            <column>
                                <table_schema>echothree</table_schema>
                                <table_name>PartyTypes</table_name>
                                <column_name>ptyp_PartyTypeName</column_name>
                                <data_type>varchar</data_type>
                                <character_maximum_length>40</character_maximum_length>
                                <ordinal_position>2</ordinal_position>
                                <is_nullable>false</is_nullable>
                            </column>
                            <column>
                                <table_schema>echothree</table_schema>
                                <table_name>PartyTypes</table_name>
                                <column_name>ptyp_ParentPartyTypeId</column_name>
                                <data_type>bigint</data_type>
                                <numeric_precision>19</numeric_precision>
                                <is_nullable>true</is_nullable>
                            </column>
                            <column>
                                <table_schema>echothree</table_schema>
                                <table_name>PartyTypes</table_name>
                                <column_name>ptyp_BillingAccountSequenceTypeId</column_name>
                                <data_type>bigint</data_type>
                                <numeric_precision>19</numeric_precision>
                                <is_nullable>true</is_nullable>
                            </column>
                            <column>
                                <table_schema>echothree</table_schema>
                                <table_name>PartyTypes</table_name>
                                <column_name>ptyp_AllowUserLogins</column_name>
                                <data_type>int</data_type>
                                <numeric_precision>10</numeric_precision>
                                <is_nullable>false</is_nullable>
                            </column>
                            <column>
                                <table_schema>echothree</table_schema>
                                <table_name>PartyTypes</table_name>
                                <column_name>ptyp_AllowPartyAliases</column_name>
                                <data_type>int</data_type>
                                <numeric_precision>10</numeric_precision>
                                <is_nullable>false</is_nullable>
                            </column>
                            <column>
                                <table_schema>echothree</table_schema>
                                <table_name>PartyTypes</table_name>
                                <column_name>ptyp_IsDefault</column_name>
                                <data_type>int</data_type>
                                <numeric_precision>10</numeric_precision>
                                <is_nullable>false</is_nullable>
                            </column>
                            <column>
                                <table_schema>echothree</table_schema>
                                <table_name>PartyTypes</table_name>
                                <column_name>ptyp_SortOrder</column_name>
                                <data_type>int</data_type>
                                <numeric_precision>10</numeric_precision>
                                <is_nullable>false</is_nullable>
                            </column>
                        </columns>
                    """);

            pw.print("""
                    </information_schema>                                   
                    """);
        }
    }
    
}
