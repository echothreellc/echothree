// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.document.remote.transfer;

import com.echothree.model.control.core.remote.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class DocumentTypeTransfer
        extends BaseTransfer {
    
    private String documentTypeName;
    private DocumentTypeTransfer parentDocumentType;
    private MimeTypeUsageTypeTransfer mimeTypeUsageType;
    private Integer maximumPages;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of DocumentTypeTransfer */
    public DocumentTypeTransfer(String documentTypeName, DocumentTypeTransfer parentDocumentType, MimeTypeUsageTypeTransfer mimeTypeUsageType,
            Integer maximumPages, Boolean isDefault, Integer sortOrder, String description) {
        this.documentTypeName = documentTypeName;
        this.parentDocumentType = parentDocumentType;
        this.mimeTypeUsageType = mimeTypeUsageType;
        this.maximumPages = maximumPages;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the documentTypeName
     */
    public String getDocumentTypeName() {
        return documentTypeName;
    }

    /**
     * @param documentTypeName the documentTypeName to set
     */
    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    /**
     * @return the parentDocumentType
     */
    public DocumentTypeTransfer getParentDocumentType() {
        return parentDocumentType;
    }

    /**
     * @param parentDocumentType the parentDocumentType to set
     */
    public void setParentDocumentType(DocumentTypeTransfer parentDocumentType) {
        this.parentDocumentType = parentDocumentType;
    }

    /**
     * @return the mimeTypeUsageType
     */
    public MimeTypeUsageTypeTransfer getMimeTypeUsageType() {
        return mimeTypeUsageType;
    }

    /**
     * @param mimeTypeUsageType the mimeTypeUsageType to set
     */
    public void setMimeTypeUsageType(MimeTypeUsageTypeTransfer mimeTypeUsageType) {
        this.mimeTypeUsageType = mimeTypeUsageType;
    }

    /**
     * @return the maximumPages
     */
    public Integer getMaximumPages() {
        return maximumPages;
    }

    /**
     * @param maximumPages the maximumPages to set
     */
    public void setMaximumPages(Integer maximumPages) {
        this.maximumPages = maximumPages;
    }

    /**
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
