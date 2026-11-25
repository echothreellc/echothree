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

package com.echothree.model.control.letter.server.logic;

import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.chain.common.pk.*;
import com.echothree.model.data.chain.server.entity.*;
import com.echothree.model.data.chain.server.value.*;
import com.echothree.model.data.letter.common.pk.*;
import com.echothree.model.data.letter.server.entity.*;
import com.echothree.model.data.letter.server.value.*;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class LetterContactMechanismPurposeLogicTest {

    private static class TestLogic extends LetterContactMechanismPurposeLogic {
        private final LetterContactMechanismPurpose entry = new LetterContactMechanismPurpose(
                new LetterContactMechanismPurposeValue(new LetterContactMechanismPurposePK(1L), null, null), EntityPermission.READ_WRITE);
        private int entryLookups;
        private EntityPermission selectedPermission;

        TestLogic(boolean missingLetter, boolean missingEntry) {
            var chainKind = new ChainKind(new ChainKindValue(new ChainKindPK(2L), null, null), EntityPermission.READ_ONLY) {
                @Override
                public ChainKindDetail getLastDetail() {
                    return new ChainKindDetail(new ChainKindDetailValue(new ChainKindDetailPK(3L), getPrimaryKey(), "KIND",
                            false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
                }
            };
            var chainType = new ChainType(new ChainTypeValue(new ChainTypePK(4L), null, null), EntityPermission.READ_ONLY) {
                @Override
                public ChainTypeDetail getLastDetail() {
                    return new ChainTypeDetail(new ChainTypeDetailValue(new ChainTypeDetailPK(5L), getPrimaryKey(), chainKind.getPrimaryKey(),
                            "TYPE", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                        @Override
                        public ChainKind getChainKind() {
                            return chainKind;
                        }
                    };
                }
            };
            var letter = new Letter(new LetterValue(new LetterPK(6L), null, null), EntityPermission.READ_ONLY) {
                @Override
                public LetterDetail getLastDetail() {
                    return new LetterDetail(new LetterDetailValue(new LetterDetailPK(7L), getPrimaryKey(), chainType.getPrimaryKey(),
                            "LETTER", new LetterSourcePK(8L), null, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                        @Override
                        public ChainType getChainType() {
                            return chainType;
                        }
                    };
                }
            };
            letterLogic = new LetterLogic() {
                @Override
                public Letter getLetterByName(ExecutionErrorAccumulator eea, String chainKindName, String chainTypeName, String letterName) {
                    assertEquals("KIND", chainKindName);
                    assertEquals("TYPE", chainTypeName);
                    assertEquals("LETTER", letterName);
                    if(missingLetter) {
                        eea.addExecutionError(ExecutionErrors.UnknownLetterName.name(), chainKindName, chainTypeName, letterName);
                    }
                    return missingLetter ? null : letter;
                }
            };
            letterControl = new LetterControl() {
                @Override
                public LetterContactMechanismPurpose getLetterContactMechanismPurpose(Letter selectedLetter, Integer priority,
                        EntityPermission entityPermission) {
                    assertSame(letter, selectedLetter);
                    assertEquals(10, priority);
                    entryLookups++;
                    selectedPermission = entityPermission;
                    return missingEntry ? null : entry;
                }
            };
        }
    }

    @ParameterizedTest
    @EnumSource(EntityPermission.class)
    public void nameLookupUsesRequestedPermission(EntityPermission entityPermission) {
        var logic = new TestLogic(false, false);
        var errors = new DummyExecutionErrorAccumulator();
        assertSame(logic.entry, logic.getLetterContactMechanismPurpose(errors, "KIND", "TYPE", "LETTER", 10, entityPermission));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(entityPermission, logic.selectedPermission);
    }

    @Test
    public void missingLetterStopsBeforeEntryLookup() {
        var logic = new TestLogic(true, false);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getLetterContactMechanismPurpose(errors, "KIND", "TYPE", "LETTER", 10));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.entryLookups);
    }

    @Test
    public void missingEntryReportsCompleteNameAndPriorityKey() {
        var logic = new TestLogic(false, true);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getLetterContactMechanismPurposeForUpdate(errors, "KIND", "TYPE", "LETTER", 10));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnknownLetterContactMechanismPurpose.name(), error.getKey());
        assertArrayEquals(new Object[] { "KIND", "TYPE", "LETTER", "10" }, error.getValues());
        assertEquals(EntityPermission.READ_WRITE, logic.selectedPermission);
    }

}
