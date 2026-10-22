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

package com.echothree.control.user.period.server.command;

import com.echothree.control.user.period.common.form.PeriodFormFactory;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.period.server.PeriodTestEntities;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.period.server.logic.PeriodKindLogic;
import com.echothree.model.control.period.server.logic.PeriodLogic;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.value.LanguageValue;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class PeriodDescriptionCommandsTest {

    private static class SingleCommand extends GetPeriodDescriptionCommand {
        SingleCommand(String kindName) {
            form = PeriodFormFactory.getGetPeriodDescriptionForm();
            form.setPeriodKindName(kindName);
            form.setPeriodName("FY2026");
            form.setLanguageIsoName("en");
        }
    }

    private static class MultipleCommand extends GetPeriodDescriptionsCommand {
        MultipleCommand(String kindName) {
            form = PeriodFormFactory.getGetPeriodDescriptionsForm();
            form.setPeriodKindName(kindName);
        }
    }

    @Test
    public void missingDescriptionReportsKindPeriodAndLanguage() {
        var kind = PeriodTestEntities.kind(1L, "FISCAL");
        var period = PeriodTestEntities.period(2L, kind, "FY2026", null, null);
        var command = new SingleCommand("FISCAL");

        command.periodKindLogic = new PeriodKindLogic() {
            @Override
            public PeriodKind getPeriodKindByName(ExecutionErrorAccumulator errors, String name) {
                assertEquals("FISCAL", name);
                return kind;
            }
        };
        command.periodLogic = new PeriodLogic() {
            @Override
            public Period getPeriodByName(ExecutionErrorAccumulator errors, PeriodKind selectedKind, String name) {
                assertSame(kind, selectedKind);
                assertEquals("FY2026", name);
                return period;
            }
        };
        var language = new Language(new LanguageValue(new LanguagePK(3L), "en", true, 0), EntityPermission.READ_ONLY);
        command.partyControl = new PartyControl() {
            @Override
            public Language getLanguageByIsoName(String name) {
                assertEquals("en", name);
                return language;
            }
        };
        command.periodControl = new PeriodControl() {
            @Override
            public PeriodDescription getPeriodDescription(Period selectedPeriod, Language selectedLanguage) {
                assertSame(period, selectedPeriod);
                assertSame(language, selectedLanguage);
                return null;
            }
        };

        assertNull(command.getEntity());
        var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnknownPeriodDescription.name(), error.getKey());
        assertArrayEquals(new Object[] { "FISCAL", "FY2026", "en" }, error.getValues());
    }

    @Test
    public void failedKindLookupStopsSingleAndMultipleDescriptionLookups() {
        var kindLogic = new PeriodKindLogic() {
            @Override
            public PeriodKind getPeriodKindByName(ExecutionErrorAccumulator errors, String name) {
                errors.addExecutionError(ExecutionErrors.UnknownPeriodKindName.name(), name);
                return null;
            }
        };
        var single = new SingleCommand("MISSING");
        var multiple = new MultipleCommand("MISSING");

        single.periodKindLogic = kindLogic;
        multiple.periodKindLogic = kindLogic;

        assertNull(single.getEntity());
        assertTrue(single.hasExecutionErrors());
        multiple.handleForm();
        assertTrue(multiple.hasExecutionErrors());
        assertNull(multiple.getEntities());
        assertNull(multiple.getTotalEntities());
    }

}
