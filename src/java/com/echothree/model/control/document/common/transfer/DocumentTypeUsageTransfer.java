// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.util.common.transfer.BaseTransfer;

public class DocumentTypeUsageTransfer
        extends BaseTransfer {
    
    private DocumentTypeUsageTypeTransfer documentTypeUsageType;
    private DocumentTypeTransfer documentType;
    private Boolean isDefault;
    private Integer sortOrder;
    private Integer maximumInstances;

    /** Creates a new instance of DocumentTypeUsageTransfer */
    public DocumentTypeUsageTransfer(DocumentTypeUsageTypeTransfer documentTypeUsageType, DocumentTypeTransfer documentType, Boolean isDefault,
            Integer sortOrder, Integer maximumInstances) {
        this.documentTypeUsageType = documentTypeUsageType;
        this.documentType = documentType;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.maximumInstances = maximumInstances;
    }

    /**
     * @return the documentTypeUsageType
     */
    public DocumentTypeUsageTypeTransfer getDocumentTypeUsageType() {
        return documentTypeUsageType;
    }

    /**
     * @param documentTypeUsageType the documentTypeUsageType to set
     */
    public void setDocumentTypeUsageType(DocumentTypeUsageTypeTransfer documentTypeUsageType) {
        this.documentTypeUsageType = documentTypeUsageType;
    }

    /**
     * @return the documentType
     */
    public DocumentTypeTransfer getDocumentType() {
        return documentType;
    }

    /**
     * @param documentType the documentType to set
     */
    public void setDocumentType(DocumentTypeTransfer documentType) {
        this.documentType = documentType;
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
     * @return the maximumInstances
     */
    public Integer getMaximumInstances() {
        return maximumInstances;
    }

    /**
     * @param maximumInstances the maximumInstances to set
     */
    public void setMaximumInstances(Integer maximumInstances) {
        this.maximumInstances = maximumInstances;
    }
    
}
