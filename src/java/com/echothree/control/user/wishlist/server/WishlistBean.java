// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
        return new CreateWishlistTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWishlistType(UserVisitPK userVisitPK, GetWishlistTypeForm form) {
        return new GetWishlistTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWishlistTypes(UserVisitPK userVisitPK, GetWishlistTypesForm form) {
        return new GetWishlistTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWishlistTypeChoices(UserVisitPK userVisitPK, GetWishlistTypeChoicesForm form) {
        return new GetWishlistTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultWishlistType(UserVisitPK userVisitPK, SetDefaultWishlistTypeForm form) {
        return new SetDefaultWishlistTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWishlistType(UserVisitPK userVisitPK, EditWishlistTypeForm form) {
        return new EditWishlistTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWishlistType(UserVisitPK userVisitPK, DeleteWishlistTypeForm form) {
        return new DeleteWishlistTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistTypeDescription(UserVisitPK userVisitPK, CreateWishlistTypeDescriptionForm form) {
        return new CreateWishlistTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWishlistTypeDescriptions(UserVisitPK userVisitPK, GetWishlistTypeDescriptionsForm form) {
        return new GetWishlistTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWishlistTypeDescription(UserVisitPK userVisitPK, EditWishlistTypeDescriptionForm form) {
        return new EditWishlistTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWishlistTypeDescription(UserVisitPK userVisitPK, DeleteWishlistTypeDescriptionForm form) {
        return new DeleteWishlistTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Type Priorities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistPriority(UserVisitPK userVisitPK, CreateWishlistPriorityForm form) {
        return new CreateWishlistPriorityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWishlistPriority(UserVisitPK userVisitPK, GetWishlistPriorityForm form) {
        return new GetWishlistPriorityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWishlistPriorities(UserVisitPK userVisitPK, GetWishlistPrioritiesForm form) {
        return new GetWishlistPrioritiesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWishlistPriorityChoices(UserVisitPK userVisitPK, GetWishlistPriorityChoicesForm form) {
        return new GetWishlistPriorityChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultWishlistPriority(UserVisitPK userVisitPK, SetDefaultWishlistPriorityForm form) {
        return new SetDefaultWishlistPriorityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWishlistPriority(UserVisitPK userVisitPK, EditWishlistPriorityForm form) {
        return new EditWishlistPriorityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWishlistPriority(UserVisitPK userVisitPK, DeleteWishlistPriorityForm form) {
        return new DeleteWishlistPriorityCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Type Priority Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistPriorityDescription(UserVisitPK userVisitPK, CreateWishlistPriorityDescriptionForm form) {
        return new CreateWishlistPriorityDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWishlistPriorityDescriptions(UserVisitPK userVisitPK, GetWishlistPriorityDescriptionsForm form) {
        return new GetWishlistPriorityDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWishlistPriorityDescription(UserVisitPK userVisitPK, EditWishlistPriorityDescriptionForm form) {
        return new EditWishlistPriorityDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWishlistPriorityDescription(UserVisitPK userVisitPK, DeleteWishlistPriorityDescriptionForm form) {
        return new DeleteWishlistPriorityDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Line Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistLine(UserVisitPK userVisitPK, CreateWishlistLineForm form) {
        return new CreateWishlistLineCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWishlistLines(UserVisitPK userVisitPK, GetWishlistLinesForm form) {
        return new GetWishlistLinesCommand(userVisitPK, form).run();
    }
    
}
