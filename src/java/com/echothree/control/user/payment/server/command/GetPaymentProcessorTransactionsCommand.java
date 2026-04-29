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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.form.GetPaymentProcessorTransactionsForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.control.PaymentProcessorTransactionControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransaction;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTransactionFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPaymentProcessorTransactionsCommand
        extends BasePaginatedMultipleEntitiesCommand<PaymentProcessorTransaction, GetPaymentProcessorTransactionsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessorTransaction.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    /** Creates a new instance of GetPaymentProcessorTransactionsCommand */
    public GetPaymentProcessorTransactionsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Inject
    PaymentProcessorTransactionControl paymentProcessorTransactionControl;

    @Override
    protected void handleForm() {
        // No form fields to handle
    }

    @Override
    protected Long getTotalEntities() {
        return paymentProcessorTransactionControl.countPaymentProcessorTransactions();
    }

    @Override
    protected Collection<PaymentProcessorTransaction> getEntities() {
        return paymentProcessorTransactionControl.getPaymentProcessorTransactions();
    }

    @Override
    protected BaseResult getResult(Collection<PaymentProcessorTransaction> entities) {
        var result = PaymentResultFactory.getGetPaymentProcessorTransactionsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(session.hasLimit(PaymentProcessorTransactionFactory.class)) {
                result.setPaymentProcessorTransactionCount(paymentProcessorTransactionControl.countPaymentProcessorTransactions());
            }

            result.setPaymentProcessorTransactions(paymentProcessorTransactionControl.getPaymentProcessorTransactionTransfers(userVisit, entities));
        }

        return result;
    }
    
}
