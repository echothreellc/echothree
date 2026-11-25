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

package com.echothree.model.control.party.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.party.common.spec.PartySpecFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PersonalTitlePK;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.party.server.value.PersonalTitleValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class PersonalTitleLogicTest {

    private static class TestLogic extends PersonalTitleLogic {
        private final PersonalTitle personalTitle = new PersonalTitle(new PersonalTitleValue(new PersonalTitlePK(1L), null, null), EntityPermission.READ_WRITE);
        private int lookups;
        private boolean missingDefault;

        TestLogic(boolean missingEntity) {
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec universalSpec,
                        String componentVendorName, String... entityTypeNames) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), componentVendorName);
                    assertArrayEquals(new String[] { EntityTypes.PersonalTitle.name() }, entityTypeNames);
                    if(missingEntity) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), universalSpec.getUuid());
                    }
                    return null;
                }
            };
            partyControl = new PartyControl() {
                @Override
                public PersonalTitle getDefaultPersonalTitle(EntityPermission entityPermission) {
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return missingDefault ? null : personalTitle;
                }

                @Override
                public PersonalTitle convertPersonalTitleIdToEntity(String personalTitleId, EntityPermission entityPermission) {
                    assertEquals("1", personalTitleId);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return personalTitle;
                }

                @Override
                public PersonalTitle getPersonalTitleByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return personalTitle;
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "id", "uuid", "entityRef" })
    public void resolvesEachIdentifierWithRequestedPermission(String identifier) {
        var logic = new TestLogic(false);
        var spec = PartySpecFactory.getPersonalTitleUniversalSpec();
        switch(identifier) {
            case "id" -> spec.setPersonalTitleId("1");
            case "uuid" -> spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
            case "entityRef" -> spec.setEntityRef("ECHO_THREE.PersonalTitle.1");
            default -> throw new IllegalArgumentException(identifier);
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertSame(logic.personalTitle, logic.getPersonalTitleByUniversalSpecForUpdate(errors, spec, false));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.lookups);
    }

    @Test
    public void rejectsMissingAndAmbiguousIdentifiersWithoutLookingUpPersonalTitle() {
        var logic = new TestLogic(false);
        var spec = PartySpecFactory.getPersonalTitleUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPersonalTitleByUniversalSpec(errors, spec, false));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());

        spec.setPersonalTitleId("1");
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPersonalTitleByUniversalSpec(errors, spec, false));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @Test
    public void missingUuidStopsBeforePersonalTitleLookup() {
        var logic = new TestLogic(true);
        var spec = PartySpecFactory.getPersonalTitleUniversalSpec();
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getPersonalTitleByUniversalSpec(errors, spec, false));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void resolvesDefaultOrReportsMissingDefaultWhenEnabled(boolean missingDefault) {
        var logic = new TestLogic(false);
        logic.missingDefault = missingDefault;
        var spec = PartySpecFactory.getPersonalTitleUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();
        var result = logic.getPersonalTitleByUniversalSpecForUpdate(errors, spec, true);

        if(missingDefault) {
            assertNull(result);
            assertEquals(ExecutionErrors.UnknownDefaultPersonalTitle.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        } else {
            assertSame(logic.personalTitle, result);
            assertFalse(errors.hasExecutionErrors());
        }
        assertEquals(1, logic.lookups);
    }

}
