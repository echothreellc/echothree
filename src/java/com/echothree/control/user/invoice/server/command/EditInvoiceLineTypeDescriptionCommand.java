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

package com.echothree.control.user.invoice.server.command;

import com.echothree.control.user.invoice.common.edit.InvoiceEditFactory;
import com.echothree.control.user.invoice.common.edit.InvoiceLineTypeDescriptionEdit;
import com.echothree.control.user.invoice.common.result.EditInvoiceLineTypeDescriptionResult;
import com.echothree.control.user.invoice.common.result.InvoiceResultFactory;
import com.echothree.control.user.invoice.common.spec.InvoiceLineTypeDescriptionSpec;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.invoice.server.entity.InvoiceLineType;
import com.echothree.model.data.invoice.server.entity.InvoiceLineTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditInvoiceLineTypeDescriptionCommand
        extends BaseAbstractEditCommand<InvoiceLineTypeDescriptionSpec, InvoiceLineTypeDescriptionEdit, EditInvoiceLineTypeDescriptionResult, InvoiceLineTypeDescription, InvoiceLineType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InvoiceLineType.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InvoiceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InvoiceLineTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    InvoiceControl invoiceControl;

    @Inject
    PartyControl partyControl;

    /** Creates a new instance of EditInvoiceLineTypeDescriptionCommand */
    public EditInvoiceLineTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditInvoiceLineTypeDescriptionResult getResult() {
        return InvoiceResultFactory.getEditInvoiceLineTypeDescriptionResult();
    }

    @Override
    public InvoiceLineTypeDescriptionEdit getEdit() {
        return InvoiceEditFactory.getInvoiceLineTypeDescriptionEdit();
    }

    @Override
    public InvoiceLineTypeDescription getEntity(EditInvoiceLineTypeDescriptionResult result) {
        InvoiceLineTypeDescription invoiceLineTypeDescription = null;
        var invoiceTypeName = spec.getInvoiceTypeName();
        var invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);

        if(invoiceType != null) {
            var invoiceLineTypeName = spec.getInvoiceLineTypeName();
            var invoiceLineType = invoiceControl.getInvoiceLineTypeByName(invoiceType, invoiceLineTypeName);

            if(invoiceLineType != null) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    invoiceLineTypeDescription = invoiceControl.getInvoiceLineTypeDescription(invoiceLineType, language,
                            editModeToEntityPermission(editMode));

                    if(invoiceLineTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownInvoiceLineTypeDescription.name(),
                                invoiceType.getLastDetail().getInvoiceTypeName(),
                                invoiceLineType.getLastDetail().getInvoiceLineTypeName(),
                                language.getLanguageIsoName());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInvoiceLineTypeName.name(),
                        invoiceType.getLastDetail().getInvoiceTypeName(), invoiceLineTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
        }

        return invoiceLineTypeDescription;
    }

    @Override
    public InvoiceLineType getLockEntity(InvoiceLineTypeDescription invoiceLineTypeDescription) {
        return invoiceLineTypeDescription.getInvoiceLineType();
    }

    @Override
    public void fillInResult(EditInvoiceLineTypeDescriptionResult result, InvoiceLineTypeDescription invoiceLineTypeDescription) {
        result.setInvoiceLineTypeDescription(invoiceControl.getInvoiceLineTypeDescriptionTransfer(getUserVisit(), invoiceLineTypeDescription));
    }

    @Override
    public void doLock(InvoiceLineTypeDescriptionEdit edit, InvoiceLineTypeDescription invoiceLineTypeDescription) {
        edit.setDescription(invoiceLineTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(InvoiceLineTypeDescription invoiceLineTypeDescription) {
        var invoiceLineTypeDescriptionValue = invoiceControl.getInvoiceLineTypeDescriptionValue(invoiceLineTypeDescription);

        invoiceLineTypeDescriptionValue.setDescription(edit.getDescription());

        invoiceControl.updateInvoiceLineTypeDescriptionFromValue(invoiceLineTypeDescriptionValue, getPartyPK());
    }
    
}
