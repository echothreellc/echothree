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

package com.echothree.model.control.rating.server.logic;

import com.echothree.control.user.rating.common.spec.RatingSpecFactory;
import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.value.EntityTypeValue;
import com.echothree.model.data.rating.common.pk.RatingTypePK;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.value.RatingTypeValue;
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
public class RatingTypeLogicTest {

    private static class TestLogic extends RatingTypeLogic {
        private final RatingType ratingType = new RatingType(new RatingTypeValue(new RatingTypePK(1L), null, null), EntityPermission.READ_WRITE);
        private int lookups;

        TestLogic(boolean missingEntity) {
            var parentEntityType = new EntityType(new EntityTypeValue(new EntityTypePK(2L), null, null), EntityPermission.READ_ONLY);
            entityTypeLogic = new EntityTypeLogic() {
                @Override
                public EntityType getEntityTypeByName(ExecutionErrorAccumulator eea, String componentVendorName, String entityTypeName) {
                    assertEquals("VENDOR", componentVendorName);
                    assertEquals("ENTITY", entityTypeName);
                    return parentEntityType;
                }
            };
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec universalSpec,
                        String componentVendorName, String... entityTypeNames) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), componentVendorName);
                    assertArrayEquals(new String[] { EntityTypes.RatingType.name() }, entityTypeNames);
                    if(missingEntity) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), universalSpec.getUuid());
                    }
                    return null;
                }
            };
            ratingControl = new RatingControl() {
                @Override
                public RatingType getRatingTypeByName(EntityType entityType, String ratingTypeName, EntityPermission entityPermission) {
                    assertEquals("RATING", ratingTypeName);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return ratingType;
                }

                @Override
                public RatingType getRatingTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return ratingType;
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "name", "uuid", "entityRef" })
    public void resolvesEachIdentifierWithRequestedPermission(String identifier) {
        var logic = new TestLogic(false);
        var spec = RatingSpecFactory.getRatingTypeUniversalSpec();
        switch(identifier) {
            case "name" -> {
                spec.setComponentVendorName("VENDOR");
                spec.setEntityTypeName("ENTITY");
                spec.setRatingTypeName("RATING");
            }
            case "uuid" -> spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
            case "entityRef" -> spec.setEntityRef("ECHO_THREE.RatingType.1");
            default -> throw new IllegalArgumentException(identifier);
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertSame(logic.ratingType, logic.getRatingTypeByUniversalSpecForUpdate(errors, spec));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.lookups);
    }

    @Test
    public void rejectsMissingAndAmbiguousIdentifiersWithoutLookingUpRatingType() {
        var logic = new TestLogic(false);
        var spec = RatingSpecFactory.getRatingTypeUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getRatingTypeByUniversalSpec(errors, spec));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());

        spec.setRatingTypeName("RATING");
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getRatingTypeByUniversalSpec(errors, spec));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @Test
    public void missingUuidStopsBeforeRatingTypeLookup() {
        var logic = new TestLogic(true);
        var spec = RatingSpecFactory.getRatingTypeUniversalSpec();
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getRatingTypeByUniversalSpec(errors, spec));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7 })
    public void rejectsPartialNamesAndNamesCombinedWithUuid(int nameMask) {
        var logic = new TestLogic(false);
        var spec = RatingSpecFactory.getRatingTypeUniversalSpec();
        if((nameMask & 1) != 0) {
            spec.setComponentVendorName("VENDOR");
        }
        if((nameMask & 2) != 0) {
            spec.setEntityTypeName("ENTITY");
        }
        if((nameMask & 4) != 0) {
            spec.setRatingTypeName("RATING");
        }
        if(nameMask == 7) {
            spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getRatingTypeByUniversalSpec(errors, spec));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.lookups);
    }

}

