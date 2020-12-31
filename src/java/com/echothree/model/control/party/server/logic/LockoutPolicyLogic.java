// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.party.server.logic;

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicy;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicyDetail;
import com.echothree.model.data.user.server.entity.UserLoginStatus;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class LockoutPolicyLogic {
    
    private LockoutPolicyLogic() {
        super();
    }
    
    private static class LockoutPolicyLogicHolder {
        static LockoutPolicyLogic instance = new LockoutPolicyLogic();
    }
    
    public static LockoutPolicyLogic getInstance() {
        return LockoutPolicyLogicHolder.instance;
    }
    
    private void resetFailureCount(final Session session, final UserLoginStatus userLoginStatus,
            final PartyTypeLockoutPolicyDetail policyDetail) {
        if(!policyDetail.getManualLockoutReset()) {
            Long resetFailureCountTime = policyDetail.getResetFailureCountTime();
            
            if(resetFailureCountTime != null && userLoginStatus.getFailureCount() != 0) {
                if(session.START_TIME - userLoginStatus.getLastFailureTime() > resetFailureCountTime) {
                    userLoginStatus.setFailureCount(0);
                }
            }
        }
    }
    
    private void checkLockoutFailureCount(final ExecutionErrorAccumulator ema, final UserLoginStatus userLoginStatus,
            final PartyTypeLockoutPolicyDetail policyDetail) {
        Integer lockoutFailureCount = policyDetail.getLockoutFailureCount();
        
        if(lockoutFailureCount != null && userLoginStatus.getFailureCount() >= lockoutFailureCount) {
            ema.addExecutionError(ExecutionErrors.LockoutFailureCountExceeded.name());
            
            if(policyDetail.getManualLockoutReset()) {
                ema.addExecutionError(ExecutionErrors.LockoutManualResetRequired.name());
            }
        }
    }
    
    private void checkLockoutInactiveTime(final Session session, final ExecutionErrorAccumulator ema,
            final UserLoginStatus userLoginStatus, final PartyTypeLockoutPolicyDetail policyDetail) {
        Long lockoutInactiveTime = policyDetail.getLockoutInactiveTime();
        Long lastLoginTime = userLoginStatus.getLastLoginTime();
        
        if(lockoutInactiveTime != null && lastLoginTime != null && session.START_TIME - userLoginStatus.getLastLoginTime() >= lockoutInactiveTime) {
            ema.addExecutionError(ExecutionErrors.LockoutInactiveTimeExceeded.name());
        }
    }
    
    public void checkUserLogin(final Session session, final ExecutionErrorAccumulator ema, final Party party,
            final UserLoginStatus userLoginStatus) {
        var partyControl = Session.getModelController(PartyControl.class);
        PartyTypeLockoutPolicy policy = partyControl.getPartyTypeLockoutPolicy(party.getLastDetail().getPartyType());
        
        if(policy != null) {
            PartyTypeLockoutPolicyDetail policyDetail = policy.getLastDetail();
            
            resetFailureCount(session, userLoginStatus, policyDetail);
            checkLockoutFailureCount(ema, userLoginStatus, policyDetail);
            checkLockoutInactiveTime(session, ema, userLoginStatus, policyDetail);
        }
    }
    
}
