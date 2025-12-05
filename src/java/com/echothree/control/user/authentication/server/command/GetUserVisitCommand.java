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

package com.echothree.control.user.authentication.server.command;

import com.echothree.control.user.authentication.common.form.GetUserVisitForm;
import com.echothree.control.user.authentication.common.result.AuthenticationResultFactory;
import com.echothree.model.data.user.server.entity.UserKey;
import com.echothree.model.data.user.server.entity.UserKeyDetail;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import javax.enterprise.context.Dependent;

@Dependent
public class GetUserVisitCommand
        extends BaseSimpleCommand<GetUserVisitForm> {
    
    /** Creates a new instance of GetUserVisitCommand */
    public GetUserVisitCommand() {
        super(null, null, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = AuthenticationResultFactory.getGetUserVisitResult();
        var userControl = getUserControl();
        var userKeyName = form.getUserKeyName();
        UserKeyDetail userKeyDetail = null;
        
        if(userKeyName != null) {
            userKeyDetail = userControl.getUserKeyDetailByNameForUpdate(userKeyName);
        }
        
        UserKey userKey;
        if(userKeyDetail == null) {
            userKey = userControl.createUserKey();
            userKeyDetail = userKey.getLastDetail();
            userKeyName = userKeyDetail.getUserKeyName();
        } else {
            userKey = userKeyDetail.getUserKey();
            userControl.getUserKeyStatusForUpdate(userKey).setLastSeenTime(session.getStartTimeLong());
        }
        
        setUserVisitPK(userControl.createUserVisit(userKey, null, null, null, null, null, null, null).getPrimaryKey());

        var party = userKeyDetail.getParty();
        if(party != null) {
            userControl.associatePartyToUserVisit(getUserVisitForUpdate(), party, userKeyDetail.getPartyRelationship(), null);
        }
        
        result.setUserKeyName(userKeyName);
        result.setUserVisitPK(getUserVisitPK());
        
        return result;
    }
    
}
