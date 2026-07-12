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

import com.echothree.control.user.forum.common.form.GetForumForumThreadsForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumForumThread;
import com.echothree.model.data.forum.server.entity.ForumThread;
import com.echothree.model.data.forum.server.factory.ForumForumThreadFactory;
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
public class GetForumForumThreadsCommand
        extends BasePaginatedMultipleEntitiesCommand<ForumForumThread, GetForumForumThreadsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ForumThreadName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    protected ForumControl forumControl;
    
    private Forum forum;
    private ForumThread forumThread;
    
    /** Creates a new instance of GetForumForumThreadsCommand */
    public GetForumForumThreadsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected void handleForm() {
        var forumName = form.getForumName();
        var forumThreadName = form.getForumThreadName();
        var parameterCount = (forumName != null? 1: 0) + (forumThreadName != null? 1: 0);
        
        if(parameterCount == 1) {
            if(forumName != null) {
                forum = forumControl.getForumByName(forumName);
                
                if(forum == null) {
                    addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
                }
            } else {
                forumThread = forumControl.getForumThreadByName(forumThreadName);
                
                if(forumThread == null) {
                    addExecutionError(ExecutionErrors.UnknownForumThreadName.name(), forumThreadName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(forum != null) {
            total = forumControl.countForumForumThreadsByForum(forum);
        } else if(forumThread != null) {
            total = forumControl.countForumForumThreadsByForumThread(forumThread);
        }

        return total;
    }

    @Override
    protected Collection<ForumForumThread> getEntities() {
        Collection<ForumForumThread> forumForumThreads = null;

        if(forum != null) {
            forumForumThreads = forumControl.getForumForumThreadsByForum(forum);
        } else if(forumThread != null) {
            forumForumThreads = forumControl.getForumForumThreadsByForumThread(forumThread);
        }

        return forumForumThreads;
    }

    @Override
    protected BaseResult getResult(Collection<ForumForumThread> entities) {
        var result = ForumResultFactory.getGetForumForumThreadsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(forum != null) {
                result.setForum(forumControl.getForumTransfer(userVisit, forum));
            } else if(forumThread != null) {
                result.setForumThread(forumControl.getForumThreadTransfer(userVisit, forumThread));
            }

            if(session.hasLimit(ForumForumThreadFactory.class)) {
                result.setForumForumThreadCount(getTotalEntities());
            }

            result.setForumForumThreads(forumControl.getForumForumThreadTransfers(userVisit, entities));
        }

        return result;
    }
    
}
