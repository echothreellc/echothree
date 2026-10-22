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

package com.echothree.control.user.period.server.command;

import com.echothree.control.user.period.common.form.GetFiscalPeriodsForm;
import com.echothree.control.user.period.common.result.PeriodResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.period.server.logic.FiscalPeriodLogic;
import com.echothree.model.control.period.server.logic.PeriodLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.period.server.factory.PeriodFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetFiscalPeriodsCommand
        extends BasePaginatedMultipleEntitiesCommand<Period, GetFiscalPeriodsForm> {

    private static final CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private static final List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.FiscalPeriod.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ParentPeriodName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PeriodTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    PeriodControl periodControl;

    @Inject
    FiscalPeriodLogic fiscalPeriodLogic;

    @Inject
    PeriodLogic periodLogic;

    public GetFiscalPeriodsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private PeriodControl.PeriodSelection periodSelection;

    @Override
    protected void handleForm() {
        var periodKind = fiscalPeriodLogic.getFiscalPeriodKind(this);

        if(!hasExecutionErrors()) {
            periodSelection = periodLogic.getPeriodSelection(this, periodKind, form.getParentPeriodName(), form.getPeriodTypeName());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : periodControl.countPeriods(periodSelection);
    }

    @Override
    protected Collection<Period> getEntities() {
        return hasExecutionErrors() ? null : periodControl.getPeriods(periodSelection);
    }

    @Override
    protected BaseResult getResult(Collection<Period> entities) {
        var result = PeriodResultFactory.getGetFiscalPeriodsResult();

        if(entities != null) {
            if(session.hasLimit(PeriodFactory.class)) {
                result.setFiscalPeriodCount(getTotalEntities());
            }

            result.setFiscalPeriods(periodControl.getFiscalPeriodTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
