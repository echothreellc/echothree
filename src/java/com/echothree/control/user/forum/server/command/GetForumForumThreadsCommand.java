// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.forum.common.result.GetForumForumThreadsResult;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumThread;
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

public class GetForumForumThreadsCommand
        extends BaseSimpleCommand<GetForumForumThreadsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ForumThreadName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetForumForumThreadsCommand */
    public GetForumForumThreadsCommand(UserVisitPK userVisitPK, GetForumForumThreadsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        GetForumForumThreadsResult result = ForumResultFactory.getGetForumForumThreadsResult();
        String forumName = form.getForumName();
        String forumThreadName = form.getForumThreadName();
        int parameterCount = (forumName != null? 1: 0) + (forumThreadName != null? 1: 0);
        
        if(parameterCount == 1) {
            if(forumName != null) {
                Forum forum = forumControl.getForumByName(forumName);
                
                if(forum != null) {
                    result.setForum(forumControl.getForumTransfer(getUserVisit(), forum));
                    result.setForumForumThreads(forumControl.getForumForumThreadTransfersByForum(getUserVisit(), forum));
                } else {
                    addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
                }
            } else if(forumThreadName != null) {
                ForumThread forumThread = forumControl.getForumThreadByName(forumThreadName);
                
                if(forumThread != null) {
                    result.setForumThread(forumControl.getForumThreadTransfer(getUserVisit(), forumThread));
                    result.setForumForumThreads(forumControl.getForumForumThreadTransfersByForumThread(getUserVisit(), forumThread));
                } else {
                    addExecutionError(ExecutionErrors.UnknownForumThreadName.name(), forumThreadName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
