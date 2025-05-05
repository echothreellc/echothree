// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.wishlist.server;

import com.echothree.control.user.wishlist.common.WishlistRemote;
import com.echothree.control.user.wishlist.common.form.*;
import com.echothree.control.user.wishlist.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class WishlistBean
        extends WishlistFormsImpl
        implements WishlistRemote, WishlistLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "WishlistBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistType(UserVisitPK userVisitPK, CreateWishlistTypeForm form) {
        return new CreateWishlistTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistType(UserVisitPK userVisitPK, GetWishlistTypeForm form) {
        return new GetWishlistTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistTypes(UserVisitPK userVisitPK, GetWishlistTypesForm form) {
        return new GetWishlistTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistTypeChoices(UserVisitPK userVisitPK, GetWishlistTypeChoicesForm form) {
        return new GetWishlistTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultWishlistType(UserVisitPK userVisitPK, SetDefaultWishlistTypeForm form) {
        return new SetDefaultWishlistTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWishlistType(UserVisitPK userVisitPK, EditWishlistTypeForm form) {
        return new EditWishlistTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWishlistType(UserVisitPK userVisitPK, DeleteWishlistTypeForm form) {
        return new DeleteWishlistTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistTypeDescription(UserVisitPK userVisitPK, CreateWishlistTypeDescriptionForm form) {
        return new CreateWishlistTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistTypeDescriptions(UserVisitPK userVisitPK, GetWishlistTypeDescriptionsForm form) {
        return new GetWishlistTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWishlistTypeDescription(UserVisitPK userVisitPK, EditWishlistTypeDescriptionForm form) {
        return new EditWishlistTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWishlistTypeDescription(UserVisitPK userVisitPK, DeleteWishlistTypeDescriptionForm form) {
        return new DeleteWishlistTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Type Priorities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistPriority(UserVisitPK userVisitPK, CreateWishlistPriorityForm form) {
        return new CreateWishlistPriorityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistPriority(UserVisitPK userVisitPK, GetWishlistPriorityForm form) {
        return new GetWishlistPriorityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistPriorities(UserVisitPK userVisitPK, GetWishlistPrioritiesForm form) {
        return new GetWishlistPrioritiesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistPriorityChoices(UserVisitPK userVisitPK, GetWishlistPriorityChoicesForm form) {
        return new GetWishlistPriorityChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultWishlistPriority(UserVisitPK userVisitPK, SetDefaultWishlistPriorityForm form) {
        return new SetDefaultWishlistPriorityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWishlistPriority(UserVisitPK userVisitPK, EditWishlistPriorityForm form) {
        return new EditWishlistPriorityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWishlistPriority(UserVisitPK userVisitPK, DeleteWishlistPriorityForm form) {
        return new DeleteWishlistPriorityCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Type Priority Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistPriorityDescription(UserVisitPK userVisitPK, CreateWishlistPriorityDescriptionForm form) {
        return new CreateWishlistPriorityDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistPriorityDescriptions(UserVisitPK userVisitPK, GetWishlistPriorityDescriptionsForm form) {
        return new GetWishlistPriorityDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWishlistPriorityDescription(UserVisitPK userVisitPK, EditWishlistPriorityDescriptionForm form) {
        return new EditWishlistPriorityDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWishlistPriorityDescription(UserVisitPK userVisitPK, DeleteWishlistPriorityDescriptionForm form) {
        return new DeleteWishlistPriorityDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Line Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistLine(UserVisitPK userVisitPK, CreateWishlistLineForm form) {
        return new CreateWishlistLineCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistLines(UserVisitPK userVisitPK, GetWishlistLinesForm form) {
        return new GetWishlistLinesCommand().run(userVisitPK, form);
    }
    
}
