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

package com.echothree.control.user.rating.common;

import com.echothree.control.user.rating.common.form.*;
import com.echothree.control.user.rating.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface RatingService
        extends RatingForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Rating Types
    // -------------------------------------------------------------------------
    
    CommandResult<CreateRatingTypeResult> createRatingType(UserVisitPK userVisitPK, CreateRatingTypeForm form);
    
    CommandResult<GetRatingTypeResult> getRatingType(UserVisitPK userVisitPK, GetRatingTypeForm form);
    
    CommandResult<GetRatingTypesResult> getRatingTypes(UserVisitPK userVisitPK, GetRatingTypesForm form);
    
    CommandResult<EditRatingTypeResult> editRatingType(UserVisitPK userVisitPK, EditRatingTypeForm form);
    
    CommandResult<?> deleteRatingType(UserVisitPK userVisitPK, DeleteRatingTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Rating Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createRatingTypeDescription(UserVisitPK userVisitPK, CreateRatingTypeDescriptionForm form);
    
    CommandResult<GetRatingTypeDescriptionResult> getRatingTypeDescription(UserVisitPK userVisitPK, GetRatingTypeDescriptionForm form);
    
    CommandResult<GetRatingTypeDescriptionsResult> getRatingTypeDescriptions(UserVisitPK userVisitPK, GetRatingTypeDescriptionsForm form);
    
    CommandResult<EditRatingTypeDescriptionResult> editRatingTypeDescription(UserVisitPK userVisitPK, EditRatingTypeDescriptionForm form);
    
    CommandResult<?> deleteRatingTypeDescription(UserVisitPK userVisitPK, DeleteRatingTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Rating Type List Items
    // -------------------------------------------------------------------------
    
    CommandResult<?> createRatingTypeListItem(UserVisitPK userVisitPK, CreateRatingTypeListItemForm form);
    
    CommandResult<GetRatingTypeListItemResult> getRatingTypeListItem(UserVisitPK userVisitPK, GetRatingTypeListItemForm form);
    
    CommandResult<GetRatingTypeListItemsResult> getRatingTypeListItems(UserVisitPK userVisitPK, GetRatingTypeListItemsForm form);
    
    CommandResult<GetRatingTypeListItemChoicesResult> getRatingTypeListItemChoices(UserVisitPK userVisitPK, GetRatingTypeListItemChoicesForm form);
    
    CommandResult<?> setDefaultRatingTypeListItem(UserVisitPK userVisitPK, SetDefaultRatingTypeListItemForm form);
    
    CommandResult<EditRatingTypeListItemResult> editRatingTypeListItem(UserVisitPK userVisitPK, EditRatingTypeListItemForm form);
    
    CommandResult<?> deleteRatingTypeListItem(UserVisitPK userVisitPK, DeleteRatingTypeListItemForm form);
    
    // -------------------------------------------------------------------------
    //   Rating Type List Item Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createRatingTypeListItemDescription(UserVisitPK userVisitPK, CreateRatingTypeListItemDescriptionForm form);
    
    CommandResult<GetRatingTypeListItemDescriptionResult> getRatingTypeListItemDescription(UserVisitPK userVisitPK, GetRatingTypeListItemDescriptionForm form);
    
    CommandResult<GetRatingTypeListItemDescriptionsResult> getRatingTypeListItemDescriptions(UserVisitPK userVisitPK, GetRatingTypeListItemDescriptionsForm form);
    
    CommandResult<EditRatingTypeListItemDescriptionResult> editRatingTypeListItemDescription(UserVisitPK userVisitPK, EditRatingTypeListItemDescriptionForm form);
    
    CommandResult<?> deleteRatingTypeListItemDescription(UserVisitPK userVisitPK, DeleteRatingTypeListItemDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Ratings
    // -------------------------------------------------------------------------
    
    CommandResult<?> createRating(UserVisitPK userVisitPK, CreateRatingForm form);
    
    CommandResult<GetRatingResult> getRating(UserVisitPK userVisitPK, GetRatingForm form);
    
    CommandResult<EditRatingResult> editRating(UserVisitPK userVisitPK, EditRatingForm form);
    
    CommandResult<?> deleteRating(UserVisitPK userVisitPK, DeleteRatingForm form);
    
}
