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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;

public class RatingTransferCaches
        extends BaseTransferCaches {
    
    protected RatingTransferCache ratingTransferCache;
    protected RatingTypeTransferCache ratingTypeTransferCache;
    protected RatingTypeDescriptionTransferCache ratingTypeDescriptionTransferCache;
    protected RatingTypeListItemTransferCache ratingTypeListItemTransferCache;
    protected RatingTypeListItemDescriptionTransferCache ratingTypeListItemDescriptionTransferCache;
    
    /** Creates a new instance of RatingTransferCaches */
    public RatingTransferCaches() {
        super();
    }
    
    public RatingTransferCache getRatingTransferCache() {
        if(ratingTransferCache == null)
            ratingTransferCache = CDI.current().select(RatingTransferCache.class).get();
        
        return ratingTransferCache;
    }
    
    public RatingTypeTransferCache getRatingTypeTransferCache() {
        if(ratingTypeTransferCache == null)
            ratingTypeTransferCache = CDI.current().select(RatingTypeTransferCache.class).get();
        
        return ratingTypeTransferCache;
    }
    
    public RatingTypeDescriptionTransferCache getRatingTypeDescriptionTransferCache() {
        if(ratingTypeDescriptionTransferCache == null)
            ratingTypeDescriptionTransferCache = CDI.current().select(RatingTypeDescriptionTransferCache.class).get();
        
        return ratingTypeDescriptionTransferCache;
    }
    
    public RatingTypeListItemTransferCache getRatingTypeListItemTransferCache() {
        if(ratingTypeListItemTransferCache == null)
            ratingTypeListItemTransferCache = CDI.current().select(RatingTypeListItemTransferCache.class).get();
        
        return ratingTypeListItemTransferCache;
    }
    
    public RatingTypeListItemDescriptionTransferCache getRatingTypeListItemDescriptionTransferCache() {
        if(ratingTypeListItemDescriptionTransferCache == null)
            ratingTypeListItemDescriptionTransferCache = CDI.current().select(RatingTypeListItemDescriptionTransferCache.class).get();
        
        return ratingTypeListItemDescriptionTransferCache;
    }
    
}
