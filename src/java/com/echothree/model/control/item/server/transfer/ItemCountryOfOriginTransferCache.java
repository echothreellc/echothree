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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.item.common.transfer.ItemCountryOfOriginTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemCountryOfOrigin;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.PercentUtils;

public class ItemCountryOfOriginTransferCache
        extends BaseItemTransferCache<ItemCountryOfOrigin, ItemCountryOfOriginTransfer> {
    
    GeoControl geoControl = Session.getModelController(GeoControl.class);
    
    /** Creates a new instance of ItemCountryOfOriginTransferCache */
    public ItemCountryOfOriginTransferCache(ItemControl itemControl) {
        super(itemControl);
    }
    
    @Override
    public ItemCountryOfOriginTransfer getTransfer(ItemCountryOfOrigin itemCountryOfOrigin) {
        var itemCountryOfOriginTransfer = get(itemCountryOfOrigin);
        
        if(itemCountryOfOriginTransfer == null) {
            var item = itemControl.getItemTransfer(userVisit, itemCountryOfOrigin.getItem());
            var countryGeoCode = geoControl.getCountryTransfer(userVisit, itemCountryOfOrigin.getCountryGeoCode());
            var unformattedPercent = itemCountryOfOrigin.getPercent();
            var percent = PercentUtils.getInstance().formatFractionalPercent(unformattedPercent);
            
            itemCountryOfOriginTransfer = new ItemCountryOfOriginTransfer(item, countryGeoCode, unformattedPercent, percent);
            put(userVisit, itemCountryOfOrigin, itemCountryOfOriginTransfer);
        }
        
        return itemCountryOfOriginTransfer;
    }
    
}
