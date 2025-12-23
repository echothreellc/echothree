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

import com.echothree.control.user.message.common.form.GetEntityMessagesForm;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetEntityMessagesCommand
        extends BaseSimpleCommand<GetEntityMessagesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
            new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
            new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("MessageName", FieldType.TAG, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetEntityMessagesCommand */
    public GetEntityMessagesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = MessageResultFactory.getGetEntityMessagesResult();
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var messageTypeName = form.getMessageTypeName();
        var messageName = form.getMessageName();
        var entityRef = form.getEntityRef();
        var parameterCount = (componentVendorName == null && entityTypeName == null && messageTypeName == null && messageName == null ? 0 : 1) + (entityRef == null ? 0 : 1);
        
        if(parameterCount == 1) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var messageControl = Session.getModelController(MessageControl.class);
            var userVisit = getUserVisit();
            
            if(entityRef != null) {
                var entityInstance = entityInstanceControl.getEntityInstanceByEntityRef(entityRef);
                
                if(entityInstance != null) {
                    result.setEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, entityInstance, false, false, false, false));
                    result.setEntityMessages(messageControl.getEntityMessageTransfersByEntityInstance(userVisit, entityInstance));
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
                }
            } else {
                var componentVendor = componentControl.getComponentVendorByName(componentVendorName);
                
                result.setComponentVendor(componentControl.getComponentVendorTransfer(userVisit, componentVendor));
                
                if(componentVendor != null) {
                    var entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName);
                    
                    result.setEntityType(entityTypeControl.getEntityTypeTransfer(userVisit, entityType));
                    
                    if(entityType != null) {
                        var messageType = messageControl.getMessageTypeByName(entityType, messageTypeName);
                        
                        if(messageType != null) {
                            var message = messageControl.getMessageByName(messageType, messageName);
                            
                            result.setMessageType(messageControl.getMessageTypeTransfer(userVisit, messageType));
                            
                            if(message != null) {
                                result.setMessage(messageControl.getMessageTransfer(userVisit, message));
                                result.setEntityMessages(messageControl.getEntityMessageTransfersByMessage(userVisit, message));
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
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
