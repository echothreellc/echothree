// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.model.control.content.common.transfer.ContentCollectionTransfer;
import com.echothree.model.control.content.common.transfer.ContentWebAddressTransfer;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.content.server.entity.ContentWebAddressDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import java.util.Set;

public class ContentWebAddressTransferCache
        extends BaseContentTransferCache<ContentWebAddress, ContentWebAddressTransfer> {
    
    /** Creates a new instance of ContentWebAddressTransferCache */
    public ContentWebAddressTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(ContentOptions.ContentWebAddressIncludeKey));
            setIncludeGuid(options.contains(ContentOptions.ContentWebAddressIncludeGuid));
            setIncludeEntityAttributeGroups(options.contains(ContentOptions.ContentWebAddressIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ContentOptions.ContentWebAddressIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public ContentWebAddressTransfer getContentWebAddressTransfer(ContentWebAddress contentWebAddress) {
        ContentWebAddressTransfer contentWebAddressTransfer = get(contentWebAddress);
        
        if(contentWebAddressTransfer == null) {
            ContentWebAddressDetail contentWebAddressDetail = contentWebAddress.getLastDetail();
            String contentWebAddressName = contentWebAddressDetail.getContentWebAddressName();
            ContentCollectionTransfer contentCollectionTransfer = contentControl.getContentCollectionTransfer(userVisit, contentWebAddressDetail.getContentCollection());
            String description = contentControl.getBestContentWebAddressDescription(contentWebAddress, getLanguage());
            
            contentWebAddressTransfer = new ContentWebAddressTransfer(contentWebAddressName, contentCollectionTransfer, description);
            put(contentWebAddress, contentWebAddressTransfer);
        }
        
        return contentWebAddressTransfer;
    }
    
}
