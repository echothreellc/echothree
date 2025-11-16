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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RatingTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    RatingTransferCache ratingTransferCache;
    
    @Inject
    RatingTypeTransferCache ratingTypeTransferCache;
    
    @Inject
    RatingTypeDescriptionTransferCache ratingTypeDescriptionTransferCache;
    
    @Inject
    RatingTypeListItemTransferCache ratingTypeListItemTransferCache;
    
    @Inject
    RatingTypeListItemDescriptionTransferCache ratingTypeListItemDescriptionTransferCache;

    /** Creates a new instance of RatingTransferCaches */
    protected RatingTransferCaches() {
        super();
    }
    
    public RatingTransferCache getRatingTransferCache() {
        return ratingTransferCache;
    }
    
    public RatingTypeTransferCache getRatingTypeTransferCache() {
        return ratingTypeTransferCache;
    }
    
    public RatingTypeDescriptionTransferCache getRatingTypeDescriptionTransferCache() {
        return ratingTypeDescriptionTransferCache;
    }
    
    public RatingTypeListItemTransferCache getRatingTypeListItemTransferCache() {
        return ratingTypeListItemTransferCache;
    }
    
    public RatingTypeListItemDescriptionTransferCache getRatingTypeListItemDescriptionTransferCache() {
        return ratingTypeListItemDescriptionTransferCache;
    }
    
}
