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

package com.echothree.model.control.message.server.logic;

import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.message.common.exception.UnknownMessageTypeNameException;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.message.server.entity.MessageType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MessageTypeLogic
        extends BaseLogic {

    @Inject
    MessageControl messageControl;

    @Inject
    EntityTypeLogic entityTypeLogic;

    protected MessageTypeLogic() {
        super();
    }

    public MessageType getMessageTypeByName(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String messageTypeName, final EntityPermission entityPermission) {
        var messageType = messageControl.getMessageTypeByName(entityType, messageTypeName, entityPermission);

        if(messageType == null) {
            handleExecutionError(UnknownMessageTypeNameException.class, eea, ExecutionErrors.UnknownMessageTypeName.name(),
                    entityType.getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityType.getLastDetail().getEntityTypeName(), messageTypeName);
        }

        return messageType;
    }

    public MessageType getMessageTypeByName(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String messageTypeName) {
        return getMessageTypeByName(eea, entityType, messageTypeName, EntityPermission.READ_ONLY);
    }

    public MessageType getMessageTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String messageTypeName) {
        return getMessageTypeByName(eea, entityType, messageTypeName, EntityPermission.READ_WRITE);
    }

    public MessageType getMessageTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String messageTypeName, final EntityPermission entityPermission) {
        var entityType = entityTypeLogic.getEntityTypeByName(eea, componentVendorName, entityTypeName);
        MessageType messageType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            messageType = getMessageTypeByName(eea, entityType, messageTypeName, entityPermission);
        }

        return messageType;
    }

    public MessageType getMessageTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String messageTypeName) {
        return getMessageTypeByName(eea, componentVendorName, entityTypeName, messageTypeName, EntityPermission.READ_ONLY);
    }

    public MessageType getMessageTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String messageTypeName) {
        return getMessageTypeByName(eea, componentVendorName, entityTypeName, messageTypeName, EntityPermission.READ_WRITE);
    }

}
