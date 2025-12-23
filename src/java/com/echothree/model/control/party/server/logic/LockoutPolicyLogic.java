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

package com.echothree.model.control.party.server.logic;

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicyDetail;
import com.echothree.model.data.user.server.entity.UserLoginStatus;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class LockoutPolicyLogic {
    
    protected LockoutPolicyLogic() {
        super();
    }

    public static LockoutPolicyLogic getInstance() {
        return CDI.current().select(LockoutPolicyLogic.class).get();
    }
    
    private void resetFailureCount(final Session session, final UserLoginStatus userLoginStatus,
            final PartyTypeLockoutPolicyDetail policyDetail) {
        if(!policyDetail.getManualLockoutReset()) {
            var resetFailureCountTime = policyDetail.getResetFailureCountTime();
            
            if(resetFailureCountTime != null && userLoginStatus.getFailureCount() != 0) {
                if(session.getStartTime() - userLoginStatus.getLastFailureTime() > resetFailureCountTime) {
                    userLoginStatus.setFailureCount(0);
                }
            }
        }
    }
    
    private void checkLockoutFailureCount(final ExecutionErrorAccumulator ema, final UserLoginStatus userLoginStatus,
            final PartyTypeLockoutPolicyDetail policyDetail) {
        var lockoutFailureCount = policyDetail.getLockoutFailureCount();
        
        if(lockoutFailureCount != null && userLoginStatus.getFailureCount() >= lockoutFailureCount) {
            ema.addExecutionError(ExecutionErrors.LockoutFailureCountExceeded.name());
            
            if(policyDetail.getManualLockoutReset()) {
                ema.addExecutionError(ExecutionErrors.LockoutManualResetRequired.name());
            }
        }
    }
    
    private void checkLockoutInactiveTime(final Session session, final ExecutionErrorAccumulator ema,
            final UserLoginStatus userLoginStatus, final PartyTypeLockoutPolicyDetail policyDetail) {
        var lockoutInactiveTime = policyDetail.getLockoutInactiveTime();
        var lastLoginTime = userLoginStatus.getLastLoginTime();
        
        if(lockoutInactiveTime != null && lastLoginTime != null && session.getStartTime() - userLoginStatus.getLastLoginTime() >= lockoutInactiveTime) {
            ema.addExecutionError(ExecutionErrors.LockoutInactiveTimeExceeded.name());
        }
    }
    
    public void checkUserLogin(final Session session, final ExecutionErrorAccumulator ema, final Party party,
            final UserLoginStatus userLoginStatus) {
        var partyControl = Session.getModelController(PartyControl.class);
        var policy = partyControl.getPartyTypeLockoutPolicy(party.getLastDetail().getPartyType());
        
        if(policy != null) {
            var policyDetail = policy.getLastDetail();
            
            resetFailureCount(session, userLoginStatus, policyDetail);
            checkLockoutFailureCount(ema, userLoginStatus, policyDetail);
            checkLockoutInactiveTime(session, ema, userLoginStatus, policyDetail);
        }
    }
    
}
