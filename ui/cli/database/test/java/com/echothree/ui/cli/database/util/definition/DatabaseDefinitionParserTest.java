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

package com.echothree.ui.cli.database.util.definition;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DatabaseDefinitionParserTest {

    @Test
    public void parsesAValidDefinition() throws Exception {
        var database = DatabaseRegressionTestSupport.parseRegressionDatabase();

        assertEquals("RegressionSchema", database.getName());
        assertEquals(2, database.getTables().size());
        assertEquals("ParentWidgetId", database.getTable("Widgets").getForeignKeys().getFirst().getName());
    }

    @Test
    public void reportsAMissingDefinitionResource() {
        var exception = assertThrows(IOException.class,
                () -> new DatabaseDefinitionParser(new Databases()).parse("/MissingDatabaseDefinition.xml"));

        assertTrue(exception.getMessage().contains("MissingDatabaseDefinition.xml"));
    }

    @Test
    public void rejectsAnExternalDtd() {
        var exception = assertThrows(Exception.class,
                () -> new DatabaseDefinitionParser(new Databases()).parse("/ExternalDtdDatabase.xml"));

        assertTrue(exception.getMessage().contains("External entity resolution is not permitted"));
    }

    @Test
    public void rejectsAnExternalFileEntity() {
        assertThrows(Exception.class,
                () -> new DatabaseDefinitionParser(new Databases()).parse("/ExternalEntityDatabase.xml"));
    }

}
