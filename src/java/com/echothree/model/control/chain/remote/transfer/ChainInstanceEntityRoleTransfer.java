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

import com.echothree.model.control.core.remote.transfer.EntityInstanceTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class ChainInstanceEntityRoleTransfer
        extends BaseTransfer {
    
    private ChainInstanceTransfer chainInstance;
    private ChainEntityRoleTypeTransfer chainEntityRoleType;
    private EntityInstanceTransfer entityInstance;
    
    /** Creates a new instance of ChainInstanceEntityRoleTransfer */
    public ChainInstanceEntityRoleTransfer(ChainInstanceTransfer chainInstance, ChainEntityRoleTypeTransfer chainEntityRoleType, EntityInstanceTransfer entityInstance) {
        this.chainInstance = chainInstance;
        this.chainEntityRoleType = chainEntityRoleType;
        this.entityInstance = entityInstance;
    }

    /**
     * @return the chainInstance
     */
    public ChainInstanceTransfer getChainInstance() {
        return chainInstance;
    }

    /**
     * @param chainInstance the chainInstance to set
     */
    public void setChainInstance(ChainInstanceTransfer chainInstance) {
        this.chainInstance = chainInstance;
    }

    /**
     * @return the chainEntityRoleType
     */
    public ChainEntityRoleTypeTransfer getChainEntityRoleType() {
        return chainEntityRoleType;
    }

    /**
     * @param chainEntityRoleType the chainEntityRoleType to set
     */
    public void setChainEntityRoleType(ChainEntityRoleTypeTransfer chainEntityRoleType) {
        this.chainEntityRoleType = chainEntityRoleType;
    }

    /**
     * @return the entityInstance
     */
    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }

    /**
     * @param entityInstance the entityInstance to set
     */
    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }
    
}
