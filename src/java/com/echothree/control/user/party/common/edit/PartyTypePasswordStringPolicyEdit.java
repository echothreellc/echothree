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

package com.echothree.control.user.party.common.edit;

import com.echothree.util.common.form.BaseEdit;

public interface PartyTypePasswordStringPolicyEdit
        extends BaseEdit {
    
    String getForceChangeAfterCreate();
    void setForceChangeAfterCreate(String forceChangeAfterCreate);

    String getForceChangeAfterReset();
    void setForceChangeAfterReset(String forceChangeAfterReset);

    String getAllowChange();
    void setAllowChange(String allowChange);
    
    String getPasswordHistory();
    void setPasswordHistory(String passwordHistory);
    
    String getMinimumPasswordLifetime();
    void setMinimumPasswordLifetime(String minimumPasswordLifetime);
    
    String getMinimumPasswordLifetimeUnitOfMeasureTypeName();
    void setMinimumPasswordLifetimeUnitOfMeasureTypeName(String minimumPasswordLifetimeUnitOfMeasureTypeName);
    
    String getMaximumPasswordLifetime();
    void setMaximumPasswordLifetime(String maximumPasswordLifetime);
    
    String getMaximumPasswordLifetimeUnitOfMeasureTypeName();
    void setMaximumPasswordLifetimeUnitOfMeasureTypeName(String maximumPasswordLifetimeUnitOfMeasureTypeName);
    
    String getExpirationWarningTime();
    void setExpirationWarningTime(String expirationWarningTime);
    
    String getExpirationWarningTimeUnitOfMeasureTypeName();
    void setExpirationWarningTimeUnitOfMeasureTypeName(String expirationWarningTimeUnitOfMeasureTypeName);
    
    String getExpiredLoginsPermitted();
    void setExpiredLoginsPermitted(String expiredLoginsPermitted);
    
    String getMinimumLength();
    void setMinimumLength(String minimumLength);
    
    String getMaximumLength();
    void setMaximumLength(String maximumLength);
    
    String getRequiredDigitCount();
    void setRequiredDigitCount(String requiredDigitCount);
    
    String getRequiredLetterCount();
    void setRequiredLetterCount(String requiredLetterCount);
    
    String getRequiredUpperCaseCount();
    void setRequiredUpperCaseCount(String requiredUpperCaseCount);
    
    String getRequiredLowerCaseCount();
    void setRequiredLowerCaseCount(String requiredLowerCaseCount);
    
    String getMaximumRepeated();
    void setMaximumRepeated(String maximumRepeated);
    
    String getMinimumCharacterTypes();
    void setMinimumCharacterTypes(String minimumCharacterTypes);
    
}
