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

import com.echothree.model.control.core.common.transfer.ApplicationEditorUseDescriptionTransfer;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.data.core.server.entity.ApplicationEditorUseDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ApplicationEditorUseDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<ApplicationEditorUseDescription, ApplicationEditorUseDescriptionTransfer> {

    ApplicationControl applicationControl = Session.getModelController(ApplicationControl.class);

    /** Creates a new instance of ApplicationEditorUseDescriptionTransferCache */
    public ApplicationEditorUseDescriptionTransferCache() {
        super();
    }
    
    public ApplicationEditorUseDescriptionTransfer getApplicationEditorUseDescriptionTransfer(UserVisit userVisit, ApplicationEditorUseDescription applicationEditorUseDescription) {
        var applicationEditorUseDescriptionTransfer = get(applicationEditorUseDescription);
        
        if(applicationEditorUseDescriptionTransfer == null) {
            var applicationEditorUseTransfer = applicationControl.getApplicationEditorUseTransfer(userVisit, applicationEditorUseDescription.getApplicationEditorUse());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, applicationEditorUseDescription.getLanguage());
            
            applicationEditorUseDescriptionTransfer = new ApplicationEditorUseDescriptionTransfer(languageTransfer, applicationEditorUseTransfer, applicationEditorUseDescription.getDescription());
            put(userVisit, applicationEditorUseDescription, applicationEditorUseDescriptionTransfer);
        }
        return applicationEditorUseDescriptionTransfer;
    }
    
}
