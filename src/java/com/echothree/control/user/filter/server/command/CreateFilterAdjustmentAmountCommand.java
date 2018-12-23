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

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.form.CreateFilterAdjustmentAmountForm;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.filter.common.FilterConstants;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentAmount;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateFilterAdjustmentAmountCommand
        extends BaseSimpleCommand<CreateFilterAdjustmentAmountForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> costFormFieldDefinitions;
    private final static List<FieldDefinition> priceFormFieldDefinitions;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("UnitOfMeasureName", FieldType.ENTITY_NAME2, Boolean.FALSE, null, null),
            new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        costFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Amount:CurrencyIsoName,CurrencyIsoName", FieldType.COST_UNIT, true, null, null)
        ));
        
        priceFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Amount:CurrencyIsoName,CurrencyIsoName", FieldType.PRICE_UNIT, true, null, null)
        ));
    }
    
    /** Creates a new instance of CreateFilterAdjustmentAmountCommand */
    public CreateFilterAdjustmentAmountCommand(UserVisitPK userVisitPK, CreateFilterAdjustmentAmountForm form) {
        super(userVisitPK, form, null, null, false);
    }
    
    @Override
    protected ValidationResult validate() {
        Validator validator = new Validator(this);
        ValidationResult validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);
        
        if(!validationResult.getHasErrors()) {
            String filterKindName = form.getFilterKindName();
            
            if(filterKindName.equals(FilterConstants.FilterKind_COST)) {
                validationResult = validator.validate(form, costFormFieldDefinitions);
            } else if(filterKindName.equals(FilterConstants.FilterKind_PRICE)) {
                validationResult = validator.validate(form, priceFormFieldDefinitions);
            } else {
                addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
            }
        }
        
        return validationResult;
    }
    
    @Override
    protected BaseResult execute() {
        FilterControl filterControl = (FilterControl)Session.getModelController(FilterControl.class);
        String filterKindName = form.getFilterKindName();
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            String filterAdjustmentName = form.getFilterAdjustmentName();
            FilterAdjustment filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);
            
            if(filterAdjustment != null) {
                String filterAdjustmentTypeName = filterAdjustment.getLastDetail().getFilterAdjustmentType().getFilterAdjustmentTypeName();
                
                if(filterAdjustmentTypeName.equals(FilterConstants.FilterAdjustmentType_AMOUNT)) {
                    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
                    String unitOfMeasureName = form.getUnitOfMeasureName();
                    String unitOfMeasureKindName = null;
                    String unitOfMeasureTypeName = null;
                    
                    if(unitOfMeasureName == null) {
                        unitOfMeasureKindName = form.getUnitOfMeasureKindName();
                        unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                    } else {
                        String splitUomName[] = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(unitOfMeasureName).toArray(new String[0]);
                        
                        if(splitUomName.length == 2) {
                            unitOfMeasureKindName = splitUomName[0];
                            unitOfMeasureTypeName = splitUomName[1];
                        }
                    }
                    
                    if(unitOfMeasureKindName != null && unitOfMeasureTypeName != null) {
                        UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
                        
                        if(unitOfMeasureKind != null) {
                            UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind,
                                    unitOfMeasureTypeName);
                            
                            if(unitOfMeasureType != null) {
                                AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                                String currencyIsoName = form.getCurrencyIsoName();
                                Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
                                
                                if(currency != null) {
                                    FilterAdjustmentAmount filterAdjustmentAmount = filterControl.getFilterAdjustmentAmount(filterAdjustment,
                                            unitOfMeasureType, currency);
                                    
                                    if(filterAdjustmentAmount == null) {
                                        Long amount = Long.valueOf(form.getAmount());
                                        
                                        filterControl.createFilterAdjustmentAmount(filterAdjustment, unitOfMeasureType, currency,
                                                amount, getPartyPK());
                                    } else {
                                        addExecutionError(ExecutionErrors.DuplicateFilterAdjustmentAmount.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidUnitOfMeasureSpecification.name(), unitOfMeasureName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidFilterAdjustmentType.name(), filterAdjustmentTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFilterAdjustmentName.name(), filterAdjustmentName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }
        
        return null;
    }
    
}
