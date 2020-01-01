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

package com.echothree.util.server.validation.fieldtype;

import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.validation.Validator;
import java.util.Map;

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
        Currency currency = validator.getCurrency();
        
        if(currency == null) {
            Map<String, String> parameters = getSplitFieldNameParameters();
            
            if(fieldValue != null) {
                String currencyIsoName = getParameterValue(parameters, CURRENCY_ISO_NAME); // beware nulls
                String vendorName = getParameterValue(parameters, VENDOR_NAME); // beware nulls
                
                if(currencyIsoName != null) {
                    currency = validator.getAccountingControl().getCurrencyByIsoName(currencyIsoName);
                    
                    if(currency == null) {
                        validationMessages.add(fieldName, new Message(Validator.ERROR_UNKOWN_CURRENCY_ISO_NAME, currencyIsoName));
                    }
                } else if(vendorName != null) {
                    Vendor vendor = validator.getVendorControl().getVendorByName(vendorName);
                    
                    if(vendor != null) {
                        currency = validator.getPartyControl().getPreferredCurrency(vendor.getParty());
                    } else {
                        validationMessages.add(fieldName, new Message(Validator.ERROR_UNKOWN_VENDOR_NAME, vendorName));
                    }
                } else {
                    currency = validator.getAccountingControl().getDefaultCurrency();
                }
            }
        }
        
        return currency;
    }
    
}
