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

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.letter.common.spec.LetterSpecFactory;
import com.echothree.model.control.chain.server.logic.ChainTypeLogic;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.chain.common.pk.ChainTypePK;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.chain.server.value.ChainTypeValue;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.letter.common.pk.LetterPK;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.letter.server.value.LetterValue;
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
public class LetterLogicTest {

    private static class TestLogic extends LetterLogic {
        private final Letter letter = new Letter(new LetterValue(new LetterPK(1L), null, null), EntityPermission.READ_WRITE);
        private int lookups;

        TestLogic(boolean missingEntity) {
            var parentChainType = new ChainType(new ChainTypeValue(new ChainTypePK(2L), null, null), EntityPermission.READ_ONLY);
            chainTypeLogic = new ChainTypeLogic() {
                @Override
                public ChainType getChainTypeByName(ExecutionErrorAccumulator eea, String chainKindName, String chainTypeName) {
                    assertEquals("KIND", chainKindName);
                    assertEquals("TYPE", chainTypeName);
                    return parentChainType;
                }
            };
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec universalSpec,
                        String componentVendorName, String... entityTypeNames) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), componentVendorName);
                    assertArrayEquals(new String[] { EntityTypes.Letter.name() }, entityTypeNames);
                    if(missingEntity) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), universalSpec.getUuid());
                    }
                    return null;
                }
            };
            letterControl = new LetterControl() {
                @Override
                public Letter getLetterByName(ChainType chainType, String letterName, EntityPermission entityPermission) {
                    assertEquals("LETTER", letterName);
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return letter;
                }

                @Override
                public Letter getLetterByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
                    assertEquals(EntityPermission.READ_WRITE, entityPermission);
                    lookups++;
                    return letter;
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "name", "uuid", "entityRef" })
    public void resolvesEachIdentifierWithRequestedPermission(String identifier) {
        var logic = new TestLogic(false);
        var spec = LetterSpecFactory.getLetterUniversalSpec();
        switch(identifier) {
            case "name" -> {
                spec.setChainKindName("KIND");
                spec.setChainTypeName("TYPE");
                spec.setLetterName("LETTER");
            }
            case "uuid" -> spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
            case "entityRef" -> spec.setEntityRef("ECHO_THREE.Letter.1");
            default -> throw new IllegalArgumentException(identifier);
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertSame(logic.letter, logic.getLetterByUniversalSpecForUpdate(errors, spec));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.lookups);
    }

    @Test
    public void rejectsMissingAndAmbiguousIdentifiersWithoutLookingUpLetter() {
        var logic = new TestLogic(false);
        var spec = LetterSpecFactory.getLetterUniversalSpec();
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getLetterByUniversalSpec(errors, spec));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());

        spec.setLetterName("LETTER");
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getLetterByUniversalSpec(errors, spec));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @Test
    public void missingUuidStopsBeforeLetterLookup() {
        var logic = new TestLogic(true);
        var spec = LetterSpecFactory.getLetterUniversalSpec();
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getLetterByUniversalSpec(errors, spec));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.lookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7 })
    public void rejectsPartialNamesAndNamesCombinedWithUuid(int nameMask) {
        var logic = new TestLogic(false);
        var spec = LetterSpecFactory.getLetterUniversalSpec();
        if((nameMask & 1) != 0) {
            spec.setChainKindName("KIND");
        }
        if((nameMask & 2) != 0) {
            spec.setChainTypeName("TYPE");
        }
        if((nameMask & 4) != 0) {
            spec.setLetterName("LETTER");
        }
        if(nameMask == 7) {
            spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getLetterByUniversalSpec(errors, spec));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.lookups);
    }

}
