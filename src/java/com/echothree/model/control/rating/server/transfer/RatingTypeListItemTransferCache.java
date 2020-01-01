// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.rating.common.transfer.RatingTypeListItemTransfer;
import com.echothree.model.control.rating.common.transfer.RatingTypeTransfer;
import com.echothree.model.control.rating.server.RatingControl;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.model.data.rating.server.entity.RatingTypeListItemDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class RatingTypeListItemTransferCache
        extends BaseRatingTransferCache<RatingTypeListItem, RatingTypeListItemTransfer> {
    
    /** Creates a new instance of RatingTypeListItemTransferCache */
    public RatingTypeListItemTransferCache(UserVisit userVisit, RatingControl ratingControl) {
        super(userVisit, ratingControl);
        
        setIncludeEntityInstance(true);
    }
    
    public RatingTypeListItemTransfer getRatingTypeListItemTransfer(RatingTypeListItem ratingTypeListItem) {
        RatingTypeListItemTransfer ratingTypeListItemTransfer = get(ratingTypeListItem);
        
        if(ratingTypeListItemTransfer == null) {
            RatingTypeListItemDetail ratingTypeListItemDetail = ratingTypeListItem.getLastDetail();
            RatingTypeTransfer ratingType = ratingControl.getRatingTypeTransfer(userVisit, ratingTypeListItemDetail.getRatingType());
            String ratingTypeListItemName = ratingTypeListItemDetail.getRatingTypeListItemName();
            Boolean isDefault = ratingTypeListItemDetail.getIsDefault();
            Integer sortOrder = ratingTypeListItemDetail.getSortOrder();
            String description = ratingControl.getBestRatingTypeListItemDescription(ratingTypeListItem, getLanguage());
            
            ratingTypeListItemTransfer = new RatingTypeListItemTransfer(ratingType, ratingTypeListItemName, isDefault, sortOrder, description);
            put(ratingTypeListItem, ratingTypeListItemTransfer);
        }
        
        return ratingTypeListItemTransfer;
    }
    
}
