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
    
    /** Creates a new instance of BaseCurrencyFieldType */
    public BaseCurrencyFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue, String[] splitFieldName,
            FieldDefinition fieldDefinition) {
        super(validator, baseForm, validationMessages, fieldValue, splitFieldName, fieldDefinition);
    }
    
    public Currency getCurrency() {
        var currency = validator.getCurrency();
        
        if(currency == null) {
            var parameters = getSplitFieldNameParameters();
            
            if(fieldValue != null) {
                var currencyIsoName = getParameterValue(parameters, CURRENCY_ISO_NAME); // beware nulls
                var vendorName = getParameterValue(parameters, VENDOR_NAME); // beware nulls
                
                if(currencyIsoName != null) {
                    currency = validator.getAccountingControl().getCurrencyByIsoName(currencyIsoName);
                    
                    if(currency == null) {
                        validationMessages.add(fieldName, new Message(Validator.ERROR_UNKNOWN_CURRENCY_ISO_NAME, currencyIsoName));
                    }
                } else if(vendorName != null) {
                    var vendor = validator.getVendorControl().getVendorByName(vendorName);
                    
                    if(vendor != null) {
                        currency = validator.getPartyControl().getPreferredCurrency(vendor.getParty());
                    } else {
                        validationMessages.add(fieldName, new Message(Validator.ERROR_UNKNOWN_VENDOR_NAME, vendorName));
                    }
                } else {
                    currency = validator.getAccountingControl().getDefaultCurrency();
                }
            }
        }
        
        return currency;
    }
    
}
