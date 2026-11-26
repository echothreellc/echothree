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

package com.echothree.model.control.core.server.logic;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.core.common.pk.EntityAttributeTypeDescriptionPK;
import com.echothree.model.data.core.common.pk.EntityAttributeTypePK;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityAttributeTypeDescription;
import com.echothree.model.data.core.server.value.EntityAttributeTypeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeTypeValue;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.value.LanguageValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EntityAttributeTypeLogicTest {

    private static class TestLogic extends EntityAttributeTypeLogic {
        private final EntityAttributeType entityAttributeType = new EntityAttributeType(new EntityAttributeTypeValue(new EntityAttributeTypePK(1L), "TYPE"), EntityPermission.READ_ONLY);
        private final Language language = new Language(new LanguageValue(new LanguagePK(1L), "en", false, 0), EntityPermission.READ_ONLY);
        private final EntityAttributeTypeDescription entityAttributeTypeDescription = new EntityAttributeTypeDescription(new EntityAttributeTypeDescriptionValue(
                new EntityAttributeTypeDescriptionPK(1L), entityAttributeType.getPrimaryKey(), language.getPrimaryKey(), "Description"), EntityPermission.READ_ONLY);
        private boolean missingType;
        private boolean missingLanguage;
        private boolean duplicateDescription;
        private int languageLookups;
        private int descriptionLookups;
        private int typeWrites;
        private int descriptionWrites;

        TestLogic() {
            coreControl = new CoreControl() {
                @Override
                public EntityAttributeType getEntityAttributeTypeByName(String name) {
                    assertEquals("TYPE", name);
                    return missingType ? null : entityAttributeType;
                }

                @Override
                public EntityAttributeType createEntityAttributeType(String name) {
                    assertEquals("TYPE", name);
                    typeWrites++;
                    return entityAttributeType;
                }

                @Override
                public EntityAttributeTypeDescription getEntityAttributeTypeDescription(EntityAttributeType type, Language resolvedLanguage) {
                    assertSame(entityAttributeType, type);
                    assertSame(language, resolvedLanguage);
                    descriptionLookups++;
                    return duplicateDescription ? entityAttributeTypeDescription : null;
                }

                @Override
                public EntityAttributeTypeDescription createEntityAttributeTypeDescription(EntityAttributeType type, Language resolvedLanguage, String description) {
                    assertSame(entityAttributeType, type);
                    assertSame(language, resolvedLanguage);
                    assertEquals("Description", description);
                    descriptionWrites++;
                    return entityAttributeTypeDescription;
                }
            };
            languageLogic = new LanguageLogic() {
                @Override
                public Language getLanguageByName(ExecutionErrorAccumulator eea, String name) {
                    assertEquals("en", name);
                    languageLookups++;
                    if(missingLanguage) {
                        eea.addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), name);
                    }
                    return missingLanguage ? null : language;
                }
            };
        }
    }

    private void assertError(DummyExecutionErrorAccumulator errors, ExecutionErrors expected, Object... arguments) {
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(expected.name(), error.getKey());
        assertArrayEquals(arguments, error.getValues());
    }

    @Test
    void createsTypeWhenAbsent() {
        var logic = new TestLogic();
        logic.missingType = true;
        var errors = new DummyExecutionErrorAccumulator();
        assertSame(logic.entityAttributeType, logic.createEntityAttributeType(errors, "TYPE"));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.typeWrites);
    }

    @Test
    void duplicateTypeReportsNameWithoutWriting() {
        var logic = new TestLogic();
        var errors = new DummyExecutionErrorAccumulator();
        logic.createEntityAttributeType(errors, "TYPE");
        assertError(errors, ExecutionErrors.DuplicateEntityAttributeTypeName, "TYPE");
        assertEquals(0, logic.typeWrites);
    }

    @Test
    void createsDescriptionWithResolvedTypeAndLanguage() {
        var logic = new TestLogic();
        var errors = new DummyExecutionErrorAccumulator();
        assertSame(logic.entityAttributeTypeDescription, logic.createEntityAttributeTypeDescription(errors, "TYPE", "en", "Description"));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.descriptionLookups);
        assertEquals(1, logic.descriptionWrites);
    }

    @Test
    void missingTypeStopsBeforeLanguageAndDescriptionLookups() {
        var logic = new TestLogic();
        logic.missingType = true;
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.createEntityAttributeTypeDescription(errors, "TYPE", "en", "Description"));
        assertError(errors, ExecutionErrors.UnknownEntityAttributeTypeName, "TYPE");
        assertEquals(0, logic.languageLookups);
        assertEquals(0, logic.descriptionLookups);
        assertEquals(0, logic.descriptionWrites);
    }

    @Test
    void missingLanguageStopsBeforeDescriptionLookup() {
        var logic = new TestLogic();
        logic.missingLanguage = true;
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.createEntityAttributeTypeDescription(errors, "TYPE", "en", "Description"));
        assertError(errors, ExecutionErrors.UnknownLanguageIsoName, "en");
        assertEquals(0, logic.descriptionLookups);
        assertEquals(0, logic.descriptionWrites);
    }

    @Test
    void duplicateDescriptionReportsCompleteKeyWithoutWriting() {
        var logic = new TestLogic();
        logic.duplicateDescription = true;
        var errors = new DummyExecutionErrorAccumulator();
        logic.createEntityAttributeTypeDescription(errors, "TYPE", "en", "Description");
        assertError(errors, ExecutionErrors.DuplicateEntityAttributeTypeDescription, "TYPE", "en");
        assertEquals(0, logic.descriptionWrites);
    }

}
