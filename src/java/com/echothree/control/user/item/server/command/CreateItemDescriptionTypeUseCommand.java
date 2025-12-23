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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.CreateItemDescriptionTypeUseForm;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateItemDescriptionTypeUseCommand
        extends BaseSimpleCommand<CreateItemDescriptionTypeUseForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescriptionTypeUse.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemDescriptionTypeUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateItemDescriptionTypeUseCommand */
    public CreateItemDescriptionTypeUseCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemDescriptionTypeName = form.getItemDescriptionTypeName();
        var itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);

        if(itemDescriptionType != null) {
            var itemDescriptionTypeUseTypeName = form.getItemDescriptionTypeUseTypeName();
            var itemDescriptionTypeUseType = itemControl.getItemDescriptionTypeUseTypeByName(itemDescriptionTypeUseTypeName);

            if(itemDescriptionTypeUseType != null) {
                var itemDescriptionTypeUse = itemControl.getItemDescriptionTypeUse(itemDescriptionType, itemDescriptionTypeUseType);

                if(itemDescriptionTypeUse == null) {
                    itemControl.createItemDescriptionTypeUse(itemDescriptionType, itemDescriptionTypeUseType, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.DuplicateItemDescriptionTypeUse.name(), itemDescriptionTypeName, itemDescriptionTypeUseTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeUseTypeName.name(), itemDescriptionTypeUseTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeName.name(), itemDescriptionTypeName);
        }
        
        return null;
    }
    
}
