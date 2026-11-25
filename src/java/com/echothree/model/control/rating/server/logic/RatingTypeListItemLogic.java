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

package com.echothree.model.control.rating.server.logic;

import com.echothree.control.user.rating.common.spec.RatingTypeListItemUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.rating.common.exception.UnknownDefaultRatingTypeListItemException;
import com.echothree.model.control.rating.common.exception.UnknownRatingTypeListItemNameException;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RatingTypeListItemLogic
        extends BaseLogic {

    @Inject
    RatingControl ratingControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    RatingTypeLogic ratingTypeLogic;

    protected RatingTypeListItemLogic() {
        super();
    }

    public RatingTypeListItem getRatingTypeListItemByName(final ExecutionErrorAccumulator eea, final RatingType ratingType, final String ratingTypeListItemName,
            final EntityPermission entityPermission) {
        var ratingTypeListItem = ratingControl.getRatingTypeListItemByName(ratingType, ratingTypeListItemName, entityPermission);

        if(ratingTypeListItem == null) {
            var ratingTypeDetail = ratingType.getLastDetail();
            var entityTypeDetail = ratingTypeDetail.getEntityType().getLastDetail();

            handleExecutionError(UnknownRatingTypeListItemNameException.class, eea, ExecutionErrors.UnknownRatingTypeListItemName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                    ratingTypeDetail.getRatingTypeName(), ratingTypeListItemName);
        }

        return ratingTypeListItem;
    }

    public RatingTypeListItem getRatingTypeListItemByName(final ExecutionErrorAccumulator eea, final RatingType ratingType,
            final String ratingTypeListItemName) {
        return getRatingTypeListItemByName(eea, ratingType, ratingTypeListItemName, EntityPermission.READ_ONLY);
    }

    public RatingTypeListItem getRatingTypeListItemByNameForUpdate(final ExecutionErrorAccumulator eea, final RatingType ratingType,
            final String ratingTypeListItemName) {
        return getRatingTypeListItemByName(eea, ratingType, ratingTypeListItemName, EntityPermission.READ_WRITE);
    }

    public RatingTypeListItem getRatingTypeListItemByName(final ExecutionErrorAccumulator eea, final String componentVendorName, final String entityTypeName,
            final String ratingTypeName, final String ratingTypeListItemName, final EntityPermission entityPermission) {
        var ratingType = ratingTypeLogic.getRatingTypeByName(eea, componentVendorName, entityTypeName, ratingTypeName);
        RatingTypeListItem ratingTypeListItem = null;

        if(!hasExecutionErrors(eea)) {
            ratingTypeListItem = getRatingTypeListItemByName(eea, ratingType, ratingTypeListItemName, entityPermission);
        }

        return ratingTypeListItem;
    }

    public RatingTypeListItem getRatingTypeListItemByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String ratingTypeName, final String ratingTypeListItemName) {
        return getRatingTypeListItemByName(eea, componentVendorName, entityTypeName, ratingTypeName, ratingTypeListItemName, EntityPermission.READ_ONLY);
    }

    public RatingTypeListItem getRatingTypeListItemByNameForUpdate(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String ratingTypeName, final String ratingTypeListItemName) {
        return getRatingTypeListItemByName(eea, componentVendorName, entityTypeName, ratingTypeName, ratingTypeListItemName, EntityPermission.READ_WRITE);
    }

    public RatingTypeListItem getRatingTypeListItemByUniversalSpec(final ExecutionErrorAccumulator eea,
            final RatingTypeListItemUniversalSpec universalSpec, final boolean allowDefault, final EntityPermission entityPermission) {
        RatingTypeListItem ratingTypeListItem = null;
        var componentVendorName = universalSpec.getComponentVendorName();
        var entityTypeName = universalSpec.getEntityTypeName();
        var ratingTypeName = universalSpec.getRatingTypeName();
        var ratingTypeListItemName = universalSpec.getRatingTypeListItemName();
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(componentVendorName != null && entityTypeName != null && ratingTypeName != null && possibleEntitySpecs == 0
                && (ratingTypeListItemName != null || allowDefault)) {
            var ratingType = ratingTypeLogic.getRatingTypeByName(eea, componentVendorName, entityTypeName, ratingTypeName);

            if(!hasExecutionErrors(eea)) {
                if(ratingTypeListItemName == null) {
                    ratingTypeListItem = ratingControl.getDefaultRatingTypeListItem(ratingType, entityPermission);

                    if(ratingTypeListItem == null) {
                        handleExecutionError(UnknownDefaultRatingTypeListItemException.class, eea,
                                ExecutionErrors.UnknownDefaultRatingTypeListItem.name(), componentVendorName, entityTypeName, ratingTypeName);
                    }
                } else {
                    ratingTypeListItem = getRatingTypeListItemByName(eea, ratingType, ratingTypeListItemName, entityPermission);
                }
            }
        } else if(componentVendorName == null && entityTypeName == null && ratingTypeName == null && ratingTypeListItemName == null
                && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.RatingTypeListItem.name());

            if(!hasExecutionErrors(eea)) {
                ratingTypeListItem = ratingControl.getRatingTypeListItemByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return ratingTypeListItem;
    }

    public RatingTypeListItem getRatingTypeListItemByUniversalSpec(final ExecutionErrorAccumulator eea,
            final RatingTypeListItemUniversalSpec universalSpec, final boolean allowDefault) {
        return getRatingTypeListItemByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public RatingTypeListItem getRatingTypeListItemByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final RatingTypeListItemUniversalSpec universalSpec, final boolean allowDefault) {
        return getRatingTypeListItemByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
