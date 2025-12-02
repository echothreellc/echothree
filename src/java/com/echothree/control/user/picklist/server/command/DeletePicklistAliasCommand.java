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

package com.echothree.control.user.picklist.server.command;

import com.echothree.control.user.picklist.common.form.DeletePicklistAliasForm;
import com.echothree.control.user.picklist.server.command.util.PicklistAliasUtil;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.picklist.server.control.PicklistControl;
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
public class DeletePicklistAliasCommand
        extends BaseSimpleCommand<DeletePicklistAliasForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PicklistName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PicklistAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeletePicklistAliasCommand */
    public DeletePicklistAliasCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(PicklistAliasUtil.getInstance().getSecurityRoleGroupNameByPicklistTypeSpec(form), SecurityRoles.Delete.name())
                )))
        )));
    }

    @Override
    protected BaseResult execute() {
        var picklistControl = Session.getModelController(PicklistControl.class);
        var picklistTypeName = form.getPicklistTypeName();
        var picklistType = picklistControl.getPicklistTypeByName(picklistTypeName);

        if(picklistType != null) {
            var picklistName = form.getPicklistName();
            var picklist = picklistControl.getPicklistByName(picklistType, picklistName);

            if(picklist != null) {
                var picklistAliasTypeName = form.getPicklistAliasTypeName();
                var picklistAliasType = picklistControl.getPicklistAliasTypeByName(picklistType, picklistAliasTypeName);

                if(picklistAliasType != null) {
                    var picklistAlias = picklistControl.getPicklistAliasForUpdate(picklist, picklistAliasType);

                    if(picklistAlias != null) {
                        picklistControl.deletePicklistAlias(picklistAlias, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPicklistAlias.name(), picklistTypeName, picklistName, picklistAliasTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPicklistAliasTypeName.name(), picklistTypeName, picklistAliasTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPicklistName.name(), picklistTypeName, picklistName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPicklistTypeName.name(), picklistTypeName);
        }

        return null;
    }
    
}
