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

import com.echothree.control.user.club.common.form.GetClubItemsForm;
import com.echothree.control.user.club.common.result.ClubResultFactory;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.club.server.logic.ClubLogic;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.model.data.club.server.entity.ClubItem;
import com.echothree.model.data.club.server.factory.ClubItemFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetClubItemsCommand
        extends BasePaginatedMultipleEntitiesCommand<ClubItem, GetClubItemsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ClubName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    ClubControl clubControl;

    @Inject
    ClubLogic clubLogic;

    /** Creates a new instance of GetClubItemsCommand */
    public GetClubItemsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    Club club;

    @Override
    protected void handleForm() {
        club = clubLogic.getClubByName(this, form.getClubName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : clubControl.countClubItemsByClub(club);
    }

    @Override
    protected Collection<ClubItem> getEntities() {
        return hasExecutionErrors() ? null : clubControl.getClubItemsByClub(club);
    }

    @Override
    protected BaseResult getResult(Collection<ClubItem> entities) {
        var result = ClubResultFactory.getGetClubItemsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setClub(clubControl.getClubTransfer(userVisit, club));

            if(session.hasLimit(ClubItemFactory.class)) {
                result.setClubItemCount(getTotalEntities());
            }

            result.setClubItems(clubControl.getClubItemTransfers(userVisit, entities));
        }

        return result;
    }
    
}
