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

import com.echothree.control.user.forum.common.form.GetForumThreadForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.forum.server.logic.ForumRoleTypeLogic;
import com.echothree.model.control.forum.server.logic.ForumThreadLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.forum.server.entity.ForumThread;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetForumThreadCommand
        extends BaseSingleEntityCommand<ForumThread, GetForumThreadForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ForumThreadName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    @Inject
    ForumControl forumControl;

    @Inject
    ForumRoleTypeLogic forumRoleTypeLogic;

    @Inject
    ForumThreadLogic forumThreadLogic;

    /** Creates a new instance of GetForumThreadCommand */
    public GetForumThreadCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected ForumThread getEntity() {
        var forumThread = forumThreadLogic.getForumThreadByUniversalSpec(this, form);

        if(!hasExecutionErrors() && forumThread != null) {
            // UUID lookup bypasses the reader-role check, but never the publication-time check.
            if(forumThread.getLastDetail().getPostedTime() <= session.getStartTime()
                    || (getParty() == null ? false : getPartyTypeName().equals(PartyTypes.EMPLOYEE.name()))) {
                if(form.getUuid() != null || forumRoleTypeLogic.isForumRoleTypePermitted(this, forumThread, getParty(), ForumConstants.ForumRoleType_READER)) {
                    if(!hasExecutionErrors()) {
                        sendEvent(forumThread.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
                    }
                } else {
                    addExecutionError(ExecutionErrors.MissingRequiredForumRoleType.name(), ForumConstants.ForumRoleType_READER);
                }
            } else {
                addExecutionError(ExecutionErrors.UnpublishedForumThread.name(), forumThread.getLastDetail().getForumThreadName());
            }
        }

        return hasExecutionErrors() ? null : forumThread;
    }

    @Override
    protected BaseResult getResult(ForumThread forumThread) {
        var result = ForumResultFactory.getGetForumThreadResult();

        if(forumThread != null) {
            result.setForumThread(forumControl.getForumThreadTransfer(getUserVisit(), forumThread));
        }

        return result;
    }
    
}
