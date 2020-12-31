// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetEntityTypesCommand
        extends BaseMultipleEntitiesCommand<EntityType, GetEntityTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityType.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetEntityTypesCommand */
    public GetEntityTypesCommand(UserVisitPK userVisitPK, GetEntityTypesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    ComponentVendor componentVendor;

    @Override
    protected Collection<EntityType> getEntities() {
        var coreControl = getCoreControl();
        var componentVendorName = form.getComponentVendorName();
        Collection<EntityType> entities = null;

        if(componentVendorName == null) {
            entities = coreControl.getEntityTypes();
        } else {
            componentVendor = ComponentVendorLogic.getInstance().getComponentVendorByName(this, componentVendorName);

            if(!hasExecutionErrors()) {
                entities = coreControl.getEntityTypesByComponentVendor(componentVendor);
            } else {
                addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getTransfers(Collection<EntityType> entities) {
        var result = CoreResultFactory.getGetEntityTypesResult();

        if(entities != null) {
            var coreControl = getCoreControl();
            var userVisit = getUserVisit();

            if(componentVendor != null) {
                result.setComponentVendor(coreControl.getComponentVendorTransfer(userVisit, componentVendor));

                if(session.hasLimit(EntityTypeFactory.class)) {
                    result.setEntityTypeCount(coreControl.countEntityTypesByComponentVendor(componentVendor));
                }
            } else {
                if(session.hasLimit(EntityTypeFactory.class)) {
                    result.setEntityTypeCount(coreControl.countEntityTypes());
                }
            }

            result.setEntityTypes(coreControl.getEntityTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
