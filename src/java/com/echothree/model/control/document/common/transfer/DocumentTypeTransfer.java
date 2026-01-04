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

package com.echothree.model.control.document.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

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
     * Returns the documentTypeName.
     * @return the documentTypeName
     */
    public String getDocumentTypeName() {
        return documentTypeName;
    }

    /**
     * Sets the documentTypeName.
     * @param documentTypeName the documentTypeName to set
     */
    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    /**
     * Returns the parentDocumentType.
     * @return the parentDocumentType
     */
    public DocumentTypeTransfer getParentDocumentType() {
        return parentDocumentType;
    }

    /**
     * Sets the parentDocumentType.
     * @param parentDocumentType the parentDocumentType to set
     */
    public void setParentDocumentType(DocumentTypeTransfer parentDocumentType) {
        this.parentDocumentType = parentDocumentType;
    }

    /**
     * Returns the mimeTypeUsageType.
     * @return the mimeTypeUsageType
     */
    public MimeTypeUsageTypeTransfer getMimeTypeUsageType() {
        return mimeTypeUsageType;
    }

    /**
     * Sets the mimeTypeUsageType.
     * @param mimeTypeUsageType the mimeTypeUsageType to set
     */
    public void setMimeTypeUsageType(MimeTypeUsageTypeTransfer mimeTypeUsageType) {
        this.mimeTypeUsageType = mimeTypeUsageType;
    }

    /**
     * Returns the maximumPages.
     * @return the maximumPages
     */
    public Integer getMaximumPages() {
        return maximumPages;
    }

    /**
     * Sets the maximumPages.
     * @param maximumPages the maximumPages to set
     */
    public void setMaximumPages(Integer maximumPages) {
        this.maximumPages = maximumPages;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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
