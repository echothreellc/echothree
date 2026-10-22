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

import com.echothree.control.user.period.common.form.GetPeriodDescriptionsForm;
import com.echothree.control.user.period.common.result.PeriodResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.period.server.logic.PeriodKindLogic;
import com.echothree.model.control.period.server.logic.PeriodLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.period.server.entity.PeriodDescription;
import com.echothree.model.data.period.server.factory.PeriodDescriptionFactory;
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
public class GetPeriodDescriptionsCommand
        extends BasePaginatedMultipleEntitiesCommand<PeriodDescription, GetPeriodDescriptionsForm> {

    private static final CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private static final List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Period.name(), SecurityRoles.Description.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PeriodKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PeriodName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    PeriodControl periodControl;

    @Inject
    PeriodKindLogic periodKindLogic;

    @Inject
    PeriodLogic periodLogic;

    public GetPeriodDescriptionsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private Period period;

    @Override
    protected void handleForm() {
        var periodKind = periodKindLogic.getPeriodKindByName(this, form.getPeriodKindName());

        if(!hasExecutionErrors()) {
            period = periodLogic.getPeriodByName(this, periodKind, form.getPeriodName());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : periodControl.countPeriodDescriptionsByPeriod(period);
    }

    @Override
    protected Collection<PeriodDescription> getEntities() {
        return hasExecutionErrors() ? null : periodControl.getPeriodDescriptionsByPeriod(period);
    }

    @Override
    protected BaseResult getResult(Collection<PeriodDescription> periodDescriptions) {
        var result = PeriodResultFactory.getGetPeriodDescriptionsResult();

        if(periodDescriptions != null) {
            var userVisit = getUserVisit();

            result.setPeriod(periodControl.getPeriodTransfer(userVisit, period));

            if(session.hasLimit(PeriodDescriptionFactory.class)) {
                result.setPeriodDescriptionCount(getTotalEntities());
            }

            result.setPeriodDescriptions(periodControl.getPeriodDescriptionTransfers(userVisit, periodDescriptions));
        }

        return result;
    }

}
