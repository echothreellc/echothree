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

package com.echothree.control.user.warehouse.common.form;

import com.echothree.control.user.warehouse.common.spec.WarehouseSpec;
import com.echothree.control.user.warehouse.common.spec.WarehouseTypeSpec;

public interface CreateWarehouseForm
        extends WarehouseSpec, WarehouseTypeSpec {
    
    String getName();
    void setName(String name);
    
    String getPreferredLanguageIsoName();
    void setPreferredLanguageIsoName(String preferredLanguageIsoName);
    
    String getPreferredCurrencyIsoName();
    void setPreferredCurrencyIsoName(String preferredCurrencyIsoName);
    
    String getPreferredJavaTimeZoneName();
    void setPreferredJavaTimeZoneName(String preferredJavaTimeZoneName);
    
    String getPreferredDateTimeFormatName();
    void setPreferredDateTimeFormatName(String preferredDateTimeFormatName);
    
    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
    String getInventoryMovePrinterGroupName();
    void setInventoryMovePrinterGroupName(String inventoryMovePrinterGroupName);
    
    String getPicklistPrinterGroupName();
    void setPicklistPrinterGroupName(String picklistPrinterGroupName);
    
    String getPackingListPrinterGroupName();
    void setPackingListPrinterGroupName(String packingListPrinterGroupName);
    
    String getShippingManifestPrinterGroupName();
    void setShippingManifestPrinterGroupName(String shippingManifestPrinterGroupName);
    
}
