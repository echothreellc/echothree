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

import com.echothree.model.control.core.common.transfer.AppearanceDescriptionTransfer;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.AppearanceDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class AppearanceDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<AppearanceDescription, AppearanceDescriptionTransfer> {

    AppearanceControl appearanceControl = Session.getModelController(AppearanceControl.class);
    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of AppearanceDescriptionTransferCache */
    public AppearanceDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public AppearanceDescriptionTransfer getAppearanceDescriptionTransfer(AppearanceDescription appearanceDescription) {
        var appearanceDescriptionTransfer = get(appearanceDescription);
        
        if(appearanceDescriptionTransfer == null) {
            var appearanceTransfer = appearanceControl.getAppearanceTransfer(userVisit, appearanceDescription.getAppearance());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, appearanceDescription.getLanguage());
            
            appearanceDescriptionTransfer = new AppearanceDescriptionTransfer(languageTransfer, appearanceTransfer, appearanceDescription.getDescription());
            put(appearanceDescription, appearanceDescriptionTransfer);
        }
        return appearanceDescriptionTransfer;
    }
    
}
