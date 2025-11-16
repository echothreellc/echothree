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
import com.echothree.model.control.user.common.UserOptions;
import com.echothree.model.control.user.common.transfer.UserLoginPasswordTransfer;
import com.echothree.model.control.user.common.transfer.UserLoginTransfer;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UserLoginTransferCache
        extends BaseUserTransferCache<UserLogin, UserLoginTransfer> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    UserControl userControl = Session.getModelController(UserControl.class);

    boolean includeUserLoginPasswords;

    /** Creates a new instance of UserLoginTransferCache */
    public UserLoginTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            includeUserLoginPasswords = options.contains(UserOptions.UserLoginIncludeUserLoginPasswords);
        }
    }
    
    public UserLoginTransfer getUserLoginTransfer(UserVisit userVisit, UserLogin userLogin) {
        var userLoginTransfer = get(userLogin);
        
        if(userLoginTransfer == null) {
            var party = userLogin.getParty();
            var partyTransfer = partyControl.getPartyTransfer(userVisit, party);
            var userLoginStatus = userControl.getUserLoginStatus(party);
            var username = userLogin.getUsername();
            var unformattedLastLoginTime = userLoginStatus.getLastLoginTime();
            var lastLoginTime = formatTypicalDateTime(userVisit, unformattedLastLoginTime);
            var failureCount = userLoginStatus.getFailureCount();
            var unformattedFirstFailureTime = userLoginStatus.getFirstFailureTime();
            var firstFailureTime = formatTypicalDateTime(userVisit, unformattedFirstFailureTime);
            var unformattedLastFailureTime = userLoginStatus.getLastFailureTime();
            var lastFailureTime = formatTypicalDateTime(userVisit, unformattedLastFailureTime);
            var expiredCount = userLoginStatus.getExpiredCount();
            var forceChange = userLoginStatus.getForceChange();
            
            userLoginTransfer = new UserLoginTransfer(partyTransfer, username, unformattedLastLoginTime, lastLoginTime, failureCount,
                    unformattedFirstFailureTime, firstFailureTime, unformattedLastFailureTime, lastFailureTime, expiredCount, forceChange);
            put(userVisit, userLogin, userLoginTransfer);

            if(includeUserLoginPasswords) {
                var userLoginPasswordTransfers = userControl.getUserLoginPasswordTransfersByParty(userVisit, userLogin.getParty());
                var userLoginPasswords = new MapWrapper<UserLoginPasswordTransfer>(userLoginPasswordTransfers.size());

                userLoginPasswordTransfers.forEach((userLoginPasswordTransfer) -> {
                    userLoginPasswords.put(userLoginPasswordTransfer.getUserLoginPasswordType().getUserLoginPasswordTypeName(), userLoginPasswordTransfer);
                });

                userLoginTransfer.setUserLoginPasswords(userLoginPasswords);
            }
        }
        
        return userLoginTransfer;
    }
    
}
