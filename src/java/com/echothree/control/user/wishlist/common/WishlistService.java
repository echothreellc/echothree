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

package com.echothree.control.user.wishlist.common;

import com.echothree.control.user.wishlist.common.form.*;
import com.echothree.control.user.wishlist.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface WishlistService
        extends WishlistForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Wishlist Types
    // -------------------------------------------------------------------------
    
    CommandResult<CreateWishlistTypeResult> createWishlistType(UserVisitPK userVisitPK, CreateWishlistTypeForm form);
    
    CommandResult<GetWishlistTypeResult> getWishlistType(UserVisitPK userVisitPK, GetWishlistTypeForm form);
    
    CommandResult<GetWishlistTypesResult> getWishlistTypes(UserVisitPK userVisitPK, GetWishlistTypesForm form);
    
    CommandResult<GetWishlistTypeChoicesResult> getWishlistTypeChoices(UserVisitPK userVisitPK, GetWishlistTypeChoicesForm form);
    
    CommandResult<?> setDefaultWishlistType(UserVisitPK userVisitPK, SetDefaultWishlistTypeForm form);
    
    CommandResult<EditWishlistTypeResult> editWishlistType(UserVisitPK userVisitPK, EditWishlistTypeForm form);
    
    CommandResult<?> deleteWishlistType(UserVisitPK userVisitPK, DeleteWishlistTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Wishlist Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createWishlistTypeDescription(UserVisitPK userVisitPK, CreateWishlistTypeDescriptionForm form);
    
    CommandResult<GetWishlistTypeDescriptionsResult> getWishlistTypeDescriptions(UserVisitPK userVisitPK, GetWishlistTypeDescriptionsForm form);
    
    CommandResult<EditWishlistTypeDescriptionResult> editWishlistTypeDescription(UserVisitPK userVisitPK, EditWishlistTypeDescriptionForm form);
    
    CommandResult<?> deleteWishlistTypeDescription(UserVisitPK userVisitPK, DeleteWishlistTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Wishlist Type Priorities
    // -------------------------------------------------------------------------
    
    CommandResult<CreateWishlistPriorityResult> createWishlistPriority(UserVisitPK userVisitPK, CreateWishlistPriorityForm form);
    
    CommandResult<GetWishlistPriorityResult> getWishlistPriority(UserVisitPK userVisitPK, GetWishlistPriorityForm form);
    
    CommandResult<GetWishlistPrioritiesResult> getWishlistPriorities(UserVisitPK userVisitPK, GetWishlistPrioritiesForm form);
    
    CommandResult<GetWishlistPriorityChoicesResult> getWishlistPriorityChoices(UserVisitPK userVisitPK, GetWishlistPriorityChoicesForm form);
    
    CommandResult<?> setDefaultWishlistPriority(UserVisitPK userVisitPK, SetDefaultWishlistPriorityForm form);
    
    CommandResult<EditWishlistPriorityResult> editWishlistPriority(UserVisitPK userVisitPK, EditWishlistPriorityForm form);
    
    CommandResult<?> deleteWishlistPriority(UserVisitPK userVisitPK, DeleteWishlistPriorityForm form);
    
    // -------------------------------------------------------------------------
    //   Wishlist Type Priority Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createWishlistPriorityDescription(UserVisitPK userVisitPK, CreateWishlistPriorityDescriptionForm form);
    
    CommandResult<GetWishlistPriorityDescriptionsResult> getWishlistPriorityDescriptions(UserVisitPK userVisitPK, GetWishlistPriorityDescriptionsForm form);
    
    CommandResult<EditWishlistPriorityDescriptionResult> editWishlistPriorityDescription(UserVisitPK userVisitPK, EditWishlistPriorityDescriptionForm form);
    
    CommandResult<?> deleteWishlistPriorityDescription(UserVisitPK userVisitPK, DeleteWishlistPriorityDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Wishlist Lines
    // -------------------------------------------------------------------------
    
    CommandResult<?> createWishlistLine(UserVisitPK userVisitPK, CreateWishlistLineForm form);
    
    CommandResult<GetWishlistLinesResult> getWishlistLines(UserVisitPK userVisitPK, GetWishlistLinesForm form);
    
}
