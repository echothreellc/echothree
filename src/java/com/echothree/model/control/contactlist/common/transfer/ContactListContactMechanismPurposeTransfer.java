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

package com.echothree.model.control.contactlist.common.transfer;

import com.echothree.model.control.contact.common.transfer.ContactMechanismPurposeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public final class ContactListContactMechanismPurposeTransfer
        extends BaseTransfer {
    
    private final ContactListTransfer contactList;
    private final ContactMechanismPurposeTransfer contactMechanismPurpose;
    private final Boolean isDefault;
    private final Integer sortOrder;
    
    /** Creates a new instance of ContactListContactMechanismPurposeTransfer */
    public ContactListContactMechanismPurposeTransfer(final ContactListTransfer contactList, final ContactMechanismPurposeTransfer contactMechanismPurpose,
            final Boolean isDefault, final Integer sortOrder) {
        this.contactList = contactList;
        this.contactMechanismPurpose = contactMechanismPurpose;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the contactList.
     * @return the contactList
     */
    public final ContactListTransfer getContactList() {
        return contactList;
    }

    /**
     * Returns the contactMechanismPurpose.
     * @return the contactMechanismPurpose
     */
    public final ContactMechanismPurposeTransfer getContactMechanismPurpose() {
        return contactMechanismPurpose;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public final Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public final Integer getSortOrder() {
        return sortOrder;
    }
    
}
