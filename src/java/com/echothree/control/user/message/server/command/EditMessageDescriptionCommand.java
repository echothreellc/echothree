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

import com.echothree.control.user.message.common.edit.MessageDescriptionEdit;
import com.echothree.control.user.message.common.edit.MessageEditFactory;
import com.echothree.control.user.message.common.form.EditMessageDescriptionForm;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.control.user.message.common.spec.MessageDescriptionSpec;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.party.server.control.PartyControl;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditMessageDescriptionCommand
        extends BaseEditCommand<MessageDescriptionSpec, MessageDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(4);
        temp.add(new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null));
        temp.add(new FieldDefinition("MessageName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditMessageDescriptionCommand */
    public EditMessageDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var result = MessageResultFactory.getEditMessageDescriptionResult();
        var componentVendorName = spec.getComponentVendorName();
        var componentVendor = componentControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            var entityTypeName = spec.getEntityTypeName();
            var entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                var messageControl = Session.getModelController(MessageControl.class);
                var messageTypeName = spec.getMessageTypeName();
                var messageType = messageControl.getMessageTypeByName(entityType, messageTypeName);
                
                if(messageType != null) {
                    var messageName = spec.getMessageName();
                    var message = messageControl.getMessageByName(messageType, messageName);
                    
                    if(message != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        var languageIsoName = spec.getLanguageIsoName();
                        var language = partyControl.getLanguageByIsoName(languageIsoName);
                        
                        if(language != null) {
                            if(editMode.equals(EditMode.LOCK)) {
                                var messageDescription = messageControl.getMessageDescription(message, language);
                                
                                if(messageDescription != null) {
                                    result.setMessageDescription(messageControl.getMessageDescriptionTransfer(getUserVisit(), messageDescription));
                                    
                                    if(lockEntity(message)) {
                                        var edit = MessageEditFactory.getMessageDescriptionEdit();
                                        
                                        result.setEdit(edit);
                                        edit.setDescription(messageDescription.getDescription());
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                    }
                                    
                                    result.setEntityLock(getEntityLockTransfer(message));
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownMessageDescription.name());
                                }
                            } else if(editMode.equals(EditMode.UPDATE)) {
                                var messageDescriptionValue = messageControl.getMessageDescriptionValueForUpdate(message, language);
                                
                                if(messageDescriptionValue != null) {
                                    if(lockEntityForUpdate(message)) {
                                        try {
                                            var description = edit.getDescription();
                                            
                                            messageDescriptionValue.setDescription(description);
                                            
                                            messageControl.updateMessageDescriptionFromValue(messageDescriptionValue, getPartyPK());
                                        } finally {
                                            unlockEntity(message);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownMessageDescription.name());
                                }
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
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
        
        return result;
    }
    
}
