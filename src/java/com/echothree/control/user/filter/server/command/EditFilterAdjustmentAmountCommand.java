// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.filter.common.edit.FilterAdjustmentAmountEdit;
import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.form.EditFilterAdjustmentAmountForm;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentAmountResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterAdjustmentAmountSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.filter.common.FilterAdjustmentTypes;
import com.echothree.model.control.filter.common.FilterKinds;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentAmount;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.value.FilterAdjustmentAmountValue;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
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

public class EditFilterAdjustmentAmountCommand
        extends BaseEditCommand<FilterAdjustmentAmountSpec, FilterAdjustmentAmountEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> costEditFieldDefinitions;
    private final static List<FieldDefinition> priceEditFieldDefinitions;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterAdjustment.name(), SecurityRoles.FilterAdjustmentAmount.name())
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
                new FieldDefinition("Amount:CurrencyIsoName,CurrencyIsoName", FieldType.COST_UNIT, true, null, null)
        );
        
        priceEditFieldDefinitions = List.of(
                new FieldDefinition("Amount:CurrencyIsoName,CurrencyIsoName", FieldType.PRICE_UNIT, true, null, null)
        );
    }
    
    /** Creates a new instance of EditFilterAdjustmentAmountCommand */
    public EditFilterAdjustmentAmountCommand(UserVisitPK userVisitPK, EditFilterAdjustmentAmountForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, null);
    }
    
    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        String filterKindName = spec.getFilterKindName();
        String currencyIsoName = spec.getCurrencyIsoName();
        
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
        EditFilterAdjustmentAmountResult result = FilterResultFactory.getEditFilterAdjustmentAmountResult();
        String filterKindName = spec.getFilterKindName();
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            String filterAdjustmentName = spec.getFilterAdjustmentName();
            FilterAdjustment filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);
            
            if(filterAdjustment != null) {
                String filterAdjustmentTypeName = filterAdjustment.getLastDetail().getFilterAdjustmentType().getFilterAdjustmentTypeName();
                
                if(filterAdjustmentTypeName.equals(FilterAdjustmentTypes.AMOUNT.name())) {
                    var uomControl = Session.getModelController(UomControl.class);
                    String unitOfMeasureName = spec.getUnitOfMeasureName();
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
                        UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
                        
                        if(unitOfMeasureKind != null) {
                            UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind,
                                    unitOfMeasureTypeName);
                            
                            if(unitOfMeasureType != null) {
                                var accountingControl = Session.getModelController(AccountingControl.class);
                                String currencyIsoName = spec.getCurrencyIsoName();
                                Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
                                
                                if(currency != null) {
                                    if(editMode.equals(EditMode.LOCK)) {
                                        FilterAdjustmentAmount filterAdjustmentAmount = filterControl.getFilterAdjustmentAmount(filterAdjustment,
                                                unitOfMeasureType, currency);
                                        
                                        if(filterAdjustmentAmount != null) {
                                            result.setFilterAdjustmentAmount(filterControl.getFilterAdjustmentAmountTransfer(getUserVisit(), filterAdjustmentAmount));
                                            
                                            if(lockEntity(filterAdjustmentAmount)) {
                                                edit = FilterEditFactory.getFilterAdjustmentAmountEdit();
                                                result.setEdit(edit);
                                                
                                                if(filterKindName.equals(FilterKinds.COST.name())) {
                                                    edit.setAmount(AmountUtils.getInstance().formatCostUnit(currency, filterAdjustmentAmount.getAmount()));
                                                } else if(filterKindName.equals(FilterKinds.PRICE.name())) {
                                                    edit.setAmount(AmountUtils.getInstance().formatPriceUnit(currency, filterAdjustmentAmount.getAmount()));
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                            }
                                            
                                            result.setEntityLock(getEntityLockTransfer(filterAdjustmentAmount));
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownFilterAdjustmentAmount.name());
                                        }
                                    } else if(editMode.equals(EditMode.UPDATE)) {
                                        FilterAdjustmentAmount filterAdjustmentAmount = filterControl.getFilterAdjustmentAmountForUpdate(filterAdjustment,
                                                unitOfMeasureType, currency);
                                        
                                        if(filterAdjustmentAmount != null) {
                                            if(lockEntityForUpdate(filterAdjustmentAmount)) {
                                                try {
                                                    var partyPK = getPartyPK();
                                                    FilterAdjustmentAmountValue filterAdjustmentAmountValue = filterControl.getFilterAdjustmentAmountValue(filterAdjustmentAmount);
                                                    
                                                    filterAdjustmentAmountValue.setAmount(Long.valueOf(edit.getAmount()));
                                                    
                                                    filterControl.updateFilterAdjustmentAmountFromValue(filterAdjustmentAmountValue, partyPK);
                                                } finally {
                                                    unlockEntity(filterAdjustmentAmount);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownFilterAdjustmentAmount.name());
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
