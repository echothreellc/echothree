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
import com.echothree.control.user.message.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface MessageService
        extends MessageForms {
    
    // -------------------------------------------------------------------------
    //   Message Types
    // -------------------------------------------------------------------------
    
    CommandResult<CreateMessageTypeResult> createMessageType(UserVisitPK userVisitPK, CreateMessageTypeForm form);
    
    CommandResult<GetMessageTypeResult> getMessageType(UserVisitPK userVisitPK, GetMessageTypeForm form);
    
    CommandResult<GetMessageTypesResult> getMessageTypes(UserVisitPK userVisitPK, GetMessageTypesForm form);
    
    CommandResult<EditMessageTypeResult> editMessageType(UserVisitPK userVisitPK, EditMessageTypeForm form);
    
    CommandResult<VoidResult> deleteMessageType(UserVisitPK userVisitPK, DeleteMessageTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Message Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createMessageTypeDescription(UserVisitPK userVisitPK, CreateMessageTypeDescriptionForm form);
    
    CommandResult<GetMessageTypeDescriptionsResult> getMessageTypeDescriptions(UserVisitPK userVisitPK, GetMessageTypeDescriptionsForm form);
    
    CommandResult<EditMessageTypeDescriptionResult> editMessageTypeDescription(UserVisitPK userVisitPK, EditMessageTypeDescriptionForm form);
    
    CommandResult<VoidResult> deleteMessageTypeDescription(UserVisitPK userVisitPK, DeleteMessageTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Messages
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createMessage(UserVisitPK userVisitPK, CreateMessageForm form);
    
    CommandResult<GetMessageResult> getMessage(UserVisitPK userVisitPK, GetMessageForm form);
    
    CommandResult<GetMessagesResult> getMessages(UserVisitPK userVisitPK, GetMessagesForm form);
    
    CommandResult<VoidResult> deleteMessage(UserVisitPK userVisitPK, DeleteMessageForm form);
    
    // -------------------------------------------------------------------------
    //   Message Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createMessageDescription(UserVisitPK userVisitPK, CreateMessageDescriptionForm form);
    
    CommandResult<GetMessageDescriptionsResult> getMessageDescriptions(UserVisitPK userVisitPK, GetMessageDescriptionsForm form);
    
    CommandResult<EditMessageDescriptionResult> editMessageDescription(UserVisitPK userVisitPK, EditMessageDescriptionForm form);
    
    CommandResult<VoidResult> deleteMessageDescription(UserVisitPK userVisitPK, DeleteMessageDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Messages
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityMessage(UserVisitPK userVisitPK, CreateEntityMessageForm form);
    
    CommandResult<GetEntityMessageResult> getEntityMessage(UserVisitPK userVisitPK, GetEntityMessageForm form);
    
    CommandResult<GetEntityMessagesResult> getEntityMessages(UserVisitPK userVisitPK, GetEntityMessagesForm form);
    
    CommandResult<VoidResult> deleteEntityMessage(UserVisitPK userVisitPK, DeleteEntityMessageForm form);
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
        
}
