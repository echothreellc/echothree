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
import com.echothree.control.user.letter.common.spec.LetterSourceUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.letter.common.pk.LetterSourcePK;
import com.echothree.model.data.letter.server.entity.LetterSource;
import com.echothree.model.data.letter.server.value.LetterSourceValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class LetterSourceLogicTest {

    private static class TestLogic extends LetterSourceLogic {
        private final LetterSource letterSource = new LetterSource(new LetterSourceValue(new LetterSourcePK(1L), null, null), EntityPermission.READ_WRITE);
        private int lookups;
        private boolean missingDefault;

        TestLogic(boolean missingEntity, EntityPermission expectedPermission) {
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec universalSpec,
                        String componentVendorName, String... entityTypeNames) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), componentVendorName);
                    assertArrayEquals(new String[] { EntityTypes.LetterSource.name() }, entityTypeNames);
                    if(missingEntity) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), universalSpec.getUuid());
                    }
                    return null;
                }
            };
            letterControl = new LetterControl() {
                @Override
                public LetterSource getDefaultLetterSource(EntityPermission entityPermission) {
                    assertEquals(expectedPermission, entityPermission);
                    lookups++;
                    return missingDefault ? null : letterSource;
                }

                @Override
                public LetterSource getLetterSourceByName(String letterSourceName, EntityPermission entityPermission) {
                    assertEquals("SOURCE", letterSourceName);
                    assertEquals(expectedPermission, entityPermission);
                    lookups++;
                    return missingEntity ? null : letterSource;
                }

                @Override
                public LetterSource getLetterSourceByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
                    assertEquals(expectedPermission, entityPermission);
                    lookups++;
                    return letterSource;
                }
            };
        }
    }

    private LetterSourceUniversalSpec spec(int identifierMask) {
        var spec = LetterSpecFactory.getLetterSourceUniversalSpec();
        if((identifierMask & 1) != 0) {
            spec.setLetterSourceName("SOURCE");
        }
        if((identifierMask & 2) != 0) {
            spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        }
        if((identifierMask & 4) != 0) {
            spec.setEntityRef("ECHO_THREE.LetterSource.1");
        }
        return spec;
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 4 })
    public void resolvesEachIdentifierWithRequestedPermission(int identifierMask) {
        for(var entityPermission : EntityPermission.values()) {
            var logic = new TestLogic(false, entityPermission);
            var errors = new DummyExecutionErrorAccumulator();
            var letterSource = entityPermission == EntityPermission.READ_ONLY
                    ? logic.getLetterSourceByUniversalSpec(errors, spec(identifierMask), false)
                    : logic.getLetterSourceByUniversalSpecForUpdate(errors, spec(identifierMask), false);

            assertSame(logic.letterSource, letterSource);
            assertFalse(errors.hasExecutionErrors());
            assertEquals(1, logic.lookups);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 3, 5, 6, 7 })
    public void rejectsMissingOrAmbiguousIdentifiersBeforeLookup(int identifierMask) {
        var logic = new TestLogic(false, EntityPermission.READ_WRITE);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getLetterSourceByUniversalSpecForUpdate(errors, spec(identifierMask), false));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.lookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 4 })
    public void missingIdentifierStopsLookupAndReportsError(int identifierMask) {
        var logic = new TestLogic(true, EntityPermission.READ_WRITE);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getLetterSourceByUniversalSpecForUpdate(errors, spec(identifierMask), false));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(identifierMask == 1 ? ExecutionErrors.UnknownLetterSourceName.name() : ExecutionErrors.UnknownUuid.name(), error.getKey());
        if(identifierMask == 1) {
            assertArrayEquals(new Object[] { "SOURCE" }, error.getValues());
        }
        assertEquals(identifierMask == 1 ? 1 : 0, logic.lookups);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void defaultLookupIsAvailableWhenExplicitlyAllowed(boolean missingDefault) {
        var logic = new TestLogic(false, EntityPermission.READ_WRITE);
        logic.missingDefault = missingDefault;
        var errors = new DummyExecutionErrorAccumulator();
        var result = logic.getLetterSourceByUniversalSpecForUpdate(errors, spec(0), true);

        if(missingDefault) {
            assertNull(result);
            assertEquals(ExecutionErrors.UnknownDefaultLetterSource.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        } else {
            assertSame(logic.letterSource, result);
            assertFalse(errors.hasExecutionErrors());
        }
        assertEquals(1, logic.lookups);
    }

}
