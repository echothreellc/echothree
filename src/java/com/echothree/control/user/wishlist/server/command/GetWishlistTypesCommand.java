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

import com.echothree.control.user.wishlist.common.form.GetWishlistTypesForm;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetWishlistTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<WishlistType, GetWishlistTypesForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    /** Creates a new instance of GetWishlistTypesCommand */
    public GetWishlistTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        var wishlistControl = Session.getModelController(WishlistControl.class);

        return wishlistControl.countWishlistTypes();
    }

    @Override
    protected Collection<WishlistType> getEntities() {
        var wishlistControl = Session.getModelController(WishlistControl.class);

        return wishlistControl.getWishlistTypes();
    }

    @Override
    protected BaseResult getResult(Collection<WishlistType> entities) {
        var result = WishlistResultFactory.getGetWishlistTypesResult();
        var wishlistControl = Session.getModelController(WishlistControl.class);

        result.setWishlistTypes(wishlistControl.getWishlistTypeTransfers(getUserVisit(), entities));

        return result;
    }
    
}
