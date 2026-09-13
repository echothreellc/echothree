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

import com.echothree.control.user.forum.common.form.GetForumForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.forum.server.logic.ForumLogic;
import com.echothree.model.control.forum.server.logic.ForumRoleTypeLogic;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetForumCommand
        extends BaseSingleEntityCommand<Forum, GetForumForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    @Inject
    ForumControl forumControl;

    @Inject
    ForumLogic forumLogic;

    @Inject
    ForumRoleTypeLogic forumRoleTypeLogic;

    /** Creates a new instance of GetForumCommand */
    public GetForumCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Forum getEntity() {
        var forum = forumLogic.getForumByUniversalSpec(this, form);

        if(!hasExecutionErrors() && forum != null) {
            // If the UUID for the Forum is specified, then bypass the ForumRoleType check.
            if(form.getUuid() != null || forumRoleTypeLogic.isForumRoleTypePermitted(this, forum, getParty(), ForumConstants.ForumRoleType_READER)) {
                if(!hasExecutionErrors()) {
                    sendEvent(forum.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
                }
            } else {
                addExecutionError(ExecutionErrors.MissingRequiredForumRoleType.name(), ForumConstants.ForumRoleType_READER);
            }
        }

        return hasExecutionErrors() ? null : forum;
    }

    @Override
    protected BaseResult getResult(Forum forum) {
        var result = ForumResultFactory.getGetForumResult();

        if(forum != null) {
            result.setForum(forumControl.getForumTransfer(getUserVisit(), forum));
        }

        return result;
    }
    
}
