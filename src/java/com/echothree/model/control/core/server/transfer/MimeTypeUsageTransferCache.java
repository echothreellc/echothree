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

import com.echothree.model.control.core.common.transfer.MimeTypeUsageTransfer;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class MimeTypeUsageTransferCache
        extends BaseCoreTransferCache<MimeTypeUsage, MimeTypeUsageTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);

    /** Creates a new instance of MimeTypeUsageTransferCache */
    public MimeTypeUsageTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public MimeTypeUsageTransfer getMimeTypeUsageTransfer(MimeTypeUsage mimeTypeUsage) {
        var mimeTypeUsageTransfer = get(mimeTypeUsage);
        
        if(mimeTypeUsageTransfer == null) {
            var mimeType = mimeTypeControl.getMimeTypeTransfer(userVisit, mimeTypeUsage.getMimeType());
            var mimeTypeUsageType = mimeTypeControl.getMimeTypeUsageTypeTransfer(userVisit,
                    mimeTypeUsage.getMimeTypeUsageType());
            
            mimeTypeUsageTransfer = new MimeTypeUsageTransfer(mimeType, mimeTypeUsageType);
            put(userVisit, mimeTypeUsage, mimeTypeUsageTransfer);
        }
        
        return mimeTypeUsageTransfer;
    }
    
}
