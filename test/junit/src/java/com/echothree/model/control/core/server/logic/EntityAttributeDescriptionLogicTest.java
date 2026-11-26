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

import com.echothree.model.control.core.common.exception.DuplicateEntityAttributeDescriptionException;
import com.echothree.model.control.core.common.exception.UnknownEntityAttributeNameException;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.exception.UnknownLanguageIsoNameException;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.core.common.pk.ComponentVendorPK;
import com.echothree.model.data.core.common.pk.EntityAttributeDescriptionPK;
import com.echothree.model.data.core.common.pk.EntityAttributePK;
import com.echothree.model.data.core.common.pk.EntityAttributeTypePK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.ComponentVendorDetail;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeDescription;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.value.ComponentVendorDetailValue;
import com.echothree.model.data.core.server.value.ComponentVendorValue;
import com.echothree.model.data.core.server.value.EntityAttributeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeDetailValue;
import com.echothree.model.data.core.server.value.EntityAttributeValue;
import com.echothree.model.data.core.server.value.EntityTypeDetailValue;
import com.echothree.model.data.core.server.value.EntityTypeValue;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.value.LanguageValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
@ResourceLock("CDIProvider")
public class EntityAttributeDescriptionLogicTest {

    private static class TestLogic extends EntityAttributeLogic {
        private final PartyPK createdBy = new PartyPK(1L);
        private final Language language = new Language(new LanguageValue(new LanguagePK(1L), "en", false, 0), EntityPermission.READ_ONLY);
        private final EntityAttribute entityAttribute;
        private final EntityAttributeDescription entityAttributeDescription;
        private boolean missingAttribute;
        private boolean missingLanguage;
        private boolean duplicate;
        private int languageLookups;
        private int descriptionLookups;
        private int writes;

        TestLogic() {
            var componentVendorPK = new ComponentVendorPK(1L);
            var entityTypePK = new EntityTypePK(1L);
            var entityAttributePK = new EntityAttributePK(1L);
            var componentVendorDetail = new ComponentVendorDetail(new ComponentVendorDetailValue(componentVendorPK, "VENDOR", "Vendor", 1L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            var componentVendor = new ComponentVendor(new ComponentVendorValue(componentVendorPK, null, null), EntityPermission.READ_ONLY) {
                @Override
                public ComponentVendorDetail getLastDetail() {
                    return componentVendorDetail;
                }
            };
            var entityTypeDetail = new EntityTypeDetail(new EntityTypeDetailValue(entityTypePK, componentVendorPK, "TYPE", false, 0L, true, 0, 1L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                @Override
                public ComponentVendor getComponentVendor() {
                    return componentVendor;
                }
            };
            var entityType = new EntityType(new EntityTypeValue(entityTypePK, null, null), EntityPermission.READ_ONLY) {
                @Override
                public EntityTypeDetail getLastDetail() {
                    return entityTypeDetail;
                }
            };
            var entityAttributeDetail = new EntityAttributeDetail(new EntityAttributeDetailValue(entityAttributePK, entityTypePK, "ATTRIBUTE", new EntityAttributeTypePK(1L), false, 0, 1L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                @Override
                public EntityType getEntityType() {
                    return entityType;
                }
            };
            entityAttribute = new EntityAttribute(new EntityAttributeValue(entityAttributePK, null, null), EntityPermission.READ_ONLY) {
                @Override
                public EntityAttributeDetail getLastDetail() {
                    return entityAttributeDetail;
                }
            };
            entityAttributeDescription = new EntityAttributeDescription(new EntityAttributeDescriptionValue(new EntityAttributeDescriptionPK(1L), entityAttributePK,
                    language.getPrimaryKey(), "Description", 1L, Long.MAX_VALUE), EntityPermission.READ_WRITE);
            languageLogic = new LanguageLogic() {
                @Override
                public Language getLanguageByName(ExecutionErrorAccumulator eea, String name) {
                    assertEquals("en", name);
                    languageLookups++;
                    if(missingLanguage) {
                        TestLogic.this.handleExecutionError(UnknownLanguageIsoNameException.class, eea, ExecutionErrors.UnknownLanguageIsoName.name(), name);
                    }
                    return missingLanguage ? null : language;
                }
            };
            coreControl = new CoreControl() {
                @Override
                public EntityAttributeDescription getEntityAttributeDescription(EntityAttribute group, Language suppliedLanguage) {
                    assertSame(entityAttribute, group);
                    assertSame(language, suppliedLanguage);
                    descriptionLookups++;
                    return duplicate ? entityAttributeDescription : null;
                }

                @Override
                public EntityAttributeDescription createEntityAttributeDescription(EntityAttribute group, Language suppliedLanguage, String description, BasePK actor) {
                    assertSame(entityAttribute, group);
                    assertSame(language, suppliedLanguage);
                    assertSame(createdBy, actor);
                    assertEquals("Description", description);
                    writes++;
                    return entityAttributeDescription;
                }
            };
        }

        @Override
        public EntityAttribute getEntityAttributeByName(ExecutionErrorAccumulator eea, String vendor, String type, String group) {
            assertEquals("VENDOR", vendor);
            assertEquals("TYPE", type);
            assertEquals("ATTRIBUTE", group);
            if(missingAttribute) {
                handleExecutionError(UnknownEntityAttributeNameException.class, eea, ExecutionErrors.UnknownEntityAttributeName.name(), vendor, type, group);
            }
            return missingAttribute ? null : entityAttribute;
        }

        EntityAttributeDescription create(ExecutionErrorAccumulator eea) {
            return createEntityAttributeDescription(eea, "VENDOR", "TYPE", "ATTRIBUTE", "en", "Description", createdBy);
        }
    }

    @Test
    void createsDescriptionWithResolvedAttributeLanguageAndActor() {
        var logic = new TestLogic();
        var errors = new DummyExecutionErrorAccumulator();
        assertSame(logic.entityAttributeDescription, logic.create(errors));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.descriptionLookups);
        assertEquals(1, logic.writes);
    }

    @Test
    void missingAttributeStopsBeforeLanguageAndDescriptionLookups() {
        var logic = new TestLogic();
        logic.missingAttribute = true;
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.create(errors));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.languageLookups);
        assertEquals(0, logic.descriptionLookups);
        assertEquals(0, logic.writes);
    }

