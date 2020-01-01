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

package com.echothree.model.control.user.server.transfer;

import com.echothree.model.control.user.common.transfer.RecoveryQuestionTransfer;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.model.data.user.server.entity.RecoveryQuestionDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class RecoveryQuestionTransferCache
        extends BaseUserTransferCache<RecoveryQuestion, RecoveryQuestionTransfer> {
    
    /** Creates a new instance of RecoveryQuestionTransferCache */
    public RecoveryQuestionTransferCache(UserVisit userVisit, UserControl userControl) {
        super(userVisit, userControl);

        setIncludeEntityInstance(true);
    }
    
    public RecoveryQuestionTransfer getRecoveryQuestionTransfer(RecoveryQuestion recoveryQuestion) {
        RecoveryQuestionTransfer recoveryQuestionTransfer = get(recoveryQuestion);
        
        if(recoveryQuestionTransfer == null) {
            RecoveryQuestionDetail recoveryQuestionDetail = recoveryQuestion.getLastDetail();
            String recoveryQuestionName = recoveryQuestionDetail.getRecoveryQuestionName();
            Boolean isDefault = recoveryQuestionDetail.getIsDefault();
            Integer sortOrder = recoveryQuestionDetail.getSortOrder();
            String description = userControl.getBestRecoveryQuestionDescription(recoveryQuestion, getLanguage());
            
            recoveryQuestionTransfer = new RecoveryQuestionTransfer(recoveryQuestionName, isDefault, sortOrder, description);
            put(recoveryQuestion, recoveryQuestionTransfer);
        }
        
        return recoveryQuestionTransfer;
    }
    
}
