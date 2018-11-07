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

package com.echothree.control.user.tag.server.command;

import com.echothree.control.user.tag.common.form.DeleteTagScopeEntityTypeForm;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.TagControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.tag.server.entity.TagScopeEntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteTagScopeEntityTypeCommand
        extends BaseSimpleCommand<DeleteTagScopeEntityTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TagScopeEntityType.name(), SecurityRoles.Delete.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteTagScopeEntityTypeCommand */
    public DeleteTagScopeEntityTypeCommand(UserVisitPK userVisitPK, DeleteTagScopeEntityTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        TagControl tagControl = (TagControl)Session.getModelController(TagControl.class);
        String tagScopeName = form.getTagScopeName();
        TagScope tagScope = tagControl.getTagScopeByName(tagScopeName);
        
        if(tagScope != null) {
            CoreControl coreControl = getCoreControl();
            String componentVendorName = form.getComponentVendorName();
            ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
            
            if(componentVendor != null) {
                String entityTypeName = form.getEntityTypeName();
                EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
                
                if(entityType != null) {
                    TagScopeEntityType tagScopeEntityType = tagControl.getTagScopeEntityTypeForUpdate(tagScope, entityType);
                    
                    if(tagScopeEntityType != null) {
                        tagControl.deleteTagScopeEntityType(tagScopeEntityType, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTagScopeEntityType.name(), tagScopeName, componentVendorName, entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTagScopeName.name(), tagScopeName);
        }
        
        return null;
    }
    
}
