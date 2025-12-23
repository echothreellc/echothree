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

public class ChainActionChainActionSetTransfer
        extends BaseTransfer {

    private ChainActionTransfer chainAction;
    private ChainActionSetTransfer nextChainActionSet;
    private Long unformattedDelayTime;
    private String delayTime;

    /** Creates a new instance of ChainActionChainActionSetTransfer */
    public ChainActionChainActionSetTransfer(ChainActionTransfer chainAction, ChainActionSetTransfer nextChainActionSet, Long unformattedDelayTime, String delayTime) {
        this.chainAction = chainAction;
        this.nextChainActionSet = nextChainActionSet;
        this.unformattedDelayTime = unformattedDelayTime;
        this.delayTime = delayTime;
    }

    public ChainActionTransfer getChainAction() {
        return chainAction;
    }

    public void setChainAction(ChainActionTransfer chainAction) {
        this.chainAction = chainAction;
    }

    public ChainActionSetTransfer getNextChainActionSet() {
        return nextChainActionSet;
    }

    public void setNextChainActionSet(ChainActionSetTransfer nextChainActionSet) {
        this.nextChainActionSet = nextChainActionSet;
    }

    public Long getUnformattedDelayTime() {
        return unformattedDelayTime;
    }

    public void setUnformattedDelayTime(Long unformattedDelayTime) {
        this.unformattedDelayTime = unformattedDelayTime;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }
}