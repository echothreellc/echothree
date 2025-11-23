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

import com.echothree.control.user.wishlist.common.form.GetWishlistPriorityForm;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.control.wishlist.server.logic.WishlistPriorityLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetWishlistPriorityCommand
        extends BaseSingleEntityCommand<WishlistPriority, GetWishlistPriorityForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("WishlistPriorityName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
            new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetWishlistPriorityCommand */
    public GetWishlistPriorityCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected WishlistPriority getEntity() {
        var wishlistPriority = WishlistPriorityLogic.getInstance().getWishlistPriorityByUniversalSpec(this, form, true);

        if(wishlistPriority != null) {
            sendEvent(wishlistPriority.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return wishlistPriority;
    }

    @Override
    protected BaseResult getResult(WishlistPriority wishlistPriority) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var result = WishlistResultFactory.getGetWishlistPriorityResult();

        if(wishlistPriority != null) {
            result.setWishlistPriority(wishlistControl.getWishlistPriorityTransfer(getUserVisit(), wishlistPriority));
        }

        return result;
    }

}
