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

import com.echothree.control.user.wishlist.common.form.CreateWishlistPriorityForm;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.model.control.wishlist.server.logic.WishlistPriorityLogic;
import com.echothree.model.control.wishlist.server.logic.WishlistTypeLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateWishlistPriorityCommand
        extends BaseSimpleCommand<CreateWishlistPriorityForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WishlistPriorityName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of CreateWishlistPriorityCommand */
    public CreateWishlistPriorityCommand(UserVisitPK userVisitPK, CreateWishlistPriorityForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = WishlistResultFactory.getCreateWishlistPriorityResult();
        var wishlistTypeName = form.getWishlistTypeName();
        var wishlistType = WishlistTypeLogic.getInstance().getWishlistTypeByName(this, wishlistTypeName);
        WishlistPriority wishlistPriority = null;

        if(!hasExecutionErrors()) {
            var wishlistPriorityName = form.getWishlistPriorityName();
            var isDefault = Boolean.valueOf(form.getIsDefault());
            var sortOrder = Integer.valueOf(form.getSortOrder());
            var description = form.getDescription();
            var createdBy = getPartyPK();

            wishlistPriority = WishlistPriorityLogic.getInstance().createWishlistPriority(this, wishlistType,
                    wishlistPriorityName, isDefault, sortOrder, getPreferredLanguage(), description, createdBy);
        }

        if(wishlistPriority != null) {
            result.setEntityRef(wishlistPriority.getPrimaryKey().getEntityRef());
            result.setWishlistTypeName(wishlistPriority.getLastDetail().getWishlistPriorityName());
        }

        return result;
    }
    
}
