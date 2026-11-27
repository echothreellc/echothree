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
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Resolves the currency used to validate a monetary field.
 *
 * <p>A currency supplied through {@link Validator#setCurrency(Currency)} takes precedence. Otherwise,
 * the field name in its {@link FieldDefinition} can include the options below.
 *
 * <dl>
 *   <dt>{@code CurrencyIsoName,FieldName}</dt>
 *   <dd>Reads a currency ISO code from the named form field. A missing or unknown code is a validation error.</dd>
 *   <dt>{@code VendorName,FieldName}</dt>
 *   <dd>Resolves the vendor named in the form field and uses its party's preferred currency. If the party has no
 *       currency preference, this lookup uses the system default.</dd>
 *   <dt>{@code DefaultCurrencyIfOmitted,true}</dt>
 *   <dd>Allows an omitted ISO code to use the {@code VendorName} lookup, if configured, or the system default.
 *       The value {@code true} is a literal flag.</dd>
 * </dl>
 *
 * <p>For example, {@code MinimumPurchaseOrderAmount:CurrencyIsoName,PreferredCurrencyIsoName:DefaultCurrencyIfOmitted,true}
 * uses the submitted preference or the system default when that preference is blank or omitted.
 * With neither currency lookup option configured, validation uses the system default.
 *
 * <p>During form validation, {@link Validator} validates and normalizes any referenced fields in the same pass
 * before parsing the amount, regardless of their declaration order. A validation error on a referenced field
 * prevents parsing the amount.
 */
public abstract class BaseCurrencyFieldType
        extends BaseFieldType {
    
    private static final String CURRENCY_ISO_NAME = "CurrencyIsoName";
    private static final String DEFAULT_CURRENCY_IF_OMITTED = "DefaultCurrencyIfOmitted";
    private static final String VENDOR_NAME = "VendorName";
    
    private boolean currencyResolved;
    private Currency resolvedCurrency;

    /** Creates a new instance of BaseCurrencyFieldType */
    public BaseCurrencyFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue, String[] splitFieldName,
            FieldDefinition fieldDefinition) {
        super(validator, baseForm, validationMessages, fieldValue, splitFieldName, fieldDefinition);
    }

    public static List<String> getFieldDependencies(String[] splitFieldName) {
        var parameters = getSplitFieldNameParameters(splitFieldName);

        return Stream.of(parameters.get(CURRENCY_ISO_NAME), parameters.get(VENDOR_NAME)).filter(Objects::nonNull).toList();
    }
    
    public Currency getCurrency() {
        if(!currencyResolved) {
            currencyResolved = true;
            resolvedCurrency = validator.getCurrency();

            if(resolvedCurrency == null && validator.isCurrencySpecified()) {
                validationMessages.add(fieldName, new Message(Validator.ERROR_MISSING_CURRENCY));
            } else if(resolvedCurrency == null) {
                var parameters = getSplitFieldNameParameters();
                var currencyIsoName = getParameterValue(parameters, CURRENCY_ISO_NAME);
                var useDefaultCurrency = currencyIsoName == null && Boolean.parseBoolean(parameters.get(DEFAULT_CURRENCY_IF_OMITTED));

                if(parameters.containsKey(CURRENCY_ISO_NAME) && !useDefaultCurrency) {
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
