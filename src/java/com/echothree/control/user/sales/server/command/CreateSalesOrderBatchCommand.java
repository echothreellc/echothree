// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.sales.common.result.CreateSalesOrderBatchResult;
import com.echothree.control.user.sales.common.result.SalesResultFactory;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.sales.server.logic.SalesOrderBatchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
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
                new FieldDefinition("Amount:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_LINE, Boolean.FALSE, null, null)
                ));
    }
    
    /** Creates a new instance of CreateSalesOrderBatchCommand */
    public CreateSalesOrderBatchCommand(UserVisitPK userVisitPK, CreateSalesOrderBatchForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CreateSalesOrderBatchResult result = SalesResultFactory.getCreateSalesOrderBatchResult();
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        String currencyIsoName = form.getCurrencyIsoName();

        Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);

        if(currency != null) {
            var paymentMethodControl = (PaymentMethodControl)Session.getModelController(PaymentMethodControl.class);
            String paymentMethodName = form.getPaymentMethodName();
            PaymentMethod paymentMethod = paymentMethodControl.getPaymentMethodByName(paymentMethodName);

            if(paymentMethod != null) {
                String strCount = form.getCount();
                Long count = strCount == null ? null : Long.valueOf(strCount);
                String strAmount = form.getAmount();
                Long amount = strAmount == null ? null : Long.valueOf(strAmount);

                Batch batch = SalesOrderBatchLogic.getInstance().createBatch(this, currency, paymentMethod, count, amount, getPartyPK());

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
