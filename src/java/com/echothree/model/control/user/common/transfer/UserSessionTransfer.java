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

import com.echothree.model.control.party.common.transfer.PartyRelationshipTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class UserSessionTransfer
        extends BaseTransfer {
    
    private UserVisitTransfer userVisit;
    private PartyTransfer party;
    private PartyRelationshipTransfer partyRelationship;
    private Long unformattedPasswordVerifiedTime;
    private String passwordVerifiedTime;
    
    /** Creates a new instance of UserSessionTransfer */
    public UserSessionTransfer(UserVisitTransfer userVisit, PartyTransfer party, PartyRelationshipTransfer partyRelationship,
            Long unformattedPasswordVerifiedTime, String passwordVerifiedTime) {
        this.userVisit = userVisit;
        this.party = party;
        this.partyRelationship = partyRelationship;
        this.unformattedPasswordVerifiedTime = unformattedPasswordVerifiedTime;
        this.passwordVerifiedTime = passwordVerifiedTime;
    }

    /**
     * @return the userVisit
     */
    public UserVisitTransfer getUserVisit() {
        return userVisit;
    }

    /**
     * @param userVisit the userVisit to set
     */
    public void setUserVisit(UserVisitTransfer userVisit) {
        this.userVisit = userVisit;
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
     * @return the partyRelationship
     */
    public PartyRelationshipTransfer getPartyRelationship() {
        return partyRelationship;
    }

    /**
     * @param partyRelationship the partyRelationship to set
     */
    public void setPartyRelationship(PartyRelationshipTransfer partyRelationship) {
        this.partyRelationship = partyRelationship;
    }

    /**
     * @return the unformattedPasswordVerifiedTime
     */
    public Long getUnformattedPasswordVerifiedTime() {
        return unformattedPasswordVerifiedTime;
    }

    /**
     * @param unformattedPasswordVerifiedTime the unformattedPasswordVerifiedTime to set
     */
    public void setUnformattedPasswordVerifiedTime(Long unformattedPasswordVerifiedTime) {
        this.unformattedPasswordVerifiedTime = unformattedPasswordVerifiedTime;
    }

    /**
     * @return the passwordVerifiedTime
     */
    public String getPasswordVerifiedTime() {
        return passwordVerifiedTime;
    }

    /**
     * @param passwordVerifiedTime the passwordVerifiedTime to set
     */
    public void setPasswordVerifiedTime(String passwordVerifiedTime) {
        this.passwordVerifiedTime = passwordVerifiedTime;
    }
    
}
