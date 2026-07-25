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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.form.GetCustomerTypeCreditLimitsForm;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.term.server.entity.CustomerTypeCreditLimit;
import com.echothree.model.data.term.server.factory.CustomerTypeCreditLimitFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCustomerTypeCreditLimitsCommand
        extends BasePaginatedMultipleEntitiesCommand<CustomerTypeCreditLimit, GetCustomerTypeCreditLimitsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    CustomerControl customerControl;

    @Inject
    TermControl termControl;

    @Inject
    CustomerTypeLogic customerTypeLogic;
    
    /** Creates a new instance of GetCustomerTypeCreditLimitsCommand */
    public GetCustomerTypeCreditLimitsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    CustomerType customerType;

    @Override
    protected void handleForm() {
        customerType = customerTypeLogic.getCustomerTypeByName(this, form.getCustomerTypeName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : termControl.countCustomerTypeCreditLimitsByCustomerType(customerType);
    }

    @Override
    protected Collection<CustomerTypeCreditLimit> getEntities() {
        return hasExecutionErrors() ? null : termControl.getCustomerTypeCreditLimitsByCustomerType(customerType);
    }

    @Override
    protected BaseResult getResult(Collection<CustomerTypeCreditLimit> entities) {
        var result = TermResultFactory.getGetCustomerTypeCreditLimitsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setCustomerType(customerControl.getCustomerTypeTransfer(userVisit, customerType));

            if(session.hasLimit(CustomerTypeCreditLimitFactory.class)) {
                result.setCustomerTypeCreditLimitCount(getTotalEntities());
            }

            result.setCustomerTypeCreditLimits(termControl.getCustomerTypeCreditLimitTransfers(userVisit, entities));
        }

        return result;
    }
    
}
