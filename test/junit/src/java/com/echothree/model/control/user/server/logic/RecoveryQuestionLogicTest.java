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

package com.echothree.model.control.user.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.user.common.spec.UserSpecFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.common.pk.RecoveryQuestionPK;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.model.data.user.server.value.RecoveryQuestionValue;
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
public class RecoveryQuestionLogicTest {

    private static class TestLogic extends RecoveryQuestionLogic {
        private final RecoveryQuestion recoveryQuestion = new RecoveryQuestion(new RecoveryQuestionValue(new RecoveryQuestionPK(1L), null, null), EntityPermission.READ_WRITE);
        private int lookups;

        TestLogic(boolean missingEntity) {
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec universalSpec,
                        String componentVendorName, String... entityTypeNames) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), componentVendorName);
                    assertArrayEquals(new String[] { EntityTypes.RecoveryQuestion.name() }, entityTypeNames);
                    if(missingEntity) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), universalSpec.getUuid());
                    }
                    return null;
                }
            };
            userControl = new UserControl() {
                @Override
                public RecoveryQuestion getRecoveryQuestionByName(String recoveryQuestionName, EntityPermission entityPermission) {
                    assertEquals("FORMAT", recoveryQuestionName);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return recoveryQuestion;
                }

                @Override
                public RecoveryQuestion getRecoveryQuestionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return recoveryQuestion;
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "name", "uuid", "entityRef" })
    public void resolvesEachIdentifierWithRequestedPermission(String identifier) {
        var logic = new TestLogic(false);
        var spec = UserSpecFactory.getRecoveryQuestionUniversalSpec();
        switch(identifier) {
            case "name" -> spec.setRecoveryQuestionName("FORMAT");
            case "uuid" -> spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
            case "entityRef" -> spec.setEntityRef("ECHO_THREE.RecoveryQuestion.1");
            default -> throw new IllegalArgumentException(identifier);
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertSame(logic.recoveryQuestion, logic.getRecoveryQuestionByUniversalSpecForUpdate(errors, spec));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.lookups);
    }

    @Test
    public void rejectsMissingAndAmbiguousIdentifiersWithoutLookingUpRecoveryQuestion() {
        var logic = new TestLogic(false);
        var spec = UserSpecFactory.getRecoveryQuestionUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getRecoveryQuestionByUniversalSpec(errors, spec));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());

        spec.setRecoveryQuestionName("FORMAT");
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getRecoveryQuestionByUniversalSpec(errors, spec));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @Test
    public void missingUuidStopsBeforeRecoveryQuestionLookup() {
        var logic = new TestLogic(true);
        var spec = UserSpecFactory.getRecoveryQuestionUniversalSpec();
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getRecoveryQuestionByUniversalSpec(errors, spec));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

}
