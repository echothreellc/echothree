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

package com.echothree.model.control.returnpolicy.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.returnpolicy.common.spec.ReturnPolicySpecFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.returnpolicy.common.pk.ReturnKindDetailPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnKindPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnReasonPK;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKindDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.model.data.returnpolicy.server.value.ReturnKindDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnKindValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnReasonValue;
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
public class ReturnReasonLogicTest {

    private static class TestLogic extends ReturnReasonLogic {
        private final ReturnReason returnReason = new ReturnReason(new ReturnReasonValue(new ReturnReasonPK(1L), null, null), EntityPermission.READ_WRITE);
        private int lookups;
        private boolean missingDefaultKind;
        private boolean missingDefaultReason;

        TestLogic(boolean missingEntity) {
            var parentReturnKind = new ReturnKind(new ReturnKindValue(new ReturnKindPK(2L), null, null), EntityPermission.READ_ONLY) {
                @Override
                public ReturnKindDetail getLastDetail() {
                    return new ReturnKindDetail(new ReturnKindDetailValue(new ReturnKindDetailPK(3L), getPrimaryKey(),
                            "KIND", new com.echothree.model.data.sequence.common.pk.SequenceTypePK(4L), false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
                }
            };
            returnKindLogic = new ReturnKindLogic() {
                @Override
                public ReturnKind getReturnKindByName(ExecutionErrorAccumulator eea, String returnKindName) {
                    assertEquals("KIND", returnKindName);
                    return parentReturnKind;
                }
            };
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec universalSpec,
                        String componentVendorName, String... entityTypeNames) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), componentVendorName);
                    assertArrayEquals(new String[] { EntityTypes.ReturnReason.name() }, entityTypeNames);
                    if(missingEntity) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), universalSpec.getUuid());
                    }
                    return null;
                }
            };
            returnPolicyControl = new ReturnPolicyControl() {
                @Override
                public ReturnKind getDefaultReturnKind() {
                    return missingDefaultKind ? null : parentReturnKind;
                }

                @Override
                public ReturnReason getDefaultReturnReason(ReturnKind returnKind, EntityPermission entityPermission) {
                    assertSame(parentReturnKind, returnKind);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return missingDefaultReason ? null : returnReason;
                }

                @Override
                public ReturnReason getReturnReasonByName(ReturnKind returnKind, String returnReasonName, EntityPermission entityPermission) {
                    assertEquals("REASON", returnReasonName);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return returnReason;
                }

                @Override
                public ReturnReason getReturnReasonByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return returnReason;
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "name", "uuid", "entityRef" })
    public void resolvesEachIdentifierWithRequestedPermission(String identifier) {
        var logic = new TestLogic(false);
        var spec = ReturnPolicySpecFactory.getReturnReasonUniversalSpec();
        switch(identifier) {
            case "name" -> {
                spec.setReturnKindName("KIND");
                spec.setReturnReasonName("REASON");
            }
            case "uuid" -> spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
            case "entityRef" -> spec.setEntityRef("ECHO_THREE.ReturnReason.1");
            default -> throw new IllegalArgumentException(identifier);
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertSame(logic.returnReason, logic.getReturnReasonByUniversalSpecForUpdate(errors, spec, false));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.lookups);
    }

    @Test
    public void rejectsMissingAndAmbiguousIdentifiersWithoutLookingUpReturnReason() {
        var logic = new TestLogic(false);
        var spec = ReturnPolicySpecFactory.getReturnReasonUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getReturnReasonByUniversalSpec(errors, spec, false));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());

        spec.setReturnReasonName("REASON");
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getReturnReasonByUniversalSpec(errors, spec, false));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @Test
    public void missingUuidStopsBeforeReturnReasonLookup() {
        var logic = new TestLogic(true);
        var spec = ReturnPolicySpecFactory.getReturnReasonUniversalSpec();
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getReturnReasonByUniversalSpec(errors, spec, false));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    public void rejectsPartialNamesAndNamesCombinedWithUuid(int nameMask) {
        var logic = new TestLogic(false);
        var spec = ReturnPolicySpecFactory.getReturnReasonUniversalSpec();
        if((nameMask & 1) != 0) {
            spec.setReturnKindName("KIND");
        }
        if((nameMask & 2) != 0) {
            spec.setReturnReasonName("REASON");
        }
        if(nameMask == 3) {
            spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getReturnReasonByUniversalSpec(errors, spec, false));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.lookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3 })
    public void resolvesDefaultsOnlyWhenAllowed(int nameMask) {
        var logic = new TestLogic(false);
        var spec = ReturnPolicySpecFactory.getReturnReasonUniversalSpec();
        if((nameMask & 1) != 0) {
            spec.setReturnKindName("KIND");
        }
        if((nameMask & 2) != 0) {
            spec.setReturnReasonName("REASON");
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertSame(logic.returnReason, logic.getReturnReasonByUniversalSpecForUpdate(errors, spec, true));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.lookups);
    }

    @Test
    public void reportsMissingDefaultKindBeforeLookingUpReason() {
        var logic = new TestLogic(false);
        logic.missingDefaultKind = true;
        var spec = ReturnPolicySpecFactory.getReturnReasonUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getReturnReasonByUniversalSpecForUpdate(errors, spec, true));
        assertEquals(ExecutionErrors.UnknownDefaultReturnKind.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.lookups);
    }

    @Test
    public void missingDefaultReasonIncludesResolvedKindName() {
        var logic = new TestLogic(false);
        logic.missingDefaultReason = true;
        var spec = ReturnPolicySpecFactory.getReturnReasonUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getReturnReasonByUniversalSpecForUpdate(errors, spec, true));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnknownDefaultReturnReason.name(), error.getKey());
        assertArrayEquals(new Object[] { "KIND" }, error.getValues());
    }

}
