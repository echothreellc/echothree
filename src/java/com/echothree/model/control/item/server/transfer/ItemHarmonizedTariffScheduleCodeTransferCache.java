// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.item.common.ItemOptions;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUseTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemHarmonizedTariffScheduleCodeTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.data.item.server.entity.ItemHarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.entity.ItemHarmonizedTariffScheduleCodeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class ItemHarmonizedTariffScheduleCodeTransferCache
        extends BaseItemTransferCache<ItemHarmonizedTariffScheduleCode, ItemHarmonizedTariffScheduleCodeTransfer> {
    
    GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
    
    /** Creates a new instance of ItemHarmonizedTariffScheduleCodeTransferCache */
    public ItemHarmonizedTariffScheduleCodeTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeEntityAttributeGroups(options.contains(ItemOptions.ItemHarmonizedTariffScheduleCodeIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ItemOptions.ItemHarmonizedTariffScheduleCodeIncludeTagScopes));
        }

        setIncludeEntityInstance(true);
    }
    
    @Override
    public ItemHarmonizedTariffScheduleCodeTransfer getTransfer(ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode) {
        ItemHarmonizedTariffScheduleCodeTransfer itemHarmonizedTariffScheduleCodeTransfer = get(itemHarmonizedTariffScheduleCode);
        
        if(itemHarmonizedTariffScheduleCodeTransfer == null) {
            ItemHarmonizedTariffScheduleCodeDetail itemHarmonizedTariffScheduleCodeDetail = itemHarmonizedTariffScheduleCode.getLastDetail();
            ItemTransfer item = itemControl.getItemTransfer(userVisit, itemHarmonizedTariffScheduleCodeDetail.getItem());
            CountryTransfer countryGeoCode = geoControl.getCountryTransfer(userVisit, itemHarmonizedTariffScheduleCodeDetail.getCountryGeoCode());
            HarmonizedTariffScheduleCodeUseTypeTransfer harmonizedTariffScheduleCodeUseType = itemControl.getHarmonizedTariffScheduleCodeUseTypeTransfer(userVisit, itemHarmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodeUseType());
            HarmonizedTariffScheduleCodeTransfer harmonizedTariffScheduleCode = itemControl.getHarmonizedTariffScheduleCodeTransfer(userVisit, itemHarmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCode());
            
            itemHarmonizedTariffScheduleCodeTransfer = new ItemHarmonizedTariffScheduleCodeTransfer(item, countryGeoCode, harmonizedTariffScheduleCodeUseType,
                    harmonizedTariffScheduleCode);
            put(itemHarmonizedTariffScheduleCode, itemHarmonizedTariffScheduleCodeTransfer);
        }
        
        return itemHarmonizedTariffScheduleCodeTransfer;
    }
    
}
