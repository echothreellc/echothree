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

import com.echothree.model.control.rating.common.exception.UnknownRatingTypeListItemNameException;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class RatingTypeListItemLogic
        extends BaseLogic {

    protected RatingTypeListItemLogic() {
        super();
    }

    public static RatingTypeListItemLogic getInstance() {
        return CDI.current().select(RatingTypeListItemLogic.class).get();
    }
    
    public RatingTypeListItem getRatingTypeListItemByName(final ExecutionErrorAccumulator eea, final RatingType ratingType, final String ratingTypeListItemName) {
        var ratingControl = Session.getModelController(RatingControl.class);
        var ratingTypeListItem = ratingControl.getRatingTypeListItemByName(ratingType, ratingTypeListItemName);

        if(ratingTypeListItem == null) {
            var ratingTypeDetail = ratingType.getLastDetail();
            var entityTypeDetail = ratingTypeDetail.getEntityType().getLastDetail();
            
            handleExecutionError(UnknownRatingTypeListItemNameException.class, eea, ExecutionErrors.UnknownRatingTypeListItemName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                    ratingTypeDetail.getRatingTypeName(), ratingTypeListItemName);
        }

        return ratingTypeListItem;
    }

    public RatingTypeListItem getRatingTypeListItemByName(final ExecutionErrorAccumulator eea, final String componentVendorName, final String entityTypeName,
            final String ratingTypeName, final String ratingTypeListItemName) {
        var ratingType = RatingTypeLogic.getInstance().getRatingTypeByName(eea, componentVendorName, entityTypeName, ratingTypeName);
        RatingTypeListItem ratingTypeListItem = null;
        
        if(!hasExecutionErrors(eea)) {
            ratingTypeListItem = getRatingTypeListItemByName(eea, ratingType, ratingTypeListItemName);
        }
        
        return ratingTypeListItem;
    }
    
}
