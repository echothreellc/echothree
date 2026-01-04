// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.core.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class EntityLockTransfer
        extends BaseTransfer {
    
    private EntityInstanceTransfer lockTargetEntityInstance;
    private EntityInstanceTransfer lockedByEntityInstance;
    private Long unformattedLockedTime;
    private String lockedTime;
    private Long unformattedExpirationTime;
    private String expirationTime;
    
    /** Creates a new instance of EntityLockTransfer */
    public EntityLockTransfer(EntityInstanceTransfer lockTargetEntityInstance, EntityInstanceTransfer lockedByEntityInstance,
            Long unformattedLockedTime, String lockedTime, Long unformattedExpirationTime, String expirationTime) {
        this.lockTargetEntityInstance = lockTargetEntityInstance;
        this.lockedByEntityInstance = lockedByEntityInstance;
        this.unformattedLockedTime = unformattedLockedTime;
        this.lockedTime = lockedTime;
        this.unformattedExpirationTime = unformattedExpirationTime;
        this.expirationTime = expirationTime;
    }
    
    public EntityInstanceTransfer getLockTargetEntityInstance() {
        return lockTargetEntityInstance;
    }
    
    public void setLockTargetEntityInstance(EntityInstanceTransfer lockTargetEntityInstance) {
        this.lockTargetEntityInstance = lockTargetEntityInstance;
    }
    
    public EntityInstanceTransfer getLockedByEntityInstance() {
        return lockedByEntityInstance;
    }
    
    public void setLockedByEntityInstance(EntityInstanceTransfer lockedByEntityInstance) {
        this.lockedByEntityInstance = lockedByEntityInstance;
    }
    
    public Long getUnformattedLockedTime() {
        return unformattedLockedTime;
    }

    public void setUnformattedLockedTime(Long unformattedLockedTime) {
        this.unformattedLockedTime = unformattedLockedTime;
    }

    public String getLockedTime() {
        return lockedTime;
    }
    
    public void setLockedTime(String lockedTime) {
        this.lockedTime = lockedTime;
    }
    
    public Long getUnformattedExpirationTime() {
        return unformattedExpirationTime;
    }

    public void setUnformattedExpirationTime(Long unformattedExpirationTime) {
        this.unformattedExpirationTime = unformattedExpirationTime;
    }
    
    public String getExpirationTime() {
        return expirationTime;
    }
    
    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

}
