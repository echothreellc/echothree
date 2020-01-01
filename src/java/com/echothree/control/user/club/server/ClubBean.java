// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.club.server;

import com.echothree.control.user.club.common.ClubRemote;
import com.echothree.control.user.club.common.form.*;
import com.echothree.control.user.club.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class ClubBean
        extends ClubFormsImpl
        implements ClubRemote, ClubLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "ClubBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Clubs
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createClub(UserVisitPK userVisitPK, CreateClubForm form) {
        return new CreateClubCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getClubs(UserVisitPK userVisitPK, GetClubsForm form) {
        return new GetClubsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getClub(UserVisitPK userVisitPK, GetClubForm form) {
        return new GetClubCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultClub(UserVisitPK userVisitPK, SetDefaultClubForm form) {
        return new SetDefaultClubCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteClub(UserVisitPK userVisitPK, DeleteClubForm form) {
        return new DeleteClubCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Club Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createClubDescription(UserVisitPK userVisitPK, CreateClubDescriptionForm form) {
        return new CreateClubDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getClubDescriptions(UserVisitPK userVisitPK, GetClubDescriptionsForm form) {
        return new GetClubDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editClubDescription(UserVisitPK userVisitPK, EditClubDescriptionForm form) {
        return new EditClubDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteClubDescription(UserVisitPK userVisitPK, DeleteClubDescriptionForm form) {
        return new DeleteClubDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Club Item Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createClubItemType(UserVisitPK userVisitPK, CreateClubItemTypeForm form) {
        return new CreateClubItemTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getClubItemTypeChoices(UserVisitPK userVisitPK, GetClubItemTypeChoicesForm form) {
        return new GetClubItemTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Club Item Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createClubItemTypeDescription(UserVisitPK userVisitPK, CreateClubItemTypeDescriptionForm form) {
        return new CreateClubItemTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Club Items
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createClubItem(UserVisitPK userVisitPK, CreateClubItemForm form) {
        return new CreateClubItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getClubItems(UserVisitPK userVisitPK, GetClubItemsForm form) {
        return new GetClubItemsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editClub(UserVisitPK userVisitPK, EditClubForm form) {
        return new EditClubCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteClubItem(UserVisitPK userVisitPK, DeleteClubItemForm form) {
        return new DeleteClubItemCommand(userVisitPK, form).run();
    }
    
}
