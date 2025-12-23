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

package com.echothree.model.control.user.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class UserLoginPasswordTypeTransfer
        extends BaseTransfer {

    private String userLoginPasswordTypeName;
    private UserLoginPasswordEncoderTypeTransfer userLoginPasswordEncoderType;
    private String description;

    /** Creates a new instance of UserLoginPasswordTypeTransfer */
    public UserLoginPasswordTypeTransfer(String userLoginPasswordTypeName, UserLoginPasswordEncoderTypeTransfer userLoginPasswordEncoderType,
            String description) {
        this.userLoginPasswordTypeName = userLoginPasswordTypeName;
        this.userLoginPasswordEncoderType = userLoginPasswordEncoderType;
        this.description = description;
    }

    /**
     * Returns the userLoginPasswordTypeName.
     * @return the userLoginPasswordTypeName
     */
    public String getUserLoginPasswordTypeName() {
        return userLoginPasswordTypeName;
    }

    /**
     * Sets the userLoginPasswordTypeName.
     * @param userLoginPasswordTypeName the userLoginPasswordTypeName to set
     */
    public void setUserLoginPasswordTypeName(String userLoginPasswordTypeName) {
        this.userLoginPasswordTypeName = userLoginPasswordTypeName;
    }

    /**
     * Returns the userLoginPasswordEncoderType.
     * @return the userLoginPasswordEncoderType
     */
    public UserLoginPasswordEncoderTypeTransfer getUserLoginPasswordEncoderType() {
        return userLoginPasswordEncoderType;
    }

    /**
     * Sets the userLoginPasswordEncoderType.
     * @param userLoginPasswordEncoderType the userLoginPasswordEncoderType to set
     */
    public void setUserLoginPasswordEncoderType(UserLoginPasswordEncoderTypeTransfer userLoginPasswordEncoderType) {
        this.userLoginPasswordEncoderType = userLoginPasswordEncoderType;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
