// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.party.common.transfer.PartyRelationshipTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.common.transfer.UserSessionTransfer;
import com.echothree.model.control.user.common.transfer.UserVisitTransfer;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class UserSessionTransferCache
        extends BaseUserTransferCache<UserSession, UserSessionTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of UserSessionTransferCache */
    public UserSessionTransferCache(UserVisit userVisit, UserControl userControl) {
        super(userVisit, userControl);
        
        partyControl = Session.getModelController(PartyControl.class);
    }
    
    public UserSessionTransfer getUserSessionTransfer(UserSession userSession) {
        UserSessionTransfer userSessionTransfer = get(userSession);
        
        if(userSessionTransfer == null) {
            UserVisitTransfer userVisitTransfer = userControl.getUserVisitTransfer(userVisit, userSession.getUserVisit());
            PartyTransfer partyTransfer = partyControl.getPartyTransfer(userVisit, userSession.getParty());
            PartyRelationship partyRelationship = userSession.getPartyRelationship();
            PartyRelationshipTransfer partyRelationshipTransfer = partyRelationship == null ? null : partyControl.getPartyRelationshipTransfer(userVisit, partyRelationship);
            Long unformattedIdentityVerifiedTime = userSession.getIdentityVerifiedTime();
            String identityVerifiedTime = unformattedIdentityVerifiedTime == null ? null : formatTypicalDateTime(unformattedIdentityVerifiedTime);
            
            userSessionTransfer = new UserSessionTransfer(userVisitTransfer, partyTransfer, partyRelationshipTransfer, unformattedIdentityVerifiedTime,
                    identityVerifiedTime);
            put(userSession, userSessionTransfer);
        }
        
        return userSessionTransfer;
    }
    
}
