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

package com.echothree.model.control.user.server.transfer;

import com.echothree.model.control.user.common.transfer.RecoveryQuestionDescriptionTransfer;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.server.entity.RecoveryQuestionDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RecoveryQuestionDescriptionTransferCache
        extends BaseUserDescriptionTransferCache<RecoveryQuestionDescription, RecoveryQuestionDescriptionTransfer> {

    UserControl userControl = Session.getModelController(UserControl.class);

    /** Creates a new instance of RecoveryQuestionDescriptionTransferCache */
    public RecoveryQuestionDescriptionTransferCache() {
        super();
    }
    
    public RecoveryQuestionDescriptionTransfer getRecoveryQuestionDescriptionTransfer(UserVisit userVisit, RecoveryQuestionDescription recoveryQuestionDescription) {
        var recoveryQuestionDescriptionTransfer = get(recoveryQuestionDescription);
        
        if(recoveryQuestionDescriptionTransfer == null) {
            var recoveryQuestionTransfer = userControl.getRecoveryQuestionTransfer(userVisit, recoveryQuestionDescription.getRecoveryQuestion());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, recoveryQuestionDescription.getLanguage());
            
            recoveryQuestionDescriptionTransfer = new RecoveryQuestionDescriptionTransfer(languageTransfer, recoveryQuestionTransfer, recoveryQuestionDescription.getDescription());
            put(userVisit, recoveryQuestionDescription, recoveryQuestionDescriptionTransfer);
        }
        
        return recoveryQuestionDescriptionTransfer;
    }
    
}
