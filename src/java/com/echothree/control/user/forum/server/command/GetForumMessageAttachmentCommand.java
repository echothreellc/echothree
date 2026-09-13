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

package com.echothree.control.user.forum.server.command;

import com.echothree.control.user.forum.common.form.GetForumMessageAttachmentForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.forum.server.logic.ForumMessageLogic;
import com.echothree.model.control.forum.server.logic.ForumRoleTypeLogic;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachment;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetForumMessageAttachmentCommand
        extends BaseSingleEntityCommand<ForumMessageAttachment, GetForumMessageAttachmentForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ForumMessageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ForumMessageAttachmentSequence", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("Referrer", FieldType.URL, false, null, null)
        );
    }

    @Inject
    ForumControl forumControl;

    @Inject
    ContentLogic contentLogic;

    @Inject
    ForumMessageLogic forumMessageLogic;

    @Inject
    ForumRoleTypeLogic forumRoleTypeLogic;

    /** Creates a new instance of GetForumMessageAttachmentCommand */
    public GetForumMessageAttachmentCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected ForumMessageAttachment getEntity() {
        ForumMessageAttachment forumMessageAttachment = null;
        contentLogic.checkReferrer(this, form.getReferrer());

        if(!hasExecutionErrors()) {
            var forumMessageName = form.getForumMessageName();
            var forumMessage = forumMessageLogic.getForumMessageByNameForUpdate(this, forumMessageName);

            if(!hasExecutionErrors()) {
                if(forumRoleTypeLogic.isForumRoleTypePermitted(this, forumMessage, getParty(), ForumConstants.ForumRoleType_READER)) {
                    var forumMessageAttachmentSequence = Integer.valueOf(form.getForumMessageAttachmentSequence());
                    forumMessageAttachment = forumControl.getForumMessageAttachmentBySequence(forumMessage, forumMessageAttachmentSequence);

                    if(forumMessageAttachment == null) {
                        addExecutionError(ExecutionErrors.UnknownForumMessageAttachment.name(), forumMessageName, forumMessageAttachmentSequence.toString());
                    }
                } else {
                    addExecutionError(ExecutionErrors.MissingRequiredForumRoleType.name(), ForumConstants.ForumRoleType_READER);
                }
            }
        }

        return hasExecutionErrors() ? null : forumMessageAttachment;
    }

    @Override
    protected BaseResult getResult(ForumMessageAttachment forumMessageAttachment) {
        var result = ForumResultFactory.getGetForumMessageAttachmentResult();

        if(forumMessageAttachment != null) {
            result.setForumMessageAttachment(forumControl.getForumMessageAttachmentTransfer(getUserVisit(), forumMessageAttachment));
        }

        return result;
    }
    
}
