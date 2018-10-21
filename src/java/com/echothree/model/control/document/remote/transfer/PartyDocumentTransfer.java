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

import com.echothree.model.control.party.remote.transfer.PartyTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class PartyDocumentTransfer
        extends BaseTransfer {
    
    private PartyTransfer party;
    private DocumentTransfer document;
    private Boolean isDefault;
    private Integer sortOrder;
    
    /** Creates a new instance of PartyDocumentTransfer */
    public PartyDocumentTransfer(PartyTransfer party, DocumentTransfer document, Boolean isDefault, Integer sortOrder) {
        this.party = party;
        this.document = document;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }

    /**
     * @return the party
     */
    public PartyTransfer getParty() {
        return party;
    }

    /**
     * @param party the party to set
     */
    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    /**
     * @return the document
     */
    public DocumentTransfer getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(DocumentTransfer document) {
        this.document = document;
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
