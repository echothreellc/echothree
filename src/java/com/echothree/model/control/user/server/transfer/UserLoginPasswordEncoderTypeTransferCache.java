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

import com.echothree.model.control.user.common.transfer.UserLoginPasswordEncoderTypeTransfer;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.server.entity.UserLoginPasswordEncoderType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UserLoginPasswordEncoderTypeTransferCache
        extends BaseUserTransferCache<UserLoginPasswordEncoderType, UserLoginPasswordEncoderTypeTransfer> {

    UserControl userControl = Session.getModelController(UserControl.class);

    /** Creates a new instance of UserLoginPasswordEncoderTypeTransferCache */
    protected UserLoginPasswordEncoderTypeTransferCache() {
        super();
    }
    
    public UserLoginPasswordEncoderTypeTransfer getUserLoginPasswordEncoderTypeTransfer(UserVisit userVisit, UserLoginPasswordEncoderType userLoginPasswordEncoderType) {
        var userLoginPasswordEncoderTypeTransfer = get(userLoginPasswordEncoderType);
        
        if(userLoginPasswordEncoderTypeTransfer == null) {
            var userLoginPasswordEncoderTypeName = userLoginPasswordEncoderType.getUserLoginPasswordEncoderTypeName();
            var description = userControl.getBestUserLoginPasswordEncoderTypeDescription(userLoginPasswordEncoderType, getLanguage(userVisit));
            
            userLoginPasswordEncoderTypeTransfer = new UserLoginPasswordEncoderTypeTransfer(userLoginPasswordEncoderTypeName, description);
            put(userVisit, userLoginPasswordEncoderType, userLoginPasswordEncoderTypeTransfer);
        }
        
        return userLoginPasswordEncoderTypeTransfer;
    }
    
}
