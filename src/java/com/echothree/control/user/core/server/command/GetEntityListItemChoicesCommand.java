// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.GetEntityListItemChoicesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.GetEntityListItemChoicesResult;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetEntityListItemChoicesCommand
        extends BaseSimpleCommand<GetEntityListItemChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityListItem.name(), SecurityRoles.Choices.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetEntityListItemChoicesCommand */
    public GetEntityListItemChoicesCommand(UserVisitPK userVisitPK, GetEntityListItemChoicesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        GetEntityListItemChoicesResult result = CoreResultFactory.getGetEntityListItemChoicesResult();
        String entityRef = form.getEntityRef();
        String componentVendorName = form.getComponentVendorName();
        String entityTypeName = form.getEntityTypeName();
        int parameterCount = (entityRef != null && componentVendorName == null && entityTypeName == null? 1: 0)
                + (entityRef == null && componentVendorName != null && entityTypeName != null? 1: 0);
        
        if(parameterCount == 1) {
            var coreControl = getCoreControl();
            EntityType entityType = null;
            
            if(entityRef == null) {
                ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
                
                if(componentVendor != null) {
                    entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
                    
                    if(entityType == null) {
                        addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                }
            } else {
                EntityInstance entityInstance = coreControl.getEntityInstanceByEntityRef(entityRef);
                
                if(entityInstance != null) {
                    entityType = entityInstance.getEntityType();
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
                }
            }
            
            if(!hasExecutionErrors()) {
                String entityAttributeName = form.getEntityAttributeName();
                EntityAttribute entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);
                
                if(entityAttribute != null) {
                    EntityAttributeType entityAttributeType = entityAttribute.getLastDetail().getEntityAttributeType();
                    String entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();
                    
                    if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())
                            || entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
                        String defaultEntityListItemChoice = form.getDefaultEntityListItemChoice();
                        Boolean allowNullChoice = Boolean.valueOf(form.getAllowNullChoice());
                        
                        result.setEntityListItemChoices(coreControl.getEntityListItemChoices(defaultEntityListItemChoice,
                                getPreferredLanguage(), allowNullChoice, entityAttribute));
                    } else {
                        addExecutionError(ExecutionErrors.InvalidEntityAttributeType.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), entityAttributeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
