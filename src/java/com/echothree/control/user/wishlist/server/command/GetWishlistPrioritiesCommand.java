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

package com.echothree.control.user.wishlist.server.command;

import com.echothree.control.user.wishlist.common.form.GetWishlistPrioritiesForm;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.control.wishlist.server.logic.WishlistTypeLogic;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.factory.WishlistPriorityFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetWishlistPrioritiesCommand
        extends BasePaginatedMultipleEntitiesCommand<WishlistPriority, GetWishlistPrioritiesForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    /** Creates a new instance of GetWishlistPrioritiesCommand */
    public GetWishlistPrioritiesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    WishlistType wishlistType;

    @Override
    protected void handleForm() {
        var wishlistTypeName = form.getWishlistTypeName();

        wishlistType = WishlistTypeLogic.getInstance().getWishlistTypeByName(this, wishlistTypeName);
    }

    @Override
    protected Long getTotalEntities() {
        var wishlistControl = Session.getModelController(WishlistControl.class);

        return hasExecutionErrors() ? null :
                wishlistControl.countWishlistPrioritiesByWishlistType(wishlistType);
    }

    @Override
    protected Collection<WishlistPriority> getEntities() {
        Collection<WishlistPriority> wishlistPriorities = null;

        if(!hasExecutionErrors()) {
            var wishlistControl = Session.getModelController(WishlistControl.class);

            wishlistPriorities = wishlistControl.getWishlistPriorities(wishlistType);
        }

        return wishlistPriorities;
    }

    @Override
    protected BaseResult getResult(Collection<WishlistPriority> entities) {
        var result = WishlistResultFactory.getGetWishlistPrioritiesResult();

        if(entities != null) {
            var wishlistControl = Session.getModelController(WishlistControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(WishlistPriorityFactory.class)) {
                result.setWishlistPriorityCount(wishlistControl.countWishlistPrioritiesByWishlistType(wishlistType));
            }

            result.setWishlistType(wishlistControl.getWishlistTypeTransfer(userVisit, wishlistType));
            result.setWishlistPriorities(wishlistControl.getWishlistPriorityTransfers(userVisit, entities));
        }

        return result;
    }
}
