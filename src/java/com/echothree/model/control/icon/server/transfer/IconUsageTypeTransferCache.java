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

package com.echothree.model.control.icon.server.transfer;

import com.echothree.model.control.icon.common.transfer.IconUsageTypeTransfer;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.data.icon.server.entity.IconUsageType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class IconUsageTypeTransferCache
        extends BaseIconTransferCache<IconUsageType, IconUsageTypeTransfer> {
    
    /** Creates a new instance of IconUsageTypeTransferCache */
    public IconUsageTypeTransferCache(IconControl iconControl) {
        super(iconControl);
        
        setIncludeEntityInstance(true);
    }
    
    public IconUsageTypeTransfer getIconUsageTypeTransfer(IconUsageType iconUsageType) {
        var iconUsageTypeTransfer = get(iconUsageType);
        
        if(iconUsageTypeTransfer == null) {
            var iconUsageTypeDetail = iconUsageType.getLastDetail();
            var iconUsageTypeName = iconUsageTypeDetail.getIconUsageTypeName();
            var isDefault = iconUsageTypeDetail.getIsDefault();
            var sortOrder = iconUsageTypeDetail.getSortOrder();
            var description = iconControl.getBestIconUsageTypeDescription(iconUsageType, getLanguage(userVisit));
            
            iconUsageTypeTransfer = new IconUsageTypeTransfer(iconUsageTypeName, isDefault, sortOrder, description);
            put(userVisit, iconUsageType, iconUsageTypeTransfer);
        }
        return iconUsageTypeTransfer;
    }
    
}
