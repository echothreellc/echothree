// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.chain.server.ChainControl;
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
    public ChainTransferCaches(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit);
        
        this.chainControl = chainControl;
    }
    
    public ChainKindTransferCache getChainKindTransferCache() {
        if(chainKindTransferCache == null)
            chainKindTransferCache = new ChainKindTransferCache(userVisit, chainControl);

        return chainKindTransferCache;
    }

    public ChainKindDescriptionTransferCache getChainKindDescriptionTransferCache() {
        if(chainKindDescriptionTransferCache == null)
            chainKindDescriptionTransferCache = new ChainKindDescriptionTransferCache(userVisit, chainControl);

        return chainKindDescriptionTransferCache;
    }

    public ChainTypeTransferCache getChainTypeTransferCache() {
        if(chainTypeTransferCache == null)
            chainTypeTransferCache = new ChainTypeTransferCache(userVisit, chainControl);

        return chainTypeTransferCache;
    }

    public ChainTypeDescriptionTransferCache getChainTypeDescriptionTransferCache() {
        if(chainTypeDescriptionTransferCache == null)
            chainTypeDescriptionTransferCache = new ChainTypeDescriptionTransferCache(userVisit, chainControl);

        return chainTypeDescriptionTransferCache;
    }

    public ChainTransferCache getChainTransferCache() {
        if(chainTransferCache == null)
            chainTransferCache = new ChainTransferCache(userVisit, chainControl);
        
        return chainTransferCache;
    }
    
    public ChainDescriptionTransferCache getChainDescriptionTransferCache() {
        if(chainDescriptionTransferCache == null)
            chainDescriptionTransferCache = new ChainDescriptionTransferCache(userVisit, chainControl);
        
        return chainDescriptionTransferCache;
    }
    
    public ChainEntityRoleTypeTransferCache getChainEntityRoleTypeTransferCache() {
        if(chainEntityRoleTypeTransferCache == null)
            chainEntityRoleTypeTransferCache = new ChainEntityRoleTypeTransferCache(userVisit, chainControl);
        
        return chainEntityRoleTypeTransferCache;
    }
    
    public ChainEntityRoleTypeDescriptionTransferCache getChainEntityRoleTypeDescriptionTransferCache() {
        if(chainEntityRoleTypeDescriptionTransferCache == null)
            chainEntityRoleTypeDescriptionTransferCache = new ChainEntityRoleTypeDescriptionTransferCache(userVisit, chainControl);

        return chainEntityRoleTypeDescriptionTransferCache;
    }

    public ChainInstanceTransferCache getChainInstanceTransferCache() {
        if(chainInstanceTransferCache == null)
            chainInstanceTransferCache = new ChainInstanceTransferCache(userVisit, chainControl);
        
        return chainInstanceTransferCache;
    }
    
    public ChainInstanceEntityRoleTransferCache getChainInstanceEntityRoleTransferCache() {
        if(chainInstanceEntityRoleTransferCache == null)
            chainInstanceEntityRoleTransferCache = new ChainInstanceEntityRoleTransferCache(userVisit, chainControl);
        
        return chainInstanceEntityRoleTransferCache;
    }
    
    public ChainActionTypeTransferCache getChainActionTypeTransferCache() {
        if(chainActionTypeTransferCache == null)
            chainActionTypeTransferCache = new ChainActionTypeTransferCache(userVisit, chainControl);

        return chainActionTypeTransferCache;
    }

    public ChainActionTypeDescriptionTransferCache getChainActionTypeDescriptionTransferCache() {
        if(chainActionTypeDescriptionTransferCache == null)
            chainActionTypeDescriptionTransferCache = new ChainActionTypeDescriptionTransferCache(userVisit, chainControl);

        return chainActionTypeDescriptionTransferCache;
    }

    public ChainActionTypeUseTransferCache getChainActionTypeUseTransferCache() {
        if(chainActionTypeUseTransferCache == null)
            chainActionTypeUseTransferCache = new ChainActionTypeUseTransferCache(userVisit, chainControl);
        
        return chainActionTypeUseTransferCache;
    }
    
        public ChainActionSetDescriptionTransferCache getChainActionSetDescriptionTransferCache() {
        if(chainActionSetDescriptionTransferCache == null)
            chainActionSetDescriptionTransferCache = new ChainActionSetDescriptionTransferCache(userVisit, chainControl);
        
        return chainActionSetDescriptionTransferCache;
    }
    
    public ChainActionDescriptionTransferCache getChainActionDescriptionTransferCache() {
        if(chainActionDescriptionTransferCache == null)
            chainActionDescriptionTransferCache = new ChainActionDescriptionTransferCache(userVisit, chainControl);
        
        return chainActionDescriptionTransferCache;
    }
    
    public ChainActionSetTransferCache getChainActionSetTransferCache() {
        if(chainActionSetTransferCache == null)
            chainActionSetTransferCache = new ChainActionSetTransferCache(userVisit, chainControl);
        
        return chainActionSetTransferCache;
    }
    
    public ChainActionTransferCache getChainActionTransferCache() {
        if(chainActionTransferCache == null)
            chainActionTransferCache = new ChainActionTransferCache(userVisit, chainControl);
        
        return chainActionTransferCache;
    }
    
    public ChainInstanceStatusTransferCache getChainInstanceStatusTransferCache() {
        if(chainInstanceStatusTransferCache == null)
            chainInstanceStatusTransferCache = new ChainInstanceStatusTransferCache(userVisit, chainControl);
        
        return chainInstanceStatusTransferCache;
    }
    
    public ChainActionLetterTransferCache getChainActionLetterTransferCache() {
        if(chainActionLetterTransferCache == null)
            chainActionLetterTransferCache = new ChainActionLetterTransferCache(userVisit, chainControl);
        
        return chainActionLetterTransferCache;
    }
    
    public ChainActionSurveyTransferCache getChainActionSurveyTransferCache() {
        if(chainActionSurveyTransferCache == null)
            chainActionSurveyTransferCache = new ChainActionSurveyTransferCache(userVisit, chainControl);
        
        return chainActionSurveyTransferCache;
    }
    
    public ChainActionChainActionSetTransferCache getChainActionChainActionSetTransferCache() {
        if(chainActionChainActionSetTransferCache == null)
            chainActionChainActionSetTransferCache = new ChainActionChainActionSetTransferCache(userVisit, chainControl);
        
        return chainActionChainActionSetTransferCache;
    }
    
}
