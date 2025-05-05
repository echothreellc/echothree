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

import com.echothree.control.user.printer.common.edit.PrinterDescriptionEdit;
import com.echothree.control.user.printer.common.edit.PrinterEditFactory;
import com.echothree.control.user.printer.common.form.EditPrinterDescriptionForm;
import com.echothree.control.user.printer.common.result.EditPrinterDescriptionResult;
import com.echothree.control.user.printer.common.result.PrinterResultFactory;
import com.echothree.control.user.printer.common.spec.PrinterDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.printer.server.entity.Printer;
import com.echothree.model.data.printer.server.entity.PrinterDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPrinterDescriptionCommand
        extends BaseAbstractEditCommand<PrinterDescriptionSpec, PrinterDescriptionEdit, EditPrinterDescriptionResult, PrinterDescription, Printer> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Printer.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditPrinterDescriptionCommand */
    public EditPrinterDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPrinterDescriptionResult getResult() {
        return PrinterResultFactory.getEditPrinterDescriptionResult();
    }

    @Override
    public PrinterDescriptionEdit getEdit() {
        return PrinterEditFactory.getPrinterDescriptionEdit();
    }

    @Override
    public PrinterDescription getEntity(EditPrinterDescriptionResult result) {
        var printerControl = Session.getModelController(PrinterControl.class);
        PrinterDescription printerDescription = null;
        var printerName = spec.getPrinterName();
        var printer = printerControl.getPrinterByName(printerName);

        if(printer != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    printerDescription = printerControl.getPrinterDescription(printer, language);
                } else { // EditMode.UPDATE
                    printerDescription = printerControl.getPrinterDescriptionForUpdate(printer, language);
                }

                if(printerDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownPrinterDescription.name(), printerName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPrinterName.name(), printerName);
        }

        return printerDescription;
    }

    @Override
    public Printer getLockEntity(PrinterDescription printerDescription) {
        return printerDescription.getPrinter();
    }

    @Override
    public void fillInResult(EditPrinterDescriptionResult result, PrinterDescription printerDescription) {
        var printerControl = Session.getModelController(PrinterControl.class);

        result.setPrinterDescription(printerControl.getPrinterDescriptionTransfer(getUserVisit(), printerDescription));
    }

    @Override
    public void doLock(PrinterDescriptionEdit edit, PrinterDescription printerDescription) {
        edit.setDescription(printerDescription.getDescription());
    }

    @Override
    public void doUpdate(PrinterDescription printerDescription) {
        var printerControl = Session.getModelController(PrinterControl.class);
        var printerDescriptionValue = printerControl.getPrinterDescriptionValue(printerDescription);

        printerDescriptionValue.setDescription(edit.getDescription());

        printerControl.updatePrinterDescriptionFromValue(printerDescriptionValue, getPartyPK());
    }

}
