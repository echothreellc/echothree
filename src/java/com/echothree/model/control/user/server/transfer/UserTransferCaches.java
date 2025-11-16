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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class UserTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    RecoveryAnswerTransferCache recoveryAnswerTransferCache;
    
    @Inject
    RecoveryQuestionTransferCache recoveryQuestionTransferCache;
    
    @Inject
    RecoveryQuestionDescriptionTransferCache recoveryQuestionDescriptionTransferCache;
    
    @Inject
    UserKeyTransferCache userKeyTransferCache;
    
    @Inject
    UserSessionTransferCache userSessionTransferCache;
    
    @Inject
    UserVisitTransferCache userVisitTransferCache;
    
    @Inject
    UserVisitGroupTransferCache userVisitGroupTransferCache;
    
    @Inject
    UserLoginTransferCache userLoginTransferCache;
    
    @Inject
    UserLoginPasswordTransferCache userLoginPasswordTransferCache;
    
    @Inject
    UserLoginPasswordTypeTransferCache userLoginPasswordTypeTransferCache;
    
    @Inject
    UserLoginPasswordEncoderTypeTransferCache userLoginPasswordEncoderTypeTransferCache;

    /** Creates a new instance of UserTransferCaches */
    protected UserTransferCaches() {
        super();
    }
    
    public RecoveryAnswerTransferCache getRecoveryAnswerTransferCache() {
        return recoveryAnswerTransferCache;
    }
    
    public RecoveryQuestionTransferCache getRecoveryQuestionTransferCache() {
        return recoveryQuestionTransferCache;
    }
    
    public RecoveryQuestionDescriptionTransferCache getRecoveryQuestionDescriptionTransferCache() {
        return recoveryQuestionDescriptionTransferCache;
    }
    
    public UserKeyTransferCache getUserKeyTransferCache() {
        return userKeyTransferCache;
    }
    
    public UserSessionTransferCache getUserSessionTransferCache() {
        return userSessionTransferCache;
    }
    
    public UserVisitTransferCache getUserVisitTransferCache() {
        return userVisitTransferCache;
    }
    
    public UserVisitGroupTransferCache getUserVisitGroupTransferCache() {
        return userVisitGroupTransferCache;
    }
    
    public UserLoginTransferCache getUserLoginTransferCache() {
        return userLoginTransferCache;
    }

    public UserLoginPasswordTransferCache getUserLoginPasswordTransferCache() {
        return userLoginPasswordTransferCache;
    }

    public UserLoginPasswordTypeTransferCache getUserLoginPasswordTypeTransferCache() {
        return userLoginPasswordTypeTransferCache;
    }

    public UserLoginPasswordEncoderTypeTransferCache getUserLoginPasswordEncoderTypeTransferCache() {
        return userLoginPasswordEncoderTypeTransferCache;
    }

}
