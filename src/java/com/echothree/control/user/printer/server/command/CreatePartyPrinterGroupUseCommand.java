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

package com.echothree.control.user.printer.server.command;

import com.echothree.control.user.printer.common.form.CreatePartyPrinterGroupUseForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Party;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreatePartyPrinterGroupUseCommand
        extends BaseSimpleCommand<CreatePartyPrinterGroupUseForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyPrinterGroupUse.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PrinterGroupUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PrinterGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of CreatePartyPrinterGroupUseCommand */
    public CreatePartyPrinterGroupUseCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyName = form.getPartyName();
        Party party;

        if(partyName != null) {
            var partyControl = Session.getModelController(PartyControl.class);

            party = partyControl.getPartyByName(partyName);
            
            if(party == null) {
                addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
            }
        } else {
            party = getParty();
        }

        if(!hasExecutionErrors()) {
            var printerControl = Session.getModelController(PrinterControl.class);
            var printerGroupUseTypeName = form.getPrinterGroupUseTypeName();
            var printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(printerGroupUseTypeName);

            if(printerGroupUseType != null) {
                var partyPrinterGroupUse = printerControl.getPartyPrinterGroupUse(party, printerGroupUseType);

                if(partyPrinterGroupUse == null) {
                    var printerGroupName = form.getPrinterGroupName();
                    var printerGroup = printerControl.getPrinterGroupByName(printerGroupName);

                    if(printerGroup != null) {
                        printerControl.createPartyPrinterGroupUse(party, printerGroupUseType, printerGroup, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPrinterGroupName.name(), printerGroupName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicatePartyPrinterGroupUse.name(), party.getLastDetail().getPartyName(), printerGroupUseTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPrinterGroupUseTypeName.name(), printerGroupUseTypeName);
            }
        }

        return null;
    }
    
}
