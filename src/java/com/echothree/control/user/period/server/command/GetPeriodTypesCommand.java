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

import com.echothree.control.user.period.common.form.GetPeriodTypesForm;
import com.echothree.control.user.period.common.result.PeriodResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.period.server.logic.PeriodKindLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.model.data.period.server.factory.PeriodTypeFactory;
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
public class GetPeriodTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<PeriodType, GetPeriodTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PeriodType.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PeriodKindName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    PeriodControl periodControl;

    @Inject
    PeriodKindLogic periodKindLogic;

    /** Creates a new instance of GetPeriodTypesCommand */
    public GetPeriodTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    PeriodKind periodKind;

    @Override
    protected void handleForm() {
        periodKind = periodKindLogic.getPeriodKindByName(this, form.getPeriodKindName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : periodControl.countPeriodTypes(periodKind);
    }

    @Override
    protected Collection<PeriodType> getEntities() {
        return hasExecutionErrors() ? null : periodControl.getPeriodTypes(periodKind);
    }

    @Override
    protected BaseResult getResult(Collection<PeriodType> entities) {
        var result = PeriodResultFactory.getGetPeriodTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setPeriodKind(periodControl.getPeriodKindTransfer(userVisit, periodKind));

            if(session.hasLimit(PeriodTypeFactory.class)) {
                result.setPeriodTypeCount(getTotalEntities());
            }

            result.setPeriodTypes(periodControl.getPeriodTypeTransfersByPeriodKind(userVisit, periodKind));
        }

        return result;
    }
    
}
