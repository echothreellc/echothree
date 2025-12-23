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

import com.echothree.model.control.party.common.transfer.PartyRelationshipTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class UserSessionTransfer
        extends BaseTransfer {
    
    private UserVisitTransfer userVisit;
    private PartyTransfer party;
    private PartyRelationshipTransfer partyRelationship;
    private Long unformattedIdentityVerifiedTime;
    private String identityVerifiedTime;
    
    /** Creates a new instance of UserSessionTransfer */
    public UserSessionTransfer(UserVisitTransfer userVisit, PartyTransfer party, PartyRelationshipTransfer partyRelationship,
            Long unformattedIdentityVerifiedTime, String identityVerifiedTime) {
        this.userVisit = userVisit;
        this.party = party;
        this.partyRelationship = partyRelationship;
        this.unformattedIdentityVerifiedTime = unformattedIdentityVerifiedTime;
        this.identityVerifiedTime = identityVerifiedTime;
    }

    /**
     * Returns the userVisit.
     * @return the userVisit
     */
    public UserVisitTransfer getUserVisit() {
        return userVisit;
    }

    /**
     * Sets the userVisit.
     * @param userVisit the userVisit to set
     */
    public void setUserVisit(UserVisitTransfer userVisit) {
        this.userVisit = userVisit;
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
     * Returns the partyRelationship.
     * @return the partyRelationship
     */
    public PartyRelationshipTransfer getPartyRelationship() {
        return partyRelationship;
    }

    /**
     * Sets the partyRelationship.
     * @param partyRelationship the partyRelationship to set
     */
    public void setPartyRelationship(PartyRelationshipTransfer partyRelationship) {
        this.partyRelationship = partyRelationship;
    }

    /**
     * Returns the unformattedIdentityVerifiedTime.
     * @return the unformattedIdentityVerifiedTime
     */
    public Long getUnformattedIdentityVerifiedTime() {
        return unformattedIdentityVerifiedTime;
    }

    /**
     * Sets the unformattedIdentityVerifiedTime.
     * @param unformattedIdentityVerifiedTime the unformattedIdentityVerifiedTime to set
     */
    public void setUnformattedIdentityVerifiedTime(Long unformattedIdentityVerifiedTime) {
        this.unformattedIdentityVerifiedTime = unformattedIdentityVerifiedTime;
    }

    /**
     * Returns the identityVerifiedTime.
     * @return the identityVerifiedTime
     */
    public String getIdentityVerifiedTime() {
        return identityVerifiedTime;
    }

    /**
     * Sets the identityVerifiedTime.
     * @param identityVerifiedTime the identityVerifiedTime to set
     */
    public void setIdentityVerifiedTime(String identityVerifiedTime) {
        this.identityVerifiedTime = identityVerifiedTime;
    }
    
}
