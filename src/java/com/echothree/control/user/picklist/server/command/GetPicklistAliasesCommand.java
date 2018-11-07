// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.picklist.common.form.GetPicklistAliasesForm;
import com.echothree.control.user.picklist.common.result.GetPicklistAliasesResult;
import com.echothree.control.user.picklist.common.result.PicklistResultFactory;
import com.echothree.control.user.picklist.server.command.util.PicklistAliasUtil;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.picklist.server.PicklistControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.picklist.server.entity.Picklist;
import com.echothree.model.data.picklist.server.entity.PicklistType;
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

public class GetPicklistAliasesCommand
        extends BaseSimpleCommand<GetPicklistAliasesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PicklistName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetPicklistAliasesCommand */
    public GetPicklistAliasesCommand(UserVisitPK userVisitPK, GetPicklistAliasesForm form) {
        super(userVisitPK, form, new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(PicklistAliasUtil.getInstance().getSecurityRoleGroupNameByPicklistTypeSpec(form), SecurityRoles.List.name())
                        )))
                ))), FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);
        GetPicklistAliasesResult result = PicklistResultFactory.getGetPicklistAliasesResult();
        String picklistTypeName = form.getPicklistTypeName();
        PicklistType picklistType = picklistControl.getPicklistTypeByName(picklistTypeName);

        if(picklistType != null) {
            String picklistName = form.getPicklistName();
            Picklist picklist = picklistControl.getPicklistByName(picklistType, picklistName);

            if(picklist != null) {
                UserVisit userVisit = getUserVisit();

                result.setPicklist(picklistControl.getPicklistTransfer(userVisit, picklist));
                result.setPicklistAliases(picklistControl.getPicklistAliasTransfersByPicklist(userVisit, picklist));
            } else {
                addExecutionError(ExecutionErrors.UnknownPicklistName.name(), picklistTypeName, picklistName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPicklistTypeName.name(), picklistTypeName);
        }

        return result;
    }
    
}
