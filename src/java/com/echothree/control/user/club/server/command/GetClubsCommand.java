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

package com.echothree.control.user.club.server.command;

import com.echothree.control.user.club.common.form.GetClubsForm;
import com.echothree.control.user.club.common.result.ClubResultFactory;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.model.data.club.server.factory.ClubFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetClubsCommand
        extends BasePaginatedMultipleEntitiesCommand<Club, GetClubsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    @Inject
    ClubControl clubControl;

    /** Creates a new instance of GetClubsCommand */
    public GetClubsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields to handle
    }

    @Override
    protected Long getTotalEntities() {
        return clubControl.countClubs();
    }

    @Override
    protected Collection<Club> getEntities() {
        return clubControl.getClubs();
    }

    @Override
    protected BaseResult getResult(Collection<Club> entities) {
        var result = ClubResultFactory.getGetClubsResult();

        if(entities != null) {
            if(session.hasLimit(ClubFactory.class)) {
                result.setClubCount(getTotalEntities());
            }

            result.setClubs(clubControl.getClubTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
