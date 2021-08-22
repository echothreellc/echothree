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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemDescriptionTypeUsesForm;
import com.echothree.control.user.item.common.result.GetItemDescriptionTypeUsesResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
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

public class GetItemDescriptionTypeUsesCommand
        extends BaseSimpleCommand<GetItemDescriptionTypeUsesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescriptionTypeUse.name(), SecurityRoles.List.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemDescriptionTypeUseTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemDescriptionTypeUsesCommand */
    public GetItemDescriptionTypeUsesCommand(UserVisitPK userVisitPK, GetItemDescriptionTypeUsesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        GetItemDescriptionTypeUsesResult result = ItemResultFactory.getGetItemDescriptionTypeUsesResult();
        String itemDescriptionTypeName = form.getItemDescriptionTypeName();
        String itemDescriptionTypeUseTypeName = form.getItemDescriptionTypeUseTypeName();
        var parameterCount = (itemDescriptionTypeName == null ? 0 : 1) + (itemDescriptionTypeUseTypeName == null ? 0 : 1);
        
        if(parameterCount == 1) {
            UserVisit userVisit = getUserVisit();
            
            if(itemDescriptionTypeUseTypeName != null) {
                ItemDescriptionTypeUseType itemDescriptionTypeUseType = itemControl.getItemDescriptionTypeUseTypeByName(itemDescriptionTypeUseTypeName);

                if(itemDescriptionTypeUseType != null) {
                    result.setItemDescriptionTypeUseType(itemControl.getItemDescriptionTypeUseTypeTransfer(userVisit, itemDescriptionTypeUseType));
                    result.setItemDescriptionTypeUses(itemControl.getItemDescriptionTypeUseTransfersByItemDescriptionTypeUseType(userVisit, itemDescriptionTypeUseType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeUseTypeName.name(), itemDescriptionTypeUseTypeName);
                }
            } else if(itemDescriptionTypeName != null) {
                ItemDescriptionType itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);
                
                if(itemDescriptionType != null) {
                    result.setItemDescriptionType(itemControl.getItemDescriptionTypeTransfer(userVisit, itemDescriptionType));
                    result.setItemDescriptionTypeUses(itemControl.getItemDescriptionTypeUseTransfersByItemDescriptionType(userVisit, itemDescriptionType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeName.name(), itemDescriptionTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
