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

import com.echothree.control.user.forum.common.form.GetForumsForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.model.data.forum.server.factory.ForumFactory;
import com.echothree.model.data.forum.server.factory.ForumGroupForumFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetForumsCommand
        extends BasePaginatedMultipleEntitiesCommand<Forum, GetForumsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ForumGroupName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    ForumControl forumControl;

    /** Creates a new instance of GetForumsCommand */
    public GetForumsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    private ForumGroup forumGroup;

    @Override
    protected void handleForm() {
        var forumGroupName = form.getForumGroupName();

        if(forumGroupName != null) {
            forumGroup = forumControl.getForumGroupByName(forumGroupName);

            if(forumGroup == null) {
                addExecutionError(ExecutionErrors.UnknownForumGroupName.name(), forumGroupName);
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        return forumGroup == null ?
                forumControl.countForums() :
                forumControl.countForumGroupForumsByForumGroup(forumGroup);
    }

    @Override
    protected Collection<Forum> getEntities() {
        Collection<Forum> forums = null;

        if(!hasExecutionErrors()) {
            if(forumGroup == null) {
                forums = forumControl.getForums();
            } else {
                var forumGroupForums = forumControl.getForumGroupForumsByForumGroup(forumGroup);

                forums = new ArrayList<>(forumGroupForums.size());
                for(var forumGroupForum : forumGroupForums) {
                    forums.add(forumGroupForum.getForum());
                }
            }
        }

        return forums;
    }

    @Override
    protected BaseResult getResult(Collection<Forum> entities) {
        var result = ForumResultFactory.getGetForumsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(forumGroup == null) {
                if(session.hasLimit(ForumFactory.class)) {
                    result.setForumCount(getTotalEntities());
                }
            } else {
                if(session.hasLimit(ForumGroupForumFactory.class)) {
                    result.setForumCount(getTotalEntities());
                }

                result.setForumGroup(forumControl.getForumGroupTransfer(userVisit, forumGroup));
            }

            result.setForums(forumControl.getForumTransfers(userVisit, new ArrayList<>(entities)));
        }

        return result;
    }
    
}
