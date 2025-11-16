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

import com.echothree.model.control.period.common.transfer.PeriodKindDescriptionTransfer;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.data.period.server.entity.PeriodKindDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PeriodKindDescriptionTransferCache
        extends BasePeriodDescriptionTransferCache<PeriodKindDescription, PeriodKindDescriptionTransfer> {

    PeriodControl periodControl = Session.getModelController(PeriodControl.class);

    /** Creates a new instance of PeriodKindDescriptionTransferCache */
    public PeriodKindDescriptionTransferCache() {
        super();
    }
    
    public PeriodKindDescriptionTransfer getPeriodKindDescriptionTransfer(UserVisit userVisit, PeriodKindDescription periodKindDescription) {
        var periodKindDescriptionTransfer = get(periodKindDescription);
        
        if(periodKindDescriptionTransfer == null) {
            var periodKindTransfer = periodControl.getPeriodKindTransfer(userVisit, periodKindDescription.getPeriodKind());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, periodKindDescription.getLanguage());
            
            periodKindDescriptionTransfer = new PeriodKindDescriptionTransfer(languageTransfer, periodKindTransfer, periodKindDescription.getDescription());
            put(userVisit, periodKindDescription, periodKindDescriptionTransfer);
        }
        
        return periodKindDescriptionTransfer;
    }
    
}
