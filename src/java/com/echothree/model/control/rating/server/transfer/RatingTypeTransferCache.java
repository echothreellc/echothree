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

package com.echothree.model.control.rating.server.transfer;

import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.rating.common.transfer.RatingTypeTransfer;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class RatingTypeTransferCache
        extends BaseRatingTransferCache<RatingType, RatingTypeTransfer> {
    
    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    
    /** Creates a new instance of RatingTypeTransferCache */
    public RatingTypeTransferCache(UserVisit userVisit, RatingControl ratingControl) {
        super(userVisit, ratingControl);
        
        setIncludeEntityInstance(true);
    }
    
    public RatingTypeTransfer getRatingTypeTransfer(RatingType ratingType) {
        var ratingTypeTransfer = get(ratingType);
        
        if(ratingTypeTransfer == null) {
            var ratingTypeDetail = ratingType.getLastDetail();
            var entityTypeTransfer = entityTypeControl.getEntityTypeTransfer(userVisit, ratingTypeDetail.getEntityType());
            var ratingTypeName = ratingTypeDetail.getRatingTypeName();
            var ratingSequence = ratingTypeDetail.getRatingSequence();
            var ratingSequenceTransfer = ratingSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, ratingSequence);
            var sortOrder = ratingTypeDetail.getSortOrder();
            var description = ratingControl.getBestRatingTypeDescription(ratingType, getLanguage(userVisit));
            
            ratingTypeTransfer = new RatingTypeTransfer(entityTypeTransfer, ratingTypeName, ratingSequenceTransfer, sortOrder, description);
            put(userVisit, ratingType, ratingTypeTransfer);
        }
        
        return ratingTypeTransfer;
    }
    
}
