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

import com.echothree.control.user.accounting.common.form.GetTransactionGroupStatusChoicesForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetTransactionGroupStatusChoicesCommand
        extends BaseSimpleCommand<GetTransactionGroupStatusChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("TransactionGroupName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("DefaultTransactionGroupStatusChoice", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetTransactionGroupStatusChoicesCommand */
    public GetTransactionGroupStatusChoicesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var result = AccountingResultFactory.getGetTransactionGroupStatusChoicesResult();
        var transactionGroupName = form.getTransactionGroupName();
        var transactionGroup = accountingControl.getTransactionGroupByName(transactionGroupName);
        
        if(transactionGroupName == null || transactionGroup != null) {
            var defaultTransactionGroupStatusChoice = form.getDefaultTransactionGroupStatusChoice();
            var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            
            result.setTransactionGroupStatusChoices(accountingControl.getTransactionGroupStatusChoices(defaultTransactionGroupStatusChoice,
                    getPreferredLanguage(), allowNullChoice, transactionGroup, getPartyPK()));
        } else {
            addExecutionError(ExecutionErrors.UnknownTransactionGroupName.name(), transactionGroupName);
        }
        
        return result;
    }
    
}
