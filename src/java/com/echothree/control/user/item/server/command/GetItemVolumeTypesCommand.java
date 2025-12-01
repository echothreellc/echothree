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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemVolumeTypesForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemVolumeType;
import com.echothree.model.data.item.server.factory.ItemVolumeTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class GetItemVolumeTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<ItemVolumeType, GetItemVolumeTypesForm> {

    @Inject
    ItemControl itemControl;

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemVolumeType.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetItemVolumeTypesCommand */
    public GetItemVolumeTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return itemControl.countItemVolumeTypes();
    }

    @Override
    protected Collection<ItemVolumeType> getEntities() {
        return itemControl.getItemVolumeTypes();
    }

    @Override
    protected BaseResult getResult(Collection<ItemVolumeType> entities) {
        var result = ItemResultFactory.getGetItemVolumeTypesResult();

        if(entities != null) {
            if(session.hasLimit(ItemVolumeTypeFactory.class)) {
                result.setItemVolumeTypeCount(getTotalEntities());
            }

            result.setItemVolumeTypes(itemControl.getItemVolumeTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
