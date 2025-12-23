// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.content.common.transfer.ContentPageLayoutAreaTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentPageLayoutArea;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ContentPageLayoutAreaTransferCache
        extends BaseContentTransferCache<ContentPageLayoutArea, ContentPageLayoutAreaTransfer> {

    ContentControl contentControl = Session.getModelController(ContentControl.class);

    /** Creates a new instance of ContentPageLayoutAreaTransferCache */
    protected ContentPageLayoutAreaTransferCache() {
        super();
    }
    
    public ContentPageLayoutAreaTransfer getContentPageLayoutAreaTransfer(UserVisit userVisit, ContentPageLayoutArea contentPageLayoutArea) {
        var contentPageLayoutAreaTransfer = get(contentPageLayoutArea);
        
        if(contentPageLayoutAreaTransfer == null) {
            var contentPageLayoutTransfer = contentControl.getContentPageLayoutTransfer(userVisit, contentPageLayoutArea.getContentPageLayout());
            var contentPageAreaTypeTransfer = contentControl.getContentPageAreaTypeTransfer(userVisit, contentPageLayoutArea.getContentPageAreaType());
            var showDescriptionField = contentPageLayoutArea.getShowDescriptionField();
            var sortOrder = contentPageLayoutArea.getSortOrder();
            var description = contentControl.getBestContentPageLayoutAreaDescription(contentPageLayoutArea, getLanguage(userVisit));
            
            contentPageLayoutAreaTransfer = new ContentPageLayoutAreaTransfer(contentPageLayoutTransfer, contentPageAreaTypeTransfer, showDescriptionField, sortOrder, description);
            put(userVisit, contentPageLayoutArea, contentPageLayoutAreaTransfer);
        }
        
        return contentPageLayoutAreaTransfer;
    }
    
}
