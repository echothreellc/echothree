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

import com.echothree.control.user.item.common.form.GetItemDescriptionTypesForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
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

public class GetItemDescriptionTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<ItemDescriptionType, GetItemDescriptionTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescriptionType.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ParentItemDescriptionTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetItemDescriptionTypesCommand */
    public GetItemDescriptionTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private ItemDescriptionType parentItemDescriptionType;
    
    @Override
    protected void handleForm() {
        var itemControl = Session.getModelController(ItemControl.class);
        var parentItemDescriptionTypeName = form.getParentItemDescriptionTypeName();

        parentItemDescriptionType = parentItemDescriptionTypeName == null ? null : itemControl.getItemDescriptionTypeByName(parentItemDescriptionTypeName);

        if(parentItemDescriptionTypeName != null && parentItemDescriptionType == null) {
            addExecutionError(ExecutionErrors.UnknownParentItemDescriptionTypeName.name(), parentItemDescriptionTypeName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        if(hasExecutionErrors())
            return null;

        var itemControl = Session.getModelController(ItemControl.class);

        return parentItemDescriptionType == null
                ? itemControl.countItemDescriptionTypes()
                : itemControl.countItemDescriptionTypesByParentItemDescriptionType(parentItemDescriptionType);
    }

    @Override
    protected Collection<ItemDescriptionType> getEntities() {
        Collection<ItemDescriptionType> itemDescriptionTypes = null;
        
        if(!hasExecutionErrors()) {
            var itemControl = Session.getModelController(ItemControl.class);

            itemDescriptionTypes = parentItemDescriptionType == null ? itemControl.getItemDescriptionTypes()
                    : itemControl.getItemDescriptionTypesByParentItemDescriptionType(parentItemDescriptionType);
        }

        return itemDescriptionTypes;
    }

    @Override
    protected BaseResult getResult(Collection<ItemDescriptionType> entities) {
        var result = ItemResultFactory.getGetItemDescriptionTypesResult();

        if(entities != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(com.echothree.model.data.item.server.factory.ItemDescriptionTypeFactory.class)) {
                result.setItemDescriptionTypeCount(getTotalEntities());
            }

            result.setParentItemDescriptionType(parentItemDescriptionType == null ? null : itemControl.getItemDescriptionTypeTransfer(userVisit, parentItemDescriptionType));
            result.setItemDescriptionTypes(itemControl.getItemDescriptionTypeTransfers(userVisit, entities));
        }

        return result;
    }
    
}
