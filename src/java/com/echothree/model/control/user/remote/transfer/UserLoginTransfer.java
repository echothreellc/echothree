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

package com.echothree.model.control.user.remote.transfer;

import com.echothree.model.control.party.remote.transfer.PartyTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;
import com.echothree.util.remote.transfer.MapWrapper;

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
     * @return the party
     */
    public PartyTransfer getParty() {
        return party;
    }

    /**
     * @param party the party to set
     */
    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the unformattedLastLoginTime
     */
    public Long getUnformattedLastLoginTime() {
        return unformattedLastLoginTime;
    }

    /**
     * @param unformattedLastLoginTime the unformattedLastLoginTime to set
     */
    public void setUnformattedLastLoginTime(Long unformattedLastLoginTime) {
        this.unformattedLastLoginTime = unformattedLastLoginTime;
    }

    /**
     * @return the lastLoginTime
     */
    public String getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime the lastLoginTime to set
     */
    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return the failureCount
     */
    public Integer getFailureCount() {
        return failureCount;
    }

    /**
     * @param failureCount the failureCount to set
     */
    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    /**
     * @return the unformattedFirstFailureTime
     */
    public Long getUnformattedFirstFailureTime() {
        return unformattedFirstFailureTime;
    }

    /**
     * @param unformattedFirstFailureTime the unformattedFirstFailureTime to set
     */
    public void setUnformattedFirstFailureTime(Long unformattedFirstFailureTime) {
        this.unformattedFirstFailureTime = unformattedFirstFailureTime;
    }

    /**
     * @return the firstFailureTime
     */
    public String getFirstFailureTime() {
        return firstFailureTime;
    }

    /**
     * @param firstFailureTime the firstFailureTime to set
     */
    public void setFirstFailureTime(String firstFailureTime) {
        this.firstFailureTime = firstFailureTime;
    }

    /**
     * @return the unformattedLastFailureTime
     */
    public Long getUnformattedLastFailureTime() {
        return unformattedLastFailureTime;
    }

    /**
     * @param unformattedLastFailureTime the unformattedLastFailureTime to set
     */
    public void setUnformattedLastFailureTime(Long unformattedLastFailureTime) {
        this.unformattedLastFailureTime = unformattedLastFailureTime;
    }

    /**
     * @return the lastFailureTime
     */
    public String getLastFailureTime() {
        return lastFailureTime;
    }

    /**
     * @param lastFailureTime the lastFailureTime to set
     */
    public void setLastFailureTime(String lastFailureTime) {
        this.lastFailureTime = lastFailureTime;
    }

    /**
     * @return the expiredCount
     */
    public Integer getExpiredCount() {
        return expiredCount;
    }

    /**
     * @param expiredCount the expiredCount to set
     */
    public void setExpiredCount(Integer expiredCount) {
        this.expiredCount = expiredCount;
    }

    /**
     * @return the forceChange
     */
    public Boolean getForceChange() {
        return forceChange;
    }

    /**
     * @param forceChange the forceChange to set
     */
    public void setForceChange(Boolean forceChange) {
        this.forceChange = forceChange;
    }

    /**
     * @return the userLoginPasswords
     */
    public MapWrapper<UserLoginPasswordTransfer> getUserLoginPasswords() {
        return userLoginPasswords;
    }

    /**
     * @param userLoginPasswords the userLoginPasswords to set
     */
    public void setUserLoginPasswords(MapWrapper<UserLoginPasswordTransfer> userLoginPasswords) {
        this.userLoginPasswords = userLoginPasswords;
    }
    
}
