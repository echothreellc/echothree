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

package com.echothree.model.control.offer.common.transfer;

import com.echothree.model.control.offer.common.transfer.UseTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class UseTypeResultTransfer
        extends BaseTransfer {
    
    private String useTypeName;
    private UseTypeTransfer useType;
    
    /** Creates a new instance of UseTypeResultTransfer */
    public UseTypeResultTransfer(String useTypeName, UseTypeTransfer useType) {
        this.useTypeName = useTypeName;
        this.useType = useType;
    }

    /**
     * Returns the useTypeName.
     * @return the useTypeName
     */
    public String getUseTypeName() {
        return useTypeName;
    }

    /**
     * Sets the useTypeName.
     * @param useTypeName the useTypeName to set
     */
    public void setUseTypeName(String useTypeName) {
        this.useTypeName = useTypeName;
    }

    /**
     * Returns the useType.
     * @return the useType
     */
    public UseTypeTransfer getUseType() {
        return useType;
    }

    /**
     * Sets the useType.
     * @param useType the useType to set
     */
    public void setUseType(UseTypeTransfer useType) {
        this.useType = useType;
    }

 }
