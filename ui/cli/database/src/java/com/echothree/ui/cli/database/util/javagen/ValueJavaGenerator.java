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

public class ValueJavaGenerator extends JavaGenerator {

    public ValueJavaGenerator(boolean verbose, Database database) {
        super(verbose, database);
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
        pw.println("import javax.annotation.Nonnull;");
        pw.println("import javax.annotation.Nullable;");
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
            if(column.getType() != ColumnDataType.EID) {
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
            if(column.getType() != ColumnDataType.EID) {
                if(wroteColumnVariable)
                    pw.print(", ");
                pw.print(column.getTypeAsJavaType() + " " + column.getVariableName());
                wroteColumnVariable = true;
            }
        }
        
        pw.println(")");
        pw.println("            throws PersistenceNotNullException {");
        
        theColumns.stream().filter((column) -> (column.getType() != ColumnDataType.EID)).map((column) -> {
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
            if(column.getType() != ColumnDataType.EID) {
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
            if(column.getType() != ColumnDataType.EID) {
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
            if(column.getType() != ColumnDataType.EID) {
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

        pw.println("    @Override");
        pw.println("    @Nonnull");
        pw.println("    public " + factoryClass + " getBaseFactoryInstance() {");
        pw.println("        return " + factoryClass + ".getInstance();");
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeValuePrimaryKey(PrintWriter pw, Table theTable) {
        var pkClass = theTable.getPKClass();
        
        pw.println("    @Override");
        pw.println("    @Nonnull");
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
                if (columnType != ColumnDataType.EID && columnType != ColumnDataType.BLOB) {
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
        pw.println("    @Nonnull");
        pw.println("    public String toString() {");
        pw.println("        if(_stringValue == null) {");
        pw.println("            _stringValue = \"{\" + ");
        pw.println("                    \"entityId=\" + getEntityId() +");

        columns.forEach((column) -> {
            var columnType = column.getType();
            if (columnType != ColumnDataType.EID && columnType != ColumnDataType.BLOB) {
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
                if (type != ColumnDataType.EID) {
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
                if(column.getType() != ColumnDataType.EID) {
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
        
        columns.stream().filter((column) -> (column.getType() != ColumnDataType.EID)).forEach((column) -> {
            pw.println("        " + column.getVariableName() + "HasBeenModified = false;");
        });
        
        pw.println("    }");
        pw.println("    ");
    }
    
    public void writeValueGetsSets(PrintWriter pw, Table theTable) {
        theTable.getColumns().stream().filter((column) -> (column.getType() != ColumnDataType.EID)).map((column) -> {
            var getFunctionName = column.getGetFunctionName();
            var variableName = column.getVariableName();
            var javaType = column.getTypeAsJavaType();
            var nullAnnotation = column.getNullAllowed() ? "@Nullable" : "@Nonnull";

            pw.println("    " + nullAnnotation);
            pw.println("    public " + javaType + " " + getFunctionName + "() {");
            pw.println("        return " + variableName + ";");
            pw.println("    }");
            pw.println("    ");
            if(!column.getNullAllowed()) {
                pw.println("    public void " + column.getSetFunctionName() + "(" + nullAnnotation + " " + javaType + " " + variableName + ")");
                pw.println("            throws PersistenceNotNullException {");
                pw.println("        checkForNull(" + variableName + ");");
                pw.println("        ");
            } else {
                pw.println("    public void " + column.getSetFunctionName() + "(" + nullAnnotation + " " + javaType + " " + variableName + ") {");
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
        pw.println("    @Nonnull");
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
    
    public void export(String baseDirectory)
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
    
}
