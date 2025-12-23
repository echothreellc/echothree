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

package com.echothree.control.user.message.common;

import com.echothree.control.user.message.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface MessageService
        extends MessageForms {
    
    // -------------------------------------------------------------------------
    //   Message Types
    // -------------------------------------------------------------------------
    
    CommandResult createMessageType(UserVisitPK userVisitPK, CreateMessageTypeForm form);
    
    CommandResult getMessageType(UserVisitPK userVisitPK, GetMessageTypeForm form);
    
    CommandResult getMessageTypes(UserVisitPK userVisitPK, GetMessageTypesForm form);
    
    CommandResult editMessageType(UserVisitPK userVisitPK, EditMessageTypeForm form);
    
    CommandResult deleteMessageType(UserVisitPK userVisitPK, DeleteMessageTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Message Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createMessageTypeDescription(UserVisitPK userVisitPK, CreateMessageTypeDescriptionForm form);
    
    CommandResult getMessageTypeDescriptions(UserVisitPK userVisitPK, GetMessageTypeDescriptionsForm form);
    
    CommandResult editMessageTypeDescription(UserVisitPK userVisitPK, EditMessageTypeDescriptionForm form);
    
    CommandResult deleteMessageTypeDescription(UserVisitPK userVisitPK, DeleteMessageTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Messages
    // -------------------------------------------------------------------------
    
    CommandResult createMessage(UserVisitPK userVisitPK, CreateMessageForm form);
    
    CommandResult getMessage(UserVisitPK userVisitPK, GetMessageForm form);
    
    CommandResult getMessages(UserVisitPK userVisitPK, GetMessagesForm form);
    
    CommandResult deleteMessage(UserVisitPK userVisitPK, DeleteMessageForm form);
    
    // -------------------------------------------------------------------------
    //   Message Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createMessageDescription(UserVisitPK userVisitPK, CreateMessageDescriptionForm form);
    
    CommandResult getMessageDescriptions(UserVisitPK userVisitPK, GetMessageDescriptionsForm form);
    
    CommandResult editMessageDescription(UserVisitPK userVisitPK, EditMessageDescriptionForm form);
    
    CommandResult deleteMessageDescription(UserVisitPK userVisitPK, DeleteMessageDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Messages
    // -------------------------------------------------------------------------
    
    CommandResult createEntityMessage(UserVisitPK userVisitPK, CreateEntityMessageForm form);
    
    CommandResult getEntityMessage(UserVisitPK userVisitPK, GetEntityMessageForm form);
    
    CommandResult getEntityMessages(UserVisitPK userVisitPK, GetEntityMessagesForm form);
    
    CommandResult deleteEntityMessage(UserVisitPK userVisitPK, DeleteEntityMessageForm form);
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
        
}
