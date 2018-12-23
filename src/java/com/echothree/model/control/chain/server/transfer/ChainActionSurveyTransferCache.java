// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.chain.server.transfer;

import com.echothree.model.control.chain.common.transfer.ChainActionSurveyTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionTransfer;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.survey.common.transfer.SurveyTransfer;
import com.echothree.model.control.survey.server.SurveyControl;
import com.echothree.model.data.chain.server.entity.ChainActionSurvey;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ChainActionSurveyTransferCache
        extends BaseChainTransferCache<ChainActionSurvey, ChainActionSurveyTransfer> {
    
    SurveyControl surveyControl = (SurveyControl)Session.getModelController(SurveyControl.class);
    
    /** Creates a new instance of ChainActionSurveyTransferCache */
    public ChainActionSurveyTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);
    }
    
    public ChainActionSurveyTransfer getChainActionSurveyTransfer(ChainActionSurvey chainActionSurvey) {
        ChainActionSurveyTransfer chainActionSurveyTransfer = get(chainActionSurvey);
        
        if(chainActionSurveyTransfer == null) {
            ChainActionTransfer chainAction = chainControl.getChainActionTransfer(userVisit, chainActionSurvey.getChainAction());
            SurveyTransfer survey = null; // TODO: surveyControl.getSurveyTransfer(userVisit, chainActionSurvey.getSurvey());
            
            chainActionSurveyTransfer = new ChainActionSurveyTransfer(chainAction, survey);
            put(chainActionSurvey, chainActionSurveyTransfer);
        }
        
        return chainActionSurveyTransfer;
    }
    
}
