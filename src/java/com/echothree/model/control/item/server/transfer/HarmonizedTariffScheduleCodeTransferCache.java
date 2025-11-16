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
import com.echothree.model.control.item.common.ItemOptions;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUseTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCode;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class HarmonizedTariffScheduleCodeTransferCache
        extends BaseItemTransferCache<HarmonizedTariffScheduleCode, HarmonizedTariffScheduleCodeTransfer> {
    
    GeoControl geoControl = Session.getModelController(GeoControl.class);
    ItemControl itemControl = Session.getModelController(ItemControl.class);

    boolean includeHarmonizedTariffScheduleCodeUses;
    
    /** Creates a new instance of HarmonizedTariffScheduleCodeTransferCache */
    public HarmonizedTariffScheduleCodeTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeHarmonizedTariffScheduleCodeUses = options.contains(ItemOptions.HarmonizedTariffScheduleCodeIncludeHarmonizedTariffScheduleCodeUses);
        }
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public HarmonizedTariffScheduleCodeTransfer getTransfer(UserVisit userVisit, HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        var harmonizedTariffScheduleCodeTransfer = get(harmonizedTariffScheduleCode);
        
        if(harmonizedTariffScheduleCodeTransfer == null) {
            var harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCode.getLastDetail();
            var countryTransfer = geoControl.getCountryTransfer(userVisit, harmonizedTariffScheduleCodeDetail.getCountryGeoCode());
            var harmonizedTariffScheduleCodeName = harmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodeName();
            var firstHarmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCodeDetail.getFirstHarmonizedTariffScheduleCodeUnit();
            var firstHarmonizedTariffScheduleCodeUnitTransfer = firstHarmonizedTariffScheduleCodeUnit == null ? null : itemControl.getHarmonizedTariffScheduleCodeUnitTransfer(userVisit, firstHarmonizedTariffScheduleCodeUnit);
            var secondHarmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCodeDetail.getSecondHarmonizedTariffScheduleCodeUnit();
            var secondHarmonizedTariffScheduleCodeUnitTransfer = secondHarmonizedTariffScheduleCodeUnit == null ? null : itemControl.getHarmonizedTariffScheduleCodeUnitTransfer(userVisit, secondHarmonizedTariffScheduleCodeUnit);
            var isDefault = harmonizedTariffScheduleCodeDetail.getIsDefault();
            var sortOrder = harmonizedTariffScheduleCodeDetail.getSortOrder();
            var harmonizedTariffScheduleCodeTranslation = itemControl.getBestHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, getLanguage(userVisit));
            var description = harmonizedTariffScheduleCodeTranslation == null ? harmonizedTariffScheduleCodeName : harmonizedTariffScheduleCodeTranslation.getDescription();
            
            harmonizedTariffScheduleCodeTransfer = new HarmonizedTariffScheduleCodeTransfer(countryTransfer, harmonizedTariffScheduleCodeName,
                    firstHarmonizedTariffScheduleCodeUnitTransfer, secondHarmonizedTariffScheduleCodeUnitTransfer, isDefault, sortOrder, description);
            put(userVisit, harmonizedTariffScheduleCode, harmonizedTariffScheduleCodeTransfer);
            
            if(includeHarmonizedTariffScheduleCodeUses) {
                var harmonizedTariffScheduleCodeUseTransfers = itemControl.getHarmonizedTariffScheduleCodeUseTransfersByHarmonizedTariffScheduleCode(userVisit, harmonizedTariffScheduleCode);
                var harmonizedTariffScheduleCodeUses = new MapWrapper<HarmonizedTariffScheduleCodeUseTransfer>();
                
                harmonizedTariffScheduleCodeUseTransfers.forEach((harmonizedTariffScheduleCodeUseTransfer) -> {
                    harmonizedTariffScheduleCodeUses.put(harmonizedTariffScheduleCodeUseTransfer.getHarmonizedTariffScheduleCodeUseType().getHarmonizedTariffScheduleCodeUseTypeName(), harmonizedTariffScheduleCodeUseTransfer);
                });
                
                harmonizedTariffScheduleCodeTransfer.setHarmonizedTariffScheduleCodeUses(harmonizedTariffScheduleCodeUses);
            }
        }
        
        return harmonizedTariffScheduleCodeTransfer;
    }
    
}
