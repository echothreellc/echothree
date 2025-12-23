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

package com.echothree.model.control.rating.server.transfer;

import com.echothree.model.control.rating.common.transfer.RatingTypeListItemDescriptionTransfer;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.rating.server.entity.RatingTypeListItemDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RatingTypeListItemDescriptionTransferCache
        extends BaseRatingDescriptionTransferCache<RatingTypeListItemDescription, RatingTypeListItemDescriptionTransfer> {

    RatingControl ratingControl = Session.getModelController(RatingControl.class);

    /** Creates a new instance of RatingTypeListItemDescriptionTransferCache */
    protected RatingTypeListItemDescriptionTransferCache() {
        super();
    }
    
    public RatingTypeListItemDescriptionTransfer getRatingTypeListItemDescriptionTransfer(UserVisit userVisit, RatingTypeListItemDescription ratingTypeListItemDescription) {
        var ratingTypeListItemDescriptionTransfer = get(ratingTypeListItemDescription);
        
        if(ratingTypeListItemDescriptionTransfer == null) {
            var ratingTypeListItemTransfer = ratingControl.getRatingTypeListItemTransfer(userVisit, ratingTypeListItemDescription.getRatingTypeListItem());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, ratingTypeListItemDescription.getLanguage());
            
            ratingTypeListItemDescriptionTransfer = new RatingTypeListItemDescriptionTransfer(languageTransfer, ratingTypeListItemTransfer, ratingTypeListItemDescription.getDescription());
            put(userVisit, ratingTypeListItemDescription, ratingTypeListItemDescriptionTransfer);
        }
        
        return ratingTypeListItemDescriptionTransfer;
    }
    
}
