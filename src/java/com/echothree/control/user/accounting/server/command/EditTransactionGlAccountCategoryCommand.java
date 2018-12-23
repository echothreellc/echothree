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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.TransactionGlAccountCategoryEdit;
import com.echothree.control.user.accounting.common.form.EditTransactionGlAccountCategoryForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditTransactionGlAccountCategoryResult;
import com.echothree.control.user.accounting.common.spec.TransactionGlAccountCategorySpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccount;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategoryDescription;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategoryDetail;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.accounting.server.value.TransactionGlAccountCategoryDescriptionValue;
import com.echothree.model.data.accounting.server.value.TransactionGlAccountCategoryDetailValue;
import com.echothree.model.data.accounting.server.value.TransactionGlAccountValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditTransactionGlAccountCategoryCommand
        extends BaseEditCommand<TransactionGlAccountCategorySpec, TransactionGlAccountCategoryEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionGlAccountCategory.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TransactionGlAccountCategoryName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionGlAccountCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditTransactionGlAccountCategoryCommand */
    public EditTransactionGlAccountCategoryCommand(UserVisitPK userVisitPK, EditTransactionGlAccountCategoryForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        EditTransactionGlAccountCategoryResult result = AccountingResultFactory.getEditTransactionGlAccountCategoryResult();
        String transactionTypeName = spec.getTransactionTypeName();
        TransactionType transactionType = accountingControl.getTransactionTypeByNameForUpdate(transactionTypeName);
        
        if(transactionType != null) {
            String transactionGlAccountCategoryName = spec.getTransactionGlAccountCategoryName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                TransactionGlAccountCategory transactionGlAccountCategory = accountingControl.getTransactionGlAccountCategoryByName(transactionType, transactionGlAccountCategoryName);

                if(transactionGlAccountCategory != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        if(lockEntity(transactionGlAccountCategory)) {
                            TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription = accountingControl.getTransactionGlAccountCategoryDescription(transactionGlAccountCategory, getPreferredLanguage());
                            TransactionGlAccount transactionGlAccount = accountingControl.getTransactionGlAccount(transactionGlAccountCategory);
                            TransactionGlAccountCategoryEdit edit = AccountingEditFactory.getTransactionGlAccountCategoryEdit();
                            TransactionGlAccountCategoryDetail transactionGlAccountCategoryDetail = transactionGlAccountCategory.getLastDetail();
                            GlAccountCategory glAccountCategory = transactionGlAccountCategoryDetail.getGlAccountCategory();

                            result.setTransactionGlAccountCategory(accountingControl.getTransactionGlAccountCategoryTransfer(getUserVisit(), transactionGlAccountCategory));
                            sendEventUsingNames(transactionGlAccountCategory.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());

                            result.setEdit(edit);
                            edit.setTransactionGlAccountCategoryName(transactionGlAccountCategoryDetail.getTransactionGlAccountCategoryName());
                            edit.setGlAccountCategoryName(glAccountCategory == null? null: glAccountCategory.getLastDetail().getGlAccountCategoryName());
                            edit.setSortOrder(transactionGlAccountCategoryDetail.getSortOrder().toString());
                            edit.setGlAccountName(transactionGlAccount == null? null: transactionGlAccount.getGlAccount().getLastDetail().getGlAccountName());

                            if(transactionGlAccountCategoryDescription != null) {
                                edit.setDescription(transactionGlAccountCategoryDescription.getDescription());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }

                        result.setEntityLock(getEntityLockTransfer(transactionGlAccountCategory));
                    } else { // EditMode.ABANDON
                        unlockEntity(transactionGlAccountCategory);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTransactionGlAccountCategoryName.name(), transactionTypeName, transactionGlAccountCategoryName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                TransactionGlAccountCategory transactionGlAccountCategory = accountingControl.getTransactionGlAccountCategoryByNameForUpdate(transactionType, transactionGlAccountCategoryName);

                if(transactionGlAccountCategory != null) {
                    transactionGlAccountCategoryName = edit.getTransactionGlAccountCategoryName();
                    TransactionGlAccountCategory duplicateTransactionGlAccountCategory = accountingControl.getTransactionGlAccountCategoryByName(transactionType, transactionGlAccountCategoryName);

                    if(duplicateTransactionGlAccountCategory == null || transactionGlAccountCategory.equals(duplicateTransactionGlAccountCategory)) {
                        String glAccountCategoryName = edit.getGlAccountCategoryName();
                        GlAccountCategory glAccountCategory = glAccountCategoryName == null? null: accountingControl.getGlAccountCategoryByName(glAccountCategoryName);

                        if(glAccountCategoryName == null || glAccountCategory != null) {
                            String glAccountName = edit.getGlAccountName();
                            GlAccount glAccount = glAccountName == null? null: accountingControl.getGlAccountByName(glAccountName);

                            if(glAccountName == null || glAccount != null) {
                                if(glAccountCategory == null || glAccount == null? true: glAccountCategory.equals(glAccount.getLastDetail().getGlAccountCategory())) {
                                    if(lockEntityForUpdate(transactionGlAccountCategory)) {
                                        try {
                                            PartyPK partyPK = getPartyPK();
                                            TransactionGlAccountCategoryDetailValue transactionGlAccountCategoryDetailValue = accountingControl.getTransactionGlAccountCategoryDetailValueForUpdate(transactionGlAccountCategory);
                                            TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription = accountingControl.getTransactionGlAccountCategoryDescriptionForUpdate(transactionGlAccountCategory, getPreferredLanguage());
                                            TransactionGlAccount transactionGlAccount = accountingControl.getTransactionGlAccount(transactionGlAccountCategory);
                                            String description = edit.getDescription();

                                            transactionGlAccountCategoryDetailValue.setTransactionGlAccountCategoryName(edit.getTransactionGlAccountCategoryName());
                                            transactionGlAccountCategoryDetailValue.setGlAccountCategoryPK(glAccountCategory == null? null: glAccountCategory.getPrimaryKey());
                                            transactionGlAccountCategoryDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                            accountingControl.updateTransactionGlAccountCategoryFromValue(transactionGlAccountCategoryDetailValue, partyPK);

                                            if(transactionGlAccountCategoryDescription == null && description != null) {
                                                accountingControl.createTransactionGlAccountCategoryDescription(transactionGlAccountCategory, getPreferredLanguage(), description, partyPK);
                                            } else if(transactionGlAccountCategoryDescription != null && description == null) {
                                                accountingControl.deleteTransactionGlAccountCategoryDescription(transactionGlAccountCategoryDescription, partyPK);
                                            } else if(transactionGlAccountCategoryDescription != null && description != null) {
                                                TransactionGlAccountCategoryDescriptionValue transactionGlAccountCategoryDescriptionValue = accountingControl.getTransactionGlAccountCategoryDescriptionValue(transactionGlAccountCategoryDescription);

                                                transactionGlAccountCategoryDescriptionValue.setDescription(description);
                                                accountingControl.updateTransactionGlAccountCategoryDescriptionFromValue(transactionGlAccountCategoryDescriptionValue, partyPK);
                                            }

                                            if(transactionGlAccount == null && glAccount != null) {
                                                accountingControl.createTransactionGlAccount(transactionGlAccountCategory, glAccount, partyPK);
                                            } else if(transactionGlAccount != null && glAccount == null) {
                                                accountingControl.deleteTransactionGlAccount(transactionGlAccount, partyPK);
                                            } else if(transactionGlAccount != null && glAccount != null) {
                                                TransactionGlAccountValue transactionGlAccountValue = accountingControl.getTransactionGlAccountValue(transactionGlAccount);

                                                transactionGlAccountValue.setGlAccountPK(glAccount.getPrimaryKey());
                                                accountingControl.updateTransactionGlAccountFromValue(transactionGlAccountValue, partyPK);
                                            }
                                        } finally {
                                            unlockEntity(transactionGlAccountCategory);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.InvalidGlAccountCategory.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownGlAccountName.name(), glAccountName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownGlAccountCategoryName.name(), glAccountCategoryName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateTransactionGlAccountCategoryName.name(), transactionTypeName, transactionGlAccountCategoryName);
                    }

                    if(hasExecutionErrors()) {
                        result.setTransactionGlAccountCategory(accountingControl.getTransactionGlAccountCategoryTransfer(getUserVisit(), transactionGlAccountCategory));
                        result.setEntityLock(getEntityLockTransfer(transactionGlAccountCategory));
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTransactionGlAccountCategoryName.name(), transactionTypeName, transactionGlAccountCategoryName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTransactionTypeName.name(), transactionTypeName);
        }
        
        return result;
    }
    
}
