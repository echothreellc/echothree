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

import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class RatingTransferCaches
        extends BaseTransferCaches {
    
    protected RatingControl ratingControl;
    
    protected RatingTransferCache ratingTransferCache;
    protected RatingTypeTransferCache ratingTypeTransferCache;
    protected RatingTypeDescriptionTransferCache ratingTypeDescriptionTransferCache;
    protected RatingTypeListItemTransferCache ratingTypeListItemTransferCache;
    protected RatingTypeListItemDescriptionTransferCache ratingTypeListItemDescriptionTransferCache;
    
    /** Creates a new instance of RatingTransferCaches */
    public RatingTransferCaches(RatingControl ratingControl) {
        super();
        
        this.ratingControl = ratingControl;
    }
    
    public RatingTransferCache getRatingTransferCache() {
        if(ratingTransferCache == null)
            ratingTransferCache = new RatingTransferCache(ratingControl);
        
        return ratingTransferCache;
    }
    
    public RatingTypeTransferCache getRatingTypeTransferCache() {
        if(ratingTypeTransferCache == null)
            ratingTypeTransferCache = new RatingTypeTransferCache(ratingControl);
        
        return ratingTypeTransferCache;
    }
    
    public RatingTypeDescriptionTransferCache getRatingTypeDescriptionTransferCache() {
        if(ratingTypeDescriptionTransferCache == null)
            ratingTypeDescriptionTransferCache = new RatingTypeDescriptionTransferCache(ratingControl);
        
        return ratingTypeDescriptionTransferCache;
    }
    
    public RatingTypeListItemTransferCache getRatingTypeListItemTransferCache() {
        if(ratingTypeListItemTransferCache == null)
            ratingTypeListItemTransferCache = new RatingTypeListItemTransferCache(ratingControl);
        
        return ratingTypeListItemTransferCache;
    }
    
    public RatingTypeListItemDescriptionTransferCache getRatingTypeListItemDescriptionTransferCache() {
        if(ratingTypeListItemDescriptionTransferCache == null)
            ratingTypeListItemDescriptionTransferCache = new RatingTypeListItemDescriptionTransferCache(ratingControl);
        
        return ratingTypeListItemDescriptionTransferCache;
    }
    
}
