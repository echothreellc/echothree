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

package com.echothree.model.control.chain.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;

public class ChainTransferCaches
        extends BaseTransferCaches {
    
    protected ChainKindTransferCache chainKindTransferCache;
    protected ChainKindDescriptionTransferCache chainKindDescriptionTransferCache;
    protected ChainTypeTransferCache chainTypeTransferCache;
    protected ChainTypeDescriptionTransferCache chainTypeDescriptionTransferCache;
    protected ChainTransferCache chainTransferCache;
    protected ChainDescriptionTransferCache chainDescriptionTransferCache;
    protected ChainEntityRoleTypeTransferCache chainEntityRoleTypeTransferCache;
    protected ChainEntityRoleTypeDescriptionTransferCache chainEntityRoleTypeDescriptionTransferCache;
    protected ChainInstanceTransferCache chainInstanceTransferCache;
    protected ChainInstanceEntityRoleTransferCache chainInstanceEntityRoleTransferCache;
    protected ChainActionTypeTransferCache chainActionTypeTransferCache;
    protected ChainActionTypeDescriptionTransferCache chainActionTypeDescriptionTransferCache;
    protected ChainActionTypeUseTransferCache chainActionTypeUseTransferCache;
    protected ChainActionSetDescriptionTransferCache chainActionSetDescriptionTransferCache;
    protected ChainActionDescriptionTransferCache chainActionDescriptionTransferCache;
    protected ChainActionSetTransferCache chainActionSetTransferCache;
    protected ChainActionTransferCache chainActionTransferCache;
    protected ChainInstanceStatusTransferCache chainInstanceStatusTransferCache;
    protected ChainActionLetterTransferCache chainActionLetterTransferCache;
    protected ChainActionSurveyTransferCache chainActionSurveyTransferCache;
    protected ChainActionChainActionSetTransferCache chainActionChainActionSetTransferCache;
    
    /** Creates a new instance of ChainTransferCaches */
    public ChainTransferCaches() {
        super();
    }
    
    public ChainKindTransferCache getChainKindTransferCache() {
        if(chainKindTransferCache == null)
            chainKindTransferCache = CDI.current().select(ChainKindTransferCache.class).get();

        return chainKindTransferCache;
    }

    public ChainKindDescriptionTransferCache getChainKindDescriptionTransferCache() {
        if(chainKindDescriptionTransferCache == null)
            chainKindDescriptionTransferCache = CDI.current().select(ChainKindDescriptionTransferCache.class).get();

        return chainKindDescriptionTransferCache;
    }

    public ChainTypeTransferCache getChainTypeTransferCache() {
        if(chainTypeTransferCache == null)
            chainTypeTransferCache = CDI.current().select(ChainTypeTransferCache.class).get();

        return chainTypeTransferCache;
    }

    public ChainTypeDescriptionTransferCache getChainTypeDescriptionTransferCache() {
        if(chainTypeDescriptionTransferCache == null)
            chainTypeDescriptionTransferCache = CDI.current().select(ChainTypeDescriptionTransferCache.class).get();

        return chainTypeDescriptionTransferCache;
    }

    public ChainTransferCache getChainTransferCache() {
        if(chainTransferCache == null)
            chainTransferCache = CDI.current().select(ChainTransferCache.class).get();
        
        return chainTransferCache;
    }
    
    public ChainDescriptionTransferCache getChainDescriptionTransferCache() {
        if(chainDescriptionTransferCache == null)
            chainDescriptionTransferCache = CDI.current().select(ChainDescriptionTransferCache.class).get();
        
        return chainDescriptionTransferCache;
    }
    
    public ChainEntityRoleTypeTransferCache getChainEntityRoleTypeTransferCache() {
        if(chainEntityRoleTypeTransferCache == null)
            chainEntityRoleTypeTransferCache = CDI.current().select(ChainEntityRoleTypeTransferCache.class).get();
        
        return chainEntityRoleTypeTransferCache;
    }
    
    public ChainEntityRoleTypeDescriptionTransferCache getChainEntityRoleTypeDescriptionTransferCache() {
        if(chainEntityRoleTypeDescriptionTransferCache == null)
            chainEntityRoleTypeDescriptionTransferCache = CDI.current().select(ChainEntityRoleTypeDescriptionTransferCache.class).get();

        return chainEntityRoleTypeDescriptionTransferCache;
    }

    public ChainInstanceTransferCache getChainInstanceTransferCache() {
        if(chainInstanceTransferCache == null)
            chainInstanceTransferCache = CDI.current().select(ChainInstanceTransferCache.class).get();
        
        return chainInstanceTransferCache;
    }
    
    public ChainInstanceEntityRoleTransferCache getChainInstanceEntityRoleTransferCache() {
        if(chainInstanceEntityRoleTransferCache == null)
            chainInstanceEntityRoleTransferCache = CDI.current().select(ChainInstanceEntityRoleTransferCache.class).get();
        
        return chainInstanceEntityRoleTransferCache;
    }
    
    public ChainActionTypeTransferCache getChainActionTypeTransferCache() {
        if(chainActionTypeTransferCache == null)
            chainActionTypeTransferCache = CDI.current().select(ChainActionTypeTransferCache.class).get();

        return chainActionTypeTransferCache;
    }

    public ChainActionTypeDescriptionTransferCache getChainActionTypeDescriptionTransferCache() {
        if(chainActionTypeDescriptionTransferCache == null)
            chainActionTypeDescriptionTransferCache = CDI.current().select(ChainActionTypeDescriptionTransferCache.class).get();

        return chainActionTypeDescriptionTransferCache;
    }

    public ChainActionTypeUseTransferCache getChainActionTypeUseTransferCache() {
        if(chainActionTypeUseTransferCache == null)
            chainActionTypeUseTransferCache = CDI.current().select(ChainActionTypeUseTransferCache.class).get();
        
        return chainActionTypeUseTransferCache;
    }
    
        public ChainActionSetDescriptionTransferCache getChainActionSetDescriptionTransferCache() {
        if(chainActionSetDescriptionTransferCache == null)
            chainActionSetDescriptionTransferCache = CDI.current().select(ChainActionSetDescriptionTransferCache.class).get();
        
        return chainActionSetDescriptionTransferCache;
    }
    
    public ChainActionDescriptionTransferCache getChainActionDescriptionTransferCache() {
        if(chainActionDescriptionTransferCache == null)
            chainActionDescriptionTransferCache = CDI.current().select(ChainActionDescriptionTransferCache.class).get();
        
        return chainActionDescriptionTransferCache;
    }
    
    public ChainActionSetTransferCache getChainActionSetTransferCache() {
        if(chainActionSetTransferCache == null)
            chainActionSetTransferCache = CDI.current().select(ChainActionSetTransferCache.class).get();
        
        return chainActionSetTransferCache;
    }
    
    public ChainActionTransferCache getChainActionTransferCache() {
        if(chainActionTransferCache == null)
            chainActionTransferCache = CDI.current().select(ChainActionTransferCache.class).get();
        
        return chainActionTransferCache;
    }
    
    public ChainInstanceStatusTransferCache getChainInstanceStatusTransferCache() {
        if(chainInstanceStatusTransferCache == null)
            chainInstanceStatusTransferCache = CDI.current().select(ChainInstanceStatusTransferCache.class).get();
        
        return chainInstanceStatusTransferCache;
    }
    
    public ChainActionLetterTransferCache getChainActionLetterTransferCache() {
        if(chainActionLetterTransferCache == null)
            chainActionLetterTransferCache = CDI.current().select(ChainActionLetterTransferCache.class).get();
        
        return chainActionLetterTransferCache;
    }
    
    public ChainActionSurveyTransferCache getChainActionSurveyTransferCache() {
        if(chainActionSurveyTransferCache == null)
            chainActionSurveyTransferCache = CDI.current().select(ChainActionSurveyTransferCache.class).get();
        
        return chainActionSurveyTransferCache;
    }
    
    public ChainActionChainActionSetTransferCache getChainActionChainActionSetTransferCache() {
        if(chainActionChainActionSetTransferCache == null)
            chainActionChainActionSetTransferCache = CDI.current().select(ChainActionChainActionSetTransferCache.class).get();
        
        return chainActionChainActionSetTransferCache;
    }
    
}
