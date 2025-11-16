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

package com.echothree.model.control.shipment.server.transfer;

import com.echothree.model.control.shipment.common.transfer.FreeOnBoardDescriptionTransfer;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.data.shipment.server.entity.FreeOnBoardDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FreeOnBoardDescriptionTransferCache
        extends BaseShipmentDescriptionTransferCache<FreeOnBoardDescription, FreeOnBoardDescriptionTransfer> {

    FreeOnBoardControl freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);

    /** Creates a new instance of FreeOnBoardDescriptionTransferCache */
    protected FreeOnBoardDescriptionTransferCache() {
        super();
    }
    
    @Override
    public FreeOnBoardDescriptionTransfer getTransfer(UserVisit userVisit, FreeOnBoardDescription freeOnBoardDescription) {
        var freeOnBoardDescriptionTransfer = get(freeOnBoardDescription);
        
        if(freeOnBoardDescriptionTransfer == null) {
            var freeOnBoardTransfer = freeOnBoardControl.getFreeOnBoardTransfer(userVisit, freeOnBoardDescription.getFreeOnBoard());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, freeOnBoardDescription.getLanguage());
            
            freeOnBoardDescriptionTransfer = new FreeOnBoardDescriptionTransfer(languageTransfer, freeOnBoardTransfer, freeOnBoardDescription.getDescription());
            put(userVisit, freeOnBoardDescription, freeOnBoardDescriptionTransfer);
        }
        
        return freeOnBoardDescriptionTransfer;
    }
    
}
