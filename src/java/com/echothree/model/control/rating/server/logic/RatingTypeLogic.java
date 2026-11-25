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

import com.echothree.control.user.rating.common.spec.RatingTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.rating.common.exception.UnknownRatingTypeNameException;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RatingTypeLogic
        extends BaseLogic {

    @Inject
    RatingControl ratingControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    EntityTypeLogic entityTypeLogic;

    protected RatingTypeLogic() {
        super();
    }

    public RatingType getRatingTypeByName(final ExecutionErrorAccumulator eea, final EntityType entityType, final String ratingTypeName,
            final EntityPermission entityPermission) {
        var ratingType = ratingControl.getRatingTypeByName(entityType, ratingTypeName, entityPermission);

        if(ratingType == null) {
            var entityTypeDetail = entityType.getLastDetail();

            handleExecutionError(UnknownRatingTypeNameException.class, eea, ExecutionErrors.UnknownRatingTypeName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(), ratingTypeName);
        }

        return ratingType;
    }

    public RatingType getRatingTypeByName(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String ratingTypeName) {
        return getRatingTypeByName(eea, entityType, ratingTypeName, EntityPermission.READ_ONLY);
    }

    public RatingType getRatingTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String ratingTypeName) {
        return getRatingTypeByName(eea, entityType, ratingTypeName, EntityPermission.READ_WRITE);
    }

    public RatingType getRatingTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String ratingTypeName, final EntityPermission entityPermission) {
        var entityType = entityTypeLogic.getEntityTypeByName(eea, componentVendorName, entityTypeName);
        RatingType ratingType = null;

        if(!hasExecutionErrors(eea)) {
            ratingType = getRatingTypeByName(eea, entityType, ratingTypeName, entityPermission);
        }

        return ratingType;
    }

    public RatingType getRatingTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String ratingTypeName) {
        return getRatingTypeByName(eea, componentVendorName, entityTypeName, ratingTypeName, EntityPermission.READ_ONLY);
    }

    public RatingType getRatingTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String ratingTypeName) {
        return getRatingTypeByName(eea, componentVendorName, entityTypeName, ratingTypeName, EntityPermission.READ_WRITE);
    }

    public RatingType getRatingTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final RatingTypeUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        RatingType ratingType = null;
        var componentVendorName = universalSpec.getComponentVendorName();
        var entityTypeName = universalSpec.getEntityTypeName();
        var ratingTypeName = universalSpec.getRatingTypeName();
        var nameParameterCount = (componentVendorName == null ? 0 : 1) + (entityTypeName == null ? 0 : 1)
                + (ratingTypeName == null ? 0 : 1);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(nameParameterCount == 3 && possibleEntitySpecs == 0) {
            ratingType = getRatingTypeByName(eea, componentVendorName, entityTypeName, ratingTypeName, entityPermission);
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.RatingType.name());

            if(!hasExecutionErrors(eea)) {
                ratingType = ratingControl.getRatingTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return ratingType;
    }

    public RatingType getRatingTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final RatingTypeUniversalSpec universalSpec) {
        return getRatingTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public RatingType getRatingTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final RatingTypeUniversalSpec universalSpec) {
        return getRatingTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
