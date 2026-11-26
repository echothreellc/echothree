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

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.exception.DuplicateEntityStringAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityStringDefaultException;
import com.echothree.model.control.core.common.exception.InvalidStringAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityStringAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityStringDefaultException;
import com.echothree.model.control.core.server.database.EntityInstancesMissingStringEntityAttributeQuery;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.core.server.entity.EntityStringDefault;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.google.re2j.Pattern;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityStringAttributeLogic
        extends BaseEntityAttributeValueLogic {

    protected EntityStringAttributeLogic() {
        super();
    }

    public EntityStringDefault createEntityStringDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Language language, final String stringAttribute, final boolean addMissingAttributes, final BasePK createdBy) {
        EntityStringDefault entityStringDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.STRING);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityStringDefault = coreControl.getEntityStringDefault(entityAttribute, language);

            if(entityStringDefault == null) {
                coreControl.createEntityStringDefault(entityAttribute, language, stringAttribute, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingStringEntityAttributeQuery().execute(entityAttribute, language).forEach(entityInstanceResult ->
                            coreControl.createEntityStringAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), language, stringAttribute, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityStringDefaultException.class, eea, ExecutionErrors.DuplicateEntityStringDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityStringDefault;
    }

    public void deleteEntityStringDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Language language, final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.STRING);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityStringDefault = coreControl.getEntityStringDefaultForUpdate(entityAttribute, language);

            if(entityStringDefault == null) {
                handleExecutionError(UnknownEntityStringDefaultException.class, eea, ExecutionErrors.UnknownEntityStringDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName(),
                        language.getLanguageIsoName());
            } else {
                coreControl.deleteEntityStringDefault(entityStringDefault, deletedBy);
            }
        }
    }

    public EntityStringAttribute createEntityStringAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Language language, final String stringAttribute, final BasePK createdBy) {
        EntityStringAttribute entityStringAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityAttributeString = coreControl.getEntityAttributeString(entityAttribute);
            var validationPattern = entityAttributeString == null ? null : entityAttributeString.getValidationPattern();

            if(validationPattern != null) {
                var pattern = Pattern.compile(validationPattern);
                var m = pattern.matcher(stringAttribute);

                if(!m.matches()) {
                    handleExecutionError(InvalidStringAttributeException.class, eea, ExecutionErrors.InvalidStringAttribute.name(),
                            stringAttribute);
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                entityStringAttribute = coreControl.getEntityStringAttribute(entityAttribute, entityInstance, language);

                if(entityStringAttribute == null) {
                    coreControl.createEntityStringAttribute(entityAttribute, entityInstance, language, stringAttribute, createdBy);
                } else {
                    handleExecutionError(DuplicateEntityStringAttributeException.class, eea, ExecutionErrors.DuplicateEntityStringAttribute.name(),
                            entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                            entityAttribute.getLastDetail().getEntityAttributeName(),
                            language.getLanguageIsoName());
                }
            }
        }

        return entityStringAttribute;
    }

    public EntityStringAttribute getEntityStringAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance, Language language,
            EntityPermission entityPermission) {
        var entityStringAttribute = coreControl.getEntityStringAttribute(entityAttribute, entityInstance, language, entityPermission);

        if(entityStringAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityStringAttributeException.class, eea, ExecutionErrors.UnknownEntityStringAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName(), language.getLanguageIsoName());
        }

        return entityStringAttribute;
    }

    public EntityStringAttribute getEntityStringAttributeForUpdate(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute,
            EntityInstance entityInstance, Language language) {
        return getEntityStringAttribute(eea, entityAttribute, entityInstance, language, EntityPermission.READ_WRITE);
    }

}
