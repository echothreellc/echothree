// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.core.server.entity.MimeTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class MimeTypeDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<MimeTypeDescription, MimeTypeDescriptionTransfer> {
    
    /** Creates a new instance of MimeTypeDescriptionTransferCache */
    public MimeTypeDescriptionTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public MimeTypeDescriptionTransfer getMimeTypeDescriptionTransfer(MimeTypeDescription mimeTypeDescription) {
        MimeTypeDescriptionTransfer mimeTypeDescriptionTransfer = get(mimeTypeDescription);
        
        if(mimeTypeDescriptionTransfer == null) {
            MimeTypeTransfer mimeTypeTransfer = coreControl.getMimeTypeTransfer(userVisit,
                    mimeTypeDescription.getMimeType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, mimeTypeDescription.getLanguage());
            
            mimeTypeDescriptionTransfer = new MimeTypeDescriptionTransfer(languageTransfer, mimeTypeTransfer, mimeTypeDescription.getDescription());
            put(mimeTypeDescription, mimeTypeDescriptionTransfer);
        }
        
        return mimeTypeDescriptionTransfer;
    }
    
}
