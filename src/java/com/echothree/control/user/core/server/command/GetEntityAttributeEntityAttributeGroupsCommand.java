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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.GetEntityAttributeEntityAttributeGroupsForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.GetEntityAttributeEntityAttributeGroupsResult;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetEntityAttributeEntityAttributeGroupsCommand
        extends BaseSimpleCommand<GetEntityAttributeEntityAttributeGroupsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttribute.name(), SecurityRoles.EntityAttributeEntityAttributeGroup.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeGroupName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetEntityAttributeEntityAttributeGroupsCommand */
    public GetEntityAttributeEntityAttributeGroupsCommand(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetEntityAttributeEntityAttributeGroupsResult result = CoreResultFactory.getGetEntityAttributeEntityAttributeGroupsResult();
        String componentVendorName = form.getComponentVendorName();
        String entityTypeName = form.getEntityTypeName();
        String entityAttributeName = form.getEntityAttributeName();
        String entityAttributeGroupName = form.getEntityAttributeGroupName();
        int parameterCount = ((componentVendorName != null) && (entityTypeName != null) && (entityAttributeName != null) ? 1 : 0)
                + (entityAttributeGroupName != null ? 1 : 0);

        if(parameterCount == 1) {
            var coreControl = getCoreControl();
            UserVisit userVisit = getUserVisit();
            
            if(entityAttributeGroupName == null) {
                EntityAttribute entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByName(this, componentVendorName, entityTypeName, entityAttributeName);
                
                if(!hasExecutionErrors()) {
                    result.setEntityAttribute(coreControl.getEntityAttributeTransfer(userVisit, entityAttribute, null));
                    result.setEntityAttributeEntityAttributeGroups(coreControl.getEntityAttributeEntityAttributeGroupTransfersByEntityAttribute(userVisit, entityAttribute, null));
                }
            } else {
                EntityAttributeGroup entityAttributeGroup = EntityAttributeLogic.getInstance().getEntityAttributeGroupByName(this, entityAttributeGroupName);
                
                if(!hasExecutionErrors()) {
                    result.setEntityAttributeGroup(coreControl.getEntityAttributeGroupTransfer(userVisit, entityAttributeGroup, null));
                    result.setEntityAttributeEntityAttributeGroups(coreControl.getEntityAttributeEntityAttributeGroupTransfersByEntityAttributeGroup(userVisit, entityAttributeGroup, null));
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        
        
        
        
        
        

        
        return result;
    }
    
}
