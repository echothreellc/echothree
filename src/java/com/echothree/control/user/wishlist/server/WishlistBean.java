// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
    public CommandResult createWishlistTypePriority(UserVisitPK userVisitPK, CreateWishlistTypePriorityForm form) {
        return new CreateWishlistTypePriorityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWishlistTypePriority(UserVisitPK userVisitPK, GetWishlistTypePriorityForm form) {
        return new GetWishlistTypePriorityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWishlistTypePriorities(UserVisitPK userVisitPK, GetWishlistTypePrioritiesForm form) {
        return new GetWishlistTypePrioritiesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWishlistTypePriorityChoices(UserVisitPK userVisitPK, GetWishlistTypePriorityChoicesForm form) {
        return new GetWishlistTypePriorityChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultWishlistTypePriority(UserVisitPK userVisitPK, SetDefaultWishlistTypePriorityForm form) {
        return new SetDefaultWishlistTypePriorityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWishlistTypePriority(UserVisitPK userVisitPK, EditWishlistTypePriorityForm form) {
        return new EditWishlistTypePriorityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWishlistTypePriority(UserVisitPK userVisitPK, DeleteWishlistTypePriorityForm form) {
        return new DeleteWishlistTypePriorityCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Type Priority Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistTypePriorityDescription(UserVisitPK userVisitPK, CreateWishlistTypePriorityDescriptionForm form) {
        return new CreateWishlistTypePriorityDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWishlistTypePriorityDescriptions(UserVisitPK userVisitPK, GetWishlistTypePriorityDescriptionsForm form) {
        return new GetWishlistTypePriorityDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWishlistTypePriorityDescription(UserVisitPK userVisitPK, EditWishlistTypePriorityDescriptionForm form) {
        return new EditWishlistTypePriorityDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWishlistTypePriorityDescription(UserVisitPK userVisitPK, DeleteWishlistTypePriorityDescriptionForm form) {
        return new DeleteWishlistTypePriorityDescriptionCommand(userVisitPK, form).run();
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
