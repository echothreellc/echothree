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

package com.echothree.control.user.sales.server.command;

import com.echothree.control.user.sales.common.form.CreateSalesOrderBatchForm;
import com.echothree.control.user.sales.common.result.SalesResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.sales.server.logic.SalesOrderBatchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateSalesOrderBatchCommand
        extends BaseSimpleCommand<CreateSalesOrderBatchForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SalesOrderBatch.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Count", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("Amount:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_LINE, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateSalesOrderBatchCommand */
    public CreateSalesOrderBatchCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SalesResultFactory.getCreateSalesOrderBatchResult();
        var accountingControl = Session.getModelController(AccountingControl.class);
        var currencyIsoName = form.getCurrencyIsoName();

        var currency = accountingControl.getCurrencyByIsoName(currencyIsoName);

        if(currency != null) {
            var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
            var paymentMethodName = form.getPaymentMethodName();
            var paymentMethod = paymentMethodControl.getPaymentMethodByName(paymentMethodName);

            if(paymentMethod != null) {
                var strCount = form.getCount();
                var count = strCount == null ? null : Long.valueOf(strCount);
                var strAmount = form.getAmount();
                var amount = strAmount == null ? null : Long.valueOf(strAmount);

                var batch = SalesOrderBatchLogic.getInstance().createBatch(this, currency, paymentMethod, count, amount, getPartyPK());

                if(!hasExecutionErrors()) {
                    result.setBatchName(batch.getLastDetail().getBatchName());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPaymentMethodName.name(), paymentMethodName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
        }
        
        return result;
    }
    
}
