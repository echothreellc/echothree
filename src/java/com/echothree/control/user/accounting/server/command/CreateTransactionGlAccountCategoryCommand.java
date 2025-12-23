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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.form.CreateTransactionGlAccountCategoryForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
import javax.enterprise.context.Dependent;

@Dependent
public class CreateTransactionGlAccountCategoryCommand
        extends BaseSimpleCommand<CreateTransactionGlAccountCategoryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionGlAccountCategory.name(), SecurityRoles.Create.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TransactionGlAccountCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of CreateTransactionGlAccountCategoryCommand */
    public CreateTransactionGlAccountCategoryCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionTypeName = form.getTransactionTypeName();
        var transactionType = accountingControl.getTransactionTypeByName(transactionTypeName);
        
        if(transactionType != null) {
            var transactionGlAccountCategoryName = form.getTransactionGlAccountCategoryName();
            var transactionGlAccountCategory = accountingControl.getTransactionGlAccountCategoryByName(transactionType, transactionGlAccountCategoryName);
            
            if(transactionGlAccountCategory == null) {
                var glAccountCategoryName = form.getGlAccountCategoryName();
                var glAccountCategory = glAccountCategoryName == null? null: accountingControl.getGlAccountCategoryByName(glAccountCategoryName);
                
                if(glAccountCategoryName == null || glAccountCategory != null) {
                    var glAccountName = form.getGlAccountName();
                    var glAccount = glAccountName == null? null: accountingControl.getGlAccountByName(glAccountName);

                    if(glAccountName == null || glAccount != null) {
                        if(glAccountCategory == null || glAccount == null? true: glAccountCategory.equals(glAccount.getLastDetail().getGlAccountCategory())) {
                            var sortOrder = Integer.valueOf(form.getSortOrder());
                            var description = form.getDescription();
                            var partyPK = getPartyPK();

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
