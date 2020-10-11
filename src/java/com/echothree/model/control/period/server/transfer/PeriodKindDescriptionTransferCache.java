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

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.period.common.transfer.PeriodKindDescriptionTransfer;
import com.echothree.model.control.period.common.transfer.PeriodKindTransfer;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.data.period.server.entity.PeriodKindDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PeriodKindDescriptionTransferCache
        extends BasePeriodDescriptionTransferCache<PeriodKindDescription, PeriodKindDescriptionTransfer> {
    
    /** Creates a new instance of PeriodKindDescriptionTransferCache */
    public PeriodKindDescriptionTransferCache(UserVisit userVisit, PeriodControl periodControl) {
        super(userVisit, periodControl);
    }
    
    public PeriodKindDescriptionTransfer getPeriodKindDescriptionTransfer(PeriodKindDescription periodKindDescription) {
        PeriodKindDescriptionTransfer periodKindDescriptionTransfer = get(periodKindDescription);
        
        if(periodKindDescriptionTransfer == null) {
            PeriodKindTransfer periodKindTransfer = periodControl.getPeriodKindTransfer(userVisit, periodKindDescription.getPeriodKind());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, periodKindDescription.getLanguage());
            
            periodKindDescriptionTransfer = new PeriodKindDescriptionTransfer(languageTransfer, periodKindTransfer, periodKindDescription.getDescription());
            put(periodKindDescription, periodKindDescriptionTransfer);
        }
        
        return periodKindDescriptionTransfer;
    }
    
}
