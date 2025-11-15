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

package com.echothree.model.control.content.server.transfer;

import com.echothree.model.control.content.common.transfer.ContentPageDescriptionTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentPageDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ContentPageDescriptionTransferCache
        extends BaseContentDescriptionTransferCache<ContentPageDescription, ContentPageDescriptionTransfer> {

    ContentControl contentControl = Session.getModelController(ContentControl.class);

    /** Creates a new instance of ContentPageDescriptionTransferCache */
    public ContentPageDescriptionTransferCache() {
        super();
    }
    
    public ContentPageDescriptionTransfer getContentPageDescriptionTransfer(UserVisit userVisit, ContentPageDescription contentPageDescription) {
        var contentPageDescriptionTransfer = get(contentPageDescription);
        
        if(contentPageDescriptionTransfer == null) {
            var contentPageTransfer = contentControl.getContentPageTransfer(userVisit, contentPageDescription.getContentPage());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, contentPageDescription.getLanguage());
            
            contentPageDescriptionTransfer = new ContentPageDescriptionTransfer(languageTransfer, contentPageTransfer, contentPageDescription.getDescription());
            put(userVisit, contentPageDescription, contentPageDescriptionTransfer);
        }
        
        return contentPageDescriptionTransfer;
    }
    
}
