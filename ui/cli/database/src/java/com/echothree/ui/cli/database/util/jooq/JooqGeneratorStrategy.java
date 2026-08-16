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

package com.echothree.ui.cli.database.util.jooq;

import com.echothree.ui.cli.database.util.definition.Column;
import com.echothree.ui.cli.database.util.definition.DatabaseDefinitionParser;
import com.echothree.ui.cli.database.util.definition.Databases;
import com.google.common.base.CaseFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.jooq.codegen.GeneratorStrategy.Mode;
import org.jooq.codegen.KeepNamesGeneratorStrategy;
import org.jooq.meta.ColumnDefinition;
import org.jooq.meta.ConstraintDefinition;
import org.jooq.meta.Definition;
import org.jooq.meta.ForeignKeyDefinition;
import org.jooq.meta.TableDefinition;
import org.jooq.meta.UniqueKeyDefinition;

public class JooqGeneratorStrategy
        extends KeepNamesGeneratorStrategy {

    private final Map<String, String> tableNames = new HashMap<>();
    private final Map<String, String> columnNames = new HashMap<>();
    private final Map<String, String> componentNames = new HashMap<>();

    public JooqGeneratorStrategy() {
        try {
            var databases = new Databases();
            var parser = new DatabaseDefinitionParser(databases);

            parser.parse("/DatabaseDefinition.xml");

            var database = databases.getDatabase("echothree");

            for(var component : database.getComponents()) {
                for(var table : component.getTables()) {
                    var dbTableName = table.getDbTableName();

                    tableNames.put(dbTableName, table.getNamePlural());
                    componentNames.put(dbTableName, component.getName().toLowerCase(Locale.ROOT));

                    for(var column : table.getColumns()) {
                        columnNames.put(dbTableName + "." + column.getDbColumnName(), javaColumnName(column));
                    }
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

    private String upperUnderscore(String name) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, name);
    }

    private String keyTableName(TableDefinition table) {
        return upperUnderscore(tableNames.get(table.getInputName()));
    }

    private String keyColumnNames(Iterable<ColumnDefinition> columns) {
        return java.util.stream.StreamSupport.stream(columns.spliterator(), false)
                .map(this::javaName)
                .map(this::upperUnderscore)
                .collect(Collectors.joining("_AND_"));
    }

    private String keyIdentifier(Definition definition) {
        if(definition instanceof ForeignKeyDefinition key) {
            return keyTableName(key.getKeyTable()) + "_" + keyColumnNames(key.getKeyColumns()) + "_FK";
        } else if(definition instanceof UniqueKeyDefinition key) {
            var tableName = keyTableName(key.getTable());

            return key.isPrimaryKey() ? tableName + "_PK" : tableName + "_" + keyColumnNames(key.getKeyColumns()) + "_UK";
        }

        return null;
    }

    @Override
    public String getJavaIdentifier(Definition definition) {
        var name = keyIdentifier(definition);

        if(name == null) {
            name = javaName(definition);
        }

        return name == null ? super.getJavaIdentifier(definition) : name;
    }

    @Override
    public String getGlobalReferencesJavaClassName(Definition definition,
            Class<? extends Definition> definitionType) {
        return ConstraintDefinition.class.isAssignableFrom(definitionType)
                ? "KeyRegistry"
                : super.getGlobalReferencesJavaClassName(definition, definitionType);
    }

    @Override
    public String getJavaPackageName(Definition definition, Mode mode) {
        var packageName = super.getJavaPackageName(definition, mode);

        if(definition instanceof TableDefinition table) {
            if(mode == Mode.RECORD) {
                packageName = packageName.replace(".tables.records", ".records");
            }

            var componentName = componentNames.get(table.getInputName());
            if(componentName != null) {
                packageName += "." + componentName;
            }
        }

        return packageName;
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
