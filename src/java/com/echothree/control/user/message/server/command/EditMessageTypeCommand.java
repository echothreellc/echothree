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

package com.echothree.control.user.message.server.command;

import com.echothree.control.user.message.common.edit.MessageEditFactory;
import com.echothree.control.user.message.common.edit.MessageTypeEdit;
import com.echothree.control.user.message.common.result.EditMessageTypeResult;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.control.user.message.common.spec.MessageTypeUniversalSpec;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.message.server.logic.MessageTypeLogic;
import com.echothree.model.data.message.server.entity.MessageType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditMessageTypeCommand
        extends BaseAbstractEditCommand<MessageTypeUniversalSpec, MessageTypeEdit, EditMessageTypeResult, MessageType, MessageType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    MessageControl messageControl;

    @Inject
    MessageTypeLogic messageTypeLogic;

    /** Creates a new instance of EditMessageTypeCommand */
    public EditMessageTypeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditMessageTypeResult getResult() {
        return MessageResultFactory.getEditMessageTypeResult();
    }

    @Override
    public MessageTypeEdit getEdit() {
        return MessageEditFactory.getMessageTypeEdit();
    }

    @Override
    public MessageType getEntity(EditMessageTypeResult result) {
        return messageTypeLogic.getMessageTypeByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public MessageType getLockEntity(MessageType messageType) {
        return messageType;
    }

    @Override
    public void fillInResult(EditMessageTypeResult result, MessageType messageType) {
        result.setMessageType(messageControl.getMessageTypeTransfer(getUserVisit(), messageType));
    }

    @Override
    public void doLock(MessageTypeEdit edit, MessageType messageType) {
        var messageTypeDescription = messageControl.getMessageTypeDescription(messageType, getPreferredLanguage());
        var messageTypeDetail = messageType.getLastDetail();

        edit.setMessageTypeName(messageTypeDetail.getMessageTypeName());
        edit.setSortOrder(messageTypeDetail.getSortOrder().toString());

        if(messageTypeDescription != null) {
            edit.setDescription(messageTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(MessageType messageType) {
        var entityType = messageType.getLastDetail().getEntityType();
        var messageTypeName = edit.getMessageTypeName();
        var duplicateMessageType = messageControl.getMessageTypeByName(entityType, messageTypeName);

        if(duplicateMessageType != null && !messageType.equals(duplicateMessageType)) {
            var entityTypeDetail = entityType.getLastDetail();

            addExecutionError(ExecutionErrors.DuplicateMessageTypeName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), messageTypeName);
        }
    }

    @Override
    public void doUpdate(MessageType messageType) {
        var partyPK = getPartyPK();
        var messageTypeDetailValue = messageControl.getMessageTypeDetailValueForUpdate(messageType);
        var messageTypeDescription = messageControl.getMessageTypeDescriptionForUpdate(messageType, getPreferredLanguage());
        var description = edit.getDescription();

        messageTypeDetailValue.setMessageTypeName(edit.getMessageTypeName());
        messageTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        messageControl.updateMessageTypeFromValue(messageTypeDetailValue, partyPK);

        if(messageTypeDescription == null && description != null) {
            messageControl.createMessageTypeDescription(messageType, getPreferredLanguage(), description, partyPK);
        } else if(messageTypeDescription != null && description == null) {
            messageControl.deleteMessageTypeDescription(messageTypeDescription, partyPK);
        } else if(messageTypeDescription != null && description != null) {
            var messageTypeDescriptionValue = messageControl.getMessageTypeDescriptionValue(messageTypeDescription);

            messageTypeDescriptionValue.setDescription(description);
            messageControl.updateMessageTypeDescriptionFromValue(messageTypeDescriptionValue, partyPK);
        }
    }

}
