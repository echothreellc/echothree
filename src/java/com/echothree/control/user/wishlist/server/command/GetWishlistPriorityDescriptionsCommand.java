// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.wishlist.common.form.GetWishlistPriorityDescriptionsForm;
import com.echothree.control.user.wishlist.common.result.GetWishlistPriorityDescriptionsResult;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetWishlistPriorityDescriptionsCommand
        extends BaseSimpleCommand<GetWishlistPriorityDescriptionsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WishlistPriorityName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetWishlistPriorityDescriptionsCommand */
    public GetWishlistPriorityDescriptionsCommand(UserVisitPK userVisitPK, GetWishlistPriorityDescriptionsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        GetWishlistPriorityDescriptionsResult result = WishlistResultFactory.getGetWishlistPriorityDescriptionsResult();
        String wishlistTypeName = form.getWishlistTypeName();
        WishlistType wishlistType = wishlistControl.getWishlistTypeByName(wishlistTypeName);
        
        if(wishlistType != null) {
            String wishlistPriorityName = form.getWishlistPriorityName();
            WishlistPriority wishlistPriority = wishlistControl.getWishlistPriorityByName(wishlistType, wishlistPriorityName);
            
            if(wishlistPriority != null) {
                result.setWishlistPriority(wishlistControl.getWishlistPriorityTransfer(getUserVisit(), wishlistPriority));
                result.setWishlistPriorityDescriptions(wishlistControl.getWishlistPriorityDescriptionTransfers(getUserVisit(), wishlistPriority));
            } else {
                addExecutionError(ExecutionErrors.UnknownWishlistPriorityName.name(), wishlistPriorityName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWishlistTypeName.name(), wishlistTypeName);
        }
        
        return result;
    }
    
}
