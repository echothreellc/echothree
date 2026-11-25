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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.edit.ReturnPolicyEditFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnPolicySpecFactory;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnReasonTypeTransfer;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.returnpolicy.server.logic.ReturnReasonTypeLogic;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnReasonPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnReasonTypePK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnTypePK;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonType;
import com.echothree.model.data.returnpolicy.server.value.ReturnReasonValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnReasonTypeValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EditReturnReasonTypeCommandTest {

    private static class TestCommand extends EditReturnReasonTypeCommand {
        private final ReturnReason returnReason = new ReturnReason(new ReturnReasonValue(new ReturnReasonPK(1L), null, null), EntityPermission.READ_ONLY);
        private final ReturnReasonType relationship = new ReturnReasonType(new ReturnReasonTypeValue(new ReturnReasonTypePK(2L),
                returnReason.getPrimaryKey(), new ReturnTypePK(3L), true, 4, 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE) {
            @Override
            public ReturnReason getReturnReason() {
                return returnReason;
            }
        };
        private boolean updated;
        private boolean transferred;

        TestCommand(EditMode requestedEditMode, boolean missingRelationship) {
            editMode = requestedEditMode;
            spec = ReturnPolicySpecFactory.getReturnReasonTypeSpec();
            spec.setReturnKindName("KIND");
            spec.setReturnReasonName("REASON");
            spec.setReturnTypeName("TYPE");
            edit = ReturnPolicyEditFactory.getReturnReasonTypeEdit();
            edit.setIsDefault("false");
            edit.setSortOrder("9");
            returnReasonTypeLogic = new ReturnReasonTypeLogic() {
                @Override
                public ReturnReasonType getReturnReasonType(ExecutionErrorAccumulator eea, String returnKindName,
                        String returnReasonName, String returnTypeName, EntityPermission entityPermission) {
                    assertEquals("KIND", returnKindName);
                    assertEquals("REASON", returnReasonName);
                    assertEquals("TYPE", returnTypeName);
                    assertEquals(requestedEditMode == EditMode.UPDATE ? EntityPermission.READ_WRITE : EntityPermission.READ_ONLY, entityPermission);
                    if(missingRelationship) {
                        eea.addExecutionError(ExecutionErrors.UnknownReturnReasonType.name(), returnKindName, returnReasonName, returnTypeName);
                    }
                    return missingRelationship ? null : relationship;
                }
            };
            returnPolicyControl = new ReturnPolicyControl() {
                @Override
                public void updateReturnReasonTypeFromValue(ReturnReasonTypeValue value, BasePK updatedBy) {
                    assertFalse(value.getIsDefault());
                    assertEquals(9, value.getSortOrder());
                    assertEquals(relationship.getPrimaryKey(), value.getPrimaryKey());
                    assertEquals(returnReason.getPrimaryKey(), value.getReturnReasonPK());
                    updated = true;
                }

                @Override
                public ReturnReasonTypeTransfer getReturnReasonTypeTransfer(UserVisit userVisit, ReturnReasonType selectedRelationship) {
                    assertSame(relationship, selectedRelationship);
                    transferred = true;
                    return null;
                }
            };
        }

        @Override
        public UserVisit getUserVisit() {
            return null;
        }

        @Override
        public PartyPK getPartyPK() {
            return null;
        }

        @Override
        public EntityLockTransfer getEntityLockTransfer(BaseEntity lockEntity) {
            assertSame(returnReason, lockEntity);
            return null;
        }

        void assertInvalidEditResult(boolean missingRelationship) {
            edit.setSortOrder("invalid");
            var validationResult = validate();
            assertTrue(validationResult.getValidationMessages().containsKey("SortOrder", Validator.ERROR_INVALID_FORMAT));
            assertNotNull(getBaseResultAfterErrors());
            assertEquals(missingRelationship, hasExecutionErrors());
            assertEquals(!missingRelationship, transferred);
        }
    }

    @ParameterizedTest
    @EnumSource(EditMode.class)
    public void usesRequestedPermissionAndLocksReturnReason(EditMode editMode) {
        var command = new TestCommand(editMode, false);

        assertSame(command.relationship, command.getEntity(command.getResult()));
        assertSame(command.returnReason, command.getLockEntity(command.relationship));
    }

    @Test
    public void copiesEditValuesAndUpdatesClone() {
        var command = new TestCommand(EditMode.UPDATE, false);
        var edit = command.getEdit();
        command.doLock(edit, command.relationship);
        assertEquals("true", edit.getIsDefault());
        assertEquals("4", edit.getSortOrder());

        command.doUpdate(command.relationship);
        assertTrue(command.updated);
        assertTrue(command.relationship.getIsDefault());
        assertEquals(4, command.relationship.getSortOrder());
    }

    @Test
    public void invalidEditRetainsRelationshipTransferAndOwningReasonLock() {
        new TestCommand(EditMode.UPDATE, false).assertInvalidEditResult(false);
    }

    @Test
    public void invalidEditWithMissingRelationshipStopsBeforeTransferAndLock() {
        new TestCommand(EditMode.UPDATE, true).assertInvalidEditResult(true);
    }

}
