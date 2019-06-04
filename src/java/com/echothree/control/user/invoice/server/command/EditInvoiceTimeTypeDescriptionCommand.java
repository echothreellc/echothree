// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.invoice.common.edit.InvoiceTimeTypeDescriptionEdit;
import com.echothree.control.user.invoice.common.form.EditInvoiceTimeTypeDescriptionForm;
import com.echothree.control.user.invoice.common.result.EditInvoiceTimeTypeDescriptionResult;
import com.echothree.control.user.invoice.common.result.InvoiceResultFactory;
import com.echothree.control.user.invoice.common.spec.InvoiceTimeTypeDescriptionSpec;
import com.echothree.model.control.invoice.server.InvoiceControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.invoice.server.entity.InvoiceTimeType;
import com.echothree.model.data.invoice.server.entity.InvoiceTimeTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
import com.echothree.model.data.invoice.server.value.InvoiceTimeTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditInvoiceTimeTypeDescriptionCommand
        extends BaseAbstractEditCommand<InvoiceTimeTypeDescriptionSpec, InvoiceTimeTypeDescriptionEdit, EditInvoiceTimeTypeDescriptionResult, InvoiceTimeTypeDescription, InvoiceTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.InvoiceTimeType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InvoiceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InvoiceTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditInvoiceTimeTypeDescriptionCommand */
    public EditInvoiceTimeTypeDescriptionCommand(UserVisitPK userVisitPK, EditInvoiceTimeTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditInvoiceTimeTypeDescriptionResult getResult() {
        return InvoiceResultFactory.getEditInvoiceTimeTypeDescriptionResult();
    }

    @Override
    public InvoiceTimeTypeDescriptionEdit getEdit() {
        return InvoiceEditFactory.getInvoiceTimeTypeDescriptionEdit();
    }

    @Override
    public InvoiceTimeTypeDescription getEntity(EditInvoiceTimeTypeDescriptionResult result) {
        var invoiceControl = (InvoiceControl)Session.getModelController(InvoiceControl.class);
        InvoiceTimeTypeDescription invoiceTimeTypeDescription = null;
        String invoiceTypeName = spec.getInvoiceTypeName();
        InvoiceType invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);

        if(invoiceType != null) {
            String invoiceTimeTypeName = spec.getInvoiceTimeTypeName();
            InvoiceTimeType invoiceTimeType = invoiceControl.getInvoiceTimeTypeByName(invoiceType, invoiceTimeTypeName);

            if(invoiceTimeType != null) {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        invoiceTimeTypeDescription = invoiceControl.getInvoiceTimeTypeDescription(invoiceTimeType, language);
                    } else { // EditMode.UPDATE
                        invoiceTimeTypeDescription = invoiceControl.getInvoiceTimeTypeDescriptionForUpdate(invoiceTimeType, language);
                    }

                    if(invoiceTimeTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownInvoiceTimeTypeDescription.name(), invoiceTypeName, invoiceTimeTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInvoiceTimeTypeName.name(), invoiceTypeName, invoiceTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
        }

        return invoiceTimeTypeDescription;
    }

    @Override
    public InvoiceTimeType getLockEntity(InvoiceTimeTypeDescription invoiceTimeTypeDescription) {
        return invoiceTimeTypeDescription.getInvoiceTimeType();
    }

    @Override
    public void fillInResult(EditInvoiceTimeTypeDescriptionResult result, InvoiceTimeTypeDescription invoiceTimeTypeDescription) {
        var invoiceControl = (InvoiceControl)Session.getModelController(InvoiceControl.class);

        result.setInvoiceTimeTypeDescription(invoiceControl.getInvoiceTimeTypeDescriptionTransfer(getUserVisit(), invoiceTimeTypeDescription));
    }

    @Override
    public void doLock(InvoiceTimeTypeDescriptionEdit edit, InvoiceTimeTypeDescription invoiceTimeTypeDescription) {
        edit.setDescription(invoiceTimeTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(InvoiceTimeTypeDescription invoiceTimeTypeDescription) {
        var invoiceControl = (InvoiceControl)Session.getModelController(InvoiceControl.class);
        InvoiceTimeTypeDescriptionValue invoiceTimeTypeDescriptionValue = invoiceControl.getInvoiceTimeTypeDescriptionValue(invoiceTimeTypeDescription);
        invoiceTimeTypeDescriptionValue.setDescription(edit.getDescription());

        invoiceControl.updateInvoiceTimeTypeDescriptionFromValue(invoiceTimeTypeDescriptionValue, getPartyPK());
    }
    
}
