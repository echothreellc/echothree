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

package com.echothree.control.user.printer.server.command;

import com.echothree.control.user.printer.remote.edit.PartyPrinterGroupUseEdit;
import com.echothree.control.user.printer.remote.edit.PrinterEditFactory;
import com.echothree.control.user.printer.remote.form.EditPartyPrinterGroupUseForm;
import com.echothree.control.user.printer.remote.result.EditPartyPrinterGroupUseResult;
import com.echothree.control.user.printer.remote.result.PrinterResultFactory;
import com.echothree.control.user.printer.remote.spec.PartyPrinterGroupUseSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.printer.server.entity.PartyPrinterGroupUse;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.printer.server.entity.PrinterGroupUseType;
import com.echothree.model.data.printer.server.value.PartyPrinterGroupUseValue;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPartyPrinterGroupUseCommand
        extends BaseAbstractEditCommand<PartyPrinterGroupUseSpec, PartyPrinterGroupUseEdit, EditPartyPrinterGroupUseResult, PartyPrinterGroupUse, Party> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyPrinterGroupUse.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PrinterGroupUseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyPrinterGroupUseCommand */
    public EditPartyPrinterGroupUseCommand(UserVisitPK userVisitPK, EditPartyPrinterGroupUseForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPartyPrinterGroupUseResult getResult() {
        return PrinterResultFactory.getEditPartyPrinterGroupUseResult();
    }

    @Override
    public PartyPrinterGroupUseEdit getEdit() {
        return PrinterEditFactory.getPartyPrinterGroupUseEdit();
    }

    @Override
    public PartyPrinterGroupUse getEntity(EditPartyPrinterGroupUseResult result) {
        PartyPrinterGroupUse partyPrinterGroupUse = null;
        String partyName = spec.getPartyName();
        Party party = null;

        if(partyName != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);

            party = partyControl.getPartyByName(partyName);
            
            if(party == null) {
                addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
            }
        } else {
            party = getParty();
        }

        if(!hasExecutionErrors()) {
            PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
            String printerGroupUseTypeName = spec.getPrinterGroupUseTypeName();
            PrinterGroupUseType printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(printerGroupUseTypeName);

            if(printerGroupUseType != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    partyPrinterGroupUse = printerControl.getPartyPrinterGroupUse(party, printerGroupUseType);
                } else { // EditMode.UPDATE
                    partyPrinterGroupUse = printerControl.getPartyPrinterGroupUseForUpdate(party, printerGroupUseType);
                }

                if(partyPrinterGroupUse == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyPrinterGroupUse.name(), party.getLastDetail().getPartyName(), printerGroupUseTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPrinterGroupUseTypeName.name(), printerGroupUseTypeName);
            }
        }

        return partyPrinterGroupUse;
    }

    @Override
    public Party getLockEntity(PartyPrinterGroupUse partyPrinterGroupUse) {
        return partyPrinterGroupUse.getParty();
    }

    @Override
    public void fillInResult(EditPartyPrinterGroupUseResult result, PartyPrinterGroupUse partyPrinterGroupUse) {
        PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);

        result.setPartyPrinterGroupUse(printerControl.getPartyPrinterGroupUseTransfer(getUserVisit(), partyPrinterGroupUse));
    }

    @Override
    public void doLock(PartyPrinterGroupUseEdit edit, PartyPrinterGroupUse partyPrinterGroupUse) {
        edit.setPrinterGroupName(partyPrinterGroupUse.getPrinterGroup().getLastDetail().getPrinterGroupName());
    }

    PrinterGroup printerGroup;

    @Override
    public void canUpdate(PartyPrinterGroupUse partyPrinterGroupUse) {
        PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
        String printerGroupName = edit.getPrinterGroupName();

        printerGroup = printerControl.getPrinterGroupByName(printerGroupName);

        if(printerGroup == null) {
            addExecutionError(ExecutionErrors.DuplicatePrinterGroupName.name(), printerGroupName);
        }
    }

    @Override
    public void doUpdate(PartyPrinterGroupUse partyPrinterGroupUse) {
        PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
        PartyPrinterGroupUseValue partyPrinterGroupUseValue = printerControl.getPartyPrinterGroupUseValue(partyPrinterGroupUse);

        partyPrinterGroupUseValue.setPrinterGroupPK(printerGroup.getPrimaryKey());

        printerControl.updatePartyPrinterGroupUseFromValue(partyPrinterGroupUseValue, getPartyPK());
    }
    
}
