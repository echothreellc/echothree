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

package com.echothree.control.user.wishlist.server.command;

import com.echothree.control.user.wishlist.common.form.GetWishlistTypePrioritiesForm;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.control.wishlist.server.logic.WishlistTypeLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriority;
import com.echothree.model.data.wishlist.server.factory.WishlistTypePriorityFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetWishlistTypePrioritiesCommand
        extends BaseMultipleEntitiesCommand<WishlistTypePriority, GetWishlistTypePrioritiesForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetWishlistTypePrioritiesCommand */
    public GetWishlistTypePrioritiesCommand(UserVisitPK userVisitPK, GetWishlistTypePrioritiesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    WishlistType wishlistType;

    @Override
    protected Collection<WishlistTypePriority> getEntities() {
        var wishlistControl = Session.getModelController(WishlistControl.class);

        wishlistType = WishlistTypeLogic.getInstance().getWishlistTypeByName(this, form.getWishlistTypeName());

        return hasExecutionErrors() ? null : wishlistControl.getWishlistTypePriorities(wishlistType);
    }

    @Override
    protected BaseResult getTransfers(Collection<WishlistTypePriority> entities) {
        var result = WishlistResultFactory.getGetWishlistTypePrioritiesResult();

        if(entities != null) {
            var wishlistControl = Session.getModelController(WishlistControl.class);

            if(session.hasLimit(WishlistTypePriorityFactory.class)) {
                result.setWishlistTypePriorityCount(wishlistControl.countWishlistTypePrioritiesByWishlistType(wishlistType));
            }

            result.setWishlistType(wishlistControl.getWishlistTypeTransfer(getUserVisit(), wishlistType));
            result.setWishlistTypePriorities(wishlistControl.getWishlistTypePriorityTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
