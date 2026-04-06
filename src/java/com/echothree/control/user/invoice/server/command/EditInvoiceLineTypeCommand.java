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
import com.echothree.control.user.invoice.common.edit.InvoiceLineTypeEdit;
import com.echothree.control.user.invoice.common.result.EditInvoiceLineTypeResult;
import com.echothree.control.user.invoice.common.result.InvoiceResultFactory;
import com.echothree.control.user.invoice.common.spec.InvoiceLineTypeSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.invoice.server.entity.InvoiceLineType;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
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
public class EditInvoiceLineTypeCommand
        extends BaseAbstractEditCommand<InvoiceLineTypeSpec, InvoiceLineTypeEdit, EditInvoiceLineTypeResult, InvoiceLineType, InvoiceLineType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InvoiceLineType.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InvoiceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InvoiceLineTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InvoiceLineTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentInvoiceLineTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    InvoiceControl invoiceControl;
    
    /** Creates a new instance of EditInvoiceLineTypeCommand */
    public EditInvoiceLineTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditInvoiceLineTypeResult getResult() {
        return InvoiceResultFactory.getEditInvoiceLineTypeResult();
    }

    @Override
    public InvoiceLineTypeEdit getEdit() {
        return InvoiceEditFactory.getInvoiceLineTypeEdit();
    }

    InvoiceType invoiceType;

    @Override
    public InvoiceLineType getEntity(EditInvoiceLineTypeResult result) {
        InvoiceLineType invoiceLineType = null;
        var invoiceTypeName = spec.getInvoiceTypeName();

        invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);

        if(invoiceType != null) {
            var invoiceLineTypeName = spec.getInvoiceLineTypeName();

            invoiceLineType = invoiceControl.getInvoiceLineTypeByName(invoiceType, invoiceLineTypeName, editModeToEntityPermission(editMode));

            if(invoiceLineType == null) {
                addExecutionError(ExecutionErrors.UnknownInvoiceLineTypeName.name(),
                        invoiceType.getLastDetail().getInvoiceTypeName(), invoiceLineTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
        }

        return invoiceLineType;
    }

    @Override
    public InvoiceLineType getLockEntity(InvoiceLineType invoiceLineType) {
        return invoiceLineType;
    }

    @Override
    public void fillInResult(EditInvoiceLineTypeResult result, InvoiceLineType invoiceLineType) {
        result.setInvoiceLineType(invoiceControl.getInvoiceLineTypeTransfer(getUserVisit(), invoiceLineType));
    }

    @Override
    public void doLock(InvoiceLineTypeEdit edit, InvoiceLineType invoiceLineType) {
        var invoiceLineTypeDescription = invoiceControl.getInvoiceLineTypeDescription(invoiceLineType, getPreferredLanguage());
        var invoiceLineTypeDetail = invoiceLineType.getLastDetail();
        var parentInvoiceLineType = invoiceLineTypeDetail.getParentInvoiceLineType();
        var defaultGlAccount = invoiceLineTypeDetail.getDefaultGlAccount();

        edit.setInvoiceLineTypeName(invoiceLineTypeDetail.getInvoiceLineTypeName());
        edit.setParentInvoiceLineTypeName(parentInvoiceLineType == null ? null : parentInvoiceLineType.getLastDetail().getInvoiceLineTypeName());
        edit.setDefaultGlAccountName(defaultGlAccount == null ? null : defaultGlAccount.getLastDetail().getGlAccountName());
        edit.setIsDefault(invoiceLineTypeDetail.getIsDefault().toString());
        edit.setSortOrder(invoiceLineTypeDetail.getSortOrder().toString());

        if(invoiceLineTypeDescription != null) {
            edit.setDescription(invoiceLineTypeDescription.getDescription());
        }
    }

    InvoiceLineType parentInvoiceLineType;
    GlAccount defaultGlAccount;

    @Override
    public void canUpdate(InvoiceLineType invoiceLineType) {
        var invoiceLineTypeName = edit.getInvoiceLineTypeName();
        var duplicateInvoiceLineType = invoiceControl.getInvoiceLineTypeByName(invoiceType, invoiceLineTypeName);

        if(duplicateInvoiceLineType == null || invoiceLineType.equals(duplicateInvoiceLineType)) {
            var parentInvoiceLineTypeName = edit.getParentInvoiceLineTypeName();

            if(parentInvoiceLineTypeName != null) {
                parentInvoiceLineType = invoiceControl.getInvoiceLineTypeByName(invoiceType, parentInvoiceLineTypeName);
            }

            if(parentInvoiceLineTypeName == null || parentInvoiceLineType != null) {
                if(invoiceControl.isParentInvoiceLineTypeSafe(invoiceLineType, parentInvoiceLineType)) {
                    var defaultGlAccountName = edit.getDefaultGlAccountName();

                    if(defaultGlAccountName != null) {
                        defaultGlAccount = accountingControl.getGlAccountByName(defaultGlAccountName);
                    }

                    if(defaultGlAccountName != null && defaultGlAccount == null) {
                        addExecutionError(ExecutionErrors.UnknownDefaultGlAccountName.name(), defaultGlAccountName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParentInvoiceLineType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentInvoiceLineTypeName.name(), parentInvoiceLineTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateInvoiceLineTypeName.name(), invoiceLineTypeName);
        }
    }

    @Override
    public void doUpdate(InvoiceLineType invoiceLineType) {
        var partyPK = getPartyPK();
        var invoiceLineTypeDetailValue = invoiceControl.getInvoiceLineTypeDetailValueForUpdate(invoiceLineType);
        var invoiceLineTypeDescription = invoiceControl.getInvoiceLineTypeDescriptionForUpdate(invoiceLineType, getPreferredLanguage());
        var description = edit.getDescription();

        invoiceLineTypeDetailValue.setInvoiceLineTypeName(edit.getInvoiceLineTypeName());
        invoiceLineTypeDetailValue.setParentInvoiceLineTypePK(parentInvoiceLineType == null ? null : parentInvoiceLineType.getPrimaryKey());
        invoiceLineTypeDetailValue.setDefaultGlAccountPK(defaultGlAccount == null ? null : defaultGlAccount.getPrimaryKey());
        invoiceLineTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        invoiceLineTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        invoiceControl.updateInvoiceLineTypeFromValue(invoiceLineTypeDetailValue, partyPK);

        if(invoiceLineTypeDescription == null && description != null) {
            invoiceControl.createInvoiceLineTypeDescription(invoiceLineType, getPreferredLanguage(), description, partyPK);
        } else if(invoiceLineTypeDescription != null && description == null) {
            invoiceControl.deleteInvoiceLineTypeDescription(invoiceLineTypeDescription, partyPK);
        } else if(invoiceLineTypeDescription != null && description != null) {
            var invoiceLineTypeDescriptionValue = invoiceControl.getInvoiceLineTypeDescriptionValue(invoiceLineTypeDescription);

            invoiceLineTypeDescriptionValue.setDescription(description);
            invoiceControl.updateInvoiceLineTypeDescriptionFromValue(invoiceLineTypeDescriptionValue, partyPK);
        }
    }

}
