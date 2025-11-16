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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ChainTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    ChainKindTransferCache chainKindTransferCache;
    
    @Inject
    ChainKindDescriptionTransferCache chainKindDescriptionTransferCache;
    
    @Inject
    ChainTypeTransferCache chainTypeTransferCache;
    
    @Inject
    ChainTypeDescriptionTransferCache chainTypeDescriptionTransferCache;
    
    @Inject
    ChainTransferCache chainTransferCache;
    
    @Inject
    ChainDescriptionTransferCache chainDescriptionTransferCache;
    
    @Inject
    ChainEntityRoleTypeTransferCache chainEntityRoleTypeTransferCache;
    
    @Inject
    ChainEntityRoleTypeDescriptionTransferCache chainEntityRoleTypeDescriptionTransferCache;
    
    @Inject
    ChainInstanceTransferCache chainInstanceTransferCache;
    
    @Inject
    ChainInstanceEntityRoleTransferCache chainInstanceEntityRoleTransferCache;
    
    @Inject
    ChainActionTypeTransferCache chainActionTypeTransferCache;
    
    @Inject
    ChainActionTypeDescriptionTransferCache chainActionTypeDescriptionTransferCache;
    
    @Inject
    ChainActionTypeUseTransferCache chainActionTypeUseTransferCache;
    
    @Inject
    ChainActionSetDescriptionTransferCache chainActionSetDescriptionTransferCache;
    
    @Inject
    ChainActionDescriptionTransferCache chainActionDescriptionTransferCache;
    
    @Inject
    ChainActionSetTransferCache chainActionSetTransferCache;
    
    @Inject
    ChainActionTransferCache chainActionTransferCache;
    
    @Inject
    ChainInstanceStatusTransferCache chainInstanceStatusTransferCache;
    
    @Inject
    ChainActionLetterTransferCache chainActionLetterTransferCache;
    
    @Inject
    ChainActionSurveyTransferCache chainActionSurveyTransferCache;
    
    @Inject
    ChainActionChainActionSetTransferCache chainActionChainActionSetTransferCache;

    /** Creates a new instance of ChainTransferCaches */
    protected ChainTransferCaches() {
        super();
    }
    
    public ChainKindTransferCache getChainKindTransferCache() {
        return chainKindTransferCache;
    }

    public ChainKindDescriptionTransferCache getChainKindDescriptionTransferCache() {
        return chainKindDescriptionTransferCache;
    }

    public ChainTypeTransferCache getChainTypeTransferCache() {
        return chainTypeTransferCache;
    }

    public ChainTypeDescriptionTransferCache getChainTypeDescriptionTransferCache() {
        return chainTypeDescriptionTransferCache;
    }

    public ChainTransferCache getChainTransferCache() {
        return chainTransferCache;
    }
    
    public ChainDescriptionTransferCache getChainDescriptionTransferCache() {
        return chainDescriptionTransferCache;
    }
    
    public ChainEntityRoleTypeTransferCache getChainEntityRoleTypeTransferCache() {
        return chainEntityRoleTypeTransferCache;
    }
    
    public ChainEntityRoleTypeDescriptionTransferCache getChainEntityRoleTypeDescriptionTransferCache() {
        return chainEntityRoleTypeDescriptionTransferCache;
    }

    public ChainInstanceTransferCache getChainInstanceTransferCache() {
        return chainInstanceTransferCache;
    }
    
    public ChainInstanceEntityRoleTransferCache getChainInstanceEntityRoleTransferCache() {
        return chainInstanceEntityRoleTransferCache;
    }
    
    public ChainActionTypeTransferCache getChainActionTypeTransferCache() {
        return chainActionTypeTransferCache;
    }

    public ChainActionTypeDescriptionTransferCache getChainActionTypeDescriptionTransferCache() {
        return chainActionTypeDescriptionTransferCache;
    }

    public ChainActionTypeUseTransferCache getChainActionTypeUseTransferCache() {
        return chainActionTypeUseTransferCache;
    }
    
        public ChainActionSetDescriptionTransferCache getChainActionSetDescriptionTransferCache() {
        return chainActionSetDescriptionTransferCache;
    }
    
    public ChainActionDescriptionTransferCache getChainActionDescriptionTransferCache() {
        return chainActionDescriptionTransferCache;
    }
    
    public ChainActionSetTransferCache getChainActionSetTransferCache() {
        return chainActionSetTransferCache;
    }
    
    public ChainActionTransferCache getChainActionTransferCache() {
        return chainActionTransferCache;
    }
    
    public ChainInstanceStatusTransferCache getChainInstanceStatusTransferCache() {
        return chainInstanceStatusTransferCache;
    }
    
    public ChainActionLetterTransferCache getChainActionLetterTransferCache() {
        return chainActionLetterTransferCache;
    }
    
    public ChainActionSurveyTransferCache getChainActionSurveyTransferCache() {
        return chainActionSurveyTransferCache;
    }
    
    public ChainActionChainActionSetTransferCache getChainActionChainActionSetTransferCache() {
        return chainActionChainActionSetTransferCache;
    }
    
}
