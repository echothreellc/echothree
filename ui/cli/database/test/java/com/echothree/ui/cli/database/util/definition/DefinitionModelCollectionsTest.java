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

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class DefinitionModelCollectionsTest {

    @Test
    public void collectionGettersDoNotAllowExternalMutation() throws Exception {
        var database = DatabaseRegressionTestSupport.parseRegressionDatabase();
        var component = database.getComponents().getFirst();
        var table = database.getTable("Widgets");
        var index = table.getPrimaryKey();

        assertThrows(UnsupportedOperationException.class, () -> database.getComponents().clear());
        assertThrows(UnsupportedOperationException.class, () -> database.getTables().clear());
        assertThrows(UnsupportedOperationException.class, () -> component.getTables().clear());
        assertThrows(UnsupportedOperationException.class, () -> table.getColumns().clear());
        assertThrows(UnsupportedOperationException.class, () -> table.getIndexes().clear());
        assertThrows(UnsupportedOperationException.class, () -> table.getForeignKeys().clear());
        assertThrows(UnsupportedOperationException.class, () -> table.getNotForeignKeys().clear());
        assertThrows(UnsupportedOperationException.class, () -> index.getIndexColumns().clear());
    }
}
