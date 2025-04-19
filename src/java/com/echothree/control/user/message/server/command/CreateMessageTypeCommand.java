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

import com.echothree.control.user.message.common.form.CreateMessageTypeForm;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.message.server.entity.MessageType;
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

public class CreateMessageTypeCommand
        extends BaseSimpleCommand<CreateMessageTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
            new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("MimeTypeUsageTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of CreateMessageTypeCommand */
    public CreateMessageTypeCommand(UserVisitPK userVisitPK, CreateMessageTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = MessageResultFactory.getCreateMessageTypeResult();
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var entityType = EntityTypeLogic.getInstance().getEntityTypeByName(this, componentVendorName, entityTypeName);
        MessageType messageType = null;

        if(!hasExecutionErrors()) {
            var entityTypeDetail = entityType.getLastDetail();

            if(entityTypeDetail.getIsExtensible()) {
                var messageControl = Session.getModelController(MessageControl.class);
                var messageTypeName = form.getMessageTypeName();

                messageType = messageControl.getMessageTypeByName(entityType, messageTypeName);
                
                if(messageType == null) {
                    var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
                    var mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();
                    var mimeTypeUsageType = mimeTypeUsageTypeName == null? null: mimeTypeControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);
                    
                    if(mimeTypeUsageTypeName == null || mimeTypeUsageType != null) {
                        var partyPK = getPartyPK();
                        var sortOrder = Integer.valueOf(form.getSortOrder());
                        var description = form.getDescription();
                        
                        messageType = messageControl.createMessageType(entityType, messageTypeName, mimeTypeUsageType, sortOrder,
                                partyPK);
                        
                        if(description != null) {
                            messageControl.createMessageTypeDescription(messageType, getPreferredLanguage(), description,
                                    partyPK);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownMimeTypeUsageTypeName.name(), mimeTypeUsageTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateMessageTypeName.name(), messageTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.EntityTypeIsNotExtensible.name(),
                        entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityTypeDetail.getEntityTypeName());
            }
        }

        if(messageType != null) {
            var basePK = messageType.getPrimaryKey();
            var messageTypeDetail = messageType.getLastDetail();
            var entityTypeDetail = messageTypeDetail.getEntityType().getLastDetail();

            result.setComponentVendorName(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName());
            result.setEntityTypeName(entityTypeDetail.getEntityTypeName());
            result.setMessageTypeName(messageTypeDetail.getMessageTypeName());
            result.setEntityRef(basePK.getEntityRef());
        }

        return result;
    }
    
}
