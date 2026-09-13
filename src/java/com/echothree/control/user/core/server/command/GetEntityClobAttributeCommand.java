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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.GetEntityClobAttributeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetEntityClobAttributeCommand
        extends BaseSingleEntityCommand<EntityClobAttribute, GetEntityClobAttributeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    EntityInstanceControl entityInstanceControl;

    @Inject
    PartyControl partyControl;

    @Inject
    EntityAttributeLogic entityAttributeLogic;

    /** Creates a new instance of GetEntityClobAttributeCommand */
    public GetEntityClobAttributeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected EntityClobAttribute getEntity() {
        EntityClobAttribute entityClobAttribute = null;
        var entityRef = form.getEntityRef();
        var entityInstance = entityInstanceControl.getEntityInstanceByEntityRef(entityRef);
        
        if(entityInstance != null) {
            var entityAttributeName = form.getEntityAttributeName();
            var entityAttribute = entityAttributeLogic.getEntityAttributeByName(this, entityInstance.getEntityType(), entityAttributeName);
            
            if(!hasExecutionErrors()) {
                var languageIsoName = form.getLanguageIsoName();
                var language = languageIsoName == null ? null : partyControl.getLanguageByIsoName(languageIsoName);
                
                if(languageIsoName == null || language != null) {
                    entityClobAttribute = language == null ? coreControl.getBestEntityClobAttribute(entityAttribute, entityInstance, getPreferredLanguage())
                            : coreControl.getEntityClobAttribute(entityAttribute, entityInstance, language);
                    
                    if(entityClobAttribute == null) {
                        var entityTypeDetail = entityInstance.getEntityType().getLastDetail();

                        addExecutionError(ExecutionErrors.UnknownEntityClobAttribute.name(), entityRef,
                                entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                entityTypeDetail.getEntityTypeName(), entityAttributeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
        }
        
        return entityClobAttribute;
    }

    @Override
    protected BaseResult getResult(EntityClobAttribute entityClobAttribute) {
        var result = CoreResultFactory.getGetEntityClobAttributeResult();

        if(entityClobAttribute != null) {
            result.setEntityClobAttribute(coreControl.getEntityClobAttributeTransfer(getUserVisit(), entityClobAttribute,
                    entityClobAttribute.getEntityInstance()));
        }

        return result;
    }
    
}
