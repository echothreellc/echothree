// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.item.common.ItemOptions;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUnitTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUseTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeDetail;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeTranslation;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnit;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import java.util.Set;

public class HarmonizedTariffScheduleCodeTransferCache
        extends BaseItemTransferCache<HarmonizedTariffScheduleCode, HarmonizedTariffScheduleCodeTransfer> {
    
    GeoControl geoControl = Session.getModelController(GeoControl.class);

    boolean includeHarmonizedTariffScheduleCodeUses;
    
    /** Creates a new instance of HarmonizedTariffScheduleCodeTransferCache */
    public HarmonizedTariffScheduleCodeTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeHarmonizedTariffScheduleCodeUses = options.contains(ItemOptions.HarmonizedTariffScheduleCodeIncludeHarmonizedTariffScheduleCodeUses);
        }
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public HarmonizedTariffScheduleCodeTransfer getTransfer(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        HarmonizedTariffScheduleCodeTransfer harmonizedTariffScheduleCodeTransfer = get(harmonizedTariffScheduleCode);
        
        if(harmonizedTariffScheduleCodeTransfer == null) {
            HarmonizedTariffScheduleCodeDetail harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCode.getLastDetail();
            CountryTransfer countryTransfer = geoControl.getCountryTransfer(userVisit, harmonizedTariffScheduleCodeDetail.getCountryGeoCode());
            String harmonizedTariffScheduleCodeName = harmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodeName();
            HarmonizedTariffScheduleCodeUnit firstHarmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCodeDetail.getFirstHarmonizedTariffScheduleCodeUnit();
            HarmonizedTariffScheduleCodeUnitTransfer firstHarmonizedTariffScheduleCodeUnitTransfer = firstHarmonizedTariffScheduleCodeUnit == null ? null : itemControl.getHarmonizedTariffScheduleCodeUnitTransfer(userVisit, firstHarmonizedTariffScheduleCodeUnit);
            HarmonizedTariffScheduleCodeUnit secondHarmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCodeDetail.getSecondHarmonizedTariffScheduleCodeUnit();
            HarmonizedTariffScheduleCodeUnitTransfer secondHarmonizedTariffScheduleCodeUnitTransfer = secondHarmonizedTariffScheduleCodeUnit == null ? null : itemControl.getHarmonizedTariffScheduleCodeUnitTransfer(userVisit, secondHarmonizedTariffScheduleCodeUnit);
            Boolean isDefault = harmonizedTariffScheduleCodeDetail.getIsDefault();
            Integer sortOrder = harmonizedTariffScheduleCodeDetail.getSortOrder();
            HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation = itemControl.getBestHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, getLanguage());
            String description = harmonizedTariffScheduleCodeTranslation == null ? harmonizedTariffScheduleCodeName : harmonizedTariffScheduleCodeTranslation.getDescription();
            
            harmonizedTariffScheduleCodeTransfer = new HarmonizedTariffScheduleCodeTransfer(countryTransfer, harmonizedTariffScheduleCodeName,
                    firstHarmonizedTariffScheduleCodeUnitTransfer, secondHarmonizedTariffScheduleCodeUnitTransfer, isDefault, sortOrder, description);
            put(harmonizedTariffScheduleCode, harmonizedTariffScheduleCodeTransfer);
            
            if(includeHarmonizedTariffScheduleCodeUses) {
                List<HarmonizedTariffScheduleCodeUseTransfer> harmonizedTariffScheduleCodeUseTransfers = itemControl.getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCode(userVisit, harmonizedTariffScheduleCode);
                MapWrapper<HarmonizedTariffScheduleCodeUseTransfer> harmonizedTariffScheduleCodeUses = new MapWrapper<>();
                
                harmonizedTariffScheduleCodeUseTransfers.forEach((harmonizedTariffScheduleCodeUseTransfer) -> {
                    harmonizedTariffScheduleCodeUses.put(harmonizedTariffScheduleCodeUseTransfer.getHarmonizedTariffScheduleCodeUseType().getHarmonizedTariffScheduleCodeUseTypeName(), harmonizedTariffScheduleCodeUseTransfer);
                });
                
                harmonizedTariffScheduleCodeTransfer.setHarmonizedTariffScheduleCodeUses(harmonizedTariffScheduleCodeUses);
            }
        }
        
        return harmonizedTariffScheduleCodeTransfer;
    }
    
}
