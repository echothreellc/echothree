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

import com.echothree.control.user.core.common.form.GetEntityAliasTypesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;

public class GetEntityAliasTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<EntityAliasType, GetEntityAliasTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAliasType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }
    
    /** Creates a new instance of GetEntityAliasTypesCommand */
    public GetEntityAliasTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    EntityType entityType;

    @Override
    protected void handleForm() {
        entityType = EntityTypeLogic.getInstance().getEntityTypeByUniversalSpec(this, form);
    }

    @Override
    protected Long getTotalEntities() {
        var entityAliasControl = Session.getModelController(EntityAliasControl.class);

        return hasExecutionErrors() ?
                null :
                entityAliasControl.countEntityAliasTypesByEntityType(entityType);
    }


    @Override
    protected Collection<EntityAliasType> getEntities() {
        var entityAliasControl = Session.getModelController(EntityAliasControl.class);

        return hasExecutionErrors() ?
                null :
                entityAliasControl.getEntityAliasTypesByEntityType(entityType);
    }

    @Override
    protected BaseResult getResult(Collection<EntityAliasType> entities) {
        var result = CoreResultFactory.getGetEntityAliasTypesResult();

        if(entities != null) {
            var entityAliasControl = Session.getModelController(EntityAliasControl.class);

            result.setEntityAliasTypes(entityAliasControl.getEntityAliasTypeTransfers(getUserVisit(), entities, null));
        }

        return result;
    }

}
