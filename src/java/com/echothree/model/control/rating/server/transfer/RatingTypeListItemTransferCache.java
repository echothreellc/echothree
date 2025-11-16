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

import com.echothree.model.control.rating.common.transfer.RatingTypeListItemTransfer;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RatingTypeListItemTransferCache
        extends BaseRatingTransferCache<RatingTypeListItem, RatingTypeListItemTransfer> {

    RatingControl ratingControl = Session.getModelController(RatingControl.class);

    /** Creates a new instance of RatingTypeListItemTransferCache */
    protected RatingTypeListItemTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public RatingTypeListItemTransfer getRatingTypeListItemTransfer(UserVisit userVisit, RatingTypeListItem ratingTypeListItem) {
        var ratingTypeListItemTransfer = get(ratingTypeListItem);
        
        if(ratingTypeListItemTransfer == null) {
            var ratingTypeListItemDetail = ratingTypeListItem.getLastDetail();
            var ratingType = ratingControl.getRatingTypeTransfer(userVisit, ratingTypeListItemDetail.getRatingType());
            var ratingTypeListItemName = ratingTypeListItemDetail.getRatingTypeListItemName();
            var isDefault = ratingTypeListItemDetail.getIsDefault();
            var sortOrder = ratingTypeListItemDetail.getSortOrder();
            var description = ratingControl.getBestRatingTypeListItemDescription(ratingTypeListItem, getLanguage(userVisit));
            
            ratingTypeListItemTransfer = new RatingTypeListItemTransfer(ratingType, ratingTypeListItemName, isDefault, sortOrder, description);
            put(userVisit, ratingTypeListItem, ratingTypeListItemTransfer);
        }
        
        return ratingTypeListItemTransfer;
    }
    
}
