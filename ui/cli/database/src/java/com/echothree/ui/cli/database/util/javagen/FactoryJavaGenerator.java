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

public class FactoryJavaGenerator extends JavaGenerator {

    public FactoryJavaGenerator(boolean verbose, Database database) {
        super(verbose, database);
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
        pw.println("import org.slf4j.Logger;");
        pw.println("import org.slf4j.LoggerFactory;");
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

            if(type == ColumnDataType.EID) {
                pkColumn = column.getDbColumnName();
            } else {
                if(!allColumnsExceptPk.isEmpty())
                    allColumnsExceptPk += ", ";
                if(type == ColumnDataType.UUID)
                    allColumnsExceptPk += "BIN_TO_UUID(" + column.getDbColumnName() + ") AS " + column.getDbColumnName();
                else
                    allColumnsExceptPk += column.getDbColumnName();

                if(!insertAllColumns.isEmpty())
                    insertAllColumns += ", ";
                insertAllColumns += column.getDbColumnName();

                if(!updateColumns.isEmpty())
                    updateColumns += ", ";
                updateColumns += column.getDbColumnName();
                if(type == ColumnDataType.UUID)
                    updateColumns += " = UUID_TO_BIN(?)";
                else
                    updateColumns += " = ?";
            }
            
            if(!questionMarks.isEmpty())
                questionMarks += ", ";
            if(type == ColumnDataType.UUID)
                questionMarks += "UUID_TO_BIN(?)";
            else
                questionMarks += "?";
        }

        // These two Strings need to have the PK added to the beginning of them.
        allColumns = !allColumnsExceptPk.isEmpty() ? pkColumn + ", " + allColumnsExceptPk: pkColumn;
        insertAllColumns = !insertAllColumns.isEmpty() ? pkColumn + ", " + insertAllColumns: pkColumn;

        pw.println("    //private static final Logger log = LoggerFactory.getLogger(" + factoryClass + ".class);");
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
        pw.println("    public void remove(" + theTable.getEntityClass() + " entity)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        remove(entity.getPrimaryKey());");
        pw.println("    }");
        pw.println("    ");
        pw.println("    @Override");
        pw.println("    public void remove(" + theTable.getPKClass() + " pk)");
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
        pw.println("    public void remove(Collection<" + pkClass + "> pks)");
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

