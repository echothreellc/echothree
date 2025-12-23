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

import com.echothree.control.user.core.common.form.GetEntityListItemChoicesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetEntityListItemChoicesCommand
        extends BaseSimpleCommand<GetEntityListItemChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
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
    public GetEntityListItemChoicesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getGetEntityListItemChoicesResult();
        var entityRef = form.getEntityRef();
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var parameterCount = (entityRef != null && componentVendorName == null && entityTypeName == null? 1: 0)
                + (entityRef == null && componentVendorName != null && entityTypeName != null? 1: 0);
        
        if(parameterCount == 1) {
            EntityType entityType = null;
            
            if(entityRef == null) {
                var componentVendor = componentControl.getComponentVendorByName(componentVendorName);
                
                if(componentVendor != null) {
                    entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName);
                    
                    if(entityType == null) {
                        addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                }
            } else {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                var entityInstance = entityInstanceControl.getEntityInstanceByEntityRef(entityRef);
                
                if(entityInstance != null) {
                    entityType = entityInstance.getEntityType();
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
                }
            }
            
            if(!hasExecutionErrors()) {
                var entityAttributeName = form.getEntityAttributeName();
                var entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);
                
                if(entityAttribute != null) {
                    var entityAttributeType = entityAttribute.getLastDetail().getEntityAttributeType();
                    var entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();
                    
                    if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())
                            || entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
                        var defaultEntityListItemChoice = form.getDefaultEntityListItemChoice();
                        var allowNullChoice = Boolean.valueOf(form.getAllowNullChoice());
                        
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
