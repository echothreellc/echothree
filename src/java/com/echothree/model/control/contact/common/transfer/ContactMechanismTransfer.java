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

package com.echothree.model.control.contact.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ContactMechanismTransfer
        extends BaseTransfer {
    
    private String contactMechanismName;
    private ContactMechanismTypeTransfer contactMechanismType;
    private Boolean allowSolicitation;
    
    private ContactEmailAddressTransfer contactEmailAddress;
    private ContactPostalAddressTransfer contactPostalAddress;
    private ContactTelephoneTransfer contactTelephone;
    private ContactWebAddressTransfer contactWebAddress;
    private ContactInet4AddressTransfer contactInet4Address;
    
    /** Creates a new instance of ContactMechanismTransfer */
    public ContactMechanismTransfer(String contactMechanismName, ContactMechanismTypeTransfer contactMechanismType,
            Boolean allowSolicitation) {
        this.contactMechanismName = contactMechanismName;
        this.contactMechanismType = contactMechanismType;
        this.allowSolicitation = allowSolicitation;
    }

    /**
     * Returns the contactMechanismName.
     * @return the contactMechanismName
     */
    public String getContactMechanismName() {
        return contactMechanismName;
    }

    /**
     * Sets the contactMechanismName.
     * @param contactMechanismName the contactMechanismName to set
     */
    public void setContactMechanismName(String contactMechanismName) {
        this.contactMechanismName = contactMechanismName;
    }

    /**
     * Returns the contactMechanismType.
     * @return the contactMechanismType
     */
    public ContactMechanismTypeTransfer getContactMechanismType() {
        return contactMechanismType;
    }

    /**
     * Sets the contactMechanismType.
     * @param contactMechanismType the contactMechanismType to set
     */
    public void setContactMechanismType(ContactMechanismTypeTransfer contactMechanismType) {
        this.contactMechanismType = contactMechanismType;
    }

    /**
     * Returns the allowSolicitation.
     * @return the allowSolicitation
     */
    public Boolean getAllowSolicitation() {
        return allowSolicitation;
    }

    /**
     * Sets the allowSolicitation.
     * @param allowSolicitation the allowSolicitation to set
     */
    public void setAllowSolicitation(Boolean allowSolicitation) {
        this.allowSolicitation = allowSolicitation;
    }

    /**
     * Returns the contactEmailAddress.
     * @return the contactEmailAddress
     */
    public ContactEmailAddressTransfer getContactEmailAddress() {
        return contactEmailAddress;
    }

    /**
     * Sets the contactEmailAddress.
     * @param contactEmailAddress the contactEmailAddress to set
     */
    public void setContactEmailAddress(ContactEmailAddressTransfer contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    /**
     * Returns the contactPostalAddress.
     * @return the contactPostalAddress
     */
    public ContactPostalAddressTransfer getContactPostalAddress() {
        return contactPostalAddress;
    }

    /**
     * Sets the contactPostalAddress.
     * @param contactPostalAddress the contactPostalAddress to set
     */
    public void setContactPostalAddress(ContactPostalAddressTransfer contactPostalAddress) {
        this.contactPostalAddress = contactPostalAddress;
    }

    /**
     * Returns the contactTelephone.
     * @return the contactTelephone
     */
    public ContactTelephoneTransfer getContactTelephone() {
        return contactTelephone;
    }

    /**
     * Sets the contactTelephone.
     * @param contactTelephone the contactTelephone to set
     */
    public void setContactTelephone(ContactTelephoneTransfer contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    /**
     * Returns the contactWebAddress.
     * @return the contactWebAddress
     */
    public ContactWebAddressTransfer getContactWebAddress() {
        return contactWebAddress;
    }

    /**
     * Sets the contactWebAddress.
     * @param contactWebAddress the contactWebAddress to set
     */
    public void setContactWebAddress(ContactWebAddressTransfer contactWebAddress) {
        this.contactWebAddress = contactWebAddress;
    }

    /**
     * Returns the contactInet4Address.
     * @return the contactInet4Address
     */
    public ContactInet4AddressTransfer getContactInet4Address() {
        return contactInet4Address;
    }

    /**
     * Sets the contactInet4Address.
     * @param contactInet4Address the contactInet4Address to set
     */
    public void setContactInet4Address(ContactInet4AddressTransfer contactInet4Address) {
        this.contactInet4Address = contactInet4Address;
    }
    
}
