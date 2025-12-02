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
import com.echothree.control.user.accounting.common.edit.TransactionTypeEdit;
import com.echothree.control.user.accounting.common.form.EditTransactionTypeForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.spec.TransactionTypeSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditTransactionTypeCommand
        extends BaseEditCommand<TransactionTypeSpec, TransactionTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditTransactionTypeCommand */
    public EditTransactionTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var result = AccountingResultFactory.getEditTransactionTypeResult();
        var transactionTypeName = spec.getTransactionTypeName();
        
        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            var transactionType = accountingControl.getTransactionTypeByName(transactionTypeName);
            
            if(transactionType != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    if(lockEntity(transactionType)) {
                        var transactionTypeDescription = accountingControl.getTransactionTypeDescription(transactionType, getPreferredLanguage());
                        var edit = AccountingEditFactory.getTransactionTypeEdit();
                        var transactionTypeDetail = transactionType.getLastDetail();

                        result.setTransactionType(accountingControl.getTransactionTypeTransfer(getUserVisit(), transactionType));

                        result.setEdit(edit);
                        edit.setTransactionTypeName(transactionTypeDetail.getTransactionTypeName());
                        edit.setSortOrder(transactionTypeDetail.getSortOrder().toString());

                        if(transactionTypeDescription != null) {
                            edit.setDescription(transactionTypeDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }

                    result.setEntityLock(getEntityLockTransfer(transactionType));
                } else { // EditMode.ABANDON
                    unlockEntity(transactionType);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTransactionTypeName.name(), transactionTypeName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var transactionType = accountingControl.getTransactionTypeByNameForUpdate(transactionTypeName);
            
            if(transactionType != null) {
                transactionTypeName = edit.getTransactionTypeName();
                var duplicateTransactionType = accountingControl.getTransactionTypeByName(transactionTypeName);
                
                if(duplicateTransactionType == null || transactionType.equals(duplicateTransactionType)) {
                    if(lockEntityForUpdate(transactionType)) {
                        try {
                            var partyPK = getPartyPK();
                            var transactionTypeDetailValue = accountingControl.getTransactionTypeDetailValueForUpdate(transactionType);
                            var transactionTypeDescription = accountingControl.getTransactionTypeDescriptionForUpdate(transactionType, getPreferredLanguage());
                            var description = edit.getDescription();

                            transactionTypeDetailValue.setTransactionTypeName(edit.getTransactionTypeName());
                            transactionTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                            accountingControl.updateTransactionTypeFromValue(transactionTypeDetailValue, partyPK);

                            if(transactionTypeDescription == null && description != null) {
                                accountingControl.createTransactionTypeDescription(transactionType, getPreferredLanguage(), description, partyPK);
                            } else if(transactionTypeDescription != null && description == null) {
                                accountingControl.deleteTransactionTypeDescription(transactionTypeDescription, partyPK);
                            } else if(transactionTypeDescription != null && description != null) {
                                var transactionTypeDescriptionValue = accountingControl.getTransactionTypeDescriptionValue(transactionTypeDescription);

                                transactionTypeDescriptionValue.setDescription(description);
                                accountingControl.updateTransactionTypeDescriptionFromValue(transactionTypeDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(transactionType);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateTransactionTypeName.name(), transactionTypeName);
                }
                
                if(hasExecutionErrors()) {
                    result.setTransactionType(accountingControl.getTransactionTypeTransfer(getUserVisit(), transactionType));
                    result.setEntityLock(getEntityLockTransfer(transactionType));
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTransactionTypeName.name(), transactionTypeName);
            }
        }
        
        return result;
    }
    
}
