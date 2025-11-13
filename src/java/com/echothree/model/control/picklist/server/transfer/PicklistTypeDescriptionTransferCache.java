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

import com.echothree.model.control.picklist.common.transfer.PicklistTypeDescriptionTransfer;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.data.picklist.server.entity.PicklistTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PicklistTypeDescriptionTransferCache
        extends BasePicklistDescriptionTransferCache<PicklistTypeDescription, PicklistTypeDescriptionTransfer> {
    
    /** Creates a new instance of PicklistTypeDescriptionTransferCache */
    public PicklistTypeDescriptionTransferCache(PicklistControl picklistControl) {
        super(picklistControl);
    }
    
    public PicklistTypeDescriptionTransfer getPicklistTypeDescriptionTransfer(UserVisit userVisit, PicklistTypeDescription picklistTypeDescription) {
        var picklistTypeDescriptionTransfer = get(picklistTypeDescription);
        
        if(picklistTypeDescriptionTransfer == null) {
            var picklistTypeTransfer = picklistControl.getPicklistTypeTransfer(userVisit, picklistTypeDescription.getPicklistType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, picklistTypeDescription.getLanguage());
            
            picklistTypeDescriptionTransfer = new PicklistTypeDescriptionTransfer(languageTransfer, picklistTypeTransfer, picklistTypeDescription.getDescription());
            put(userVisit, picklistTypeDescription, picklistTypeDescriptionTransfer);
        }
        
        return picklistTypeDescriptionTransfer;
    }
    
}
