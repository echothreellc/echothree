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

import com.echothree.control.user.party.common.form.CreatePartyAliasForm;
import com.echothree.control.user.party.server.command.util.PartyAliasUtil;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class CreatePartyAliasCommand
        extends BaseSimpleCommand<CreatePartyAliasForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePartyAliasCommand */
    public CreatePartyAliasCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(PartyAliasUtil.getInstance().getSecurityRoleGroupNameByPartySpec(form), SecurityRoles.Create.name())
                )))
        )));
    }

    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyName = form.getPartyName();
        var party = partyControl.getPartyByName(partyName);

        if(party != null) {
            var partyType = party.getLastDetail().getPartyType();
            var partyAliasTypeName = form.getPartyAliasTypeName();
            var partyAliasType = partyControl.getPartyAliasTypeByName(partyType, partyAliasTypeName);

            if(partyAliasType != null) {
                var partyAliasTypeDetail = partyAliasType.getLastDetail();
                var validationPattern = partyAliasTypeDetail.getValidationPattern();
                var alias = form.getAlias();

                if(validationPattern != null) {
                    var pattern = Pattern.compile(validationPattern);
                    var m = pattern.matcher(alias);

                    if(!m.matches()) {
                        addExecutionError(ExecutionErrors.InvalidAlias.name(), alias);
                    }
                }

                if(!hasExecutionErrors()) {
                    if(partyControl.getPartyAlias(party, partyAliasType) == null && partyControl.getPartyAliasByAlias(partyAliasType, alias) == null) {
                        partyControl.createPartyAlias(party, partyAliasType, alias, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.DuplicatePartyAlias.name(), partyName, partyAliasTypeName, alias);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyAliasTypeName.name(), partyType.getPartyTypeName(), partyAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return null;
    }
    
}
