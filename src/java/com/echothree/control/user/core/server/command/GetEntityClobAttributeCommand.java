// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetEntityClobAttributeCommand
        extends BaseSimpleCommand<GetEntityClobAttributeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetEntityClobAttributeCommand */
    public GetEntityClobAttributeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var result = CoreResultFactory.getGetEntityClobAttributeResult();
        var entityRef = form.getEntityRef();
        var entityInstance = entityInstanceControl.getEntityInstanceByEntityRef(entityRef);
        
        if(entityInstance != null) {
            var coreControl = getCoreControl();
            var entityAttributeName = form.getEntityAttributeName();
            var entityAttribute = coreControl.getEntityAttributeByName(entityInstance.getEntityType(), entityAttributeName);
            
            if(entityAttribute != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = form.getLanguageIsoName();
                var language = languageIsoName == null ? null : partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    var entityClobAttribute = language == null ? coreControl.getBestEntityClobAttribute(entityAttribute, entityInstance, getPreferredLanguage())
                            : coreControl.getEntityClobAttribute(entityAttribute, entityInstance, language);
                    
                    if(entityClobAttribute != null) {
                        result.setEntityClobAttribute(coreControl.getEntityClobAttributeTransfer(getUserVisit(), entityClobAttribute, entityInstance));
                    } else {
                        var entityTypeDetail = entityInstance.getEntityType().getLastDetail();

                        addExecutionError(ExecutionErrors.UnknownEntityClobAttribute.name(), entityRef,
                                entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                entityTypeDetail.getEntityTypeName(), entityAttributeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                var entityTypeDetail = entityInstance.getEntityType().getLastDetail();
                
                addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityTypeDetail.getEntityTypeName(), entityAttributeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
        }
        
        return result;
    }
    
}
