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

package com.echothree.model.control.content.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ContentPageAreaTypeTransfer
        extends BaseTransfer {
    
    private String contentPageAreaTypeName;
    private String description;
    
    /** Creates a new instance of ContentPageAreaTypeTransfer */
    public ContentPageAreaTypeTransfer(String contentPageAreaTypeName, String description) {
        this.contentPageAreaTypeName = contentPageAreaTypeName;
        this.description = description;
    }

    /**
     * Returns the contentPageAreaTypeName.
     * @return the contentPageAreaTypeName
     */
    public String getContentPageAreaTypeName() {
        return contentPageAreaTypeName;
    }

    /**
     * Sets the contentPageAreaTypeName.
     * @param contentPageAreaTypeName the contentPageAreaTypeName to set
     */
    public void setContentPageAreaTypeName(String contentPageAreaTypeName) {
        this.contentPageAreaTypeName = contentPageAreaTypeName;
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
