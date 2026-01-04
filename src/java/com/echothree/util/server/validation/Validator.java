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

package com.echothree.util.server.validation;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.control.BaseCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.fieldtype.*;
import com.google.common.base.Splitter;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Validator {
    
    private final BaseCommand baseCommand;
    private AccountingControl accountingControl = null;
    private PartyControl partyControl = null;
    private UserControl userControl = null;
    private VendorControl vendorControl = null;
    private Log log = null;
    
    private Currency currency = null;
    
    public static final String ERROR_REQUIRED_FIELD            = "RequiredField";
    public static final String ERROR_MINIMUM_LENGTH            = "MinimumLength";
    public static final String ERROR_MAXIMUM_LENGTH            = "MaximumLength";
    public static final String ERROR_MINIMUM_VALUE             = "MinimumValue";
    public static final String ERROR_MAXIMUM_VALUE             = "MaximumValue";
    public static final String ERROR_NO_VALUE_ALLOWED          = "NoValueAllowed";
    public static final String ERROR_UNKNOWN_CURRENCY_ISO_NAME = "UnknownCurrencyIsoName";
    public static final String ERROR_UNKNOWN_VENDOR_NAME       = "UnknownVendorName";
    public static final String ERROR_INTERNAL_ERROR            = "InternalError";
    public static final String ERROR_INVALID_FORMAT            = "InvalidFormat";
    public static final String ERROR_INVALID_OPTION            = "InvalidOption";
    public static final String ERROR_INVALID_LIMIT             = "InvalidLimit";
    
    public static final Map<FieldType, Class<? extends BaseFieldType>> fieldTypes;
    
    static {
        Map<FieldType, Class<? extends BaseFieldType>> map = new HashMap<>(44);
        
        map.put(FieldType.BOOLEAN, BooleanFieldType.class);
        map.put(FieldType.COMMAND_NAME, CommandNameFieldType.class);
        map.put(FieldType.COST_LINE, CostLineFieldType.class);
        map.put(FieldType.COST_UNIT, CostUnitFieldType.class);
        map.put(FieldType.CREDIT_CARD_MONTH, CreditCardMonthFieldType.class);
        map.put(FieldType.CREDIT_CARD_YEAR, CreditCardYearFieldType.class);
        map.put(FieldType.DATE, DateFieldType.class);
        map.put(FieldType.DATE_TIME, DateTimeFieldType.class);
        map.put(FieldType.EMAIL_ADDRESS, EmailAddressFieldType.class);
        map.put(FieldType.ENTITY_NAME, EntityNameFieldType.class);
        map.put(FieldType.ENTITY_NAME2, EntityName2FieldType.class);
        map.put(FieldType.ENTITY_NAMES, EntityNamesFieldType.class);
        map.put(FieldType.ENTITY_REF, EntityRefFieldType.class);
        map.put(FieldType.ENTITY_TYPE_NAME, EntityTypeNameFieldType.class);
        map.put(FieldType.FRACTIONAL_PERCENT, FractionalPercentFieldType.class);
        map.put(FieldType.UUID, UuidFieldType.class);
        map.put(FieldType.HARMONIZED_TARIFF_SCHEDULE_CODE, HarmonizedTariffScheduleCodeFieldType.class);
        map.put(FieldType.HOST_NAME, HostNameFieldType.class);
        map.put(FieldType.ID, IdFieldType.class);
        map.put(FieldType.INET_4_ADDRESS, Inet4AddressFieldType.class);
        map.put(FieldType.KEY, KeyFieldType.class);
        map.put(FieldType.LATITUDE, LatitudeFieldType.class);
        map.put(FieldType.LONGITUDE, LongitudeFieldType.class);
        map.put(FieldType.MIME_TYPE, MimeTypeFieldType.class);
        map.put(FieldType.NULL, NullFieldType.class);
        map.put(FieldType.NUMBER_3, Number3FieldType.class);
        map.put(FieldType.NUMBERS, NumbersFieldType.class);
        map.put(FieldType.PRICE_LINE, PriceLineFieldType.class);
        map.put(FieldType.PRICE_UNIT, PriceUnitFieldType.class);
        map.put(FieldType.REGULAR_EXPRESSION, RegularExpressionFieldType.class);
        map.put(FieldType.SEQUENCE_MASK, SequenceMaskFieldType.class);
        map.put(FieldType.SIGNED_INTEGER, SignedIntegerFieldType.class);
        map.put(FieldType.SIGNED_LONG, SignedLongFieldType.class);
        map.put(FieldType.STRING, StringFieldType.class);
        map.put(FieldType.TAG, TagFieldType.class);
        map.put(FieldType.TIME_ZONE_NAME, TimeZoneNameFieldType.class);
        map.put(FieldType.UNSIGNED_COST_LINE, UnsignedCostLineFieldType.class);
        map.put(FieldType.UNSIGNED_COST_UNIT, UnsignedCostUnitFieldType.class);
        map.put(FieldType.UNSIGNED_INTEGER, UnsignedIntegerFieldType.class);
        map.put(FieldType.UNSIGNED_LONG, UnsignedLongFieldType.class);
        map.put(FieldType.UNSIGNED_PRICE_LINE, UnsignedPriceLineFieldType.class);
        map.put(FieldType.UNSIGNED_PRICE_UNIT, UnsignedPriceUnitFieldType.class);
        map.put(FieldType.UPPER_LETTER_2, UpperLetter2FieldType.class);
        map.put(FieldType.UPPER_LETTER_3, UpperLetter3FieldType.class);
        map.put(FieldType.URL, UrlFieldType.class);
        map.put(FieldType.YEAR, YearFieldType.class);
        
        fieldTypes = Collections.unmodifiableMap(map);
    }
    
    /** Creates a new instance of Validator */
    public Validator(BaseCommand baseCommand) {
        this.baseCommand = baseCommand;
    }
    
    public BaseCommand getBaseCommand() {
        return baseCommand;
    }

    public AccountingControl getAccountingControl() {
        if(accountingControl == null)
            accountingControl = Session.getModelController(AccountingControl.class);
        return accountingControl;
    }
    
    public PartyControl getPartyControl() {
        if(partyControl == null)
            partyControl = Session.getModelController(PartyControl.class);
        return partyControl;
    }
    
    public UserControl getUserControl() {
        if(userControl == null)
            userControl = Session.getModelController(UserControl.class);
        return userControl;
    }
    
    public VendorControl getVendorControl() {
        if(vendorControl == null)
            vendorControl = Session.getModelController(VendorControl.class);
        return vendorControl;
    }
    
    protected Log getLog() {
        if(log == null) {
            log = LogFactory.getLog(this.getClass());
        }
        
        return log;
    }
    
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * Returns An ArrayList if there are errors found, otherwise null.
     * @return An ArrayList if there are errors found, otherwise null
     */
    public Messages validateField(BaseForm form, FieldDefinition fieldDefinition) {
        var validationMessages = new Messages();
        var splitFieldName = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(fieldDefinition.getFieldName()).toArray(new String[0]);
        var fieldName = splitFieldName[0];
        var originalFieldValue = form == null ? null : (String)form.get(fieldName);
        var fieldValue = originalFieldValue;
        
        // Clean the String.
        fieldValue = StringUtils.getInstance().trimToNull(fieldValue);
        
        if(fieldValue == null) {
            // If its null, and its a required field, then its an error.
            if(fieldDefinition.getIsRequired()) {
                validationMessages.add(fieldName, new Message(ERROR_REQUIRED_FIELD));
            }
        } else {
            var fieldType = fieldDefinition.getFieldType();
            var fieldValidator = fieldTypes.get(fieldType);
            
            // Not all fieldTypes have an additional validator class
            if(fieldValidator != null) {
                try {
                    var constructor = fieldValidator.getConstructor(new Class[]{Validator.class, BaseForm.class, Messages.class, String.class, String [].class, FieldDefinition.class});
                    var baseFieldType = (BaseFieldType)constructor.newInstance(new Object[]{this, form, validationMessages, fieldValue, splitFieldName, fieldDefinition});

                    fieldValue = baseFieldType.validate();
                } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    //e.printStackTrace();
                    //e.getCause().printStackTrace();
                    validationMessages.add(fieldName, new Message(ERROR_INTERNAL_ERROR));
                    fieldValue = null;
                    //System.err.println("Validator.validateField: fieldName = " + fieldName + ", Exception");
                }
            }
        }
        
        // Put back the value we've cleaned up.
        if(form != null) {
            form.set(fieldName, fieldValue);
        }
        
        if(!validationMessages.isEmpty()) {
            getLog().info("formName = " + form.getFormName() + ", fieldName = " + fieldName + ", originalFieldValue = \""
                    + originalFieldValue + "\", fieldValue = \"" + fieldValue + "\", errorList = " + validationMessages);
        }
        
        return validationMessages.isEmpty()? null: validationMessages;
    }
    
    private final static FieldDefinition preferredClobMimeTypeNameFieldDefinition = new FieldDefinition("PreferredClobMimeTypeName", FieldType.MIME_TYPE, false, null, null);
    
    public void validatePreferredClobMimeTypeName(Messages formValidationMessages, BaseForm form) {
        var validationMessages = validateField(form, preferredClobMimeTypeNameFieldDefinition);

        if(validationMessages != null) {
            formValidationMessages.add(validationMessages);
        }
    }
    
    public void validateOptions(Messages formValidationMessages, BaseForm form) {
        var options = form.getOptions();

        if(options != null) {
            options.forEach((option) -> {
                var m = Patterns.Option.matcher(option);
                if (!m.matches()) {
                    formValidationMessages.add(option, new Message(Validator.ERROR_INVALID_OPTION));
                }
            });
        }
    }
    
    public static String validateLong(String fieldValue) {
        try {
            var testLong = Long.valueOf(fieldValue);
            
            fieldValue = testLong.toString();
        } catch (NumberFormatException nfe) {
            if(fieldValue.equalsIgnoreCase("MAX_VALUE")) {
                fieldValue = Long.toString(Long.MAX_VALUE);
            } else {
                fieldValue = null;
            }
        }
        
        return fieldValue;
    }
    
    public static String validateUnsignedLong(String unsignedLong) {
        if(unsignedLong != null) {
            var m = Patterns.UnsignedNumbers.matcher(unsignedLong);

            if(m.matches()) {
                unsignedLong = validateLong(unsignedLong);
            } else {
                unsignedLong = null;
            }
        }
        
        return unsignedLong;
    }
    
    public void validateLimits(Messages formValidationMessages, BaseForm form) {
        var limits = form.getLimits();

        if(limits != null) {
            limits.keySet().forEach((tableNameSingular) -> {
                var validLimit = true;
                var m = Patterns.TableNameSingular.matcher(tableNameSingular);
                if(m.matches()) {
                    var limit = limits.get(tableNameSingular);

                    if(limit != null) {
                        var count = limit.getCount();
                        var newCount = count == null ? null : validateUnsignedLong(count);

                        if(count == null || newCount != null) {
                            limit.setCount(newCount);
                        } else {
                            validLimit = false;
                        }

                        if(validLimit) {
                            var offset = limit.getOffset();
                            var newOffset = offset == null ? null : validateUnsignedLong(offset);

                            if(offset == null || newOffset != null) {
                                limit.setOffset(newOffset);
                            } else {
                                validLimit = false;
                            }
                        }
                    }
                } else {
                    validLimit = false;
                }
                if (!validLimit) {
                    formValidationMessages.add(tableNameSingular, new Message(Validator.ERROR_INVALID_LIMIT));
                }
            });
        }
    }
    
    public ValidationResult validate(BaseForm form, List<FieldDefinition> fieldDefinitions) {
        var formValidationMessages = new Messages();
        
        fieldDefinitions.stream().map((fieldDefinition) ->
                validateField(form, fieldDefinition)).filter(Objects::nonNull).forEach(formValidationMessages::add);
        
        if(form != null) {
            validatePreferredClobMimeTypeName(formValidationMessages, form);
            validateOptions(formValidationMessages, form);
            validateLimits(formValidationMessages, form);
        }

        var hasErrors = !formValidationMessages.isEmpty();

        return new ValidationResult(hasErrors ? formValidationMessages : null);
    }

}
