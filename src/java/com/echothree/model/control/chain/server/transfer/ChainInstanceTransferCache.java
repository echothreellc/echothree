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

import com.echothree.model.control.chain.common.ChainOptions;
import com.echothree.model.control.chain.common.transfer.ChainInstanceEntityRoleTransfer;
import com.echothree.model.control.chain.common.transfer.ChainInstanceTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ChainInstanceTransferCache
        extends BaseChainTransferCache<ChainInstance, ChainInstanceTransfer> {

    ChainControl chainControl = Session.getModelController(ChainControl.class);

    boolean includeChainInstanceStatus;
    boolean includeChainInstanceEntityRoles;
    
    /** Creates a new instance of ChainInstanceTransferCache */
    protected ChainInstanceTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeChainInstanceStatus = options.contains(ChainOptions.ChainInstanceIncludeChainInstanceStatus);
            includeChainInstanceEntityRoles = options.contains(ChainOptions.ChainInstanceIncludeChainInstanceEntityRoles);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public ChainInstanceTransfer getChainInstanceTransfer(UserVisit userVisit, ChainInstance chainInstance) {
        var chainInstanceTransfer = get(chainInstance);
        
        if(chainInstanceTransfer == null) {
            var chainInstanceDetail = chainInstance.getLastDetail();
            var chainInstanceName = chainInstanceDetail.getChainInstanceName();
            var chain = chainControl.getChainTransfer(userVisit, chainInstanceDetail.getChain());
            
            chainInstanceTransfer = new ChainInstanceTransfer(chainInstanceName, chain);
            put(userVisit, chainInstance, chainInstanceTransfer);
            
            if(includeChainInstanceStatus) {
                chainInstanceTransfer.setChainInstanceStatus(chainControl.getChainInstanceStatusTransfer(userVisit, chainInstance));
            }
            
            if(includeChainInstanceEntityRoles) {
                var chainInstanceEntityRoleTransfers = chainControl.getChainInstanceEntityRoleTransfersByChainInstance(userVisit, chainInstance);
                var chainInstanceEntityRoles = new MapWrapper<ChainInstanceEntityRoleTransfer>(chainInstanceEntityRoleTransfers.size());

                chainInstanceEntityRoleTransfers.forEach((chainInstanceEntityRoleTransfer) -> {
                    chainInstanceEntityRoles.put(chainInstanceEntityRoleTransfer.getChainEntityRoleType().getChainEntityRoleTypeName(), chainInstanceEntityRoleTransfer);
                });

                chainInstanceTransfer.setChainInstanceEntityRoles(chainInstanceEntityRoles);
            }
        }
        
        return chainInstanceTransfer;
    }
    
}
