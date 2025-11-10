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

package com.echothree.model.control.offer.server.transfer;

import com.echothree.model.control.offer.common.transfer.UseNameElementDescriptionTransfer;
import com.echothree.model.control.offer.server.control.UseNameElementControl;
import com.echothree.model.data.offer.server.entity.UseNameElementDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class UseNameElementDescriptionTransferCache
        extends BaseOfferDescriptionTransferCache<UseNameElementDescription, UseNameElementDescriptionTransfer> {

    UseNameElementControl useNameElementControl = Session.getModelController(UseNameElementControl.class);

    /** Creates a new instance of UseNameElementDescriptionTransferCache */
    public UseNameElementDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public UseNameElementDescriptionTransfer getUseNameElementDescriptionTransfer(UseNameElementDescription useNameElementDescription) {
        var useNameElementDescriptionTransfer = get(useNameElementDescription);
        
        if(useNameElementDescriptionTransfer == null) {
            var useNameElementTransfer = useNameElementControl.getUseNameElementTransfer(userVisit, useNameElementDescription.getUseNameElement());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, useNameElementDescription.getLanguage());
            
            useNameElementDescriptionTransfer = new UseNameElementDescriptionTransfer(languageTransfer, useNameElementTransfer,
                    useNameElementDescription.getDescription());
            put(userVisit, useNameElementDescription, useNameElementDescriptionTransfer);
        }
        
        return useNameElementDescriptionTransfer;
    }
    
}
