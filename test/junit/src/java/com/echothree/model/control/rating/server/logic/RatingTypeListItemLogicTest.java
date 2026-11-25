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

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.rating.common.spec.RatingSpecFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.rating.common.pk.RatingTypePK;
import com.echothree.model.data.rating.common.pk.RatingTypeListItemPK;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.model.data.rating.server.value.RatingTypeValue;
import com.echothree.model.data.rating.server.value.RatingTypeListItemValue;
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
public class RatingTypeListItemLogicTest {

    private static class TestLogic extends RatingTypeListItemLogic {
        private final RatingType parentRatingType = new RatingType(new RatingTypeValue(new RatingTypePK(1L), null, null), EntityPermission.READ_ONLY);
        private final RatingTypeListItem listItem = new RatingTypeListItem(
                new RatingTypeListItemValue(new RatingTypeListItemPK(2L), null, null), EntityPermission.READ_WRITE);
        private int itemLookups;
        private boolean missingParent;
        private boolean missingUuid;
        private boolean missingDefault;

        TestLogic() {
            ratingTypeLogic = new RatingTypeLogic() {
                @Override
                public RatingType getRatingTypeByName(ExecutionErrorAccumulator eea, String componentVendorName,
                        String entityTypeName, String ratingTypeName) {
                    assertEquals("VENDOR", componentVendorName);
                    assertEquals("ENTITY", entityTypeName);
                    assertEquals("TYPE", ratingTypeName);
                    if(missingParent) {
                        eea.addExecutionError(ExecutionErrors.UnknownRatingTypeName.name(), componentVendorName, entityTypeName, ratingTypeName);
                    }
                    return missingParent ? null : parentRatingType;
                }
            };
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec universalSpec,
                        String componentVendorName, String... entityTypeNames) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), componentVendorName);
                    assertArrayEquals(new String[] { EntityTypes.RatingTypeListItem.name() }, entityTypeNames);
                    if(missingUuid) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), universalSpec.getUuid());
                    }
                    return null;
                }
            };
            ratingControl = new RatingControl() {
                @Override
                public RatingTypeListItem getRatingTypeListItemByName(RatingType ratingType, String itemName, EntityPermission entityPermission) {
                    assertSame(parentRatingType, ratingType);
                    assertEquals("ITEM", itemName);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    itemLookups++;
                    return listItem;
                }

                @Override
                public RatingTypeListItem getDefaultRatingTypeListItem(RatingType ratingType, EntityPermission entityPermission) {
                    assertSame(parentRatingType, ratingType);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    itemLookups++;
                    return missingDefault ? null : listItem;
                }

                @Override
                public RatingTypeListItem getRatingTypeListItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    itemLookups++;
                    return listItem;
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
    public void requiresCompleteNameKeyWhenDefaultsAreDisabled(int nameMask) {
        var logic = new TestLogic();
        var spec = RatingSpecFactory.getRatingTypeListItemUniversalSpec();
        if((nameMask & 1) != 0) {
            spec.setComponentVendorName("VENDOR");
        }
        if((nameMask & 2) != 0) {
            spec.setEntityTypeName("ENTITY");
        }
        if((nameMask & 4) != 0) {
            spec.setRatingTypeName("TYPE");
        }
        if((nameMask & 8) != 0) {
            spec.setRatingTypeListItemName("ITEM");
        }
        var errors = new DummyExecutionErrorAccumulator();
        var result = logic.getRatingTypeListItemByUniversalSpecForUpdate(errors, spec, false);

        if(nameMask == 15) {
            assertSame(logic.listItem, result);
            assertFalse(errors.hasExecutionErrors());
            assertEquals(1, logic.itemLookups);
        } else {
            assertNull(result);
            assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
            assertEquals(0, logic.itemLookups);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "uuid", "entityRef", "mixed", "both" })
    public void acceptsSingleEntityIdentifierAndRejectsMixedIdentifiers(String identifier) {
        var logic = new TestLogic();
        var spec = RatingSpecFactory.getRatingTypeListItemUniversalSpec();
        if(!"entityRef".equals(identifier)) {
            spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        }
        if("entityRef".equals(identifier) || "both".equals(identifier)) {
            spec.setEntityRef("ECHO_THREE.RatingTypeListItem.2");
        }
        if("mixed".equals(identifier)) {
            spec.setRatingTypeListItemName("ITEM");
        }
        var errors = new DummyExecutionErrorAccumulator();
        var result = logic.getRatingTypeListItemByUniversalSpecForUpdate(errors, spec, false);

        if("uuid".equals(identifier) || "entityRef".equals(identifier)) {
            assertSame(logic.listItem, result);
            assertFalse(errors.hasExecutionErrors());
        } else {
            assertNull(result);
            assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
            assertEquals(0, logic.itemLookups);
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void defaultLookupUsesNamedParentAndReportsItsCompleteKey(boolean missingDefault) {
        var logic = new TestLogic();
        logic.missingDefault = missingDefault;
        var spec = RatingSpecFactory.getRatingTypeListItemUniversalSpec();
        spec.setComponentVendorName("VENDOR");
        spec.setEntityTypeName("ENTITY");
        spec.setRatingTypeName("TYPE");
        var errors = new DummyExecutionErrorAccumulator();
        var result = logic.getRatingTypeListItemByUniversalSpecForUpdate(errors, spec, true);

        if(missingDefault) {
            assertNull(result);
            var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
            assertEquals(ExecutionErrors.UnknownDefaultRatingTypeListItem.name(), error.getKey());
            assertArrayEquals(new Object[] { "VENDOR", "ENTITY", "TYPE" }, error.getValues());
        } else {
            assertSame(logic.listItem, result);
            assertFalse(errors.hasExecutionErrors());
        }
    }

    @Test
    public void missingParentStopsBeforeItemLookup() {
        var logic = new TestLogic();
        logic.missingParent = true;
        var spec = RatingSpecFactory.getRatingTypeListItemUniversalSpec();
        spec.setComponentVendorName("VENDOR");
        spec.setEntityTypeName("ENTITY");
        spec.setRatingTypeName("TYPE");
        spec.setRatingTypeListItemName("ITEM");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getRatingTypeListItemByUniversalSpec(errors, spec, false));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.itemLookups);
    }

    @Test
    public void unknownUuidStopsBeforeItemLookup() {
        var logic = new TestLogic();
        logic.missingUuid = true;
        var spec = RatingSpecFactory.getRatingTypeListItemUniversalSpec();
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getRatingTypeListItemByUniversalSpec(errors, spec, false));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.itemLookups);
    }

}
