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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.TransactionEntityRoleTypeDescriptionEdit;
import com.echothree.control.user.accounting.common.form.EditTransactionEntityRoleTypeDescriptionForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditTransactionEntityRoleTypeDescriptionResult;
import com.echothree.control.user.accounting.common.spec.TransactionEntityRoleTypeDescriptionSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleType;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleTypeDescription;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.accounting.server.value.TransactionEntityRoleTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditTransactionEntityRoleTypeDescriptionCommand
        extends BaseEditCommand<TransactionEntityRoleTypeDescriptionSpec, TransactionEntityRoleTypeDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionEntityRoleType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TransactionEntityRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditTransactionEntityRoleTypeDescriptionCommand */
    public EditTransactionEntityRoleTypeDescriptionCommand(UserVisitPK userVisitPK, EditTransactionEntityRoleTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        EditTransactionEntityRoleTypeDescriptionResult result = AccountingResultFactory.getEditTransactionEntityRoleTypeDescriptionResult();
        String transactionTypeName = spec.getTransactionTypeName();
        TransactionType transactionType = accountingControl.getTransactionTypeByName(transactionTypeName);
        
        if(transactionType != null) {
            String transactionEntityRoleTypeName = spec.getTransactionEntityRoleTypeName();
            TransactionEntityRoleType transactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeByName(transactionType, transactionEntityRoleTypeName);

            if(transactionEntityRoleType != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription = accountingControl.getTransactionEntityRoleTypeDescription(transactionEntityRoleType, language);

                        if(transactionEntityRoleTypeDescription != null) {
                            if(editMode.equals(EditMode.LOCK)) {
                                result.setTransactionEntityRoleTypeDescription(accountingControl.getTransactionEntityRoleTypeDescriptionTransfer(getUserVisit(), transactionEntityRoleTypeDescription));

                                if(lockEntity(transactionEntityRoleType)) {
                                    TransactionEntityRoleTypeDescriptionEdit edit = AccountingEditFactory.getTransactionEntityRoleTypeDescriptionEdit();

                                    result.setEdit(edit);
                                    edit.setDescription(transactionEntityRoleTypeDescription.getDescription());
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }

                                result.setEntityLock(getEntityLockTransfer(transactionEntityRoleType));
                            } else { // EditMode.ABANDON
                                unlockEntity(transactionEntityRoleType);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownTransactionEntityRoleTypeDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        TransactionEntityRoleTypeDescriptionValue transactionEntityRoleTypeDescriptionValue = accountingControl.getTransactionEntityRoleTypeDescriptionValueForUpdate(transactionEntityRoleType, language);

                        if(transactionEntityRoleTypeDescriptionValue != null) {
                            if(lockEntityForUpdate(transactionEntityRoleType)) {
                                try {
                                    String description = edit.getDescription();

                                    transactionEntityRoleTypeDescriptionValue.setDescription(description);

                                    accountingControl.updateTransactionEntityRoleTypeDescriptionFromValue(transactionEntityRoleTypeDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(transactionEntityRoleType);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownTransactionEntityRoleTypeDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTransactionEntityRoleTypeName.name(), transactionEntityRoleTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTransactionTypeName.name(), transactionTypeName);
        }
        
        return result;
    }
    
}
