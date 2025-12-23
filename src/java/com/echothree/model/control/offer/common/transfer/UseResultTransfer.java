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

import com.echothree.model.control.offer.common.transfer.UseTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class UseResultTransfer
        extends BaseTransfer {
    
    private String useName;
    private UseTransfer use;
    
    /** Creates a new instance of UseResultTransfer */
    public UseResultTransfer(String useName, UseTransfer use) {
        this.useName = useName;
        this.use = use;
    }

    /**
     * Returns the useName.
     * @return the useName
     */
    public String getUseName() {
        return useName;
    }

    /**
     * Sets the useName.
     * @param useName the useName to set
     */
    public void setUseName(String useName) {
        this.useName = useName;
    }

    /**
     * Returns the use.
     * @return the use
     */
    public UseTransfer getUse() {
        return use;
    }

    /**
     * Sets the use.
     * @param use the use to set
     */
    public void setUse(UseTransfer use) {
        this.use = use;
    }

 }
