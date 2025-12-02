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

import com.echothree.control.user.accounting.common.form.GetTransactionsForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.TransactionGroupLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.model.data.accounting.server.entity.TransactionGroup;
import com.echothree.model.data.accounting.server.factory.TransactionFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetTransactionsCommand
        extends BasePaginatedMultipleEntitiesCommand<Transaction, GetTransactionsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Transaction.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TransactionGroupName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetTransactionsCommand */
    public GetTransactionsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    TransactionGroup transactionGroup;

    @Override
    protected void handleForm() {
        final var transactionGroupName = form.getTransactionGroupName();

        if(transactionGroupName != null) {
            transactionGroup = TransactionGroupLogic.getInstance().getTransactionGroupByName(this, transactionGroupName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long totalEntities = null;

        if(!hasExecutionErrors()) {
            var accountingControl = Session.getModelController(AccountingControl.class);

            totalEntities = transactionGroup == null ?
                    accountingControl.countTransactions() :
                    accountingControl.countTransactionsByTransactionGroup(transactionGroup);
        }

        return totalEntities;
    }

    @Override
    protected Collection<Transaction> getEntities() {
        Collection<Transaction> entities = null;

        if(!hasExecutionErrors()) {
            final var accountingControl = Session.getModelController(AccountingControl.class);

            entities = transactionGroup == null ?
                    accountingControl.getTransactions() :
                    accountingControl.getTransactionsByTransactionGroup(transactionGroup);
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(final Collection<Transaction> entities) {
        final var result = AccountingResultFactory.getGetTransactionsResult();

        if(entities != null) {
            final var accountingControl = Session.getModelController(AccountingControl.class);
            final var userVisit = getUserVisit();

            if(session.hasLimit(TransactionFactory.class)) {
                result.setTransactionCount(getTotalEntities());
            }

            if(transactionGroup != null) {
                result.setTransactionGroup(accountingControl.getTransactionGroupTransfer(userVisit, transactionGroup));
            }

            result.setTransactions(accountingControl.getTransactionTransfers(userVisit, entities));
        }

        return result;
    }
}
