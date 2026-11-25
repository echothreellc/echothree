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
import com.echothree.model.data.party.common.pk.NameSuffixPK;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.value.NameSuffixValue;
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
public class NameSuffixLogicTest {

    private static class TestLogic extends NameSuffixLogic {
        private final NameSuffix nameSuffix = new NameSuffix(new NameSuffixValue(new NameSuffixPK(1L), null, null), EntityPermission.READ_WRITE);
        private int lookups;
        private boolean missingDefault;

        TestLogic(boolean missingEntity) {
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec universalSpec,
                        String componentVendorName, String... entityTypeNames) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), componentVendorName);
                    assertArrayEquals(new String[] { EntityTypes.NameSuffix.name() }, entityTypeNames);
                    if(missingEntity) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), universalSpec.getUuid());
                    }
                    return null;
                }
            };
            partyControl = new PartyControl() {
                @Override
                public NameSuffix getDefaultNameSuffix(EntityPermission entityPermission) {
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return missingDefault ? null : nameSuffix;
                }

                @Override
                public NameSuffix convertNameSuffixIdToEntity(String nameSuffixId, EntityPermission entityPermission) {
                    assertEquals("1", nameSuffixId);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return nameSuffix;
                }

                @Override
                public NameSuffix getNameSuffixByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return nameSuffix;
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "id", "uuid", "entityRef" })
    public void resolvesEachIdentifierWithRequestedPermission(String identifier) {
        var logic = new TestLogic(false);
        var spec = PartySpecFactory.getNameSuffixUniversalSpec();
        switch(identifier) {
            case "id" -> spec.setNameSuffixId("1");
            case "uuid" -> spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
            case "entityRef" -> spec.setEntityRef("ECHO_THREE.NameSuffix.1");
            default -> throw new IllegalArgumentException(identifier);
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertSame(logic.nameSuffix, logic.getNameSuffixByUniversalSpecForUpdate(errors, spec, false));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.lookups);
    }

    @Test
    public void rejectsMissingAndAmbiguousIdentifiersWithoutLookingUpNameSuffix() {
        var logic = new TestLogic(false);
        var spec = PartySpecFactory.getNameSuffixUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getNameSuffixByUniversalSpec(errors, spec, false));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());

        spec.setNameSuffixId("1");
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getNameSuffixByUniversalSpec(errors, spec, false));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @Test
    public void missingUuidStopsBeforeNameSuffixLookup() {
        var logic = new TestLogic(true);
        var spec = PartySpecFactory.getNameSuffixUniversalSpec();
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getNameSuffixByUniversalSpec(errors, spec, false));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void resolvesDefaultOrReportsMissingDefaultWhenEnabled(boolean missingDefault) {
        var logic = new TestLogic(false);
        logic.missingDefault = missingDefault;
        var spec = PartySpecFactory.getNameSuffixUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();
        var result = logic.getNameSuffixByUniversalSpecForUpdate(errors, spec, true);

        if(missingDefault) {
            assertNull(result);
            assertEquals(ExecutionErrors.UnknownDefaultNameSuffix.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        } else {
            assertSame(logic.nameSuffix, result);
            assertFalse(errors.hasExecutionErrors());
        }
        assertEquals(1, logic.lookups);
    }

}
