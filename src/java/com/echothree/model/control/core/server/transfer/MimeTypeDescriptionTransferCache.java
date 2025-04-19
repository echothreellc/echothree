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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeDescriptionTransfer;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.core.server.entity.MimeTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class MimeTypeDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<MimeTypeDescription, MimeTypeDescriptionTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);

    /** Creates a new instance of MimeTypeDescriptionTransferCache */
    public MimeTypeDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public MimeTypeDescriptionTransfer getMimeTypeDescriptionTransfer(MimeTypeDescription mimeTypeDescription) {
        var mimeTypeDescriptionTransfer = get(mimeTypeDescription);
        
        if(mimeTypeDescriptionTransfer == null) {
            var mimeTypeTransfer = mimeTypeControl.getMimeTypeTransfer(userVisit,
                    mimeTypeDescription.getMimeType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, mimeTypeDescription.getLanguage());
            
            mimeTypeDescriptionTransfer = new MimeTypeDescriptionTransfer(languageTransfer, mimeTypeTransfer, mimeTypeDescription.getDescription());
            put(mimeTypeDescription, mimeTypeDescriptionTransfer);
        }
        
        return mimeTypeDescriptionTransfer;
    }
    
}
