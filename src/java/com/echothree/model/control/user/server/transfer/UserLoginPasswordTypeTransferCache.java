// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.user.common.transfer.UserLoginPasswordEncoderTypeTransfer;
import com.echothree.model.control.user.common.transfer.UserLoginPasswordTypeTransfer;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.server.entity.UserLoginPasswordType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class UserLoginPasswordTypeTransferCache
        extends BaseUserTransferCache<UserLoginPasswordType, UserLoginPasswordTypeTransfer> {
    
    /** Creates a new instance of UserLoginPasswordTypeTransferCache */
    public UserLoginPasswordTypeTransferCache(UserVisit userVisit, UserControl userControl) {
        super(userVisit, userControl);
    }
    
    public UserLoginPasswordTypeTransfer getUserLoginPasswordTypeTransfer(UserLoginPasswordType userLoginPasswordType) {
        UserLoginPasswordTypeTransfer userLoginPasswordTypeTransfer = get(userLoginPasswordType);
        
        if(userLoginPasswordTypeTransfer == null) {
            String userLoginPasswordTypeName = userLoginPasswordType.getUserLoginPasswordTypeName();
            UserLoginPasswordEncoderTypeTransfer userLoginPasswordEncoderType = userControl.getUserLoginPasswordEncoderTypeTransfer(userVisit, userLoginPasswordType.getUserLoginPasswordEncoderType());
            String description = userControl.getBestUserLoginPasswordTypeDescription(userLoginPasswordType, getLanguage());
            
            userLoginPasswordTypeTransfer = new UserLoginPasswordTypeTransfer(userLoginPasswordTypeName, userLoginPasswordEncoderType, description);
            put(userLoginPasswordType, userLoginPasswordTypeTransfer);
        }
        
        return userLoginPasswordTypeTransfer;
    }
    
}
