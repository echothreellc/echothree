// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.wishlist.server.logic;

import com.echothree.control.user.wishlist.common.spec.WishlistTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.wishlist.common.exception.DuplicateWishlistTypeNameException;
import com.echothree.model.control.wishlist.common.exception.UnknownDefaultWishlistTypeException;
import com.echothree.model.control.wishlist.common.exception.UnknownWishlistTypeNameException;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.value.WishlistTypeDetailValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class WishlistTypeLogic
    extends BaseLogic {

    protected WishlistTypeLogic() {
        super();
    }

    public static WishlistTypeLogic getInstance() {
        return CDI.current().select(WishlistTypeLogic.class).get();
    }

    public WishlistType createWishlistType(final ExecutionErrorAccumulator eea, final String wishlistTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var wishlistType = wishlistControl.getWishlistTypeByName(wishlistTypeName);

        if(wishlistType == null) {
            wishlistType = wishlistControl.createWishlistType(wishlistTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                wishlistControl.createWishlistTypeDescription(wishlistType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateWishlistTypeNameException.class, eea, ExecutionErrors.DuplicateWishlistTypeName.name(), wishlistTypeName);
        }

        return wishlistType;
    }

    public WishlistType getWishlistTypeByName(final ExecutionErrorAccumulator eea, final String wishlistTypeName,
            final EntityPermission entityPermission) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var wishlistType = wishlistControl.getWishlistTypeByName(wishlistTypeName, entityPermission);

        if(wishlistType == null) {
            handleExecutionError(UnknownWishlistTypeNameException.class, eea, ExecutionErrors.UnknownWishlistTypeName.name(), wishlistTypeName);
        }

        return wishlistType;
    }

    public WishlistType getWishlistTypeByName(final ExecutionErrorAccumulator eea, final String wishlistTypeName) {
        return getWishlistTypeByName(eea, wishlistTypeName, EntityPermission.READ_ONLY);
    }

    public WishlistType getWishlistTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String wishlistTypeName) {
        return getWishlistTypeByName(eea, wishlistTypeName, EntityPermission.READ_WRITE);
    }

    public WishlistType getWishlistTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final WishlistTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        WishlistType wishlistType = null;
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var wishlistTypeName = universalSpec.getWishlistTypeName();
        var parameterCount = (wishlistTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    wishlistType = wishlistControl.getDefaultWishlistType(entityPermission);

                    if(wishlistType == null) {
                        handleExecutionError(UnknownDefaultWishlistTypeException.class, eea, ExecutionErrors.UnknownDefaultWishlistType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(wishlistTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.WishlistType.name());

                    if(!eea.hasExecutionErrors()) {
                        wishlistType = wishlistControl.getWishlistTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    wishlistType = getWishlistTypeByName(eea, wishlistTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return wishlistType;
    }

    public WishlistType getWishlistTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final WishlistTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getWishlistTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public WishlistType getWishlistTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final WishlistTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getWishlistTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateWishlistTypeFromValue(final WishlistTypeDetailValue wishlistTypeDetailValue,
            final BasePK updatedBy) {
        final var wishlistControl = Session.getModelController(WishlistControl.class);

        wishlistControl.updateWishlistTypeFromValue(wishlistTypeDetailValue, updatedBy);
    }
    
    public void deleteWishlistType(final ExecutionErrorAccumulator eea, final WishlistType wishlistType,
            final BasePK deletedBy) {
        var wishlistControl = Session.getModelController(WishlistControl.class);

        wishlistControl.deleteWishlistType(wishlistType, deletedBy);
    }

}
