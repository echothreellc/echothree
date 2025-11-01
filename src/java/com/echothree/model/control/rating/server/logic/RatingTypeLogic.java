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

package com.echothree.model.control.rating.server.logic;

import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.rating.common.exception.UnknownRatingTypeNameException;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class RatingTypeLogic
        extends BaseLogic {

    protected RatingTypeLogic() {
        super();
    }

    public static RatingTypeLogic getInstance() {
        return CDI.current().select(RatingTypeLogic.class).get();
    }
    
    public RatingType getRatingTypeByName(final ExecutionErrorAccumulator eea, final EntityType entityType, final String ratingTypeName) {
        var ratingControl = Session.getModelController(RatingControl.class);
        var ratingType = ratingControl.getRatingTypeByName(entityType, ratingTypeName);

        if(ratingType == null) {
            var entityTypeDetail = entityType.getLastDetail();
            
            handleExecutionError(UnknownRatingTypeNameException.class, eea, ExecutionErrors.UnknownRatingTypeName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(), ratingTypeName);
        }

        return ratingType;
    }

    public RatingType getRatingTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName, final String entityTypeName, final String ratingTypeName) {
        var entityType = EntityTypeLogic.getInstance().getEntityTypeByName(eea, componentVendorName, entityTypeName);
        RatingType ratingType = null;
        
        if(!hasExecutionErrors(eea)) {
            ratingType = getRatingTypeByName(eea, entityType, ratingTypeName);
        }
        
        return ratingType;
    }
    
}
