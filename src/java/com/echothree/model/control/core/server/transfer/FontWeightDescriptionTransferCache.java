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

import com.echothree.model.control.core.common.transfer.FontWeightDescriptionTransfer;
import com.echothree.model.control.core.server.control.FontControl;
import com.echothree.model.data.core.server.entity.FontWeightDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FontWeightDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<FontWeightDescription, FontWeightDescriptionTransfer> {

    FontControl fontControl = Session.getModelController(FontControl.class);

    /** Creates a new instance of FontWeightDescriptionTransferCache */
    protected FontWeightDescriptionTransferCache() {
        super();
    }
    
    public FontWeightDescriptionTransfer getFontWeightDescriptionTransfer(UserVisit userVisit, FontWeightDescription fontWeightDescription) {
        var fontWeightDescriptionTransfer = get(fontWeightDescription);
        
        if(fontWeightDescriptionTransfer == null) {
            var fontWeightTransfer = fontControl.getFontWeightTransfer(userVisit, fontWeightDescription.getFontWeight());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, fontWeightDescription.getLanguage());
            
            fontWeightDescriptionTransfer = new FontWeightDescriptionTransfer(languageTransfer, fontWeightTransfer, fontWeightDescription.getDescription());
            put(userVisit, fontWeightDescription, fontWeightDescriptionTransfer);
        }
        return fontWeightDescriptionTransfer;
    }
    
}