    @Test
    void missingLanguageStopsBeforeDescriptionLookup() {
        var logic = new TestLogic();
        logic.missingLanguage = true;
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.create(errors));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(1, logic.languageLookups);
        assertEquals(0, logic.descriptionLookups);
        assertEquals(0, logic.writes);
    }

    @Test
    void duplicateReportsCompleteKeyWithoutWriting() {
        var logic = new TestLogic();
        logic.duplicate = true;
        var errors = new DummyExecutionErrorAccumulator();
        logic.create(errors);
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.DuplicateEntityAttributeDescription.name(), error.getKey());
        assertArrayEquals(new Object[]{"VENDOR", "TYPE", "ATTRIBUTE", "en"}, error.getValues());
        assertEquals(1, logic.descriptionLookups);
        assertEquals(0, logic.writes);
    }

    @ParameterizedTest
    @ValueSource(strings = { "success", "owner", "language", "duplicate" })
    void nullAccumulatorCreatesOrThrowsMatchingException(String scenario) {
        var logic = new TestLogic();
        logic.missingAttribute = scenario.equals("owner");
        logic.missingLanguage = scenario.equals("language");
        logic.duplicate = scenario.equals("duplicate");

        try(var messages = new LogicExceptionTestSupport()) {
            switch(scenario) {
                case "owner" -> messages.assertException(UnknownEntityAttributeNameException.class, "UnknownEntityAttributeName", () -> logic.create(null));
                case "language" -> messages.assertException(UnknownLanguageIsoNameException.class, "UnknownLanguageIsoName", () -> logic.create(null));
                case "duplicate" -> messages.assertException(DuplicateEntityAttributeDescriptionException.class, "DuplicateEntityAttributeDescription", () -> logic.create(null));
                default -> assertSame(logic.entityAttributeDescription, logic.create(null));
            }
        }
        assertEquals(scenario.equals("owner") ? 0 : 1, logic.languageLookups);
        assertEquals(scenario.equals("owner") || scenario.equals("language") ? 0 : 1, logic.descriptionLookups);
        assertEquals(scenario.equals("success") ? 1 : 0, logic.writes);
    }

}
