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

import com.echothree.model.control.picklist.common.transfer.PicklistTimeTypeDescriptionTransfer;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.data.picklist.server.entity.PicklistTimeTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PicklistTimeTypeDescriptionTransferCache
        extends BasePicklistDescriptionTransferCache<PicklistTimeTypeDescription, PicklistTimeTypeDescriptionTransfer> {
    
    /** Creates a new instance of PicklistTimeTypeDescriptionTransferCache */
    public PicklistTimeTypeDescriptionTransferCache(UserVisit userVisit, PicklistControl picklistControl) {
        super(userVisit, picklistControl);
    }
    
    public PicklistTimeTypeDescriptionTransfer getPicklistTimeTypeDescriptionTransfer(PicklistTimeTypeDescription picklistTimeTypeDescription) {
        var picklistTimeTypeDescriptionTransfer = get(picklistTimeTypeDescription);
        
        if(picklistTimeTypeDescriptionTransfer == null) {
            var picklistTimeTypeTransfer = picklistControl.getPicklistTimeTypeTransfer(userVisit, picklistTimeTypeDescription.getPicklistTimeType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, picklistTimeTypeDescription.getLanguage());
            
            picklistTimeTypeDescriptionTransfer = new PicklistTimeTypeDescriptionTransfer(languageTransfer, picklistTimeTypeTransfer, picklistTimeTypeDescription.getDescription());
            put(picklistTimeTypeDescription, picklistTimeTypeDescriptionTransfer);
        }
        
        return picklistTimeTypeDescriptionTransfer;
    }
    
}
