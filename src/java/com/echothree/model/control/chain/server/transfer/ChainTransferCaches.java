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
            chainKindTransferCache = new ChainKindTransferCache();

        return chainKindTransferCache;
    }

    public ChainKindDescriptionTransferCache getChainKindDescriptionTransferCache() {
        if(chainKindDescriptionTransferCache == null)
            chainKindDescriptionTransferCache = new ChainKindDescriptionTransferCache();

        return chainKindDescriptionTransferCache;
    }

    public ChainTypeTransferCache getChainTypeTransferCache() {
        if(chainTypeTransferCache == null)
            chainTypeTransferCache = new ChainTypeTransferCache();

        return chainTypeTransferCache;
    }

    public ChainTypeDescriptionTransferCache getChainTypeDescriptionTransferCache() {
        if(chainTypeDescriptionTransferCache == null)
            chainTypeDescriptionTransferCache = new ChainTypeDescriptionTransferCache();

        return chainTypeDescriptionTransferCache;
    }

    public ChainTransferCache getChainTransferCache() {
        if(chainTransferCache == null)
            chainTransferCache = new ChainTransferCache();
        
        return chainTransferCache;
    }
    
    public ChainDescriptionTransferCache getChainDescriptionTransferCache() {
        if(chainDescriptionTransferCache == null)
            chainDescriptionTransferCache = new ChainDescriptionTransferCache();
        
        return chainDescriptionTransferCache;
    }
    
    public ChainEntityRoleTypeTransferCache getChainEntityRoleTypeTransferCache() {
        if(chainEntityRoleTypeTransferCache == null)
            chainEntityRoleTypeTransferCache = new ChainEntityRoleTypeTransferCache();
        
        return chainEntityRoleTypeTransferCache;
    }
    
    public ChainEntityRoleTypeDescriptionTransferCache getChainEntityRoleTypeDescriptionTransferCache() {
        if(chainEntityRoleTypeDescriptionTransferCache == null)
            chainEntityRoleTypeDescriptionTransferCache = new ChainEntityRoleTypeDescriptionTransferCache();

        return chainEntityRoleTypeDescriptionTransferCache;
    }

    public ChainInstanceTransferCache getChainInstanceTransferCache() {
        if(chainInstanceTransferCache == null)
            chainInstanceTransferCache = new ChainInstanceTransferCache();
        
        return chainInstanceTransferCache;
    }
    
    public ChainInstanceEntityRoleTransferCache getChainInstanceEntityRoleTransferCache() {
        if(chainInstanceEntityRoleTransferCache == null)
            chainInstanceEntityRoleTransferCache = new ChainInstanceEntityRoleTransferCache();
        
        return chainInstanceEntityRoleTransferCache;
    }
    
    public ChainActionTypeTransferCache getChainActionTypeTransferCache() {
        if(chainActionTypeTransferCache == null)
            chainActionTypeTransferCache = new ChainActionTypeTransferCache();

        return chainActionTypeTransferCache;
    }

    public ChainActionTypeDescriptionTransferCache getChainActionTypeDescriptionTransferCache() {
        if(chainActionTypeDescriptionTransferCache == null)
            chainActionTypeDescriptionTransferCache = new ChainActionTypeDescriptionTransferCache();

        return chainActionTypeDescriptionTransferCache;
    }

    public ChainActionTypeUseTransferCache getChainActionTypeUseTransferCache() {
        if(chainActionTypeUseTransferCache == null)
            chainActionTypeUseTransferCache = new ChainActionTypeUseTransferCache();
        
        return chainActionTypeUseTransferCache;
    }
    
        public ChainActionSetDescriptionTransferCache getChainActionSetDescriptionTransferCache() {
        if(chainActionSetDescriptionTransferCache == null)
            chainActionSetDescriptionTransferCache = new ChainActionSetDescriptionTransferCache();
        
        return chainActionSetDescriptionTransferCache;
    }
    
    public ChainActionDescriptionTransferCache getChainActionDescriptionTransferCache() {
        if(chainActionDescriptionTransferCache == null)
            chainActionDescriptionTransferCache = new ChainActionDescriptionTransferCache();
        
        return chainActionDescriptionTransferCache;
    }
    
    public ChainActionSetTransferCache getChainActionSetTransferCache() {
        if(chainActionSetTransferCache == null)
            chainActionSetTransferCache = new ChainActionSetTransferCache();
        
        return chainActionSetTransferCache;
    }
    
    public ChainActionTransferCache getChainActionTransferCache() {
        if(chainActionTransferCache == null)
            chainActionTransferCache = new ChainActionTransferCache();
        
        return chainActionTransferCache;
    }
    
    public ChainInstanceStatusTransferCache getChainInstanceStatusTransferCache() {
        if(chainInstanceStatusTransferCache == null)
            chainInstanceStatusTransferCache = new ChainInstanceStatusTransferCache();
        
        return chainInstanceStatusTransferCache;
    }
    
    public ChainActionLetterTransferCache getChainActionLetterTransferCache() {
        if(chainActionLetterTransferCache == null)
            chainActionLetterTransferCache = new ChainActionLetterTransferCache();
        
        return chainActionLetterTransferCache;
    }
    
    public ChainActionSurveyTransferCache getChainActionSurveyTransferCache() {
        if(chainActionSurveyTransferCache == null)
            chainActionSurveyTransferCache = new ChainActionSurveyTransferCache();
        
        return chainActionSurveyTransferCache;
    }
    
    public ChainActionChainActionSetTransferCache getChainActionChainActionSetTransferCache() {
        if(chainActionChainActionSetTransferCache == null)
            chainActionChainActionSetTransferCache = new ChainActionChainActionSetTransferCache();
        
        return chainActionChainActionSetTransferCache;
    }
    
}
