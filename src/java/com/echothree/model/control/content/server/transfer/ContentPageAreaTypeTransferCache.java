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

import com.echothree.model.control.content.common.transfer.ContentPageAreaTypeTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentPageAreaType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ContentPageAreaTypeTransferCache
        extends BaseContentTransferCache<ContentPageAreaType, ContentPageAreaTypeTransfer> {

    ContentControl contentControl = Session.getModelController(ContentControl.class);

    /** Creates a new instance of ContentPageAreaTypeTransferCache */
    protected ContentPageAreaTypeTransferCache() {
        super();
    }
    
    public ContentPageAreaTypeTransfer getTransfer(UserVisit userVisit, ContentPageAreaType contentPageAreaType) {
        var contentPageAreaTypeTransfer = get(contentPageAreaType);
        
        if(contentPageAreaTypeTransfer == null) {
            var contentPageAreaTypeName = contentPageAreaType.getContentPageAreaTypeName();
            var description = contentControl.getBestContentPageAreaTypeDescription(contentPageAreaType, getLanguage(userVisit));
            
            contentPageAreaTypeTransfer = new ContentPageAreaTypeTransfer(contentPageAreaTypeName, description);
            put(userVisit, contentPageAreaType, contentPageAreaTypeTransfer);
        }
        
        return contentPageAreaTypeTransfer;
    }
    
}
