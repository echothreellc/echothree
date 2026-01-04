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

package com.echothree.model.control.user.common.transfer;

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.MapWrapper;

public class UserLoginTransfer
        extends BaseTransfer {

    // From UserLogin:
    private PartyTransfer party;
    private String username;

    // From UserLoginStatus:
    private Long unformattedLastLoginTime;
    private String lastLoginTime;
    private Integer failureCount;
    private Long unformattedFirstFailureTime;
    private String firstFailureTime;
    private Long unformattedLastFailureTime;
    private String lastFailureTime;
    private Integer expiredCount;
    private Boolean forceChange;

    private MapWrapper<UserLoginPasswordTransfer> userLoginPasswords;

    /** Creates a new instance of UserLoginTransfer */
    public UserLoginTransfer(PartyTransfer party, String username, Long unformattedLastLoginTime, String lastLoginTime, Integer failureCount,
            Long unformattedFirstFailureTime, String firstFailureTime, Long unformattedLastFailureTime, String lastFailureTime, Integer expiredCount,
            Boolean forceChange) {
        this.party = party;
        this.username = username;
        this.unformattedLastLoginTime = unformattedLastLoginTime;
        this.lastLoginTime = lastLoginTime;
        this.failureCount = failureCount;
        this.unformattedFirstFailureTime = unformattedFirstFailureTime;
        this.firstFailureTime = firstFailureTime;
        this.unformattedLastFailureTime = unformattedLastFailureTime;
        this.lastFailureTime = lastFailureTime;
        this.expiredCount = expiredCount;
        this.forceChange = forceChange;
    }

    /**
     * Returns the party.
     * @return the party
     */
    public PartyTransfer getParty() {
        return party;
    }

    /**
     * Sets the party.
     * @param party the party to set
     */
    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    /**
     * Returns the username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the unformattedLastLoginTime.
     * @return the unformattedLastLoginTime
     */
    public Long getUnformattedLastLoginTime() {
        return unformattedLastLoginTime;
    }

    /**
     * Sets the unformattedLastLoginTime.
     * @param unformattedLastLoginTime the unformattedLastLoginTime to set
     */
    public void setUnformattedLastLoginTime(Long unformattedLastLoginTime) {
        this.unformattedLastLoginTime = unformattedLastLoginTime;
    }

    /**
     * Returns the lastLoginTime.
     * @return the lastLoginTime
     */
    public String getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * Sets the lastLoginTime.
     * @param lastLoginTime the lastLoginTime to set
     */
    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * Returns the failureCount.
     * @return the failureCount
     */
    public Integer getFailureCount() {
        return failureCount;
    }

    /**
     * Sets the failureCount.
     * @param failureCount the failureCount to set
     */
    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    /**
     * Returns the unformattedFirstFailureTime.
     * @return the unformattedFirstFailureTime
     */
    public Long getUnformattedFirstFailureTime() {
        return unformattedFirstFailureTime;
    }

    /**
     * Sets the unformattedFirstFailureTime.
     * @param unformattedFirstFailureTime the unformattedFirstFailureTime to set
     */
    public void setUnformattedFirstFailureTime(Long unformattedFirstFailureTime) {
        this.unformattedFirstFailureTime = unformattedFirstFailureTime;
    }

    /**
     * Returns the firstFailureTime.
     * @return the firstFailureTime
     */
    public String getFirstFailureTime() {
        return firstFailureTime;
    }

    /**
     * Sets the firstFailureTime.
     * @param firstFailureTime the firstFailureTime to set
     */
    public void setFirstFailureTime(String firstFailureTime) {
        this.firstFailureTime = firstFailureTime;
    }

    /**
     * Returns the unformattedLastFailureTime.
     * @return the unformattedLastFailureTime
     */
    public Long getUnformattedLastFailureTime() {
        return unformattedLastFailureTime;
    }

    /**
     * Sets the unformattedLastFailureTime.
     * @param unformattedLastFailureTime the unformattedLastFailureTime to set
     */
    public void setUnformattedLastFailureTime(Long unformattedLastFailureTime) {
        this.unformattedLastFailureTime = unformattedLastFailureTime;
    }

    /**
     * Returns the lastFailureTime.
     * @return the lastFailureTime
     */
    public String getLastFailureTime() {
        return lastFailureTime;
    }

    /**
     * Sets the lastFailureTime.
     * @param lastFailureTime the lastFailureTime to set
     */
    public void setLastFailureTime(String lastFailureTime) {
        this.lastFailureTime = lastFailureTime;
    }

    /**
     * Returns the expiredCount.
     * @return the expiredCount
     */
    public Integer getExpiredCount() {
        return expiredCount;
    }

    /**
     * Sets the expiredCount.
     * @param expiredCount the expiredCount to set
     */
    public void setExpiredCount(Integer expiredCount) {
        this.expiredCount = expiredCount;
    }

    /**
     * Returns the forceChange.
     * @return the forceChange
     */
    public Boolean getForceChange() {
        return forceChange;
    }

    /**
     * Sets the forceChange.
     * @param forceChange the forceChange to set
     */
    public void setForceChange(Boolean forceChange) {
        this.forceChange = forceChange;
    }

    /**
     * Returns the userLoginPasswords.
     * @return the userLoginPasswords
     */
    public MapWrapper<UserLoginPasswordTransfer> getUserLoginPasswords() {
        return userLoginPasswords;
    }

    /**
     * Sets the userLoginPasswords.
     * @param userLoginPasswords the userLoginPasswords to set
     */
    public void setUserLoginPasswords(MapWrapper<UserLoginPasswordTransfer> userLoginPasswords) {
        this.userLoginPasswords = userLoginPasswords;
    }
    
}
