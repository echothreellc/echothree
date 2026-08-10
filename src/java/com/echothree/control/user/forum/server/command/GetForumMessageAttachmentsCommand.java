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

import com.echothree.control.user.forum.common.form.GetForumMessageAttachmentsForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.forum.server.logic.ForumMessageLogic;
import com.echothree.model.control.forum.server.logic.ForumRoleTypeLogic;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachment;
import com.echothree.model.data.forum.server.factory.ForumMessageAttachmentFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetForumMessageAttachmentsCommand
        extends BasePaginatedMultipleEntitiesCommand<ForumMessageAttachment, GetForumMessageAttachmentsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ForumMessageName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    ForumControl forumControl;

    @Inject
    ForumMessageLogic forumMessageLogic;

    @Inject
    ForumRoleTypeLogic forumRoleTypeLogic;

    /** Creates a new instance of GetForumMessageAttachmentsCommand */
    public GetForumMessageAttachmentsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    ForumMessage forumMessage;

    @Override
    protected void handleForm() {
        var forumMessageName = form.getForumMessageName();

        forumMessage = forumMessageLogic.getForumMessageByName(this, forumMessageName);

        if(!hasExecutionErrors()) {
            if(!forumRoleTypeLogic.isForumRoleTypePermitted(this, forumMessage, getParty(), ForumConstants.ForumRoleType_READER)) {
                addExecutionError(ExecutionErrors.MissingRequiredForumRoleType.name(), ForumConstants.ForumRoleType_READER);
                forumMessage = null;
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        return forumMessage == null ? null : forumControl.countForumMessageAttachmentsByForumMessage(forumMessage);
    }

    @Override
    protected Collection<ForumMessageAttachment> getEntities() {
        return forumMessage == null ? null : forumControl.getForumMessageAttachmentsByForumMessage(forumMessage);
    }

    @Override
    protected BaseResult getResult(Collection<ForumMessageAttachment> entities) {
        var result = ForumResultFactory.getGetForumMessageAttachmentsResult();

        if(forumMessage != null) {
            var userVisit = getUserVisit();

            result.setForumMessage(forumControl.getForumMessageTransfer(userVisit, forumMessage));

            if(session.hasLimit(ForumMessageAttachmentFactory.class)) {
                result.setForumMessageAttachmentCount(getTotalEntities());
            }

            result.setForumMessageAttachments(forumControl.getForumMessageAttachmentTransfers(userVisit, entities));
        }

        return result;
    }
    
}
