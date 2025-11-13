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

import com.echothree.model.control.offer.common.transfer.UseTypeDescriptionTransfer;
import com.echothree.model.control.offer.server.control.UseTypeControl;
import com.echothree.model.data.offer.server.entity.UseTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class UseTypeDescriptionTransferCache
        extends BaseOfferDescriptionTransferCache<UseTypeDescription, UseTypeDescriptionTransfer> {

    UseTypeControl useTypeControl = Session.getModelController(UseTypeControl.class);

    /** Creates a new instance of UseTypeDescriptionTransferCache */
    public UseTypeDescriptionTransferCache() {
        super();
    }
    
    public UseTypeDescriptionTransfer getUseTypeDescriptionTransfer(UseTypeDescription useTypeDescription) {
        var useTypeDescriptionTransfer = get(useTypeDescription);
        
        if(useTypeDescriptionTransfer == null) {
            var useTypeTransfer = useTypeControl.getUseTypeTransfer(userVisit, useTypeDescription.getUseType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, useTypeDescription.getLanguage());
            
            useTypeDescriptionTransfer = new UseTypeDescriptionTransfer(languageTransfer, useTypeTransfer, useTypeDescription.getDescription());
            put(userVisit, useTypeDescription, useTypeDescriptionTransfer);
        }
        
        return useTypeDescriptionTransfer;
    }
    
}
