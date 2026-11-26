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
import com.echothree.control.user.message.common.edit.MessageTypeDescriptionEdit;
import com.echothree.control.user.message.common.result.EditMessageTypeDescriptionResult;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.control.user.message.common.spec.MessageTypeDescriptionSpec;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.message.server.logic.MessageTypeLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.message.server.entity.MessageType;
import com.echothree.model.data.message.server.entity.MessageTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditMessageTypeDescriptionCommand
        extends BaseEditCommand<MessageTypeDescriptionSpec, MessageTypeDescriptionEdit, EditMessageTypeDescriptionResult, MessageTypeDescription, MessageType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    MessageControl messageControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    MessageTypeLogic messageTypeLogic;

    /** Creates a new instance of EditMessageTypeDescriptionCommand */
    public EditMessageTypeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditMessageTypeDescriptionResult getResult() {
        return MessageResultFactory.getEditMessageTypeDescriptionResult();
    }

    @Override
    public MessageTypeDescriptionEdit getEdit() {
        return MessageEditFactory.getMessageTypeDescriptionEdit();
    }

    @Override
    public MessageTypeDescription getEntity(EditMessageTypeDescriptionResult result) {
        MessageTypeDescription messageTypeDescription = null;
        var componentVendorName = spec.getComponentVendorName();
        var entityTypeName = spec.getEntityTypeName();
        var messageTypeName = spec.getMessageTypeName();
        var languageIsoName = spec.getLanguageIsoName();
        var messageType = messageTypeLogic.getMessageTypeByName(this, componentVendorName, entityTypeName, messageTypeName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            messageTypeDescription = messageControl.getMessageTypeDescription(messageType, language,
                    editModeToEntityPermission(editMode));

            if(messageTypeDescription == null) {
                addExecutionError(ExecutionErrors.UnknownMessageTypeDescription.name(), componentVendorName, entityTypeName, messageTypeName, languageIsoName);
            }
        }

        return messageTypeDescription;
    }

    @Override
    public MessageType getLockEntity(MessageTypeDescription messageTypeDescription) {
        return messageTypeDescription.getMessageType();
    }

    @Override
    public void fillInResult(EditMessageTypeDescriptionResult result, MessageTypeDescription messageTypeDescription) {
        result.setMessageTypeDescription(messageControl.getMessageTypeDescriptionTransfer(getUserVisit(), messageTypeDescription));
    }

    @Override
    public void doLock(MessageTypeDescriptionEdit edit, MessageTypeDescription messageTypeDescription) {
        edit.setDescription(messageTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(MessageTypeDescription messageTypeDescription) {
        var messageTypeDescriptionValue = messageControl.getMessageTypeDescriptionValue(messageTypeDescription);

        messageTypeDescriptionValue.setDescription(edit.getDescription());

        messageControl.updateMessageTypeDescriptionFromValue(messageTypeDescriptionValue, getPartyPK());
    }

}
