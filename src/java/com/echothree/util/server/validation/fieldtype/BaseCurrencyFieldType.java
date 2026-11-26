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

package com.echothree.util.server.validation.fieldtype;

import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.validation.Validator;

public abstract class BaseCurrencyFieldType
        extends BaseFieldType {
    
    private static final String CURRENCY_ISO_NAME = "CurrencyIsoName";
    private static final String VENDOR_NAME = "VendorName";
    
    private boolean currencyResolved;
    private Currency resolvedCurrency;

    /** Creates a new instance of BaseCurrencyFieldType */
    public BaseCurrencyFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue, String[] splitFieldName,
            FieldDefinition fieldDefinition) {
        super(validator, baseForm, validationMessages, fieldValue, splitFieldName, fieldDefinition);
    }
    
    public Currency getCurrency() {
        if(!currencyResolved) {
            currencyResolved = true;
            resolvedCurrency = validator.getCurrency();

            if(resolvedCurrency == null && validator.isCurrencySpecified()) {
                validationMessages.add(fieldName, new Message(Validator.ERROR_MISSING_CURRENCY));
            } else if(resolvedCurrency == null) {
                var parameters = getSplitFieldNameParameters();

                if(parameters.containsKey(CURRENCY_ISO_NAME)) {
                    var currencyIsoName = getParameterValue(parameters, CURRENCY_ISO_NAME);

                    if(currencyIsoName == null || currencyIsoName.isBlank()) {
                        validationMessages.add(fieldName, new Message(Validator.ERROR_MISSING_CURRENCY));
                    } else {
                        resolvedCurrency = validator.getAccountingControl().getCurrencyByIsoName(currencyIsoName);

                        if(resolvedCurrency == null) {
                            validationMessages.add(fieldName, new Message(Validator.ERROR_UNKNOWN_CURRENCY_ISO_NAME, currencyIsoName));
                        }
                    }
                } else if(parameters.containsKey(VENDOR_NAME)) {
                    var vendorName = getParameterValue(parameters, VENDOR_NAME);
                    var vendor = vendorName == null ? null : validator.getVendorControl().getVendorByName(vendorName);

                    if(vendor == null) {
                        validationMessages.add(fieldName, new Message(Validator.ERROR_UNKNOWN_VENDOR_NAME, vendorName));
                    } else {
                        resolvedCurrency = validator.getPartyControl().getPreferredCurrency(vendor.getParty());

                        if(resolvedCurrency == null) {
                            validationMessages.add(fieldName, new Message(Validator.ERROR_MISSING_CURRENCY));
                        }
                    }
                } else {
                    resolvedCurrency = validator.getAccountingControl().getDefaultCurrency();

                    if(resolvedCurrency == null) {
                        validationMessages.add(fieldName, new Message(Validator.ERROR_MISSING_CURRENCY));
                    }
                }
            }
        }

        return resolvedCurrency;
    }

}
