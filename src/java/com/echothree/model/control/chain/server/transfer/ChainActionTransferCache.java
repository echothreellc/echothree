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

package com.echothree.model.control.chain.server.transfer;

import com.echothree.model.control.chain.common.ChainConstants;
import com.echothree.model.control.chain.common.ChainOptions;
import com.echothree.model.control.chain.common.transfer.ChainActionTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainAction;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ChainActionTransferCache
        extends BaseChainTransferCache<ChainAction, ChainActionTransfer> {

    ChainControl chainControl = Session.getModelController(ChainControl.class);

    boolean includeRelated;

    /** Creates a new instance of ChainActionTransferCache */
    public ChainActionTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            includeRelated = options.contains(ChainOptions.ChainActionIncludeRelated);
        }
        
        setIncludeEntityInstance(true);
    }

    public ChainActionTransfer getChainActionTransfer(UserVisit userVisit, ChainAction chainAction) {
        var chainActionTransfer = get(chainAction);

        if(chainActionTransfer == null) {
            var chainActionDetail = chainAction.getLastDetail();
            var chainActionSetTransfer = chainControl.getChainActionSetTransfer(userVisit, chainActionDetail.getChainActionSet());
            var chainActionName = chainActionDetail.getChainActionName();
            var chainActionType = chainActionDetail.getChainActionType();
            var chainActionTypeTransfer = chainControl.getChainActionTypeTransfer(userVisit, chainActionType);
            var sortOrder = chainActionDetail.getSortOrder();
            var description = chainControl.getBestChainActionDescription(chainAction, getLanguage(userVisit));

            chainActionTransfer = new ChainActionTransfer(chainActionSetTransfer, chainActionName, chainActionTypeTransfer, sortOrder, description);
            put(userVisit, chainAction, chainActionTransfer);

            if(includeRelated) {
                var chainActionTypeName = chainActionType.getLastDetail().getChainActionTypeName();
                if(chainActionTypeName.equals(ChainConstants.ChainActionType_LETTER)) {
                    chainActionTransfer.setChainActionLetter(chainControl.getChainActionLetterTransfer(userVisit, chainAction));
                } else if(chainActionTypeName.equals(ChainConstants.ChainActionType_SURVEY)) {
                    chainActionTransfer.setChainActionSurvey(chainControl.getChainActionSurveyTransfer(userVisit, chainAction));
                } else if(chainActionTypeName.equals(ChainConstants.ChainActionType_CHAIN_ACTION_SET)) {
                    chainActionTransfer.setChainActionChainActionSet(chainControl.getChainActionChainActionSetTransfer(userVisit, chainAction));
                }
            }
        }

        return chainActionTransfer;
    }
}