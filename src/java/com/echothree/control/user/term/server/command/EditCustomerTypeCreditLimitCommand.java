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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.edit.CustomerTypeCreditLimitEdit;
import com.echothree.control.user.term.common.edit.TermEditFactory;
import com.echothree.control.user.term.common.form.EditCustomerTypeCreditLimitForm;
import com.echothree.control.user.term.common.result.EditCustomerTypeCreditLimitResult;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.control.user.term.common.spec.CustomerTypeCreditLimitSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.term.server.entity.CustomerTypeCreditLimit;
import com.echothree.model.data.term.server.value.CustomerTypeCreditLimitValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditCustomerTypeCreditLimitCommand
        extends BaseEditCommand<CustomerTypeCreditLimitSpec, CustomerTypeCreditLimitEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CreditLimit", FieldType.UNSIGNED_PRICE_LINE, false, null, null),
                new FieldDefinition("PotentialCreditLimit", FieldType.UNSIGNED_PRICE_LINE, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditCustomerTypeCreditLimitCommand */
    public EditCustomerTypeCreditLimitCommand(UserVisitPK userVisitPK, EditCustomerTypeCreditLimitForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        String currencyIsoName = spec.getCurrencyIsoName();
        validator.setCurrency(accountingControl.getCurrencyByIsoName(currencyIsoName));
    }
    
    @Override
    protected BaseResult execute() {
        var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        EditCustomerTypeCreditLimitResult result = TermResultFactory.getEditCustomerTypeCreditLimitResult();
        String customerTypeName= spec.getCustomerTypeName();
        CustomerType customerType = customerControl.getCustomerTypeByName(customerTypeName);
        
        if(customerType != null) {
            var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
            String currencyIsoName = spec.getCurrencyIsoName();
            Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
            
            if(currency != null) {
                var termControl = (TermControl)Session.getModelController(TermControl.class);
                
                if(editMode.equals(EditMode.LOCK)) {
                    CustomerTypeCreditLimit customerTypeCreditLimit = termControl.getCustomerTypeCreditLimit(customerType, currency);
                    
                    if(customerTypeCreditLimit != null) {
                        result.setCustomerTypeCreditLimit(termControl.getCustomerTypeCreditLimitTransfer(getUserVisit(), customerTypeCreditLimit));
                        
                        if(lockEntity(customerType)) {
                            CustomerTypeCreditLimitEdit edit = TermEditFactory.getCustomerTypeCreditLimitEdit();
                            
                            result.setEdit(edit);
                            edit.setCreditLimit(AmountUtils.getInstance().formatPriceLine(currency, customerTypeCreditLimit.getCreditLimit()));
                            edit.setPotentialCreditLimit(AmountUtils.getInstance().formatPriceLine(currency, customerTypeCreditLimit.getPotentialCreditLimit()));
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(customerType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCustomerTypeCreditLimit.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    CustomerTypeCreditLimitValue customerTypeCreditLimitValue = termControl.getCustomerTypeCreditLimitValueForUpdate(customerType, currency);
                    
                    if(customerTypeCreditLimitValue != null) {
                        if(lockEntityForUpdate(customerType)) {
                            String strCreditLimit = edit.getCreditLimit();
                            Long creditLimit = strCreditLimit == null? null: Long.valueOf(strCreditLimit);
                            String strPotentialCreditLimit = edit.getPotentialCreditLimit();
                            Long potentialCreditLimit = strPotentialCreditLimit == null? null: Long.valueOf(strPotentialCreditLimit);
                            
                            try {
                                customerTypeCreditLimitValue.setCreditLimit(creditLimit);
                                customerTypeCreditLimitValue.setPotentialCreditLimit(potentialCreditLimit);
                                
                                termControl.updateCustomerTypeCreditLimitFromValue(customerTypeCreditLimitValue, getPartyPK());
                            } finally {
                                unlockEntity(customerType);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCustomerTypeCreditLimit.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }
        
        return result;
    }
    
}
