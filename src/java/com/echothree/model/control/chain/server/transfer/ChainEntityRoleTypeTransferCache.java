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

package com.echothree.model.control.chain.server.transfer;

import com.echothree.model.control.chain.common.transfer.ChainEntityRoleTypeTransfer;
import com.echothree.model.control.chain.common.transfer.ChainTypeTransfer;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleType;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ChainEntityRoleTypeTransferCache
        extends BaseChainTransferCache<ChainEntityRoleType, ChainEntityRoleTypeTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of ChainEntityRoleTypeTransferCache */
    public ChainEntityRoleTypeTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ChainEntityRoleTypeTransfer getChainEntityRoleTypeTransfer(ChainEntityRoleType chainEntityRoleType) {
        ChainEntityRoleTypeTransfer chainEntityRoleTypeTransfer = get(chainEntityRoleType);
        
        if(chainEntityRoleTypeTransfer == null) {
            ChainEntityRoleTypeDetail chainEntityRoleTypeDetail = chainEntityRoleType.getLastDetail();
            ChainTypeTransfer chainType = chainControl.getChainTypeTransfer(userVisit, chainEntityRoleTypeDetail.getChainType());
            String chainEntityRoleTypeName = chainEntityRoleTypeDetail.getChainEntityRoleTypeName();
            EntityTypeTransfer entityType = coreControl.getEntityTypeTransfer(userVisit, chainEntityRoleTypeDetail.getEntityType());
            Integer sortOrder = chainEntityRoleTypeDetail.getSortOrder();
            String description = chainControl.getBestChainEntityRoleTypeDescription(chainEntityRoleType, getLanguage());
            
            chainEntityRoleTypeTransfer = new ChainEntityRoleTypeTransfer(chainType, chainEntityRoleTypeName, entityType, sortOrder, description);
            put(chainEntityRoleType, chainEntityRoleTypeTransfer);
        }
        
        return chainEntityRoleTypeTransfer;
    }
    
}
