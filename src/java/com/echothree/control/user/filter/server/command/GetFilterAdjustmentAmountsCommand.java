// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.filter.common.form.GetFilterAdjustmentAmountsForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.result.GetFilterAdjustmentAmountsResult;
import com.echothree.model.control.filter.common.FilterConstants;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.filter.server.logic.FilterKindLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentAmount;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.factory.FilterAdjustmentFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;

public class GetFilterAdjustmentAmountsCommand
        extends BaseMultipleEntitiesCommand<FilterAdjustmentAmount, GetFilterAdjustmentAmountsForm> {

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
                new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetFilterAdjustmentAmountsCommand */
    public GetFilterAdjustmentAmountsCommand(UserVisitPK userVisitPK, GetFilterAdjustmentAmountsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    FilterKind filterKind;
    FilterAdjustment filterAdjustment;

    @Override
    protected Collection<FilterAdjustmentAmount> getEntities() {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKindName = form.getFilterKindName();

        filterKind = filterControl.getFilterKindByName(filterKindName);

        if(filterKind != null) {
            var filterAdjustmentName = form.getFilterAdjustmentName();

            filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);

            if(filterAdjustment != null) {
                if(!filterAdjustment.getLastDetail().getFilterAdjustmentType().getFilterAdjustmentTypeName().equals(FilterConstants.FilterAdjustmentType_AMOUNT)) {
                    addExecutionError(ExecutionErrors.InvalidFilterAdjustmentType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFilterAdjustmentName.name(), filterAdjustmentName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }

        return hasExecutionErrors() ? null : filterControl.getFilterAdjustmentAmounts(filterAdjustment);
    }

    @Override
    protected BaseResult getTransfers(Collection<FilterAdjustmentAmount> entities) {
        var result = FilterResultFactory.getGetFilterAdjustmentAmountsResult();

        if(entities != null) {
            var filterControl = Session.getModelController(FilterControl.class);

            result.setFilterKind(filterControl.getFilterKindTransfer(getUserVisit(), filterKind));
            result.setFilterAdjustment(filterControl.getFilterAdjustmentTransfer(getUserVisit(), filterAdjustment));
            result.setFilterAdjustmentAmounts(filterControl.getFilterAdjustmentAmountTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
