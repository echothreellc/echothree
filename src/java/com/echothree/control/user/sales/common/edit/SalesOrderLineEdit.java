// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.sales.common.edit;

import com.echothree.control.user.contact.common.spec.ContactMechanismSpec;
import com.echothree.control.user.inventory.common.spec.InventoryConditionSpec;
import com.echothree.control.user.item.common.spec.ItemSpec;
import com.echothree.control.user.offer.common.spec.SourceSpec;
import com.echothree.control.user.shipping.common.spec.ShippingMethodSpec;
import com.echothree.util.common.form.BaseEdit;

public interface SalesOrderLineEdit
        extends BaseEdit, ContactMechanismSpec, ShippingMethodSpec, ItemSpec, InventoryConditionSpec, SourceSpec {
    
    String getOrderShipmentGroupSequence();
    void setOrderShipmentGroupSequence(String orderShipmentGroupSequence);
    
    String getOrderLineSequence();
    void setOrderLineSequence(String orderLineSequence);
    
    String getParentOrderLineSequence();
    void setParentOrderLineSequence(String parentOrderLineSequence);
    
    String getUnitOfMeasureTypeName();
    void setUnitOfMeasureTypeName(String unitOfMeasureTypeName);
    
    String getQuantity();
    void setQuantity(String quantity);
    
    String getUnitAmount();
    void setUnitAmount(String unitAmount);
    
    String getDescription();
    void setDescription(String description);
    
    String getCancellationPolicyName();
    void setCancellationPolicyName(String cancellationPolicyName);
    
    String getReturnPolicyName();
    void setReturnPolicyName(String returnPolicyName);
    
    String getTaxable();
    void setTaxable(String taxable);

}
