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

import com.echothree.model.control.chain.common.transfer.ChainKindTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ChainKindTransferCache
        extends BaseChainTransferCache<ChainKind, ChainKindTransfer> {

    ChainControl chainControl = Session.getModelController(ChainControl.class);

    /** Creates a new instance of ChainKindTransferCache */
    protected ChainKindTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public ChainKindTransfer getChainKindTransfer(UserVisit userVisit, ChainKind chainKind) {
        var chainKindTransfer = get(chainKind);
        
        if(chainKindTransfer == null) {
            var chainKindDetail = chainKind.getLastDetail();
            var chainKindName = chainKindDetail.getChainKindName();
            var isDefault = chainKindDetail.getIsDefault();
            var sortOrder = chainKindDetail.getSortOrder();
            var description = chainControl.getBestChainKindDescription(chainKind, getLanguage(userVisit));
            
            chainKindTransfer = new ChainKindTransfer(chainKindName, isDefault, sortOrder, description);
            put(userVisit, chainKind, chainKindTransfer);
        }
        
        return chainKindTransfer;
    }
    
}
