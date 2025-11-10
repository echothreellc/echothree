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

import com.echothree.model.control.chain.common.transfer.ChainEntityRoleTypeTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ChainEntityRoleTypeTransferCache
        extends BaseChainTransferCache<ChainEntityRoleType, ChainEntityRoleTypeTransfer> {

    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);

    /** Creates a new instance of ChainEntityRoleTypeTransferCache */
    public ChainEntityRoleTypeTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ChainEntityRoleTypeTransfer getChainEntityRoleTypeTransfer(ChainEntityRoleType chainEntityRoleType) {
        var chainEntityRoleTypeTransfer = get(chainEntityRoleType);
        
        if(chainEntityRoleTypeTransfer == null) {
            var chainEntityRoleTypeDetail = chainEntityRoleType.getLastDetail();
            var chainType = chainControl.getChainTypeTransfer(userVisit, chainEntityRoleTypeDetail.getChainType());
            var chainEntityRoleTypeName = chainEntityRoleTypeDetail.getChainEntityRoleTypeName();
            var entityType = entityTypeControl.getEntityTypeTransfer(userVisit, chainEntityRoleTypeDetail.getEntityType());
            var sortOrder = chainEntityRoleTypeDetail.getSortOrder();
            var description = chainControl.getBestChainEntityRoleTypeDescription(chainEntityRoleType, getLanguage(userVisit));
            
            chainEntityRoleTypeTransfer = new ChainEntityRoleTypeTransfer(chainType, chainEntityRoleTypeName, entityType, sortOrder, description);
            put(userVisit, chainEntityRoleType, chainEntityRoleTypeTransfer);
        }
        
        return chainEntityRoleTypeTransfer;
    }
    
}
