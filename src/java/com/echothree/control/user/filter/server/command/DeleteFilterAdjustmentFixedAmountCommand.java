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

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.form.DeleteFilterAdjustmentFixedAmountForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.filter.common.FilterConstants;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentFixedAmount;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteFilterAdjustmentFixedAmountCommand
        extends BaseSimpleCommand<DeleteFilterAdjustmentFixedAmountForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureName", FieldType.ENTITY_NAME2, false, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteFilterAdjustmentFixedAmountCommand */
    public DeleteFilterAdjustmentFixedAmountCommand(UserVisitPK userVisitPK, DeleteFilterAdjustmentFixedAmountForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = Session.getModelController(FilterControl.class);
        String filterKindName = form.getFilterKindName();
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            String filterAdjustmentName = form.getFilterAdjustmentName();
            FilterAdjustment filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);
            
            if(filterAdjustment != null) {
                if(filterAdjustment.getLastDetail().getFilterAdjustmentType().getFilterAdjustmentTypeName().equals(FilterConstants.FilterAdjustmentType_FIXED_AMOUNT)) {
                    var uomControl = Session.getModelController(UomControl.class);
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
                            UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                            
                            if(unitOfMeasureType != null) {
                                var accountingControl = Session.getModelController(AccountingControl.class);
                                String currencyIsoName = form.getCurrencyIsoName();
                                Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
                                
                                if(currency != null) {
                                    FilterAdjustmentFixedAmount filterAdjustmentFixedAmount = filterControl.getFilterAdjustmentFixedAmountForUpdate(filterAdjustment, unitOfMeasureType,
                                    currency);
                                    
                                    if(filterAdjustmentFixedAmount != null) {
                                        filterControl.deleteFilterAdjustmentFixedAmount(filterAdjustmentFixedAmount, getPartyPK());
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownFilterAdjustmentFixedAmount.name());
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
                        addExecutionError(ExecutionErrors.InvalidUnitOfMeasureSpecification.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidFilterAdjustmentType.name());
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
