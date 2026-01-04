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

public class ContentCatalogResultTransfer
        extends BaseTransfer {
    
    private String contentCollectionName;
    private String contentCatalogName;
    private ContentCatalogTransfer contentCatalog;
    
    /** Creates a new instance of ItemResultTransfer */
    public ContentCatalogResultTransfer(String contentCollectionName, String contentCatalogName,
            ContentCatalogTransfer contentCatalog) {
        this.contentCollectionName = contentCollectionName;
        this.contentCatalogName = contentCatalogName;
        this.contentCatalog = contentCatalog;
    }

    /**
     * Returns the contentCollectionName.
     * @return the contentCollectionName
     */
    public String getContentCollectionName() {
        return contentCollectionName;
    }

    /**
     * Sets the contentCollectionName.
     * @param contentCollectionName the contentCollectionName to set
     */
    public void setContentCollectionName(String contentCollectionName) {
        this.contentCollectionName = contentCollectionName;
    }

    /**
     * Returns the contentCatalogName.
     * @return the contentCatalogName
     */
    public String getContentCatalogName() {
        return contentCatalogName;
    }

    /**
     * Sets the contentCatalogName.
     * @param contentCatalogName the contentCatalogName to set
     */
    public void setContentCatalogName(String contentCatalogName) {
        this.contentCatalogName = contentCatalogName;
    }

    /**
     * Returns the contentCatalog.
     * @return the contentCatalog
     */
    public ContentCatalogTransfer getContentCatalog() {
        return contentCatalog;
    }

    /**
     * Sets the contentCatalog.
     * @param contentCatalog the contentCatalog to set
     */
    public void setContentCatalog(ContentCatalogTransfer contentCatalog) {
        this.contentCatalog = contentCatalog;
    }

}
