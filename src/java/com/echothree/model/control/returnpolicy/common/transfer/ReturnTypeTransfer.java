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

package com.echothree.model.control.returnpolicy.common.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ReturnTypeTransfer
        extends BaseTransfer {
    
    private ReturnKindTransfer returnKind;
    private String returnTypeName;
    private SequenceTransfer returnSequence;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ReturnTypeTransfer */
    public ReturnTypeTransfer(ReturnKindTransfer returnKind, String returnTypeName, SequenceTransfer returnSequence, Boolean isDefault, Integer sortOrder,
            String description) {
        this.returnKind = returnKind;
        this.returnTypeName = returnTypeName;
        this.returnSequence = returnSequence;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the returnKind.
     * @return the returnKind
     */
    public ReturnKindTransfer getReturnKind() {
        return returnKind;
    }

    /**
     * Sets the returnKind.
     * @param returnKind the returnKind to set
     */
    public void setReturnKind(ReturnKindTransfer returnKind) {
        this.returnKind = returnKind;
    }

    /**
     * Returns the returnTypeName.
     * @return the returnTypeName
     */
    public String getReturnTypeName() {
        return returnTypeName;
    }

    /**
     * Sets the returnTypeName.
     * @param returnTypeName the returnTypeName to set
     */
    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName;
    }

    /**
     * Returns the returnSequence.
     * @return the returnSequence
     */
    public SequenceTransfer getReturnSequence() {
        return returnSequence;
    }

    /**
     * Sets the returnSequence.
     * @param returnSequence the returnSequence to set
     */
    public void setReturnSequence(SequenceTransfer returnSequence) {
        this.returnSequence = returnSequence;
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
