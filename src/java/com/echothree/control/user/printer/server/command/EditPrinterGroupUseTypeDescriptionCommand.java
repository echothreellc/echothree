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

import com.echothree.control.user.printer.common.edit.PrinterEditFactory;
import com.echothree.control.user.printer.common.edit.PrinterGroupUseTypeDescriptionEdit;
import com.echothree.control.user.printer.common.form.EditPrinterGroupUseTypeDescriptionForm;
import com.echothree.control.user.printer.common.result.EditPrinterGroupUseTypeDescriptionResult;
import com.echothree.control.user.printer.common.result.PrinterResultFactory;
import com.echothree.control.user.printer.common.spec.PrinterGroupUseTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.printer.server.entity.PrinterGroupUseType;
import com.echothree.model.data.printer.server.entity.PrinterGroupUseTypeDescription;
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

public class EditPrinterGroupUseTypeDescriptionCommand
        extends BaseAbstractEditCommand<PrinterGroupUseTypeDescriptionSpec, PrinterGroupUseTypeDescriptionEdit, EditPrinterGroupUseTypeDescriptionResult, PrinterGroupUseTypeDescription, PrinterGroupUseType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PrinterGroupUseType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterGroupUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditPrinterGroupUseTypeDescriptionCommand */
    public EditPrinterGroupUseTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPrinterGroupUseTypeDescriptionResult getResult() {
        return PrinterResultFactory.getEditPrinterGroupUseTypeDescriptionResult();
    }

    @Override
    public PrinterGroupUseTypeDescriptionEdit getEdit() {
        return PrinterEditFactory.getPrinterGroupUseTypeDescriptionEdit();
    }

    @Override
    public PrinterGroupUseTypeDescription getEntity(EditPrinterGroupUseTypeDescriptionResult result) {
        var printerControl = Session.getModelController(PrinterControl.class);
        PrinterGroupUseTypeDescription printerGroupUseTypeDescription = null;
        var printerGroupUseTypeName = spec.getPrinterGroupUseTypeName();
        var printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(printerGroupUseTypeName);

        if(printerGroupUseType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    printerGroupUseTypeDescription = printerControl.getPrinterGroupUseTypeDescription(printerGroupUseType, language);
                } else { // EditMode.UPDATE
                    printerGroupUseTypeDescription = printerControl.getPrinterGroupUseTypeDescriptionForUpdate(printerGroupUseType, language);
                }

                if(printerGroupUseTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownPrinterGroupUseTypeDescription.name(), printerGroupUseTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPrinterGroupUseTypeName.name(), printerGroupUseTypeName);
        }

        return printerGroupUseTypeDescription;
    }

    @Override
    public PrinterGroupUseType getLockEntity(PrinterGroupUseTypeDescription printerGroupUseTypeDescription) {
        return printerGroupUseTypeDescription.getPrinterGroupUseType();
    }

    @Override
    public void fillInResult(EditPrinterGroupUseTypeDescriptionResult result, PrinterGroupUseTypeDescription printerGroupUseTypeDescription) {
        var printerControl = Session.getModelController(PrinterControl.class);

        result.setPrinterGroupUseTypeDescription(printerControl.getPrinterGroupUseTypeDescriptionTransfer(getUserVisit(), printerGroupUseTypeDescription));
    }

    @Override
    public void doLock(PrinterGroupUseTypeDescriptionEdit edit, PrinterGroupUseTypeDescription printerGroupUseTypeDescription) {
        edit.setDescription(printerGroupUseTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(PrinterGroupUseTypeDescription printerGroupUseTypeDescription) {
        var printerControl = Session.getModelController(PrinterControl.class);
        var printerGroupUseTypeDescriptionValue = printerControl.getPrinterGroupUseTypeDescriptionValue(printerGroupUseTypeDescription);

        printerGroupUseTypeDescriptionValue.setDescription(edit.getDescription());

        printerControl.updatePrinterGroupUseTypeDescriptionFromValue(printerGroupUseTypeDescriptionValue, getPartyPK());
    }

}
