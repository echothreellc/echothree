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

package com.echothree.model.control.party.common.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class PartyTypeTransfer
        extends BaseTransfer {
    
    private String partyTypeName;
    private PartyTypeTransfer parentPartyType;
    private SequenceTypeTransfer billingAccountSequenceType;
    private Boolean allowUserLogins;
    private Boolean allowPartyAliases;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    private PartyTypeAuditPolicyTransfer partyTypeAuditPolicy;
    private PartyTypeLockoutPolicyTransfer partyTypeLockoutPolicy;
    private PartyTypePasswordStringPolicyTransfer partyTypePasswordStringPolicy;

    private ListWrapper<PartyAliasTypeTransfer> partyAliasTypes;
    
    /** Creates a new instance of PartyTypeTransfer */
    public PartyTypeTransfer(String partyTypeName, PartyTypeTransfer parentPartyType, SequenceTypeTransfer billingAccountSequenceType, Boolean allowUserLogins,
            Boolean allowPartyAliases, Boolean isDefault, Integer sortOrder, String description) {
        this.partyTypeName = partyTypeName;
        this.parentPartyType = parentPartyType;
        this.billingAccountSequenceType = billingAccountSequenceType;
        this.allowUserLogins = allowUserLogins;
        this.allowPartyAliases = allowPartyAliases;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getPartyTypeName() {
        return partyTypeName;
    }
    
    public void setPartyTypeName(String partyTypeName) {
        this.partyTypeName = partyTypeName;
    }
    
    public PartyTypeTransfer getParentPartyType() {
        return parentPartyType;
    }
    
    public void setParentPartyType(PartyTypeTransfer parentPartyType) {
        this.parentPartyType = parentPartyType;
    }
    
    public SequenceTypeTransfer getBillingAccountSequenceType() {
        return billingAccountSequenceType;
    }

    public void setBillingAccountSequenceType(SequenceTypeTransfer billingAccountSequenceType) {
        this.billingAccountSequenceType = billingAccountSequenceType;
    }
    
    public Boolean getAllowUserLogins() {
        return allowUserLogins;
    }
    
    public void setAllowUserLogins(Boolean allowUserLogins) {
        this.allowUserLogins = allowUserLogins;
    }
    
    /**
     * Returns the allowPartyAliases.
     * @return the allowPartyAliases
     */
    public Boolean getAllowPartyAliases() {
        return allowPartyAliases;
    }

    /**
     * Sets the allowPartyAliases.
     * @param allowPartyAliases the allowPartyAliases to set
     */
    public void setAllowPartyAliases(Boolean allowPartyAliases) {
        this.allowPartyAliases = allowPartyAliases;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public PartyTypeAuditPolicyTransfer getPartyTypeAuditPolicy() {
        return partyTypeAuditPolicy;
    }
    
    public void setPartyTypeAuditPolicy(PartyTypeAuditPolicyTransfer partyTypeAuditPolicy) {
        this.partyTypeAuditPolicy = partyTypeAuditPolicy;
    }
    
    public PartyTypeLockoutPolicyTransfer getPartyTypeLockoutPolicy() {
        return partyTypeLockoutPolicy;
    }
    
    public void setPartyTypeLockoutPolicy(PartyTypeLockoutPolicyTransfer partyTypeLockoutPolicy) {
        this.partyTypeLockoutPolicy = partyTypeLockoutPolicy;
    }
    
    public PartyTypePasswordStringPolicyTransfer getPartyTypePasswordStringPolicy() {
        return partyTypePasswordStringPolicy;
    }
    
    public void setPartyTypePasswordStringPolicy(PartyTypePasswordStringPolicyTransfer partyTypePasswordStringPolicy) {
        this.partyTypePasswordStringPolicy = partyTypePasswordStringPolicy;
    }

    /**
     * Returns the partyAliasTypes.
     * @return the partyAliasTypes
     */
    public ListWrapper<PartyAliasTypeTransfer> getPartyAliasTypes() {
        return partyAliasTypes;
    }

    /**
     * Sets the partyAliasTypes.
     * @param partyAliasTypes the partyAliasTypes to set
     */
    public void setPartyAliasTypes(ListWrapper<PartyAliasTypeTransfer> partyAliasTypes) {
        this.partyAliasTypes = partyAliasTypes;
    }

}
