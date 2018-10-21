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
import com.echothree.control.user.invoice.remote.edit.InvoiceTypeEdit;
import com.echothree.control.user.invoice.remote.form.EditInvoiceTypeForm;
import com.echothree.control.user.invoice.remote.result.EditInvoiceTypeResult;
import com.echothree.control.user.invoice.remote.result.InvoiceResultFactory;
import com.echothree.control.user.invoice.remote.spec.InvoiceTypeSpec;
import com.echothree.model.control.invoice.server.InvoiceControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
import com.echothree.model.data.invoice.server.entity.InvoiceTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceTypeDetail;
import com.echothree.model.data.invoice.server.value.InvoiceTypeDescriptionValue;
import com.echothree.model.data.invoice.server.value.InvoiceTypeDetailValue;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.sequence.server.entity.SequenceType;
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

public class EditInvoiceTypeCommand
        extends BaseEditCommand<InvoiceTypeSpec, InvoiceTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.InvoiceType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InvoiceTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InvoiceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentInvoiceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InvoiceSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditInvoiceTypeCommand */
    public EditInvoiceTypeCommand(UserVisitPK userVisitPK, EditInvoiceTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        InvoiceControl invoiceControl = (InvoiceControl)Session.getModelController(InvoiceControl.class);
        EditInvoiceTypeResult result = InvoiceResultFactory.getEditInvoiceTypeResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String invoiceTypeName = spec.getInvoiceTypeName();
            InvoiceType invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);
            
            if(invoiceType != null) {
                result.setInvoiceType(invoiceControl.getInvoiceTypeTransfer(getUserVisit(), invoiceType));
                
                if(lockEntity(invoiceType)) {
                    InvoiceTypeDescription invoiceTypeDescription = invoiceControl.getInvoiceTypeDescription(invoiceType, getPreferredLanguage());
                    InvoiceTypeEdit edit = InvoiceEditFactory.getInvoiceTypeEdit();
                    InvoiceTypeDetail invoiceTypeDetail = invoiceType.getLastDetail();
                    InvoiceType parentInvoiceType = invoiceTypeDetail.getParentInvoiceType();
                    SequenceType invoiceSequenceType = invoiceTypeDetail.getInvoiceSequenceType();
                    
                    result.setEdit(edit);
                    edit.setInvoiceTypeName(invoiceTypeDetail.getInvoiceTypeName());
                    edit.setParentInvoiceTypeName(parentInvoiceType == null? null: parentInvoiceType.getLastDetail().getInvoiceTypeName());
                    edit.setInvoiceSequenceTypeName(invoiceSequenceType == null? null: invoiceSequenceType.getLastDetail().getSequenceTypeName());
                    edit.setIsDefault(invoiceTypeDetail.getIsDefault().toString());
                    edit.setSortOrder(invoiceTypeDetail.getSortOrder().toString());
                    
                    if(invoiceTypeDescription != null)
                        edit.setDescription(invoiceTypeDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(invoiceType));
            } else {
                addExecutionError(ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String invoiceTypeName = spec.getInvoiceTypeName();
            InvoiceType invoiceType = invoiceControl.getInvoiceTypeByNameForUpdate(invoiceTypeName);
            
            if(invoiceType != null) {
                invoiceTypeName = edit.getInvoiceTypeName();
                InvoiceType duplicateInvoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);
                
                if(duplicateInvoiceType == null || invoiceType.equals(duplicateInvoiceType)) {
                    String parentInvoiceTypeName = edit.getParentInvoiceTypeName();
                    InvoiceType parentInvoiceType = null;
                    
                    if(parentInvoiceTypeName != null) {
                        parentInvoiceType = invoiceControl.getInvoiceTypeByName(parentInvoiceTypeName);
                    }
                    
                    if(parentInvoiceTypeName == null || parentInvoiceType != null) {
                        if(invoiceControl.isParentInvoiceTypeSafe(invoiceType, parentInvoiceType)) {
                            SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
                            String invoiceSequenceTypeName = edit.getInvoiceSequenceTypeName();
                            SequenceType invoiceSequenceType = sequenceControl.getSequenceTypeByName(invoiceSequenceTypeName);
                            
                            if(invoiceSequenceTypeName == null || invoiceSequenceType != null) {
                                if(lockEntityForUpdate(invoiceType)) {
                                    try {
                                        PartyPK partyPK = getPartyPK();
                                        InvoiceTypeDetailValue invoiceTypeDetailValue = invoiceControl.getInvoiceTypeDetailValueForUpdate(invoiceType);
                                        InvoiceTypeDescription invoiceTypeDescription = invoiceControl.getInvoiceTypeDescriptionForUpdate(invoiceType, getPreferredLanguage());
                                        String description = edit.getDescription();
                                        
                                        invoiceTypeDetailValue.setInvoiceTypeName(edit.getInvoiceTypeName());
                                        invoiceTypeDetailValue.setParentInvoiceTypePK(parentInvoiceType == null? null: parentInvoiceType.getPrimaryKey());
                                        invoiceTypeDetailValue.setInvoiceSequenceTypePK(invoiceSequenceType == null? null: invoiceSequenceType.getPrimaryKey());
                                        invoiceTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                        invoiceTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                        
                                        invoiceControl.updateInvoiceTypeFromValue(invoiceTypeDetailValue, partyPK);
                                        
                                        if(invoiceTypeDescription == null && description != null) {
                                            invoiceControl.createInvoiceTypeDescription(invoiceType, getPreferredLanguage(), description, partyPK);
                                        } else if(invoiceTypeDescription != null && description == null) {
                                            invoiceControl.deleteInvoiceTypeDescription(invoiceTypeDescription, partyPK);
                                        } else if(invoiceTypeDescription != null && description != null) {
                                            InvoiceTypeDescriptionValue invoiceTypeDescriptionValue = invoiceControl.getInvoiceTypeDescriptionValue(invoiceTypeDescription);
                                            
                                            invoiceTypeDescriptionValue.setDescription(description);
                                            invoiceControl.updateInvoiceTypeDescriptionFromValue(invoiceTypeDescriptionValue, partyPK);
                                        }
                                    } finally {
                                        unlockEntity(invoiceType);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownInvoiceSequenceTypeName.name(), invoiceSequenceTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidParentInvoiceType.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownParentInvoiceTypeName.name(), parentInvoiceTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateInvoiceTypeName.name(), invoiceTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
            }
            
            if(hasExecutionErrors()) {
                result.setInvoiceType(invoiceControl.getInvoiceTypeTransfer(getUserVisit(), invoiceType));
                result.setEntityLock(getEntityLockTransfer(invoiceType));
            }
        }
        
        return result;
    }
    
}
