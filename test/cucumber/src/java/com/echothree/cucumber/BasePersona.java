// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.cucumber;

import com.echothree.control.user.contact.common.edit.ContactEmailAddressEdit;
import com.echothree.control.user.contact.common.edit.ContactPostalAddressEdit;
import com.echothree.control.user.contact.common.edit.ContactTelephoneEdit;
import com.echothree.control.user.contact.common.edit.ContactWebAddressEdit;
import com.echothree.control.user.item.common.edit.ItemEdit;
import com.echothree.model.data.user.common.pk.UserVisitPK;

public class BasePersona {

    public String persona;
    public UserVisitPK userVisitPK;

    // Contact
    public ContactEmailAddressEdit contactEmailAddressEdit;
    public ContactPostalAddressEdit contactPostalAddressEdit;
    public ContactTelephoneEdit contactTelephoneEdit;
    public ContactWebAddressEdit contactWebAddressEdit;

    public String lastEmailAddressContactMechanismName;
    public String lastPostalAddressContactMechanismName;
    public String lastTelephoneContactMechanismName;
    public String lastWebAddressContactMechanismName;

    // Core
    public String lastEntityRef;

    // Customer
    public String lastCustomerName;

    // Item
    public ItemEdit itemEdit;

    public String lastItemName;

    // Party
    public String lastPartyName;

    // Sales
    public String lastSalesOrderBatchName;
    public String lastSalesOrderName;
    public String lastSalesOrderLineSequence;

}
