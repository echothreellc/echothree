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

package com.echothree.control.user.club.common;

import com.echothree.control.user.club.common.form.*;
import com.echothree.control.user.club.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface ClubService
        extends ClubForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Clubs
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createClub(UserVisitPK userVisitPK, CreateClubForm form);
    
    CommandResult<GetClubsResult> getClubs(UserVisitPK userVisitPK, GetClubsForm form);
    
    CommandResult<GetClubResult> getClub(UserVisitPK userVisitPK, GetClubForm form);
    
    CommandResult<?> setDefaultClub(UserVisitPK userVisitPK, SetDefaultClubForm form);
    
    CommandResult<EditClubResult> editClub(UserVisitPK userVisitPK, EditClubForm form);
    
    CommandResult<?> deleteClub(UserVisitPK userVisitPK, DeleteClubForm form);
    
    // --------------------------------------------------------------------------------
    //   Club Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createClubDescription(UserVisitPK userVisitPK, CreateClubDescriptionForm form);
    
    CommandResult<GetClubDescriptionsResult> getClubDescriptions(UserVisitPK userVisitPK, GetClubDescriptionsForm form);
    
    CommandResult<EditClubDescriptionResult> editClubDescription(UserVisitPK userVisitPK, EditClubDescriptionForm form);
    
    CommandResult<?> deleteClubDescription(UserVisitPK userVisitPK, DeleteClubDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Club Item Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createClubItemType(UserVisitPK userVisitPK, CreateClubItemTypeForm form);
    
    CommandResult<GetClubItemTypeChoicesResult> getClubItemTypeChoices(UserVisitPK userVisitPK, GetClubItemTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Club Item Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createClubItemTypeDescription(UserVisitPK userVisitPK, CreateClubItemTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Club Items
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createClubItem(UserVisitPK userVisitPK, CreateClubItemForm form);
    
    CommandResult<GetClubItemsResult> getClubItems(UserVisitPK userVisitPK, GetClubItemsForm form);
    
    CommandResult<?> deleteClubItem(UserVisitPK userVisitPK, DeleteClubItemForm form);
    
}
