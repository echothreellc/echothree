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

import com.echothree.ui.cli.database.util.definition.DatabaseDefinitionParser;
import com.echothree.ui.cli.database.util.definition.Databases;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.jooq.ForeignKey;
import org.jooq.UniqueKey;
import org.jooq.codegen.GeneratorStrategy.Mode;
import org.jooq.codegen.JavaGenerator;
import org.jooq.codegen.JavaWriter;
import org.jooq.meta.ConstraintDefinition;
import org.jooq.meta.ForeignKeyDefinition;
import org.jooq.meta.SchemaDefinition;
import org.jooq.meta.UniqueKeyDefinition;

public class JooqJavaGenerator
        extends JavaGenerator {

    private final Map<String, String> componentNames = new LinkedHashMap<>();

    public JooqJavaGenerator() {
        try {
            var databases = new Databases();
            var parser = new DatabaseDefinitionParser(databases);

            parser.parse("/DatabaseDefinition.xml");

            for(var component : databases.getDatabase("echothree").getComponents()) {
                for(var table : component.getTables()) {
                    componentNames.put(table.getDbTableName(), component.getName());
                }
            }
        } catch(Exception e) {
            throw new IllegalStateException("Unable to load jOOQ key components", e);
        }
    }

    private <T> Map<String, List<T>> componentLists() {
        var result = new LinkedHashMap<String, List<T>>();

        for(var component : componentNames.values().stream().distinct().toList()) {
            result.put(component, new ArrayList<>());
        }

        return result;
    }

    private String className(String component, String keyType) {
        return component + keyType + "Keys";
    }

    private String keysPackage(SchemaDefinition schema, String component) {
        var packageName = getStrategy().getGlobalReferencesJavaPackageName(schema, ConstraintDefinition.class) + ".keys";

        return component == null ? packageName : packageName + "." + component.toLowerCase(Locale.ROOT);
    }

    private JavaWriter newKeysWriter(SchemaDefinition schema, String className, String component) {
        var keysFile = getStrategy().getGlobalReferencesFile(schema, ConstraintDefinition.class);
        var directory = component == null
                ? keysFile.getParentFile()
                : new File(keysFile.getParentFile(), "keys/" + component.toLowerCase(Locale.ROOT));

        return newJavaWriter(new File(directory, className + ".java"));
    }

    private void printUniqueKeys(JavaWriter out, List<UniqueKeyDefinition> keys) {
        var distribute = keys.size() > maxMembersPerInitialiser();

        for(var i = 0; i < keys.size(); i++) {
            var key = keys.get(i);

            if(distribute) {
                var keyType = out.ref(getStrategy().getFullJavaClassName(key.getTable(), Mode.RECORD));
                var keyId = getStrategy().getJavaIdentifier(key);
                var block = i / maxMembersPerInitialiser();

                out.println("public static final %s<%s> %s = UniqueKeys%s.%s;", UniqueKey.class, keyType, keyId, block, keyId);
            } else {
                printUniqueKey(out, -1, key, false);
            }
        }

        if(distribute) {
            for(var i = 0; i < keys.size(); i++) {
                printUniqueKey(out, i, keys.get(i), true);
            }
            out.println("}");
        }
    }

    private void printForeignKeys(JavaWriter out, List<ForeignKeyDefinition> keys) {
        var distribute = keys.size() > maxMembersPerInitialiser();

        for(var i = 0; i < keys.size(); i++) {
            var key = keys.get(i);

            if(distribute) {
                var keyType = out.ref(getStrategy().getFullJavaClassName(key.getKeyTable(), Mode.RECORD));
                var referencedType = out.ref(getStrategy().getFullJavaClassName(key.getReferencedTable(), Mode.RECORD));
                var keyId = getStrategy().getJavaIdentifier(key);
                var block = i / maxMembersPerInitialiser();

                out.println("public static final %s<%s, %s> %s = ForeignKeys%s.%s;", ForeignKey.class, keyType, referencedType, keyId, block, keyId);
            } else {
                printForeignKey(out, -1, key, false);
            }
        }

        if(distribute) {
            for(var i = 0; i < keys.size(); i++) {
                printForeignKey(out, i, keys.get(i), true);
            }
            out.println("}");
        }
    }

    private void generateKeyClass(SchemaDefinition schema, String className, String component,
            List<String> componentInterfaces, List<UniqueKeyDefinition> uniqueKeys,
            List<ForeignKeyDefinition> foreignKeys) {
        var componentClass = component != null;
        var out = newKeysWriter(schema, className, component);

        out.refConflicts(getStrategy().getJavaIdentifiers(uniqueKeys));
        out.refConflicts(getStrategy().getJavaIdentifiers(foreignKeys));
        if(componentClass) {
            out.printPackageSpecification(keysPackage(schema, component));
            out.printImports();
        } else {
            printGlobalReferencesPackage(out, schema, ConstraintDefinition.class);
        }
        printClassJavadoc(out, componentClass
                ? "Key definitions for the " + className.replace("Keys", "") + " component."
                : "Internal registry for all generated key definitions.");
        if(componentClass) {
            out.println("public interface %s {", className);
        } else {
            out.println("public class %s implements %s {", className,
                    componentInterfaces.stream()
                            .collect(java.util.stream.Collectors.joining(", ")));
        }

        printUniqueKeys(out, uniqueKeys);
        printForeignKeys(out, foreignKeys);

        out.println("}");
        closeJavaWriter(out);
    }

    @Override
    protected void generateRelations(SchemaDefinition schema) {
        Map<String, List<UniqueKeyDefinition>> uniqueKeys = componentLists();
        Map<String, List<ForeignKeyDefinition>> foreignKeys = componentLists();

        for(var key : schema.getDatabase().getKeys(schema)) {
            uniqueKeys.get(componentNames.get(key.getTable().getInputName())).add(key);
        }
        for(var key : schema.getDatabase().getForeignKeys(schema)) {
            foreignKeys.get(componentNames.get(key.getKeyTable().getInputName())).add(key);
        }

        var componentInterfaces = new ArrayList<String>();
        for(var component : componentNames.values().stream().distinct().toList()) {
            var keys = uniqueKeys.get(component);

            if(!keys.isEmpty()) {
                var currentClass = className(component, "Unique");

                generateKeyClass(schema, currentClass, component, List.of(), keys, List.of());
                componentInterfaces.add(keysPackage(schema, component) + "." + currentClass);
            }
        }
        for(var component : componentNames.values().stream().distinct().toList()) {
            var keys = foreignKeys.get(component);

            if(!keys.isEmpty()) {
                var currentClass = className(component, "Foreign");

                generateKeyClass(schema, currentClass, component, List.of(), List.of(), keys);
                componentInterfaces.add(keysPackage(schema, component) + "." + currentClass);
            }
        }

        generateKeyClass(schema, getStrategy().getGlobalReferencesJavaClassName(schema, ConstraintDefinition.class),
                null, componentInterfaces, List.of(), List.of());
    }

}
