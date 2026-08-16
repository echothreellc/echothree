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

package com.echothree.ui.cli.database.util.javagen;

import com.echothree.ui.cli.database.util.definition.Component;
import com.echothree.ui.cli.database.util.definition.ColumnDataType;
import com.echothree.ui.cli.database.util.definition.Database;
import com.echothree.ui.cli.database.util.definition.Table;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class EntityJavaGenerator extends JavaGenerator {

    public EntityJavaGenerator(boolean verbose, Database database) {
        super(verbose, database);
    }

    public void export(String baseDirectory)
    throws Exception {
        for(var theComponent: myComponents) {
            var componentDirectory = createEntityDirectoryForComponent(theComponent, baseDirectory);
            
            for(var theTable: theComponent.getTables()) {
                if(theTable.hasEID()) {
                    var classFileName = theTable.getEntityClass() + ".java";
                    var f = new File(componentDirectory + File.separatorChar + classFileName);
                    
                    try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
                        var pw = new PrintWriter(bw);
                        
                        writeCopyright(pw);
                        writeVersion(pw, classFileName);
                        writePackage(pw, theComponent.getEntityPackage());
                        writeEntityImports(pw, theTable);
                        writeEntityClass(pw, theTable);
                    }
                }
            }
        }
    }
    
    public void writeEntityFKPKImports(PrintWriter pw, Table theTable)
    throws Exception {
        var foreignImports = new HashSet<String>();
        foreignImports.add(theTable.getPKImport()); // make sure we don't import ourselves
        
        for(var theForeignKey: theTable.getForeignKeys()) {
            var fkTableName = theForeignKey.getDestinationTable();
            var fkTable = theForeignKey.getTable().getDatabase().getTable(fkTableName);

            var fkImport = fkTable.getPKImport();
            if(!foreignImports.contains(fkImport)) {
                pw.println("import " + fkImport + ";");
                foreignImports.add(fkImport);
            }
        }
        
        if(foreignImports.size() > 1)
            pw.println("");
    }
    
    public void writeEntityFKEntityImports(PrintWriter pw, Table theTable)
    throws Exception {
        var foreignImports = new HashSet<String>();
        
        foreignImports.add(theTable.getEntityImport()); // make sure we don't import ourselves
        
        for(var theForeignKey: theTable.getForeignKeys()) {
            var fkTableName = theForeignKey.getDestinationTable();
            var fkTable = theForeignKey.getTable().getDatabase().getTable(fkTableName);

            var fkImport = fkTable.getEntityImport();
            if(!foreignImports.contains(fkImport)) {
                pw.println("import " + fkImport + ";");
                foreignImports.add(fkImport);
            }
        }
        if(foreignImports.size() > 1)
            pw.println("");
    }
    
    public void writeEntityFKFactoryImports(PrintWriter pw, Table theTable)
    throws Exception {
        var foreignImports = new HashSet<String>();
        
        foreignImports.add(theTable.getFactoryImport()); // make sure we don't import ourselves
        
        for(var theForeignKey: theTable.getForeignKeys()) {
            var fkTableName = theForeignKey.getDestinationTable();
            var fkTable = theForeignKey.getTable().getDatabase().getTable(fkTableName);

            var fkImport = fkTable.getFactoryImport();
            if(!foreignImports.contains(fkImport)) {
                pw.println("import " + fkImport + ";");
                foreignImports.add(fkImport);
            }
        }
        if(foreignImports.size() > 1)
            pw.println("");
    }
    
    public void writeEntityFKImports(PrintWriter pw, Table theTable)
    throws Exception {
        writeEntityFKPKImports(pw, theTable);
        writeEntityFKEntityImports(pw, theTable);
        writeEntityFKFactoryImports(pw, theTable);
    }
    
    public void writeEntityImports(PrintWriter pw, Table theTable)
    throws Exception {
        pw.println("import " + theTable.getPKImport() + ";");
        pw.println("");
        
        writeEntityFKImports(pw, theTable);
        
        pw.println("import " + theTable.getPKImport() + ";");
        pw.println("");
        pw.println("import " + theTable.getValueImport() + ";");
        pw.println("");
        pw.println("import " + theTable.getFactoryImport() + ";");
        pw.println("");
        pw.println("import com.echothree.util.common.exception.PersistenceException;");
        pw.println("import com.echothree.util.common.exception.PersistenceDatabaseException;");
        pw.println("import com.echothree.util.common.exception.PersistenceNotNullException;");
        pw.println("import com.echothree.util.common.exception.PersistenceReadOnlyException;");
        pw.println("");
        pw.println("import com.echothree.util.common.persistence.BasePK;");
        pw.println("");
        pw.println("import com.echothree.util.common.persistence.type.ByteArray;");
        pw.println("");
        pw.println("import com.echothree.util.server.persistence.BaseEntity;");
        pw.println("import com.echothree.util.server.persistence.EntityPermission;");
        pw.println("import com.echothree.util.server.persistence.Session;");
        pw.println("import com.echothree.util.server.persistence.ThreadSession;");
        pw.println("");
        pw.println("import java.io.Serializable;");
        pw.println("");
        pw.println("import javax.annotation.Nonnull;");
        pw.println("import javax.annotation.Nullable;");
        pw.println("");
    }
    
    public void writeEntityInstanceVariables(PrintWriter pw, Table theTable) {
        pw.println("    private " + theTable.getPKClass() + " _pk;");
        pw.println("    private " + theTable.getValueClass() + " _value;");
        pw.println("    ");
    }
    
    public void writeEntityConstructors(PrintWriter pw, Table theTable) {
        var entityClass = theTable.getEntityClass();
        
        pw.println("    /** Creates a new instance of " + entityClass + " */");
        pw.println("    public " + entityClass + "()");
        pw.println("            throws PersistenceException {");
        pw.println("        super();");
        pw.println("    }");
        pw.println("    ");
        pw.println("    /** Creates a new instance of " + entityClass + " */");
        pw.println("    public " + entityClass + "(" + theTable.getValueClass() + " value, EntityPermission entityPermission) {");
        pw.println("        super(entityPermission);");
        pw.println("        ");
        pw.println("        _value = value;");
        pw.println("        _pk = value.getPrimaryKey();");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeEntityCoreFunctions(PrintWriter pw, Table theTable) {
        var entityClass = theTable.getEntityClass();
        var factoryClass = theTable.getFactoryClass();
        var valueClass = theTable.getValueClass();

        pw.println("    @Override");
        pw.println("    @Nonnull");
        pw.println("    public " + factoryClass + " getBaseFactoryInstance() {");
        pw.println("        return " + factoryClass + ".getInstance();");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public boolean hasBeenModified() {");
        pw.println("        return _value.hasBeenModified();");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public int hashCode() {");
        pw.println("        return _pk.hashCode();");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    @Nonnull");
        pw.println("    public String toString() {");
        pw.println("        return _pk.toString();");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public boolean equals(Object other) {");
        pw.println("        if(this == other)");
        pw.println("            return true;");
        pw.println("        ");
        pw.println("        if(other instanceof " + entityClass + " that) {");
        pw.println("            " + valueClass + " thatValue = that.get" + valueClass + "();");
        pw.println("            return _value.equals(thatValue);");
        pw.println("        } else {");
        pw.println("            return false;");
        pw.println("        }");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public void store()");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        getBaseFactoryInstance().store(this);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public void remove()");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        getBaseFactoryInstance().remove(this);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Nonnull");
        pw.println("    public " + valueClass + " get" + valueClass + "() {");
        pw.println("        return _value;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public void set" + valueClass + "(@Nonnull " + valueClass + " value)");
        pw.println("            throws PersistenceReadOnlyException {");
        pw.println("        checkReadWrite();");
        pw.println("        _value = value;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    @Nonnull");
        pw.println("    public " + theTable.getPKClass() + " getPrimaryKey() {");
        pw.println("        return _pk;");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeEntityGetsSets(PrintWriter pw, Table theTable) {
        theTable.getColumns().forEach((column) -> {
            var type = column.getType();
            
            if (type != ColumnDataType.EID) {
                var getFunctionName = column.getGetFunctionName();
                var setFunctionName = column.getSetFunctionName();
                var variableName = column.getVariableName();
                var javaType = column.getTypeAsJavaType();
                var nullAnnotation = column.getNullAllowed() ? "@Nullable" : "@Nonnull";
                String fkEntityClass = null;

                pw.println("    " + nullAnnotation);
                pw.println("    public " + javaType + " " + getFunctionName + "() {");
                pw.println("        return _value." + getFunctionName + "();");
                pw.println("    }");
                pw.println("    ");
                
                if(type == ColumnDataType.FOREIGN_KEY) {
                    var getEntityFunctionName = column.getGetEntityFunctionName();
                    var fkFactoryClass = column.getFKFactoryClass();
                    
                    fkEntityClass = column.getFKEntityClass();

                    pw.println("    " + nullAnnotation);
                    pw.println("    public " + fkEntityClass + " " + getEntityFunctionName + "(EntityPermission entityPermission) {");
                    if(column.getNullAllowed()) {
                        pw.println("        " + javaType + " pk = " + getFunctionName + "();");
                        pw.println("        " + fkEntityClass + " entity = pk == null? null: " + fkFactoryClass + ".getInstance().getEntityFromPK(entityPermission, pk);");
                        pw.println("        ");
                        pw.println("        return entity;");
                    } else {
                        pw.println("        return " + fkFactoryClass + ".getInstance().getEntityFromPK(entityPermission, " + getFunctionName + "());");
                    }
                    pw.println("    }");
                    pw.println("    ");
                    pw.println("    " + nullAnnotation);
                    pw.println("    public " + fkEntityClass + " " + getEntityFunctionName + "() {");
                    pw.println("        return " + getEntityFunctionName + "(EntityPermission.READ_ONLY);");
                    pw.println("    }");
                    pw.println("    ");
                    pw.println("    " + nullAnnotation);
                    pw.println("    public " + fkEntityClass + " " + getEntityFunctionName + "ForUpdate() {");
                    pw.println("        return " + getEntityFunctionName + "(EntityPermission.READ_WRITE);");
                    pw.println("    }");
                    pw.println("    ");
                }
                
                pw.println("    public void " + setFunctionName + "(" + nullAnnotation + " " + javaType + " " + variableName + ")");
                pw.println("            throws PersistenceNotNullException, PersistenceReadOnlyException {");
                pw.println("        checkReadWrite();");
                pw.println("        _value." + setFunctionName + "(" + variableName + ");");
                pw.println("    }");
                pw.println("    ");
                
                if(type == ColumnDataType.FOREIGN_KEY) {
                    pw.println("    public void " + column.getSetEntityFunctionName() + "(" + nullAnnotation + " " + fkEntityClass + " entity) {");
                    pw.println("        " + setFunctionName + "(entity == null ? null : entity.getPrimaryKey());");
                    pw.println("    }");
                    pw.println("    ");
                }
                
                pw.println("    public boolean " + getFunctionName + "HasBeenModified() {");
                pw.println("        return _value." + getFunctionName + "HasBeenModified();");
                pw.println("    }");
                pw.println("    ");
            }
        });
    }
    
    public void writeEntityClass(PrintWriter pw, Table theTable) {
        pw.println("public class " + theTable.getEntityClass());
        pw.println("        extends BaseEntity");
        pw.println("        implements Serializable {");
        pw.println("    ");
        
        writeEntityInstanceVariables(pw, theTable);
        writeEntityConstructors(pw, theTable);
        writeEntityCoreFunctions(pw, theTable);
        writeEntityGetsSets(pw, theTable);
        
        pw.println("}");
    }
    
}
