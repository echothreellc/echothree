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

import com.echothree.model.control.message.common.exception.UnknownMessageNameException;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.message.server.entity.Message;
import com.echothree.model.data.message.server.entity.MessageType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MessageLogic
        extends BaseLogic {

    @Inject
    MessageControl messageControl;

    @Inject
    MessageTypeLogic messageTypeLogic;

    protected MessageLogic() {
        super();
    }

    public Message getMessageByName(final ExecutionErrorAccumulator eea, final MessageType messageType, final String messageName,
            final EntityPermission entityPermission) {
        var message = messageControl.getMessageByName(messageType, messageName, entityPermission);

        if(message == null) {
            var messageTypeDetail = messageType.getLastDetail();
            var entityTypeDetail = messageTypeDetail.getEntityType().getLastDetail();

            handleExecutionError(UnknownMessageNameException.class, eea, ExecutionErrors.UnknownMessageName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                    messageTypeDetail.getMessageTypeName(), messageName);
        }

        return message;
    }

    public Message getMessageByName(final ExecutionErrorAccumulator eea, final MessageType messageType, final String messageName) {
        return getMessageByName(eea, messageType, messageName, EntityPermission.READ_ONLY);
    }

    public Message getMessageByNameForUpdate(final ExecutionErrorAccumulator eea, final MessageType messageType, final String messageName) {
        return getMessageByName(eea, messageType, messageName, EntityPermission.READ_WRITE);
    }

    public Message getMessageByName(final ExecutionErrorAccumulator eea, final String componentVendorName, final String entityTypeName,
            final String messageTypeName, final String messageName, final EntityPermission entityPermission) {
        var messageType = messageTypeLogic.getMessageTypeByName(eea, componentVendorName, entityTypeName, messageTypeName);
        Message message = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            message = getMessageByName(eea, messageType, messageName, entityPermission);
        }

        return message;
    }

    public Message getMessageByName(final ExecutionErrorAccumulator eea, final String componentVendorName, final String entityTypeName,
            final String messageTypeName, final String messageName) {
        return getMessageByName(eea, componentVendorName, entityTypeName, messageTypeName, messageName, EntityPermission.READ_ONLY);
    }

    public Message getMessageByNameForUpdate(final ExecutionErrorAccumulator eea, final String componentVendorName, final String entityTypeName,
            final String messageTypeName, final String messageName) {
        return getMessageByName(eea, componentVendorName, entityTypeName, messageTypeName, messageName, EntityPermission.READ_WRITE);
    }

}
