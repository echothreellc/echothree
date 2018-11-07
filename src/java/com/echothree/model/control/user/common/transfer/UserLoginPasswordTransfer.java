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
     * @return the userLoginPasswordType
     */
    public UserLoginPasswordTypeTransfer getUserLoginPasswordType() {
        return userLoginPasswordType;
    }

    /**
     * @param userLoginPasswordType the userLoginPasswordType to set
     */
    public void setUserLoginPasswordType(UserLoginPasswordTypeTransfer userLoginPasswordType) {
        this.userLoginPasswordType = userLoginPasswordType;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the unformattedChangedTime
     */
    public Long getUnformattedChangedTime() {
        return unformattedChangedTime;
    }

    /**
     * @param unformattedChangedTime the unformattedChangedTime to set
     */
    public void setUnformattedChangedTime(Long unformattedChangedTime) {
        this.unformattedChangedTime = unformattedChangedTime;
    }

    /**
     * @return the changedTime
     */
    public String getChangedTime() {
        return changedTime;
    }

    /**
     * @param changedTime the changedTime to set
     */
    public void setChangedTime(String changedTime) {
        this.changedTime = changedTime;
    }

    /**
     * @return the wasReset
     */
    public Boolean getWasReset() {
        return wasReset;
    }

    /**
     * @param wasReset the wasReset to set
     */
    public void setWasReset(Boolean wasReset) {
        this.wasReset = wasReset;
    }

}
