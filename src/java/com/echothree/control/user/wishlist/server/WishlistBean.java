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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateWishlistTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistType(UserVisitPK userVisitPK, GetWishlistTypeForm form) {
        return CDI.current().select(GetWishlistTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistTypes(UserVisitPK userVisitPK, GetWishlistTypesForm form) {
        return CDI.current().select(GetWishlistTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistTypeChoices(UserVisitPK userVisitPK, GetWishlistTypeChoicesForm form) {
        return CDI.current().select(GetWishlistTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultWishlistType(UserVisitPK userVisitPK, SetDefaultWishlistTypeForm form) {
        return CDI.current().select(SetDefaultWishlistTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWishlistType(UserVisitPK userVisitPK, EditWishlistTypeForm form) {
        return CDI.current().select(EditWishlistTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWishlistType(UserVisitPK userVisitPK, DeleteWishlistTypeForm form) {
        return CDI.current().select(DeleteWishlistTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistTypeDescription(UserVisitPK userVisitPK, CreateWishlistTypeDescriptionForm form) {
        return CDI.current().select(CreateWishlistTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistTypeDescriptions(UserVisitPK userVisitPK, GetWishlistTypeDescriptionsForm form) {
        return CDI.current().select(GetWishlistTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWishlistTypeDescription(UserVisitPK userVisitPK, EditWishlistTypeDescriptionForm form) {
        return CDI.current().select(EditWishlistTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWishlistTypeDescription(UserVisitPK userVisitPK, DeleteWishlistTypeDescriptionForm form) {
        return CDI.current().select(DeleteWishlistTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Type Priorities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistPriority(UserVisitPK userVisitPK, CreateWishlistPriorityForm form) {
        return CDI.current().select(CreateWishlistPriorityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistPriority(UserVisitPK userVisitPK, GetWishlistPriorityForm form) {
        return CDI.current().select(GetWishlistPriorityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistPriorities(UserVisitPK userVisitPK, GetWishlistPrioritiesForm form) {
        return CDI.current().select(GetWishlistPrioritiesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistPriorityChoices(UserVisitPK userVisitPK, GetWishlistPriorityChoicesForm form) {
        return CDI.current().select(GetWishlistPriorityChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultWishlistPriority(UserVisitPK userVisitPK, SetDefaultWishlistPriorityForm form) {
        return CDI.current().select(SetDefaultWishlistPriorityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWishlistPriority(UserVisitPK userVisitPK, EditWishlistPriorityForm form) {
        return CDI.current().select(EditWishlistPriorityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWishlistPriority(UserVisitPK userVisitPK, DeleteWishlistPriorityForm form) {
        return CDI.current().select(DeleteWishlistPriorityCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Type Priority Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistPriorityDescription(UserVisitPK userVisitPK, CreateWishlistPriorityDescriptionForm form) {
        return CDI.current().select(CreateWishlistPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistPriorityDescriptions(UserVisitPK userVisitPK, GetWishlistPriorityDescriptionsForm form) {
        return CDI.current().select(GetWishlistPriorityDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWishlistPriorityDescription(UserVisitPK userVisitPK, EditWishlistPriorityDescriptionForm form) {
        return CDI.current().select(EditWishlistPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWishlistPriorityDescription(UserVisitPK userVisitPK, DeleteWishlistPriorityDescriptionForm form) {
        return CDI.current().select(DeleteWishlistPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Wishlist Line Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWishlistLine(UserVisitPK userVisitPK, CreateWishlistLineForm form) {
        return CDI.current().select(CreateWishlistLineCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWishlistLines(UserVisitPK userVisitPK, GetWishlistLinesForm form) {
        return CDI.current().select(GetWishlistLinesCommand.class).get().run(userVisitPK, form);
    }
    
}
