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

package com.echothree.model.control.warehouse.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.PartyGroupTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class WarehouseTransfer
        extends PartyTransfer {
    
    private String warehouseName;
    private WarehouseTypeTransfer warehouseType;
    private Boolean isDefault;
    private Integer sortOrder;

    private Long locationsCount;
    private ListWrapper<LocationTransfer> locations;

    /** Creates a new instance of WarehouseTransfer */
    public WarehouseTransfer(String partyName, PartyTypeTransfer partyType, LanguageTransfer preferredLanguage, CurrencyTransfer preferredCurrency,
            TimeZoneTransfer preferredTimeZone, DateTimeFormatTransfer preferredDateTimeFormat, PersonTransfer person, PartyGroupTransfer partyGroup,
            String warehouseName, WarehouseTypeTransfer warehouseType, Boolean isDefault, Integer sortOrder) {
        super(partyName, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat, person, partyGroup, null);
        
        this.warehouseName = warehouseName;
        this.warehouseType = warehouseType;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }
    
    public String getWarehouseName() {
        return warehouseName;
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public WarehouseTypeTransfer getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(WarehouseTypeTransfer warehouseType) {
        this.warehouseType = warehouseType;
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

    public Long getLocationsCount() {
        return locationsCount;
    }

    public void setLocationsCount(final Long locationsCount) {
        this.locationsCount = locationsCount;
    }

    public ListWrapper<LocationTransfer> getLocations() {
        return locations;
    }

    public void setLocations(final ListWrapper<LocationTransfer> locations) {
        this.locations = locations;
    }

}
