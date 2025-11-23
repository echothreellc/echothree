// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.filter.common.edit.FilterAdjustmentFixedAmountEdit;
import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.form.EditFilterAdjustmentFixedAmountForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterAdjustmentFixedAmountSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.filter.common.FilterAdjustmentTypes;
import com.echothree.model.control.filter.common.FilterKinds;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.validation.Validator;
import com.google.common.base.Splitter;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditFilterAdjustmentFixedAmountCommand
        extends BaseEditCommand<FilterAdjustmentFixedAmountSpec, FilterAdjustmentFixedAmountEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> costEditFieldDefinitions;
    private final static List<FieldDefinition> priceEditFieldDefinitions;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterAdjustment.name(), SecurityRoles.FilterAdjustmentFixedAmount.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureName", FieldType.ENTITY_NAME2, false, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        costEditFieldDefinitions = List.of(
                new FieldDefinition("UnitAmount:CurrencyIsoName,CurrencyIsoName", FieldType.COST_UNIT, true, null, null)
        );
        
        priceEditFieldDefinitions = List.of(
                new FieldDefinition("UnitAmount:CurrencyIsoName,CurrencyIsoName", FieldType.PRICE_UNIT, true, null, null)
        );
    }
    
    /** Creates a new instance of EditFilterAdjustmentFixedAmountCommand */
    public EditFilterAdjustmentFixedAmountCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, null);
    }
    
    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var filterKindName = spec.getFilterKindName();
        var currencyIsoName = spec.getCurrencyIsoName();
        
        validator.setCurrency(accountingControl.getCurrencyByIsoName(currencyIsoName));
        
        if(filterKindName.equals(FilterKinds.COST.name())) {
            setEditFieldDefinitions(costEditFieldDefinitions);
        } else if(filterKindName.equals(FilterKinds.PRICE.name())) {
            setEditFieldDefinitions(priceEditFieldDefinitions);
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = Session.getModelController(FilterControl.class);
        var result = FilterResultFactory.getEditFilterAdjustmentFixedAmountResult();
        var filterKindName = spec.getFilterKindName();
        var filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            var filterAdjustmentName = spec.getFilterAdjustmentName();
            var filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);
            
            if(filterAdjustment != null) {
                var filterAdjustmentTypeName = filterAdjustment.getLastDetail().getFilterAdjustmentType().getFilterAdjustmentTypeName();
                
                if(filterAdjustmentTypeName.equals(FilterAdjustmentTypes.FIXED_AMOUNT.name())) {
                    var uomControl = Session.getModelController(UomControl.class);
                    var unitOfMeasureName = spec.getUnitOfMeasureName();
                    String unitOfMeasureKindName = null;
                    String unitOfMeasureTypeName = null;
                    
                    if(unitOfMeasureName == null) {
                        unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
                        unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
                    } else {
                        String splitUomName[] = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(unitOfMeasureName).toArray(new String[0]);
                        
                        if(splitUomName.length == 2) {
                            unitOfMeasureKindName = splitUomName[0];
                            unitOfMeasureTypeName = splitUomName[1];
                        }
                    }
                    
                    if(unitOfMeasureKindName != null && unitOfMeasureTypeName != null) {
                        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
                        
                        if(unitOfMeasureKind != null) {
                            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind,
                                    unitOfMeasureTypeName);
                            
                            if(unitOfMeasureType != null) {
                                var accountingControl = Session.getModelController(AccountingControl.class);
                                var currencyIsoName = spec.getCurrencyIsoName();
                                var currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
                                
                                if(currency != null) {
                                    if(editMode.equals(EditMode.LOCK)) {
                                        var filterAdjustmentFixedAmount = filterControl.getFilterAdjustmentFixedAmount(filterAdjustment,
                                                unitOfMeasureType, currency);
                                        
                                        if(filterAdjustmentFixedAmount != null) {
                                            result.setFilterAdjustmentFixedAmount(filterControl.getFilterAdjustmentFixedAmountTransfer(getUserVisit(), filterAdjustmentFixedAmount));
                                            
                                            if(lockEntity(filterAdjustmentFixedAmount)) {
                                                edit = FilterEditFactory.getFilterAdjustmentFixedAmountEdit();
                                                result.setEdit(edit);
                                                
                                                if(filterKindName.equals(FilterKinds.COST.name())) {
                                                    edit.setUnitAmount(AmountUtils.getInstance().formatCostUnit(currency, filterAdjustmentFixedAmount.getUnitAmount()));
                                                } else if(filterKindName.equals(FilterKinds.PRICE.name())) {
                                                    edit.setUnitAmount(AmountUtils.getInstance().formatPriceUnit(currency, filterAdjustmentFixedAmount.getUnitAmount()));
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                            }
                                            
                                            result.setEntityLock(getEntityLockTransfer(filterAdjustmentFixedAmount));
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownFilterAdjustmentFixedAmount.name());
                                        }
                                    } else if(editMode.equals(EditMode.UPDATE)) {
                                        var filterAdjustmentFixedAmount = filterControl.getFilterAdjustmentFixedAmountForUpdate(filterAdjustment,
                                                unitOfMeasureType, currency);
                                        
                                        if(filterAdjustmentFixedAmount != null) {
                                            if(lockEntityForUpdate(filterAdjustmentFixedAmount)) {
                                                try {
                                                    var partyPK = getPartyPK();
                                                    var filterAdjustmentFixedAmountValue = filterControl.getFilterAdjustmentFixedAmountValue(filterAdjustmentFixedAmount);
                                                    
                                                    filterAdjustmentFixedAmountValue.setUnitAmount(Long.valueOf(edit.getUnitAmount()));
                                                    
                                                    filterControl.updateFilterAdjustmentFixedAmountFromValue(filterAdjustmentFixedAmountValue, partyPK);
                                                } finally {
                                                    unlockEntity(filterAdjustmentFixedAmount);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownFilterAdjustmentFixedAmount.name());
                                        }
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
        
        return result;
    }
    
}
