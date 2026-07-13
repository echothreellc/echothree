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

import com.echothree.control.user.forum.common.form.GetForumPartyRolesForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.forum.server.logic.ForumLogic;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumPartyRole;
import com.echothree.model.data.forum.server.factory.ForumPartyRoleFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetForumPartyRolesCommand
        extends BasePaginatedMultipleEntitiesCommand<ForumPartyRole, GetForumPartyRolesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    ForumControl forumControl;
    
    @Inject
    ForumLogic forumLogic;
    
    /** Creates a new instance of GetForumPartyRolesCommand */
    public GetForumPartyRolesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    private Forum forum;
    
    @Override
    protected void handleForm() {
        var forumName = form.getForumName();
        
        forum = forumLogic.getForumByName(this, forumName);
    }
    
    @Override
    protected Long getTotalEntities() {
        return forum == null ? null : forumControl.countForumPartyRoleByForum(forum);
    }
    
    @Override
    protected Collection<ForumPartyRole> getEntities() {
        return forum == null ? null : forumControl.getForumPartyRolesByForum(forum);
    }
    
    @Override
    protected BaseResult getResult(Collection<ForumPartyRole> entities) {
        var result = ForumResultFactory.getGetForumPartyRolesResult();
        
        if(forum != null) {
            var userVisit = getUserVisit();
            
            result.setForum(forumControl.getForumTransfer(userVisit, forum));

            if(session.hasLimit(ForumPartyRoleFactory.class)) {
                result.setForumPartyRoleCount(getTotalEntities());
            }

            result.setForumPartyRoles(forumControl.getForumPartyRoleTransfers(userVisit, entities));
        }
        
        return result;
    }
    
}
