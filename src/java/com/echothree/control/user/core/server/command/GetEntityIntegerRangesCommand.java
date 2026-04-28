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

import com.echothree.control.user.core.common.form.GetEntityIntegerRangesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityIntegerRange;
import com.echothree.model.data.core.server.factory.EntityIntegerRangeFactory;
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
import javax.inject.Inject;

@Dependent
public class GetEntityIntegerRangesCommand
        extends BasePaginatedMultipleEntitiesCommand<EntityIntegerRange, GetEntityIntegerRangesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityIntegerRange.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetEntityIntegerRangesCommand */
    public GetEntityIntegerRangesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Inject
    EntityAttributeLogic entityAttributeLogic;

    private EntityAttribute entityAttribute;

    @Override
    protected void handleForm() {
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var entityAttributeName = form.getEntityAttributeName();

        entityAttribute = entityAttributeLogic.getEntityAttributeByName(this, componentVendorName, entityTypeName, entityAttributeName);

        if(!hasExecutionErrors()) {
            var entityAttributeType = entityAttribute.getLastDetail().getEntityAttributeType();
            var entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();

            if(!entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
                addExecutionError(ExecutionErrors.InvalidEntityAttributeType.name(), componentVendorName, entityTypeName, entityAttributeName,
                        entityAttributeTypeName);
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : coreControl.countEntityIntegerRanges(entityAttribute);
    }

    @Override
    protected Collection<EntityIntegerRange> getEntities() {
        return hasExecutionErrors() ? null : coreControl.getEntityIntegerRanges(entityAttribute);
    }

    @Override
    protected BaseResult getResult(Collection<EntityIntegerRange> entities) {
        var result = CoreResultFactory.getGetEntityIntegerRangesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setEntityAttribute(coreControl.getEntityAttributeTransfer(userVisit, entityAttribute, null));

            if(session.hasLimit(EntityIntegerRangeFactory.class)) {
                result.setEntityIntegerRangeCount(getTotalEntities());
            }

            result.setEntityIntegerRanges(coreControl.getEntityIntegerRangeTransfers(userVisit, entities, null));
        }

        return result;
    }

}
