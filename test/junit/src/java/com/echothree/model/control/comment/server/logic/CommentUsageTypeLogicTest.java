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

package com.echothree.model.control.comment.server.logic;

import com.echothree.control.user.comment.common.spec.CommentSpecFactory;
import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.comment.common.pk.CommentTypePK;
import com.echothree.model.data.comment.common.pk.CommentUsageTypePK;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.comment.server.entity.CommentUsageType;
import com.echothree.model.data.comment.server.value.CommentTypeValue;
import com.echothree.model.data.comment.server.value.CommentUsageTypeValue;
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
public class CommentUsageTypeLogicTest {

    private static class TestLogic extends CommentUsageTypeLogic {
        private final CommentType parentCommentType = new CommentType(new CommentTypeValue(new CommentTypePK(1L), null, null), EntityPermission.READ_ONLY);
        private final CommentUsageType listItem = new CommentUsageType(
                new CommentUsageTypeValue(new CommentUsageTypePK(2L), null, null), EntityPermission.READ_WRITE);
        private int itemLookups;
        private boolean missingParent;
        private boolean missingUuid;

        TestLogic() {
            commentTypeLogic = new CommentTypeLogic() {
                @Override
                public CommentType getCommentTypeByName(ExecutionErrorAccumulator eea, String componentVendorName,
                        String entityTypeName, String commentTypeName) {
                    assertEquals("VENDOR", componentVendorName);
                    assertEquals("ENTITY", entityTypeName);
                    assertEquals("TYPE", commentTypeName);
                    if(missingParent) {
                        eea.addExecutionError(ExecutionErrors.UnknownCommentTypeName.name(), componentVendorName, entityTypeName, commentTypeName);
                    }
                    return missingParent ? null : parentCommentType;
                }
            };
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec universalSpec,
                        String componentVendorName, String... entityTypeNames) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), componentVendorName);
                    assertArrayEquals(new String[] { EntityTypes.CommentUsageType.name() }, entityTypeNames);
                    if(missingUuid) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), universalSpec.getUuid());
                    }
                    return null;
                }
            };
            commentControl = new CommentControl() {
                @Override
                public CommentUsageType getCommentUsageTypeByName(CommentType commentType, String itemName, EntityPermission entityPermission) {
                    assertSame(parentCommentType, commentType);
                    assertEquals("ITEM", itemName);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    itemLookups++;
                    return listItem;
                }

                @Override
                public CommentUsageType getCommentUsageTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
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
        var spec = CommentSpecFactory.getCommentUsageTypeUniversalSpec();
        if((nameMask & 1) != 0) {
            spec.setComponentVendorName("VENDOR");
        }
        if((nameMask & 2) != 0) {
            spec.setEntityTypeName("ENTITY");
        }
        if((nameMask & 4) != 0) {
            spec.setCommentTypeName("TYPE");
        }
        if((nameMask & 8) != 0) {
            spec.setCommentUsageTypeName("ITEM");
        }
        var errors = new DummyExecutionErrorAccumulator();
        var result = logic.getCommentUsageTypeByUniversalSpecForUpdate(errors, spec);

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
        var spec = CommentSpecFactory.getCommentUsageTypeUniversalSpec();
        if(!"entityRef".equals(identifier)) {
            spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        }
        if("entityRef".equals(identifier) || "both".equals(identifier)) {
            spec.setEntityRef("ECHO_THREE.CommentUsageType.2");
        }
        if("mixed".equals(identifier)) {
            spec.setCommentUsageTypeName("ITEM");
        }
        var errors = new DummyExecutionErrorAccumulator();
        var result = logic.getCommentUsageTypeByUniversalSpecForUpdate(errors, spec);

        if("uuid".equals(identifier) || "entityRef".equals(identifier)) {
            assertSame(logic.listItem, result);
            assertFalse(errors.hasExecutionErrors());
        } else {
            assertNull(result);
            assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
            assertEquals(0, logic.itemLookups);
        }
    }

    @Test
    public void missingParentStopsBeforeItemLookup() {
        var logic = new TestLogic();
        logic.missingParent = true;
        var spec = CommentSpecFactory.getCommentUsageTypeUniversalSpec();
        spec.setComponentVendorName("VENDOR");
        spec.setEntityTypeName("ENTITY");
        spec.setCommentTypeName("TYPE");
        spec.setCommentUsageTypeName("ITEM");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getCommentUsageTypeByUniversalSpec(errors, spec));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.itemLookups);
    }

    @Test
    public void unknownUuidStopsBeforeItemLookup() {
        var logic = new TestLogic();
        logic.missingUuid = true;
        var spec = CommentSpecFactory.getCommentUsageTypeUniversalSpec();
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getCommentUsageTypeByUniversalSpec(errors, spec));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.itemLookups);
    }

}
