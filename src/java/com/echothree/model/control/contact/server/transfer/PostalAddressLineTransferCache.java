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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.common.ContactOptions;
import com.echothree.model.control.contact.common.transfer.PostalAddressLineTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.PostalAddressLine;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PostalAddressLineTransferCache
        extends BaseContactTransferCache<PostalAddressLine, PostalAddressLineTransfer> {

    ContactControl contactControl = Session.getModelController(ContactControl.class);

    boolean includeElements;
    
    /** Creates a new instance of PostalAddressLineTransferCache */
    protected PostalAddressLineTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeElements = options.contains(ContactOptions.PostalAddressLineIncludeElements);
        }
    }
    
    public PostalAddressLineTransfer getPostalAddressLineTransfer(UserVisit userVisit, PostalAddressLine postalAddressLine) {
        var postalAddressLineTransfer = get(postalAddressLine);
        
        if(postalAddressLineTransfer == null) {
            var postalAddressLineDetail = postalAddressLine.getLastDetail();
            var postalAddressFormat = contactControl.getPostalAddressFormatTransfer(userVisit, postalAddressLineDetail.getPostalAddressFormat());
            var postalAddressLineSortOrder = postalAddressLineDetail.getPostalAddressLineSortOrder();
            var prefix = postalAddressLineDetail.getPrefix();
            var alwaysIncludePrefix = postalAddressLineDetail.getAlwaysIncludePrefix();
            var suffix = postalAddressLineDetail.getSuffix();
            var alwaysIncludeSuffix = postalAddressLineDetail.getAlwaysIncludeSuffix();
            var collapseIfEmpty = postalAddressLineDetail.getCollapseIfEmpty();
            
            postalAddressLineTransfer = new PostalAddressLineTransfer(postalAddressFormat, postalAddressLineSortOrder, prefix,
                    alwaysIncludePrefix, suffix, alwaysIncludeSuffix, collapseIfEmpty);
            put(userVisit, postalAddressLine, postalAddressLineTransfer);
            
            if(includeElements) {
                postalAddressLineTransfer.setPostalAddressLineElements(new ListWrapper<>(contactControl.getPostalAddressLineElementTransfersByPostalAddressLine(userVisit, postalAddressLine)));
            }
        }
        
        return postalAddressLineTransfer;
    }
    
}
