// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.tag.common.form.GetTagScopeEntityTypesForm;
import com.echothree.control.user.tag.common.result.GetTagScopeEntityTypesResult;
import com.echothree.control.user.tag.common.result.TagResultFactory;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.TagControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
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

public class GetTagScopeEntityTypesCommand
        extends BaseSimpleCommand<GetTagScopeEntityTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TagScopeEntityType.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetTagScopeEntityTypesCommand */
    public GetTagScopeEntityTypesCommand(UserVisitPK userVisitPK, GetTagScopeEntityTypesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetTagScopeEntityTypesResult result = TagResultFactory.getGetTagScopeEntityTypesResult();
        String tagScopeName = form.getTagScopeName();
        String componentVendorName = form.getComponentVendorName();
        String entityTypeName = form.getEntityTypeName();
        int parameterCount = (tagScopeName == null? 0: 1) + (componentVendorName == null && entityTypeName == null? 0: 1);
        
        if(parameterCount == 1) {
            var tagControl = (TagControl)Session.getModelController(TagControl.class);
            UserVisit userVisit = getUserVisit();
            
            if(tagScopeName != null) {
                TagScope tagScope = tagControl.getTagScopeByName(tagScopeName);
                
                if(tagScope != null) {
                    result.setTagScope(tagControl.getTagScopeTransfer(userVisit, tagScope));
                    result.setTagScopeEntityTypes(tagControl.getTagScopeEntityTypeTransfersByTagScope(userVisit, tagScope));
                } else {
                    addExecutionError(ExecutionErrors.UnknownTagScopeName.name(), tagScopeName);
                }
            } else {
                var coreControl = getCoreControl();
                ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
                
                if(componentVendor != null) {
                    EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
                    
                    if(entityType != null) {
                        result.setEntityType(coreControl.getEntityTypeTransfer(userVisit, entityType));
                        result.setTagScopeEntityTypes(tagControl.getTagScopeEntityTypeTransfersByEntityType(userVisit, entityType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        
        return result;
    }
    
}
