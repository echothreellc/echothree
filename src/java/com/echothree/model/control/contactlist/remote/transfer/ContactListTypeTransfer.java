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

package com.echothree.model.control.contactlist.remote.transfer;

import com.echothree.model.control.chain.remote.transfer.ChainTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class ContactListTypeTransfer
        extends BaseTransfer {
    
    private String contactListTypeName;
    private ChainTransfer confirmationRequestChain;
    private ChainTransfer subscribeChain;
    private ChainTransfer unsubscribeChain;
    private Boolean usedForSolicitation;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ContactListTypeTransfer */
    public ContactListTypeTransfer(String contactListTypeName, ChainTransfer confirmationRequestChain, ChainTransfer subscribeChain,
            ChainTransfer unsubscribeChain, Boolean usedForSolicitation, Boolean isDefault, Integer sortOrder, String description) {
        this.contactListTypeName = contactListTypeName;
        this.confirmationRequestChain = confirmationRequestChain;
        this.subscribeChain = subscribeChain;
        this.unsubscribeChain = unsubscribeChain;
        this.usedForSolicitation = usedForSolicitation;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the contactListTypeName
     */
    public String getContactListTypeName() {
        return contactListTypeName;
    }

    /**
     * @param contactListTypeName the contactListTypeName to set
     */
    public void setContactListTypeName(String contactListTypeName) {
        this.contactListTypeName = contactListTypeName;
    }

    /**
     * @return the confirmationRequestChain
     */
    public ChainTransfer getConfirmationRequestChain() {
        return confirmationRequestChain;
    }

    /**
     * @param confirmationRequestChain the confirmationRequestChain to set
     */
    public void setConfirmationRequestChain(ChainTransfer confirmationRequestChain) {
        this.confirmationRequestChain = confirmationRequestChain;
    }

    /**
     * @return the subscribeChain
     */
    public ChainTransfer getSubscribeChain() {
        return subscribeChain;
    }

    /**
     * @param subscribeChain the subscribeChain to set
     */
    public void setSubscribeChain(ChainTransfer subscribeChain) {
        this.subscribeChain = subscribeChain;
    }

    /**
     * @return the unsubscribeChain
     */
    public ChainTransfer getUnsubscribeChain() {
        return unsubscribeChain;
    }

    /**
     * @param unsubscribeChain the unsubscribeChain to set
     */
    public void setUnsubscribeChain(ChainTransfer unsubscribeChain) {
        this.unsubscribeChain = unsubscribeChain;
    }

    /**
     * @return the usedForSolicitation
     */
    public Boolean getUsedForSolicitation() {
        return usedForSolicitation;
    }

    /**
     * @param usedForSolicitation the usedForSolicitation to set
     */
    public void setUsedForSolicitation(Boolean usedForSolicitation) {
        this.usedForSolicitation = usedForSolicitation;
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
