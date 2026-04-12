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
import com.echothree.control.user.invoice.common.edit.InvoiceTypeDescriptionEdit;
import com.echothree.control.user.invoice.common.result.EditInvoiceTypeDescriptionResult;
import com.echothree.control.user.invoice.common.result.InvoiceResultFactory;
import com.echothree.control.user.invoice.common.spec.InvoiceTypeDescriptionSpec;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
import com.echothree.model.data.invoice.server.entity.InvoiceTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditInvoiceTypeDescriptionCommand
        extends BaseAbstractEditCommand<InvoiceTypeDescriptionSpec, InvoiceTypeDescriptionEdit, EditInvoiceTypeDescriptionResult, InvoiceTypeDescription, InvoiceType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InvoiceType.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InvoiceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PartyControl partyControl;

    @Inject
    InvoiceControl invoiceControl;

    /** Creates a new instance of EditInvoiceTypeDescriptionCommand */
    public EditInvoiceTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditInvoiceTypeDescriptionResult getResult() {
        return InvoiceResultFactory.getEditInvoiceTypeDescriptionResult();
    }

    @Override
    public InvoiceTypeDescriptionEdit getEdit() {
        return InvoiceEditFactory.getInvoiceTypeDescriptionEdit();
    }

    @Override
    public InvoiceTypeDescription getEntity(EditInvoiceTypeDescriptionResult result) {
        InvoiceTypeDescription invoiceTypeDescription = null;
        var invoiceTypeName = spec.getInvoiceTypeName();
        var invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);

        if(invoiceType != null) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                invoiceTypeDescription = invoiceControl.getInvoiceTypeDescription(invoiceType, language,
                        editModeToEntityPermission(editMode));

                if(invoiceTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownInvoiceTypeDescription.name(), invoiceTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
        }

        return invoiceTypeDescription;
    }

    @Override
    public InvoiceType getLockEntity(InvoiceTypeDescription invoiceTypeDescription) {
        return invoiceTypeDescription.getInvoiceType();
    }

    @Override
    public void fillInResult(EditInvoiceTypeDescriptionResult result, InvoiceTypeDescription invoiceTypeDescription) {
        result.setInvoiceTypeDescription(invoiceControl.getInvoiceTypeDescriptionTransfer(getUserVisit(), invoiceTypeDescription));
    }

    @Override
    public void doLock(InvoiceTypeDescriptionEdit edit, InvoiceTypeDescription invoiceTypeDescription) {
        edit.setDescription(invoiceTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(InvoiceTypeDescription invoiceTypeDescription) {
        var invoiceTypeDescriptionValue = invoiceControl.getInvoiceTypeDescriptionValue(invoiceTypeDescription);

        invoiceTypeDescriptionValue.setDescription(edit.getDescription());

        invoiceControl.updateInvoiceTypeDescriptionFromValue(invoiceTypeDescriptionValue, getPartyPK());
    }
    
}
