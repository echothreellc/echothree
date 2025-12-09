// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class DatabaseUtilitiesForJava {
    
    private static final String MODEL_CONTROL_CORE_COMMON_PACKAGE = "com.echothree.model.control.core.common";
    
    boolean verbose;
    Database myDatabase;
    List<Component> myComponents;
    
    /** Creates a new instance of DatabaseUtilitiesForJava */
    public DatabaseUtilitiesForJava(boolean verbose, Database theDatabase) {
        this.verbose = verbose;
        myDatabase = theDatabase;
        myComponents = theDatabase.getComponents();
    }
    
    public String createDirectoryForClassPackage(String classPackage, String baseDirectory) {
        var directory = baseDirectory;
        var currentIndex = 0;
        int nextDot;
        do {
            nextDot = classPackage.indexOf('.', currentIndex);
            if(nextDot == -1)
                directory = directory + File.separatorChar + classPackage.substring(currentIndex);
            else
                directory = directory + File.separatorChar + classPackage.substring(currentIndex, nextDot);
            currentIndex = nextDot + 1;
        } while (nextDot != -1);

        var theDirectory = new File(directory);
        if(!theDirectory.exists()) {
            theDirectory.mkdirs();
        }
        
        return directory;
    }
    
    public String createPKDirectoryForComponent(Component theComponent, String baseDirectory) {
        return createDirectoryForClassPackage(theComponent.getPKPackage(), baseDirectory);
    }
    
    public String createValueDirectoryForComponent(Component theComponent, String baseDirectory) {
        return createDirectoryForClassPackage(theComponent.getValuePackage(), baseDirectory);
    }
    
    public String createEntityDirectoryForComponent(Component theComponent, String baseDirectory) {
        return createDirectoryForClassPackage(theComponent.getEntityPackage(), baseDirectory);
    }
    
    public String createFactoryDirectoryForComponent(Component theComponent, String baseDirectory) {
        return createDirectoryForClassPackage(theComponent.getFactoryPackage(), baseDirectory);
    }
    
    public String createCommonDirectoryForComponent(Component theComponent, String baseDirectory) {
        return createDirectoryForClassPackage(theComponent.getCommonPackage(), baseDirectory);
    }
    
    public String createCommonCoreControlDirectory(String baseDirectory) {
        return createDirectoryForClassPackage(MODEL_CONTROL_CORE_COMMON_PACKAGE, baseDirectory);
    }
    
    public void writeCopyright(PrintWriter pw) {
        pw.println("// --------------------------------------------------------------------------------");
        pw.println("// Copyright 2002-2025 Echo Three, LLC");
        pw.println("//");
        pw.println("// Licensed under the Apache License, Version 2.0 (the \"License\");");
        pw.println("// you may not use this file except in compliance with the License.");
        pw.println("// You may obtain a copy of the License at");
        pw.println("//");
        pw.println("//     http://www.apache.org/licenses/LICENSE-2.0");
        pw.println("//");
        pw.println("// Unless required by applicable law or agreed to in writing, software");
        pw.println("// distributed under the License is distributed on an \"AS IS\" BASIS,");
        pw.println("// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.");
        pw.println("// See the License for the specific language governing permissions and");
        pw.println("// limitations under the License.");
        pw.println("// --------------------------------------------------------------------------------");
        pw.println("// Generated File -- DO NOT EDIT BY HAND");
        pw.println("// --------------------------------------------------------------------------------");
        pw.println("");
    }
    
    public void writeVersion(PrintWriter pw, String classFileName) {
        pw.println("/**");
        pw.println(" * " + classFileName);
        pw.println(" */");
        pw.println("");
    }
    
    public void writePackage(PrintWriter pw, String classPackage) {
        pw.println("package " + classPackage + ";");
        pw.println("");
    }
    
    public void writePKImports(PrintWriter pw, Table theTable) {
        pw.println("import " + theTable.getConstantsImport() + ";");
        pw.println("");
        pw.println("import com.echothree.util.common.persistence.BasePK;");
        pw.println("");
    }
    
    public void writePKConstructors(PrintWriter pw, Table theTable) {
        var pkClass = theTable.getPKClass();
        
        pw.println("    /** Creates a new instance of " + pkClass + " */");
        pw.println("    public " + pkClass + "(Long entityId) {");
        pw.println("        super(" + theTable.getConstantsClass() + ".COMPONENT_VENDOR_NAME, " + theTable.getConstantsClass() + ".ENTITY_TYPE_NAME, entityId);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    /** Creates a new instance of " + pkClass + " */");
        pw.println("    public " + pkClass + "(String entityId) {");
        pw.println("        super(" + theTable.getConstantsClass() + ".COMPONENT_VENDOR_NAME, " + theTable.getConstantsClass() + ".ENTITY_TYPE_NAME, Long.valueOf(entityId));");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writePKClass(PrintWriter pw, Table theTable) {
        pw.println("public class " + theTable.getPKClass());
        pw.println("        extends BasePK {");
        pw.println("    ");
        
        writePKConstructors(pw, theTable);
        
        pw.println("}");
    }
    
    public void exportPKs(String baseDirectory)
    throws Exception {
        for(var theComponent: myComponents) {
            var componentDirectory = createPKDirectoryForComponent(theComponent, baseDirectory);
            
            for(var theTable: theComponent.getTables()) {
                if(theTable.hasEID()) {
                    var classFileName = theTable.getPKClass() + ".java";
                    var f = new File(componentDirectory + File.separatorChar + classFileName);
                    
                    try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
                        var pw = new PrintWriter(bw);
                        
                        writeCopyright(pw);
                        writeVersion(pw, classFileName);
                        writePackage(pw, theComponent.getPKPackage());
                        writePKImports(pw, theTable);
                        writePKClass(pw, theTable);
                    }
                }
            }
        }
    }
    
    public void writeValueFKImports(PrintWriter pw, Component theComponent, Table theTable)
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
    
    public void writeValueImports(PrintWriter pw, Component theComponent, Table theTable)
    throws Exception {
        pw.println("import " + theTable.getPKImport() + ";");
        pw.println("");
        pw.println("import " + theTable.getFactoryImport() + ";");
        pw.println("");
        
        writeValueFKImports(pw, theComponent, theTable);
        
        pw.println("import com.echothree.util.common.exception.PersistenceCloneException;");
        pw.println("import com.echothree.util.common.exception.PersistenceNotNullException;");
        pw.println("");
        pw.println("import com.echothree.util.server.persistence.BaseValue;");
        pw.println("");
        pw.println("import java.io.Serializable;");
        pw.println("");
        if(theTable.hasBlob()) {
            pw.println("import com.echothree.util.common.persistence.type.ByteArray;");
            pw.println("");
        }
    }
    
    public void writeValueInstanceVariables(PrintWriter pw, Table theTable) {
        var wroteColumnVariable = false;
        var theColumns = theTable.getColumns();
        
        for(var column: theColumns) {
            if(column.getType() != ColumnType.columnEID) {
                var variableName = column.getVariableName();
                
                pw.println("    private " + column.getTypeAsJavaType() + " " + variableName + ";");
                pw.println("    private boolean " + variableName + "HasBeenModified = false;");
                wroteColumnVariable = true;
            }
        }
        
        if(wroteColumnVariable)
            pw.println("    ");
        
        pw.println("    private transient Integer _hashCode = null;");
        pw.println("    private transient String _stringValue = null;");
        pw.println("    ");
        pw.print("    private void constructFields(");
        
        wroteColumnVariable = false;
        for(var column: theColumns) {
            if(column.getType() != ColumnType.columnEID) {
                if(wroteColumnVariable)
                    pw.print(", ");
                pw.print(column.getTypeAsJavaType() + " " + column.getVariableName());
                wroteColumnVariable = true;
            }
        }
        
        pw.println(")");
        pw.println("            throws PersistenceNotNullException {");
        
        theColumns.stream().filter((column) -> (column.getType() != ColumnType.columnEID)).map((column) -> {
            var variableName = column.getVariableName();
            if(!column.getNullAllowed()) {
                pw.println("        checkForNull(" + variableName + ");");
            }
            return variableName;
        }).forEach((variableName) -> {
            pw.println("        this." + variableName + " = " + variableName + ";");
        });
        
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeValueConstructors(PrintWriter pw, Table theTable) {
        var valueClass = theTable.getValueClass();
        var columns = theTable.getColumns();
        
        pw.println("    /** Creates a new instance of " + valueClass + " */");
        pw.print("    public " + valueClass + "(");

        var wroteColumnVariable = false;
        for(var column: columns) {
            if(wroteColumnVariable)
                pw.print(", ");
            
            pw.print(column.getTypeAsJavaType() + " " + column.getVariableName());
            wroteColumnVariable = true;
        }
        
        pw.println(")");
        pw.println("            throws PersistenceNotNullException {");
        pw.println("        super(" + theTable.getEID().getVariableName() + ");");
        pw.print("        constructFields(");
        
        wroteColumnVariable = false;
        for(var column: columns) {
            if(column.getType() != ColumnType.columnEID) {
                if(wroteColumnVariable)
                    pw.print(", ");
                pw.print(column.getVariableName());
                wroteColumnVariable = true;
            }
        }
        
        pw.println(");");
        pw.println("    }");
        pw.println("    ");
        pw.println("    /** Creates a new instance of " + valueClass + " */");
        pw.print("    public " + valueClass + "(");
        
        wroteColumnVariable = false;
        for(var column: columns) {
            if(column.getType() != ColumnType.columnEID) {
                if(wroteColumnVariable)
                    pw.print(", ");
                pw.print(column.getTypeAsJavaType() + " " + column.getVariableName());
                wroteColumnVariable = true;
            }
        }
        
        pw.println(")");
        pw.println("            throws PersistenceNotNullException {");
        pw.println("        super();");
        pw.print("        constructFields(");
        
        wroteColumnVariable = false;
        for(var column: columns) {
            if(column.getType() != ColumnType.columnEID) {
                if(wroteColumnVariable)
                    pw.print(", ");
                pw.print(column.getVariableName());
                wroteColumnVariable = true;
            }
        }
        
        pw.println(");");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeValueCoreFunctions(PrintWriter pw, Table theTable) {
        var factoryClass = theTable.getFactoryClass();
        
        pw.println("   @Override");
        pw.println("   public " + factoryClass + " getBaseFactoryInstance() {");
        pw.println("        return " + factoryClass + ".getInstance();");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeValuePrimaryKey(PrintWriter pw, Table theTable) {
        var pkClass = theTable.getPKClass();
        
        pw.println("   @Override");
        pw.println("    public " + pkClass + " getPrimaryKey() {");
        pw.println("        if(_primaryKey == null) {");
        pw.println("            _primaryKey = new " + pkClass + "(entityId);");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _primaryKey;");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeValueHashAndString(PrintWriter pw, Table theTable) {
        var columns = theTable.getColumns();
        
        pw.println("    private void clearHashAndString() {");
        pw.println("        _hashCode = null;");
        pw.println("        _stringValue = null;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public int hashCode() {");
        pw.println("        if(_hashCode == null) {");
        pw.println("            int hashCode = 17;");
        pw.println("            ");
        pw.println("            hashCode = 37 * hashCode + ((entityId != null) ? entityId.hashCode() : 0);");
        
        if(columns.size() > 1) {
            pw.println("            ");
            
            columns.forEach((column) -> {
                var columnType = column.getType();
                if (columnType != ColumnType.columnEID && columnType != ColumnType.columnBLOB) {
                    var variableName = column.getVariableName();
                    pw.println("            hashCode = 37 * hashCode + ((" + variableName + " != null) ? " + variableName + ".hashCode() : 0);");
                }
            });
        }
        
        pw.println("            ");
        pw.println("            _hashCode = hashCode;");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _hashCode;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public String toString() {");
        pw.println("        if(_stringValue == null) {");
        pw.println("            _stringValue = \"{\" + ");
        pw.println("                    \"entityId=\" + getEntityId() +");

        columns.forEach((column) -> {
            var columnType = column.getType();
            if (columnType != ColumnType.columnEID && columnType != ColumnType.columnBLOB) {
                pw.println("                    \", " + column.getVariableName() + "=\" + " + column.getGetFunctionName() + "() +");
            }
        });

        pw.println("                    \"}\";");
        pw.println("        }");
        pw.println("        return _stringValue;");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeValueEquals(PrintWriter pw, Table theTable) {
        var valueClass = theTable.getValueClass();
        
        pw.println("    @Override");
        pw.println("    public boolean equals(Object other) {");
        pw.println("        if(this == other)");
        pw.println("            return true;");
        pw.println("        ");
        pw.println("        if(!hasIdentity())");
        pw.println("            return false;");
        pw.println("        ");
        pw.println("        if(other instanceof  " + valueClass + " that) {");
        pw.println("            if(!that.hasIdentity())");
        pw.println("                return false;");
        pw.println("            ");
        pw.println("            Long thisEntityId = getEntityId();");
        pw.println("            Long thatEntityId = that.getEntityId();");
        pw.println("            ");
        pw.println("            boolean objectsEqual = thisEntityId.equals(thatEntityId);");
        pw.println("            if(objectsEqual)");
        pw.println("                objectsEqual = isIdentical(that);");
        pw.println("            ");
        pw.println("            return objectsEqual;");
        pw.println("        } else {");
        pw.println("            return false;");
        pw.println("        }");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeValueIdentical(PrintWriter pw, Table theTable) {
        var columns = theTable.getColumns();
        var valueClass = theTable.getValueClass();
        
        pw.println("    public boolean isIdentical(Object other) {");
        pw.println("        if(other instanceof " + valueClass + " that) {");
        pw.println("            boolean objectsEqual = true;");
        pw.println("            ");
        
        if(columns.size() > 1) {
            pw.println("            ");
            
            columns.forEach((column) -> {
                var type = column.getType();
                if (type != ColumnType.columnEID) {
                    var getFunctionName = column.getGetFunctionName();
                    var variableSuffixName = column.getVariableSuffixName();
                    var javaType = column.getTypeAsJavaType();
                    
                    pw.println("            if(objectsEqual) {");
                    pw.println("                " + javaType + " this" + variableSuffixName + " = " + getFunctionName + "();");
                    pw.println("                " + javaType + " that" + variableSuffixName + " = that." + getFunctionName + "();");
                    pw.println("                ");
                    pw.println("                if(this" + variableSuffixName + " == null) {");
                    pw.println("                    objectsEqual = objectsEqual && (that" + variableSuffixName + " == null);");
                    pw.println("                } else {");
                    pw.println("                    objectsEqual = objectsEqual && this" + variableSuffixName + ".equals(that" + variableSuffixName + ");");
                    pw.println("                }");
                    pw.println("            }");
                    pw.println("            ");
                }
            });
        }
        
        pw.println("            return objectsEqual;");
        pw.println("        } else {");
        pw.println("            return false;");
        pw.println("        }");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeValueModified(PrintWriter pw, Table theTable) {
        var columns = theTable.getColumns();
        
        pw.println("    @Override");
        pw.println("    public boolean hasBeenModified() {");
        
        if(columns.size() > 1) {
            pw.print("        return ");

            var variableWritten = false;
            for(var column: columns) {
                if(column.getType() != ColumnType.columnEID) {
                    if(variableWritten)
                        pw.print(" || ");
                    pw.print(column.getVariableName() + "HasBeenModified");
                    variableWritten = true;
                }
            }
            pw.println(";");
        } else
            pw.println("        return false;");
        
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public void clearHasBeenModified() {");
        
        columns.stream().filter((column) -> (column.getType() != ColumnType.columnEID)).forEach((column) -> {
            pw.println("        " + column.getVariableName() + "HasBeenModified = false;");
        });
        
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeValueGetsSets(PrintWriter pw, Table theTable) {
        theTable.getColumns().stream().filter((column) -> (column.getType() != ColumnType.columnEID)).map((column) -> {
            var getFunctionName = column.getGetFunctionName();
            var variableName = column.getVariableName();
            var javaType = column.getTypeAsJavaType();
            
            pw.println("    public " + javaType + " " + getFunctionName + "() {");
            pw.println("        return " + variableName + ";");
            pw.println("    }");
            pw.println("    ");
            if(!column.getNullAllowed()) {
                pw.println("    public void " + column.getSetFunctionName() + "(" + javaType + " " + variableName + ")");
                pw.println("            throws PersistenceNotNullException {");
                pw.println("        checkForNull(" + variableName + ");");
                pw.println("        ");
            } else {
                pw.println("    public void " + column.getSetFunctionName() + "(" + javaType + " " + variableName + ") {");
            }
            pw.println("        boolean update = true;");
            pw.println("        ");
            pw.println("        if(this." + variableName + " != null) {");
            pw.println("            if(this." + variableName + ".equals(" + variableName + ")) {");
            pw.println("                update = false;");
            pw.println("            }");
            pw.println("        } else if(" + variableName + " == null) {");
            pw.println("            update = false;");
            pw.println("        }");
            pw.println("        ");
            pw.println("        if(update) {");
            pw.println("            this." + variableName + " = " + variableName + ";");
            pw.println("            " + variableName + "HasBeenModified = true;");
            pw.println("            clearHashAndString();");
            pw.println("        }");
            pw.println("    }");
            pw.println("    ");
            pw.println("    public boolean " + getFunctionName + "HasBeenModified() {");
            pw.println("        return " + variableName + "HasBeenModified;");
            return column;
        }).map((_item) -> {
            pw.println("    }");
            return _item;
        }).forEach((_item) -> {
            pw.println("    ");
        });
    }
    
    public void writeValueClone(PrintWriter pw, Table theTable) {
        var valueClass = theTable.getValueClass();
        
        pw.println("    @Override");
        pw.println("    public " + valueClass + " clone() {");
        pw.println("        Object result;");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            result = super.clone();");
        pw.println("        } catch (CloneNotSupportedException cnse) {");
        pw.println("            // This shouldn't happen, fail when it does.");
        pw.println("            throw new PersistenceCloneException(cnse);");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return (" + valueClass + ")result;");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeValueClass(PrintWriter pw, Table theTable) {
        pw.println("public class " + theTable.getValueClass());
        pw.println("        extends BaseValue" + "<" + theTable.getPKClass() + ">");
        pw.println("        implements Cloneable, Serializable {");
        pw.println("    ");
        
        writeValueInstanceVariables(pw, theTable);
        writeValueConstructors(pw, theTable);
        writeValueCoreFunctions(pw, theTable);
        writeValueClone(pw, theTable);
        writeValuePrimaryKey(pw, theTable);
        writeValueHashAndString(pw, theTable);
        writeValueEquals(pw, theTable);
        writeValueIdentical(pw, theTable);
        writeValueModified(pw, theTable);
        writeValueGetsSets(pw, theTable);
        
        pw.println("}");
    }
    
    public void exportValues(String baseDirectory)
    throws Exception {
        for(var theComponent: myComponents) {
            var componentDirectory = createValueDirectoryForComponent(theComponent, baseDirectory);
            
            for(var theTable: theComponent.getTables()) {
                if(theTable.hasEID()) {
                    var classFileName = theTable.getValueClass() + ".java";
                    var f = new File(componentDirectory + File.separatorChar + classFileName);
                    
                    try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
                        var pw = new PrintWriter(bw);
                        
                        writeCopyright(pw);
                        writeVersion(pw, classFileName);
                        writePackage(pw, theComponent.getValuePackage());
                        writeValueImports(pw, theComponent, theTable);
                        writeValueClass(pw, theTable);
                    }
                }
            }
        }
    }
    
    public void exportEntities(String baseDirectory)
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
        pw.println("    public void store(Session session)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        getBaseFactoryInstance().store(session, this);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public void remove(Session session)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        getBaseFactoryInstance().remove(session, this);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public void remove()");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        getBaseFactoryInstance().remove(ThreadSession.currentSession(), this);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + valueClass + " get" + valueClass + "() {");
        pw.println("        return _value;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public void set" + valueClass + "(" + valueClass + " value)");
        pw.println("            throws PersistenceReadOnlyException {");
        pw.println("        checkReadWrite();");
        pw.println("        _value = value;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public " + theTable.getPKClass() + " getPrimaryKey() {");
        pw.println("        return _pk;");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeEntityGetsSets(PrintWriter pw, Table theTable) {
        theTable.getColumns().forEach((column) -> {
            var type = column.getType();
            
            if (type != ColumnType.columnEID) {
                var getFunctionName = column.getGetFunctionName();
                var setFunctionName = column.getSetFunctionName();
                var variableName = column.getVariableName();
                var javaType = column.getTypeAsJavaType();
                String fkEntityClass = null;
                
                pw.println("    public " + javaType + " " + getFunctionName + "() {");
                pw.println("        return _value." + getFunctionName + "();");
                pw.println("    }");
                pw.println("    ");
                
                if(type == ColumnType.columnForeignKey) {
                    var getEntityFunctionName = column.getGetEntityFunctionName();
                    var fkFactoryClass = column.getFKFactoryClass();
                    
                    fkEntityClass = column.getFKEntityClass();
                    
                    pw.println("    public " + fkEntityClass + " " + getEntityFunctionName + "(Session session, EntityPermission entityPermission) {");
                    if(column.getNullAllowed()) {
                        pw.println("        " + javaType + " pk = " + getFunctionName + "();");
                        pw.println("        " + fkEntityClass + " entity = pk == null? null: " + fkFactoryClass + ".getInstance().getEntityFromPK(session, entityPermission, pk);");
                        pw.println("        ");
                        pw.println("        return entity;");
                    } else {
                        pw.println("        return " + fkFactoryClass + ".getInstance().getEntityFromPK(session, entityPermission, " + getFunctionName + "());");
                    }
                    pw.println("    }");
                    pw.println("    ");
                    pw.println("    public " + fkEntityClass + " " + getEntityFunctionName + "(EntityPermission entityPermission) {");
                    pw.println("        return " + getEntityFunctionName + "(ThreadSession.currentSession(), entityPermission);");
                    pw.println("    }");
                    pw.println("    ");
                    pw.println("    public " + fkEntityClass + " " + getEntityFunctionName + "(Session session) {");
                    pw.println("        return " + getEntityFunctionName + "(session, EntityPermission.READ_ONLY);");
                    pw.println("    }");
                    pw.println("    ");
                    pw.println("    public " + fkEntityClass + " " + getEntityFunctionName + "() {");
                    pw.println("        return " + getEntityFunctionName + "(ThreadSession.currentSession(), EntityPermission.READ_ONLY);");
                    pw.println("    }");
                    pw.println("    ");
                    pw.println("    public " + fkEntityClass + " " + getEntityFunctionName + "ForUpdate(Session session) {");
                    pw.println("        return " + getEntityFunctionName + "(session, EntityPermission.READ_WRITE);");
                    pw.println("    }");
                    pw.println("    ");
                    pw.println("    public " + fkEntityClass + " " + getEntityFunctionName + "ForUpdate() {");
                    pw.println("        return " + getEntityFunctionName + "(ThreadSession.currentSession(), EntityPermission.READ_WRITE);");
                    pw.println("    }");
                    pw.println("    ");
                }
                
                pw.println("    public void " + setFunctionName + "(" + javaType + " " + variableName + ")");
                pw.println("            throws PersistenceNotNullException, PersistenceReadOnlyException {");
                pw.println("        checkReadWrite();");
                pw.println("        _value." + setFunctionName + "(" + variableName + ");");
                pw.println("    }");
                pw.println("    ");
                
                if(type == ColumnType.columnForeignKey) {
                    pw.println("    public void " + column.getSetEntityFunctionName() + "(" + fkEntityClass + " entity) {");
                    pw.println("        " + setFunctionName + "(entity == null? null: entity.getPrimaryKey());");
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
    
    public void writeFactoryFKPKImports(PrintWriter pw, Table theTable)
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
    
    public void writeFactoryFKEntityImports(PrintWriter pw, Table theTable)
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
    
    public void writeFactoryFKImports(PrintWriter pw, Table theTable)
    throws Exception {
        writeFactoryFKPKImports(pw, theTable);
        writeFactoryFKEntityImports(pw, theTable);
    }
    
    public void writeFactoryImports(PrintWriter pw, Table theTable)
    throws Exception {
        writeFactoryFKImports(pw, theTable);
        pw.println("import " + theTable.getConstantsImport() + ";");
        pw.println("import " + theTable.getPKImport() + ";");
        pw.println("import " + theTable.getValueImport() + ";");
        pw.println("import " + theTable.getEntityImport() + ";");
        pw.println("import com.echothree.util.common.exception.PersistenceDatabaseException;");
        pw.println("import com.echothree.util.common.exception.PersistenceDatabaseUpdateException;");
        pw.println("import com.echothree.util.common.exception.PersistenceNotNullException;");
        pw.println("import com.echothree.util.server.persistence.BaseFactory;");
        pw.println("import com.echothree.util.server.persistence.EntityIdGenerator;");
        pw.println("import com.echothree.util.server.persistence.EntityPermission;");
        pw.println("import com.echothree.util.server.persistence.PersistenceDebugFlags;");
        pw.println("import com.echothree.util.server.persistence.Session;");

        if(theTable.hasBlob()) {
            pw.println("import com.echothree.util.common.persistence.type.ByteArray;");
            pw.println("import java.sql.Blob;");
        }
        
        if(theTable.hasClob()) {
            pw.println("import java.sql.Clob;");
        }
        
        pw.println("import java.sql.PreparedStatement;");
        pw.println("import java.sql.ResultSet;");
        pw.println("import java.sql.SQLException;");
        pw.println("import java.sql.Types;");
        pw.println("import java.io.ByteArrayInputStream;");
        pw.println("import java.io.StringReader;");
        pw.println("import java.util.ArrayList;");
        pw.println("import java.util.Collection;");
        pw.println("import java.util.HashSet;");
        pw.println("import java.util.List;");
        pw.println("import java.util.Map;");
        pw.println("import java.util.Set;");
        pw.println("import javax.enterprise.context.ApplicationScoped;");
        pw.println("import javax.enterprise.inject.spi.CDI;");
        pw.println("import javax.inject.Inject;");
        pw.println("import org.apache.commons.logging.Log;");
        pw.println("import org.apache.commons.logging.LogFactory;");
        pw.println("");
    }

    public void writeFactoryInjections(PrintWriter pw, Table theTable) {
        pw.println("    @Inject");
        pw.println("    Session session;");
        pw.println("    ");
    }

    public void writeFactoryInstanceVariables(PrintWriter pw, Table theTable)
    throws Exception {
        var columns = theTable.getColumns();
        var dbTableName = theTable.getDbTableName();
        var chunkSize = theTable.getChunkSize();
        var factoryClass = theTable.getFactoryClass();
        String pkColumn = null;
        var allColumnsExceptPk = "";
        String allColumns;
        var questionMarks = "";
        var insertAllColumns = "";
        var updateColumns = "";
        
        for(var column: columns) {
            var type = column.getType();

            if(type == ColumnType.columnEID) {
                pkColumn = column.getDbColumnName();
            } else {
                if(!allColumnsExceptPk.isEmpty())
                    allColumnsExceptPk += ", ";
                if(type == ColumnType.columnUUID)
                    allColumnsExceptPk += "BIN_TO_UUID(" + column.getDbColumnName() + ") AS " + column.getDbColumnName();
                else
                    allColumnsExceptPk += column.getDbColumnName();

                if(!insertAllColumns.isEmpty())
                    insertAllColumns += ", ";
                insertAllColumns += column.getDbColumnName();

                if(!updateColumns.isEmpty())
                    updateColumns += ", ";
                updateColumns += column.getDbColumnName();
                if(type == ColumnType.columnUUID)
                    updateColumns += " = UUID_TO_BIN(?)";
                else
                    updateColumns += " = ?";
            }
            
            if(!questionMarks.isEmpty())
                questionMarks += ", ";
            if(type == ColumnType.columnUUID)
                questionMarks += "UUID_TO_BIN(?)";
            else
                questionMarks += "?";
        }

        // These two Strings need to have the PK added to the beginning of them.
        allColumns = !allColumnsExceptPk.isEmpty() ? pkColumn + ", " + allColumnsExceptPk: pkColumn;
        insertAllColumns = !insertAllColumns.isEmpty() ? pkColumn + ", " + insertAllColumns: pkColumn;

        pw.println("    //final private static Log log = LogFactory.getLog(" + factoryClass + ".class);");
        pw.println("    ");
        pw.println("    final private static String SQL_SELECT_READ_ONLY = \"SELECT " + allColumns + " FROM " + dbTableName + " WHERE " + pkColumn + " = ?\";");
        pw.println("    final private static String SQL_SELECT_READ_WRITE = \"SELECT " + allColumns + " FROM " + dbTableName + " WHERE " + pkColumn + " = ? FOR UPDATE\";");
        pw.println("    final private static String SQL_INSERT = \"INSERT INTO " + dbTableName + " (" + insertAllColumns + ") VALUES (" + questionMarks + ")\";");
        if(!updateColumns.isEmpty())
            pw.println("    final private static String SQL_UPDATE = \"UPDATE " + dbTableName + " SET " + updateColumns + " WHERE " + pkColumn + " = ?\";");
        pw.println("    final private static String SQL_DELETE = \"DELETE FROM " + dbTableName + " WHERE " + pkColumn + " = ?\";");
        pw.println("    final private static String SQL_VALID = \"SELECT COUNT(*) FROM " + dbTableName + " WHERE " + pkColumn + " = ?\";");
        pw.println("    ");
        pw.println("    final private static String PK_COLUMN = \"" + pkColumn + "\";");
        pw.println("    final private static String ALL_COLUMNS = \"" + allColumns + "\";");
        pw.println("    final public static String TABLE_NAME = \"" + dbTableName + "\";");
        pw.println("    ");
        
        for(var column:columns) {
            pw.println("    final public static String " + column.getDbColumnName().toUpperCase(Locale.getDefault()) + " = \"" + column.getDbColumnName() + "\";");
        }
        
        pw.println("    ");
        pw.println("    final private static EntityIdGenerator entityIdGenerator = new EntityIdGenerator(" + theTable.getConstantsClass() + ".COMPONENT_VENDOR_NAME, "
                + theTable.getConstantsClass() + ".ENTITY_TYPE_NAME" + (chunkSize == null? "": ", " + chunkSize) + ");");
        pw.println("    ");
    }
    
    public void writeFactoryConstructors(PrintWriter pw, Table theTable) {
        var factoryClass = theTable.getFactoryClass();
        
        pw.println("    /** Creates a new instance of " + factoryClass + " */");
        pw.println("    protected " + factoryClass + "() {");
        pw.println("        super();");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public static " + factoryClass + " getInstance() {");
        pw.println("        return CDI.current().select(" + factoryClass + ".class).get();");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeFactoryCoreFunctions(PrintWriter pw, Table theTable) {
        pw.println("    @Override");
        pw.println("    public String getPKColumn() {");
        pw.println("        return PK_COLUMN;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public String getAllColumns() {");
        pw.println("        return ALL_COLUMNS;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public String getTableName() {");
        pw.println("        return TABLE_NAME;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public String getComponentVendorName() {");
        pw.println("        return " + theTable.getConstantsClass() + ".COMPONENT_VENDOR_NAME;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public String getEntityTypeName() {");
        pw.println("        return " + theTable.getConstantsClass() + ".ENTITY_TYPE_NAME;");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeFactoryPrepareFunction(PrintWriter pw, Table theTable) {
        var factoryClass = theTable.getFactoryClass();
        
        pw.println("    public PreparedStatement prepareStatement(String query) {");
        pw.println("        return session.prepareStatement(" + factoryClass + ".class, query);");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeFactoryPkFunctions(PrintWriter pw, Table theTable)
    throws Exception {
        var pkClass = theTable.getPKClass();
        var dbColumnName = theTable.getEID().getDbColumnName();
        
        pw.println("    public " + pkClass + " getNextPK() {");
        pw.println("        return new " + pkClass + "(entityIdGenerator.getNextEntityId());");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public Set<" + pkClass + "> getPKsFromResultSetAsSet(ResultSet rs)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        Set<" + pkClass + "> _result = new HashSet<>();");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            while(rs.next()) {");
        pw.println("                _result.add(getPKFromResultSet(rs));");
        pw.println("            }");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _result;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + pkClass + "> getPKsFromResultSetAsList(ResultSet rs)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        java.util.List<" + pkClass + "> _result = new ArrayList<>();");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            while(rs.next()) {");
        pw.println("                _result.add(getPKFromResultSet(rs));");
        pw.println("            }");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _result;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + pkClass + " getPKFromResultSet(ResultSet rs)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        " + pkClass + " _result;");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            long " + dbColumnName + " = rs.getLong(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
        pw.println("            Long _entityId = rs.wasNull() ? null : " + dbColumnName + ";");
        pw.println("            ");
        pw.println("            _result = new " + pkClass + "(_entityId);");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _result;");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeFactoryRemoveFunctions(PrintWriter pw, Table theTable) {
        var pkClass = theTable.getPKClass();
        
        pw.println("    @Override");
        pw.println("    public void remove(Session session, " + theTable.getEntityClass() + " entity)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        remove(session, entity.getPrimaryKey());");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public void remove(Session session, " + theTable.getPKClass() + " pk)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        PreparedStatement _ps = session.prepareStatement(SQL_DELETE);");
        pw.println("        long _entityId = pk.getEntityId();");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            _ps.setLong(1, _entityId);");
        pw.println("            ");
        pw.println("            if(PersistenceDebugFlags.CheckEntityDeleteRowCount) {");
        pw.println("                int _count = _ps.executeUpdate();");
        pw.println("                ");
        pw.println("                if(_count != 1) {");
        pw.println("                    throw new PersistenceDatabaseUpdateException(\"remove failed, _count = \" + _count);");
        pw.println("                }");
        pw.println("            } else {");
        pw.println("                 _ps.executeUpdate();");
        pw.println("            }");
        pw.println("            ");
        pw.println("            session.getValueCache().remove(pk);");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        }");
        pw.println("        ");
        pw.println("        session.removed(pk, false);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public void remove(Session session, Collection<" + pkClass + "> pks)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        if(pks.size() > 0) {");
        pw.println("            PreparedStatement _ps = session.prepareStatement(SQL_DELETE);");
        pw.println("            int _modifiedEntities = 0;");
        pw.println("            ");
        pw.println("            try {");
        pw.println("                for(" + pkClass + " pk : pks) {");
        pw.println("                    long _entityId = pk.getEntityId();");
        pw.println("                    ");
        pw.println("                    _ps.setLong(1, _entityId);");
        pw.println("                    ");
        pw.println("                    _ps.addBatch();");
        pw.println("                    _modifiedEntities++;");
        pw.println("                }");
        pw.println("                ");
        pw.println("                if(_modifiedEntities != 0) {");
        pw.println("                    if(PersistenceDebugFlags.CheckEntityDeleteRowCount) {");
        pw.println("                        int[] _counts = _ps.executeBatch();");
        pw.println("                        ");
        pw.println("                        for(int _countOffset = 0 ; _countOffset < _modifiedEntities  ; _countOffset++) {");
        pw.println("                            if(_counts[_countOffset] != 1 && _counts[_countOffset] != PreparedStatement.SUCCESS_NO_INFO) {");
        pw.println("                                throw new PersistenceDatabaseUpdateException(\"batch remove failed, _counts[\" + _countOffset + \"] = \" + _counts[_countOffset]);");
        pw.println("                            }");
        pw.println("                        }");
        pw.println("                    } else {");
        pw.println("                        _ps.executeBatch();");
        pw.println("                    }");
        pw.println("                    ");
        pw.println("                    _ps.clearBatch();");
        pw.println("                    ");
        pw.println("                    pks.forEach((pk) -> {");
        pw.println("                        session.getValueCache().remove(pk);");
        pw.println("                    });");
        pw.println("                }");
        pw.println("            } catch (SQLException se) {");
        pw.println("                throw new PersistenceDatabaseException(se);");
        pw.println("            }");
        pw.println("            ");
        pw.println("            pks.forEach((pk) -> {");
        pw.println("                session.removed(pk, true);");
        pw.println("            });");
        pw.println("        }");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public void remove(Collection<" + pkClass + "> pks)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        remove(session, pks);");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeFactoryStoreFunctions(PrintWriter pw, Table theTable)
    throws Exception {
        var entityClass = theTable.getEntityClass();
        var valueClass = theTable.getValueClass();
        var columns = theTable.getColumns();
        
        pw.println("    private boolean bindForStore(PreparedStatement _ps, " + valueClass + " _value)");
        pw.println("            throws SQLException {");
        pw.println("        boolean _hasBeenModified = _value.hasBeenModified();");
        pw.println("        ");
        pw.println("        if(_hasBeenModified) {");
        var parameterCount = 1;
        for(var column: columns) {
            var type = column.getType();

            if(type != ColumnType.columnEID) {
                var dbColumnName = column.getDbColumnName();

                pw.println("            " + column.getTypeAsJavaType() + " " + dbColumnName + " = _value." + column.getGetFunctionName() + "();");
                pw.println("            if(" + dbColumnName + " == null)");
                pw.println("                _ps.setNull(" + parameterCount + ", Types." + column.getTypeAsJavaSqlType() + ");");
                pw.println("            else");

                switch(type) {
                    case ColumnType.columnInteger ->
                            pw.println("                _ps.setInt(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnType.columnLong ->
                            pw.println("                _ps.setLong(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnType.columnString ->
                            pw.println("                _ps.setString(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnType.columnBoolean ->
                            pw.println("                _ps.setInt(" + parameterCount + ", " + dbColumnName + "? 1: 0);");
                    case ColumnType.columnDate ->
                            pw.println("                _ps.setInt(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnType.columnTime ->
                            pw.println("                _ps.setLong(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnType.columnForeignKey ->
                            pw.println("                _ps.setLong(" + parameterCount + ", " + dbColumnName + ".getEntityId());");
                    case ColumnType.columnBLOB ->
                            pw.println("                _ps.setBinaryStream(" + parameterCount + ", new ByteArrayInputStream(" + dbColumnName + ".byteArrayValue()), " + dbColumnName + ".length());");
                    case ColumnType.columnCLOB ->
                            pw.println("                _ps.setCharacterStream(" + parameterCount + ", new StringReader(" + dbColumnName + "), " + dbColumnName + ".length());");
                    case ColumnType.columnUUID ->
                            pw.println("                _ps.setString(" + parameterCount + ", " + dbColumnName + ");");
                    default -> pw.println("<error>");
                }

                pw.println("            ");

                parameterCount++;
            }
        }

        pw.println("            _ps.setLong(" + parameterCount + ", _value.getPrimaryKey().getEntityId());");
        pw.println("            ");
        pw.println("            _value.clearHasBeenModified();");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _hasBeenModified;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public void store(Session session, " + entityClass + " entity)");
        pw.println("            throws PersistenceDatabaseException {");
        if(columns.size() > 1) {
            pw.println("        PreparedStatement _ps = session.prepareStatement(SQL_UPDATE);");
            pw.println("        ");
            pw.println("        try {");
            pw.println("            " + valueClass + " _value = entity.get" + valueClass + "();");
            pw.println("            ");
            pw.println("            if(bindForStore(_ps, _value)) {");
            pw.println("                if(PersistenceDebugFlags.CheckEntityUpdateRowCount) {");
            pw.println("                    int _count = _ps.executeUpdate();");
            pw.println("                    ");
            pw.println("                    if(_count != 1) {");
            pw.println("                        throw new PersistenceDatabaseUpdateException(\"update failed, _count = \" + _count);");
            pw.println("                    }");
            pw.println("                } else {");
            pw.println("                     _ps.executeUpdate();");
            pw.println("                }");
            pw.println("                ");
            pw.println("                session.getValueCache().put(_value);");
            pw.println("            }");
            pw.println("        } catch (SQLException se) {");
            pw.println("            throw new PersistenceDatabaseException(se);");
            pw.println("        }");
        } else {
            pw.println("        throw new PersistenceDatabaseException(\"nothing to store\");");
        }
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public void store(Session session, Collection<" + entityClass + "> entities)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        if(entities.size() > 0) {");
        if(columns.size() > 1) {
            pw.println("            PreparedStatement _ps = session.prepareStatement(SQL_UPDATE);");
            pw.println("            int _modifiedEntities = 0;");
            pw.println("            ");
            pw.println("            try {");
            pw.println("                for(" + entityClass + " entity : entities) {");
            pw.println("                    if(bindForStore(_ps, entity.get" + valueClass + "())) {");
            pw.println("                        _ps.addBatch();");
            pw.println("                        _modifiedEntities++;");
            pw.println("                    }");
            pw.println("                }");
            pw.println("                ");
            pw.println("                if(_modifiedEntities != 0) {");
            pw.println("                    if(PersistenceDebugFlags.CheckEntityUpdateRowCount) {");
            pw.println("                        int[] _counts = _ps.executeBatch();");
            pw.println("                        ");
            pw.println("                        for(int _countOffset = 0 ; _countOffset < _modifiedEntities  ; _countOffset++) {");
            pw.println("                            if(_counts[_countOffset] != 1 && _counts[_countOffset] != PreparedStatement.SUCCESS_NO_INFO) {");
            pw.println("                                throw new PersistenceDatabaseUpdateException(\"batch update failed, _counts[\" + _countOffset + \"] = \" + _counts[_countOffset]);");
            pw.println("                            }");
            pw.println("                        }");
            pw.println("                    } else {");
            pw.println("                         _ps.executeBatch();");
            pw.println("                    }");
            pw.println("                    ");
            pw.println("                    _ps.clearBatch();");
            pw.println("                    ");
            pw.println("                    entities.forEach((entity) -> {");
            pw.println("                        session.getValueCache().put(entity.get" + valueClass + "());");
            pw.println("                    });");
            pw.println("                }");
            pw.println("            } catch (SQLException se) {");
            pw.println("                throw new PersistenceDatabaseException(se);");
            pw.println("            }");
            pw.println("        }");
            pw.println("    }");
        } else {
            pw.println("        throw new PersistenceDatabaseException(\"nothing to store\");");
        }
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public void store(Collection<" + entityClass + "> entities)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        store(session, entities);");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeFactoryCreateFunctions(PrintWriter pw, Table theTable)
    throws Exception {
        var columns = theTable.getColumns();
        var entityClass = theTable.getEntityClass();
        var pkClass = theTable.getPKClass();
        var valueClass = theTable.getValueClass();
        var createEntityParameters = "";
        var createPkParameters = "";
        var pkParameters = "";
        var valueParameters = "";
        var nullParameters = "";
        var isFirst = true;
        
        for(var column: columns) {
            var type = column.getType();

            if(type != ColumnType.columnEID) {
                if(!isFirst) {
                    createEntityParameters += ", ";
                    createPkParameters += ", ";
                } else {
                    isFirst = false;
                }
                
                pkParameters += ", ";
                valueParameters += ", ";
                nullParameters += ", ";
                
                if(type == ColumnType.columnForeignKey) {
                    createEntityParameters += column.getFKEntityClass() + " " + column.getEntityVariableName();
                    pkParameters += column.getEntityVariableName() + " == null ? null : " + column.getEntityVariableName() + ".getPrimaryKey()";
                    createPkParameters += column.getFKPKClass() + " " + column.getVariableName();
                    nullParameters += "(" + column.getFKPKClass() + ")null";
                } else {
                    createEntityParameters += column.getTypeAsJavaType() + " " + column.getVariableName();
                    pkParameters += column.getVariableName();
                    createPkParameters += column.getTypeAsJavaType() + " " + column.getVariableName();
                    nullParameters += "null";
                }
                
                valueParameters += column.getVariableName();
            }
        }
        
        if(theTable.hasNotNullColumn() && (theTable.getColumns().size() > 1)) {
            pw.println("    public " + entityClass + " create(Session session)");
            pw.println("            throws PersistenceDatabaseException, PersistenceNotNullException {");
            pw.println("        return create(session" + nullParameters + ");");
            pw.println("    }");
            pw.println("    ");
            pw.println("    public " + entityClass + " create()");
            pw.println("            throws PersistenceDatabaseException, PersistenceNotNullException {");
            pw.println("        return create(session" + nullParameters + ");");
            pw.println("    }");
            pw.println("    ");
        }
        
        if(theTable.hasForeignKey()) {
            pw.println("    public " + entityClass + " create(Session session, " + createEntityParameters + ")");
            pw.println("            throws PersistenceDatabaseException, PersistenceNotNullException {");
            pw.println("        return create(session" + pkParameters + ");");
            pw.println("    }");
            pw.println("    ");
            pw.println("    public " + entityClass + " create(" + createEntityParameters + ")");
            pw.println("            throws PersistenceDatabaseException, PersistenceNotNullException {");
            pw.println("        return create(session" + pkParameters + ");");
            pw.println("    }");
            pw.println("    ");
        }
        pw.println("    private void bindForCreate(PreparedStatement _ps, " + valueClass + " _value)");
        pw.println("            throws SQLException {");
        pw.println("        _ps.setLong(1, _value.getEntityId());");
        pw.println("        ");
        var parameterCount = 2;
        for(var column: columns) {
            var type = column.getType();

            if(type != ColumnType.columnEID) {
                var dbColumnName = column.getDbColumnName();
                
                pw.println("        " + column.getTypeAsJavaType() + " " + dbColumnName + " = _value." + column.getGetFunctionName() + "();");
                pw.println("        if(" + dbColumnName + " == null)");
                pw.println("            _ps.setNull(" + parameterCount + ", Types." + column.getTypeAsJavaSqlType() + ");");
                pw.println("        else");

                switch(type) {
                    case ColumnType.columnInteger ->
                            pw.println("            _ps.setInt(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnType.columnLong ->
                            pw.println("            _ps.setLong(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnType.columnString ->
                            pw.println("            _ps.setString(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnType.columnBoolean ->
                            pw.println("            _ps.setInt(" + parameterCount + ", " + dbColumnName + "? 1: 0);");
                    case ColumnType.columnDate ->
                            pw.println("            _ps.setInt(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnType.columnTime ->
                            pw.println("            _ps.setLong(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnType.columnForeignKey ->
                            pw.println("            _ps.setLong(" + parameterCount + ", " + dbColumnName + ".getEntityId());");
                    case ColumnType.columnBLOB ->
                            pw.println("            _ps.setBinaryStream(" + parameterCount + ", new ByteArrayInputStream(" + dbColumnName + ".byteArrayValue()), " + dbColumnName + ".length());");
                    case ColumnType.columnCLOB ->
                            pw.println("            _ps.setCharacterStream(" + parameterCount + ", new StringReader(" + dbColumnName + "), " + dbColumnName + ".length());");
                    case ColumnType.columnUUID ->
                            pw.println("            _ps.setString(" + parameterCount + ", " + dbColumnName + ");");
                    default -> pw.println("<error>");
                }
                
                pw.println("            ");
                
                parameterCount++;
            }
        }
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " create(Session session, " + createPkParameters + ")");
        pw.println("            throws PersistenceDatabaseException, PersistenceNotNullException {");
        pw.println("        " + pkClass + " _pk = getNextPK();");
        pw.println("        " + valueClass + " _value = new " + valueClass + "(_pk" + valueParameters + ");");
        pw.println("        ");
        pw.println("        PreparedStatement _ps = session.prepareStatement(SQL_INSERT);");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            bindForCreate(_ps, _value);");
        pw.println("            ");
        pw.println("            if(PersistenceDebugFlags.CheckEntityInsertRowCount) {");
        pw.println("                int _count = _ps.executeUpdate();");
        pw.println("                ");
        pw.println("                if(_count != 1) {");
        pw.println("                    throw new PersistenceDatabaseUpdateException(\"insert failed, _count = \" + _count);");
        pw.println("                }");
        pw.println("            } else {");
        pw.println("                 _ps.executeUpdate();");
        pw.println("            }");
        pw.println("            ");
        pw.println("            session.getValueCache().put(_value);");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        }");
        pw.println("        ");
        pw.println("        " + entityClass + " _entity = new " + entityClass + "(_value, EntityPermission.READ_ONLY);");
        pw.println("        session.putReadOnlyEntity(_pk, _entity);");
        pw.println("        ");
        pw.println("        return _entity;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " create(" + createPkParameters + ")");
        pw.println("            throws PersistenceDatabaseException, PersistenceNotNullException {");
        pw.println("        return create(session" + valueParameters + ");");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public void create(Session session, Collection<" + valueClass + "> _values)");
        pw.println("            throws PersistenceDatabaseException, PersistenceNotNullException {");
        pw.println("        int _size = _values.size();");
        pw.println("        ");
        pw.println("        if(_size > 0) {");
        pw.println("            PreparedStatement _ps = session.prepareStatement(SQL_INSERT);");
        pw.println("            List<" + valueClass + "> _cacheValues = new ArrayList<>(_size);");
        pw.println("            ");
        pw.println("            try {");
        pw.println("                for(" + valueClass + " _value : _values) {");
        pw.println("                    _value.setEntityId(entityIdGenerator.getNextEntityId());");
        pw.println("                    bindForCreate(_ps, _value);");
        pw.println("                    ");
        pw.println("                    _ps.addBatch();");
        pw.println("                    ");
        pw.println("                    _cacheValues.add(_value);");
        pw.println("                }");
        pw.println("                ");
        pw.println("                if(PersistenceDebugFlags.CheckEntityInsertRowCount) {");
        pw.println("                    int[] _counts = _ps.executeBatch();");
        pw.println("                    ");
        pw.println("                    for(int _countOffset = 0 ; _countOffset < _size ; _countOffset++) {");
        pw.println("                        if(_counts[_countOffset] != 1 && _counts[_countOffset] != PreparedStatement.SUCCESS_NO_INFO) {");
        pw.println("                            throw new PersistenceDatabaseUpdateException(\"batch insert failed, _counts[\" + _countOffset + \"] = \" + _counts[_countOffset]);");
        pw.println("                        }");
        pw.println("                    }");
        pw.println("                } else {");
        pw.println("                     _ps.executeBatch();");
        pw.println("                }");
        pw.println("                ");
        pw.println("                _ps.clearBatch();");
        pw.println("            } catch (SQLException se) {");
        pw.println("                throw new PersistenceDatabaseException(se);");
        pw.println("            }");
        pw.println("            ");
        pw.println("            _cacheValues.forEach((_cacheValue) -> {");
        pw.println("                " + entityClass + " _cacheEntity = new " + entityClass + "(_cacheValue, EntityPermission.READ_ONLY);");
        pw.println("                ");
        pw.println("                session.putReadOnlyEntity(_cacheValue.getPrimaryKey(), _cacheEntity);");
        pw.println("            });");
        pw.println("        }");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public void create(Collection<" + valueClass + "> _values)");
        pw.println("            throws PersistenceDatabaseException, PersistenceNotNullException {");
        pw.println("        create(session, _values);");
        pw.println("    }");
        pw.println("    ");
      }
    
    public void writeFactoryValueFunctions(PrintWriter pw, Table theTable)
    throws Exception {
        var entityClass = theTable.getEntityClass();
        var valueClass = theTable.getValueClass();
        var pkClass = theTable.getPKClass();
        var eidColumn = theTable.getEID();
        var eidDbColumnName = eidColumn.getDbColumnName();
        
        pw.println("    public java.util.List<" + valueClass + "> getValuesFromPKs(Session session, Collection<" + pkClass + "> pks)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        java.util.List<" + valueClass + "> _values = new ArrayList<>(pks.size());");
        pw.println("        ");
        pw.println("        for(" + pkClass + " _pk: pks) {");
        pw.println("            _values.add(getValueFromPK(session, _pk));");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _values;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + valueClass + " getValueFromPK(Session session, " + pkClass + " pk)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        " + valueClass + " _value;");
        pw.println("        ");
        pw.println("        // See if we already have the entity in the session cache");
        pw.println("        " + entityClass + " _entity = (" + entityClass + ")session.getEntity(pk);");
        pw.println("        if(_entity == null)");
        pw.println("            _value = getEntityFromPK(session, EntityPermission.READ_ONLY, pk).get" + valueClass + "();");
        pw.println("        else");
        pw.println("            _value = _entity.get" + valueClass + "();");
        pw.println("        ");
        pw.println("        return _value;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + valueClass + "> getValuesFromResultSet(Session session, ResultSet rs)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        java.util.List<" + valueClass + "> _result = new ArrayList<>();");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            while(rs.next()) {");
        pw.println("                _result.add(getValueFromResultSet(session, rs));");
        pw.println("            }");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _result;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + valueClass + " getValueFromResultSet(Session session, ResultSet rs)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        " + valueClass + " _value;");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            Long " + eidDbColumnName + " = rs.getLong(" + eidDbColumnName.toUpperCase(Locale.getDefault()) + ");");
        pw.println("            " + pkClass + " _pk = new " + pkClass + "(" + eidDbColumnName + ");");
        pw.println("            ");
        pw.println("            // See if we already have the entity in the session cache");
        pw.println("            " + entityClass + " _entity = (" + entityClass + ")session.getEntity(_pk);");
        pw.println("            ");
        pw.println("            if(_entity == null) {");

        var valueParameters = "";
        for(var column: theTable.getColumns()) {
            var type = column.getType();

            if(type != ColumnType.columnEID) {
                var dbColumnName = column.getDbColumnName();

                switch(type) {
                    case ColumnType.columnInteger -> {
                        pw.println("                Integer " + dbColumnName + " = rs.getInt(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnType.columnLong -> {
                        pw.println("                Long " + dbColumnName + " = rs.getLong(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnType.columnString -> {
                        pw.println("                String " + dbColumnName + " = rs.getString(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnType.columnBoolean -> {
                        pw.println("                Boolean " + dbColumnName + " = rs.getInt(" + dbColumnName.toUpperCase(Locale.getDefault()) + ") == 1;");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnType.columnDate -> {
                        pw.println("                Integer " + dbColumnName + " = rs.getInt(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnType.columnTime -> {
                        pw.println("                Long " + dbColumnName + " = rs.getLong(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnType.columnForeignKey -> {
                        pw.println("                Long " + dbColumnName + " = rs.getLong(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", new " + column.getFKPKClass() + "(" + dbColumnName + ")";
                    }
                    case ColumnType.columnBLOB -> {
                        pw.println("                Blob " + dbColumnName + " = rs.getBlob(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", new ByteArray(" + dbColumnName + ".getBytes(1L, (int)" + dbColumnName + ".length()))";
                    }
                    case ColumnType.columnCLOB -> {
                        pw.println("                Clob " + dbColumnName + " = rs.getClob(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName + " == null? null: " + dbColumnName + ".getSubString(1L, (int)" + dbColumnName + ".length())";
                    }
                    case ColumnType.columnUUID -> {
                        pw.println("                String " + dbColumnName + " = rs.getString(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    default -> pw.println("<error>");
                }
                
                pw.println("                if(rs.wasNull())");
                pw.println("                    " + dbColumnName + " = null;");
                pw.println("                ");
            }
        }
        
        pw.println("                _value = new " + valueClass + "(_pk" + valueParameters + ");");
        pw.println("            } else");
        pw.println("                _value = _entity.get" + valueClass + "();");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _value;");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeFactoryEntityFunctions(PrintWriter pw, Table theTable)
    throws Exception {
        var factoryClass = theTable.getFactoryClass();
        var entityClass = theTable.getEntityClass();
        var valueClass = theTable.getValueClass();
        var pkClass = theTable.getPKClass();
        var eidColumn = theTable.getEID();
        var eidDbColumnName = eidColumn.getDbColumnName();
        
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromPKs(EntityPermission entityPermission, Collection<" + pkClass + "> pks)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        return getEntitiesFromPKs(session, entityPermission, pks);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromPKs(Session session, EntityPermission entityPermission, Collection<" + pkClass + "> pks)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        java.util.List<" + entityClass + "> _entities = new ArrayList<>(pks.size());");
        pw.println("        ");
        pw.println("        for(" + pkClass + " _pk: pks) {");
        pw.println("            _entities.add(getEntityFromPK(session, entityPermission, _pk));");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _entities;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromValue(EntityPermission entityPermission, " + valueClass + " value) {");
        pw.println("        return getEntityFromPK(session, entityPermission, value.getPrimaryKey());");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromValue(Session session, EntityPermission entityPermission, " + valueClass + " value) {");
        pw.println("        return getEntityFromPK(session, entityPermission, value.getPrimaryKey());");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromPK(EntityPermission entityPermission, " + pkClass + " pk)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        return getEntityFromPK(session, entityPermission, pk);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromCache(Session session, " + pkClass + " pk) {");
        pw.println("        " + valueClass + " _value = (" + valueClass + ")session.getValueCache().get(pk);");
        pw.println("    ");
        pw.println("        return _value == null ? null : new " + entityClass + "(_value, EntityPermission.READ_ONLY);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromPK(Session session, EntityPermission entityPermission, " + pkClass + " pk)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        " + entityClass + " _entity;");
        pw.println("        ");
        pw.println("        // See if we already have the entity in the session cache");
        pw.println("        _entity = (" + entityClass + ")session.getEntity(pk);");
        pw.println("        if(_entity != null) {");
        pw.println("            // If the requested permission is READ_WRITE, and the cached permission is");
        pw.println("            // READ_ONLY, then pretend that the cached object wasn't found, and create");
        pw.println("            // a new entity that is READ_WRITE.");
        pw.println("            if(entityPermission.equals(EntityPermission.READ_WRITE)) {");
        pw.println("                if(_entity.getEntityPermission().equals(EntityPermission.READ_ONLY))");
        pw.println("                    _entity = null;");
        pw.println("            }");
        pw.println("        }");
        pw.println("        ");
        pw.println("        if(_entity == null && entityPermission.equals(EntityPermission.READ_ONLY)) {");
        pw.println("            _entity = getEntityFromCache(session, pk);");
        pw.println("        }");
        pw.println("        ");
        pw.println("        if(_entity == null) {");
        pw.println("            PreparedStatement _ps = session.prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? SQL_SELECT_READ_ONLY: SQL_SELECT_READ_WRITE);");
        pw.println("            long _entityId = pk.getEntityId();");
        pw.println("            ResultSet _rs = null;");
        pw.println("            ");
        pw.println("            try {");
        pw.println("                _ps.setLong(1, _entityId);");
        pw.println("                _rs = _ps.executeQuery();");
        pw.println("                if(_rs.next()) {");
        pw.println("                    _entity = getEntityFromResultSet(session, entityPermission, _rs);");
        pw.println("                }");
        pw.println("            } catch (SQLException se) {");
        pw.println("                throw new PersistenceDatabaseException(se);");
        pw.println("            } finally {");
        pw.println("                if(_rs != null) {");
        pw.println("                    try {");
        pw.println("                        _rs.close();");
        pw.println("                    } catch (SQLException se) {");
        pw.println("                        // do nothing");
        pw.println("                    }");
        pw.println("                }");
        pw.println("            }");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _entity;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public Set<" + pkClass + "> getPKsFromQueryAsSet(PreparedStatement ps, final Object... params)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        Set<" + pkClass + "> _pks;");
        pw.println("        ResultSet _rs = null;");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            if(params.length != 0) {");
        pw.println("                Session.setQueryParams(ps, params);");
        pw.println("            }");
        pw.println("            ");
        pw.println("            _rs = ps.executeQuery();");
        pw.println("            _pks = getPKsFromResultSetAsSet(_rs);");
        pw.println("            _rs.close();");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        } finally {");
        pw.println("            if(_rs != null) {");
        pw.println("                try {");
        pw.println("                    _rs.close();");
        pw.println("                } catch (SQLException se) {");
        pw.println("                    // do nothing");
        pw.println("                }");
        pw.println("            }");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _pks;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + pkClass + "> getPKsFromQueryAsList(PreparedStatement ps, final Object... params)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        java.util.List<" + pkClass + "> _pks;");
        pw.println("        ResultSet _rs = null;");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            if(params.length != 0) {");
        pw.println("                Session.setQueryParams(ps, params);");
        pw.println("            }");
        pw.println("            ");
        pw.println("            _rs = ps.executeQuery();");
        pw.println("            _pks = getPKsFromResultSetAsList(_rs);");
        pw.println("            _rs.close();");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        } finally {");
        pw.println("            if(_rs != null) {");
        pw.println("                try {");
        pw.println("                    _rs.close();");
        pw.println("                } catch (SQLException se) {");
        pw.println("                    // do nothing");
        pw.println("                }");
        pw.println("            }");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _pks;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + pkClass + " getPKFromQuery(PreparedStatement ps, final Object... params)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        " + pkClass + " _pk = null;");
        pw.println("        ResultSet _rs = null;");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            if(params.length != 0) {");
        pw.println("                Session.setQueryParams(ps, params);");
        pw.println("            }");
        pw.println("            ");
        pw.println("            _rs = ps.executeQuery();");
        pw.println("            if(_rs.next()) {");
        pw.println("                _pk = getPKFromResultSet(_rs);");
        pw.println("            }");
        pw.println("            _rs.close();");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        } finally {");
        pw.println("            if(_rs != null) {");
        pw.println("                try {");
        pw.println("                    _rs.close();");
        pw.println("                } catch (SQLException se) {");
        pw.println("                    // do nothing");
        pw.println("                }");
        pw.println("            }");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _pk;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromQuery(Session session, EntityPermission entityPermission, Map<EntityPermission, String>queryMap, final Object... params)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        PreparedStatement ps = session.prepareStatement(" + factoryClass + ".class, queryMap.get(entityPermission));");
        pw.println("        ");
        pw.println("        return getEntitiesFromQuery(session, entityPermission, ps, params);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromQuery(EntityPermission entityPermission, Map<EntityPermission, String>queryMap, final Object... params)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        PreparedStatement ps = session.prepareStatement(" + factoryClass + ".class, queryMap.get(entityPermission));");
        pw.println("        ");
        pw.println("        return getEntitiesFromQuery(session, entityPermission, ps, params);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromQuery(Session session, EntityPermission entityPermission, Map<EntityPermission, String>queryMap)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        PreparedStatement ps = session.prepareStatement(" + factoryClass + ".class, queryMap.get(entityPermission));");
        pw.println("        ");
        pw.println("        return getEntitiesFromQuery(session, entityPermission, ps);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromQuery(EntityPermission entityPermission, Map<EntityPermission, String>queryMap)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        PreparedStatement ps = session.prepareStatement(" + factoryClass + ".class, queryMap.get(entityPermission));");
        pw.println("        ");
        pw.println("        return getEntitiesFromQuery(session, entityPermission, ps);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromQuery(EntityPermission entityPermission, PreparedStatement ps)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        return getEntitiesFromQuery(session, entityPermission, ps);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromQuery(EntityPermission entityPermission, PreparedStatement ps, final Object... params)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        return getEntitiesFromQuery(session, entityPermission, ps, params);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromQuery(Session session, EntityPermission entityPermission, PreparedStatement ps, final Object... params)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        java.util.List<" + entityClass + "> _entities;");
        pw.println("        ResultSet _rs = null;");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            if(params.length != 0) {");
        pw.println("                Session.setQueryParams(ps, params);");
        pw.println("            }");
        pw.println("            ");
        pw.println("            _rs = ps.executeQuery();");
        pw.println("            _entities = getEntitiesFromResultSet(session, entityPermission, _rs);");
        pw.println("            _rs.close();");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        } finally {");
        pw.println("            if(_rs != null) {");
        pw.println("                try {");
        pw.println("                    _rs.close();");
        pw.println("                } catch (SQLException se) {");
        pw.println("                    // do nothing");
        pw.println("                }");
        pw.println("            }");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _entities;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromQuery(Session session, EntityPermission entityPermission, Map<EntityPermission, String>queryMap, final Object... params)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        PreparedStatement ps = session.prepareStatement(" + factoryClass + ".class, queryMap.get(entityPermission));");
        pw.println("        ");
        pw.println("        return getEntityFromQuery(session, entityPermission, ps, params);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromQuery(EntityPermission entityPermission, Map<EntityPermission, String>queryMap, final Object... params)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        PreparedStatement ps = session.prepareStatement(" + factoryClass + ".class, queryMap.get(entityPermission));");
        pw.println("        ");
        pw.println("        return getEntityFromQuery(session, entityPermission, ps, params);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromQuery(Session session, EntityPermission entityPermission, Map<EntityPermission, String>queryMap)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        PreparedStatement ps = session.prepareStatement(" + factoryClass + ".class, queryMap.get(entityPermission));");
        pw.println("        ");
        pw.println("        return getEntityFromQuery(session, entityPermission, ps);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromQuery(EntityPermission entityPermission, Map<EntityPermission, String>queryMap)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        PreparedStatement ps = session.prepareStatement(" + factoryClass + ".class, queryMap.get(entityPermission));");
        pw.println("        ");
        pw.println("        return getEntityFromQuery(session, entityPermission, ps);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromQuery(EntityPermission entityPermission, PreparedStatement ps)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        return getEntityFromQuery(session, entityPermission, ps);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromQuery(EntityPermission entityPermission, PreparedStatement ps, final Object... params)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        return getEntityFromQuery(session, entityPermission, ps, params);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromQuery(Session session, EntityPermission entityPermission, PreparedStatement ps, final Object... params)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        " + entityClass + " _entity = null;");
        pw.println("        ResultSet _rs = null;");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            if(params.length != 0) {");
        pw.println("                Session.setQueryParams(ps, params);");
        pw.println("            }");
        pw.println("            ");
        pw.println("            _rs = ps.executeQuery();");
        pw.println("            if(_rs.next()) {");
        pw.println("                _entity = getEntityFromResultSet(session, entityPermission, _rs);");
        pw.println("            }");
        pw.println("            _rs.close();");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        } finally {");
        pw.println("            if(_rs != null) {");
        pw.println("                try {");
        pw.println("                    _rs.close();");
        pw.println("                } catch (SQLException se) {");
        pw.println("                    // do nothing");
        pw.println("                }");
        pw.println("            }");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _entity;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromResultSet(EntityPermission entityPermission, ResultSet rs)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        return getEntitiesFromResultSet(session, entityPermission, rs);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromResultSet(Session session, EntityPermission entityPermission, ResultSet rs)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        java.util.List<" + entityClass + "> _result = new ArrayList<>();");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            while(rs.next()) {");
        pw.println("                _result.add(getEntityFromResultSet(session, entityPermission, rs));");
        pw.println("            }");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _result;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromResultSet(EntityPermission entityPermission, ResultSet rs)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        return getEntityFromResultSet(session, entityPermission, rs);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromResultSet(Session session, EntityPermission entityPermission, ResultSet rs)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        " + entityClass + " _entity;");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            Long " + eidDbColumnName + " = rs.getLong(" + eidDbColumnName.toUpperCase(Locale.getDefault()) + ");");
        pw.println("            " + pkClass + " _pk = new " + pkClass + "(" + eidDbColumnName + ");");
        pw.println("            ");
        pw.println("            // See if we already have the entity in the session cache");
        pw.println("            _entity = (" + entityClass + ")session.getEntity(_pk);");
        pw.println("            if(_entity != null) {");
        pw.println("                // If the requested permission is READ_WRITE, and the cached permission is");
        pw.println("                // READ_ONLY, then pretend that the cached object wasn't found, and create");
        pw.println("                // a new entity that is READ_WRITE.");
        pw.println("                if(entityPermission.equals(EntityPermission.READ_WRITE)) {");
        pw.println("                    if(_entity.getEntityPermission().equals(EntityPermission.READ_ONLY))");
        pw.println("                        _entity = null;");
        pw.println("                }");
        pw.println("            }");
        pw.println("            boolean foundInSessionCache = _entity != null;");
        pw.println("            ");
        pw.println("            if(_entity == null && entityPermission.equals(EntityPermission.READ_ONLY)) {");
        pw.println("                _entity = getEntityFromCache(session, _pk);");
        pw.println("            }");
        pw.println("            ");
        pw.println("            if(_entity == null) {");

        var valueParameters = "";
        for(var column: theTable.getColumns()) {
            var type = column.getType();

            if(type != ColumnType.columnEID) {
                var dbColumnName = column.getDbColumnName();

                switch(type) {
                    case ColumnType.columnInteger -> {
                        pw.println("                Integer " + dbColumnName + " = rs.getInt(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnType.columnLong -> {
                        pw.println("                Long " + dbColumnName + " = rs.getLong(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnType.columnString -> {
                        pw.println("                String " + dbColumnName + " = rs.getString(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnType.columnBoolean -> {
                        pw.println("                Boolean " + dbColumnName + " = rs.getInt(" + dbColumnName.toUpperCase(Locale.getDefault()) + ") == 1;");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnType.columnDate -> {
                        pw.println("                Integer " + dbColumnName + " = rs.getInt(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnType.columnTime -> {
                        pw.println("                Long " + dbColumnName + " = rs.getLong(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnType.columnForeignKey -> {
                        pw.println("                Long " + dbColumnName + " = rs.getLong(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName + " == null? null: new " + column.getFKPKClass() + "(" + dbColumnName + ")";
                    }
                    case ColumnType.columnBLOB -> {
                        pw.println("                Blob " + dbColumnName + " = rs.getBlob(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", new ByteArray(" + dbColumnName + ".getBytes(1L, (int)" + dbColumnName + ".length()))";
                    }
                    case ColumnType.columnCLOB -> {
                        pw.println("                Clob " + dbColumnName + " = rs.getClob(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName + " == null? null: " + dbColumnName + ".getSubString(1L, (int)" + dbColumnName + ".length())";
                    }
                    case ColumnType.columnUUID -> {
                        pw.println("                String " + dbColumnName + " = rs.getString(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    default -> pw.println("<error>");
                }
                
                pw.println("                if(rs.wasNull())");
                pw.println("                    " + dbColumnName + " = null;");
                pw.println("                ");
            }
        }
        
        pw.println("                " + valueClass + " _value = new " + valueClass + "(_pk" + valueParameters + ");");
        pw.println("                _entity = new " + entityClass + "(_value, entityPermission);");
        pw.println("            }");
        pw.println("            ");
        pw.println("            if(!foundInSessionCache) {");
        pw.println("                if(entityPermission.equals(EntityPermission.READ_ONLY)) {");
        pw.println("                    session.putReadOnlyEntity(_pk, _entity);");
        pw.println("                    session.getValueCache().put(_entity.get" + valueClass + "());");
        pw.println("                } else {");
        pw.println("                    session.putReadWriteEntity(_pk, _entity);");
        pw.println("                }");
        pw.println("            }");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _entity;");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeFactoryValidFunction(PrintWriter pw, Table theTable) {
        pw.println("    @Override");
        pw.println("    public boolean validPK(Session session, " + theTable.getPKClass() + " pk)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        boolean valid = false;");
        pw.println("        PreparedStatement _ps = session.prepareStatement(SQL_VALID);");
        pw.println("        ResultSet _rs = null;");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            _ps.setLong(1, pk.getEntityId());");
        pw.println("            ");
        pw.println("            _rs = _ps.executeQuery();");
        pw.println("            if(_rs.next()) {");
        pw.println("                long _count = _rs.getLong(1);");
        pw.println("                if(_rs.wasNull())");
        pw.println("                    _count = 0;");
        pw.println("                ");
        pw.println("                if(_count == 1)");
        pw.println("                    valid = true;");
        pw.println("            }");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        } finally {");
        pw.println("            if(_rs != null) {");
        pw.println("                try {");
        pw.println("                    _rs.close();");
        pw.println("                } catch (SQLException se) {");
        pw.println("                    // do nothing");
        pw.println("                }");
        pw.println("            }");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return valid;");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeFactoryClass(PrintWriter pw, Table theTable)
    throws Exception {
        pw.println("@ApplicationScoped");
        pw.println("public class " + theTable.getFactoryClass());
        pw.println("        implements BaseFactory<" + theTable.getPKClass() + ", " + theTable.getEntityClass() + "> {");
        pw.println("    ");

        writeFactoryInjections(pw, theTable);
        writeFactoryInstanceVariables(pw, theTable);
        writeFactoryConstructors(pw, theTable);
        writeFactoryCoreFunctions(pw, theTable);
        
        writeFactoryPrepareFunction(pw, theTable);
        writeFactoryPkFunctions(pw, theTable);
        writeFactoryValueFunctions(pw, theTable);
        writeFactoryEntityFunctions(pw, theTable);
        
        writeFactoryCreateFunctions(pw, theTable);
        writeFactoryStoreFunctions(pw, theTable);
        writeFactoryRemoveFunctions(pw, theTable);
        writeFactoryValidFunction(pw, theTable);
        
        pw.println("}");
    }
    
    public void exportFactories(String baseDirectory)
    throws Exception {
        for(var theComponent: myComponents) {
            var componentDirectory = createFactoryDirectoryForComponent(theComponent, baseDirectory);
            
            for(var theTable: theComponent.getTables()) {
                if(theTable.hasEID()) {
                    var classFileName = theTable.getFactoryClass() + ".java";
                    var f = new File(componentDirectory + File.separatorChar + classFileName);
                    
                    try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
                        var pw = new PrintWriter(bw);
                        
                        writeCopyright(pw);
                        writeVersion(pw, classFileName);
                        writePackage(pw, theComponent.getFactoryPackage());
                        writeFactoryImports(pw, theTable);
                        writeFactoryClass(pw, theTable);
                    }
                }
            }
        }
    }
    
    public void writeConstantsInstanceVariables(PrintWriter pw, Table theTable) {
        pw.println("    String COMPONENT_VENDOR_NAME = \"ECHO_THREE\";");
        pw.println("    String ENTITY_TYPE_NAME = \"" + theTable.getNameSingular() + "\";");
        pw.println("    ");
    }
    
    public void writeConstantsClass(PrintWriter pw, Table theTable) {
        pw.println("public interface " + theTable.getConstantsClass() + " {");
        pw.println("    ");
        
        writeConstantsInstanceVariables(pw, theTable);
        
        pw.println("}");
    }
    
    public void exportCommons(String baseDirectory)
    throws Exception {
        for(var theComponent: myComponents) {
            var componentDirectory = createCommonDirectoryForComponent(theComponent, baseDirectory);
            
            for(var theTable: theComponent.getTables()) {
                var classFileName = theTable.getConstantsClass() + ".java";
                var f = new File(componentDirectory + File.separatorChar + classFileName);
                
                try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
                    var pw = new PrintWriter(bw);
                    
                    writeCopyright(pw);
                    writeVersion(pw, classFileName);
                    writePackage(pw, theComponent.getCommonPackage());
                    writeConstantsClass(pw, theTable);
                }
            }
        }
    }
    
    public void exportEntityTypesEnum(String baseDirectory)
    throws Exception {
        var directory = createCommonCoreControlDirectory(baseDirectory);
        var classFileName = "EntityTypes.java";
        var f = new File(directory + File.separatorChar + classFileName);
                
        try (var bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8)) {
            var pw = new PrintWriter(bw);

            writeCopyright(pw);
            writePackage(pw, MODEL_CONTROL_CORE_COMMON_PACKAGE);

            pw.println("public enum EntityTypes {");
            pw.println("    ");

            var notFirst = false;
            for(var theComponent: myComponents) {
                for(var theTable: theComponent.getTables()) {
                    if(notFirst) {
                        pw.println(",");
                    }
                    pw.print("    " + theTable.nameSingular);
                    notFirst = true;
                }
            }
            pw.println("");
            
            pw.println("    ");
            pw.println("}");
        }
    }
    
    public void exportJava(String baseDirectory)
    throws Exception {
        exportPKs(baseDirectory);
        exportValues(baseDirectory);
        exportEntities(baseDirectory);
        exportFactories(baseDirectory);
        exportCommons(baseDirectory);
        exportEntityTypesEnum(baseDirectory);
    }
    
}
