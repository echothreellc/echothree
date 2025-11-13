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

import com.echothree.model.control.content.common.transfer.ContentPageLayoutTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContentPageLayoutTransferCache
        extends BaseContentTransferCache<ContentPageLayout, ContentPageLayoutTransfer> {
    
    /** Creates a new instance of ContentPageLayoutTransferCache */
    public ContentPageLayoutTransferCache(ContentControl contentControl) {
        super(contentControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ContentPageLayoutTransfer getTransfer(UserVisit userVisit, ContentPageLayout contentPageLayout) {
        var contentPageLayoutTransfer = get(contentPageLayout);
        
        if(contentPageLayoutTransfer == null) {
            var contentPageLayoutDetail = contentPageLayout.getLastDetail();
            var contentPageLayoutName = contentPageLayoutDetail.getContentPageLayoutName();
            var isDefault = contentPageLayoutDetail.getIsDefault();
            var sortOrder = contentPageLayoutDetail.getSortOrder();
            var description = contentControl.getBestContentPageLayoutDescription(contentPageLayout, getLanguage(userVisit));
            
            contentPageLayoutTransfer = new ContentPageLayoutTransfer(contentPageLayoutName, isDefault, sortOrder, description);
            put(userVisit, contentPageLayout, contentPageLayoutTransfer);
        }
        
        return contentPageLayoutTransfer;
    }
    
}
