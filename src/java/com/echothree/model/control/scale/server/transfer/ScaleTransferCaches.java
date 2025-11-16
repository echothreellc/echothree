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

package com.echothree.model.control.scale.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ScaleTransferCaches
        extends BaseTransferCaches {
    
    protected ScaleTypeTransferCache scaleTypeTransferCache;
    protected ScaleTypeDescriptionTransferCache scaleTypeDescriptionTransferCache;
    protected ScaleTransferCache scaleTransferCache;
    protected ScaleDescriptionTransferCache scaleDescriptionTransferCache;
    protected ScaleUseTypeTransferCache scaleUseTypeTransferCache;
    protected ScaleUseTypeDescriptionTransferCache scaleUseTypeDescriptionTransferCache;
    protected PartyScaleUseTransferCache partyScaleUseTransferCache;
    
    /** Creates a new instance of ScaleTransferCaches */
    protected ScaleTransferCaches() {
        super();
    }
    
    public ScaleTypeTransferCache getScaleTypeTransferCache() {
        if(scaleTypeTransferCache == null)
            scaleTypeTransferCache = CDI.current().select(ScaleTypeTransferCache.class).get();

        return scaleTypeTransferCache;
    }

    public ScaleTypeDescriptionTransferCache getScaleTypeDescriptionTransferCache() {
        if(scaleTypeDescriptionTransferCache == null)
            scaleTypeDescriptionTransferCache = CDI.current().select(ScaleTypeDescriptionTransferCache.class).get();

        return scaleTypeDescriptionTransferCache;
    }

    public ScaleTransferCache getScaleTransferCache() {
        if(scaleTransferCache == null)
            scaleTransferCache = CDI.current().select(ScaleTransferCache.class).get();
        
        return scaleTransferCache;
    }
    
    public ScaleDescriptionTransferCache getScaleDescriptionTransferCache() {
        if(scaleDescriptionTransferCache == null)
            scaleDescriptionTransferCache = CDI.current().select(ScaleDescriptionTransferCache.class).get();
        
        return scaleDescriptionTransferCache;
    }
    
    public ScaleUseTypeTransferCache getScaleUseTypeTransferCache() {
        if(scaleUseTypeTransferCache == null)
            scaleUseTypeTransferCache = CDI.current().select(ScaleUseTypeTransferCache.class).get();

        return scaleUseTypeTransferCache;
    }

    public ScaleUseTypeDescriptionTransferCache getScaleUseTypeDescriptionTransferCache() {
        if(scaleUseTypeDescriptionTransferCache == null)
            scaleUseTypeDescriptionTransferCache = CDI.current().select(ScaleUseTypeDescriptionTransferCache.class).get();

        return scaleUseTypeDescriptionTransferCache;
    }

    public PartyScaleUseTransferCache getPartyScaleUseTransferCache() {
        if(partyScaleUseTransferCache == null)
            partyScaleUseTransferCache = CDI.current().select(PartyScaleUseTransferCache.class).get();
        
        return partyScaleUseTransferCache;
    }
    
}
