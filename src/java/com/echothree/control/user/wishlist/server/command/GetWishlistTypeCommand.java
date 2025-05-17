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

import com.echothree.control.user.wishlist.common.form.GetWishlistTypeForm;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.control.wishlist.server.logic.WishlistTypeLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetWishlistTypeCommand
        extends BaseSingleEntityCommand<WishlistType, GetWishlistTypeForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }
    
    /** Creates a new instance of GetWishlistTypeCommand */
    public GetWishlistTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected WishlistType getEntity() {
        var wishlistType = WishlistTypeLogic.getInstance().getWishlistTypeByUniversalSpec(this, form, true);

        if(wishlistType != null) {
            sendEvent(wishlistType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return wishlistType;
    }

    @Override
    protected BaseResult getResult(WishlistType itemAliasType) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var result = WishlistResultFactory.getGetWishlistTypeResult();

        if(itemAliasType != null) {
            result.setWishlistType(wishlistControl.getWishlistTypeTransfer(getUserVisit(), itemAliasType));
        }

        return result;
    }
    
}
