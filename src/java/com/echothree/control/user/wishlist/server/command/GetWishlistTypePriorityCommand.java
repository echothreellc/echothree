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

import com.echothree.control.user.wishlist.common.form.GetWishlistTypePriorityForm;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.control.wishlist.server.logic.WishlistTypePriorityLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriority;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetWishlistTypePriorityCommand
        extends BaseSingleEntityCommand<WishlistTypePriority, GetWishlistTypePriorityForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("WishlistTypePriorityName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetWishlistTypePriorityCommand */
    public GetWishlistTypePriorityCommand(UserVisitPK userVisitPK, GetWishlistTypePriorityForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected WishlistTypePriority getEntity() {
        var wishlistTypePriority = WishlistTypePriorityLogic.getInstance().getWishlistTypePriorityByUniversalSpec(this, form, true);

        if(wishlistTypePriority != null) {
            sendEvent(wishlistTypePriority.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return wishlistTypePriority;
    }

    @Override
    protected BaseResult getTransfer(WishlistTypePriority wishlistTypePriority) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var result = WishlistResultFactory.getGetWishlistTypePriorityResult();

        if(wishlistTypePriority != null) {
            result.setWishlistTypePriority(wishlistControl.getWishlistTypePriorityTransfer(getUserVisit(), wishlistTypePriority));
        }

        return result;
    }

}
