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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.common.transfer.PostalAddressFormatDescriptionTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.PostalAddressFormatDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PostalAddressFormatDescriptionTransferCache
        extends BaseContactDescriptionTransferCache<PostalAddressFormatDescription, PostalAddressFormatDescriptionTransfer> {
    
    /** Creates a new instance of PostalAddressFormatDescriptionTransferCache */
    public PostalAddressFormatDescriptionTransferCache(ContactControl contactControl) {
        super(contactControl);
    }
    
    public PostalAddressFormatDescriptionTransfer getPostalAddressFormatDescriptionTransfer(UserVisit userVisit, PostalAddressFormatDescription postalAddressFormatDescription) {
        var postalAddressFormatDescriptionTransfer = get(postalAddressFormatDescription);
        
        if(postalAddressFormatDescriptionTransfer == null) {
            var postalAddressFormatTransfer = contactControl.getPostalAddressFormatTransfer(userVisit, postalAddressFormatDescription.getPostalAddressFormat());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, postalAddressFormatDescription.getLanguage());
            
            postalAddressFormatDescriptionTransfer = new PostalAddressFormatDescriptionTransfer(languageTransfer, postalAddressFormatTransfer, postalAddressFormatDescription.getDescription());
            put(userVisit, postalAddressFormatDescription, postalAddressFormatDescriptionTransfer);
        }
        
        return postalAddressFormatDescriptionTransfer;
    }
    
}
