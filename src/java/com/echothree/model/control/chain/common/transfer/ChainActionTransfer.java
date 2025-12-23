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

package com.echothree.model.control.chain.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ChainActionTransfer
        extends BaseTransfer {

    private ChainActionSetTransfer chainActionSet;
    private String chainActionName;
    private ChainActionTypeTransfer chainActionType;
    private Integer sortOrder;
    private String description;

    private ChainActionLetterTransfer chainActionLetter;
    private ChainActionSurveyTransfer chainActionSurvey;
    private ChainActionChainActionSetTransfer chainActionChainActionSet;
    
    /** Creates a new instance of ChainActionTransfer */
    public ChainActionTransfer(ChainActionSetTransfer chainActionSet, String chainActionName, ChainActionTypeTransfer chainActionType, Integer sortOrder, String description) {
        this.chainActionSet = chainActionSet;
        this.chainActionName = chainActionName;
        this.chainActionType = chainActionType;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public ChainActionSetTransfer getChainActionSet() {
        return chainActionSet;
    }

    public void setChainActionSet(ChainActionSetTransfer chainActionSet) {
        this.chainActionSet = chainActionSet;
    }

    public String getChainActionName() {
        return chainActionName;
    }

    public void setChainActionName(String chainActionName) {
        this.chainActionName = chainActionName;
    }

    public ChainActionTypeTransfer getChainActionType() {
        return chainActionType;
    }

    public void setChainActionType(ChainActionTypeTransfer chainActionType) {
        this.chainActionType = chainActionType;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ChainActionLetterTransfer getChainActionLetter() {
        return chainActionLetter;
    }

    public void setChainActionLetter(ChainActionLetterTransfer chainActionLetter) {
        this.chainActionLetter = chainActionLetter;
    }

    public ChainActionSurveyTransfer getChainActionSurvey() {
        return chainActionSurvey;
    }

    public void setChainActionSurvey(ChainActionSurveyTransfer chainActionSurvey) {
        this.chainActionSurvey = chainActionSurvey;
    }

    public ChainActionChainActionSetTransfer getChainActionChainActionSet() {
        return chainActionChainActionSet;
    }

    public void setChainActionChainActionSet(ChainActionChainActionSetTransfer chainActionChainActionSet) {
        this.chainActionChainActionSet = chainActionChainActionSet;
    }

}
