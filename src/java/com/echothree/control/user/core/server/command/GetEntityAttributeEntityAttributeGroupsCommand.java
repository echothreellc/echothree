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

import com.echothree.control.user.core.common.form.GetEntityAttributeEntityAttributeGroupsForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetEntityAttributeEntityAttributeGroupsCommand
        extends BasePaginatedMultipleEntitiesCommand<EntityAttributeEntityAttributeGroup, GetEntityAttributeEntityAttributeGroupsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttribute.name(), SecurityRoles.EntityAttributeEntityAttributeGroup.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeGroupName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetEntityAttributeEntityAttributeGroupsCommand */
    public GetEntityAttributeEntityAttributeGroupsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    EntityAttribute entityAttribute;
    EntityAttributeGroup entityAttributeGroup;

    @Override
    protected void handleForm() {
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var entityAttributeName = form.getEntityAttributeName();
        var entityAttributeGroupName = form.getEntityAttributeGroupName();
        var parameterCount = ((componentVendorName != null) && (entityTypeName != null) && (entityAttributeName != null) ? 1 : 0)
                + (entityAttributeGroupName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(entityAttributeGroupName == null) {
                entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByName(this, componentVendorName, entityTypeName, entityAttributeName);
            } else { // entityAttributeGroup
                entityAttributeGroup = EntityAttributeLogic.getInstance().getEntityAttributeGroupByName(this, entityAttributeGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long totalEntities = null;

        if(!hasExecutionErrors()) {

            if(entityAttribute != null) {
                totalEntities = coreControl.countEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute);
            } else {
                totalEntities = coreControl.countEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup);
            }
        }

        return totalEntities;
    }

    @Override
    protected Collection<EntityAttributeEntityAttributeGroup> getEntities() {
        Collection<EntityAttributeEntityAttributeGroup> result = null;

        if(!hasExecutionErrors()) {

            if(entityAttribute != null) {
                result = coreControl.getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute);
            } else { // entityAttributeGroup
                result = coreControl.getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup);
            }
        }

        return result;
    }

    @Override
    protected BaseResult getResult(Collection<EntityAttributeEntityAttributeGroup> entities) {
        var result = CoreResultFactory.getGetEntityAttributeEntityAttributeGroupsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(entityAttributeGroup == null) {
                result.setEntityAttribute(coreControl.getEntityAttributeTransfer(userVisit, entityAttribute, null));
            } else {
                result.setEntityAttributeGroup(coreControl.getEntityAttributeGroupTransfer(userVisit, entityAttributeGroup, null));
            }

            result.setEntityAttributeEntityAttributeGroups(coreControl.getEntityAttributeEntityAttributeGroupTransfers(userVisit, entities, null));
        }

        return result;
    }

}
