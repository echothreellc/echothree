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

import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class ChainTransferCaches
        extends BaseTransferCaches {
    
    protected ChainControl chainControl;
    
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
    public ChainTransferCaches(ChainControl chainControl) {
        super();
        
        this.chainControl = chainControl;
    }
    
    public ChainKindTransferCache getChainKindTransferCache() {
        if(chainKindTransferCache == null)
            chainKindTransferCache = new ChainKindTransferCache(chainControl);

        return chainKindTransferCache;
    }

    public ChainKindDescriptionTransferCache getChainKindDescriptionTransferCache() {
        if(chainKindDescriptionTransferCache == null)
            chainKindDescriptionTransferCache = new ChainKindDescriptionTransferCache(chainControl);

        return chainKindDescriptionTransferCache;
    }

    public ChainTypeTransferCache getChainTypeTransferCache() {
        if(chainTypeTransferCache == null)
            chainTypeTransferCache = new ChainTypeTransferCache(chainControl);

        return chainTypeTransferCache;
    }

    public ChainTypeDescriptionTransferCache getChainTypeDescriptionTransferCache() {
        if(chainTypeDescriptionTransferCache == null)
            chainTypeDescriptionTransferCache = new ChainTypeDescriptionTransferCache(chainControl);

        return chainTypeDescriptionTransferCache;
    }

    public ChainTransferCache getChainTransferCache() {
        if(chainTransferCache == null)
            chainTransferCache = new ChainTransferCache(chainControl);
        
        return chainTransferCache;
    }
    
    public ChainDescriptionTransferCache getChainDescriptionTransferCache() {
        if(chainDescriptionTransferCache == null)
            chainDescriptionTransferCache = new ChainDescriptionTransferCache(chainControl);
        
        return chainDescriptionTransferCache;
    }
    
    public ChainEntityRoleTypeTransferCache getChainEntityRoleTypeTransferCache() {
        if(chainEntityRoleTypeTransferCache == null)
            chainEntityRoleTypeTransferCache = new ChainEntityRoleTypeTransferCache(chainControl);
        
        return chainEntityRoleTypeTransferCache;
    }
    
    public ChainEntityRoleTypeDescriptionTransferCache getChainEntityRoleTypeDescriptionTransferCache() {
        if(chainEntityRoleTypeDescriptionTransferCache == null)
            chainEntityRoleTypeDescriptionTransferCache = new ChainEntityRoleTypeDescriptionTransferCache(chainControl);

        return chainEntityRoleTypeDescriptionTransferCache;
    }

    public ChainInstanceTransferCache getChainInstanceTransferCache() {
        if(chainInstanceTransferCache == null)
            chainInstanceTransferCache = new ChainInstanceTransferCache(chainControl);
        
        return chainInstanceTransferCache;
    }
    
    public ChainInstanceEntityRoleTransferCache getChainInstanceEntityRoleTransferCache() {
        if(chainInstanceEntityRoleTransferCache == null)
            chainInstanceEntityRoleTransferCache = new ChainInstanceEntityRoleTransferCache(chainControl);
        
        return chainInstanceEntityRoleTransferCache;
    }
    
    public ChainActionTypeTransferCache getChainActionTypeTransferCache() {
        if(chainActionTypeTransferCache == null)
            chainActionTypeTransferCache = new ChainActionTypeTransferCache(chainControl);

        return chainActionTypeTransferCache;
    }

    public ChainActionTypeDescriptionTransferCache getChainActionTypeDescriptionTransferCache() {
        if(chainActionTypeDescriptionTransferCache == null)
            chainActionTypeDescriptionTransferCache = new ChainActionTypeDescriptionTransferCache(chainControl);

        return chainActionTypeDescriptionTransferCache;
    }

    public ChainActionTypeUseTransferCache getChainActionTypeUseTransferCache() {
        if(chainActionTypeUseTransferCache == null)
            chainActionTypeUseTransferCache = new ChainActionTypeUseTransferCache(chainControl);
        
        return chainActionTypeUseTransferCache;
    }
    
        public ChainActionSetDescriptionTransferCache getChainActionSetDescriptionTransferCache() {
        if(chainActionSetDescriptionTransferCache == null)
            chainActionSetDescriptionTransferCache = new ChainActionSetDescriptionTransferCache(chainControl);
        
        return chainActionSetDescriptionTransferCache;
    }
    
    public ChainActionDescriptionTransferCache getChainActionDescriptionTransferCache() {
        if(chainActionDescriptionTransferCache == null)
            chainActionDescriptionTransferCache = new ChainActionDescriptionTransferCache(chainControl);
        
        return chainActionDescriptionTransferCache;
    }
    
    public ChainActionSetTransferCache getChainActionSetTransferCache() {
        if(chainActionSetTransferCache == null)
            chainActionSetTransferCache = new ChainActionSetTransferCache(chainControl);
        
        return chainActionSetTransferCache;
    }
    
    public ChainActionTransferCache getChainActionTransferCache() {
        if(chainActionTransferCache == null)
            chainActionTransferCache = new ChainActionTransferCache(chainControl);
        
        return chainActionTransferCache;
    }
    
    public ChainInstanceStatusTransferCache getChainInstanceStatusTransferCache() {
        if(chainInstanceStatusTransferCache == null)
            chainInstanceStatusTransferCache = new ChainInstanceStatusTransferCache(chainControl);
        
        return chainInstanceStatusTransferCache;
    }
    
    public ChainActionLetterTransferCache getChainActionLetterTransferCache() {
        if(chainActionLetterTransferCache == null)
            chainActionLetterTransferCache = new ChainActionLetterTransferCache(chainControl);
        
        return chainActionLetterTransferCache;
    }
    
    public ChainActionSurveyTransferCache getChainActionSurveyTransferCache() {
        if(chainActionSurveyTransferCache == null)
            chainActionSurveyTransferCache = new ChainActionSurveyTransferCache(chainControl);
        
        return chainActionSurveyTransferCache;
    }
    
    public ChainActionChainActionSetTransferCache getChainActionChainActionSetTransferCache() {
        if(chainActionChainActionSetTransferCache == null)
            chainActionChainActionSetTransferCache = new ChainActionChainActionSetTransferCache(chainControl);
        
        return chainActionChainActionSetTransferCache;
    }
    
}
