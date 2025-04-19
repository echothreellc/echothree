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

import com.echothree.control.user.message.common.form.CreateMessageForm;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.message.server.entity.Message;
import com.echothree.model.data.message.server.entity.MessageType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateMessageCommand
        extends BaseSimpleCommand<CreateMessageForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
            new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("MessageName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, false, null, null),
            new FieldDefinition("ClobMessage", FieldType.STRING, false, 1L, null),
            new FieldDefinition("StringMessage", FieldType.STRING, false, 1L, 512L),
            // BlobMessage is not validated
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of CreateMessageCommand */
    public CreateMessageCommand(UserVisitPK userVisitPK, CreateMessageForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    protected Message createMessage(MessageControl messageControl, MessageType messageType, String messageName,
            Boolean includeByDefault, Boolean isDefault, Integer sortOrder, MimeType mimeType, ByteArray blobMessage,
            String clobMessage, String stringMessage, BasePK createdBy) {
        var message = messageControl.createMessage(messageType, messageName, includeByDefault, isDefault, sortOrder,
                createdBy);
        
        if(blobMessage != null) {
            messageControl.createMessageBlob(message, getPreferredLanguage(), mimeType, blobMessage, createdBy);
        } else if(clobMessage != null) {
            messageControl.createMessageClob(message, getPreferredLanguage(), mimeType, clobMessage, createdBy);
        } else if(stringMessage != null) {
            messageControl.createMessageString(message, getPreferredLanguage(), stringMessage, createdBy);
        }
        
        return message;
    }
    
    @Override
    protected BaseResult execute() {
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
                    
                    if(message == null) {
                        var mimeTypeName = form.getMimeTypeName();
                        var includeByDefault = Boolean.valueOf(form.getIncludeByDefault());
                        var isDefault = Boolean.valueOf(form.getIsDefault());
                        var sortOrder = Integer.valueOf(form.getSortOrder());
                        var description = form.getDescription();
                        BasePK createdBy = getPartyPK();
                        
                        if(mimeTypeName == null) {
                            var messageString = form.getStringMessage();
                            
                            if(messageString != null) {
                                message = createMessage(messageControl, messageType, messageName, includeByDefault, isDefault,
                                        sortOrder, null, null, null, messageString, createdBy);
                            } else {
                                addExecutionError(ExecutionErrors.MissingStringMessage.name());
                            }
                        } else {
                            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
                            var mimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);
                            
                            if(mimeType != null) {
                                var entityAttributeType = mimeType.getLastDetail().getEntityAttributeType();
                                var entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();
                                
                                if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                                    var blobMessage = form.getBlobMessage();
                                    
                                    if(blobMessage != null) {
                                        message = createMessage(messageControl, messageType, messageName, includeByDefault,
                                                isDefault, sortOrder, mimeType, blobMessage, null, null, createdBy);
                                    } else {
                                        addExecutionError(ExecutionErrors.MissingBlobMessage.name());
                                    }
                                } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                                    var clobMessage = form.getClobMessage();
                                    
                                    if(clobMessage != null) {
                                        message = createMessage(messageControl, messageType, messageName, includeByDefault,
                                                isDefault, sortOrder, mimeType, null, clobMessage, null, createdBy);
                                    } else {
                                        addExecutionError(ExecutionErrors.MissingClobMessage.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownEntityAttributeTypeName.name(), entityAttributeTypeName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
                            }
                        }
                        
                        if(description != null) {
                            messageControl.createMessageDescription(message, getPreferredLanguage(), description, createdBy);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateMessageName.name(), messageName);
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
        
        return null;
    }
    
}
