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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.TransactionEntityRoleTypeEdit;
import com.echothree.control.user.accounting.common.form.EditTransactionEntityRoleTypeForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.spec.TransactionEntityRoleTypeSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditTransactionEntityRoleTypeCommand
        extends BaseEditCommand<TransactionEntityRoleTypeSpec, TransactionEntityRoleTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionEntityRoleType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TransactionEntityRoleTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionEntityRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditTransactionEntityRoleTypeCommand */
    public EditTransactionEntityRoleTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var result = AccountingResultFactory.getEditTransactionEntityRoleTypeResult();
        var transactionTypeName = spec.getTransactionTypeName();
        var transactionType = accountingControl.getTransactionTypeByNameForUpdate(transactionTypeName);
        
        if(transactionType != null) {
            var transactionEntityRoleTypeName = spec.getTransactionEntityRoleTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                var transactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeByName(transactionType, transactionEntityRoleTypeName);

                if(transactionEntityRoleType != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        if(lockEntity(transactionEntityRoleType)) {
                            var transactionEntityRoleTypeDescription = accountingControl.getTransactionEntityRoleTypeDescription(transactionEntityRoleType, getPreferredLanguage());
                            var edit = AccountingEditFactory.getTransactionEntityRoleTypeEdit();
                            var transactionEntityRoleTypeDetail = transactionEntityRoleType.getLastDetail();
                            var entityTypeDetail = transactionEntityRoleTypeDetail.getEntityType().getLastDetail();
                            var componentVendorDetail = entityTypeDetail.getComponentVendor().getLastDetail();

                            result.setTransactionEntityRoleType(accountingControl.getTransactionEntityRoleTypeTransfer(getUserVisit(), transactionEntityRoleType));

                            result.setEdit(edit);
                            edit.setTransactionEntityRoleTypeName(transactionEntityRoleTypeDetail.getTransactionEntityRoleTypeName());
                            edit.setComponentVendorName(componentVendorDetail.getComponentVendorName());
                            edit.setEntityTypeName(entityTypeDetail.getEntityTypeName());
                            edit.setSortOrder(transactionEntityRoleTypeDetail.getSortOrder().toString());

                            if(transactionEntityRoleTypeDescription != null) {
                                edit.setDescription(transactionEntityRoleTypeDescription.getDescription());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }

                        result.setEntityLock(getEntityLockTransfer(transactionEntityRoleType));
                    } else { // EditMode.ABANDON
                        unlockEntity(transactionEntityRoleType);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTransactionEntityRoleTypeName.name(), transactionTypeName, transactionEntityRoleTypeName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                var transactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeByNameForUpdate(transactionType, transactionEntityRoleTypeName);

                if(transactionEntityRoleType != null) {
                    transactionEntityRoleTypeName = edit.getTransactionEntityRoleTypeName();
                    var duplicateTransactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeByName(transactionType, transactionEntityRoleTypeName);

                    if(duplicateTransactionEntityRoleType == null || transactionEntityRoleType.equals(duplicateTransactionEntityRoleType)) {
                        var componentVendorName = edit.getComponentVendorName();
                        var componentVendor = getComponentControl().getComponentVendorByName(componentVendorName);

                        if(componentVendor != null) {
                            var entityTypeName = edit.getEntityTypeName();
                            var entityType = getEntityTypeControl().getEntityTypeByName(componentVendor, entityTypeName);

                            if(entityType != null) {
                                if(lockEntityForUpdate(transactionEntityRoleType)) {
                                    try {
                                        var partyPK = getPartyPK();
                                        var transactionEntityRoleTypeDetailValue = accountingControl.getTransactionEntityRoleTypeDetailValueForUpdate(transactionEntityRoleType);
                                        var transactionEntityRoleTypeDescription = accountingControl.getTransactionEntityRoleTypeDescriptionForUpdate(transactionEntityRoleType, getPreferredLanguage());
                                        var description = edit.getDescription();

                                        transactionEntityRoleTypeDetailValue.setTransactionEntityRoleTypeName(edit.getTransactionEntityRoleTypeName());
                                        transactionEntityRoleTypeDetailValue.setEntityTypePK(entityType.getPrimaryKey());
                                        transactionEntityRoleTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                        accountingControl.updateTransactionEntityRoleTypeFromValue(transactionEntityRoleTypeDetailValue, partyPK);

                                        if(transactionEntityRoleTypeDescription == null && description != null) {
                                            accountingControl.createTransactionEntityRoleTypeDescription(transactionEntityRoleType, getPreferredLanguage(), description, partyPK);
                                        } else if(transactionEntityRoleTypeDescription != null && description == null) {
                                            accountingControl.deleteTransactionEntityRoleTypeDescription(transactionEntityRoleTypeDescription, partyPK);
                                        } else if(transactionEntityRoleTypeDescription != null && description != null) {
                                            var transactionEntityRoleTypeDescriptionValue = accountingControl.getTransactionEntityRoleTypeDescriptionValue(transactionEntityRoleTypeDescription);

                                            transactionEntityRoleTypeDescriptionValue.setDescription(description);
                                            accountingControl.updateTransactionEntityRoleTypeDescriptionFromValue(transactionEntityRoleTypeDescriptionValue, partyPK);
                                        }
                                    } finally {
                                        unlockEntity(transactionEntityRoleType);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateTransactionEntityRoleTypeName.name(), transactionTypeName, transactionEntityRoleTypeName);
                    }

                    if(hasExecutionErrors()) {
                        result.setTransactionEntityRoleType(accountingControl.getTransactionEntityRoleTypeTransfer(getUserVisit(), transactionEntityRoleType));
                        result.setEntityLock(getEntityLockTransfer(transactionEntityRoleType));
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTransactionEntityRoleTypeName.name(), transactionTypeName, transactionEntityRoleTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTransactionTypeName.name(), transactionTypeName);
        }
        
        return result;
    }
    
}
