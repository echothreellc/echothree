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

import com.echothree.control.user.term.common.edit.CustomerTypeCreditLimitEdit;
import com.echothree.control.user.term.common.edit.TermEditFactory;
import com.echothree.control.user.term.common.result.EditCustomerTypeCreditLimitResult;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.control.user.term.common.spec.CustomerTypeCreditLimitSpec;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.term.server.entity.CustomerTypeCreditLimit;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.validation.Validator;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditCustomerTypeCreditLimitCommand
        extends BaseEditCommand<CustomerTypeCreditLimitSpec, CustomerTypeCreditLimitEdit, EditCustomerTypeCreditLimitResult, CustomerTypeCreditLimit, CustomerType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CreditLimit", FieldType.UNSIGNED_PRICE_LINE, false, null, null),
                new FieldDefinition("PotentialCreditLimit", FieldType.UNSIGNED_PRICE_LINE, false, null, null)
        );
    }

    private Currency currency;
    private boolean currencyResolved;

    @Inject
    TermControl termControl;

    @Inject
    CurrencyLogic currencyLogic;

    @Inject
    CustomerTypeLogic customerTypeLogic;

    /** Creates a new instance of EditCustomerTypeCreditLimitCommand */
    public EditCustomerTypeCreditLimitCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    private Currency resolveCurrency() {
        if(!currencyResolved) {
            currency = currencyLogic.getCurrencyByName(this, spec.getCurrencyIsoName());
            currencyResolved = true;
        }

        return currency;
    }

    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        validator.setCurrency(resolveCurrency());
    }

    @Override
    public EditCustomerTypeCreditLimitResult getResult() {
        return TermResultFactory.getEditCustomerTypeCreditLimitResult();
    }

    @Override
    public CustomerTypeCreditLimitEdit getEdit() {
        return TermEditFactory.getCustomerTypeCreditLimitEdit();
    }

    @Override
    public CustomerTypeCreditLimit getEntity(EditCustomerTypeCreditLimitResult result) {
        CustomerTypeCreditLimit customerTypeCreditLimit = null;
        var customerTypeName = spec.getCustomerTypeName();
        var currencyIsoName = spec.getCurrencyIsoName();
        var customerType = customerTypeLogic.getCustomerTypeByName(this, customerTypeName);
        var currency = resolveCurrency();

        if(!hasExecutionErrors()) {
            customerTypeCreditLimit = termControl.getCustomerTypeCreditLimit(customerType, currency, editModeToEntityPermission(editMode));

            if(customerTypeCreditLimit == null) {
                addExecutionError(ExecutionErrors.UnknownCustomerTypeCreditLimit.name(), customerTypeName, currencyIsoName);
            }
        }

        return customerTypeCreditLimit;
    }

    @Override
    public CustomerType getLockEntity(CustomerTypeCreditLimit customerTypeCreditLimit) {
        return customerTypeCreditLimit.getCustomerType();
    }

    @Override
    public void fillInResult(EditCustomerTypeCreditLimitResult result, CustomerTypeCreditLimit customerTypeCreditLimit) {
        result.setCustomerTypeCreditLimit(termControl.getCustomerTypeCreditLimitTransfer(getUserVisit(), customerTypeCreditLimit));
    }

    @Override
    public void doLock(CustomerTypeCreditLimitEdit edit, CustomerTypeCreditLimit customerTypeCreditLimit) {
        var currency = customerTypeCreditLimit.getCurrency();
        var amountUtils = AmountUtils.getInstance();

        edit.setCreditLimit(amountUtils.formatPriceLine(currency, customerTypeCreditLimit.getCreditLimit()));
        edit.setPotentialCreditLimit(amountUtils.formatPriceLine(currency, customerTypeCreditLimit.getPotentialCreditLimit()));
    }

    @Override
    public void doUpdate(CustomerTypeCreditLimit customerTypeCreditLimit) {
        var customerTypeCreditLimitValue = termControl.getCustomerTypeCreditLimitValue(customerTypeCreditLimit);
        var creditLimit = edit.getCreditLimit();
        var potentialCreditLimit = edit.getPotentialCreditLimit();

        customerTypeCreditLimitValue.setCreditLimit(creditLimit == null ? null : Long.valueOf(creditLimit));
        customerTypeCreditLimitValue.setPotentialCreditLimit(potentialCreditLimit == null ? null : Long.valueOf(potentialCreditLimit));

        if(customerTypeCreditLimitValue.hasBeenModified()) {
            termControl.updateCustomerTypeCreditLimitFromValue(customerTypeCreditLimitValue, getPartyPK());
        }
    }

}
