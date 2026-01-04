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

package com.echothree.model.control.rating.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class RatingTransfer
        extends BaseTransfer {
    
    private String ratingName;
    private RatingTypeListItemTransfer ratingTypeListItem;
    private EntityInstanceTransfer ratedEntityInstance;
    private EntityInstanceTransfer ratedByEntityInstance;
    
    /** Creates a new instance of RatingTransfer */
    public RatingTransfer() {
        // Nothing.
    }
    
    /** Creates a new instance of RatingTransfer */
    public RatingTransfer(String ratingName, RatingTypeListItemTransfer ratingTypeListItem, EntityInstanceTransfer ratedEntityInstance,
            EntityInstanceTransfer ratedByEntityInstance) {
        this.ratingName = ratingName;
        this.ratingTypeListItem = ratingTypeListItem;
        this.ratedEntityInstance = ratedEntityInstance;
        this.ratedByEntityInstance = ratedByEntityInstance;
    }
    
    public String getRatingName() {
        return ratingName;
    }
    
    public void setRatingName(String ratingName) {
        this.ratingName = ratingName;
    }
    
    public RatingTypeListItemTransfer getRatingTypeListItem() {
        return ratingTypeListItem;
    }
    
    public void setRatingTypeListItem(RatingTypeListItemTransfer ratingTypeListItem) {
        this.ratingTypeListItem = ratingTypeListItem;
    }
    
    public EntityInstanceTransfer getRatedEntityInstance() {
        return ratedEntityInstance;
    }
    
    public void setRatedEntityInstance(EntityInstanceTransfer ratedEntityInstance) {
        this.ratedEntityInstance = ratedEntityInstance;
    }
    
    public EntityInstanceTransfer getRatedByEntityInstance() {
        return ratedByEntityInstance;
    }
    
    public void setRatedByEntityInstance(EntityInstanceTransfer ratedByEntityInstance) {
        this.ratedByEntityInstance = ratedByEntityInstance;
    }
    
}
