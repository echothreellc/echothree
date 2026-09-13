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

import com.echothree.control.user.forum.common.form.GetForumGroupForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.forum.server.logic.ForumGroupLogic;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetForumGroupCommand
        extends BaseSingleEntityCommand<ForumGroup, GetForumGroupForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ForumGroupName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    ForumControl forumControl;

    @Inject
    ForumGroupLogic forumGroupLogic;

    /** Creates a new instance of GetForumGroupCommand */
    public GetForumGroupCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected ForumGroup getEntity() {
        var forumGroup = forumGroupLogic.getForumGroupByName(this, form.getForumGroupName());
        
        if(!hasExecutionErrors()) {
            sendEvent(forumGroup.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return forumGroup;
    }

    @Override
    protected BaseResult getResult(ForumGroup forumGroup) {
        var result = ForumResultFactory.getGetForumGroupResult();

        if(forumGroup != null) {
            result.setForumGroup(forumControl.getForumGroupTransfer(getUserVisit(), forumGroup));
        }

        return result;
    }
    
}
