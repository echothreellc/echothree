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

package com.echothree.model.control.wishlist.server.logic;

import com.echothree.control.user.wishlist.common.spec.WishlistPriorityUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.wishlist.common.exception.DuplicateWishlistPriorityNameException;
import com.echothree.model.control.wishlist.common.exception.UnknownDefaultWishlistPriorityException;
import com.echothree.model.control.wishlist.common.exception.UnknownDefaultWishlistTypeException;
import com.echothree.model.control.wishlist.common.exception.UnknownWishlistPriorityNameException;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class WishlistPriorityLogic
        extends BaseLogic {

    protected WishlistPriorityLogic() {
        super();
    }

    public static WishlistPriorityLogic getInstance() {
        return CDI.current().select(WishlistPriorityLogic.class).get();
    }

    public WishlistPriority createWishlistPriority(final ExecutionErrorAccumulator eea, final String wishlistTypeName, final String wishlistPriorityName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var wishlistType = WishlistTypeLogic.getInstance().getWishlistTypeByName(eea, wishlistTypeName);
        WishlistPriority wishlistPriority = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            wishlistPriority = createWishlistPriority(eea, wishlistType, wishlistPriorityName, isDefault, sortOrder, language, description, createdBy);
        }

        return wishlistPriority;
    }

    public WishlistPriority createWishlistPriority(final ExecutionErrorAccumulator eea, final WishlistType wishlistType, final String wishlistPriorityName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var wishlistPriority = wishlistControl.getWishlistPriorityByName(wishlistType, wishlistPriorityName);

        if(wishlistPriority == null) {
            wishlistPriority = wishlistControl.createWishlistPriority(wishlistType, wishlistPriorityName, isDefault, sortOrder, createdBy);

            if(description != null) {
                wishlistControl.createWishlistPriorityDescription(wishlistPriority, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateWishlistPriorityNameException.class, eea, ExecutionErrors.DuplicateWishlistPriorityName.name(),
                    wishlistType.getLastDetail().getWishlistTypeName(), wishlistPriorityName);
        }

        return wishlistPriority;
    }

    public WishlistPriority getWishlistPriorityByName(final ExecutionErrorAccumulator eea, final WishlistType wishlistType, final String wishlistPriorityName,
            final EntityPermission entityPermission) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var wishlistPriority = wishlistControl.getWishlistPriorityByName(wishlistType, wishlistPriorityName, entityPermission);

        if(wishlistPriority == null) {
            handleExecutionError(UnknownWishlistPriorityNameException.class, eea, ExecutionErrors.UnknownWishlistPriorityName.name(),
                    wishlistType.getLastDetail().getWishlistTypeName(), wishlistPriorityName);
        }

        return wishlistPriority;
    }

    public WishlistPriority getWishlistPriorityByName(final ExecutionErrorAccumulator eea, final WishlistType wishlistType, final String wishlistPriorityName) {
        return getWishlistPriorityByName(eea, wishlistType, wishlistPriorityName, EntityPermission.READ_ONLY);
    }

    public WishlistPriority getWishlistPriorityByNameForUpdate(final ExecutionErrorAccumulator eea, final WishlistType wishlistType, final String wishlistPriorityName) {
        return getWishlistPriorityByName(eea, wishlistType, wishlistPriorityName, EntityPermission.READ_WRITE);
    }

    public WishlistPriority getWishlistPriorityByName(final ExecutionErrorAccumulator eea, final String wishlistTypeName, final String wishlistPriorityName,
            final EntityPermission entityPermission) {
        var wishlistType = WishlistTypeLogic.getInstance().getWishlistTypeByName(eea, wishlistTypeName);
        WishlistPriority wishlistPriority = null;

        if(!eea.hasExecutionErrors()) {
            wishlistPriority = getWishlistPriorityByName(eea, wishlistType, wishlistPriorityName, entityPermission);
        }

        return wishlistPriority;
    }

    public WishlistPriority getWishlistPriorityByName(final ExecutionErrorAccumulator eea, final String wishlistTypeName, final String wishlistPriorityName) {
        return getWishlistPriorityByName(eea, wishlistTypeName, wishlistPriorityName, EntityPermission.READ_ONLY);
    }

    public WishlistPriority getWishlistPriorityByNameForUpdate(final ExecutionErrorAccumulator eea, final String wishlistTypeName, final String wishlistPriorityName) {
        return getWishlistPriorityByName(eea, wishlistTypeName, wishlistPriorityName, EntityPermission.READ_WRITE);
    }

    public WishlistPriority getWishlistPriorityByUniversalSpec(final ExecutionErrorAccumulator eea, final WishlistPriorityUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var wishlistTypeName = universalSpec.getWishlistTypeName();
        var wishlistPriorityName = universalSpec.getWishlistPriorityName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(wishlistTypeName, wishlistPriorityName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        WishlistPriority wishlistPriority = null;

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
                if(wishlistPriorityName == null) {
                    if(allowDefault) {
                        wishlistPriority = wishlistControl.getDefaultWishlistPriority(wishlistType, entityPermission);

                        if(wishlistPriority == null) {
                            handleExecutionError(UnknownDefaultWishlistPriorityException.class, eea, ExecutionErrors.UnknownDefaultWishlistPriority.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    wishlistPriority = getWishlistPriorityByName(eea, wishlistType, wishlistPriorityName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.WishlistPriority.name());

            if(!eea.hasExecutionErrors()) {
                wishlistPriority = wishlistControl.getWishlistPriorityByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return wishlistPriority;
    }

    public WishlistPriority getWishlistPriorityByUniversalSpec(final ExecutionErrorAccumulator eea, final WishlistPriorityUniversalSpec universalSpec,
            boolean allowDefault) {
        return getWishlistPriorityByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public WishlistPriority getWishlistPriorityByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final WishlistPriorityUniversalSpec universalSpec,
            boolean allowDefault) {
        return getWishlistPriorityByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteWishlistPriority(final ExecutionErrorAccumulator eea, final WishlistPriority wishlistPriority,
            final BasePK deletedBy) {
        var wishlistControl = Session.getModelController(WishlistControl.class);

        wishlistControl.deleteWishlistPriority(wishlistPriority, deletedBy);
    }

}
