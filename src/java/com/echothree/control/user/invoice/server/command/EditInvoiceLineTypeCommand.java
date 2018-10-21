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

package com.echothree.control.user.invoice.server.command;

import com.echothree.control.user.invoice.remote.edit.InvoiceEditFactory;
import com.echothree.control.user.invoice.remote.edit.InvoiceLineTypeEdit;
import com.echothree.control.user.invoice.remote.form.EditInvoiceLineTypeForm;
import com.echothree.control.user.invoice.remote.result.EditInvoiceLineTypeResult;
import com.echothree.control.user.invoice.remote.result.InvoiceResultFactory;
import com.echothree.control.user.invoice.remote.spec.InvoiceLineTypeSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.invoice.server.InvoiceControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.invoice.server.entity.InvoiceLineType;
import com.echothree.model.data.invoice.server.entity.InvoiceLineTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceLineTypeDetail;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
import com.echothree.model.data.invoice.server.value.InvoiceLineTypeDescriptionValue;
import com.echothree.model.data.invoice.server.value.InvoiceLineTypeDetailValue;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditInvoiceLineTypeCommand
        extends BaseEditCommand<InvoiceLineTypeSpec, InvoiceLineTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.InvoiceLineType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InvoiceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InvoiceLineTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InvoiceLineTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentInvoiceLineTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditInvoiceLineTypeCommand */
    public EditInvoiceLineTypeCommand(UserVisitPK userVisitPK, EditInvoiceLineTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        InvoiceControl invoiceControl = (InvoiceControl)Session.getModelController(InvoiceControl.class);
        EditInvoiceLineTypeResult result = InvoiceResultFactory.getEditInvoiceLineTypeResult();
        String invoiceTypeName = spec.getInvoiceTypeName();
        InvoiceType invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);
        
        if(invoiceType != null) {
            if(editMode.equals(EditMode.LOCK)) {
                String invoiceLineTypeName = spec.getInvoiceLineTypeName();
                InvoiceLineType invoiceLineType = invoiceControl.getInvoiceLineTypeByName(invoiceType, invoiceLineTypeName);
                
                if(invoiceLineType != null) {
                    result.setInvoiceLineType(invoiceControl.getInvoiceLineTypeTransfer(getUserVisit(), invoiceLineType));
                    
                    if(lockEntity(invoiceLineType)) {
                        InvoiceLineTypeDescription invoiceLineTypeDescription = invoiceControl.getInvoiceLineTypeDescription(invoiceLineType, getPreferredLanguage());
                        InvoiceLineTypeEdit edit = InvoiceEditFactory.getInvoiceLineTypeEdit();
                        InvoiceLineTypeDetail invoiceLineTypeDetail = invoiceLineType.getLastDetail();
                        InvoiceLineType parentInvoiceLineType = invoiceLineTypeDetail.getParentInvoiceLineType();
                        
                        result.setEdit(edit);
                        edit.setInvoiceLineTypeName(invoiceLineTypeDetail.getInvoiceLineTypeName());
                        edit.setParentInvoiceLineTypeName(parentInvoiceLineType == null? null: parentInvoiceLineType.getLastDetail().getInvoiceLineTypeName());
                        edit.setIsDefault(invoiceLineTypeDetail.getIsDefault().toString());
                        edit.setSortOrder(invoiceLineTypeDetail.getSortOrder().toString());
                        
                        if(invoiceLineTypeDescription != null)
                            edit.setDescription(invoiceLineTypeDescription.getDescription());
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(invoiceLineType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownInvoiceLineTypeName.name(), invoiceLineTypeName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                String invoiceLineTypeName = spec.getInvoiceLineTypeName();
                InvoiceLineType invoiceLineType = invoiceControl.getInvoiceLineTypeByNameForUpdate(invoiceType, invoiceLineTypeName);
                
                if(invoiceLineType != null) {
                    invoiceLineTypeName = edit.getInvoiceLineTypeName();
                    InvoiceLineType duplicateInvoiceLineType = invoiceControl.getInvoiceLineTypeByName(invoiceType, invoiceLineTypeName);
                    
                    if(duplicateInvoiceLineType == null || invoiceLineType.equals(duplicateInvoiceLineType)) {
                        String parentInvoiceLineTypeName = edit.getParentInvoiceLineTypeName();
                        InvoiceLineType parentInvoiceLineType = null;
                        
                        if(parentInvoiceLineTypeName != null) {
                            parentInvoiceLineType = invoiceControl.getInvoiceLineTypeByName(invoiceType, parentInvoiceLineTypeName);
                        }
                        
                        if(parentInvoiceLineTypeName == null || parentInvoiceLineType != null) {
                            if(invoiceControl.isParentInvoiceLineTypeSafe(invoiceLineType, parentInvoiceLineType)) {
                                AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                                String defaultGlAccountName = edit.getDefaultGlAccountName();
                                GlAccount defaultGlAccount = defaultGlAccountName == null? null: accountingControl.getGlAccountByName(defaultGlAccountName);

                                if(defaultGlAccountName == null || defaultGlAccount != null) {
                                    if(lockEntityForUpdate(invoiceLineType)) {
                                        try {
                                            PartyPK partyPK = getPartyPK();
                                            InvoiceLineTypeDetailValue invoiceLineTypeDetailValue = invoiceControl.getInvoiceLineTypeDetailValueForUpdate(invoiceLineType);
                                            InvoiceLineTypeDescription invoiceLineTypeDescription = invoiceControl.getInvoiceLineTypeDescriptionForUpdate(invoiceLineType, getPreferredLanguage());
                                            String description = edit.getDescription();

                                            invoiceLineTypeDetailValue.setInvoiceLineTypeName(edit.getInvoiceLineTypeName());
                                            invoiceLineTypeDetailValue.setParentInvoiceLineTypePK(parentInvoiceLineType == null? null: parentInvoiceLineType.getPrimaryKey());
                                            invoiceLineTypeDetailValue.setDefaultGlAccountPK(defaultGlAccount == null? null: defaultGlAccount.getPrimaryKey());
                                            invoiceLineTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                            invoiceLineTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                            invoiceControl.updateInvoiceLineTypeFromValue(invoiceLineTypeDetailValue, partyPK);

                                            if(invoiceLineTypeDescription == null && description != null) {
                                                invoiceControl.createInvoiceLineTypeDescription(invoiceLineType, getPreferredLanguage(), description, partyPK);
                                            } else if(invoiceLineTypeDescription != null && description == null) {
                                                invoiceControl.deleteInvoiceLineTypeDescription(invoiceLineTypeDescription, partyPK);
                                            } else if(invoiceLineTypeDescription != null && description != null) {
                                                InvoiceLineTypeDescriptionValue invoiceLineTypeDescriptionValue = invoiceControl.getInvoiceLineTypeDescriptionValue(invoiceLineTypeDescription);

                                                invoiceLineTypeDescriptionValue.setDescription(description);
                                                invoiceControl.updateInvoiceLineTypeDescriptionFromValue(invoiceLineTypeDescriptionValue, partyPK);
                                            }
                                        } finally {
                                            unlockEntity(invoiceLineType);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
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
                } else {
                    addExecutionError(ExecutionErrors.UnknownInvoiceLineTypeName.name(), invoiceLineTypeName);
                }
                
                if(hasExecutionErrors()) {
                    result.setInvoiceLineType(invoiceControl.getInvoiceLineTypeTransfer(getUserVisit(), invoiceLineType));
                    result.setEntityLock(getEntityLockTransfer(invoiceLineType));
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
        }
        
        return result;
    }
    
}
