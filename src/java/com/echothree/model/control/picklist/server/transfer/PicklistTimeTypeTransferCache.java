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

package com.echothree.model.control.picklist.server.transfer;

import com.echothree.model.control.picklist.common.transfer.PicklistTimeTypeTransfer;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.data.picklist.server.entity.PicklistTimeType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PicklistTimeTypeTransferCache
        extends BasePicklistTransferCache<PicklistTimeType, PicklistTimeTypeTransfer> {
    
    /** Creates a new instance of PicklistTimeTypeTransferCache */
    public PicklistTimeTypeTransferCache(UserVisit userVisit, PicklistControl picklistControl) {
        super(userVisit, picklistControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PicklistTimeTypeTransfer getPicklistTimeTypeTransfer(PicklistTimeType picklistTimeType) {
        var picklistTimeTypeTransfer = get(picklistTimeType);
        
        if(picklistTimeTypeTransfer == null) {
            var picklistTimeTypeDetail = picklistTimeType.getLastDetail();
            var picklistTimeTypeName = picklistTimeTypeDetail.getPicklistTimeTypeName();
            var isDefault = picklistTimeTypeDetail.getIsDefault();
            var sortOrder = picklistTimeTypeDetail.getSortOrder();
            var description = picklistControl.getBestPicklistTimeTypeDescription(picklistTimeType, getLanguage(userVisit));
            
            picklistTimeTypeTransfer = new PicklistTimeTypeTransfer(picklistTimeTypeName, isDefault, sortOrder, description);
            put(userVisit, picklistTimeType, picklistTimeTypeTransfer);
        }
        
        return picklistTimeTypeTransfer;
    }
    
}
