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

import com.echothree.control.user.accounting.common.form.CreateTransactionGlAccountCategoryForm;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateTransactionGlAccountCategoryCommand
        extends BaseSimpleCommand<CreateTransactionGlAccountCategoryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionGlAccountCategory.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TransactionGlAccountCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateTransactionGlAccountCategoryCommand */
    public CreateTransactionGlAccountCategoryCommand(UserVisitPK userVisitPK, CreateTransactionGlAccountCategoryForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        String transactionTypeName = form.getTransactionTypeName();
        TransactionType transactionType = accountingControl.getTransactionTypeByName(transactionTypeName);
        
        if(transactionType != null) {
            String transactionGlAccountCategoryName = form.getTransactionGlAccountCategoryName();
            TransactionGlAccountCategory transactionGlAccountCategory = accountingControl.getTransactionGlAccountCategoryByName(transactionType, transactionGlAccountCategoryName);
            
            if(transactionGlAccountCategory == null) {
                String glAccountCategoryName = form.getGlAccountCategoryName();
                GlAccountCategory glAccountCategory = glAccountCategoryName == null? null: accountingControl.getGlAccountCategoryByName(glAccountCategoryName);
                
                if(glAccountCategoryName == null || glAccountCategory != null) {
                    String glAccountName = form.getGlAccountName();
                    GlAccount glAccount = glAccountName == null? null: accountingControl.getGlAccountByName(glAccountName);

                    if(glAccountName == null || glAccount != null) {
                        if(glAccountCategory == null || glAccount == null? true: glAccountCategory.equals(glAccount.getLastDetail().getGlAccountCategory())) {
                            Integer sortOrder = Integer.valueOf(form.getSortOrder());
                            String description = form.getDescription();
                            PartyPK partyPK = getPartyPK();

                            transactionGlAccountCategory = accountingControl.createTransactionGlAccountCategory(transactionType, transactionGlAccountCategoryName, glAccountCategory, sortOrder, partyPK);
                            
                            if(glAccount != null) {
                                accountingControl.createTransactionGlAccount(transactionGlAccountCategory, glAccount, partyPK);
                            }
                            
                            if(description != null) {
                                accountingControl.createTransactionGlAccountCategoryDescription(transactionGlAccountCategory, getPreferredLanguage(), description, partyPK);
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
                addExecutionError(ExecutionErrors.DuplicateTransactionGlAccountCategoryName.name(), transactionGlAccountCategoryName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTransactionTypeName.name(), transactionTypeName);
        }
        
        return null;
    }
    
}
