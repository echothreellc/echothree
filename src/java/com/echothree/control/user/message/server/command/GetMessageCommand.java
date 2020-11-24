// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.message.common.form.GetMessageForm;
import com.echothree.control.user.message.common.result.GetMessageResult;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.message.server.entity.Message;
import com.echothree.model.data.message.server.entity.MessageType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetMessageCommand
        extends BaseSimpleCommand<GetMessageForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
            new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("MessageName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetMessageCommand */
    public GetMessageCommand(UserVisitPK userVisitPK, GetMessageForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetMessageResult result = MessageResultFactory.getGetMessageResult();
        var coreControl = getCoreControl();
        String componentVendorName = form.getComponentVendorName();
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            UserVisit userVisit = getUserVisit();
            String entityTypeName = form.getEntityTypeName();
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            result.setComponentVendor(coreControl.getComponentVendorTransfer(userVisit, componentVendor));
            
            if(entityType != null) {
                var messageControl = Session.getModelController(MessageControl.class);
                String messageTypeName = form.getMessageTypeName();
                MessageType messageType = messageControl.getMessageTypeByName(entityType, messageTypeName);
                
                result.setEntityType(coreControl.getEntityTypeTransfer(userVisit, entityType));
                
                if(messageType != null) {
                    String messageName = form.getMessageName();
                    Message message = messageControl.getMessageByName(messageType, messageName);
                    
                    result.setMessageType(messageControl.getMessageTypeTransfer(userVisit, messageType));
                    
                    if(message != null) {
                        result.setMessage(messageControl.getMessageTransfer(userVisit, message));
                        
                        sendEventUsingNames(messageType.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownMessageName.name(), messageName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownMessageTypeName.name(), messageTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }
        
        return result;
    }
    
}
