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

import com.echothree.control.user.message.common.spec.MessageTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
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
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    EntityTypeLogic entityTypeLogic;

    protected MessageTypeLogic() {
        super();
    }

    public MessageType getMessageTypeByName(final ExecutionErrorAccumulator eea, final EntityType entityType, final String messageTypeName,
            final EntityPermission entityPermission) {
        var messageType = messageControl.getMessageTypeByName(entityType, messageTypeName, entityPermission);

        if(messageType == null) {
            var entityTypeDetail = entityType.getLastDetail();

            handleExecutionError(UnknownMessageTypeNameException.class, eea, ExecutionErrors.UnknownMessageTypeName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(), messageTypeName);
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

        if(!hasExecutionErrors(eea)) {
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

    public MessageType getMessageTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final MessageTypeUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        MessageType messageType = null;
        var componentVendorName = universalSpec.getComponentVendorName();
        var entityTypeName = universalSpec.getEntityTypeName();
        var messageTypeName = universalSpec.getMessageTypeName();
        var nameParameterCount = (componentVendorName == null ? 0 : 1) + (entityTypeName == null ? 0 : 1)
                + (messageTypeName == null ? 0 : 1);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(nameParameterCount == 3 && possibleEntitySpecs == 0) {
            messageType = getMessageTypeByName(eea, componentVendorName, entityTypeName, messageTypeName, entityPermission);
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.MessageType.name());

            if(!hasExecutionErrors(eea)) {
                messageType = messageControl.getMessageTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return messageType;
    }

    public MessageType getMessageTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final MessageTypeUniversalSpec universalSpec) {
        return getMessageTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public MessageType getMessageTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final MessageTypeUniversalSpec universalSpec) {
        return getMessageTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
