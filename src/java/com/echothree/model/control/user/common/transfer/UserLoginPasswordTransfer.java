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

public class UserLoginPasswordTransfer
        extends BaseTransfer {

    private PartyTransfer party;
    private UserLoginPasswordTypeTransfer userLoginPasswordType;

    // From UserLoginPasswordString:
    private String password;
    private Long unformattedChangedTime;
    private String changedTime;
    private Boolean wasReset;

    /** Creates a new instance of UserLoginPasswordTransfer */
    public UserLoginPasswordTransfer(PartyTransfer party, UserLoginPasswordTypeTransfer userLoginPasswordType, String password, Long unformattedChangedTime,
            String changedTime, Boolean wasReset) {
        this.party = party;
        this.userLoginPasswordType = userLoginPasswordType;
        this.password = password;
        this.unformattedChangedTime = unformattedChangedTime;
        this.changedTime = changedTime;
        this.wasReset = wasReset;
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
     * Returns the userLoginPasswordType.
     * @return the userLoginPasswordType
     */
    public UserLoginPasswordTypeTransfer getUserLoginPasswordType() {
        return userLoginPasswordType;
    }

    /**
     * Sets the userLoginPasswordType.
     * @param userLoginPasswordType the userLoginPasswordType to set
     */
    public void setUserLoginPasswordType(UserLoginPasswordTypeTransfer userLoginPasswordType) {
        this.userLoginPasswordType = userLoginPasswordType;
    }

    /**
     * Returns the password.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the unformattedChangedTime.
     * @return the unformattedChangedTime
     */
    public Long getUnformattedChangedTime() {
        return unformattedChangedTime;
    }

    /**
     * Sets the unformattedChangedTime.
     * @param unformattedChangedTime the unformattedChangedTime to set
     */
    public void setUnformattedChangedTime(Long unformattedChangedTime) {
        this.unformattedChangedTime = unformattedChangedTime;
    }

    /**
     * Returns the changedTime.
     * @return the changedTime
     */
    public String getChangedTime() {
        return changedTime;
    }

    /**
     * Sets the changedTime.
     * @param changedTime the changedTime to set
     */
    public void setChangedTime(String changedTime) {
        this.changedTime = changedTime;
    }

    /**
     * Returns the wasReset.
     * @return the wasReset
     */
    public Boolean getWasReset() {
        return wasReset;
    }

    /**
     * Sets the wasReset.
     * @param wasReset the wasReset to set
     */
    public void setWasReset(Boolean wasReset) {
        this.wasReset = wasReset;
    }

}
