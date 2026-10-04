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
import com.echothree.ui.cli.database.util.current.CurrentIndex;
import com.echothree.ui.cli.database.util.current.CurrentTable;
import com.echothree.ui.cli.database.util.definition.Database;
import com.echothree.ui.cli.database.util.definition.DatabaseDefinitionParser;
import com.echothree.ui.cli.database.util.definition.DatabasePhysicalNames;
import com.echothree.ui.cli.database.util.definition.Databases;
import java.sql.Types;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DatabaseUtilitiesTest {

    private Database parseRegressionDatabase() throws Exception {
        var databases = new Databases();
        new DatabaseDefinitionParser(databases).parse("/RegressionDatabase.xml");
        return databases.getDatabase("RegressionSchema");
    }

    @Test
    public void missingForeignKeyIsNotScheduledForDropWhenRecreatingItsIndex() throws Exception {
        var database = parseRegressionDatabase();
        var table = database.getTable("Widgets");
        var foreignKeyColumn = table.getColumn("ParentWidgetId");
        var currentTable = new CurrentTable(DatabasePhysicalNames.tableName(table));
        var currentColumn = new CurrentColumn(currentTable, foreignKeyColumn.getDbColumnName(), Types.BIGINT, 20, true);
        var currentIndex = new CurrentIndex(currentTable, "parent_widget_idx", false, Set.of(currentColumn));
        var utilities = new DatabaseUtilitiesForMySQL(false, database, null, null, null, null, null, null);
        var neededUpdates = new DatabaseUpdateTasks();

        currentTable.addColumn(currentColumn);
        utilities.recreateForeignKeyIfNecessary(currentTable, table, currentIndex, neededUpdates);

        assertTrue(neededUpdates.getExtraForeignKeys().isEmpty());
        assertEquals(Set.of(foreignKeyColumn), neededUpdates.getForeignKeys());
    }

}
