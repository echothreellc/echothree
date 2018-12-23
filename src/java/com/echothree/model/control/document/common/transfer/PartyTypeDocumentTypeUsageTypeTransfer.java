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

import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PartyTypeDocumentTypeUsageTypeTransfer
        extends BaseTransfer {
    
    private PartyTypeTransfer partyType;
    private DocumentTypeUsageTypeTransfer documentTypeUsageType;
    private Boolean isDefault;
    private Integer sortOrder;

    /** Creates a new instance of PartyTypeDocumentTypeUsageTypeTransfer */
    public PartyTypeDocumentTypeUsageTypeTransfer(PartyTypeTransfer partyType, DocumentTypeUsageTypeTransfer documentTypeUsageType, Boolean isDefault,
            Integer sortOrder) {
        this.partyType = partyType;
        this.documentTypeUsageType = documentTypeUsageType;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }

    /**
     * @return the partyType
     */
    public PartyTypeTransfer getPartyType() {
        return partyType;
    }

    /**
     * @param partyType the partyType to set
     */
    public void setPartyType(PartyTypeTransfer partyType) {
        this.partyType = partyType;
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
    
}
