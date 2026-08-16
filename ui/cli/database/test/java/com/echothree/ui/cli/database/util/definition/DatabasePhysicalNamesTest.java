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

import java.util.Locale;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DatabasePhysicalNamesTest {

    @Test
    public void constructsEveryPhysicalNameFromOnePolicy() throws Exception {
        var database = DatabaseRegressionTestSupport.parseRegressionDatabase();
        var widgets = database.getTable("Widgets");
        var widgetDetails = database.getTable("WidgetDetails");
        var widgetId = widgets.getColumn("WidgetId");
        var parentWidgetId = widgets.getColumn("ParentWidgetId");
        var detailWidgetId = widgetDetails.getColumn("WidgetId");

        assertEquals("widgets", DatabasePhysicalNames.tableName(widgets));
        assertEquals("wdg_widgetid", DatabasePhysicalNames.columnName(widgetId));
        assertEquals("wdg_parentwidgetid", DatabasePhysicalNames.columnName(parentWidgetId));
        assertEquals("custom_parentwidgetid", DatabasePhysicalNames.columnName(parentWidgetId, "custom"));
        assertEquals("wdgdt_wdg_widgetid", DatabasePhysicalNames.columnName(detailWidgetId));
        assertEquals("PRIMARY", DatabasePhysicalNames.indexName(widgets.getPrimaryKey()));
        assertEquals("widgetname_idx", DatabasePhysicalNames.indexName(widgets.getIndexes().get(1)));
        assertEquals("wdgdt_wdg_widgetid_fk", DatabasePhysicalNames.foreignKeyName(detailWidgetId));
    }

    @Test
    public void schemaNamesAreIndependentOfDefaultLocale() {
        var originalLocale = Locale.getDefault();

        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            assertEquals("identifiers", DatabasePhysicalNames.tableName("IDENTIFIERS"));
            assertEquals("idx_identifier", DatabasePhysicalNames.columnName("idx", "IDENTIFIER"));
        } finally {
            Locale.setDefault(originalLocale);
        }
    }

}
