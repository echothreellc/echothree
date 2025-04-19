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

import com.echothree.control.user.message.common.edit.MessageEditFactory;
import com.echothree.control.user.message.common.edit.MessageTypeEdit;
import com.echothree.control.user.message.common.form.EditMessageTypeForm;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.control.user.message.common.spec.MessageTypeSpec;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditMessageTypeCommand
        extends BaseEditCommand<MessageTypeSpec, MessageTypeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null));
        temp.add(new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(4);
        temp.add(new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        temp.add(new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditMessageTypeCommand */
    public EditMessageTypeCommand(UserVisitPK userVisitPK, EditMessageTypeForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var result = MessageResultFactory.getEditMessageTypeResult();
        var componentVendorName = spec.getComponentVendorName();
        var componentVendor = getComponentControl().getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            var entityTypeName = spec.getEntityTypeName();
            var entityType = getEntityTypeControl().getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                var messageControl = Session.getModelController(MessageControl.class);
                
                if(editMode.equals(EditMode.LOCK)) {
                    var messageTypeName = spec.getMessageTypeName();
                    var messageType = messageControl.getMessageTypeByName(entityType, messageTypeName);
                    
                    if(messageType != null) {
                        result.setMessageType(messageControl.getMessageTypeTransfer(getUserVisit(), messageType));
                        
                        if(lockEntity(messageType)) {
                            var messageTypeDescription = messageControl.getMessageTypeDescription(messageType, getPreferredLanguage());
                            var edit = MessageEditFactory.getMessageTypeEdit();
                            var messageTypeDetail = messageType.getLastDetail();
                            
                            result.setEdit(edit);
                            edit.setMessageTypeName(messageTypeDetail.getMessageTypeName());
                            edit.setSortOrder(messageTypeDetail.getSortOrder().toString());
                            
                            if(messageTypeDescription != null)
                                edit.setDescription(messageTypeDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(messageType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownMessageTypeName.name(), messageTypeName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var messageTypeName = spec.getMessageTypeName();
                    var messageType = messageControl.getMessageTypeByNameForUpdate(entityType, messageTypeName);
                    
                    if(messageType != null) {
                        messageTypeName = edit.getMessageTypeName();
                        var duplicateMessageType = messageControl.getMessageTypeByName(entityType, messageTypeName);
                        
                        if(duplicateMessageType == null || messageType.equals(duplicateMessageType)) {
                            if(lockEntityForUpdate(messageType)) {
                                try {
                                    var partyPK = getPartyPK();
                                    var messageTypeDetailValue = messageControl.getMessageTypeDetailValueForUpdate(messageType);
                                    var messageTypeDescription = messageControl.getMessageTypeDescriptionForUpdate(messageType, getPreferredLanguage());
                                    var description = edit.getDescription();
                                    
                                    messageTypeDetailValue.setMessageTypeName(edit.getMessageTypeName());
                                    messageTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    messageControl.updateMessageTypeFromValue(messageTypeDetailValue, partyPK);
                                    
                                    if(messageTypeDescription == null && description != null) {
                                        messageControl.createMessageTypeDescription(messageType, getPreferredLanguage(), description, partyPK);
                                    } else if(messageTypeDescription != null && description == null) {
                                        messageControl.deleteMessageTypeDescription(messageTypeDescription, partyPK);
                                    } else if(messageTypeDescription != null && description != null) {
                                        var messageTypeDescriptionValue = messageControl.getMessageTypeDescriptionValue(messageTypeDescription);
                                        
                                        messageTypeDescriptionValue.setDescription(description);
                                        messageControl.updateMessageTypeDescriptionFromValue(messageTypeDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(messageType);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateMessageTypeName.name(), messageTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownMessageTypeName.name(), messageTypeName);
                    }
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
