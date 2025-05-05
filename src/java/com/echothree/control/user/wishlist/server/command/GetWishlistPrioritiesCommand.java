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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.model.data.wishlist.server.factory.WishlistPriorityFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetWishlistPrioritiesCommand
        extends BaseMultipleEntitiesCommand<WishlistPriority, GetWishlistPrioritiesForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetWishlistPrioritiesCommand */
    public GetWishlistPrioritiesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    WishlistType wishlistType;

    @Override
    protected Collection<WishlistPriority> getEntities() {
        var wishlistControl = Session.getModelController(WishlistControl.class);

        wishlistType = WishlistTypeLogic.getInstance().getWishlistTypeByName(this, form.getWishlistTypeName());

        return hasExecutionErrors() ? null : wishlistControl.getWishlistPriorities(wishlistType);
    }

    @Override
    protected BaseResult getResult(Collection<WishlistPriority> entities) {
        var result = WishlistResultFactory.getGetWishlistPrioritiesResult();

        if(entities != null) {
            var wishlistControl = Session.getModelController(WishlistControl.class);

            if(session.hasLimit(WishlistPriorityFactory.class)) {
                result.setWishlistPriorityCount(wishlistControl.countWishlistPrioritiesByWishlistType(wishlistType));
            }

            result.setWishlistType(wishlistControl.getWishlistTypeTransfer(getUserVisit(), wishlistType));
            result.setWishlistPriorities(wishlistControl.getWishlistPriorityTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
