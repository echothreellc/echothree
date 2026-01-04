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

package com.echothree.model.control.payment.common.transfer;

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.party.common.transfer.NameSuffixTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.common.transfer.PersonalTitleTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class PartyPaymentMethodTransfer
        extends BaseTransfer {
    
    private String partyPaymentMethodName;
    private PartyTransfer party;
    private String description;
    private PaymentMethodTransfer paymentMethod;
    private Boolean deleteWhenUnused;
    private Boolean isDefault;
    private Integer sortOrder;
    
    private WorkflowEntityStatusTransfer partyPaymentMethodStatus;
    private ListWrapper<PartyPaymentMethodContactMechanismTransfer> partyPaymentMethodContactMechanisms;
    
    // Credit Card
    private String number;
    private Integer expirationMonth;
    private Integer expirationYear;
    private PersonalTitleTransfer personalTitle;
    private String firstName;
    private String middleName;
    private String lastName;
    private NameSuffixTransfer nameSuffix;
    private String name;
    private PartyContactMechanismTransfer billingPartyContactMechanism;
    private String issuerName;
    private PartyContactMechanismTransfer issuerPartyContactMechanism;
    
    // Credit Card Security Code
    private String securityCode;
    
    /** Creates a new instance of PartyPaymentMethodTransfer */
    public PartyPaymentMethodTransfer(String partyPaymentMethodName, PartyTransfer party, String description,
            PaymentMethodTransfer paymentMethod, Boolean deleteWhenUnused, Boolean isDefault, Integer sortOrder, WorkflowEntityStatusTransfer partyPaymentMethodStatus,
            String number, Integer expirationMonth, Integer expirationYear, PersonalTitleTransfer personalTitle, String firstName,
            String middleName, String lastName, NameSuffixTransfer nameSuffix, String name,
            PartyContactMechanismTransfer billingPartyContactMechanism, String issuerName, PartyContactMechanismTransfer issuerPartyContactMechanism,
            String securityCode) {
        this.partyPaymentMethodName = partyPaymentMethodName;
        this.party = party;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.deleteWhenUnused = deleteWhenUnused;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.partyPaymentMethodStatus = partyPaymentMethodStatus;
        this.number = number;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.personalTitle = personalTitle;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nameSuffix = nameSuffix;
        this.name = name;
        this.billingPartyContactMechanism = billingPartyContactMechanism;
        this.issuerName = issuerName;
        this.issuerPartyContactMechanism = issuerPartyContactMechanism;
        this.securityCode = securityCode;
    }
    
    public String getPartyPaymentMethodName() {
        return partyPaymentMethodName;
    }
    
    public void setPartyPaymentMethodName(String partyPaymentMethodName) {
        this.partyPaymentMethodName = partyPaymentMethodName;
    }
    
    public PartyTransfer getParty() {
        return party;
    }
    
    public void setParty(PartyTransfer party) {
        this.party = party;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public PaymentMethodTransfer getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(PaymentMethodTransfer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public Boolean getDeleteWhenUnused() {
        return deleteWhenUnused;
    }
    
    public void setDeleteWhenUnused(Boolean deleteWhenUnused) {
        this.deleteWhenUnused = deleteWhenUnused;
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
    
    public WorkflowEntityStatusTransfer getPartyPaymentMethodStatus() {
        return partyPaymentMethodStatus;
    }
    
    public void setPartyPaymentMethodStatus(WorkflowEntityStatusTransfer partyPaymentMethodStatus) {
        this.partyPaymentMethodStatus = partyPaymentMethodStatus;
    }
    
    public ListWrapper<PartyPaymentMethodContactMechanismTransfer> getPartyPaymentMethodContactMechanisms() {
        return partyPaymentMethodContactMechanisms;
    }
    
    public void setPartyPaymentMethodContactMechanisms(ListWrapper<PartyPaymentMethodContactMechanismTransfer> partyPaymentMethodContactMechanisms) {
        this.partyPaymentMethodContactMechanisms = partyPaymentMethodContactMechanisms;
    }
    
    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }
    
    public Integer getExpirationMonth() {
        return expirationMonth;
    }
    
    public void setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }
    
    public Integer getExpirationYear() {
        return expirationYear;
    }
    
    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }
    
    public PersonalTitleTransfer getPersonalTitle() {
        return personalTitle;
    }
    
    public void setPersonalTitle(PersonalTitleTransfer personalTitle) {
        this.personalTitle = personalTitle;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public NameSuffixTransfer getNameSuffix() {
        return nameSuffix;
    }
    
    public void setNameSuffix(NameSuffixTransfer nameSuffix) {
        this.nameSuffix = nameSuffix;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public PartyContactMechanismTransfer getBillingPartyContactMechanism() {
        return billingPartyContactMechanism;
    }
    
    public void setBillingPartyContactMechanism(PartyContactMechanismTransfer billingPartyContactMechanism) {
        this.billingPartyContactMechanism = billingPartyContactMechanism;
    }
    
    public String getIssuerName() {
        return issuerName;
    }
    
    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }
    
    public PartyContactMechanismTransfer getIssuerPartyContactMechanism() {
        return issuerPartyContactMechanism;
    }
    
    public void setIssuerPartyContactMechanism(PartyContactMechanismTransfer issuerPartyContactMechanism) {
        this.issuerPartyContactMechanism = issuerPartyContactMechanism;
    }
    
    public String getSecurityCode() {
        return securityCode;
    }
    
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
    
}
