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

import com.echothree.control.user.item.common.form.GetItemDescriptionTypeUsesForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUse;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseType;
import com.echothree.model.data.item.server.factory.ItemDescriptionTypeUseFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetItemDescriptionTypeUsesCommand
        extends BasePaginatedMultipleEntitiesCommand<ItemDescriptionTypeUse, GetItemDescriptionTypeUsesForm> {

    @Inject
    ItemControl itemControl;

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescriptionTypeUse.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemDescriptionTypeUseTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetItemDescriptionTypeUsesCommand */
    public GetItemDescriptionTypeUsesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    ItemDescriptionTypeUseType itemDescriptionTypeUseType;
    ItemDescriptionType itemDescriptionType;

    @Override
    protected void handleForm() {
        var itemDescriptionTypeName = form.getItemDescriptionTypeName();
        var itemDescriptionTypeUseTypeName = form.getItemDescriptionTypeUseTypeName();
        var parameterCount = (itemDescriptionTypeName == null ? 0 : 1) + (itemDescriptionTypeUseTypeName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(itemDescriptionTypeUseTypeName != null) {
                itemDescriptionTypeUseType = itemControl.getItemDescriptionTypeUseTypeByName(itemDescriptionTypeUseTypeName);

                if(itemDescriptionTypeUseType == null) {
                    addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeUseTypeName.name(), itemDescriptionTypeUseTypeName);
                }
            } else {
                itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);

                if(itemDescriptionType == null) {
                    addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeName.name(), itemDescriptionTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long totalEntities = null;

        if(!hasExecutionErrors()) {
            if(itemDescriptionTypeUseType != null) {
                totalEntities = itemControl.countItemDescriptionTypeUsesByItemDescriptionTypeUseType(itemDescriptionTypeUseType);
            } else {
                totalEntities = itemControl.countItemDescriptionTypeUsesByItemDescriptionType(itemDescriptionType);
            }
        }

        return totalEntities;
    }

    @Override
    protected Collection<ItemDescriptionTypeUse> getEntities() {
        Collection<ItemDescriptionTypeUse> entities = null;

        if(!hasExecutionErrors()) {
            if(itemDescriptionTypeUseType != null) {
                entities = itemControl.getItemDescriptionTypeUsesByItemDescriptionTypeUseType(itemDescriptionTypeUseType);
            } else {
                entities = itemControl.getItemDescriptionTypeUsesByItemDescriptionType(itemDescriptionType);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<ItemDescriptionTypeUse> entities) {
        var result = ItemResultFactory.getGetItemDescriptionTypeUsesResult();

        if(entities != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(ItemDescriptionTypeUseFactory.class)) {
                result.setItemDescriptionTypeUseCount(getTotalEntities());
            }

            if(itemDescriptionTypeUseType != null) {
                result.setItemDescriptionTypeUseType(itemControl.getItemDescriptionTypeUseTypeTransfer(userVisit, itemDescriptionTypeUseType));
            }
            
            if(itemDescriptionType != null) {
                result.setItemDescriptionType(itemControl.getItemDescriptionTypeTransfer(userVisit, itemDescriptionType));
            }

            result.setItemDescriptionTypeUses(itemControl.getItemDescriptionTypeUseTransfers(userVisit, entities));
        }

        return result;
    }

}
