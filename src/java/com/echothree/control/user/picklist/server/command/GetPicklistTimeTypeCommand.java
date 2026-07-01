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

package com.echothree.control.user.picklist.server.command;

import com.echothree.control.user.picklist.common.form.GetPicklistTimeTypeForm;
import com.echothree.control.user.picklist.common.result.PicklistResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.control.picklist.server.logic.PicklistTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPicklistTimeTypeCommand
        extends BaseSimpleCommand<GetPicklistTimeTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PicklistTimeType.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PicklistTimeTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    PicklistControl picklistControl;

    @Inject
    PicklistTypeLogic picklistTypeLogic;

    /** Creates a new instance of GetPicklistTimeTypeCommand */
    public GetPicklistTimeTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = PicklistResultFactory.getGetPicklistTimeTypeResult();
        var picklistType = picklistTypeLogic.getPicklistTypeByName(this, form.getPicklistTypeName());

        if(!hasExecutionErrors()) {
            var picklistTimeTypeName = form.getPicklistTimeTypeName();
            var picklistTimeType = picklistControl.getPicklistTimeTypeByName(picklistType, picklistTimeTypeName);

            if(picklistTimeType != null) {
                result.setPicklistTimeType(picklistControl.getPicklistTimeTypeTransfer(getUserVisit(), picklistTimeType));

                sendEvent(picklistTimeType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownPicklistTimeTypeName.name(), picklistTimeTypeName);
            }
        }

        return result;
    }
    
}
