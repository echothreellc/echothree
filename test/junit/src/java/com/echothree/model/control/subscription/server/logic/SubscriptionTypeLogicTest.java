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

package com.echothree.model.control.subscription.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.subscription.common.spec.SubscriptionSpecFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.subscription.common.pk.SubscriptionKindDetailPK;
import com.echothree.model.data.subscription.common.pk.SubscriptionKindPK;
import com.echothree.model.data.subscription.common.pk.SubscriptionTypePK;
import com.echothree.model.data.subscription.server.entity.SubscriptionKind;
import com.echothree.model.data.subscription.server.entity.SubscriptionKindDetail;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.model.data.subscription.server.value.SubscriptionKindDetailValue;
import com.echothree.model.data.subscription.server.value.SubscriptionKindValue;
import com.echothree.model.data.subscription.server.value.SubscriptionTypeValue;
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
public class SubscriptionTypeLogicTest {

    private static class TestLogic extends SubscriptionTypeLogic {
        private final SubscriptionType subscriptionType = new SubscriptionType(new SubscriptionTypeValue(new SubscriptionTypePK(1L), null, null), EntityPermission.READ_WRITE);
        private int lookups;

        TestLogic(boolean missingEntity) {
            var parentSubscriptionKind = new SubscriptionKind(new SubscriptionKindValue(new SubscriptionKindPK(2L), null, null), EntityPermission.READ_ONLY) {
                @Override
                public SubscriptionKindDetail getLastDetail() {
                    return new SubscriptionKindDetail(new SubscriptionKindDetailValue(new SubscriptionKindDetailPK(3L),
                            getPrimaryKey(), "KIND", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
                }
            };
            subscriptionKindLogic = new SubscriptionKindLogic() {
                @Override
                public SubscriptionKind getSubscriptionKindByName(ExecutionErrorAccumulator eea, String subscriptionKindName) {
                    assertEquals("KIND", subscriptionKindName);
                    return parentSubscriptionKind;
                }
            };
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec universalSpec,
                        String componentVendorName, String... entityTypeNames) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), componentVendorName);
                    assertArrayEquals(new String[] { EntityTypes.SubscriptionType.name() }, entityTypeNames);
                    if(missingEntity) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), universalSpec.getUuid());
                    }
                    return null;
                }
            };
            subscriptionControl = new SubscriptionControl() {
                @Override
                public SubscriptionKind getDefaultSubscriptionKind() {
                    return parentSubscriptionKind;
                }

                @Override
                public SubscriptionType getDefaultSubscriptionType(SubscriptionKind subscriptionKind, EntityPermission entityPermission) {
                    assertSame(parentSubscriptionKind, subscriptionKind);
                    return null;
                }

                @Override
                public SubscriptionType getSubscriptionTypeByName(SubscriptionKind subscriptionKind, String subscriptionTypeName, EntityPermission entityPermission) {
                    assertEquals("TYPE", subscriptionTypeName);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return subscriptionType;
                }

                @Override
                public SubscriptionType getSubscriptionTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return subscriptionType;
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "name", "uuid", "entityRef" })
    public void resolvesEachIdentifierWithRequestedPermission(String identifier) {
        var logic = new TestLogic(false);
        var spec = SubscriptionSpecFactory.getSubscriptionTypeUniversalSpec();
        switch(identifier) {
            case "name" -> {
                spec.setSubscriptionKindName("KIND");
                spec.setSubscriptionTypeName("TYPE");
            }
            case "uuid" -> spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
            case "entityRef" -> spec.setEntityRef("ECHO_THREE.SubscriptionType.1");
            default -> throw new IllegalArgumentException(identifier);
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertSame(logic.subscriptionType, logic.getSubscriptionTypeByUniversalSpecForUpdate(errors, spec, false));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.lookups);
    }

    @Test
    public void rejectsMissingAndAmbiguousIdentifiersWithoutLookingUpSubscriptionType() {
        var logic = new TestLogic(false);
        var spec = SubscriptionSpecFactory.getSubscriptionTypeUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getSubscriptionTypeByUniversalSpec(errors, spec, false));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());

        spec.setSubscriptionTypeName("TYPE");
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getSubscriptionTypeByUniversalSpec(errors, spec, false));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @Test
    public void missingUuidStopsBeforeSubscriptionTypeLookup() {
        var logic = new TestLogic(true);
        var spec = SubscriptionSpecFactory.getSubscriptionTypeUniversalSpec();
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getSubscriptionTypeByUniversalSpec(errors, spec, false));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    public void rejectsPartialNamesAndNamesCombinedWithUuid(int nameMask) {
        var logic = new TestLogic(false);
        var spec = SubscriptionSpecFactory.getSubscriptionTypeUniversalSpec();
        if((nameMask & 1) != 0) {
            spec.setSubscriptionKindName("KIND");
        }
        if((nameMask & 2) != 0) {
            spec.setSubscriptionTypeName("TYPE");
        }
        if(nameMask == 3) {
            spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getSubscriptionTypeByUniversalSpec(errors, spec, false));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.lookups);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void missingDefaultTypeReportsResolvedSubscriptionKind(boolean specifyKind) {
        var logic = new TestLogic(false);
        var spec = SubscriptionSpecFactory.getSubscriptionTypeUniversalSpec();
        if(specifyKind) {
            spec.setSubscriptionKindName("KIND");
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getSubscriptionTypeByUniversalSpec(errors, spec, true));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnknownDefaultSubscriptionType.name(), error.getKey());
        assertArrayEquals(new Object[] { "KIND" }, error.getValues());
    }

}
