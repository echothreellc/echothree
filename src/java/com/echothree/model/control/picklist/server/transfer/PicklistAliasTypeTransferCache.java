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

package com.echothree.model.control.picklist.server.transfer;

import com.echothree.model.control.picklist.common.transfer.PicklistAliasTypeTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTypeTransfer;
import com.echothree.model.control.picklist.server.PicklistControl;
import com.echothree.model.data.picklist.server.entity.PicklistAliasType;
import com.echothree.model.data.picklist.server.entity.PicklistAliasTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PicklistAliasTypeTransferCache
        extends BasePicklistTransferCache<PicklistAliasType, PicklistAliasTypeTransfer> {
    
    /** Creates a new instance of PicklistAliasTypeTransferCache */
    public PicklistAliasTypeTransferCache(UserVisit userVisit, PicklistControl picklistControl) {
        super(userVisit, picklistControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PicklistAliasTypeTransfer getPicklistAliasTypeTransfer(PicklistAliasType picklistAliasType) {
        PicklistAliasTypeTransfer picklistAliasTypeTransfer = get(picklistAliasType);
        
        if(picklistAliasTypeTransfer == null) {
            PicklistAliasTypeDetail picklistAliasTypeDetail = picklistAliasType.getLastDetail();
            PicklistTypeTransfer picklistType = picklistControl.getPicklistTypeTransfer(userVisit, picklistAliasTypeDetail.getPicklistType());
            String picklistAliasTypeName = picklistAliasTypeDetail.getPicklistAliasTypeName();
            String validationPattern = picklistAliasTypeDetail.getValidationPattern();
            Boolean isDefault = picklistAliasTypeDetail.getIsDefault();
            Integer sortOrder = picklistAliasTypeDetail.getSortOrder();
            String description = picklistControl.getBestPicklistAliasTypeDescription(picklistAliasType, getLanguage());
            
            picklistAliasTypeTransfer = new PicklistAliasTypeTransfer(picklistType, picklistAliasTypeName, validationPattern, isDefault, sortOrder, description);
            put(picklistAliasType, picklistAliasTypeTransfer);
        }
        
        return picklistAliasTypeTransfer;
    }
    
}
