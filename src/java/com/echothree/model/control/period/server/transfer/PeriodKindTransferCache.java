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

package com.echothree.model.control.period.server.transfer;

import com.echothree.model.control.period.common.transfer.PeriodKindTransfer;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PeriodKindTransferCache
        extends BasePeriodTransferCache<PeriodKind, PeriodKindTransfer> {
    
    /** Creates a new instance of PeriodKindTransferCache */
    public PeriodKindTransferCache(PeriodControl periodControl) {
        super(periodControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PeriodKindTransfer getPeriodKindTransfer(UserVisit userVisit, PeriodKind periodKind) {
        var periodKindTransfer = get(periodKind);
        
        if(periodKindTransfer == null) {
            var periodKindDetail = periodKind.getLastDetail();
            var periodKindName = periodKindDetail.getPeriodKindName();
            var isDefault = periodKindDetail.getIsDefault();
            var sortOrder = periodKindDetail.getSortOrder();
            var description = periodControl.getBestPeriodKindDescription(periodKind, getLanguage(userVisit));
            
            periodKindTransfer = new PeriodKindTransfer(periodKindName, isDefault, sortOrder, description);
            put(userVisit, periodKind, periodKindTransfer);
        }
        
        return periodKindTransfer;
    }
    
}
