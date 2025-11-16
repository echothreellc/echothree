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

import com.echothree.model.control.content.common.transfer.ContentPageLayoutDescriptionTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentPageLayoutDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ContentPageLayoutDescriptionTransferCache
        extends BaseContentDescriptionTransferCache<ContentPageLayoutDescription, ContentPageLayoutDescriptionTransfer> {

    ContentControl contentControl = Session.getModelController(ContentControl.class);

    /** Creates a new instance of ContentPageLayoutDescriptionTransferCache */
    protected ContentPageLayoutDescriptionTransferCache() {
        super();
    }
    
    public ContentPageLayoutDescriptionTransfer getTransfer(UserVisit userVisit, ContentPageLayoutDescription contentPageLayoutDescription) {
        var contentPageLayoutDescriptionTransfer = get(contentPageLayoutDescription);
        
        if(contentPageLayoutDescriptionTransfer == null) {
            var contentPageLayoutTransfer = contentControl.getContentPageLayoutTransfer(userVisit, contentPageLayoutDescription.getContentPageLayout());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, contentPageLayoutDescription.getLanguage());
            
            contentPageLayoutDescriptionTransfer = new ContentPageLayoutDescriptionTransfer(languageTransfer, contentPageLayoutTransfer, contentPageLayoutDescription.getDescription());
            put(userVisit, contentPageLayoutDescription, contentPageLayoutDescriptionTransfer);
        }
        
        return contentPageLayoutDescriptionTransfer;
    }
    
}
