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

import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class UserTransferCaches
        extends BaseTransferCaches {
    
    protected UserControl userControl;
    
    protected RecoveryAnswerTransferCache recoveryAnswerTransferCache;
    protected RecoveryQuestionTransferCache recoveryQuestionTransferCache;
    protected RecoveryQuestionDescriptionTransferCache recoveryQuestionDescriptionTransferCache;
    protected UserKeyTransferCache userKeyTransferCache;
    protected UserSessionTransferCache userSessionTransferCache;
    protected UserVisitTransferCache userVisitTransferCache;
    protected UserVisitGroupTransferCache userVisitGroupTransferCache;
    protected UserLoginTransferCache userLoginTransferCache;
    protected UserLoginPasswordTransferCache userLoginPasswordTransferCache;
    protected UserLoginPasswordTypeTransferCache userLoginPasswordTypeTransferCache;
    protected UserLoginPasswordEncoderTypeTransferCache userLoginPasswordEncoderTypeTransferCache;
    
    /** Creates a new instance of UserTransferCaches */
    public UserTransferCaches(UserControl userControl) {
        super();
        
        this.userControl = userControl;
    }
    
    public RecoveryAnswerTransferCache getRecoveryAnswerTransferCache() {
        if(recoveryAnswerTransferCache == null)
            recoveryAnswerTransferCache = new RecoveryAnswerTransferCache(userControl);
        
        return recoveryAnswerTransferCache;
    }
    
    public RecoveryQuestionTransferCache getRecoveryQuestionTransferCache() {
        if(recoveryQuestionTransferCache == null)
            recoveryQuestionTransferCache = new RecoveryQuestionTransferCache(userControl);
        
        return recoveryQuestionTransferCache;
    }
    
    public RecoveryQuestionDescriptionTransferCache getRecoveryQuestionDescriptionTransferCache() {
        if(recoveryQuestionDescriptionTransferCache == null)
            recoveryQuestionDescriptionTransferCache = new RecoveryQuestionDescriptionTransferCache(userControl);
        
        return recoveryQuestionDescriptionTransferCache;
    }
    
    public UserKeyTransferCache getUserKeyTransferCache() {
        if(userKeyTransferCache == null)
            userKeyTransferCache = new UserKeyTransferCache(userControl);
        
        return userKeyTransferCache;
    }
    
    public UserSessionTransferCache getUserSessionTransferCache() {
        if(userSessionTransferCache == null)
            userSessionTransferCache = new UserSessionTransferCache(userControl);
        
        return userSessionTransferCache;
    }
    
    public UserVisitTransferCache getUserVisitTransferCache() {
        if(userVisitTransferCache == null)
            userVisitTransferCache = new UserVisitTransferCache(userControl);
        
        return userVisitTransferCache;
    }
    
    public UserVisitGroupTransferCache getUserVisitGroupTransferCache() {
        if(userVisitGroupTransferCache == null)
            userVisitGroupTransferCache = new UserVisitGroupTransferCache(userControl);
        
        return userVisitGroupTransferCache;
    }
    
    public UserLoginTransferCache getUserLoginTransferCache() {
        if(userLoginTransferCache == null)
            userLoginTransferCache = new UserLoginTransferCache(userControl);

        return userLoginTransferCache;
    }

    public UserLoginPasswordTransferCache getUserLoginPasswordTransferCache() {
        if(userLoginPasswordTransferCache == null)
            userLoginPasswordTransferCache = new UserLoginPasswordTransferCache(userControl);

        return userLoginPasswordTransferCache;
    }

    public UserLoginPasswordTypeTransferCache getUserLoginPasswordTypeTransferCache() {
        if(userLoginPasswordTypeTransferCache == null)
            userLoginPasswordTypeTransferCache = new UserLoginPasswordTypeTransferCache(userControl);

        return userLoginPasswordTypeTransferCache;
    }

    public UserLoginPasswordEncoderTypeTransferCache getUserLoginPasswordEncoderTypeTransferCache() {
        if(userLoginPasswordEncoderTypeTransferCache == null)
            userLoginPasswordEncoderTypeTransferCache = new UserLoginPasswordEncoderTypeTransferCache(userControl);

        return userLoginPasswordEncoderTypeTransferCache;
    }

}
