// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.content.common.transfer.ContentPageDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageTransfer;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.content.server.entity.ContentPageDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContentPageDescriptionTransferCache
        extends BaseContentDescriptionTransferCache<ContentPageDescription, ContentPageDescriptionTransfer> {
    
    /** Creates a new instance of ContentPageDescriptionTransferCache */
    public ContentPageDescriptionTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);
    }
    
    public ContentPageDescriptionTransfer getContentPageDescriptionTransfer(ContentPageDescription contentPageDescription) {
        ContentPageDescriptionTransfer contentPageDescriptionTransfer = get(contentPageDescription);
        
        if(contentPageDescriptionTransfer == null) {
            ContentPageTransfer contentPageTransfer = contentControl.getContentPageTransfer(userVisit, contentPageDescription.getContentPage());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, contentPageDescription.getLanguage());
            
            contentPageDescriptionTransfer = new ContentPageDescriptionTransfer(languageTransfer, contentPageTransfer, contentPageDescription.getDescription());
            put(contentPageDescription, contentPageDescriptionTransfer);
        }
        
        return contentPageDescriptionTransfer;
    }
    
}
