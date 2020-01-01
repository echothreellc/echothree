// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.security.server.transfer;

import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateTrainingClassTransfer;
import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateTransfer;
import com.echothree.model.control.security.server.SecurityControl;
import com.echothree.model.control.training.common.transfer.TrainingClassTransfer;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateTrainingClass;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartySecurityRoleTemplateTrainingClassTransferCache
        extends BaseSecurityTransferCache<PartySecurityRoleTemplateTrainingClass, PartySecurityRoleTemplateTrainingClassTransfer> {
    
    TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
    
    /** Creates a new instance of PartySecurityRoleTemplateTrainingClassTransferCache */
    public PartySecurityRoleTemplateTrainingClassTransferCache(UserVisit userVisit, SecurityControl securityControl) {
        super(userVisit, securityControl);
    }
    
    public PartySecurityRoleTemplateTrainingClassTransfer getPartySecurityRoleTemplateTrainingClassTransfer(PartySecurityRoleTemplateTrainingClass partySecurityRoleTemplateTrainingClass) {
        PartySecurityRoleTemplateTrainingClassTransfer partySecurityRoleTemplateTrainingClassTransfer = get(partySecurityRoleTemplateTrainingClass);
        
        if(partySecurityRoleTemplateTrainingClassTransfer == null) {
            PartySecurityRoleTemplateTransfer partySecurityRoleTemplate = securityControl.getPartySecurityRoleTemplateTransfer(userVisit, partySecurityRoleTemplateTrainingClass.getPartySecurityRoleTemplate());
            TrainingClassTransfer trainingClass = trainingControl.getTrainingClassTransfer(userVisit, partySecurityRoleTemplateTrainingClass.getTrainingClass());
            
            partySecurityRoleTemplateTrainingClassTransfer = new PartySecurityRoleTemplateTrainingClassTransfer(partySecurityRoleTemplate, trainingClass);
            put(partySecurityRoleTemplateTrainingClass, partySecurityRoleTemplateTrainingClassTransfer);
        }
        
        return partySecurityRoleTemplateTrainingClassTransfer;
    }
    
}
