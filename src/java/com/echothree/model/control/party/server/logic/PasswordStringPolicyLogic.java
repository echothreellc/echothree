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
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicyDetail;
import com.echothree.model.data.user.server.entity.UserLoginPassword;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.user.server.value.UserLoginPasswordStringValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.Sha1Utils;
import com.echothree.util.server.string.UnitOfMeasureUtils;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PasswordStringPolicyLogic {

    protected PasswordStringPolicyLogic() {
        super();
    }

    public static PasswordStringPolicyLogic getInstance() {
        return CDI.current().select(PasswordStringPolicyLogic.class).get();
    }
    
    private void checkAllowChange(final ExecutionErrorAccumulator ema, final PartyTypePasswordStringPolicyDetail policyDetail) {
        if(!policyDetail.getAllowChange()) {
            ema.addExecutionError(ExecutionErrors.PasswordChangeNotAllowed.name());
        }
    }
    
    private void checkPasswordHistory(final ExecutionErrorAccumulator ema,
            final PartyTypePasswordStringPolicyDetail policyDetail, final UserLoginPassword ulp, final String password) {
        var passwordHistory = policyDetail.getPasswordHistory();
        
        if(passwordHistory != null) {
            var userControl = Session.getModelController(UserControl.class);
            
            for(var userLoginPasswordString: userControl.getUserLoginPasswordStringHistory(ulp, passwordHistory)) {
                var salt = userLoginPasswordString.getSalt();
                
                if(Sha1Utils.getInstance().encode(salt, password).equals(userLoginPasswordString.getPassword())) {
                    ema.addExecutionError(ExecutionErrors.PasswordInRecentHistory.name(), passwordHistory);
                    break;
                }
            }
        }
    }
    
    private void checkMinimumPasswordLifetime(final Session session, final UserVisit userVisit,
            final ExecutionErrorAccumulator ema, final PartyTypePasswordStringPolicyDetail policyDetail, final UserLoginPasswordStringValue ulpsv) {
        var minimumPasswordLifetime = policyDetail.getMinimumPasswordLifetime();
        
        if(minimumPasswordLifetime != null) {
            var currentPasswordLifetime = session.getStartTime() - ulpsv.getChangedTime();
            
            if(currentPasswordLifetime < minimumPasswordLifetime) {
                var uomControl = Session.getModelController(UomControl.class);
                var timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
                var fmtMinimumPasswordLifetime = UnitOfMeasureUtils.getInstance().formatUnitOfMeasure(userVisit,
                        timeUnitOfMeasureKind, minimumPasswordLifetime);
                var fmtCurrentPasswordLifetime = UnitOfMeasureUtils.getInstance().formatUnitOfMeasure(userVisit,
                        timeUnitOfMeasureKind, Long.valueOf(currentPasswordLifetime));
                
                ema.addExecutionError(ExecutionErrors.PasswordMinimumLifetimeNotMet.name(), fmtMinimumPasswordLifetime, fmtCurrentPasswordLifetime);
            }
        }
    }
    
    private void checkLength(final ExecutionErrorAccumulator ema, final PartyTypePasswordStringPolicyDetail policyDetail,
            final String password) {
        var length = password.length();
        var minimumLegnth = policyDetail.getMinimumLength();
        var maximumLength = policyDetail.getMaximumLength();
        
        if(minimumLegnth != null && length < minimumLegnth) {
            ema.addExecutionError(ExecutionErrors.PasswordLessThanMinimumLength.name(), minimumLegnth);
        }
        
        if(maximumLength != null && length < maximumLength) {
            ema.addExecutionError(ExecutionErrors.PasswordGreaterThanMaximumLength.name(), maximumLength);
        }
    }
    
    private int getTypeCount(final Map<Integer, Integer> types, final byte type) {
        var count = types.get(Integer.valueOf(type));
        
        return count == null? 0: count;
    }
    
    private void checkCharacterTypes(final ExecutionErrorAccumulator ema, final PartyTypePasswordStringPolicyDetail policyDetail,
            final String password) {
        var requiredDigitCount = policyDetail.getRequiredDigitCount();
        var requiredLetterCount = policyDetail.getRequiredLetterCount();
        var requiredUpperCaseCount = policyDetail.getRequiredUpperCaseCount();
        var requiredLowerCaseCount = policyDetail.getRequiredLowerCaseCount();
        var maximumRepeated = policyDetail.getMaximumRepeated();
        var minimumCharacterTypes = policyDetail.getMinimumCharacterTypes();
        Map<Integer, Integer> types = new HashMap<>();
        var lastCh = 0;
        var repeat = 0;
        var maxRepeat = 0;
        
        for(int ch : StringUtils.getInstance().codePoints(password)) {
            Integer type = Character.getType(ch);
            var count = types.get(type);
            
            if(count == null) {
                types.put(type, 1);
            } else {
                types.put(type, count + 1);
            }
            
            if(ch == lastCh) {
                repeat++;
            } else {
                lastCh = ch;
                
                if(repeat > maxRepeat) {
                    maxRepeat = repeat;
                }
                
                repeat = 1;
            }
        }
        
        if(repeat > maxRepeat) {
            maxRepeat = repeat;
        }

        var upperCaseCount = getTypeCount(types, Character.UPPERCASE_LETTER);
        var lowerCaseCount = getTypeCount(types, Character.LOWERCASE_LETTER);
        var letterCount = upperCaseCount + lowerCaseCount;
        
        if(requiredDigitCount != null) {
            var digitCount = getTypeCount(types, Character.DECIMAL_DIGIT_NUMBER);
            
            if(digitCount < requiredDigitCount) {
                ema.addExecutionError(ExecutionErrors.PasswordRequiredDigitCountNotMet.name(), requiredDigitCount);
            }
        }
        
        if(requiredLetterCount != null) {
            if(letterCount < requiredLetterCount) {
                ema.addExecutionError(ExecutionErrors.PasswordRequiredLetterCountNotMet.name(), requiredLetterCount);
            }
        }
        
        if(requiredUpperCaseCount != null) {
            if(upperCaseCount < requiredUpperCaseCount) {
                ema.addExecutionError(ExecutionErrors.PasswordRequiredUpperCaseCountNotMet.name(), requiredUpperCaseCount);
            }
        }
        
        if(requiredLowerCaseCount != null) {
            if(lowerCaseCount < requiredLowerCaseCount) {
                ema.addExecutionError(ExecutionErrors.PasswordRequiredLowerCaseCountNotMet.name(), requiredLowerCaseCount);
            }
        }
        
        if(maximumRepeated != null) {
            if(maxRepeat < maximumRepeated) {
                ema.addExecutionError(ExecutionErrors.PasswordMaximumRepeatedExceeded.name(), maximumRepeated);
            }
        }
        
        if(minimumCharacterTypes != null) {
            var characterTypes = types.size();
            
            if(characterTypes < minimumCharacterTypes) {
                ema.addExecutionError(ExecutionErrors.PasswordMinimumCharacterTypesNotMet.name(), minimumCharacterTypes);
            }
        }
    }
    
    public PartyTypePasswordStringPolicy checkStringPassword(final Session session, final UserVisit userVisit, final ExecutionErrorAccumulator ema,
            final PartyType partyType, final UserLoginPassword ulp, final UserLoginPasswordStringValue ulpsv, final String password) {
        var partyControl = Session.getModelController(PartyControl.class);
        var policy = partyControl.getPartyTypePasswordStringPolicy(partyType);
        
        if(policy != null) {
            var policyDetail = policy.getLastDetail();
            
            if(ulp != null) {
                checkPasswordHistory(ema, policyDetail, ulp, password);
            }
            
            if(ulpsv != null) {
                checkAllowChange(ema, policyDetail);
                checkMinimumPasswordLifetime(session, userVisit, ema, policyDetail, ulpsv);
            }
            
            checkLength(ema, policyDetail, password);
            checkCharacterTypes(ema, policyDetail, password);
        }

        return policy;
    }
    
    public PartyTypePasswordStringPolicy checkStringPassword(final Session session, final UserVisit userVisit, final ExecutionErrorAccumulator ema,
            final Party party, final UserLoginPassword ulp, final UserLoginPasswordStringValue ulpsv, final String password) {
        var partyType = party.getLastDetail().getPartyType();
        
        return checkStringPassword(session, userVisit, ema, partyType, ulp, ulpsv, password);
    }
    
}
