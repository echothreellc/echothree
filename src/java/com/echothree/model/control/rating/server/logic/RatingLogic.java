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

import com.echothree.control.user.rating.common.spec.RatingUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.rating.common.exception.UnknownRatingNameException;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.rating.server.entity.Rating;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RatingLogic
        extends BaseLogic {

    @Inject
    RatingControl ratingControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected RatingLogic() {
        super();
    }

    public Rating getRatingByName(final ExecutionErrorAccumulator eea, final String ratingName,
            final EntityPermission entityPermission) {
        var rating = ratingControl.getRatingByName(ratingName, entityPermission);

        if(rating == null) {
            handleExecutionError(UnknownRatingNameException.class, eea, ExecutionErrors.UnknownRatingName.name(), ratingName);
        }

        return rating;
    }

    public Rating getRatingByName(final ExecutionErrorAccumulator eea, final String ratingName) {
        return getRatingByName(eea, ratingName, EntityPermission.READ_ONLY);
    }

    public Rating getRatingByNameForUpdate(final ExecutionErrorAccumulator eea, final String ratingName) {
        return getRatingByName(eea, ratingName, EntityPermission.READ_WRITE);
    }

    public Rating getRatingByUniversalSpec(final ExecutionErrorAccumulator eea, final RatingUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        Rating rating = null;
        var ratingName = universalSpec.getRatingName();
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        var parameterCount = (ratingName == null ? 0 : 1) + possibleEntitySpecs;

        if(parameterCount == 1) {
            if(ratingName == null) {
                var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.Rating.name());

                if(!hasExecutionErrors(eea)) {
                    rating = ratingControl.getRatingByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                rating = getRatingByName(eea, ratingName, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return rating;
    }

    public Rating getRatingByUniversalSpec(final ExecutionErrorAccumulator eea, final RatingUniversalSpec universalSpec) {
        return getRatingByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Rating getRatingByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final RatingUniversalSpec universalSpec) {
        return getRatingByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
