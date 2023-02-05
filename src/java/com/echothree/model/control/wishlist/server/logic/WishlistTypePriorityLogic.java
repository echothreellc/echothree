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

package com.echothree.model.control.wishlist.server.logic;

import com.echothree.control.user.wishlist.common.spec.WishlistTypePriorityUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.wishlist.common.exception.DuplicateWishlistTypePriorityNameException;
import com.echothree.model.control.wishlist.common.exception.UnknownDefaultWishlistTypeException;
import com.echothree.model.control.wishlist.common.exception.UnknownDefaultWishlistTypePriorityException;
import com.echothree.model.control.wishlist.common.exception.UnknownWishlistTypePriorityNameException;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriority;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;

public class WishlistTypePriorityLogic
        extends BaseLogic {

    private WishlistTypePriorityLogic() {
        super();
    }

    private static class WishlistTypePriorityLogicHolder {
        static WishlistTypePriorityLogic instance = new WishlistTypePriorityLogic();
    }

    public static WishlistTypePriorityLogic getInstance() {
        return WishlistTypePriorityLogic.WishlistTypePriorityLogicHolder.instance;
    }

    public WishlistTypePriority createWishlistTypePriority(final ExecutionErrorAccumulator eea, final String wishlistTypeName, final String wishlistTypePriorityName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var wishlistType = WishlistTypeLogic.getInstance().getWishlistTypeByName(eea, wishlistTypeName);
        WishlistTypePriority wishlistTypePriority = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            wishlistTypePriority = createWishlistTypePriority(eea, wishlistType, wishlistTypePriorityName, isDefault, sortOrder, language, description, createdBy);
        }

        return wishlistTypePriority;
    }

    public WishlistTypePriority createWishlistTypePriority(final ExecutionErrorAccumulator eea, final WishlistType wishlistType, final String wishlistTypePriorityName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var wishlistTypePriority = wishlistControl.getWishlistTypePriorityByName(wishlistType, wishlistTypePriorityName);

        if(wishlistTypePriority == null) {
            wishlistTypePriority = wishlistControl.createWishlistTypePriority(wishlistType, wishlistTypePriorityName, isDefault, sortOrder, createdBy);

            if(description != null) {
                wishlistControl.createWishlistTypePriorityDescription(wishlistTypePriority, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateWishlistTypePriorityNameException.class, eea, ExecutionErrors.DuplicateWishlistTypePriorityName.name(),
                    wishlistType.getLastDetail().getWishlistTypeName(), wishlistTypePriorityName);
        }

        return wishlistTypePriority;
    }

    public WishlistTypePriority getWishlistTypePriorityByName(final ExecutionErrorAccumulator eea, final WishlistType wishlistType, final String wishlistTypePriorityName,
            final EntityPermission entityPermission) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var wishlistTypePriority = wishlistControl.getWishlistTypePriorityByName(wishlistType, wishlistTypePriorityName, entityPermission);

        if(wishlistTypePriority == null) {
            handleExecutionError(UnknownWishlistTypePriorityNameException.class, eea, ExecutionErrors.UnknownWishlistTypePriorityName.name(),
                    wishlistType.getLastDetail().getWishlistTypeName(), wishlistTypePriorityName);
        }

        return wishlistTypePriority;
    }

    public WishlistTypePriority getWishlistTypePriorityByName(final ExecutionErrorAccumulator eea, final WishlistType wishlistType, final String wishlistTypePriorityName) {
        return getWishlistTypePriorityByName(eea, wishlistType, wishlistTypePriorityName, EntityPermission.READ_ONLY);
    }

    public WishlistTypePriority getWishlistTypePriorityByNameForUpdate(final ExecutionErrorAccumulator eea, final WishlistType wishlistType, final String wishlistTypePriorityName) {
        return getWishlistTypePriorityByName(eea, wishlistType, wishlistTypePriorityName, EntityPermission.READ_WRITE);
    }

    public WishlistTypePriority getWishlistTypePriorityByName(final ExecutionErrorAccumulator eea, final String wishlistTypeName, final String wishlistTypePriorityName,
            final EntityPermission entityPermission) {
        var wishlistType = WishlistTypeLogic.getInstance().getWishlistTypeByName(eea, wishlistTypeName);
        WishlistTypePriority wishlistTypePriority = null;

        if(!eea.hasExecutionErrors()) {
            wishlistTypePriority = getWishlistTypePriorityByName(eea, wishlistType, wishlistTypePriorityName, entityPermission);
        }

        return wishlistTypePriority;
    }

    public WishlistTypePriority getWishlistTypePriorityByName(final ExecutionErrorAccumulator eea, final String wishlistTypeName, final String wishlistTypePriorityName) {
        return getWishlistTypePriorityByName(eea, wishlistTypeName, wishlistTypePriorityName, EntityPermission.READ_ONLY);
    }

    public WishlistTypePriority getWishlistTypePriorityByNameForUpdate(final ExecutionErrorAccumulator eea, final String wishlistTypeName, final String wishlistTypePriorityName) {
        return getWishlistTypePriorityByName(eea, wishlistTypeName, wishlistTypePriorityName, EntityPermission.READ_WRITE);
    }

    public WishlistTypePriority getWishlistTypePriorityByUniversalSpec(final ExecutionErrorAccumulator eea, final WishlistTypePriorityUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var wishlistTypeName = universalSpec.getWishlistTypeName();
        var wishlistTypePriorityName = universalSpec.getWishlistTypePriorityName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(wishlistTypeName, wishlistTypePriorityName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        WishlistTypePriority wishlistTypePriority = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            WishlistType wishlistType = null;

            if(wishlistTypeName == null) {
                if(allowDefault) {
                    wishlistType = wishlistControl.getDefaultWishlistType();

                    if(wishlistType == null) {
                        handleExecutionError(UnknownDefaultWishlistTypeException.class, eea, ExecutionErrors.UnknownDefaultWishlistType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                wishlistType = WishlistTypeLogic.getInstance().getWishlistTypeByName(eea, wishlistTypeName);
            }

            if(!eea.hasExecutionErrors()) {
                if(wishlistTypePriorityName == null) {
                    if(allowDefault) {
                        wishlistTypePriority = wishlistControl.getDefaultWishlistTypePriority(wishlistType, entityPermission);

                        if(wishlistTypePriority == null) {
                            handleExecutionError(UnknownDefaultWishlistTypePriorityException.class, eea, ExecutionErrors.UnknownDefaultWishlistTypePriority.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    wishlistTypePriority = getWishlistTypePriorityByName(eea, wishlistType, wishlistTypePriorityName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHOTHREE.name(), EntityTypes.WishlistTypePriority.name());

            if(!eea.hasExecutionErrors()) {
                wishlistTypePriority = wishlistControl.getWishlistTypePriorityByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return wishlistTypePriority;
    }

    public WishlistTypePriority getWishlistTypePriorityByUniversalSpec(final ExecutionErrorAccumulator eea, final WishlistTypePriorityUniversalSpec universalSpec,
            boolean allowDefault) {
        return getWishlistTypePriorityByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public WishlistTypePriority getWishlistTypePriorityByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final WishlistTypePriorityUniversalSpec universalSpec,
            boolean allowDefault) {
        return getWishlistTypePriorityByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteWishlistTypePriority(final ExecutionErrorAccumulator eea, final WishlistTypePriority wishlistTypePriority,
            final BasePK deletedBy) {
        var wishlistControl = Session.getModelController(WishlistControl.class);

        wishlistControl.deleteWishlistTypePriority(wishlistTypePriority, deletedBy);
    }

}
