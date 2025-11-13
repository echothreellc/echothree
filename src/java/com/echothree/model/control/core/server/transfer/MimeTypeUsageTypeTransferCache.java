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

import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;

public class MimeTypeUsageTypeTransferCache
        extends BaseCoreTransferCache<MimeTypeUsageType, MimeTypeUsageTypeTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);

    TransferProperties transferProperties;
    boolean filterMimeTypeUsageTypeName;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    
    /** Creates a new instance of MimeTypeUsageTypeTransferCache */
    public MimeTypeUsageTypeTransferCache() {
        super();
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(MimeTypeUsageTypeTransfer.class);
            
            if(properties != null) {
                filterMimeTypeUsageTypeName = !properties.contains(CoreProperties.MIME_TYPE_USAGE_TYPE_NAME);
                filterIsDefault = !properties.contains(CoreProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(CoreProperties.SORT_ORDER);
                filterDescription = !properties.contains(CoreProperties.DESCRIPTION);
            }
        }
    }
    
    public MimeTypeUsageTypeTransfer getMimeTypeUsageTypeTransfer(UserVisit userVisit, MimeTypeUsageType mimeTypeUsageType) {
        var mimeTypeUsageTypeTransfer = get(mimeTypeUsageType);
        
        if(mimeTypeUsageTypeTransfer == null) {
            var mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();
            var isDefault = mimeTypeUsageType.getIsDefault();
            var sortOrder = mimeTypeUsageType.getSortOrder();
            var description = mimeTypeControl.getBestMimeTypeUsageTypeDescription(mimeTypeUsageType, getLanguage(userVisit));
            
            mimeTypeUsageTypeTransfer = new MimeTypeUsageTypeTransfer(mimeTypeUsageTypeName, isDefault, sortOrder, description);
            put(userVisit, mimeTypeUsageType, mimeTypeUsageTypeTransfer);
        }
        
        return mimeTypeUsageTypeTransfer;
    }
    
}
