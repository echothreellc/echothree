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

package com.echothree.ui.cli.database.util.jooqgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class JooqGeneratorStrategyTest {

    @Test
    public void tableFieldIdentifiersUseConstantNaming() {
        var strategy = new JooqGeneratorStrategy();

        assertEquals("ALLOCATION_PRIORITY", strategy.columnIdentifier("AllocationPriority"));
        assertEquals("LANGUAGE_ISO_NAME", strategy.columnIdentifier("LanguageIsoName"));
        assertEquals("THRU_TIME", strategy.columnIdentifier("ThruTime"));
    }

}
