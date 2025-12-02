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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.form.DeleteCustomerTypeCreditLimitForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.term.server.control.TermControl;
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
public class DeleteCustomerTypeCreditLimitCommand
        extends BaseSimpleCommand<DeleteCustomerTypeCreditLimitForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
        new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
        new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteCustomerTypeCreditLimitCommand */
    public DeleteCustomerTypeCreditLimitCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var customerControl = Session.getModelController(CustomerControl.class);
        var customerTypeName = form.getCustomerTypeName();
        var customerType = customerControl.getCustomerTypeByName(customerTypeName);
        
        if(customerType != null) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var currencyIsoName = form.getCurrencyIsoName();
            var currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
            
            if(currency != null) {
                var termControl = Session.getModelController(TermControl.class);
                var customerTypeCreditLimit = termControl.getCustomerTypeCreditLimitForUpdate(customerType, currency);
                
                if(customerTypeCreditLimit != null) {
                    termControl.deleteCustomerTypeCreditLimit(customerTypeCreditLimit, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeCreditLimit.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }
        
        return null;
    }
    
}
