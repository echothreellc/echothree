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

public class ContentWebAddressTransfer
        extends BaseTransfer {
    
    private String contentWebAddressName;
    private ContentCollectionTransfer contentCollection;
    private String description;
    
    /** Creates a new instance of ContentWebAddressTransfer */
    public ContentWebAddressTransfer(String contentWebAddressName, ContentCollectionTransfer contentCollection, String description) {
        this.contentWebAddressName = contentWebAddressName;
        this.contentCollection = contentCollection;
        this.description = description;
    }

    /**
     * Returns the contentWebAddressName.
     * @return the contentWebAddressName
     */
    public String getContentWebAddressName() {
        return contentWebAddressName;
    }

    /**
     * Sets the contentWebAddressName.
     * @param contentWebAddressName the contentWebAddressName to set
     */
    public void setContentWebAddressName(String contentWebAddressName) {
        this.contentWebAddressName = contentWebAddressName;
    }

    /**
     * Returns the contentCollection.
     * @return the contentCollection
     */
    public ContentCollectionTransfer getContentCollection() {
        return contentCollection;
    }

    /**
     * Sets the contentCollection.
     * @param contentCollection the contentCollection to set
     */
    public void setContentCollection(ContentCollectionTransfer contentCollection) {
        this.contentCollection = contentCollection;
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
