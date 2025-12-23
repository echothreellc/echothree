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

package com.echothree.model.control.party.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class PartyTypeAuditPolicyTransfer
        extends BaseTransfer {

    private PartyTypeTransfer partyType;
    private Boolean auditCommands;
    private Long unformattedRetainUserVisitsTime;
    private String retainUserVisitsTime;

    /** Creates a new instance of PartyTypeAuditPolicyTransfer */
    public PartyTypeAuditPolicyTransfer(PartyTypeTransfer partyType, Boolean auditCommands, Long unformattedRetainUserVisitsTime, String retainUserVisitsTime) {
        this.partyType = partyType;
        this.auditCommands = auditCommands;
        this.unformattedRetainUserVisitsTime = unformattedRetainUserVisitsTime;
        this.retainUserVisitsTime = retainUserVisitsTime;
    }

    /**
     * Returns the partyType.
     * @return the partyType
     */
    public PartyTypeTransfer getPartyType() {
        return partyType;
    }

    /**
     * Sets the partyType.
     * @param partyType the partyType to set
     */
    public void setPartyType(PartyTypeTransfer partyType) {
        this.partyType = partyType;
    }

    /**
     * Returns the auditCommands.
     * @return the auditCommands
     */
    public Boolean getAuditCommands() {
        return auditCommands;
    }

    /**
     * Sets the auditCommands.
     * @param auditCommands the auditCommands to set
     */
    public void setAuditCommands(Boolean auditCommands) {
        this.auditCommands = auditCommands;
    }

    /**
     * Returns the unformattedRetainUserVisitsTime.
     * @return the unformattedRetainUserVisitsTime
     */
    public Long getUnformattedRetainUserVisitsTime() {
        return unformattedRetainUserVisitsTime;
    }

    /**
     * Sets the unformattedRetainUserVisitsTime.
     * @param unformattedRetainUserVisitsTime the unformattedRetainUserVisitsTime to set
     */
    public void setUnformattedRetainUserVisitsTime(Long unformattedRetainUserVisitsTime) {
        this.unformattedRetainUserVisitsTime = unformattedRetainUserVisitsTime;
    }

    /**
     * Returns the retainUserVisitsTime.
     * @return the retainUserVisitsTime
     */
    public String getRetainUserVisitsTime() {
        return retainUserVisitsTime;
    }

    /**
     * Sets the retainUserVisitsTime.
     * @param retainUserVisitsTime the retainUserVisitsTime to set
     */
    public void setRetainUserVisitsTime(String retainUserVisitsTime) {
        this.retainUserVisitsTime = retainUserVisitsTime;
    }

}