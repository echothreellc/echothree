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

import com.echothree.control.user.forum.common.form.GetForumMessageAttachmentsForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.forum.server.logic.ForumLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetForumMessageAttachmentsCommand
        extends BaseSimpleCommand<GetForumMessageAttachmentsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumMessageName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of GetForumMessageAttachmentsCommand */
    public GetForumMessageAttachmentsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var forumControl = Session.getModelController(ForumControl.class);
        var result = ForumResultFactory.getGetForumMessageAttachmentsResult();
        var forumMessageName = form.getForumMessageName();
        var forumMessage = forumControl.getForumMessageByNameForUpdate(forumMessageName);

        if(forumMessage != null) {
            if(ForumLogic.getInstance().isForumRoleTypePermitted(this, forumMessage, getParty(), ForumConstants.ForumRoleType_READER)) {
                var userVisit = getUserVisit();

                result.setForumMessage(forumControl.getForumMessageTransfer(userVisit, forumMessage));
                result.setForumMessageAttachments(forumControl.getForumMessageAttachmentTransfersByForumMessage(userVisit, forumMessage));
            } else {
                addExecutionError(ExecutionErrors.MissingRequiredForumRoleType.name(), ForumConstants.ForumRoleType_READER);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumMessageName.name(), forumMessageName);
        }
        
        return result;
    }
    
}
