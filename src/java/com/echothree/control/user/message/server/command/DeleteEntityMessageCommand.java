// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.message.common.form.DeleteEntityMessageForm;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class DeleteEntityMessageCommand
        extends BaseSimpleCommand<DeleteEntityMessageForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
            new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
            new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("MessageName", FieldType.TAG, true, null, null)
        ));
    }
    
    /** Creates a new instance of DeleteEntityMessageCommand */
    public DeleteEntityMessageCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityRef = form.getEntityRef();
        var entityInstance = entityInstanceControl.getEntityInstanceByEntityRef(entityRef);
        
        if(entityInstance != null) {
            var componentVendorName = form.getComponentVendorName();
            var componentVendor = getComponentControl().getComponentVendorByName(componentVendorName);
            
            if(componentVendor != null) {
                var entityTypeName = form.getEntityTypeName();
                var entityType = getEntityTypeControl().getEntityTypeByName(componentVendor, entityTypeName);
                
                if(entityType != null) {
                    var messageControl = Session.getModelController(MessageControl.class);
                    var messageTypeName = form.getMessageTypeName();
                    var messageType = messageControl.getMessageTypeByName(entityType, messageTypeName);
                    
                    if(messageType != null) {
                        var messageName = form.getMessageName();
                        var message = messageControl.getMessageByName(messageType, messageName);
                        
                        if(message != null) {
                            var entityMessage = messageControl.getEntityMessageForUpdate(entityInstance, message);
                            
                            if(entityMessage != null) {
                                messageControl.deleteEntityMessage(entityMessage, getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.UnknownEntityMessage.name());
                            }
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
        } else {
            addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
        }
        
        return null;
    }
    
}
