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

public class ChainActionTypeUseTransfer
        extends BaseTransfer {

    private ChainKindTransfer chainKind;
    private ChainActionTypeTransfer chainActionType;
    private Boolean isDefault;

    /** Creates a new instance of ChainActionTypeUseTransfer */
    public ChainActionTypeUseTransfer(ChainKindTransfer chainKind, ChainActionTypeTransfer chainActionType, Boolean isDefault) {
        this.chainKind = chainKind;
        this.chainActionType = chainActionType;
        this.isDefault = isDefault;
    }

    public ChainKindTransfer getChainKind() {
        return chainKind;
    }

    public void setChainKind(ChainKindTransfer chainKind) {
        this.chainKind = chainKind;
    }

    public ChainActionTypeTransfer getChainActionType() {
        return chainActionType;
    }

    public void setChainActionType(ChainActionTypeTransfer chainActionType) {
        this.chainActionType = chainActionType;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}