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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.edit.OfferEditFactory;
import com.echothree.control.user.offer.common.result.EditUseTypeDescriptionResult;
import com.echothree.control.user.offer.common.spec.OfferSpecFactory;
import com.echothree.control.user.offer.common.spec.UseTypeDescriptionSpec;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.offer.common.transfer.UseTypeDescriptionTransfer;
import com.echothree.model.control.offer.server.control.UseTypeControl;
import com.echothree.model.control.offer.server.logic.UseTypeLogic;
import com.echothree.model.data.offer.common.pk.UseTypeDescriptionPK;
import com.echothree.model.data.offer.common.pk.UseTypePK;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.entity.UseTypeDescription;
import com.echothree.model.data.offer.server.value.UseTypeDescriptionValue;
import com.echothree.model.data.offer.server.value.UseTypeValue;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EditUseTypeDescriptionCommandTest {

    private static class TestCommand extends EditUseTypeDescriptionCommand {
        private final UseType useType = new UseType(new UseTypeValue(new UseTypePK(1L), null, null), EntityPermission.READ_ONLY);
        private final UseTypeDescription description = new UseTypeDescription(new UseTypeDescriptionValue(
                new UseTypeDescriptionPK(3L), useType.getPrimaryKey(), new LanguagePK(2L), "Original", 0L, Long.MAX_VALUE),
                EntityPermission.READ_WRITE) {
            @Override
            public UseType getUseType() {
                return useType;
            }
        };
        private final UseTypeDescriptionTransfer transfer = new UseTypeDescriptionTransfer(null, null, "Original");
        private final PartyPK updatedBy = new PartyPK(4L);
        private boolean lockAllowed = true;
        private boolean missingDescription;
        private boolean failUpdate;
        private int locks;
        private int unlocks;
        private int updates;

        TestCommand(EditMode mode) {
            editMode = mode;
            spec = OfferSpecFactory.getUseTypeDescriptionSpec();
            spec.setUseTypeName("TYPE");
            spec.setLanguageIsoName("en");
            edit = OfferEditFactory.getUseTypeDescriptionEdit();
            edit.setDescription("Updated");
            useTypeLogic = new UseTypeLogic() {
                @Override
                public UseTypeDescription getUseTypeDescriptionBySpec(ExecutionErrorAccumulator eea,
                        UseTypeDescriptionSpec descriptionSpec, EntityPermission permission) {
                    assertEquals("TYPE", descriptionSpec.getUseTypeName());
                    assertEquals("en", descriptionSpec.getLanguageIsoName());
                    assertEquals(mode == EditMode.UPDATE ? EntityPermission.READ_WRITE : EntityPermission.READ_ONLY, permission);
                    if(missingDescription) {
                        eea.addExecutionError(ExecutionErrors.UnknownUseTypeDescription.name(), "TYPE", "en");
                    }
                    return missingDescription ? null : description;
                }
            };
            useTypeControl = new UseTypeControl() {
                @Override
                public UseTypeDescriptionTransfer getUseTypeDescriptionTransfer(UserVisit userVisit, UseTypeDescription entity) {
                    assertSame(description, entity);
                    return transfer;
                }

                @Override
                public void updateUseTypeDescriptionFromValue(UseTypeDescriptionValue value, BasePK changedBy) {
                    assertSame(updatedBy, changedBy);
                    assertEquals(description.getPrimaryKey(), value.getPrimaryKey());
                    assertEquals(useType.getPrimaryKey(), value.getUseTypePK());
                    assertEquals(new LanguagePK(2L), value.getLanguagePK());
                    assertEquals("Updated", value.getDescription());
                    assertEquals("Original", description.getDescription());
                    updates++;
                    if(failUpdate) {
                        throw new IllegalStateException("Update failed");
                    }
                }
            };
        }

        @Override
        public UserVisit getUserVisit() {
            return null;
        }

        @Override
        public PartyPK getPartyPK() {
            return updatedBy;
        }

        @Override
        public boolean lockEntity(BaseEntity entity) {
            assertSame(useType, entity);
            locks++;
            return lockAllowed;
        }

        @Override
        public boolean lockEntityForUpdate(BaseEntity entity) {
            return lockEntity(entity);
        }

        @Override
        public boolean unlockEntity(BaseEntity entity) {
            assertSame(useType, entity);
            unlocks++;
            return true;
        }

        @Override
        public EntityLockTransfer getEntityLockTransfer(BaseEntity entity) {
            assertSame(useType, entity);
            return null;
        }

        EditUseTypeDescriptionResult executeEdit() {
            return (EditUseTypeDescriptionResult)execute();
        }
    }

    @Test
    public void lockPopulatesEditAndTransferAndLocksOwner() {
        var command = new TestCommand(EditMode.LOCK);
        var result = command.executeEdit();
        assertEquals("Original", result.getEdit().getDescription());
        assertSame(command.transfer, result.getUseTypeDescription());
        assertEquals(1, command.locks);
        assertEquals(0, command.unlocks);
        assertEquals(0, command.updates);
        assertFalse(command.hasExecutionErrors());
    }

    @ParameterizedTest
    @EnumSource(value = EditMode.class, names = { "LOCK", "UPDATE" })
    public void failedLocksReportExpectedErrorAndCurrentTransfer(EditMode mode) {
        var command = new TestCommand(mode);
        command.lockAllowed = false;
        var result = command.executeEdit();
        assertNull(result.getEdit());
        assertSame(command.transfer, result.getUseTypeDescription());
        assertEquals(mode == EditMode.LOCK ? "EntityLockFailed" : "EntityLockStale",
                command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, command.updates);
        assertEquals(0, command.unlocks);
    }

    @Test
    public void abandonUnlocksOwnerWithoutChangingDescription() {
        var command = new TestCommand(EditMode.ABANDON);
        var result = command.executeEdit();
        assertNull(result.getEdit());
        assertNull(result.getUseTypeDescription());
        assertEquals(0, command.locks);
        assertEquals(1, command.unlocks);
        assertEquals(0, command.updates);
        assertFalse(command.hasExecutionErrors());
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void updateWritesClonedDescriptionAndAlwaysUnlocksOwner(boolean failUpdate) {
        var command = new TestCommand(EditMode.UPDATE);
        command.failUpdate = failUpdate;
        if(failUpdate) {
            assertThrows(IllegalStateException.class, command::executeEdit);
        } else {
            command.executeEdit();
            assertFalse(command.hasExecutionErrors());
        }
        assertEquals(1, command.locks);
        assertEquals(1, command.updates);
        assertEquals(1, command.unlocks);
    }

    @ParameterizedTest
    @EnumSource(EditMode.class)
    public void missingDescriptionStopsBeforeLockingOrWriting(EditMode mode) {
        var command = new TestCommand(mode);
        command.missingDescription = true;
        assertNotNull(command.executeEdit());
        assertTrue(command.hasExecutionErrors());
        assertEquals(0, command.locks);
        assertEquals(0, command.updates);
        assertEquals(0, command.unlocks);
    }

}
