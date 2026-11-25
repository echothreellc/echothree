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

package com.echothree.model.control.communication.server.logic;

import com.echothree.control.user.communication.common.spec.CommunicationSpecFactory;
import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.communication.common.pk.CommunicationEventPurposePK;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurpose;
import com.echothree.model.data.communication.server.value.CommunicationEventPurposeValue;
import com.echothree.model.data.core.server.entity.EntityInstance;
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
public class CommunicationEventPurposeLogicTest {

    private static class TestLogic extends CommunicationEventPurposeLogic {
        private final CommunicationEventPurpose communicationEventPurpose = new CommunicationEventPurpose(new CommunicationEventPurposeValue(new CommunicationEventPurposePK(1L), null, null), EntityPermission.READ_WRITE);
        private int lookups;
        private boolean missingDefault;

        TestLogic(boolean missingEntity) {
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec universalSpec,
                        String componentVendorName, String... entityTypeNames) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), componentVendorName);
                    assertArrayEquals(new String[] { EntityTypes.CommunicationEventPurpose.name() }, entityTypeNames);
                    if(missingEntity) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), universalSpec.getUuid());
                    }
                    return null;
                }
            };
            communicationControl = new CommunicationControl() {
                @Override
                public CommunicationEventPurpose getDefaultCommunicationEventPurpose(EntityPermission entityPermission) {
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return missingDefault ? null : communicationEventPurpose;
                }

                @Override
                public CommunicationEventPurpose getCommunicationEventPurposeByName(String communicationEventPurposeName, EntityPermission entityPermission) {
                    assertEquals("FORMAT", communicationEventPurposeName);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return communicationEventPurpose;
                }

                @Override
                public CommunicationEventPurpose getCommunicationEventPurposeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return communicationEventPurpose;
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "name", "uuid", "entityRef" })
    public void resolvesEachIdentifierWithRequestedPermission(String identifier) {
        var logic = new TestLogic(false);
        var spec = CommunicationSpecFactory.getCommunicationEventPurposeUniversalSpec();
        switch(identifier) {
            case "name" -> spec.setCommunicationEventPurposeName("FORMAT");
            case "uuid" -> spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
            case "entityRef" -> spec.setEntityRef("ECHO_THREE.CommunicationEventPurpose.1");
            default -> throw new IllegalArgumentException(identifier);
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertSame(logic.communicationEventPurpose, logic.getCommunicationEventPurposeByUniversalSpecForUpdate(errors, spec, false));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.lookups);
    }

    @Test
    public void rejectsMissingAndAmbiguousIdentifiersWithoutLookingUpCommunicationEventPurpose() {
        var logic = new TestLogic(false);
        var spec = CommunicationSpecFactory.getCommunicationEventPurposeUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getCommunicationEventPurposeByUniversalSpec(errors, spec, false));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());

        spec.setCommunicationEventPurposeName("FORMAT");
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getCommunicationEventPurposeByUniversalSpec(errors, spec, false));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @Test
    public void missingUuidStopsBeforeCommunicationEventPurposeLookup() {
        var logic = new TestLogic(true);
        var spec = CommunicationSpecFactory.getCommunicationEventPurposeUniversalSpec();
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getCommunicationEventPurposeByUniversalSpec(errors, spec, false));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void resolvesDefaultOrReportsMissingDefaultWhenEnabled(boolean missingDefault) {
        var logic = new TestLogic(false);
        logic.missingDefault = missingDefault;
        var spec = CommunicationSpecFactory.getCommunicationEventPurposeUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();
        var result = logic.getCommunicationEventPurposeByUniversalSpecForUpdate(errors, spec, true);

        if(missingDefault) {
            assertNull(result);
            assertEquals(ExecutionErrors.UnknownDefaultCommunicationEventPurpose.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        } else {
            assertSame(logic.communicationEventPurpose, result);
            assertFalse(errors.hasExecutionErrors());
        }
        assertEquals(1, logic.lookups);
    }

}
