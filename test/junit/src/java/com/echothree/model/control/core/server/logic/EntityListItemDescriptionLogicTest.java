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
import com.echothree.model.data.core.common.pk.ComponentVendorPK;
import com.echothree.model.data.core.common.pk.EntityAttributePK;
import com.echothree.model.data.core.common.pk.EntityAttributeTypePK;
import com.echothree.model.data.core.common.pk.EntityListItemDescriptionPK;
import com.echothree.model.data.core.common.pk.EntityListItemPK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.ComponentVendorDetail;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemDescription;
import com.echothree.model.data.core.server.entity.EntityListItemDetail;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.value.ComponentVendorDetailValue;
import com.echothree.model.data.core.server.value.ComponentVendorValue;
import com.echothree.model.data.core.server.value.EntityAttributeDetailValue;
import com.echothree.model.data.core.server.value.EntityAttributeValue;
import com.echothree.model.data.core.server.value.EntityListItemDescriptionValue;
import com.echothree.model.data.core.server.value.EntityListItemDetailValue;
import com.echothree.model.data.core.server.value.EntityListItemValue;
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
public class EntityListItemDescriptionLogicTest {

    private static class TestLogic extends EntityListItemLogic {
        private final PartyPK createdBy = new PartyPK(1L);
        private final Language language = new Language(new LanguageValue(new LanguagePK(1L), "en", false, 0), EntityPermission.READ_ONLY);
        private final EntityListItem entityListItem;
        private final EntityListItemDescription entityListItemDescription;
        private boolean missingListItem;
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
            var entityAttribute = new EntityAttribute(new EntityAttributeValue(entityAttributePK, null, null), EntityPermission.READ_ONLY) {
                @Override
                public EntityAttributeDetail getLastDetail() {
                    return entityAttributeDetail;
                }
            };
            var entityListItemPK = new EntityListItemPK(1L);
            var entityListItemDetail = new EntityListItemDetail(new EntityListItemDetailValue(entityListItemPK, entityAttributePK, "LIST_ITEM", false, 0, 1L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                @Override
                public EntityAttribute getEntityAttribute() {
                    return entityAttribute;
                }
            };
            entityListItem = new EntityListItem(new EntityListItemValue(entityListItemPK, null, null), EntityPermission.READ_ONLY) {
                @Override
                public EntityListItemDetail getLastDetail() {
                    return entityListItemDetail;
                }
            };
            entityListItemDescription = new EntityListItemDescription(new EntityListItemDescriptionValue(new EntityListItemDescriptionPK(1L), entityListItemPK,
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
            coreControl = new CoreControl() {
                @Override
                public EntityListItemDescription getEntityListItemDescription(EntityListItem group, Language suppliedLanguage) {
                    assertSame(entityListItem, group);
                    assertSame(language, suppliedLanguage);
                    descriptionLookups++;
                    return duplicate ? entityListItemDescription : null;
                }

                @Override
                public EntityListItemDescription createEntityListItemDescription(EntityListItem group, Language suppliedLanguage, String description, BasePK actor) {
                    assertSame(entityListItem, group);
                    assertSame(language, suppliedLanguage);
                    assertSame(createdBy, actor);
                    assertEquals("Description", description);
                    writes++;
                    return entityListItemDescription;
                }
            };
        }

        @Override
        public EntityListItem getEntityListItemByName(ExecutionErrorAccumulator eea, String vendor, String type, String attribute, String group) {
            assertEquals("VENDOR", vendor);
            assertEquals("TYPE", type);
            assertEquals("ATTRIBUTE", attribute);
            assertEquals("LIST_ITEM", group);
            if(missingListItem) {
                eea.addExecutionError(ExecutionErrors.UnknownEntityListItemName.name(), vendor, type, attribute, group);
            }
            return missingListItem ? null : entityListItem;
        }

        EntityListItemDescription create(ExecutionErrorAccumulator eea) {
            return createEntityListItemDescription(eea, "VENDOR", "TYPE", "ATTRIBUTE", "LIST_ITEM", "en", "Description", createdBy);
        }
    }

    @Test
    void createsDescriptionWithResolvedListItemLanguageAndActor() {
        var logic = new TestLogic();
        var errors = new DummyExecutionErrorAccumulator();
        assertSame(logic.entityListItemDescription, logic.create(errors));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.descriptionLookups);
        assertEquals(1, logic.writes);
    }

    @Test
    void missingListItemStopsBeforeLanguageAndDescriptionLookups() {
        var logic = new TestLogic();
        logic.missingListItem = true;
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
        assertEquals(ExecutionErrors.DuplicateEntityListItemDescription.name(), error.getKey());
        assertArrayEquals(new Object[]{"VENDOR", "TYPE", "ATTRIBUTE", "LIST_ITEM", "en"}, error.getValues());
        assertEquals(1, logic.descriptionLookups);
        assertEquals(0, logic.writes);
    }

}
