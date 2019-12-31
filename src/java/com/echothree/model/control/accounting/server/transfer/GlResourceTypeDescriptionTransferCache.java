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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.common.transfer.GlResourceTypeDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.GlResourceTypeTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.accounting.server.entity.GlResourceTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GlResourceTypeDescriptionTransferCache
        extends BaseAccountingDescriptionTransferCache<GlResourceTypeDescription, GlResourceTypeDescriptionTransfer> {
    
    /** Creates a new instance of GlResourceTypeDescriptionTransferCache */
    public GlResourceTypeDescriptionTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
    }
    
    @Override
    public GlResourceTypeDescriptionTransfer getTransfer(GlResourceTypeDescription glResourceTypeDescription) {
        GlResourceTypeDescriptionTransfer glResourceTypeDescriptionTransfer = get(glResourceTypeDescription);
        
        if(glResourceTypeDescriptionTransfer == null) {
            GlResourceTypeTransferCache glResourceTypeTransferCache = accountingControl.getAccountingTransferCaches(userVisit).getGlResourceTypeTransferCache();
            GlResourceTypeTransfer glResourceTypeTransfer = glResourceTypeTransferCache.getTransfer(glResourceTypeDescription.getGlResourceType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, glResourceTypeDescription.getLanguage());
            
            glResourceTypeDescriptionTransfer = new GlResourceTypeDescriptionTransfer(languageTransfer, glResourceTypeTransfer, glResourceTypeDescription.getDescription());
            put(glResourceTypeDescription, glResourceTypeDescriptionTransfer);
        }
        
        return glResourceTypeDescriptionTransfer;
    }
    
}
