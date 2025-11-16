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

import com.echothree.model.control.core.common.transfer.TextDecorationDescriptionTransfer;
import com.echothree.model.control.core.server.control.TextControl;
import com.echothree.model.data.core.server.entity.TextDecorationDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TextDecorationDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<TextDecorationDescription, TextDecorationDescriptionTransfer> {

    TextControl textControl = Session.getModelController(TextControl.class);

    /** Creates a new instance of TextDecorationDescriptionTransferCache */
    protected TextDecorationDescriptionTransferCache() {
        super();
    }
    
    public TextDecorationDescriptionTransfer getTextDecorationDescriptionTransfer(UserVisit userVisit, TextDecorationDescription textDecorationDescription) {
        var textDecorationDescriptionTransfer = get(textDecorationDescription);
        
        if(textDecorationDescriptionTransfer == null) {
            var textDecorationTransfer = textControl.getTextDecorationTransfer(userVisit, textDecorationDescription.getTextDecoration());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, textDecorationDescription.getLanguage());
            
            textDecorationDescriptionTransfer = new TextDecorationDescriptionTransfer(languageTransfer, textDecorationTransfer, textDecorationDescription.getDescription());
            put(userVisit, textDecorationDescription, textDecorationDescriptionTransfer);
        }
        return textDecorationDescriptionTransfer;
    }
    
}
