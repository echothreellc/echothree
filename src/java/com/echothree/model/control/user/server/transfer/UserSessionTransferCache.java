// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.user.server.transfer;

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.common.transfer.UserSessionTransfer;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class UserSessionTransferCache
        extends BaseUserTransferCache<UserSession, UserSessionTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of UserSessionTransferCache */
    public UserSessionTransferCache(UserControl userControl) {
        super(userControl);
        
        partyControl = Session.getModelController(PartyControl.class);
    }
    
    public UserSessionTransfer getUserSessionTransfer(UserSession userSession) {
        var userSessionTransfer = get(userSession);
        
        if(userSessionTransfer == null) {
            var userVisitTransfer = userControl.getUserVisitTransfer(userVisit, userSession.getUserVisit());
            var partyTransfer = partyControl.getPartyTransfer(userVisit, userSession.getParty());
            var partyRelationship = userSession.getPartyRelationship();
            var partyRelationshipTransfer = partyRelationship == null ? null : partyControl.getPartyRelationshipTransfer(userVisit, partyRelationship);
            var unformattedIdentityVerifiedTime = userSession.getIdentityVerifiedTime();
            var identityVerifiedTime = unformattedIdentityVerifiedTime == null ? null : formatTypicalDateTime(userVisit, unformattedIdentityVerifiedTime);
            
            userSessionTransfer = new UserSessionTransfer(userVisitTransfer, partyTransfer, partyRelationshipTransfer, unformattedIdentityVerifiedTime,
                    identityVerifiedTime);
            put(userVisit, userSession, userSessionTransfer);
        }
        
        return userSessionTransfer;
    }
    
}