            if(type != ColumnDataType.EID) {
                var dbColumnName = column.getDbColumnName();

                pw.println("            " + column.getTypeAsJavaType() + " " + dbColumnName + " = _value." + column.getGetFunctionName() + "();");
                pw.println("            if(" + dbColumnName + " == null)");
                pw.println("                _ps.setNull(" + parameterCount + ", Types." + column.getTypeAsJavaSqlType() + ");");
                pw.println("            else");

                switch(type) {
                    case ColumnDataType.INTEGER ->
                            pw.println("                _ps.setInt(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnDataType.LONG ->
                            pw.println("                _ps.setLong(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnDataType.STRING ->
                            pw.println("                _ps.setString(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnDataType.BOOLEAN ->
                            pw.println("                _ps.setInt(" + parameterCount + ", " + dbColumnName + "? 1: 0);");
                    case ColumnDataType.DATE ->
                            pw.println("                _ps.setInt(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnDataType.TIME ->
                            pw.println("                _ps.setLong(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnDataType.FOREIGN_KEY ->
                            pw.println("                _ps.setLong(" + parameterCount + ", " + dbColumnName + ".getEntityId());");
                    case ColumnDataType.BLOB ->
                            pw.println("                _ps.setBinaryStream(" + parameterCount + ", new ByteArrayInputStream(" + dbColumnName + ".byteArrayValue()), " + dbColumnName + ".length());");
                    case ColumnDataType.CLOB ->
                            pw.println("                _ps.setCharacterStream(" + parameterCount + ", new StringReader(" + dbColumnName + "), " + dbColumnName + ".length());");
                    case ColumnDataType.UUID ->
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
        pw.println("    public void store(" + entityClass + " entity)");
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
        pw.println("    public void store(Collection<" + entityClass + "> entities)");
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

            if(type != ColumnDataType.EID) {
                if(!isFirst) {
                    createEntityParameters += ", ";
                    createPkParameters += ", ";
                    pkParameters += ", ";
                    nullParameters += ", ";
                } else {
                    isFirst = false;
                }

                valueParameters += ", ";

                if(type == ColumnDataType.FOREIGN_KEY) {
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
            pw.println("    public " + entityClass + " create()");
            pw.println("            throws PersistenceDatabaseException, PersistenceNotNullException {");
            pw.println("        return create(" + nullParameters + ");");
            pw.println("    }");
        }
        
        if(theTable.hasForeignKey()) {
            pw.println("    public " + entityClass + " create(" + createEntityParameters + ")");
            pw.println("            throws PersistenceDatabaseException, PersistenceNotNullException {");
            pw.println("        return create(" + pkParameters + ");");
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

            if(type != ColumnDataType.EID) {
                var dbColumnName = column.getDbColumnName();
                
                pw.println("        " + column.getTypeAsJavaType() + " " + dbColumnName + " = _value." + column.getGetFunctionName() + "();");
                pw.println("        if(" + dbColumnName + " == null)");
                pw.println("            _ps.setNull(" + parameterCount + ", Types." + column.getTypeAsJavaSqlType() + ");");
                pw.println("        else");

                switch(type) {
                    case ColumnDataType.INTEGER ->
                            pw.println("            _ps.setInt(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnDataType.LONG ->
                            pw.println("            _ps.setLong(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnDataType.STRING ->
                            pw.println("            _ps.setString(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnDataType.BOOLEAN ->
                            pw.println("            _ps.setInt(" + parameterCount + ", " + dbColumnName + "? 1: 0);");
                    case ColumnDataType.DATE ->
                            pw.println("            _ps.setInt(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnDataType.TIME ->
                            pw.println("            _ps.setLong(" + parameterCount + ", " + dbColumnName + ");");
                    case ColumnDataType.FOREIGN_KEY ->
                            pw.println("            _ps.setLong(" + parameterCount + ", " + dbColumnName + ".getEntityId());");
                    case ColumnDataType.BLOB ->
                            pw.println("            _ps.setBinaryStream(" + parameterCount + ", new ByteArrayInputStream(" + dbColumnName + ".byteArrayValue()), " + dbColumnName + ".length());");
                    case ColumnDataType.CLOB ->
                            pw.println("            _ps.setCharacterStream(" + parameterCount + ", new StringReader(" + dbColumnName + "), " + dbColumnName + ".length());");
                    case ColumnDataType.UUID ->
                            pw.println("            _ps.setString(" + parameterCount + ", " + dbColumnName + ");");
                    default -> pw.println("<error>");
                }
                
                pw.println("            ");
                
                parameterCount++;
            }
        }
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " create(" + createPkParameters + ")");
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
        pw.println("    public void create(Collection<" + valueClass + "> _values)");
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
      }
    
    public void writeFactoryValueFunctions(PrintWriter pw, Table theTable)
    throws Exception {
        var entityClass = theTable.getEntityClass();
        var valueClass = theTable.getValueClass();
        var pkClass = theTable.getPKClass();
        var eidColumn = theTable.getEID();
        var eidDbColumnName = eidColumn.getDbColumnName();
        
        pw.println("    public java.util.List<" + valueClass + "> getValuesFromPKs(Collection<" + pkClass + "> pks)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        java.util.List<" + valueClass + "> _values = new ArrayList<>(pks.size());");
        pw.println("        ");
        pw.println("        for(" + pkClass + " _pk: pks) {");
        pw.println("            _values.add(getValueFromPK(_pk));");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _values;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + valueClass + " getValueFromPK(" + pkClass + " pk)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        " + valueClass + " _value;");
        pw.println("        ");
        pw.println("        // See if we already have the entity in the session cache");
        pw.println("        " + entityClass + " _entity = (" + entityClass + ")session.getEntity(pk);");
        pw.println("        if(_entity == null)");
        pw.println("            _value = getEntityFromPK(EntityPermission.READ_ONLY, pk).get" + valueClass + "();");
        pw.println("        else");
        pw.println("            _value = _entity.get" + valueClass + "();");
        pw.println("        ");
        pw.println("        return _value;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + valueClass + "> getValuesFromResultSet(ResultSet rs)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        java.util.List<" + valueClass + "> _result = new ArrayList<>();");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            while(rs.next()) {");
        pw.println("                _result.add(getValueFromResultSet(rs));");
        pw.println("            }");
        pw.println("        } catch (SQLException se) {");
        pw.println("            throw new PersistenceDatabaseException(se);");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _result;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + valueClass + " getValueFromResultSet(ResultSet rs)");
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

            if(type != ColumnDataType.EID) {
                var dbColumnName = column.getDbColumnName();

                switch(type) {
                    case ColumnDataType.INTEGER -> {
                        pw.println("                Integer " + dbColumnName + " = rs.getInt(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnDataType.LONG -> {
                        pw.println("                Long " + dbColumnName + " = rs.getLong(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnDataType.STRING -> {
                        pw.println("                String " + dbColumnName + " = rs.getString(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnDataType.BOOLEAN -> {
                        pw.println("                Boolean " + dbColumnName + " = rs.getInt(" + dbColumnName.toUpperCase(Locale.getDefault()) + ") == 1;");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnDataType.DATE -> {
                        pw.println("                Integer " + dbColumnName + " = rs.getInt(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnDataType.TIME -> {
                        pw.println("                Long " + dbColumnName + " = rs.getLong(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnDataType.FOREIGN_KEY -> {
                        pw.println("                Long " + dbColumnName + " = rs.getLong(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", new " + column.getFKPKClass() + "(" + dbColumnName + ")";
                    }
                    case ColumnDataType.BLOB -> {
                        pw.println("                Blob " + dbColumnName + " = rs.getBlob(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", new ByteArray(" + dbColumnName + ".getBytes(1L, (int)" + dbColumnName + ".length()))";
                    }
                    case ColumnDataType.CLOB -> {
                        pw.println("                Clob " + dbColumnName + " = rs.getClob(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName + " == null? null: " + dbColumnName + ".getSubString(1L, (int)" + dbColumnName + ".length())";
                    }
                    case ColumnDataType.UUID -> {
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
        pw.println("        java.util.List<" + entityClass + "> _entities = new ArrayList<>(pks.size());");
        pw.println("        ");
        pw.println("        for(" + pkClass + " _pk: pks) {");
        pw.println("            _entities.add(getEntityFromPK(entityPermission, _pk));");
        pw.println("        }");
        pw.println("        ");
        pw.println("        return _entities;");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromValue(EntityPermission entityPermission, " + valueClass + " value) {");
        pw.println("        return getEntityFromPK(entityPermission, value.getPrimaryKey());");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromCache(" + pkClass + " pk) {");
        pw.println("        " + valueClass + " _value = (" + valueClass + ")session.getValueCache().get(pk);");
        pw.println("    ");
        pw.println("        return _value == null ? null : new " + entityClass + "(_value, EntityPermission.READ_ONLY);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromPK(EntityPermission entityPermission, " + pkClass + " pk)");
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
        pw.println("            _entity = getEntityFromCache(pk);");
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
        pw.println("                    _entity = getEntityFromResultSet(entityPermission, _rs);");
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
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromQuery(EntityPermission entityPermission, Map<EntityPermission, String>queryMap, final Object... params)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        PreparedStatement ps = session.prepareStatement(" + factoryClass + ".class, queryMap.get(entityPermission));");
        pw.println("        ");
        pw.println("        return getEntitiesFromQuery(entityPermission, ps, params);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromQuery(EntityPermission entityPermission, Map<EntityPermission, String>queryMap)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        PreparedStatement ps = session.prepareStatement(" + factoryClass + ".class, queryMap.get(entityPermission));");
        pw.println("        ");
        pw.println("        return getEntitiesFromQuery(entityPermission, ps);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public java.util.List<" + entityClass + "> getEntitiesFromQuery(EntityPermission entityPermission, PreparedStatement ps, final Object... params)");
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
        pw.println("            _entities = getEntitiesFromResultSet(entityPermission, _rs);");
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
        pw.println("    public " + entityClass + " getEntityFromQuery(EntityPermission entityPermission, Map<EntityPermission, String>queryMap, final Object... params)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        PreparedStatement ps = session.prepareStatement(" + factoryClass + ".class, queryMap.get(entityPermission));");
        pw.println("        ");
        pw.println("        return getEntityFromQuery(entityPermission, ps, params);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromQuery(EntityPermission entityPermission, Map<EntityPermission, String>queryMap)");
        pw.println("            throws PersistenceDatabaseException {");
        pw.println("        PreparedStatement ps = session.prepareStatement(" + factoryClass + ".class, queryMap.get(entityPermission));");
        pw.println("        ");
        pw.println("        return getEntityFromQuery(entityPermission, ps);");
        pw.println("    }");
        pw.println("    ");
        pw.println("    public " + entityClass + " getEntityFromQuery(EntityPermission entityPermission, PreparedStatement ps, final Object... params)");
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
        pw.println("                _entity = getEntityFromResultSet(entityPermission, _rs);");
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
        pw.println("        java.util.List<" + entityClass + "> _result = new ArrayList<>();");
        pw.println("        ");
        pw.println("        try {");
        pw.println("            while(rs.next()) {");
        pw.println("                _result.add(getEntityFromResultSet(entityPermission, rs));");
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
        pw.println("                _entity = getEntityFromCache(_pk);");
        pw.println("            }");
        pw.println("            ");
        pw.println("            if(_entity == null) {");

        var valueParameters = "";
        for(var column: theTable.getColumns()) {
            var type = column.getType();

            if(type != ColumnDataType.EID) {
                var dbColumnName = column.getDbColumnName();

                switch(type) {
                    case ColumnDataType.INTEGER -> {
                        pw.println("                Integer " + dbColumnName + " = rs.getInt(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnDataType.LONG -> {
                        pw.println("                Long " + dbColumnName + " = rs.getLong(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnDataType.STRING -> {
                        pw.println("                String " + dbColumnName + " = rs.getString(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnDataType.BOOLEAN -> {
                        pw.println("                Boolean " + dbColumnName + " = rs.getInt(" + dbColumnName.toUpperCase(Locale.getDefault()) + ") == 1;");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnDataType.DATE -> {
                        pw.println("                Integer " + dbColumnName + " = rs.getInt(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnDataType.TIME -> {
                        pw.println("                Long " + dbColumnName + " = rs.getLong(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName;
                    }
                    case ColumnDataType.FOREIGN_KEY -> {
                        pw.println("                Long " + dbColumnName + " = rs.getLong(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName + " == null? null: new " + column.getFKPKClass() + "(" + dbColumnName + ")";
                    }
                    case ColumnDataType.BLOB -> {
                        pw.println("                Blob " + dbColumnName + " = rs.getBlob(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", new ByteArray(" + dbColumnName + ".getBytes(1L, (int)" + dbColumnName + ".length()))";
                    }
                    case ColumnDataType.CLOB -> {
                        pw.println("                Clob " + dbColumnName + " = rs.getClob(" + dbColumnName.toUpperCase(Locale.getDefault()) + ");");
                        valueParameters += ", " + dbColumnName + " == null? null: " + dbColumnName + ".getSubString(1L, (int)" + dbColumnName + ".length())";
                    }
                    case ColumnDataType.UUID -> {
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
        pw.println("    public boolean validPK(" + theTable.getPKClass() + " pk)");
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
    
    public void export(String baseDirectory)
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
    
}
