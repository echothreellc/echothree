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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.common.form.GetPartySkillsForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.data.employee.server.entity.PartySkill;
import com.echothree.model.data.employee.server.entity.SkillType;
import com.echothree.model.data.employee.server.factory.PartySkillFactory;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartySkillsCommand
        extends BasePaginatedMultipleEntitiesCommand<PartySkill, GetPartySkillsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SkillTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    EmployeeControl employeeControl;

    @Inject
    PartyControl partyControl;

    @Inject
    PartyLogic partyLogic;

    /** Creates a new instance of GetPartySkillsCommand */
    public GetPartySkillsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    private Party party;
    private SkillType skillType;

    @Override
    protected void handleForm() {
        var partyName = form.getPartyName();
        var skillTypeName = form.getSkillTypeName();
        var parameterCount = (partyName == null ? 0 : 1) + (skillTypeName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(partyName != null) {
                party = partyLogic.getPartyByName(this, partyName, PartyTypes.EMPLOYEE.name());
            } else {
                skillType = employeeControl.getSkillTypeByName(skillTypeName);

                if(skillType == null) {
                    addExecutionError(ExecutionErrors.UnknownSkillTypeName.name(), skillTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(party != null) {
                total = employeeControl.countPartySkillsByParty(party);
            } else {
                total = employeeControl.countPartySkillsBySkillType(skillType);
            }
        }

        return total;
    }

    @Override
    protected Collection<PartySkill> getEntities() {
        Collection<PartySkill> entities = null;

        if(!hasExecutionErrors()) {
            if(party != null) {
                entities = employeeControl.getPartySkillsByParty(party);
            } else {
                entities = employeeControl.getPartySkillsBySkillType(skillType);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<PartySkill> entities) {
        var result = EmployeeResultFactory.getGetPartySkillsResult();

        if(entities != null) {
            if(party != null) {
                result.setParty(partyControl.getPartyTransfer(getUserVisit(), party));
            } else {
                result.setSkillType(employeeControl.getSkillTypeTransfer(getUserVisit(), skillType));
            }

            if(session.hasLimit(PartySkillFactory.class)) {
                result.setPartySkillCount(getTotalEntities());
            }

            result.setPartySkills(employeeControl.getPartySkillTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
