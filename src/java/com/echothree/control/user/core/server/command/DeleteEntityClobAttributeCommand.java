// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.DeleteEntityClobAttributeForm;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteEntityClobAttributeCommand
        extends BaseSimpleCommand<DeleteEntityClobAttributeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteEntityClobAttributeCommand */
    public DeleteEntityClobAttributeCommand(UserVisitPK userVisitPK, DeleteEntityClobAttributeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CoreControl coreControl = getCoreControl();
        String entityRef = form.getEntityRef();
        EntityInstance entityInstance = coreControl.getEntityInstanceByEntityRef(entityRef);
        
        if(entityInstance != null) {
            String entityAttributeName = form.getEntityAttributeName();
            EntityAttribute entityAttribute = coreControl.getEntityAttributeByName(entityInstance.getEntityType(), entityAttributeName);
            
            if(entityAttribute != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = form.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    EntityClobAttribute entityClobAttribute = coreControl.getEntityClobAttributeForUpdate(entityAttribute, entityInstance, language);
                    
                    if(entityClobAttribute != null) {
                        coreControl.deleteEntityClobAttribute(entityClobAttribute, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityClobAttribute.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                EntityTypeDetail entityTypeDetail = entityInstance.getEntityType().getLastDetail();
                
                addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityTypeDetail.getEntityTypeName(), entityAttributeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
        }
        
        return null;
    }
    
}
