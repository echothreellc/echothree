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
import com.echothree.control.user.invoice.common.edit.InvoiceTimeTypeEdit;
import com.echothree.control.user.invoice.common.form.EditInvoiceTimeTypeForm;
import com.echothree.control.user.invoice.common.result.EditInvoiceTimeTypeResult;
import com.echothree.control.user.invoice.common.result.InvoiceResultFactory;
import com.echothree.control.user.invoice.common.spec.InvoiceTimeTypeSpec;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.invoice.server.entity.InvoiceTimeType;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditInvoiceTimeTypeCommand
        extends BaseAbstractEditCommand<InvoiceTimeTypeSpec, InvoiceTimeTypeEdit, EditInvoiceTimeTypeResult, InvoiceTimeType, InvoiceTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.InvoiceTimeType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InvoiceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InvoiceTimeTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InvoiceTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditInvoiceTimeTypeCommand */
    public EditInvoiceTimeTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditInvoiceTimeTypeResult getResult() {
        return InvoiceResultFactory.getEditInvoiceTimeTypeResult();
    }

    @Override
    public InvoiceTimeTypeEdit getEdit() {
        return InvoiceEditFactory.getInvoiceTimeTypeEdit();
    }

    @Override
    public InvoiceTimeType getEntity(EditInvoiceTimeTypeResult result) {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        InvoiceTimeType invoiceTimeType = null;
        var invoiceTypeName = spec.getInvoiceTypeName();
        var invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);

        if(invoiceType != null) {
            var invoiceTimeTypeName = spec.getInvoiceTimeTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                invoiceTimeType = invoiceControl.getInvoiceTimeTypeByName(invoiceType, invoiceTimeTypeName);
            } else { // EditMode.UPDATE
                invoiceTimeType = invoiceControl.getInvoiceTimeTypeByNameForUpdate(invoiceType, invoiceTimeTypeName);
            }

            if(invoiceTimeType != null) {
                result.setInvoiceTimeType(invoiceControl.getInvoiceTimeTypeTransfer(getUserVisit(), invoiceTimeType));
            } else {
                addExecutionError(ExecutionErrors.UnknownInvoiceTimeTypeName.name(), invoiceTypeName, invoiceTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
        }

        return invoiceTimeType;
    }

    @Override
    public InvoiceTimeType getLockEntity(InvoiceTimeType invoiceTimeType) {
        return invoiceTimeType;
    }

    @Override
    public void fillInResult(EditInvoiceTimeTypeResult result, InvoiceTimeType invoiceTimeType) {
        var invoiceControl = Session.getModelController(InvoiceControl.class);

        result.setInvoiceTimeType(invoiceControl.getInvoiceTimeTypeTransfer(getUserVisit(), invoiceTimeType));
    }

    @Override
    public void doLock(InvoiceTimeTypeEdit edit, InvoiceTimeType invoiceTimeType) {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var invoiceTimeTypeDescription = invoiceControl.getInvoiceTimeTypeDescription(invoiceTimeType, getPreferredLanguage());
        var invoiceTimeTypeDetail = invoiceTimeType.getLastDetail();

        edit.setInvoiceTimeTypeName(invoiceTimeTypeDetail.getInvoiceTimeTypeName());
        edit.setIsDefault(invoiceTimeTypeDetail.getIsDefault().toString());
        edit.setSortOrder(invoiceTimeTypeDetail.getSortOrder().toString());

        if(invoiceTimeTypeDescription != null) {
            edit.setDescription(invoiceTimeTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(InvoiceTimeType invoiceTimeType) {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var invoiceTypeName = spec.getInvoiceTypeName();
        var invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);

        if(invoiceType != null) {
            var invoiceTimeTypeName = edit.getInvoiceTimeTypeName();
            var duplicateInvoiceTimeType = invoiceControl.getInvoiceTimeTypeByName(invoiceType, invoiceTimeTypeName);

            if(duplicateInvoiceTimeType != null && !invoiceTimeType.equals(duplicateInvoiceTimeType)) {
                addExecutionError(ExecutionErrors.DuplicateInvoiceTimeTypeName.name(), invoiceTypeName, invoiceTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
        }
    }

    @Override
    public void doUpdate(InvoiceTimeType invoiceTimeType) {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var partyPK = getPartyPK();
        var invoiceTimeTypeDetailValue = invoiceControl.getInvoiceTimeTypeDetailValueForUpdate(invoiceTimeType);
        var invoiceTimeTypeDescription = invoiceControl.getInvoiceTimeTypeDescriptionForUpdate(invoiceTimeType, getPreferredLanguage());
        var description = edit.getDescription();

        invoiceTimeTypeDetailValue.setInvoiceTimeTypeName(edit.getInvoiceTimeTypeName());
        invoiceTimeTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        invoiceTimeTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        invoiceControl.updateInvoiceTimeTypeFromValue(invoiceTimeTypeDetailValue, partyPK);

        if(invoiceTimeTypeDescription == null && description != null) {
            invoiceControl.createInvoiceTimeTypeDescription(invoiceTimeType, getPreferredLanguage(), description, partyPK);
        } else {
            if(invoiceTimeTypeDescription != null && description == null) {
                invoiceControl.deleteInvoiceTimeTypeDescription(invoiceTimeTypeDescription, partyPK);
            } else {
                if(invoiceTimeTypeDescription != null && description != null) {
                    var invoiceTimeTypeDescriptionValue = invoiceControl.getInvoiceTimeTypeDescriptionValue(invoiceTimeTypeDescription);

                    invoiceTimeTypeDescriptionValue.setDescription(description);
                    invoiceControl.updateInvoiceTimeTypeDescriptionFromValue(invoiceTimeTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
