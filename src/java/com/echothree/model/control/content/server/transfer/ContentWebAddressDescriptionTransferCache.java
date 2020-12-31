// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.content.server.transfer;

import com.echothree.model.control.content.common.transfer.ContentWebAddressDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentWebAddressTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.content.server.entity.ContentWebAddressDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContentWebAddressDescriptionTransferCache
        extends BaseContentDescriptionTransferCache<ContentWebAddressDescription, ContentWebAddressDescriptionTransfer> {
    
    /** Creates a new instance of ContentWebAddressDescriptionTransferCache */
    public ContentWebAddressDescriptionTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);
    }
    
    public ContentWebAddressDescriptionTransfer getContentWebAddressDescriptionTransfer(ContentWebAddressDescription contentWebAddressDescription) {
        ContentWebAddressDescriptionTransfer contentWebAddressDescriptionTransfer = get(contentWebAddressDescription);
        
        if(contentWebAddressDescriptionTransfer == null) {
            ContentWebAddressTransferCache contentWebAddressTransferCache = contentControl.getContentTransferCaches(userVisit).getContentWebAddressTransferCache();
            ContentWebAddressTransfer contentWebAddressTransfer = contentWebAddressTransferCache.getContentWebAddressTransfer(contentWebAddressDescription.getContentWebAddress());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, contentWebAddressDescription.getLanguage());
            
            contentWebAddressDescriptionTransfer = new ContentWebAddressDescriptionTransfer(languageTransfer, contentWebAddressTransfer, contentWebAddressDescription.getDescription());
            put(contentWebAddressDescription, contentWebAddressDescriptionTransfer);
        }
        
        return contentWebAddressDescriptionTransfer;
    }
    
}
