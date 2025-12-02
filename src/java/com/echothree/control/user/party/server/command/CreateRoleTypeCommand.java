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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreateRoleTypeForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.RoleType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateRoleTypeCommand
        extends BaseSimpleCommand<CreateRoleTypeForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("RoleTypeName", FieldType.STRING, true, null, 40L),
                new FieldDefinition("ParentRoleTypeName", FieldType.STRING, false, null, 40L)
                ));
    }
    
    /** Creates a new instance of CreateRoleTypeCommand */
    public CreateRoleTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var roleTypeName = form.getRoleTypeName();
        var roleType = partyControl.getRoleTypeByName(roleTypeName);
        
        if(roleType == null) {
            var parentRoleTypeName = form.getParentRoleTypeName();
            RoleType parentRoleType = null;
            
            if(parentRoleTypeName != null)
                parentRoleType = partyControl.getRoleTypeByName(parentRoleTypeName);
            
            if(parentRoleTypeName == null || (parentRoleTypeName != null && parentRoleType != null)) {
                partyControl.createRoleType(roleTypeName, parentRoleType);
            } // TODO: error, unknown parentRoleTypeName
        } // TODO: error, duplicate roleTypeName
        
        return null;
    }
    
}
