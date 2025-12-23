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

package com.echothree.model.control.associate.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class AssociateReferralTransfer
        extends BaseTransfer {
    
    private String associateReferralName;
    private AssociateTransfer associate;
    private AssociatePartyContactMechanismTransfer associateContactMechanism;
    private EntityInstanceTransfer targetEntityInstance;
    private Long unformattedAssociateReferralTime;
    private String associateReferralTime;
    
    /** Creates a new instance of AssociateReferralTransfer */
    public AssociateReferralTransfer(String associateReferralName, AssociateTransfer associate, AssociatePartyContactMechanismTransfer associateContactMechanism,
            EntityInstanceTransfer targetEntityInstance, Long unformattedAssociateReferralTime, String associateReferralTime) {
        this.associateReferralName = associateReferralName;
        this.associate = associate;
        this.associateContactMechanism = associateContactMechanism;
        this.targetEntityInstance = targetEntityInstance;
        this.unformattedAssociateReferralTime = unformattedAssociateReferralTime;
        this.associateReferralTime = associateReferralTime;
    }

    /**
     * Returns the associateReferralName.
     * @return the associateReferralName
     */
    public String getAssociateReferralName() {
        return associateReferralName;
    }

    /**
     * Sets the associateReferralName.
     * @param associateReferralName the associateReferralName to set
     */
    public void setAssociateReferralName(String associateReferralName) {
        this.associateReferralName = associateReferralName;
    }

    /**
     * Returns the associate.
     * @return the associate
     */
    public AssociateTransfer getAssociate() {
        return associate;
    }

    /**
     * Sets the associate.
     * @param associate the associate to set
     */
    public void setAssociate(AssociateTransfer associate) {
        this.associate = associate;
    }

    /**
     * Returns the associateContactMechanism.
     * @return the associateContactMechanism
     */
    public AssociatePartyContactMechanismTransfer getAssociateContactMechanism() {
        return associateContactMechanism;
    }

    /**
     * Sets the associateContactMechanism.
     * @param associateContactMechanism the associateContactMechanism to set
     */
    public void setAssociateContactMechanism(AssociatePartyContactMechanismTransfer associateContactMechanism) {
        this.associateContactMechanism = associateContactMechanism;
    }

    /**
     * Returns the targetEntityInstance.
     * @return the targetEntityInstance
     */
    public EntityInstanceTransfer getTargetEntityInstance() {
        return targetEntityInstance;
    }

    /**
     * Sets the targetEntityInstance.
     * @param targetEntityInstance the targetEntityInstance to set
     */
    public void setTargetEntityInstance(EntityInstanceTransfer targetEntityInstance) {
        this.targetEntityInstance = targetEntityInstance;
    }

    /**
     * Returns the unformattedAssociateReferralTime.
     * @return the unformattedAssociateReferralTime
     */
    public Long getUnformattedAssociateReferralTime() {
        return unformattedAssociateReferralTime;
    }

    /**
     * Sets the unformattedAssociateReferralTime.
     * @param unformattedAssociateReferralTime the unformattedAssociateReferralTime to set
     */
    public void setUnformattedAssociateReferralTime(Long unformattedAssociateReferralTime) {
        this.unformattedAssociateReferralTime = unformattedAssociateReferralTime;
    }

    /**
     * Returns the associateReferralTime.
     * @return the associateReferralTime
     */
    public String getAssociateReferralTime() {
        return associateReferralTime;
    }

    /**
     * Sets the associateReferralTime.
     * @param associateReferralTime the associateReferralTime to set
     */
    public void setAssociateReferralTime(String associateReferralTime) {
        this.associateReferralTime = associateReferralTime;
    }
    
}
