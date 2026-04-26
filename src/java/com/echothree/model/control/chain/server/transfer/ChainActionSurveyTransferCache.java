// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import javax.inject.Inject;
import com.echothree.model.control.chain.common.transfer.ChainActionSurveyTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.survey.common.transfer.SurveyTransfer;
import com.echothree.model.control.survey.server.control.SurveyControl;
import com.echothree.model.data.chain.server.entity.ChainActionSurvey;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ChainActionSurveyTransferCache
        extends BaseChainTransferCache<ChainActionSurvey, ChainActionSurveyTransfer> {

    @Inject
    ChainControl chainControl;

    @Inject
    SurveyControl surveyControl;
    
    /** Creates a new instance of ChainActionSurveyTransferCache */
    protected ChainActionSurveyTransferCache() {
        super();
    }
    
    public ChainActionSurveyTransfer getChainActionSurveyTransfer(UserVisit userVisit, ChainActionSurvey chainActionSurvey) {
        var chainActionSurveyTransfer = get(chainActionSurvey);
        
        if(chainActionSurveyTransfer == null) {
            var chainAction = chainControl.getChainActionTransfer(userVisit, chainActionSurvey.getChainAction());
            SurveyTransfer survey = null; // TODO: surveyControl.getSurveyTransfer(userVisit, chainActionSurvey.getSurvey());
            
            chainActionSurveyTransfer = new ChainActionSurveyTransfer(chainAction, survey);
            put(userVisit, chainActionSurvey, chainActionSurveyTransfer);
        }
        
        return chainActionSurveyTransfer;
    }
    
}
