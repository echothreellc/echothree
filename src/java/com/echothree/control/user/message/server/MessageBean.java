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

package com.echothree.control.user.message.server;

import com.echothree.control.user.message.common.MessageRemote;
import com.echothree.control.user.message.common.form.*;
import com.echothree.control.user.message.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class MessageBean
        extends MessageFormsImpl
        implements MessageRemote, MessageLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "MessageBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Message Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createMessageType(UserVisitPK userVisitPK, CreateMessageTypeForm form) {
        return CDI.current().select(CreateMessageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMessageType(UserVisitPK userVisitPK, GetMessageTypeForm form) {
        return CDI.current().select(GetMessageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMessageTypes(UserVisitPK userVisitPK, GetMessageTypesForm form) {
        return CDI.current().select(GetMessageTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editMessageType(UserVisitPK userVisitPK, EditMessageTypeForm form) {
        return CDI.current().select(EditMessageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteMessageType(UserVisitPK userVisitPK, DeleteMessageTypeForm form) {
        return CDI.current().select(DeleteMessageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Message Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createMessageTypeDescription(UserVisitPK userVisitPK, CreateMessageTypeDescriptionForm form) {
        return CDI.current().select(CreateMessageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMessageTypeDescriptions(UserVisitPK userVisitPK, GetMessageTypeDescriptionsForm form) {
        return CDI.current().select(GetMessageTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editMessageTypeDescription(UserVisitPK userVisitPK, EditMessageTypeDescriptionForm form) {
        return CDI.current().select(EditMessageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteMessageTypeDescription(UserVisitPK userVisitPK, DeleteMessageTypeDescriptionForm form) {
        return CDI.current().select(DeleteMessageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Messages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createMessage(UserVisitPK userVisitPK, CreateMessageForm form) {
        return CDI.current().select(CreateMessageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMessage(UserVisitPK userVisitPK, GetMessageForm form) {
        return CDI.current().select(GetMessageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMessages(UserVisitPK userVisitPK, GetMessagesForm form) {
        return CDI.current().select(GetMessagesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteMessage(UserVisitPK userVisitPK, DeleteMessageForm form) {
        return CDI.current().select(DeleteMessageCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Message Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createMessageDescription(UserVisitPK userVisitPK, CreateMessageDescriptionForm form) {
        return CDI.current().select(CreateMessageDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMessageDescriptions(UserVisitPK userVisitPK, GetMessageDescriptionsForm form) {
        return CDI.current().select(GetMessageDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editMessageDescription(UserVisitPK userVisitPK, EditMessageDescriptionForm form) {
        return CDI.current().select(EditMessageDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteMessageDescription(UserVisitPK userVisitPK, DeleteMessageDescriptionForm form) {
        return CDI.current().select(DeleteMessageDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Messages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityMessage(UserVisitPK userVisitPK, CreateEntityMessageForm form) {
        return CDI.current().select(CreateEntityMessageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityMessage(UserVisitPK userVisitPK, GetEntityMessageForm form) {
        return CDI.current().select(GetEntityMessageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityMessages(UserVisitPK userVisitPK, GetEntityMessagesForm form) {
        return CDI.current().select(GetEntityMessagesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityMessage(UserVisitPK userVisitPK, DeleteEntityMessageForm form) {
        return CDI.current().select(DeleteEntityMessageCommand.class).get().run(userVisitPK, form);
    }
    
}
