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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.GetMoodsForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Mood;
import com.echothree.model.data.party.server.factory.MoodFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetMoodsCommand
        extends BasePaginatedMultipleEntitiesCommand<Mood, GetMoodsForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    @Inject
    PartyControl partyControl;

    /** Creates a new instance of GetMoodsCommand */
    public GetMoodsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return partyControl.countMoods();
    }

    @Override
    protected Collection<Mood> getEntities() {
        return partyControl.getMoods();
    }

    @Override
    protected BaseResult getResult(Collection<Mood> entities) {
        var result = PartyResultFactory.getGetMoodsResult();

        if(entities != null) {
            if(session.hasLimit(MoodFactory.class)) {
                result.setMoodCount(getTotalEntities());
            }

            result.setMoods(partyControl.getMoodTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
