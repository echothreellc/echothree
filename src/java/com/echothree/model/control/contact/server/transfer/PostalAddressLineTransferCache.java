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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.common.ContactOptions;
import com.echothree.model.control.contact.common.transfer.PostalAddressFormatTransfer;
import com.echothree.model.control.contact.common.transfer.PostalAddressLineTransfer;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.data.contact.server.entity.PostalAddressLine;
import com.echothree.model.data.contact.server.entity.PostalAddressLineDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.Set;

public class PostalAddressLineTransferCache
        extends BaseContactTransferCache<PostalAddressLine, PostalAddressLineTransfer> {
    
    boolean includeElements;
    
    /** Creates a new instance of PostalAddressLineTransferCache */
    public PostalAddressLineTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeElements = options.contains(ContactOptions.PostalAddressLineIncludeElements);
        }
    }
    
    public PostalAddressLineTransfer getPostalAddressLineTransfer(PostalAddressLine postalAddressLine) {
        PostalAddressLineTransfer postalAddressLineTransfer = get(postalAddressLine);
        
        if(postalAddressLineTransfer == null) {
            PostalAddressLineDetail postalAddressLineDetail = postalAddressLine.getLastDetail();
            PostalAddressFormatTransfer postalAddressFormat = contactControl.getPostalAddressFormatTransfer(userVisit, postalAddressLineDetail.getPostalAddressFormat());
            Integer postalAddressLineSortOrder = postalAddressLineDetail.getPostalAddressLineSortOrder();
            String prefix = postalAddressLineDetail.getPrefix();
            Boolean alwaysIncludePrefix = postalAddressLineDetail.getAlwaysIncludePrefix();
            String suffix = postalAddressLineDetail.getSuffix();
            Boolean alwaysIncludeSuffix = postalAddressLineDetail.getAlwaysIncludeSuffix();
            Boolean collapseIfEmpty = postalAddressLineDetail.getCollapseIfEmpty();
            
            postalAddressLineTransfer = new PostalAddressLineTransfer(postalAddressFormat, postalAddressLineSortOrder, prefix,
                    alwaysIncludePrefix, suffix, alwaysIncludeSuffix, collapseIfEmpty);
            put(postalAddressLine, postalAddressLineTransfer);
            
            if(includeElements) {
                postalAddressLineTransfer.setPostalAddressLineElements(new ListWrapper<>(contactControl.getPostalAddressLineElementTransfersByPostalAddressLine(userVisit, postalAddressLine)));
            }
        }
        
        return postalAddressLineTransfer;
    }
    
}
