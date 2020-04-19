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
import com.echothree.control.user.core.common.edit.EntityAttributeEdit;
import com.echothree.control.user.core.common.edit.EntityListItemEdit;
import com.echothree.control.user.core.common.form.CreateEntityAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityListItemForm;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityListItemForm;
import com.echothree.control.user.core.common.spec.EntityAttributeSpec;
import com.echothree.control.user.core.common.spec.EntityListItemSpec;
import com.echothree.control.user.item.common.form.CreateItemForm;
import com.echothree.control.user.party.common.form.CreateCustomerWithLoginForm;
import com.echothree.control.user.payment.common.form.CreatePartyPaymentMethodForm;
import com.echothree.cucumber.authentication.UserVisits;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import javax.naming.NamingException;

public class BasePersona {

    public String persona;
    public UserVisitPK userVisitPK;

    public BasePersona(String persona)
            throws NamingException {
        this.persona = persona;
        userVisitPK = UserVisits.getUserVisitPK();
    }

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
    public CreateEntityAttributeForm createEntityAttributeForm;
    public DeleteEntityAttributeForm deleteEntityAttributeForm;
    public EntityAttributeSpec entityAttributeSpec;
    public EntityAttributeEdit entityAttributeEdit;

    public CreateEntityListItemForm createEntityListItemForm;
    public DeleteEntityListItemForm deleteEntityListItemForm;
    public EntityListItemSpec entityListItemSpec;
    public EntityListItemEdit entityListItemEdit;

    public String lastEntityAttributeName;
    public String lastEntityListItemName;
    public String lastEntityRef;

    // Customer
    public CreateCustomerWithLoginForm createCustomerWithLoginForm;

    public String lastCustomerName;

    // Item
    public CreateItemForm createItemForm;

    public String lastItemName;

    // Party
    public String lastPartyName;

    // Payment
    public CreatePartyPaymentMethodForm createPartyPaymentMethodForm;

    public String lastPartyPaymentMethodName;

    // Sales
    public String lastSalesOrderBatchName;
    public String lastSalesOrderName;
    public String lastSalesOrderLineSequence;

}
