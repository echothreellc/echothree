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

import java.util.HashMap;
import java.util.Map;
import org.jooq.codegen.GeneratorStrategy.Mode;
import org.jooq.codegen.KeepNamesGeneratorStrategy;
import org.jooq.meta.ColumnDefinition;
import org.jooq.meta.Definition;
import org.jooq.meta.TableDefinition;

public class JooqGeneratorStrategy
        extends KeepNamesGeneratorStrategy {

    private final Map<String, String> tableNames = new HashMap<>();
    private final Map<String, String> columnNames = new HashMap<>();

    public JooqGeneratorStrategy() {
        try {
            var databases = new Databases();
            var parser = new DatabaseDefinitionParser(databases);

            parser.parse("/DatabaseDefinition.xml");

            for(var table : databases.getDatabase("echothree").getTables()) {
                var dbTableName = table.getDbTableName();

                tableNames.put(dbTableName, table.getNamePlural());
                for(var column : table.getColumns()) {
                    columnNames.put(dbTableName + "." + column.getDbColumnName(), javaColumnName(column));
                }
            }
        } catch(Exception e) {
            throw new IllegalStateException("Unable to load jOOQ Java names", e);
        }
    }

    private String javaColumnName(Column column) {
        var name = column.getName();

        return name.endsWith("Id") ? name.substring(0, name.length() - 2) : name;
    }

    private String javaName(Definition definition) {
        if(definition instanceof ColumnDefinition column) {
            return columnNames.get(column.getContainer().getInputName() + "." + column.getInputName());
        } else if(definition instanceof TableDefinition table) {
            return tableNames.get(table.getInputName());
        }

        return null;
    }

    @Override
    public String getJavaIdentifier(Definition definition) {
        var name = javaName(definition);

        return name == null ? super.getJavaIdentifier(definition) : name;
    }

    @Override
    public String getJavaSetterName(Definition definition, Mode mode) {
        var name = javaName(definition);

        return name == null ? super.getJavaSetterName(definition, mode) : name;
    }

    @Override
    public String getJavaGetterName(Definition definition, Mode mode) {
        var name = javaName(definition);

        return name == null ? super.getJavaGetterName(definition, mode) : name;
    }

    @Override
    public String getJavaMethodName(Definition definition, Mode mode) {
        var name = javaName(definition);

        return name == null ? super.getJavaMethodName(definition, mode) : name;
    }

    @Override
    public String getJavaClassName(Definition definition, Mode mode) {
        var name = javaName(definition);

        return name == null ? super.getJavaClassName(definition, mode) : name;
    }

    @Override
    public String getJavaMemberName(Definition definition, Mode mode) {
        var name = javaName(definition);

        return name == null ? super.getJavaMemberName(definition, mode) : name;
    }

}
