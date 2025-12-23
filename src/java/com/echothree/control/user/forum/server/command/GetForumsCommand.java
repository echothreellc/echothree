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
import com.echothree.model.data.forum.server.factory.ForumFactory;
import com.echothree.model.data.forum.server.factory.ForumGroupForumFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetForumsCommand
        extends BaseSimpleCommand<GetForumsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ForumGroupName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetForumsCommand */
    public GetForumsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var forumControl = Session.getModelController(ForumControl.class);
        var result = ForumResultFactory.getGetForumsResult();
        var forumGroupName = form.getForumGroupName();
        var forumGroup = forumControl.getForumGroupByName(forumGroupName);
        
        if(forumGroupName == null || forumGroup != null) {
            var userVisit = getUserVisit();
            
            if(forumGroup == null) {
                if(session.hasLimit(ForumFactory.class)) {
                    result.setForumCount(forumControl.countForums());
                }

                result.setForums(forumControl.getForumTransfers(userVisit));
            } else {
                var forumGroupForums = forumControl.getForumGroupForumsByForumGroup(forumGroup);
                List<Forum> forums = new ArrayList<>(forumGroupForums.size());
                
                forumGroupForums.forEach((forumGroupForum) -> {
                    forums.add(forumGroupForum.getForum());
                });
                
                if(session.hasLimit(ForumGroupForumFactory.class)) {
                    result.setForumCount(forumControl.countForumGroupForumsByForumGroup(forumGroup));
                }

                result.setForumGroup(forumControl.getForumGroupTransfer(userVisit, forumGroup));
                result.setForums(forumControl.getForumTransfers(userVisit, forums));
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumGroupName.name(), forumGroupName);
        }
        
        return result;
    }
    
}
