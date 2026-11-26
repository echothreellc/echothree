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

import com.echothree.model.control.core.server.control.EntityAttributeGroupControl;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.core.common.pk.ComponentVendorPK;
import com.echothree.model.data.core.common.pk.EntityAttributeGroupDescriptionPK;
import com.echothree.model.data.core.common.pk.EntityAttributeGroupPK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.ComponentVendorDetail;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDescription;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDetail;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.value.ComponentVendorDetailValue;
import com.echothree.model.data.core.server.value.ComponentVendorValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDetailValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupValue;
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
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EntityAttributeGroupDescriptionLogicTest {

    private static class TestLogic extends EntityAttributeGroupLogic {
        private final PartyPK createdBy = new PartyPK(1L);
        private final Language language = new Language(new LanguageValue(new LanguagePK(1L), "en", false, 0), EntityPermission.READ_ONLY);
        private final EntityAttributeGroup entityAttributeGroup;
        private final EntityAttributeGroupDescription entityAttributeGroupDescription;
        private boolean missingGroup;
        private boolean missingLanguage;
        private boolean duplicate;
        private int languageLookups;
        private int descriptionLookups;
        private int writes;

        TestLogic() {
            var componentVendorPK = new ComponentVendorPK(1L);
            var entityTypePK = new EntityTypePK(1L);
            var entityAttributeGroupPK = new EntityAttributeGroupPK(1L);
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
            var entityAttributeGroupDetail = new EntityAttributeGroupDetail(new EntityAttributeGroupDetailValue(entityAttributeGroupPK, entityTypePK, "GROUP", false, 0, 1L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                @Override
                public EntityType getEntityType() {
                    return entityType;
                }
            };
            entityAttributeGroup = new EntityAttributeGroup(new EntityAttributeGroupValue(entityAttributeGroupPK, null, null), EntityPermission.READ_ONLY) {
                @Override
                public EntityAttributeGroupDetail getLastDetail() {
                    return entityAttributeGroupDetail;
                }
            };
            entityAttributeGroupDescription = new EntityAttributeGroupDescription(new EntityAttributeGroupDescriptionValue(new EntityAttributeGroupDescriptionPK(1L), entityAttributeGroupPK,
                    language.getPrimaryKey(), "Description", 1L, Long.MAX_VALUE), EntityPermission.READ_WRITE);
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
            entityAttributeGroupControl = new EntityAttributeGroupControl() {
                @Override
                public EntityAttributeGroupDescription getEntityAttributeGroupDescription(EntityAttributeGroup group, Language suppliedLanguage) {
                    assertSame(entityAttributeGroup, group);
                    assertSame(language, suppliedLanguage);
                    descriptionLookups++;
                    return duplicate ? entityAttributeGroupDescription : null;
                }

                @Override
                public EntityAttributeGroupDescription createEntityAttributeGroupDescription(EntityAttributeGroup group, Language suppliedLanguage, String description, BasePK actor) {
                    assertSame(entityAttributeGroup, group);
                    assertSame(language, suppliedLanguage);
                    assertSame(createdBy, actor);
                    assertEquals("Description", description);
                    writes++;
                    return entityAttributeGroupDescription;
                }
            };
        }

        @Override
        public EntityAttributeGroup getEntityAttributeGroupByName(ExecutionErrorAccumulator eea, String vendor, String type, String group) {
            assertEquals("VENDOR", vendor);
            assertEquals("TYPE", type);
            assertEquals("GROUP", group);
            if(missingGroup) {
                eea.addExecutionError(ExecutionErrors.UnknownEntityAttributeGroupName.name(), vendor, type, group);
            }
            return missingGroup ? null : entityAttributeGroup;
        }

        EntityAttributeGroupDescription create(ExecutionErrorAccumulator eea) {
            return createEntityAttributeGroupDescription(eea, "VENDOR", "TYPE", "GROUP", "en", "Description", createdBy);
        }
    }

    @Test
    void createsDescriptionWithResolvedGroupLanguageAndActor() {
        var logic = new TestLogic();
        var errors = new DummyExecutionErrorAccumulator();
        assertSame(logic.entityAttributeGroupDescription, logic.create(errors));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.descriptionLookups);
        assertEquals(1, logic.writes);
    }

    @Test
    void missingGroupStopsBeforeLanguageAndDescriptionLookups() {
        var logic = new TestLogic();
        logic.missingGroup = true;
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
        assertEquals(ExecutionErrors.DuplicateEntityAttributeGroupDescription.name(), error.getKey());
        assertArrayEquals(new Object[]{"VENDOR", "TYPE", "GROUP", "en"}, error.getValues());
        assertEquals(1, logic.descriptionLookups);
        assertEquals(0, logic.writes);
    }

}
