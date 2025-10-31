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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.common.edit.SecurityEditFactory;
import com.echothree.control.user.security.common.edit.SecurityRolePartyTypeEdit;
import com.echothree.control.user.security.common.form.EditSecurityRolePartyTypeForm;
import com.echothree.control.user.security.common.result.EditSecurityRolePartyTypeResult;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.control.user.security.common.spec.SecurityRolePartyTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRolePartyType;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditSecurityRolePartyTypeCommand
        extends BaseAbstractEditCommand<SecurityRolePartyTypeSpec, SecurityRolePartyTypeEdit, EditSecurityRolePartyTypeResult, SecurityRolePartyType, SecurityRole> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SecurityRolePartyType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SecurityRoleName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartySelectorName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }

    /** Creates a new instance of EditSecurityRolePartyTypeCommand */
    public EditSecurityRolePartyTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSecurityRolePartyTypeResult getResult() {
        return SecurityResultFactory.getEditSecurityRolePartyTypeResult();
    }

    @Override
    public SecurityRolePartyTypeEdit getEdit() {
        return SecurityEditFactory.getSecurityRolePartyTypeEdit();
    }

    PartyType partyType;
    
    @Override
    public SecurityRolePartyType getEntity(EditSecurityRolePartyTypeResult result) {
        var securityControl = Session.getModelController(SecurityControl.class);
        SecurityRolePartyType securityRolePartyType = null;
        var securityRoleGroupName = spec.getSecurityRoleGroupName();
        var securityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName);
        
        if(securityRoleGroup != null) {
            var securityRoleName = spec.getSecurityRoleName();
            var securityRole = securityControl.getSecurityRoleByName(securityRoleGroup, securityRoleName);

            if(securityRole != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var partyTypeName = spec.getPartyTypeName();
                
                partyType = partyControl.getPartyTypeByName(partyTypeName);

                if(partyType != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        securityRolePartyType = securityControl.getSecurityRolePartyType(securityRole, partyType);
                    } else { // EditMode.UPDATE
                        securityRolePartyType = securityControl.getSecurityRolePartyTypeForUpdate(securityRole, partyType);
                    }

                    if(securityRolePartyType == null) {
                        addExecutionError(ExecutionErrors.UnknownSecurityRolePartyType.name(), securityRoleName, partyTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSecurityRoleName.name(), securityRoleGroupName, securityRoleName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSecurityRoleGroupName.name(), securityRoleGroupName);
        }

        return securityRolePartyType;
    }

    @Override
    public SecurityRole getLockEntity(SecurityRolePartyType securityRolePartyType) {
        return securityRolePartyType.getSecurityRole();
    }

    @Override
    public void fillInResult(EditSecurityRolePartyTypeResult result, SecurityRolePartyType securityRolePartyType) {
        var securityControl = Session.getModelController(SecurityControl.class);

        result.setSecurityRolePartyType(securityControl.getSecurityRolePartyTypeTransfer(getUserVisit(), securityRolePartyType));
    }

    Selector partySelector;
    
    @Override
    public void doLock(SecurityRolePartyTypeEdit edit, SecurityRolePartyType securityRolePartyType) {
        partySelector = securityRolePartyType.getPartySelector();
        
        edit.setPartySelectorName(partySelector == null? null: partySelector.getLastDetail().getSelectorName());
    }

    @Override
    protected void canUpdate(SecurityRolePartyType securityRolePartyType) {
        var partySelectorName = edit.getPartySelectorName();

        if(partySelectorName != null) {
            var partyTypeName = partyType.getPartyTypeName();
            
            if(partyType.getAllowUserLogins()) {
                var selectorControl = Session.getModelController(SelectorControl.class);
                var selectorKind = selectorControl.getSelectorKindByName(partyTypeName);

                if(selectorKind != null) {
                    var selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.SECURITY_ROLE.name());

                    if(selectorType != null) {
                        partySelector = selectorControl.getSelectorByName(selectorType, partySelectorName);

                        if(partySelector == null) {
                            addExecutionError(ExecutionErrors.UnknownPartySelectorName.name(), partyTypeName, SelectorTypes.SECURITY_ROLE.name(),
                                    partySelectorName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartySelectorTypeName.name(), partyTypeName, SelectorTypes.SECURITY_ROLE.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartySelectorKindName.name(), partyTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidPartyType.name(), partyTypeName);
            }
        }
    }
    
    @Override
    public void doUpdate(SecurityRolePartyType securityRolePartyType) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var securityRolePartyTypeValue = securityControl.getSecurityRolePartyTypeValue(securityRolePartyType);
        
        securityRolePartyTypeValue.setPartySelectorPK(partySelector == null? null: partySelector.getPrimaryKey());
        
        securityControl.updateSecurityRolePartyTypeFromValue(securityRolePartyTypeValue, getPartyPK());
    }

}
