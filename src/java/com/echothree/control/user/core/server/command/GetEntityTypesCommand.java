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

import com.echothree.control.user.core.common.form.GetEntityTypesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.logic.ComponentVendorLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.factory.EntityTypeFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;

public class GetEntityTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<EntityType, GetEntityTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityType.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetEntityTypesCommand */
    public GetEntityTypesCommand(UserVisitPK userVisitPK, GetEntityTypesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    ComponentVendor componentVendor;

    @Override
    protected void handleForm() {
        var componentVendorName = form.getComponentVendorName();

        if(componentVendorName != null) {
            componentVendor = ComponentVendorLogic.getInstance().getComponentVendorByName(this, componentVendorName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null :
                componentVendor == null ?
                getEntityTypeControl().countEntityTypes() :
                getEntityTypeControl().countEntityTypesByComponentVendor(componentVendor);
    }

    @Override
    protected Collection<EntityType> getEntities() {
        Collection<EntityType> entities = null;

        if(!hasExecutionErrors()) {
            entities = componentVendor == null ?
                    getEntityTypeControl().getEntityTypes():
                    getEntityTypeControl().getEntityTypesByComponentVendor(componentVendor);
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<EntityType> entities) {
        var result = CoreResultFactory.getGetEntityTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(componentVendor != null) {
                result.setComponentVendor(getComponentControl().getComponentVendorTransfer(userVisit, componentVendor));

                if(session.hasLimit(EntityTypeFactory.class)) {
                    result.setEntityTypeCount(getEntityTypeControl().countEntityTypesByComponentVendor(componentVendor));
                }
            } else {
                if(session.hasLimit(EntityTypeFactory.class)) {
                    result.setEntityTypeCount(getEntityTypeControl().countEntityTypes());
                }
            }

            result.setEntityTypes(getEntityTypeControl().getEntityTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
