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

import com.echothree.control.user.accounting.common.form.GetTransactionGlAccountCategoriesForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.TransactionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.accounting.server.factory.TransactionTypeFactory;
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
public class GetTransactionGlAccountCategoriesCommand
        extends BasePaginatedMultipleEntitiesCommand<TransactionGlAccountCategory, GetTransactionGlAccountCategoriesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionGlAccountCategory.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetTransactionGlAccountCategoriesCommand */
    public GetTransactionGlAccountCategoriesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    TransactionType transactionType;

    @Override
    protected void handleForm() {
        transactionType = TransactionTypeLogic.getInstance().getTransactionTypeByName(this, form.getTransactionTypeName());
    }

    @Override
    protected Long getTotalEntities() {
        Long totalEntities = null;

        if(!hasExecutionErrors()) {
            var accountingControl = Session.getModelController(AccountingControl.class);

            totalEntities = accountingControl.countTransactionGlAccountCategoriesByTransactionType(transactionType);
        }

        return totalEntities;
    }

    @Override
    protected Collection<TransactionGlAccountCategory> getEntities() {
        Collection<TransactionGlAccountCategory> entities = null;

        if(!hasExecutionErrors()) {
            var accountingControl = Session.getModelController(AccountingControl.class);

            entities = accountingControl.getTransactionGlAccountCategoriesByTransactionType(transactionType);
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<TransactionGlAccountCategory> entities) {
        var result = AccountingResultFactory.getGetTransactionGlAccountCategoriesResult();

        if(entities != null) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var userVisit = getUserVisit();

            result.setTransactionType(accountingControl.getTransactionTypeTransfer(userVisit, transactionType));

            if(session.hasLimit(TransactionTypeFactory.class)) {
                result.setTransactionGlAccountCategoryCount(getTotalEntities());
            }

            result.setTransactionGlAccountCategories(accountingControl.getTransactionGlAccountCategoryTransfers(userVisit, entities));
        }

        return result;
    }

}
