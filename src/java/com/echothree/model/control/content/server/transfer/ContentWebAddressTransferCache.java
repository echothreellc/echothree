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

import com.echothree.model.control.content.common.ContentOptions;
import com.echothree.model.control.content.common.transfer.ContentWebAddressTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ContentWebAddressTransferCache
        extends BaseContentTransferCache<ContentWebAddress, ContentWebAddressTransfer> {

    ContentControl contentControl = Session.getModelController(ContentControl.class);

    /** Creates a new instance of ContentWebAddressTransferCache */
    public ContentWebAddressTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(ContentOptions.ContentWebAddressIncludeUuid));
            setIncludeEntityAttributeGroups(options.contains(ContentOptions.ContentWebAddressIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ContentOptions.ContentWebAddressIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public ContentWebAddressTransfer getContentWebAddressTransfer(UserVisit userVisit, ContentWebAddress contentWebAddress) {
        var contentWebAddressTransfer = get(contentWebAddress);
        
        if(contentWebAddressTransfer == null) {
            var contentWebAddressDetail = contentWebAddress.getLastDetail();
            var contentWebAddressName = contentWebAddressDetail.getContentWebAddressName();
            var contentCollectionTransfer = contentControl.getContentCollectionTransfer(userVisit, contentWebAddressDetail.getContentCollection());
            var description = contentControl.getBestContentWebAddressDescription(contentWebAddress, getLanguage(userVisit));
            
            contentWebAddressTransfer = new ContentWebAddressTransfer(contentWebAddressName, contentCollectionTransfer, description);
            put(userVisit, contentWebAddress, contentWebAddressTransfer);
        }
        
        return contentWebAddressTransfer;
    }
    
}
