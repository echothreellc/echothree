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

package com.echothree.model.control.user.server.transfer;

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.common.UserConstants;
import com.echothree.model.control.user.common.transfer.UserLoginPasswordTransfer;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.server.entity.UserLoginPassword;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UserLoginPasswordTransferCache
        extends BaseUserTransferCache<UserLoginPassword, UserLoginPasswordTransfer> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    UserControl userControl = Session.getModelController(UserControl.class);

    /** Creates a new instance of UserLoginPasswordTransferCache */
    protected UserLoginPasswordTransferCache() {
        super();
    }
    
    public UserLoginPasswordTransfer getUserLoginPasswordTransfer(UserVisit userVisit, UserLoginPassword userLoginPassword) {
        var userLoginPasswordTransfer = get(userLoginPassword);
        
        if(userLoginPasswordTransfer == null) {
            var party = partyControl.getPartyTransfer(userVisit, userLoginPassword.getParty());
            var userLoginPasswordType = userControl.getUserLoginPasswordTypeTransfer(userVisit, userLoginPassword.getUserLoginPasswordType());
            String password = null;
            Long unformattedChangedTime = null;
            String changedTime = null;
            Boolean wasReset = null;

            var userLoginPasswordTypeName = userLoginPasswordType.getUserLoginPasswordTypeName();
            if(userLoginPasswordTypeName.equals(UserConstants.UserLoginPasswordType_STRING) ||
                    userLoginPasswordTypeName.equals(UserConstants.UserLoginPasswordType_RECOVERED_STRING)) {
                var userLoginPasswordString = userControl.getUserLoginPasswordString(userLoginPassword);
                var userLoginPasswordEncoderTypeName = userLoginPasswordType.getUserLoginPasswordEncoderType().getUserLoginPasswordEncoderTypeName();

                // Allow only one very carefully checked case where the password will be returned in the TO. Only recovered passwords, and only if they are
                // plain text. Hashed passwords will never be returned.
                if(userLoginPasswordTypeName.equals(UserConstants.UserLoginPasswordType_RECOVERED_STRING) &&
                        userLoginPasswordEncoderTypeName.equals(UserConstants.UserLoginPasswordEncoderType_TEXT)) {
                    password = userLoginPasswordString.getPassword();
                }

                unformattedChangedTime = userLoginPasswordString.getChangedTime();
                changedTime = formatTypicalDateTime(userVisit, unformattedChangedTime);
                wasReset = userLoginPasswordString.getWasReset();
            }

            userLoginPasswordTransfer = new UserLoginPasswordTransfer(party, userLoginPasswordType, password, unformattedChangedTime, changedTime, wasReset);
            put(userVisit, userLoginPassword, userLoginPasswordTransfer);
        }
        
        return userLoginPasswordTransfer;
    }
    
}
