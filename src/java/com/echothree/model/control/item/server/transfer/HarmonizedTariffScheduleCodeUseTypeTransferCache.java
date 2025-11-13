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

import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUseTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class HarmonizedTariffScheduleCodeUseTypeTransferCache
        extends BaseItemTransferCache<HarmonizedTariffScheduleCodeUseType, HarmonizedTariffScheduleCodeUseTypeTransfer> {
    
    /** Creates a new instance of HarmonizedTariffScheduleCodeUseTypeTransferCache */
    public HarmonizedTariffScheduleCodeUseTypeTransferCache(ItemControl itemControl) {
        super(itemControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public HarmonizedTariffScheduleCodeUseTypeTransfer getTransfer(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        var harmonizedTariffScheduleCodeUseTypeTransfer = get(harmonizedTariffScheduleCodeUseType);
        
        if(harmonizedTariffScheduleCodeUseTypeTransfer == null) {
            var harmonizedTariffScheduleCodeUseTypeDetail = harmonizedTariffScheduleCodeUseType.getLastDetail();
            var harmonizedTariffScheduleCodeUseTypeName = harmonizedTariffScheduleCodeUseTypeDetail.getHarmonizedTariffScheduleCodeUseTypeName();
            var isDefault = harmonizedTariffScheduleCodeUseTypeDetail.getIsDefault();
            var sortOrder = harmonizedTariffScheduleCodeUseTypeDetail.getSortOrder();
            var description = itemControl.getBestHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseType, getLanguage(userVisit));
            
            harmonizedTariffScheduleCodeUseTypeTransfer = new HarmonizedTariffScheduleCodeUseTypeTransfer(harmonizedTariffScheduleCodeUseTypeName, isDefault, sortOrder, description);
            put(userVisit, harmonizedTariffScheduleCodeUseType, harmonizedTariffScheduleCodeUseTypeTransfer);
        }
        
        return harmonizedTariffScheduleCodeUseTypeTransfer;
    }
    
}
