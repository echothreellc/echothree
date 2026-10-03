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

package com.echothree.model.control.inventory.server.logic;

import com.echothree.model.control.inventory.server.control.InventoryTransferTransactionControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.inventory.common.pk.InventoryTransactionPK;
import com.echothree.model.data.inventory.common.pk.InventoryTransactionTypePK;
import com.echothree.model.data.inventory.common.pk.InventoryTransferTransactionPK;
import com.echothree.model.data.inventory.server.entity.InventoryTransaction;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionDetail;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTypeDetail;
import com.echothree.model.data.inventory.server.entity.InventoryTransferTransaction;
import com.echothree.model.data.inventory.server.entity.InventoryTransferTransactionDetail;
import com.echothree.model.data.inventory.server.value.InventoryTransactionDetailValue;
import com.echothree.model.data.inventory.server.value.InventoryTransactionTypeDetailValue;
import com.echothree.model.data.inventory.server.value.InventoryTransactionTypeValue;
import com.echothree.model.data.inventory.server.value.InventoryTransactionValue;
import com.echothree.model.data.inventory.server.value.InventoryTransferTransactionDetailValue;
import com.echothree.model.data.inventory.server.value.InventoryTransferTransactionValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.sequence.common.pk.SequenceTypePK;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.sequence.server.value.SequenceTypeValue;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InventoryTransferTransactionLogicTest {

    private final SequenceType transferSequence = new SequenceType(
            new SequenceTypeValue(new SequenceTypePK(1L), null, null), EntityPermission.READ_ONLY);
    private final InventoryTransactionType type = type(transferSequence);
    private final InventoryTransaction source = transaction(10L, "SOURCE");
    private final InventoryTransaction destination = transaction(20L, "DESTINATION");
    private final InventoryTransferTransaction existing = transfer(100L);
    private final TestControl control = new TestControl();
    private final InventoryTransferTransactionLogic logic = new InventoryTransferTransactionLogic();
    private int sequenceCalls;
    private final Errors errors = new Errors();

    @SuppressWarnings("UnnecessaryAssignment") // Supply test doubles without starting CDI.
    @BeforeEach
    void injectDependencies() {
        logic.inventoryTransferTransactionControl = control;
        logic.inventoryTransactionTypeLogic = new InventoryTransactionTypeLogic() {
            @Override
            public InventoryTransactionType getInventoryTransactionTypeByName(ExecutionErrorAccumulator eea, String name) {
                assertEquals("TYPE", name);
                return type;
            }
        };
        logic.sequenceGeneratorLogic = new SequenceGeneratorLogic() {
            @Override
            public String getNextSequenceValue(ExecutionErrorAccumulator eea, SequenceType sequenceType) {
                assertSame(transferSequence, sequenceType);
                sequenceCalls++;
                return "TRANSFER0001";
            }
        };
    }

    @Test
    void explicitNameDoesNotConsumeSequence() {
        assertSame(existing, create(type, "EXPLICIT"));
        assertEquals("EXPLICIT", control.createdName);
        assertEquals(0, sequenceCalls);
    }

    @Test
    void missingNameUsesTransferSequence() {
        assertSame(existing, create(type, null));
        assertEquals("TRANSFER0001", control.createdName);
        assertEquals(1, sequenceCalls);
    }

    @Test
    void duplicateNameIsRejected() {
        control.byName = existing;
        assertSame(existing, create(type, "DUPLICATE"));
        assertError("DuplicateInventoryTransferTransactionName", "TYPE", "DUPLICATE");
        assertFalse(control.created);
    }

    @Test
    void unknownNameIsRejected() {
        assertNull(logic.getInventoryTransferTransactionByName(errors, type, "UNKNOWN"));
        assertError("UnknownInventoryTransferTransactionName", "TYPE", "UNKNOWN");
    }

    @Test
    void updateLookupRequestsWritePermission() {
        control.byName = existing;
        assertSame(existing, logic.getInventoryTransferTransactionByNameForUpdate(null, type, "EXISTING"));
        assertSame(EntityPermission.READ_WRITE, control.permission);
    }

    @Test
    void updateDelegatesToControl() {
        logic.updateInventoryTransferTransactionFromValue(detailValue(), null);
        assertTrue(control.updated);
    }

    private void assertError(String key, Object... values) {
        assertEquals(1, errors.messages.size());
        var message = errors.messages.get().next();
        assertEquals(key, message.getKey());
        assertArrayEquals(values, message.getValues());
    }

    private static class Errors implements ExecutionErrorAccumulator {
        private final Messages messages = new Messages();

        @Override
        public void addExecutionError(Message message) {
            messages.add(Messages.EXECUTION_ERROR, message);
        }

        @Override
        public void addExecutionError(String key, Object... values) {
            addExecutionError(new Message(key, values));
        }

        @Override
        public Messages getExecutionErrors() {
            return messages;
        }

        @Override
        public boolean hasExecutionErrors() {
            return !messages.isEmpty();
        }
    }

    private InventoryTransferTransaction create(InventoryTransactionType transactionType, String name) {
        return logic.createInventoryTransferTransaction(errors, transactionType.getLastDetail().getInventoryTransactionTypeName(), name, source, destination,
                "REFERENCE", "DESCRIPTION", null);
    }

    private InventoryTransferTransactionDetailValue detailValue() {
        return new InventoryTransferTransactionDetailValue(existing.getPrimaryKey(), type.getPrimaryKey(), "EXISTING",
                source.getPrimaryKey(), destination.getPrimaryKey(), "REFERENCE", "EDITED", 0L, Long.MAX_VALUE);
    }

    private InventoryTransactionType type(SequenceType sequence) {
        var pk = new InventoryTransactionTypePK(1L);
        var detail = new InventoryTransactionTypeDetail(new InventoryTransactionTypeDetailValue(pk, "TYPE",
                sequence == null ? null : sequence.getPrimaryKey(), null, null, null, false, 0, 0L, Long.MAX_VALUE),
                EntityPermission.READ_ONLY) {
            @Override
            public SequenceType getInventoryTransferTransactionSequenceType() {
                return sequence;
            }
        };

        return new InventoryTransactionType(new InventoryTransactionTypeValue(pk, null, null), EntityPermission.READ_ONLY) {
            @Override
            public InventoryTransactionTypeDetail getLastDetail() {
                return detail;
            }
        };
    }

    private InventoryTransaction transaction(long id, String name) {
        var pk = new InventoryTransactionPK(id);
        var detail = new InventoryTransactionDetail(new InventoryTransactionDetailValue(pk, type.getPrimaryKey(), name,
                new PartyPK(1L), new PartyPK(2L), null, null, null, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
            @Override
            public InventoryTransactionType getInventoryTransactionType() {
                return type;
            }
        };

        return new InventoryTransaction(new InventoryTransactionValue(pk, null, null), EntityPermission.READ_ONLY) {
            @Override
            public InventoryTransactionDetail getLastDetail() {
                return detail;
            }
        };
    }

    private InventoryTransferTransaction transfer(long id) {
        var pk = new InventoryTransferTransactionPK(id);
        var detail = new InventoryTransferTransactionDetail(new InventoryTransferTransactionDetailValue(pk,
                type.getPrimaryKey(), "EXISTING", source.getPrimaryKey(), destination.getPrimaryKey(), null, null,
                0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
            @Override
            public InventoryTransactionType getInventoryTransactionType() {
                return type;
            }
        };

        return new InventoryTransferTransaction(new InventoryTransferTransactionValue(pk, null, null), EntityPermission.READ_ONLY) {
            @Override
            public InventoryTransferTransactionDetail getLastDetail() {
                return detail;
            }
        };
    }

    private class TestControl extends InventoryTransferTransactionControl {
        InventoryTransferTransaction byName;
        EntityPermission permission;
        String createdName;
        boolean created;
        boolean updated;

        @Override
        public InventoryTransferTransaction createInventoryTransferTransaction(InventoryTransactionType transactionType,
                String name, InventoryTransaction sourceTransaction, InventoryTransaction destinationTransaction,
                String reference, String description, BasePK createdBy) {
            assertSame(type, transactionType);
            assertSame(source, sourceTransaction);
            assertSame(destination, destinationTransaction);
            assertEquals("REFERENCE", reference);
            assertEquals("DESCRIPTION", description);
            createdName = name;
            created = true;
            return existing;
        }

        @Override
        public InventoryTransferTransaction getInventoryTransferTransactionByName(InventoryTransactionType transactionType,
                String name, EntityPermission entityPermission) {
            permission = entityPermission;
            return byName;
        }

        @Override
        public void updateInventoryTransferTransactionFromValue(InventoryTransferTransactionDetailValue value, BasePK updatedBy) {
            updated = true;
        }
    }

}
