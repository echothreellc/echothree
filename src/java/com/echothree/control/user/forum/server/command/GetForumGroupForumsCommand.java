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

import com.echothree.control.user.forum.common.form.GetForumGroupForumsForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.forum.server.logic.ForumGroupLogic;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.model.data.forum.server.entity.ForumGroupForum;
import com.echothree.model.data.forum.server.factory.ForumGroupForumFactory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetForumGroupForumsCommand
        extends BasePaginatedMultipleEntitiesCommand<ForumGroupForum, GetForumGroupForumsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ForumGroupName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    ForumControl forumControl;
    @Inject
    ForumGroupLogic forumGroupLogic;
    
    /** Creates a new instance of GetForumGroupForumsCommand */
    public GetForumGroupForumsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    private ForumGroup forumGroup;
    private Forum forum;

    @Override
    protected void handleForm() {
        var forumGroupName = form.getForumGroupName();
        var forumName = form.getForumName();
        var parameterCount = (forumGroupName != null ? 1 : 0) + (forumName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(forumGroupName != null) {
                forumGroup = forumGroupLogic.getForumGroupByName(this, forumGroupName);
            } else if(forumName != null) {
                forum = forumControl.getForumByName(forumName);

                if(forum == null) {
                    addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long totalEntities = null;

        if(!hasExecutionErrors()) {
            if(forumGroup != null) {
                totalEntities = forumControl.countForumGroupForumsByForumGroup(forumGroup);
            } else if(forum != null) {
                totalEntities = forumControl.countForumGroupForumsByForum(forum);
            }
        }

        return totalEntities;
    }

    @Override
    protected Collection<ForumGroupForum> getEntities() {
        Collection<ForumGroupForum> forumGroupForums = null;

        if(!hasExecutionErrors()) {
            if(forumGroup != null) {
                forumGroupForums = forumControl.getForumGroupForumsByForumGroup(forumGroup);
            } else if(forum != null) {
                forumGroupForums = forumControl.getForumGroupForumsByForum(forum);
            }
        }

        return forumGroupForums;
    }

    @Override
    protected BaseResult getResult(Collection<ForumGroupForum> entities) {
        var result = ForumResultFactory.getGetForumGroupForumsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(forumGroup != null) {
                result.setForumGroup(forumControl.getForumGroupTransfer(userVisit, forumGroup));
            } else if(forum != null) {
                result.setForum(forumControl.getForumTransfer(userVisit, forum));
            }

            if(session.hasLimit(ForumGroupForumFactory.class)) {
                result.setForumGroupForumCount(getTotalEntities());
            }

            result.setForumGroupForums(forumControl.getForumGroupForumTransfers(userVisit, entities));
        }

        return result;
    }
    
}
