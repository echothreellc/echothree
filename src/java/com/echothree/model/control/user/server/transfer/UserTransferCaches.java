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
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UserTransferCaches
        extends BaseTransferCaches {
    
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
    protected UserTransferCaches() {
        super();
    }
    
    public RecoveryAnswerTransferCache getRecoveryAnswerTransferCache() {
        if(recoveryAnswerTransferCache == null)
            recoveryAnswerTransferCache = CDI.current().select(RecoveryAnswerTransferCache.class).get();
        
        return recoveryAnswerTransferCache;
    }
    
    public RecoveryQuestionTransferCache getRecoveryQuestionTransferCache() {
        if(recoveryQuestionTransferCache == null)
            recoveryQuestionTransferCache = CDI.current().select(RecoveryQuestionTransferCache.class).get();
        
        return recoveryQuestionTransferCache;
    }
    
    public RecoveryQuestionDescriptionTransferCache getRecoveryQuestionDescriptionTransferCache() {
        if(recoveryQuestionDescriptionTransferCache == null)
            recoveryQuestionDescriptionTransferCache = CDI.current().select(RecoveryQuestionDescriptionTransferCache.class).get();
        
        return recoveryQuestionDescriptionTransferCache;
    }
    
    public UserKeyTransferCache getUserKeyTransferCache() {
        if(userKeyTransferCache == null)
            userKeyTransferCache = CDI.current().select(UserKeyTransferCache.class).get();
        
        return userKeyTransferCache;
    }
    
    public UserSessionTransferCache getUserSessionTransferCache() {
        if(userSessionTransferCache == null)
            userSessionTransferCache = CDI.current().select(UserSessionTransferCache.class).get();
        
        return userSessionTransferCache;
    }
    
    public UserVisitTransferCache getUserVisitTransferCache() {
        if(userVisitTransferCache == null)
            userVisitTransferCache = CDI.current().select(UserVisitTransferCache.class).get();
        
        return userVisitTransferCache;
    }
    
    public UserVisitGroupTransferCache getUserVisitGroupTransferCache() {
        if(userVisitGroupTransferCache == null)
            userVisitGroupTransferCache = CDI.current().select(UserVisitGroupTransferCache.class).get();
        
        return userVisitGroupTransferCache;
    }
    
    public UserLoginTransferCache getUserLoginTransferCache() {
        if(userLoginTransferCache == null)
            userLoginTransferCache = CDI.current().select(UserLoginTransferCache.class).get();

        return userLoginTransferCache;
    }

    public UserLoginPasswordTransferCache getUserLoginPasswordTransferCache() {
        if(userLoginPasswordTransferCache == null)
            userLoginPasswordTransferCache = CDI.current().select(UserLoginPasswordTransferCache.class).get();

        return userLoginPasswordTransferCache;
    }

    public UserLoginPasswordTypeTransferCache getUserLoginPasswordTypeTransferCache() {
        if(userLoginPasswordTypeTransferCache == null)
            userLoginPasswordTypeTransferCache = CDI.current().select(UserLoginPasswordTypeTransferCache.class).get();

        return userLoginPasswordTypeTransferCache;
    }

    public UserLoginPasswordEncoderTypeTransferCache getUserLoginPasswordEncoderTypeTransferCache() {
        if(userLoginPasswordEncoderTypeTransferCache == null)
            userLoginPasswordEncoderTypeTransferCache = CDI.current().select(UserLoginPasswordEncoderTypeTransferCache.class).get();

        return userLoginPasswordEncoderTypeTransferCache;
    }

}
