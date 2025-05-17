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

import com.echothree.control.user.printer.common.form.DeletePartyPrinterGroupUseForm;
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

public class DeletePartyPrinterGroupUseCommand
        extends BaseSimpleCommand<DeletePartyPrinterGroupUseForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyPrinterGroupUse.name(), SecurityRoles.Delete.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PrinterGroupUseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of DeletePartyPrinterGroupUseCommand */
    public DeletePartyPrinterGroupUseCommand() {
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
                var partyPrinterGroupUse = printerControl.getPartyPrinterGroupUseForUpdate(party, printerGroupUseType);

                if(partyPrinterGroupUse != null) {
                    printerControl.deletePartyPrinterGroupUse(partyPrinterGroupUse, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyPrinterGroupUse.name(), party.getLastDetail().getPartyName(), printerGroupUseTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPrinterGroupUseTypeName.name(), printerGroupUseTypeName);
            }
        }

        return null;
    }
    
}
