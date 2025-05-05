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

import com.echothree.control.user.filter.common.form.GetFilterAdjustmentAmountForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.filter.common.FilterAdjustmentTypes;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.filter.server.logic.FilterAdjustmentLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentAmount;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetFilterAdjustmentAmountCommand
        extends BaseSingleEntityCommand<FilterAdjustmentAmount, GetFilterAdjustmentAmountForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterAdjustment.name(), SecurityRoles.FilterAdjustmentAmount.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureName", FieldType.ENTITY_NAME2, false, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    /** Creates a new instance of DeleteFilterAdjustmentAmountCommand */
    public GetFilterAdjustmentAmountCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected FilterAdjustmentAmount getEntity() {
        var filterKindName = form.getFilterKindName();
        var filterAdjustmentName = form.getFilterAdjustmentName();
        var filterAdjustment = FilterAdjustmentLogic.getInstance().getFilterAdjustmentByName(this, filterKindName, filterAdjustmentName);
        FilterAdjustmentAmount filterAdjustmentAmount = null;

        if(!hasExecutionErrors()) {
            if(filterAdjustment.getLastDetail().getFilterAdjustmentType().getFilterAdjustmentTypeName().equals(FilterAdjustmentTypes.AMOUNT.name())) {
                var unitOfMeasureName = form.getUnitOfMeasureName();
                var unitOfMeasureKindName = form.getUnitOfMeasureKindName();
                var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                var unitOfMeasureType = UnitOfMeasureTypeLogic.getInstance().getUnitOfMeasureTypeByName(this,
                        unitOfMeasureName, unitOfMeasureKindName, unitOfMeasureTypeName);

                if(!hasExecutionErrors()) {
                    var currencyIsoName = form.getCurrencyIsoName();
                    var currency = CurrencyLogic.getInstance().getCurrencyByName(this, currencyIsoName);

                    if(!hasExecutionErrors()) {
                        var filterControl = Session.getModelController(FilterControl.class);

                        filterAdjustmentAmount = filterControl.getFilterAdjustmentAmount(filterAdjustment, unitOfMeasureType, currency);

                        if(filterAdjustmentAmount == null) {
                            addExecutionError(ExecutionErrors.UnknownFilterAdjustmentAmount.name());
                        }
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidFilterAdjustmentType.name());
            }
        }

        return filterAdjustmentAmount;
    }

    @Override
    protected BaseResult getResult(FilterAdjustmentAmount entity) {
        var result = FilterResultFactory.getGetFilterAdjustmentAmountResult();

        if(entity != null) {
            var filterControl = Session.getModelController(FilterControl.class);

            result.setFilterAdjustmentAmount(filterControl.getFilterAdjustmentAmountTransfer(getUserVisit(), entity));
        }

        return result;
    }

}
