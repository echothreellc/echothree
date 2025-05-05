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

import com.echothree.control.user.core.common.form.GetEntityAttributeTypesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;

public class GetEntityAttributeTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<EntityAttributeType, GetEntityAttributeTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttributeType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    /** Creates a new instance of GetEntityAttributeTypesCommand */
    public GetEntityAttributeTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return getCoreControl().countEntityAttributeTypes();
    }

    @Override
    protected Collection<EntityAttributeType> getEntities() {
        var coreControl = getCoreControl();
        
        return coreControl.getEntityAttributeTypes();
    }
    
    @Override
    protected BaseResult getResult(Collection<EntityAttributeType> entities) {
        var result = CoreResultFactory.getGetEntityAttributeTypesResult();
        var coreControl = getCoreControl();
        
        result.setEntityAttributeTypes(coreControl.getEntityAttributeTypeTransfers(getUserVisit(), entities));
        
        return result;
    }
    
}
