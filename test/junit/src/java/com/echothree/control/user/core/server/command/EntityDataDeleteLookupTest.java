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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.control.BaseCommand;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class EntityDataDeleteLookupTest {

    static Stream<Arguments> values() {
        return EntityEditCompatibilityTest.kinds().flatMap(kind ->
                Stream.of(false, true).map(missing -> Arguments.of(kind, missing)));
    }

    @ParameterizedTest
    @MethodSource("values")
    public void deletesUseDomainLookupsAndMatchEditMissingValueErrors(String kind, boolean missing) throws ReflectiveOperationException {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        fixture.missingValue = missing;
        var commandType = Class.forName(getClass().getPackageName() + ".Delete" + kind + "Command");
        var command = (BaseSimpleCommand<?>)commandType.getConstructor().newInstance();
        var form = (BaseForm)CoreFormFactory.class.getMethod("getDelete" + kind + "Form").invoke(null);
        form.set(((BaseForm)fixture.spec).get());
        fixture.injectCommandDependencies(command);
        EntityEditFixture.inject(command, "form", form);
        EntityEditFixture.inject(command, "party", new Party(fixture.value(PartyValue.class), EntityPermission.READ_ONLY) {
            @Override
            public PartyPK getPrimaryKey() {
                return fixture.updatedBy;
            }
        });
        var execute = BaseCommand.class.getDeclaredMethod("execute");
        execute.setAccessible(true);
        var unused = execute.invoke(command);

        assertEquals(1, fixture.valueLookups);
        assertEquals(1, fixture.readWriteLookups);
        assertEquals(0, fixture.transfers);
        assertEquals(0, fixture.updates);
        assertTrue(fixture.locks.isEmpty());
        if(missing) {
            assertTrue(fixture.deletions.isEmpty());
            var missingValueError = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
            assertEquals("Unknown" + kind, missingValueError.getKey());
            var editFixture = new EntityEditFixture(kind, EditMode.UPDATE);
            editFixture.missingValue = true;
            editFixture.execute();
            assertArrayEquals(editFixture.command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getValues(),
                    missingValueError.getValues());
        } else {
            assertFalse(command.hasExecutionErrors());
            assertEquals(List.of(kind), fixture.deletions);
        }
    }
}

