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

package com.echothree.control.user.letter.server.command;

import com.echothree.control.user.letter.common.edit.LetterEditFactory;
import com.echothree.control.user.letter.common.spec.LetterSpecFactory;
import com.echothree.model.control.contact.server.logic.ContactMechanismPurposeLogic;
import com.echothree.model.control.letter.server.logic.LetterContactMechanismPurposeLogic;
import com.echothree.model.data.contact.common.pk.ContactMechanismPurposePK;
import com.echothree.model.data.contact.common.pk.ContactMechanismTypePK;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.ContactMechanismType;
import com.echothree.model.data.contact.server.value.ContactMechanismPurposeValue;
import com.echothree.model.data.contact.server.value.ContactMechanismTypeValue;
import com.echothree.model.data.letter.common.pk.LetterPK;
import com.echothree.model.data.letter.common.pk.LetterContactMechanismPurposePK;
import com.echothree.model.data.letter.common.pk.LetterContactMechanismPurposeDetailPK;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.letter.server.entity.LetterContactMechanismPurpose;
import com.echothree.model.data.letter.server.entity.LetterContactMechanismPurposeDetail;
import com.echothree.model.data.letter.server.value.LetterValue;
import com.echothree.model.data.letter.server.value.LetterContactMechanismPurposeValue;
import com.echothree.model.data.letter.server.value.LetterContactMechanismPurposeDetailValue;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EditLetterContactMechanismPurposeCommandTest {

    private static class TestCommand extends EditLetterContactMechanismPurposeCommand {
        private final Letter letter = new Letter(new LetterValue(new LetterPK(1L), null, null), EntityPermission.READ_ONLY);
        private final LetterContactMechanismPurpose entry = new LetterContactMechanismPurpose(
                new LetterContactMechanismPurposeValue(new LetterContactMechanismPurposePK(2L), null, null), EntityPermission.READ_WRITE) {
            @Override
            public LetterContactMechanismPurposeDetail getLastDetail() {
                return new LetterContactMechanismPurposeDetail(new LetterContactMechanismPurposeDetailValue(
                        new LetterContactMechanismPurposeDetailPK(3L), getPrimaryKey(), letter.getPrimaryKey(), 10,
                        new ContactMechanismPurposePK(4L), 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                    @Override
                    public Letter getLetter() {
                        return letter;
                    }
                };
            }
        };
        private int entryLookups;

        TestCommand(String missingEntity, String contactMechanismTypeName) {
            editMode = EditMode.UPDATE;
            spec = LetterSpecFactory.getLetterContactMechanismPurposeSpec();
            spec.setChainKindName("KIND");
            spec.setChainTypeName("TYPE");
            spec.setLetterName("LETTER");
            spec.setPriority("10");
            edit = LetterEditFactory.getLetterContactMechanismPurposeEdit();
            edit.setContactMechanismPurposeName("PURPOSE");
            letterContactMechanismPurposeLogic = new LetterContactMechanismPurposeLogic() {
                @Override
                public LetterContactMechanismPurpose getLetterContactMechanismPurpose(ExecutionErrorAccumulator eea,
                        String chainKindName, String chainTypeName, String letterName, Integer priority, EntityPermission entityPermission) {
                    assertEquals("KIND", chainKindName);
                    assertEquals("TYPE", chainTypeName);
                    assertEquals("LETTER", letterName);
                    assertEquals(10, priority);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    LetterContactMechanismPurpose result = null;
                    if("letter".equals(missingEntity)) {
                        eea.addExecutionError(ExecutionErrors.UnknownLetterName.name(), chainKindName, chainTypeName, letterName);
                    } else {
                        entryLookups++;
                        if("entry".equals(missingEntity)) {
                            eea.addExecutionError(ExecutionErrors.UnknownLetterContactMechanismPurpose.name(),
                                    chainKindName, chainTypeName, letterName, priority.toString());
                        } else {
                            result = entry;
                        }
                    }
                    return result;
                }
            };
            contactMechanismPurposeLogic = new ContactMechanismPurposeLogic() {
                @Override
                public ContactMechanismPurpose getContactMechanismPurposeByName(ExecutionErrorAccumulator eea, String contactMechanismPurposeName) {
                    if("purpose".equals(missingEntity)) {
                        eea.addExecutionError(ExecutionErrors.UnknownContactMechanismPurposeName.name(), contactMechanismPurposeName);
                        return null;
                    }
                    var contactMechanismType = new ContactMechanismType(new ContactMechanismTypeValue(new ContactMechanismTypePK(5L),
                            contactMechanismTypeName, null, false, 0), EntityPermission.READ_ONLY);
                    return new ContactMechanismPurpose(new ContactMechanismPurposeValue(new ContactMechanismPurposePK(4L), "PURPOSE",
                            contactMechanismType.getPrimaryKey(), false, false, 0), EntityPermission.READ_ONLY) {
                        @Override
                        public ContactMechanismType getContactMechanismType() {
                            return contactMechanismType;
                        }
                    };
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "letter", "entry" })
    public void missingEntitiesReportCompleteKeys(String missingEntity) {
        var command = new TestCommand(missingEntity, "EMAIL_ADDRESS");
        assertNull(command.getEntity(command.getResult()));
        var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertArrayEquals("letter".equals(missingEntity) ? new Object[] { "KIND", "TYPE", "LETTER" }
                : new Object[] { "KIND", "TYPE", "LETTER", "10" }, error.getValues());
        assertEquals("letter".equals(missingEntity) ? 0 : 1, command.entryLookups);
    }

    @Test
    public void resolvesEntryForUpdateAndLocksItsLetter() {
        var command = new TestCommand(null, "EMAIL_ADDRESS");
        assertSame(command.entry, command.getEntity(command.getResult()));
        assertSame(command.letter, command.getLockEntity(command.entry));
        assertFalse(command.hasExecutionErrors());
    }

    @ParameterizedTest
    @ValueSource(strings = { "EMAIL_ADDRESS", "POSTAL_ADDRESS", "TELECOM_ADDRESS" })
    public void permitsOnlyEmailAndPostalPurposes(String contactMechanismTypeName) {
        var command = new TestCommand(null, contactMechanismTypeName);
        command.canUpdate(command.entry);
        assertEquals("TELECOM_ADDRESS".equals(contactMechanismTypeName), command.hasExecutionErrors());
        if(command.hasExecutionErrors()) {
            var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
            assertEquals(ExecutionErrors.InvalidContactMechanismType.name(), error.getKey());
            assertArrayEquals(new Object[] { "TELECOM_ADDRESS" }, error.getValues());
        }
    }

    @Test
    public void missingPurposeAccumulatesErrorWithoutDereferencingNull() {
        var command = new TestCommand("purpose", "EMAIL_ADDRESS");
        assertDoesNotThrow(() -> command.canUpdate(command.entry));
        assertTrue(command.hasExecutionErrors());
        var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnknownContactMechanismPurposeName.name(), error.getKey());
        assertArrayEquals(new Object[] { "PURPOSE" }, error.getValues());
    }

}
