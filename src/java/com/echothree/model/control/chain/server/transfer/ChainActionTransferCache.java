// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.control.chain.common.transfer.ChainActionSetTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionTypeTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainAction;
import com.echothree.model.data.chain.server.entity.ChainActionDetail;
import com.echothree.model.data.chain.server.entity.ChainActionType;
import com.echothree.model.data.user.server.entity.UserVisit;
import java.util.Set;

public class ChainActionTransferCache
        extends BaseChainTransferCache<ChainAction, ChainActionTransfer> {

    boolean includeRelated;

    /** Creates a new instance of ChainActionTransferCache */
    public ChainActionTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);

        var options = session.getOptions();
        if(options != null) {
            includeRelated = options.contains(ChainOptions.ChainActionIncludeRelated);
        }
        
        setIncludeEntityInstance(true);
    }

    public ChainActionTransfer getChainActionTransfer(ChainAction chainAction) {
        ChainActionTransfer chainActionTransfer = get(chainAction);

        if(chainActionTransfer == null) {
            ChainActionDetail chainActionDetail = chainAction.getLastDetail();
            ChainActionSetTransfer chainActionSetTransfer = chainControl.getChainActionSetTransfer(userVisit, chainActionDetail.getChainActionSet());
            String chainActionName = chainActionDetail.getChainActionName();
            ChainActionType chainActionType = chainActionDetail.getChainActionType();
            ChainActionTypeTransfer chainActionTypeTransfer = chainControl.getChainActionTypeTransfer(userVisit, chainActionType);
            Integer sortOrder = chainActionDetail.getSortOrder();
            String description = chainControl.getBestChainActionDescription(chainAction, getLanguage());

            chainActionTransfer = new ChainActionTransfer(chainActionSetTransfer, chainActionName, chainActionTypeTransfer, sortOrder, description);
            put(chainAction, chainActionTransfer);

            if(includeRelated) {
                String chainActionTypeName = chainActionType.getLastDetail().getChainActionTypeName();
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