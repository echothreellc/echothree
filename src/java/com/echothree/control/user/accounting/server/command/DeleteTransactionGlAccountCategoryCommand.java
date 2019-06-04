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

import com.echothree.control.user.accounting.common.form.DeleteTransactionGlAccountCategoryForm;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionType;
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

public class DeleteTransactionGlAccountCategoryCommand
        extends BaseSimpleCommand<DeleteTransactionGlAccountCategoryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionGlAccountCategory.name(), SecurityRoles.Delete.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TransactionGlAccountCategoryName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteTransactionGlAccountCategoryCommand */
    public DeleteTransactionGlAccountCategoryCommand(UserVisitPK userVisitPK, DeleteTransactionGlAccountCategoryForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        String transactionTypeName = form.getTransactionTypeName();
        TransactionType transactionType = accountingControl.getTransactionTypeByName(transactionTypeName);
        
        if(transactionType != null) {
            String transactionGlAccountCategoryName = form.getTransactionGlAccountCategoryName();
            TransactionGlAccountCategory transactionGlAccountCategory = accountingControl.getTransactionGlAccountCategoryByNameForUpdate(transactionType, transactionGlAccountCategoryName);
            
            if(transactionGlAccountCategory != null) {
                accountingControl.deleteTransactionGlAccountCategory(transactionGlAccountCategory, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownTransactionGlAccountCategoryName.name(), transactionTypeName, transactionGlAccountCategoryName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTransactionTypeName.name(), transactionTypeName);
        }
        
        return null;
    }
    
}
