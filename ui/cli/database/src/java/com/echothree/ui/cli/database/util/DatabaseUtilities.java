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

import com.echothree.ui.cli.database.util.current.CurrentColumn;
import com.echothree.ui.cli.database.util.current.CurrentDatabase;
import com.echothree.ui.cli.database.util.current.CurrentDatabaseUtils;
import com.echothree.ui.cli.database.util.current.CurrentForeignKey;
import com.echothree.ui.cli.database.util.current.CurrentIndex;
import com.echothree.ui.cli.database.util.current.CurrentTable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class DatabaseUtilities {
    
    private final Log log = LogFactory.getLog(this.getClass());
    
    boolean verbose;
    Database myDatabase;
    String connectionClass;
    String connectionUrl;
    String connectionUser;
    String connectionPassword;
    String connectionCharacterSet;
    String connectionCollation;
    
    Connection myConnection = null;
    DatabaseMetaData myDatabaseMetaData = null;
    
    /** Creates a new instance of DatabaseUtilities
     */
    protected DatabaseUtilities(boolean verbose, Database theDatabase, String connectionClass, String connectionUrl, String connectionUser,
            String connectionPassword, String connectionCharacterSet, String connectionCollation) {
        this.verbose = verbose;
        myDatabase = theDatabase;
        this.connectionClass = connectionClass;
        this.connectionUrl = connectionUrl;
        this.connectionUser = connectionUser;
        this.connectionPassword = connectionPassword;
        this.connectionCharacterSet = connectionCharacterSet;
        this.connectionCollation = connectionCollation;
    }
    
    /** Opens a database connection for use by all other functions
     */
    void openDatabaseConnection(Database theDatabase)
            throws Exception {
        Class.forName(connectionClass);
        myConnection = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
        myDatabaseMetaData = myConnection.getMetaData();
        myDatabase = theDatabase;
    }
    
    void closeDatabaseConnection()
            throws Exception {
        myDatabaseMetaData = null;
        myConnection.close();
        myConnection = null;
    }
    
    public void emptyDatabase()
            throws Exception {
        openDatabaseConnection(myDatabase);
        doEmptyDatabase();
        closeDatabaseConnection();
    }
    
    void doEmptyDatabase()
            throws Exception {
        throw new UnsupportedOperationException();
    }
    
    public void setCharacterSetAndCollection()
            throws Exception {
        openDatabaseConnection(myDatabase);

        doSetDatabaseCharacterSetAndCollection();
        doSetTableCharacterSetAndCollection();
        doSetColumnCharacterSetAndCollection();

        closeDatabaseConnection();
    }
    
    void doSetDatabaseCharacterSetAndCollection()
            throws Exception {
        throw new UnsupportedOperationException();
    }

    void doSetTableCharacterSetAndCollection()
            throws Exception {
        throw new UnsupportedOperationException();
    }

    void doSetColumnCharacterSetAndCollection()
            throws Exception {
        throw new UnsupportedOperationException();
    }
    
    /** Checks the database for any extra tables, then it goes through and
     * checks each of the entities in the database and applies any changes
     * that need to be made.
     */
    public void checkDatabase()
            throws Exception {
        openDatabaseConnection(myDatabase);

        var currentDatabase = CurrentDatabaseUtils.getInstance().getCurrentDatabase(myConnection);
        var neededUpdates = new DatabaseUpdateTasks();
        
        checkTables(currentDatabase, neededUpdates);
        executeUpdates(neededUpdates);
        
        closeDatabaseConnection();
    }
    
    /** Return the start of a CREATE TABLE command, this will probably be the
     * same for all databases
     * @return Text to use as part of the SQL query for this table
     * @param tableName The table name that this SQL is for
     */
    String getCreateTableBeginning(String tableName) {
        var dbTableName = tableName.toLowerCase(Locale.getDefault());
        
        if(verbose && dbTableName.length() > 30)
            log.warn("table \"" + dbTableName + "\" exceeds 30 characters");
        
        return "CREATE TABLE " + tableName.toLowerCase(Locale.getDefault()) + " ( ";
    }
    
    /** Returns a string that may represent columns that are needed by, for example,
     * a persistence layer, but that are not directly used by the application
     */
    String getCreateTableHiddenColumns(String columnPrefix, String tableName) {
        return "";
    }
    
    boolean isHiddenColumn(Table theTable, String columnName) {
        return false;
    }
    
    /** Return a String which is the end of a CREATE TABLE SQL command, usually this
     * will just be the closing parenthesis
     * @return Text to use as part of the SQL query for this table
     * @param tableName The table name that this SQL is for
     */
    String getCreateTableEnding(String columnPrefix, String tableName) {
        return " )";
    }
    
    /** Return a String which represents any additional text that needs to be
     * included after the CREATE TABLE command, for example, the table type in
     * MySQL
     * @return Text to use as part of the SQL query for this table
     * @param tableName The table name that this SQL is for
     */
    String getCreateTableParameters(String tableName) {
        return "";
    }
    
    /** Returns a string that contains the column prefix, a separator character and the
     * column name in all lower case
     */
    String getColumnName(String columnPrefix, String columnName) {
        return columnPrefix + "_" + columnName.toLowerCase(Locale.getDefault());
    }
    
    /** Returns a string that contains the column prefix, a separator character and the
     * column name in all lower case, unless it's a foreign key and thinks get unnecessarily
     * complex
     */
    String getColumnName(Column theColumn)
    throws Exception {
        String result;
        
        if(theColumn.getType() == ColumnType.columnForeignKey) {
            var theTable = theColumn.getTable();
            var columnPrefix = theTable.getColumnPrefix().toLowerCase(Locale.getDefault());

            var destinationTableName = theColumn.getDestinationTable();
            var destinationTable = theTable.getDatabase().getTable(destinationTableName);
            var destinationColumnName = theColumn.getDestinationColumn();
            //Column destinationColumn = destinationTable.getColumn(destinationColumnName);
            var destinationColumnPrefix = destinationTable.getColumnPrefix().toLowerCase(Locale.getDefault());

            var referencesSelf = theColumn.getTable() == destinationTable;
            var differingColumnName = !referencesSelf && !theColumn.getName().equals(destinationColumnName);
            var fkColumnPrefix = (referencesSelf? "": columnPrefix + "_") + (differingColumnName? "": destinationColumnPrefix + "_");
            result = fkColumnPrefix + theColumn.getName().toLowerCase(Locale.getDefault());
        } else
            result = getColumnName(theColumn.getTable().getColumnPrefixLowerCase(), theColumn.getName());
        
        return result;
    }
    
    /** Returns a string that contains the column name, with the first character forced
     * to lower case
     */
    String getColumnParameterName(Column theColumn) {
        var columnName = theColumn.getName();
        var parameterName = columnName.substring(0, 1).toLowerCase(Locale.getDefault());
        
        if(theColumn.getType() == ColumnType.columnForeignKey)
            parameterName += columnName.substring(1, columnName.length() - 2) + "PK";
        else
            parameterName += columnName.substring(1);
        return parameterName;
    }
    
    /** Returns a string that is the Java type for the Column.
     */
    String getColumnParameterType(Column theColumn)
    throws Exception {
        String result = null;

        switch(theColumn.getType()) {
            case ColumnType.columnEID -> result = "Long";
            case ColumnType.columnInteger -> result = "Integer";
            case ColumnType.columnLong -> result = "Long";
            case ColumnType.columnString -> result = "String";
            case ColumnType.columnBoolean -> result = "Boolean";
            case ColumnType.columnDate -> result = "Integer";
            case ColumnType.columnTime -> result = "Long";
            case ColumnType.columnCLOB -> result = "char []";
            case ColumnType.columnBLOB -> result = "byte []";
            case ColumnType.columnForeignKey -> {
                var destinationTable = theColumn.getTable().getDatabase().getTable(theColumn.getDestinationTable());
                result = destinationTable.getNameSingular() + "Bean";
            }
        }
        return result;
    }
    
    /** Returns the SQL needed for a column of type EID
     * @return Text to use as part of the SQL query for this column
     * @param theColumn Column that is the SQL is being generated for
     */
    String getEIDDefinition(String columnName, Column theColumn, Column theFKColumn)
    throws Exception {
        if((theFKColumn == null) && theColumn.getNullAllowed())
            throw new Exception("EID columns cannot be null ("
                    + theColumn.getTable().getNamePlural() + "."
                    + theColumn.getName() + ")");
        
        return getLongDefinition(columnName, theColumn, theFKColumn);
    }
    
    /** Returns the SQL needed for a column of type Integer
     * @return Text to use as part of the SQL query for this column
     * @param theColumn Column that is the SQL is being generated for
     */
    String getIntegerDefinition(String columnName, Column theColumn, Column theFKColumn) {
        return null;
    }
    
    /** Returns the SQL needed for a column of type Long
     * @return Text to use as part of the SQL query for this column
     * @param theColumn Column that is the SQL is being generated for
     */
    String getLongDefinition(String columnName, Column theColumn, Column theFKColumn) {
        return null;
    }
    
    /** Returns the SQL needed for a column of type String
     * @return Text to use as part of the SQL query for this column
     * @param theColumn Column that is the SQL is being generated for
     */
    String getStringDefinition(String columnName, Column theColumn, Column theFKColumn) {
        return null;
    }
    
    /** Checks an existing column against the column definition.
     * @param ci Column in the existing database that needs to be verified.
     * @param theColumn Column that the CurrentColumn will be compared to.
     * @return Returns true if the CurrentColumn is OK.
     */
    boolean checkColumnDefinition(CurrentColumn ci, Column theColumn) throws Exception {
        return true;
    }
    
    /** Returns the SQL needed for a column of type Boolean
     * @return Text to use as part of the SQL query for this column
     * @param theColumn Column that is the SQL is being generated for
     */
    String getBooleanDefinition(String columnName, Column theColumn, Column theFKColumn) {
        return null;
    }
    
    /** Returns the SQL needed for a column of type Date
     * @return Text to use as part of the SQL query for this column
     * @param theColumn Column that is the SQL is being generated for
     */
    String getDateDefinition(String columnName, Column theColumn, Column theFKColumn) {
        return null;
    }
    
    /** Returns the SQL needed for a column of type Time, which maps to
     * standard unix time measured in seconds
     * @return Text to use as part of the SQL query for this column
     * @param theColumn Column that is the SQL is being generated for
     */
    String getTimeDefinition(String columnName, Column theColumn, Column theFKColumn) {
        return getLongDefinition(columnName, theColumn, theFKColumn);
    }
    
    String getCLOBDefinition(String columnName, Column theColumn) {
        return null;
    }
    
    String getBLOBDefinition(String columnName, Column theColumn) {
        return null;
    }
    
    /** Returns the SQL needed for a column of type ForeignKey
     * @return Text to use as part of the SQL query for this column
     * @param columnPrefix Upper case text to use as a column name prefix
     * @param theColumn Column that is the SQL is being generated for
     */
    String getForeignKeyDefinition(Table theTable, String columnPrefix, Column theColumn)
    throws Exception {
        var destinationTableName = theColumn.getDestinationTable();
        var destinationTable = theTable.getDatabase().getTable(destinationTableName);
        var destinationColumnName = theColumn.getDestinationColumn();
        var destinationColumn = destinationTable.getColumn(destinationColumnName);

        var destinationColumnPrefix = destinationTable.getColumnPrefix().toLowerCase(Locale.getDefault());

        var referencesSelf = theColumn.getTable() == destinationTable;
        var differingColumnName = !referencesSelf && !theColumn.getName().equals(destinationColumnName);
        var fkColumnPrefix = (referencesSelf? "": columnPrefix + "_") + (differingColumnName? "": destinationColumnPrefix + "_");
        var fkColumnName = fkColumnPrefix + theColumn.getName().toLowerCase(Locale.getDefault());

        var fkResult = getColumnDefinitionWithName(destinationTable, fkColumnPrefix, fkColumnName, destinationColumn,
                theColumn);
        
        return fkResult;
    }

    String getUUIDDefinition(String columnName, Column theColumn) {
        return null;
    }

    String getColumnDefinitionWithName(Table theTable, String columnPrefix,
            String columnName, Column theColumn, Column theFKColumn) throws Exception {
        String columnResult = null;

        switch(theColumn.getType()) {
            case ColumnType.columnEID -> columnResult = getEIDDefinition(columnName, theColumn, theFKColumn);
            case ColumnType.columnInteger -> columnResult = getIntegerDefinition(columnName, theColumn, theFKColumn);
            case ColumnType.columnLong -> columnResult = getLongDefinition(columnName, theColumn, theFKColumn);
            case ColumnType.columnString -> columnResult = getStringDefinition(columnName, theColumn, theFKColumn);
            case ColumnType.columnBoolean -> columnResult = getBooleanDefinition(columnName, theColumn, theFKColumn);
            case ColumnType.columnDate -> columnResult = getDateDefinition(columnName, theColumn, theFKColumn);
            case ColumnType.columnTime -> columnResult = getTimeDefinition(columnName, theColumn, theFKColumn);
            case ColumnType.columnCLOB -> columnResult = getCLOBDefinition(columnName, theColumn);
            case ColumnType.columnBLOB -> columnResult = getBLOBDefinition(columnName, theColumn);
            case ColumnType.columnForeignKey ->
                    columnResult = getForeignKeyDefinition(theTable, columnPrefix, theColumn);
            case ColumnType.columnUUID -> columnResult = getUUIDDefinition(columnName, theColumn);
        }
        
        return columnResult;
    }
    
    /** Get the SQL needed to include in either a CREATE TABLE or an ALTER TABLE for
     * an individual column in a table
     * @return Text to use as part of the SQL query for this column
     * @param theColumn Column that is the SQL is being generated for
     */
    String getColumnDefinition(Table theTable, String columnPrefix, Column theColumn)
        throws Exception {
        var columnName = getColumnName(columnPrefix, theColumn.getName());
        
        if(verbose && columnName.length() > 30)
            log.warn("column \"" + columnName + "\" exceeds 30 characters");
        
        return getColumnDefinitionWithName(theTable, columnPrefix, columnName, theColumn, null);
    }
    
    String getColumnDefinition(Column theColumn)
        throws Exception {
        var theTable = theColumn.getTable();
        
        return getColumnDefinition(theTable, theTable.getColumnPrefix(), theColumn);
    }
    
    /** Returns the name to use for an index in the database
     */
    String getIndexName(Index theIndex) {
        String indexName;
        
        if(theIndex.getType() == Index.indexPrimaryKey) {
            indexName = "PRIMARY";
        } else {
            indexName = theIndex.getName().toLowerCase(Locale.getDefault()) + "_idx";
        }
        
        return indexName;
    }
    
    /** Returns the name of a foreign key column that should be used when creating
     * an index
     */
    String getIndexForeignKeyColumnName(String columnPrefix, Column theColumn)
    throws Exception {

        var destinationTableName = theColumn.getDestinationTable();
        var destinationTable = theColumn.getTable().getDatabase().getTable(destinationTableName);
        var destinationColumnName = theColumn.getDestinationColumn();
        var destinationColumnPrefix = destinationTable.getColumnPrefix().toLowerCase(Locale.getDefault());

        var referencesSelf = theColumn.getTable() == destinationTable;
        var differingColumnName = !referencesSelf && !theColumn.getName().equals(destinationColumnName);
        var fkColumnPrefix = (referencesSelf? "": columnPrefix + "_") + (differingColumnName? "": destinationColumnPrefix + "_");
        var fkColumnName = fkColumnPrefix + theColumn.getName().toLowerCase(Locale.getDefault());
        
        return fkColumnName;
    }
    
    /** Returns a comma separated list of column names that are used in the index
     */
    String getIndexColumnList(Index theIndex) throws Exception {
        var columnPrefix = theIndex.getTable().getColumnPrefix().toLowerCase(Locale.getDefault());
        var result = "";
        var afterFirst = false;
        
        for(var theColumn: theIndex.getIndexColumns()) {
            if(afterFirst)
                result += ", ";
            else
                afterFirst = true;
            
            if(theColumn.getType() == ColumnType.columnForeignKey)
                result += getIndexForeignKeyColumnName(columnPrefix, theColumn);
            else
                result += columnPrefix + "_" + theColumn.getName().toLowerCase(Locale.getDefault());
        }
        
        return result;
    }
    
    /** Returns the SQL needed for a primary key
     */
    String getPrimaryKeyIndex(Index theIndex) throws Exception {
        return null;
    }
    
    /** Returns the SQL needed for a unique index
     */
    String getUniqueIndex(Index theIndex) throws Exception {
        return null;
    }
    
    /** Returns the SQL needed for an index that allows the same key to be used more than
     * once
     */
    String getMultipleIndex(Index theIndex) throws Exception {
        return null;
    }
    
    /** Returns a database-type specific string that is the text to use to create or
     * add a foreign key reference to a table
     */
    String getForeignKeyDefinition(Column theFK, Table sourceTable, String sourceColumnName, Table destinationTable, String destinationColumnName) throws Exception {
        return null;
    }
    
    /** Create a table that is completely missing from the database
     * @throws Exception Thrown when a database error occurs
     */
    void createMissingTable(Table theTable) throws Exception {
        var tableName = theTable.getNamePlural();
        var tableNameLC = tableName.toLowerCase(Locale.getDefault());
        var columnPrefix = theTable.getColumnPrefix().toLowerCase(Locale.getDefault());

        var createSQL = new StringBuilder(getCreateTableBeginning(tableNameLC));
        
        List theColumns = theTable.getColumns();
        var columnCount = theColumns.size();
        for(var i = 0; i < columnCount; i++) {
            if(i != 0)
                createSQL.append(", ");
            createSQL.append(getColumnDefinition(theTable, columnPrefix, (Column)theColumns.get(i)));
        }
        
        createSQL.append(getCreateTableHiddenColumns(columnPrefix, tableNameLC));
        createSQL.append(getCreateTableEnding(columnPrefix, tableNameLC));
        createSQL.append(getCreateTableParameters(tableNameLC));

        var createSQLString = createSQL.toString();
        if(verbose) {
            System.out.println(createSQLString);
        }
    
        try(var stmt = myConnection.createStatement()) {
            stmt.execute(createSQLString);
        }
    }
    
    String getDropTable(CurrentTable ct) {
        return "DROP TABLE " + ct.getTableName();
    }
    
    boolean checkExistingTableColumns(CurrentTable ct, Table theTable, DatabaseUpdateTasks neededUpdates)
            throws Exception {
        var recreatingTable = false;
        var columns = ct.getColumns();
        Set<String> xmlColumns = new HashSet<>();
        List<CurrentColumn> extraColumns = new ArrayList<>();
        
        for(var theColumn: theTable.getColumns()) {
            xmlColumns.add(theColumn.getDbColumnName());
        }

        // Check for extra columns.
        columns.values().forEach((cc) -> {
            var columnName = cc.getColumnName();
            if (!xmlColumns.contains(columnName) && !isHiddenColumn(theTable, columnName)) {
                extraColumns.add(cc);
            }
        });
        
        if(extraColumns.size() == columns.size()) {
            // Everything's going away, just drop the table and recreate.
            neededUpdates.addExtraTable(ct);
            neededUpdates.addTable(theTable);
            
            recreatingTable = true;
        } else {
            // Otherwise, add updates to remove the extra columns and continue on.
            neededUpdates.addExtraColumns(extraColumns);
            
            // Check for missing columns.
            for(var theColumn: theTable.getColumns()) {
                var columnName = theColumn.getDbColumnName();

                var cc = ct.getColumn(columnName);
                if(cc != null) {
                    // Check CurrentColumn against the Column
                    if(!checkColumnDefinition(cc, theColumn)) {
                        neededUpdates.addIncorrectColumn(theColumn);
                    }
                } else {
                    neededUpdates.addColumn(theColumn);
                }
            }
        }
        
        return recreatingTable;
    }
    
    Column getColumnByDbName(Table theTable, String dbColumnName) throws Exception {
        Column theColumn = null;

        for(var foundColumn: theTable.getColumns()) {
            if(foundColumn.getDbColumnName().equals(dbColumnName)) {
                theColumn = foundColumn;
                break;
            }
        }
        
        return theColumn;
    }
    
    void recreateForeignKeyIfNecessary(CurrentTable ct, Table theTable, CurrentIndex ci, DatabaseUpdateTasks neededUpdates) throws Exception {
        var firstColumn = ci.getColumns().iterator().next();
        var firstColumnName = firstColumn.getColumnName();
        var theColumn = getColumnByDbName(theTable, firstColumnName);

        if(theColumn != null && theColumn.getType() == ColumnType.columnForeignKey) {
            var cfk = ct.getForeignKeyByColumnName(firstColumnName);

            neededUpdates.addExtraForeignKey(cfk);
            neededUpdates.addForeignKey(theColumn);
        }
    }
    
    void checkExistingTableIndexes(CurrentTable ct, Table theTable, DatabaseUpdateTasks neededUpdates) throws Exception {
        var indexes = ct.getIndexes();
        Set<String> xmlIndexes = new HashSet<>();
        
        // Check for missing indexes.
        for(var theIndex: theTable.getIndexes()) {
            var indexName = getIndexName(theIndex);
            
            xmlIndexes.add(indexName);

            var ci = ct.getIndex(indexName);
            if(ci != null) {
                var indexIncorrect = false;
                var indexType = theIndex.getType();
                
                if(ci.isUnique() == (indexType == Index.indexMultiple)) {
                    indexIncorrect = true;
                } else if(ci.isPrimaryKey() != (indexType == Index.indexPrimaryKey)) {
                    indexIncorrect = true;
                }
                
                if(!indexIncorrect) {
                    var indexColumns = theIndex.getIndexColumns();
                    var currentColumns = ci.getColumns();

                    if(currentColumns.size() == indexColumns.size()) {
                        var currentColumnsIter = currentColumns.iterator();

                        for(var indexColumn: theIndex.getIndexColumns()) {
                            if(!indexColumn.getDbColumnName().equals(currentColumnsIter.next().getColumnName())) {
                                indexIncorrect = true;
                                break;
                            }
                        }
                    } else {
                        indexIncorrect = true;
                    }
                }
                
                if(indexIncorrect) {
                    // Drop existing index, and recreate it.
                    recreateForeignKeyIfNecessary(ct, theTable, ci, neededUpdates);
                    
                    neededUpdates.addExtraIndex(ci);
                    neededUpdates.addIndex(theIndex);
                }
            } else {
                // Add missing index.
                neededUpdates.addIndex(theIndex);
            }
        }
        
        // Check for extra indexes.
        for(var ci: indexes.values()) {
            var indexName = ci.getIndexName();
            
            if(!xmlIndexes.contains(indexName)) {
                // Drop existing index.
                recreateForeignKeyIfNecessary(ct, theTable, ci, neededUpdates);
                neededUpdates.addExtraIndex(ci);
            }
        }
    }
    
    /** Compare a table that already exists in the database to the XML configuration and
     * make any needed alterations
     * @throws Exception Thrown when a database error occurs
     */
    void checkExistingTable(CurrentTable ct, Table theTable, DatabaseUpdateTasks neededUpdates)
            throws Exception {
        if(!checkExistingTableColumns(ct, theTable, neededUpdates)) {
            checkExistingTableIndexes(ct, theTable, neededUpdates);
        }
    }
    
    /** Compare tables in the database with those in the XML configuration file,
     * create any tables that are missing, alter any tables that are not correct
     * @throws Exception Thrown when a database error occurs
     */
    void checkTables(CurrentDatabase cd, DatabaseUpdateTasks neededUpdates) throws Exception {
        var tables = cd.getTables();
        Set<String> xmlTables = new HashSet<>();
        
        // Check for missing tables.
        for(var theTable: myDatabase.getTables()) {
            var tableName = theTable.getNamePlural().toLowerCase(Locale.getDefault());
            
            xmlTables.add(tableName);

            var ct = tables.get(tableName);
            if(ct != null) {
                checkExistingTable(ct, theTable, neededUpdates);
            } else {
                neededUpdates.addTable(theTable);
            }
        }
        
        // Check for extra tables.
        tables.values().stream().filter((ct) -> !xmlTables.contains(ct.getTableName())).forEach((ct) -> {
            neededUpdates.addExtraTable(ct);
        });
    }
    
    void executeTableUpdates(List<Table> tablesNeeded)
            throws Exception {
        for(var theTable: tablesNeeded) {
            createMissingTable(theTable);
        }
    }
    
    String getAlterTableAddColumn(Column theColumn)
            throws Exception {
        return "ALTER TABLE " + theColumn.getTable().getDbTableName() + " ADD COLUMN " + getColumnDefinition(theColumn);
    }
    
    void createMissingColumn(Column theColumn) throws Exception {
        var alterSQL = getAlterTableAddColumn(theColumn);

        if(verbose) {
            System.out.println(alterSQL);
        }

        var stmt = myConnection.createStatement();
        stmt.execute(alterSQL);
        stmt.close();
    }
    
    String getAlterTableDropColumn(CurrentColumn cc) {
        return "ALTER TABLE " + cc.getTable().getTableName() + " DROP COLUMN " + cc.getColumnName();
    }
    
    void executeColumnUpdates(List<Column> columnsNeeded) throws Exception {
        for(var theColumn: columnsNeeded) {
            createMissingColumn(theColumn);
        }
    }
    
    String getAlterTableAddIndex(Index theIndex, String sqlForIndex) {
        return "ALTER TABLE " + theIndex.getTable().getNamePlural().toLowerCase(Locale.getDefault())  + " ADD " + sqlForIndex;
    }
    
    String getAlterTableDropIndex(CurrentIndex ci) {
        String result;
        
        if(ci.isPrimaryKey()) {
            result = "ALTER TABLE " + ci.getTable().getTableName() + " DROP PRIMARY KEY";
        } else {
            result = "DROP INDEX " + ci.getIndexName() + " ON " + ci.getTable().getTableName();
        }
        
        return result;
    }
    
    void createMissingIndex(Index theIndex)
            throws Exception {
        var sqlForIndex = switch(theIndex.getType()) {
            case Index.indexPrimaryKey -> getPrimaryKeyIndex(theIndex);
            case Index.indexUnique -> getUniqueIndex(theIndex);
            case Index.indexMultiple -> getMultipleIndex(theIndex);
            default -> null;
        };

        var alterSQL = getAlterTableAddIndex(theIndex, sqlForIndex);
        
        if(verbose) {
            System.out.println(alterSQL);
        }

        var stmt = myConnection.createStatement();
        stmt.execute(alterSQL);
        stmt.close();
    }
    
    void executeIndexUpdates(Set<Index> indexesNeeded)
            throws Exception {
        for(var theIndex: indexesNeeded) {
            createMissingIndex(theIndex);
        }
    }
    
    String getAlterTableAddForeignKey(Column theFK, String sqlForFK) {
        return "ALTER TABLE " + theFK.getTable().getNamePluralLowerCase() + " ADD " + sqlForFK;
    }
    
    String getAlterTableDropForeignKey(CurrentForeignKey cfk) {
        return "ALTER TABLE " + cfk.getTable().getTableName() + " DROP FOREIGN KEY " + cfk.getForeignKeyName();
    }
    
    void createMissingForeignKey(Column theFK)
            throws Exception {
        var destinationTableName = theFK.getDestinationTable();
        var destinationTable = theFK.getTable().getDatabase().getTable(destinationTableName);
        var destinationColumnName = theFK.getDestinationColumn();
        var destinationColumn = destinationTable.getColumn(destinationColumnName);

        var columnPrefix = theFK.getTable().getColumnPrefix().toLowerCase(Locale.getDefault());
        var destinationColumnPrefix = destinationTable.getColumnPrefix().toLowerCase(Locale.getDefault());

        var fkDestinationColumnName = destinationColumnPrefix + "_" + destinationColumn.getName().toLowerCase(Locale.getDefault());

        var referencesSelf = theFK.getTable() == destinationTable;
        var differingColumnName = !referencesSelf && !theFK.getName().equals(destinationColumnName);
        var fkColumnPrefix = (referencesSelf? "": columnPrefix + "_") + (differingColumnName? "": destinationColumnPrefix + "_");
        var fkSourceColumnName = fkColumnPrefix + theFK.getName().toLowerCase(Locale.getDefault());

        var sqlForFK = getForeignKeyDefinition(theFK, theFK.getTable(), fkSourceColumnName, destinationTable, fkDestinationColumnName);
        var alterSQL = getAlterTableAddForeignKey(theFK, sqlForFK);
        
        if(verbose) {
            System.out.println(alterSQL);
        }
        
        try(var stmt = myConnection.createStatement()) {
            stmt.execute(alterSQL);
        }
    }
    
    void executeForeignKeyUpdates(Set<Column> foreignKeysNeeded) throws Exception {
        for(var theFK: foreignKeysNeeded) {
            createMissingForeignKey(theFK);
        }
    }
    
    void dropExtraForeignKey(CurrentForeignKey cfk)
            throws Exception {
        var alterSQL = getAlterTableDropForeignKey(cfk);

        if(verbose) {
            System.out.println(alterSQL);
        }
        
        try(var stmt = myConnection.createStatement()) {
            stmt.execute(alterSQL);
        }
    }
    
    void dropExtraForeignKeys(Set<CurrentForeignKey> cfks)
            throws Exception {
        for(var cfk: cfks) {
            dropExtraForeignKey(cfk);
        }
    }
    
    void dropExtraIndex(CurrentIndex ci)
            throws Exception {
        var alterSQL = getAlterTableDropIndex(ci);

        if(verbose) {
            System.out.println(alterSQL);
        }
        
        try(var stmt = myConnection.createStatement()) {
            stmt.execute(alterSQL);
        }
    }
    
    void dropExtraIndexes(Set<CurrentIndex> cis)
            throws Exception {
        for(var ci: cis) {
            dropExtraIndex(ci);
        }
    }
    
    void dropExtraColumn(CurrentColumn cc)
            throws Exception {
        var alterSQL = getAlterTableDropColumn(cc);

        if(verbose) {
            System.out.println(alterSQL);
        }
        
        try(var stmt = myConnection.createStatement()) {
            stmt.execute(alterSQL);
        }
    }
    
    void dropExtraColumns(List<CurrentColumn> ccs)
            throws Exception {
        for(var cc: ccs) {
            dropExtraColumn(cc);
        }
    }
    
    void dropExtraTable(CurrentTable ct)
            throws Exception {
        var alterSQL = getDropTable(ct);

        if(verbose) {
            System.out.println(alterSQL);
        }
        
        try(var stmt = myConnection.createStatement()) {
            stmt.execute(alterSQL);
        }
    }
    
    void dropExtraTables(List<CurrentTable> cts)
            throws Exception {
        for(var ct: cts) {
            dropExtraTable(ct);
        }
    }
    
    String getAlterColumn(Column incorrectColumn)
            throws Exception {
        return "ALTER TABLE " + incorrectColumn.getTable().getDbTableName() + " MODIFY " + getColumnDefinition(incorrectColumn);
    }
    
    void modifyIncorrectColumn(Column incorrectColumn)
            throws Exception {
        var alterSQL = getAlterColumn(incorrectColumn);

        if(verbose) {
            System.out.println(alterSQL);
        }
        
        try(var stmt = myConnection.createStatement()) {
            stmt.execute(alterSQL);
        }
    }
    
    void modifyIncorrectColumns(List<Column> incorrectColumns)
            throws Exception {
        for(var incorrectColumn: incorrectColumns) {
             modifyIncorrectColumn(incorrectColumn);
        }
    }
    
    void executeUpdates(DatabaseUpdateTasks neededUpdates) throws Exception {
        dropExtraForeignKeys(neededUpdates.getExtraForeignKeys());
        dropExtraIndexes(neededUpdates.getExtraIndexes());
        dropExtraColumns(neededUpdates.getExtraColumns());
        dropExtraTables(neededUpdates.getExtraTables());
        modifyIncorrectColumns(neededUpdates.getIncorrectColumns());
        executeTableUpdates(neededUpdates.getTables());
        executeColumnUpdates(neededUpdates.getColumns());
        executeIndexUpdates(neededUpdates.getIndexes());
        executeForeignKeyUpdates(neededUpdates.getForeignKeys());
    }
    
}
