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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetItemDescriptionTypesCommand
        extends BaseMultipleEntitiesCommand<ItemDescriptionType, GetItemDescriptionTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescriptionType.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ParentItemDescriptionTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemDescriptionTypesCommand */
    public GetItemDescriptionTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    ItemDescriptionType parentItemDescriptionType;
    
    @Override
    protected Collection<ItemDescriptionType> getEntities() {
        var itemControl = Session.getModelController(ItemControl.class);
        var parentItemDescriptionTypeName = form.getParentItemDescriptionTypeName();
        Collection<ItemDescriptionType> itemDescriptionTypes = null;

        parentItemDescriptionType = parentItemDescriptionTypeName == null? null: itemControl.getItemDescriptionTypeByName(parentItemDescriptionTypeName);

        if(parentItemDescriptionTypeName == null || parentItemDescriptionType != null) {
            itemDescriptionTypes = parentItemDescriptionType == null ? itemControl.getItemDescriptionTypes()
                    : itemControl.getItemDescriptionTypesByParentItemDescriptionType(parentItemDescriptionType);
        } else {
            addExecutionError(ExecutionErrors.UnknownParentItemDescriptionTypeName.name(), parentItemDescriptionTypeName);
        }

        return itemDescriptionTypes;
    }

    @Override
    protected BaseResult getResult(Collection<ItemDescriptionType> entities) {
        var result = ItemResultFactory.getGetItemDescriptionTypesResult();

        if(entities != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var userVisit = getUserVisit();

            result.setParentItemDescriptionType(parentItemDescriptionType == null ? null : itemControl.getItemDescriptionTypeTransfer(userVisit, parentItemDescriptionType));
            result.setItemDescriptionTypes(itemControl.getItemDescriptionTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
