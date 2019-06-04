// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.message.common.form.GetMessagesForm;
import com.echothree.control.user.message.common.result.GetMessagesResult;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.message.server.MessageControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
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

public class GetMessagesCommand
        extends BaseSimpleCommand<GetMessagesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
            new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetMessagesCommand */
    public GetMessagesCommand(UserVisitPK userVisitPK, GetMessagesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetMessagesResult result = MessageResultFactory.getGetMessagesResult();
        var coreControl = getCoreControl();
        String componentVendorName = form.getComponentVendorName();
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            UserVisit userVisit = getUserVisit();
            String entityTypeName = form.getEntityTypeName();
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            result.setComponentVendor(coreControl.getComponentVendorTransfer(userVisit, componentVendor));
            
            if(entityType != null) {
                var messageControl = (MessageControl)Session.getModelController(MessageControl.class);
                String messageTypeName = form.getMessageTypeName();
                MessageType messageType = messageControl.getMessageTypeByName(entityType, messageTypeName);
                
                result.setEntityType(coreControl.getEntityTypeTransfer(userVisit, entityType));
                
                if(messageType != null) {
                    result.setMessageType(messageControl.getMessageTypeTransfer(userVisit, messageType));
                    result.setMessages(messageControl.getMessageTransfers(userVisit, messageType));
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
