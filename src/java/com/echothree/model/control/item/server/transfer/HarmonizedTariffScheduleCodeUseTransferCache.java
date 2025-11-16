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

import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUseTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUse;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class HarmonizedTariffScheduleCodeUseTransferCache
        extends BaseItemTransferCache<HarmonizedTariffScheduleCodeUse, HarmonizedTariffScheduleCodeUseTransfer> {

    ItemControl itemControl = Session.getModelController(ItemControl.class);

    /** Creates a new instance of HarmonizedTariffScheduleCodeUseTransferCache */
    protected HarmonizedTariffScheduleCodeUseTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public HarmonizedTariffScheduleCodeUseTransfer getTransfer(UserVisit userVisit, HarmonizedTariffScheduleCodeUse harmonizedTariffScheduleCodeUse) {
        var harmonizedTariffScheduleCodeUseTransfer = get(harmonizedTariffScheduleCodeUse);
        
        if(harmonizedTariffScheduleCodeUseTransfer == null) {
            var harmonizedTariffScheduleCode = itemControl.getHarmonizedTariffScheduleCodeTransfer(userVisit, harmonizedTariffScheduleCodeUse.getHarmonizedTariffScheduleCode());
            var harmonizedTariffScheduleCodeUseType = itemControl.getHarmonizedTariffScheduleCodeUseTypeTransfer(userVisit, harmonizedTariffScheduleCodeUse.getHarmonizedTariffScheduleCodeUseType());
            
            harmonizedTariffScheduleCodeUseTransfer = new HarmonizedTariffScheduleCodeUseTransfer(harmonizedTariffScheduleCode, harmonizedTariffScheduleCodeUseType);
            put(userVisit, harmonizedTariffScheduleCodeUse, harmonizedTariffScheduleCodeUseTransfer);
        }
        
        return harmonizedTariffScheduleCodeUseTransfer;
    }
    
}
