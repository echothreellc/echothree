// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.wishlist.common.form.SetDefaultWishlistTypePriorityForm;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.value.WishlistTypePriorityDetailValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetDefaultWishlistTypePriorityCommand
        extends BaseSimpleCommand<SetDefaultWishlistTypePriorityForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WishlistTypePriorityName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of SetDefaultWishlistTypePriorityCommand */
    public SetDefaultWishlistTypePriorityCommand(UserVisitPK userVisitPK, SetDefaultWishlistTypePriorityForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        String wishlistTypeName = form.getWishlistTypeName();
        WishlistType wishlistType = wishlistControl.getWishlistTypeByName(wishlistTypeName);
        
        if(wishlistType != null) {
            String wishlistTypePriorityName = form.getWishlistTypePriorityName();
            WishlistTypePriorityDetailValue wishlistTypePriorityDetailValue = wishlistControl.getWishlistTypePriorityDetailValueByNameForUpdate(wishlistType, wishlistTypePriorityName);
            
            if(wishlistTypePriorityDetailValue != null) {
                wishlistTypePriorityDetailValue.setIsDefault(Boolean.TRUE);
                wishlistControl.updateWishlistTypePriorityFromValue(wishlistTypePriorityDetailValue, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownWishlistTypePriorityName.name(), wishlistTypePriorityName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWishlistTypeName.name(), wishlistTypeName);
        }
        
        return null;
    }
    
}
