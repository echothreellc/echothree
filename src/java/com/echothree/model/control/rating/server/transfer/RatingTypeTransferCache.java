// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.rating.common.transfer.RatingTypeTransfer;
import com.echothree.model.control.rating.server.RatingControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeDetail;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class RatingTypeTransferCache
        extends BaseRatingTransferCache<RatingType, RatingTypeTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    
    /** Creates a new instance of RatingTypeTransferCache */
    public RatingTypeTransferCache(UserVisit userVisit, RatingControl ratingControl) {
        super(userVisit, ratingControl);
        
        setIncludeEntityInstance(true);
    }
    
    public RatingTypeTransfer getRatingTypeTransfer(RatingType ratingType) {
        RatingTypeTransfer ratingTypeTransfer = get(ratingType);
        
        if(ratingTypeTransfer == null) {
            RatingTypeDetail ratingTypeDetail = ratingType.getLastDetail();
            EntityTypeTransfer entityTypeTransfer = coreControl.getEntityTypeTransfer(userVisit, ratingTypeDetail.getEntityType());
            String ratingTypeName = ratingTypeDetail.getRatingTypeName();
            Sequence ratingSequence = ratingTypeDetail.getRatingSequence();
            SequenceTransfer ratingSequenceTransfer = ratingSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, ratingSequence);
            Integer sortOrder = ratingTypeDetail.getSortOrder();
            String description = ratingControl.getBestRatingTypeDescription(ratingType, getLanguage());
            
            ratingTypeTransfer = new RatingTypeTransfer(entityTypeTransfer, ratingTypeName, ratingSequenceTransfer, sortOrder, description);
            put(ratingType, ratingTypeTransfer);
        }
        
        return ratingTypeTransfer;
    }
    
}
