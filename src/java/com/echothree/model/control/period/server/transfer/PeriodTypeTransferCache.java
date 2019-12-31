// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.period.common.transfer.PeriodTypeTransfer;
import com.echothree.model.control.period.server.PeriodControl;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.model.data.period.server.entity.PeriodTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PeriodTypeTransferCache
        extends BasePeriodTransferCache<PeriodType, PeriodTypeTransfer> {
    
    /** Creates a new instance of PeriodTypeTransferCache */
    public PeriodTypeTransferCache(UserVisit userVisit, PeriodControl periodControl) {
        super(userVisit, periodControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PeriodTypeTransfer getPeriodTypeTransfer(PeriodType periodType) {
        PeriodTypeTransfer periodTypeTransfer = get(periodType);
        
        if(periodTypeTransfer == null) {
            PeriodTypeDetail periodTypeDetail = periodType.getLastDetail();
            PeriodKindTransfer periodKindTransfer = periodControl.getPeriodKindTransfer(userVisit, periodTypeDetail.getPeriodKind());
            String periodTypeName = periodTypeDetail.getPeriodTypeName();
            Boolean isDefault = periodTypeDetail.getIsDefault();
            Integer sortOrder = periodTypeDetail.getSortOrder();
            String description = periodControl.getBestPeriodTypeDescription(periodType, getLanguage());
            
            periodTypeTransfer = new PeriodTypeTransfer(periodKindTransfer, periodTypeName, isDefault, sortOrder, description);
            put(periodType, periodTypeTransfer);
        }
        
        return periodTypeTransfer;
    }
    
}
