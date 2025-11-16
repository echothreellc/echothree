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

package com.echothree.model.control.party.server.transfer;

import com.echothree.model.control.party.common.transfer.GenderDescriptionTransfer;
import com.echothree.model.data.party.server.entity.GenderDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GenderDescriptionTransferCache
        extends BasePartyDescriptionTransferCache<GenderDescription, GenderDescriptionTransfer> {
    
    /** Creates a new instance of GenderDescriptionTransferCache */
    protected GenderDescriptionTransferCache() {
        super();
    }

    @Override
    public GenderDescriptionTransfer getTransfer(UserVisit userVisit, GenderDescription genderDescription) {
        var genderDescriptionTransfer = get(genderDescription);
        
        if(genderDescriptionTransfer == null) {
            var genderTransfer = partyControl.getGenderTransfer(userVisit, genderDescription.getGender());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, genderDescription.getLanguage());
            
            genderDescriptionTransfer = new GenderDescriptionTransfer(languageTransfer, genderTransfer, genderDescription.getDescription());
            put(userVisit, genderDescription, genderDescriptionTransfer);
        }
        
        return genderDescriptionTransfer;
    }
    
}
