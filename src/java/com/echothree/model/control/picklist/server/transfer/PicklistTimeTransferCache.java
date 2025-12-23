// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.picklist.common.transfer.PicklistTimeTransfer;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.data.picklist.server.entity.PicklistTime;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PicklistTimeTransferCache
        extends BasePicklistTransferCache<PicklistTime, PicklistTimeTransfer> {

    PicklistControl picklistControl = Session.getModelController(PicklistControl.class);

    /** Creates a new instance of PicklistTimeTransferCache */
    protected PicklistTimeTransferCache() {
        super();
    }
    
    public PicklistTimeTransfer getPicklistTimeTransfer(UserVisit userVisit, PicklistTime picklistTime) {
        var picklistTimeTransfer = get(picklistTime);
        
        if(picklistTimeTransfer == null) {
            var picklistTimeType = picklistControl.getPicklistTimeTypeTransfer(userVisit, picklistTime.getPicklistTimeType());
            var unformattedTime = picklistTime.getTime();
            var time = formatTypicalDateTime(userVisit, unformattedTime);
            
            picklistTimeTransfer = new PicklistTimeTransfer(picklistTimeType, unformattedTime, time);
            put(userVisit, picklistTime, picklistTimeTransfer);
        }
        
        return picklistTimeTransfer;
    }
    
}
