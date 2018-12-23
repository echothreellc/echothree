// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

public class PartyTypeLockoutPolicyTransfer
        extends BaseTransfer {

    private PartyTypeTransfer partyType;
    private Integer lockoutFailureCount;
    private Long unformattedResetFailureCountTime;
    private String resetFailureCountTime;
    private Boolean manualLockoutReset;
    private Long unformattedLockoutInactiveTime;
    private String lockoutInactiveTime;

    /** Creates a new instance of PartyTypeLockoutPolicyTransfer */
    public PartyTypeLockoutPolicyTransfer(PartyTypeTransfer partyType, Integer lockoutFailureCount, Long unformattedResetFailureCountTime,
            String resetFailureCountTime, Boolean manualLockoutReset, Long unformattedLockoutInactiveTime, String lockoutInactiveTime) {
        this.partyType = partyType;
        this.lockoutFailureCount = lockoutFailureCount;
        this.unformattedResetFailureCountTime = unformattedResetFailureCountTime;
        this.resetFailureCountTime = resetFailureCountTime;
        this.manualLockoutReset = manualLockoutReset;
        this.unformattedLockoutInactiveTime = unformattedLockoutInactiveTime;
        this.lockoutInactiveTime = lockoutInactiveTime;
    }

    /**
     * @return the partyType
     */
    public PartyTypeTransfer getPartyType() {
        return partyType;
    }

    /**
     * @param partyType the partyType to set
     */
    public void setPartyType(PartyTypeTransfer partyType) {
        this.partyType = partyType;
    }

    /**
     * @return the lockoutFailureCount
     */
    public Integer getLockoutFailureCount() {
        return lockoutFailureCount;
    }

    /**
     * @param lockoutFailureCount the lockoutFailureCount to set
     */
    public void setLockoutFailureCount(Integer lockoutFailureCount) {
        this.lockoutFailureCount = lockoutFailureCount;
    }

    /**
     * @return the unformattedResetFailureCountTime
     */
    public Long getUnformattedResetFailureCountTime() {
        return unformattedResetFailureCountTime;
    }

    /**
     * @param unformattedResetFailureCountTime the unformattedResetFailureCountTime to set
     */
    public void setUnformattedResetFailureCountTime(Long unformattedResetFailureCountTime) {
        this.unformattedResetFailureCountTime = unformattedResetFailureCountTime;
    }

    /**
     * @return the resetFailureCountTime
     */
    public String getResetFailureCountTime() {
        return resetFailureCountTime;
    }

    /**
     * @param resetFailureCountTime the resetFailureCountTime to set
     */
    public void setResetFailureCountTime(String resetFailureCountTime) {
        this.resetFailureCountTime = resetFailureCountTime;
    }

    /**
     * @return the manualLockoutReset
     */
    public Boolean getManualLockoutReset() {
        return manualLockoutReset;
    }

    /**
     * @param manualLockoutReset the manualLockoutReset to set
     */
    public void setManualLockoutReset(Boolean manualLockoutReset) {
        this.manualLockoutReset = manualLockoutReset;
    }

    /**
     * @return the unformattedLockoutInactiveTime
     */
    public Long getUnformattedLockoutInactiveTime() {
        return unformattedLockoutInactiveTime;
    }

    /**
     * @param unformattedLockoutInactiveTime the unformattedLockoutInactiveTime to set
     */
    public void setUnformattedLockoutInactiveTime(Long unformattedLockoutInactiveTime) {
        this.unformattedLockoutInactiveTime = unformattedLockoutInactiveTime;
    }

    /**
     * @return the lockoutInactiveTime
     */
    public String getLockoutInactiveTime() {
        return lockoutInactiveTime;
    }

    /**
     * @param lockoutInactiveTime the lockoutInactiveTime to set
     */
    public void setLockoutInactiveTime(String lockoutInactiveTime) {
        this.lockoutInactiveTime = lockoutInactiveTime;
    }

}