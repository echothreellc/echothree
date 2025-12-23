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

package com.echothree.model.control.message.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class EntityMessageTransfer
        extends BaseTransfer {
    
    private EntityInstanceTransfer entityInstance;
    private MessageTransfer message;
    
    /** Creates a new instance of EntityMessageTransfer */
    public EntityMessageTransfer(EntityInstanceTransfer entityInstance, MessageTransfer message) {
        this.entityInstance = entityInstance;
        this.message = message;
    }
    
    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }
    
    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }
    
    public MessageTransfer getMessage() {
        return message;
    }
    
    public void setMessage(MessageTransfer message) {
        this.message = message;
    }
    
}
