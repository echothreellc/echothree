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
public class GetForumGroupForumsCommand
        extends BaseSimpleCommand<GetForumGroupForumsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumGroupName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetForumGroupForumsCommand */
    public GetForumGroupForumsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var forumControl = Session.getModelController(ForumControl.class);
        var result = ForumResultFactory.getGetForumGroupForumsResult();
        var forumGroupName = form.getForumGroupName();
        var forumName = form.getForumName();
        var parameterCount = (forumGroupName != null? 1: 0) + (forumName != null? 1: 0);
        
        if(parameterCount == 1) {
            if(forumGroupName != null) {
                var forumGroup = forumControl.getForumGroupByName(forumGroupName);
                
                if(forumGroup != null) {
                    result.setForumGroup(forumControl.getForumGroupTransfer(getUserVisit(), forumGroup));
                    result.setForumGroupForums(forumControl.getForumGroupForumTransfersByForumGroup(getUserVisit(), forumGroup));
                } else {
                    addExecutionError(ExecutionErrors.UnknownForumGroupName.name(), forumGroupName);
                }
            } else if(forumName != null) {
                var forum = forumControl.getForumByName(forumName);
                
                if(forum != null) {
                    result.setForum(forumControl.getForumTransfer(getUserVisit(), forum));
                    result.setForumGroupForums(forumControl.getForumGroupForumTransfersByForum(getUserVisit(), forum));
                } else {
                    addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
