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

package com.echothree.model.control.chain.remote.transfer;

import com.echothree.util.remote.transfer.BaseTransfer;
import com.echothree.util.remote.transfer.MapWrapper;

public class ChainInstanceTransfer
        extends BaseTransfer {
    
    private String chainInstanceName;
    private ChainTransfer chain;
    private ChainInstanceStatusTransfer chainInstanceStatus;
    
    private MapWrapper<ChainInstanceEntityRoleTransfer> chainInstanceEntityRoles;
    
    /** Creates a new instance of ChainInstanceTransfer */
    public ChainInstanceTransfer(String chainInstanceName, ChainTransfer chain) {
        this.chainInstanceName = chainInstanceName;
        this.chain = chain;
    }

    /**
     * @return the chainInstanceName
     */
    public String getChainInstanceName() {
        return chainInstanceName;
    }

    /**
     * @param chainInstanceName the chainInstanceName to set
     */
    public void setChainInstanceName(String chainInstanceName) {
        this.chainInstanceName = chainInstanceName;
    }

    /**
     * @return the chain
     */
    public ChainTransfer getChain() {
        return chain;
    }

    /**
     * @param chain the chain to set
     */
    public void setChain(ChainTransfer chain) {
        this.chain = chain;
    }

    /**
     * @return the chainInstanceStatus
     */
    public ChainInstanceStatusTransfer getChainInstanceStatus() {
        return chainInstanceStatus;
    }

    /**
     * @param chainInstanceStatus the chainInstanceStatus to set
     */
    public void setChainInstanceStatus(ChainInstanceStatusTransfer chainInstanceStatus) {
        this.chainInstanceStatus = chainInstanceStatus;
    }

    /**
     * @return the chainInstanceEntityRoles
     */
    public MapWrapper<ChainInstanceEntityRoleTransfer> getChainInstanceEntityRoles() {
        return chainInstanceEntityRoles;
    }

    /**
     * @param chainInstanceEntityRoles the chainInstanceEntityRoles to set
     */
    public void setChainInstanceEntityRoles(MapWrapper<ChainInstanceEntityRoleTransfer> chainInstanceEntityRoles) {
        this.chainInstanceEntityRoles = chainInstanceEntityRoles;
    }

 }
