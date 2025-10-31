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

import com.echothree.control.user.core.common.form.CreateEntityAttributeEntityAttributeGroupForm;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateEntityAttributeEntityAttributeGroupCommand
        extends BaseSimpleCommand<CreateEntityAttributeEntityAttributeGroupForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttribute.name(), SecurityRoles.EntityAttributeEntityAttributeGroup.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityAttributeGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateEntityAttributeEntityAttributeGroupCommand */
    public CreateEntityAttributeEntityAttributeGroupCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByName(this, form.getComponentVendorName(), form.getEntityTypeName(), form.getEntityAttributeName());
        
        if(!hasExecutionErrors()) {
            var entityAttributeGroup = EntityAttributeLogic.getInstance().getEntityAttributeGroupByName(this, form.getEntityAttributeGroupName());
            
            if(!hasExecutionErrors()) {
                var coreControl = getCoreControl();
                var entityAttributeEntityAttributeGroup = coreControl.getEntityAttributeEntityAttributeGroup(entityAttribute, entityAttributeGroup);

                if(entityAttributeEntityAttributeGroup == null) {
                    var sortOrder = Integer.valueOf(form.getSortOrder());

                    coreControl.createEntityAttributeEntityAttributeGroup(entityAttribute, entityAttributeGroup, sortOrder, getPartyPK());
                } else {
                    var entityAttributeDetail = entityAttribute.getLastDetail();
                    var entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();
                    
                    addExecutionError(ExecutionErrors.DuplicateEntityAttributeEntityAttributeGroup.name(),
                            entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                            entityAttributeDetail.getEntityAttributeName(), entityAttributeGroup.getLastDetail().getEntityAttributeGroupName());
                }
            }
        }
        
        return null;
    }
    
}
