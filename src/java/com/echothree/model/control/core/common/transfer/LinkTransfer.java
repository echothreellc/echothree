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

package com.echothree.model.control.core.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class LinkTransfer
        extends BaseTransfer {

    private LinkTypeTransfer linkType;
    private EntityInstanceTransfer sourceEntityInstance;
    private EntityInstanceTransfer targetEntityInstance;

    public LinkTransfer(LinkTypeTransfer linkType, EntityInstanceTransfer sourceEntityInstance, EntityInstanceTransfer targetEntityInstance) {
        this.linkType = linkType;
        this.sourceEntityInstance = sourceEntityInstance;
        this.targetEntityInstance = targetEntityInstance;
    }

    public LinkTypeTransfer getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkTypeTransfer linkType) {
        this.linkType = linkType;
    }

    public EntityInstanceTransfer getSourceEntityInstance() {
        return sourceEntityInstance;
    }

    public void setSourceEntityInstance(EntityInstanceTransfer sourceEntityInstance) {
        this.sourceEntityInstance = sourceEntityInstance;
    }

    public EntityInstanceTransfer getTargetEntityInstance() {
        return targetEntityInstance;
    }

    public void setTargetEntityInstance(EntityInstanceTransfer targetEntityInstance) {
        this.targetEntityInstance = targetEntityInstance;
    }

}
