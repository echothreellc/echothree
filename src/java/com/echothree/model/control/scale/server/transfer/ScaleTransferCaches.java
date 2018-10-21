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

package com.echothree.model.control.scale.server.transfer;

import com.echothree.model.control.scale.server.ScaleControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class ScaleTransferCaches
        extends BaseTransferCaches {
    
    protected ScaleControl scaleControl;
    
    protected ScaleTypeTransferCache scaleTypeTransferCache;
    protected ScaleTypeDescriptionTransferCache scaleTypeDescriptionTransferCache;
    protected ScaleTransferCache scaleTransferCache;
    protected ScaleDescriptionTransferCache scaleDescriptionTransferCache;
    protected ScaleUseTypeTransferCache scaleUseTypeTransferCache;
    protected ScaleUseTypeDescriptionTransferCache scaleUseTypeDescriptionTransferCache;
    protected PartyScaleUseTransferCache partyScaleUseTransferCache;
    
    /** Creates a new instance of ScaleTransferCaches */
    public ScaleTransferCaches(UserVisit userVisit, ScaleControl scaleControl) {
        super(userVisit);
        
        this.scaleControl = scaleControl;
    }
    
    public ScaleTypeTransferCache getScaleTypeTransferCache() {
        if(scaleTypeTransferCache == null)
            scaleTypeTransferCache = new ScaleTypeTransferCache(userVisit, scaleControl);

        return scaleTypeTransferCache;
    }

    public ScaleTypeDescriptionTransferCache getScaleTypeDescriptionTransferCache() {
        if(scaleTypeDescriptionTransferCache == null)
            scaleTypeDescriptionTransferCache = new ScaleTypeDescriptionTransferCache(userVisit, scaleControl);

        return scaleTypeDescriptionTransferCache;
    }

    public ScaleTransferCache getScaleTransferCache() {
        if(scaleTransferCache == null)
            scaleTransferCache = new ScaleTransferCache(userVisit, scaleControl);
        
        return scaleTransferCache;
    }
    
    public ScaleDescriptionTransferCache getScaleDescriptionTransferCache() {
        if(scaleDescriptionTransferCache == null)
            scaleDescriptionTransferCache = new ScaleDescriptionTransferCache(userVisit, scaleControl);
        
        return scaleDescriptionTransferCache;
    }
    
    public ScaleUseTypeTransferCache getScaleUseTypeTransferCache() {
        if(scaleUseTypeTransferCache == null)
            scaleUseTypeTransferCache = new ScaleUseTypeTransferCache(userVisit, scaleControl);

        return scaleUseTypeTransferCache;
    }

    public ScaleUseTypeDescriptionTransferCache getScaleUseTypeDescriptionTransferCache() {
        if(scaleUseTypeDescriptionTransferCache == null)
            scaleUseTypeDescriptionTransferCache = new ScaleUseTypeDescriptionTransferCache(userVisit, scaleControl);

        return scaleUseTypeDescriptionTransferCache;
    }

    public PartyScaleUseTransferCache getPartyScaleUseTransferCache() {
        if(partyScaleUseTransferCache == null)
            partyScaleUseTransferCache = new PartyScaleUseTransferCache(userVisit, scaleControl);
        
        return partyScaleUseTransferCache;
    }
    
}
