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

import com.echothree.control.user.message.common.edit.MessageDescriptionEdit;
import com.echothree.control.user.message.common.edit.MessageEditFactory;
import com.echothree.control.user.message.common.result.EditMessageDescriptionResult;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.control.user.message.common.spec.MessageDescriptionSpec;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.message.server.logic.MessageLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.message.server.entity.Message;
import com.echothree.model.data.message.server.entity.MessageDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditMessageDescriptionCommand
        extends BaseEditCommand<MessageDescriptionSpec, MessageDescriptionEdit, EditMessageDescriptionResult, MessageDescription, Message> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MessageName", FieldType.ENTITY_NAME, true, null, null),
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
    MessageLogic messageLogic;

    /** Creates a new instance of EditMessageDescriptionCommand */
    public EditMessageDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditMessageDescriptionResult getResult() {
        return MessageResultFactory.getEditMessageDescriptionResult();
    }

    @Override
    public MessageDescriptionEdit getEdit() {
        return MessageEditFactory.getMessageDescriptionEdit();
    }

    @Override
    public MessageDescription getEntity(EditMessageDescriptionResult result) {
        MessageDescription messageDescription = null;
        var componentVendorName = spec.getComponentVendorName();
        var entityTypeName = spec.getEntityTypeName();
        var messageTypeName = spec.getMessageTypeName();
        var messageName = spec.getMessageName();
        var languageIsoName = spec.getLanguageIsoName();
        var message = messageLogic.getMessageByName(this, componentVendorName, entityTypeName, messageTypeName, messageName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            messageDescription = messageControl.getMessageDescription(message, language,
                    editModeToEntityPermission(editMode));

            if(messageDescription == null) {
                addExecutionError(ExecutionErrors.UnknownMessageDescription.name(), componentVendorName, entityTypeName, messageTypeName, messageName, languageIsoName);
            }
        }

        return messageDescription;
    }

    @Override
    public Message getLockEntity(MessageDescription messageDescription) {
        return messageDescription.getMessage();
    }

    @Override
    public void fillInResult(EditMessageDescriptionResult result, MessageDescription messageDescription) {
        result.setMessageDescription(messageControl.getMessageDescriptionTransfer(getUserVisit(), messageDescription));
    }

    @Override
    public void doLock(MessageDescriptionEdit edit, MessageDescription messageDescription) {
        edit.setDescription(messageDescription.getDescription());
    }

    @Override
    public void doUpdate(MessageDescription messageDescription) {
        var messageDescriptionValue = messageControl.getMessageDescriptionValue(messageDescription);

        messageDescriptionValue.setDescription(edit.getDescription());

        messageControl.updateMessageDescriptionFromValue(messageDescriptionValue, getPartyPK());
    }

}
