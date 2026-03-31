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

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.edit.FilterAdjustmentPercentEdit;
import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentPercentResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterAdjustmentPercentSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.filter.common.FilterAdjustmentTypes;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentPercent;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.string.PercentUtils;
import com.echothree.util.server.validation.Validator;
import com.google.common.base.Splitter;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditFilterAdjustmentPercentCommand
        extends BaseAbstractEditCommand<FilterAdjustmentPercentSpec, FilterAdjustmentPercentEdit, EditFilterAdjustmentPercentResult, FilterAdjustmentPercent, FilterAdjustment> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterAdjustment.name(), SecurityRoles.FilterAdjustmentPercent.name())
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
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Percent", FieldType.FRACTIONAL_PERCENT, true, null, null)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    FilterControl filterControl;

    @Inject
    UomControl uomControl;

    /** Creates a new instance of EditFilterAdjustmentPercentCommand */
    public EditFilterAdjustmentPercentCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        var currencyIsoName = spec.getCurrencyIsoName();

        validator.setCurrency(accountingControl.getCurrencyByIsoName(currencyIsoName));
    }

    @Override
    public EditFilterAdjustmentPercentResult getResult() {
        return FilterResultFactory.getEditFilterAdjustmentPercentResult();
    }

    @Override
    public FilterAdjustmentPercentEdit getEdit() {
        return FilterEditFactory.getFilterAdjustmentPercentEdit();
    }

    @Override
    public FilterAdjustmentPercent getEntity(EditFilterAdjustmentPercentResult result) {
        FilterAdjustmentPercent filterAdjustmentPercent = null;
        var filterKindName = spec.getFilterKindName();
        var filterKind = filterControl.getFilterKindByName(filterKindName);

        if(filterKind != null) {
            var filterAdjustmentName = spec.getFilterAdjustmentName();
            var filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);

            if(filterAdjustment != null) {
                var filterAdjustmentType = filterAdjustment.getLastDetail().getFilterAdjustmentType();

                if(filterAdjustmentType != null && filterAdjustmentType.getFilterAdjustmentTypeName().equals(FilterAdjustmentTypes.PERCENT.name())) {
                    var unitOfMeasureName = spec.getUnitOfMeasureName();
                    String unitOfMeasureKindName = null;
                    String unitOfMeasureTypeName = null;

                    if(unitOfMeasureName == null) {
                        unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
                        unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
                    } else {
                        var splitUomName = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(unitOfMeasureName).toArray(new String[0]);

                        if(splitUomName.length == 2) {
                            unitOfMeasureKindName = splitUomName[0];
                            unitOfMeasureTypeName = splitUomName[1];
                        }
                    }

                    if(unitOfMeasureKindName != null && unitOfMeasureTypeName != null) {
                        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

                        if(unitOfMeasureKind != null) {
                            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

                            if(unitOfMeasureType != null) {
                                var currencyIsoName = spec.getCurrencyIsoName();
                                var currency = accountingControl.getCurrencyByIsoName(currencyIsoName);

                                if(currency != null) {
                                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                        filterAdjustmentPercent = filterControl.getFilterAdjustmentPercent(filterAdjustment, unitOfMeasureType, currency);
                                    } else { // EditMode.UPDATE
                                        filterAdjustmentPercent = filterControl.getFilterAdjustmentPercentForUpdate(filterAdjustment, unitOfMeasureType, currency);
                                    }

                                    if(filterAdjustmentPercent == null) {
                                        addExecutionError(ExecutionErrors.UnknownFilterAdjustmentPercent.name());
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
                    addExecutionError(ExecutionErrors.InvalidFilterAdjustmentType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFilterAdjustmentName.name(), filterAdjustmentName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }

        return filterAdjustmentPercent;
    }

    @Override
    public FilterAdjustment getLockEntity(FilterAdjustmentPercent filterAdjustmentPercent) {
        return filterAdjustmentPercent.getFilterAdjustment();
    }

    @Override
    public void fillInResult(EditFilterAdjustmentPercentResult result, FilterAdjustmentPercent filterAdjustmentPercent) {
        result.setFilterAdjustmentPercent(filterControl.getFilterAdjustmentPercentTransfer(getUserVisit(), filterAdjustmentPercent));
    }

    @Override
    public void doLock(FilterAdjustmentPercentEdit edit, FilterAdjustmentPercent filterAdjustmentPercent) {
        edit.setPercent(PercentUtils.getInstance().formatFractionalPercent(filterAdjustmentPercent.getPercent()));
    }

    @Override
    public void doUpdate(FilterAdjustmentPercent filterAdjustmentPercent) {
        var filterAdjustmentPercentValue = filterControl.getFilterAdjustmentPercentValue(filterAdjustmentPercent);

        filterAdjustmentPercentValue.setPercent(Integer.valueOf(edit.getPercent()));

        filterControl.updateFilterAdjustmentPercentFromValue(filterAdjustmentPercentValue, getPartyPK());
    }

}
