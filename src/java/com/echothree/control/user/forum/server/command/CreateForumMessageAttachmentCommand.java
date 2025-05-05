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

package com.echothree.control.user.forum.server.command;

import com.echothree.control.user.forum.common.form.CreateForumMessageAttachmentForm;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachment;
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

public class CreateForumMessageAttachmentCommand
        extends BaseSimpleCommand<CreateForumMessageAttachmentForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumMessageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ForumMessageAttachmentSequence", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("Clob", FieldType.STRING, false, 1L, null),
                new FieldDefinition("String", FieldType.STRING, false, 1L, 512L)
                ));
    }
    
    /** Creates a new instance of CreateForumMessageAttachmentCommand */
    public CreateForumMessageAttachmentCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumMessageName = form.getForumMessageName();
        var forumMessage = forumControl.getForumMessageByNameForUpdate(forumMessageName);

        if(forumMessage != null) {
            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
            var mimeTypeName = form.getMimeTypeName();
            var mimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);

            if(mimeType != null) {
                var entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
                var blob = form.getBlob();
                var clob = form.getClob();

                if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name()) && blob == null) {
                    addExecutionError(ExecutionErrors.MissingBlob.name());
                } else {
                    if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name()) && clob == null) {
                        addExecutionError(ExecutionErrors.MissingClob.name());
                    }
                }

                if(!hasExecutionErrors()) {
                    var partyPK = getPartyPK();
                    var strForumMessageAttachmentSequence = form.getForumMessageAttachmentSequence();
                    var forumMessageAttachmentSequence = strForumMessageAttachmentSequence == null ? null : Integer.valueOf(strForumMessageAttachmentSequence);
                    ForumMessageAttachment forumMessageAttachment;
                    var forumMessageStatus = forumControl.getOrCreateForumMessageStatusForUpdate(forumMessage);

                    if(forumMessageAttachmentSequence == null) {
                        forumMessageAttachmentSequence = forumMessageStatus.getForumMessageAttachmentSequence() + 1;
                        forumMessageStatus.setForumMessageAttachmentSequence(forumMessageAttachmentSequence);
                    } else {
                        forumMessageAttachment = forumControl.getForumMessageAttachmentBySequence(forumMessage, forumMessageAttachmentSequence);

                        if(forumMessageAttachment == null) {
                            // If the forumMessageAttachmentSequence is > the last one that was recorded in the OrderStatus, jump the
                            // one in OrderStatus forward - it should always record the greatest forumMessageAttachmentSequence used.
                            if(forumMessageAttachmentSequence > forumMessageStatus.getForumMessageAttachmentSequence()) {
                                forumMessageStatus.setForumMessageAttachmentSequence(forumMessageAttachmentSequence);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateForumMessageAttachmentSequence.name(), forumMessageName, forumMessageAttachmentSequence.toString());
                        }
                    }

                    forumMessageAttachment = forumControl.createForumMessageAttachment(forumMessage, forumMessageAttachmentSequence, mimeType, partyPK);

                    if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                        forumControl.createForumMessageBlobAttachment(forumMessageAttachment, blob, partyPK);
                    } else {
                        if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                            forumControl.createForumMessageClobAttachment(forumMessageAttachment, clob, partyPK);
                        }
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumMessageName.name(), forumMessageName);
        }
        
        return null;
    }
    
}
