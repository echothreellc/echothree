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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.PartyAliasEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.result.EditPartyAliasResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.PartyAliasSpec;
import com.echothree.control.user.party.server.command.util.PartyAliasUtil;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.PartyAlias;
import com.echothree.model.data.party.server.entity.PartyAliasType;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditPartyAliasCommand
        extends BaseAbstractEditCommand<PartyAliasSpec, PartyAliasEdit, EditPartyAliasResult, PartyAlias, PartyAlias> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyAliasCommand */
    public EditPartyAliasCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(PartyAliasUtil.getInstance().getSecurityRoleGroupNameByPartyName(spec.getPartyName()), SecurityRoles.Edit.name())
                )))
        )));
    }

    @Override
    public EditPartyAliasResult getResult() {
        return PartyResultFactory.getEditPartyAliasResult();
    }

    @Override
    public PartyAliasEdit getEdit() {
        return PartyEditFactory.getPartyAliasEdit();
    }

    PartyAliasType partyAliasType;
    
    @Override
    public PartyAlias getEntity(EditPartyAliasResult result) {
        var partyControl = Session.getModelController(PartyControl.class);
        PartyAlias partyAlias = null;
        var partyName = spec.getPartyName();
        var party = partyControl.getPartyByName(partyName);

        if(party != null) {
            var partyType = party.getLastDetail().getPartyType();
            var partyAliasTypeName = spec.getPartyAliasTypeName();

            partyAliasType = partyControl.getPartyAliasTypeByName(partyType, partyAliasTypeName);

            if(partyAliasType != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    partyAlias = partyControl.getPartyAlias(party, partyAliasType);
                } else { // EditMode.UPDATE
                    partyAlias = partyControl.getPartyAliasForUpdate(party, partyAliasType);
                }

                if(partyAlias != null) {
                    result.setPartyAlias(partyControl.getPartyAliasTransfer(getUserVisit(), partyAlias));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyAlias.name(), partyName, partyAliasTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyAliasTypeName.name(), partyType.getPartyTypeName(), partyAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return partyAlias;
    }

    @Override
    public PartyAlias getLockEntity(PartyAlias partyAlias) {
        return partyAlias;
    }

    @Override
    public void fillInResult(EditPartyAliasResult result, PartyAlias partyAlias) {
        var partyControl = Session.getModelController(PartyControl.class);

        result.setPartyAlias(partyControl.getPartyAliasTransfer(getUserVisit(), partyAlias));
    }

    @Override
    public void doLock(PartyAliasEdit edit, PartyAlias partyAlias) {
        edit.setAlias(partyAlias.getAlias());
    }

    @Override
    public void canUpdate(PartyAlias partyAlias) {
        var partyControl = Session.getModelController(PartyControl.class);
        var alias = edit.getAlias();
        var duplicatePartyAlias = partyControl.getPartyAliasByAlias(partyAliasType, alias);

        if(duplicatePartyAlias != null && !partyAlias.equals(duplicatePartyAlias)) {
            var partyAliasTypeDetail = partyAlias.getPartyAliasType().getLastDetail();

            addExecutionError(ExecutionErrors.DuplicatePartyAlias.name(), partyAliasTypeDetail.getPartyType().getPartyTypeName(),
                    partyAliasTypeDetail.getPartyAliasTypeName(), alias);
        }
    }

    @Override
    public void doUpdate(PartyAlias partyAlias) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyAliasValue = partyControl.getPartyAliasValue(partyAlias);

        partyAliasValue.setAlias(edit.getAlias());

        partyControl.updatePartyAliasFromValue(partyAliasValue, getPartyPK());
    }

}
