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

package com.echothree.control.user.item.common.form;

import com.echothree.control.user.item.common.edit.ItemEdit;
import com.echothree.control.user.item.common.spec.ItemDeliveryTypeSpec;
import com.echothree.control.user.item.common.spec.ItemInventoryTypeSpec;
import com.echothree.control.user.item.common.spec.ItemPriceTypeSpec;
import com.echothree.control.user.item.common.spec.ItemTypeSpec;
import com.echothree.control.user.item.common.spec.ItemUseTypeSpec;
import com.echothree.control.user.party.common.spec.CompanySpec;
import com.echothree.control.user.uom.common.spec.UnitOfMeasureKindSpec;

public interface CreateItemForm
        extends ItemEdit, ItemTypeSpec, ItemUseTypeSpec, CompanySpec, ItemDeliveryTypeSpec, ItemInventoryTypeSpec,
        UnitOfMeasureKindSpec, ItemPriceTypeSpec {
    
    String getInventorySerialized();
    void setInventorySerialized(String inventorySerialized);
    
    String getItemStatus();
    void setItemStatus(String itemStatus);
    
}
