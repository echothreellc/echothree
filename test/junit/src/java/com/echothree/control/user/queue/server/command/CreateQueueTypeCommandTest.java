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

package com.echothree.control.user.queue.server.command;

import com.echothree.control.user.queue.common.form.QueueFormFactory;
import com.echothree.control.user.queue.common.result.CreateQueueTypeResult;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.queue.common.pk.QueueTypeDetailPK;
import com.echothree.model.data.queue.common.pk.QueueTypePK;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.queue.server.entity.QueueTypeDetail;
import com.echothree.model.data.queue.server.value.QueueTypeDetailValue;
import com.echothree.model.data.queue.server.value.QueueTypeValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateQueueTypeCommandTest {

    private static final String NAME = "TEST";
    private static final QueueTypePK PK = new QueueTypePK(123L);
    private static final QueueTypeDetailPK DETAIL_PK = new QueueTypeDetailPK(456L);

    private static QueueType entity() {
        var detail = new QueueTypeDetail(new QueueTypeDetailValue(DETAIL_PK, PK, NAME, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);

        return new QueueType(new QueueTypeValue(PK, DETAIL_PK, DETAIL_PK), EntityPermission.READ_ONLY) {
            @Override
            public QueueTypeDetail getLastDetail() {
                return detail;
            }
        };
    }

    private static class MemoryControl extends QueueControl {
        QueueType existing;
        int creates;

        MemoryControl(boolean duplicate) {
            existing = duplicate ? entity() : null;
        }

        @Override
        public QueueType getQueueTypeByName(String name) {
            assertEquals(NAME, name);
            return existing;
        }

        @Override
        public QueueType createQueueType(String name, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
            creates++;
            assertEquals(NAME, name);
            assertFalse(isDefault);
            assertEquals(0, sortOrder);
            existing = entity();
            return existing;
        }
    }

    private static class TestCommand extends CreateQueueTypeCommand {
        @SuppressWarnings("UnnecessaryAssignment") // Supply the CDI dependency without a container.
        TestCommand(MemoryControl control) {
            queueControl = control;
            form = QueueFormFactory.getCreateQueueTypeForm();
            form.setQueueTypeName(NAME);
            form.setIsDefault("false");
            form.setSortOrder("0");
        }

        @Override
        public PartyPK getPartyPK() {
            return new PartyPK(1L);
        }
    }

    @Test
    public void newlyCreatedEntityReturnsItsReferenceAndName() {
        var control = new MemoryControl(false);
        var command = new TestCommand(control);
        var result = (CreateQueueTypeResult)command.execute();

        assertEquals(PK.getEntityRef(), result.getEntityRef());
        assertEquals(NAME, result.getQueueTypeName());
        assertEquals(1, control.creates);
        assertFalse(command.hasExecutionErrors());
    }

    @Test
    public void duplicateReturnsExistingReferenceAndKeepsDuplicateError() {
        var control = new MemoryControl(true);
        var command = new TestCommand(control);
        var result = (CreateQueueTypeResult)command.execute();

        assertEquals(PK.getEntityRef(), result.getEntityRef());
        assertEquals(NAME, result.getQueueTypeName());
        assertEquals(0, control.creates);
        assertTrue(command.hasExecutionErrors());
        var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.DuplicateQueueTypeName.name(), error.getKey());
        assertArrayEquals(new Object[] { NAME }, error.getValues());
    }

}
