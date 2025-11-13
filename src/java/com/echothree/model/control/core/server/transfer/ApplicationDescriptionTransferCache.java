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

import com.echothree.model.control.core.common.transfer.ApplicationDescriptionTransfer;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.data.core.server.entity.ApplicationDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ApplicationDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<ApplicationDescription, ApplicationDescriptionTransfer> {

    ApplicationControl applicationControl = Session.getModelController(ApplicationControl.class);

    /** Creates a new instance of ApplicationDescriptionTransferCache */
    public ApplicationDescriptionTransferCache() {
        super();
    }
    
    public ApplicationDescriptionTransfer getApplicationDescriptionTransfer(ApplicationDescription applicationDescription) {
        var applicationDescriptionTransfer = get(applicationDescription);
        
        if(applicationDescriptionTransfer == null) {
            var applicationTransfer = applicationControl.getApplicationTransfer(userVisit, applicationDescription.getApplication());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, applicationDescription.getLanguage());
            
            applicationDescriptionTransfer = new ApplicationDescriptionTransfer(languageTransfer, applicationTransfer, applicationDescription.getDescription());
            put(userVisit, applicationDescription, applicationDescriptionTransfer);
        }
        return applicationDescriptionTransfer;
    }
    
}
