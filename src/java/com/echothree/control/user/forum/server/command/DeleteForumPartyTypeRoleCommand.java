// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.forum.remote.form.DeleteForumPartyTypeRoleForm;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumPartyTypeRole;
import com.echothree.model.data.forum.server.entity.ForumRoleType;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteForumPartyTypeRoleCommand
        extends BaseSimpleCommand<DeleteForumPartyTypeRoleForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ForumRoleTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of DeleteForumPartyTypeRoleCommand */
    public DeleteForumPartyTypeRoleCommand(UserVisitPK userVisitPK, DeleteForumPartyTypeRoleForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        ForumControl forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        String forumName = form.getForumName();
        Forum forum = forumControl.getForumByName(forumName);
        
        if(forum != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String partyTypeName = form.getPartyTypeName();
            PartyType partyType = partyControl.getPartyTypeByName(partyTypeName);
            
            if(partyType != null) {
                String forumRoleTypeName = form.getForumRoleTypeName();
                ForumRoleType forumRoleType = forumControl.getForumRoleTypeByName(forumRoleTypeName);
                
                if(forumRoleType != null) {
                    ForumPartyTypeRole forumPartyTypeRole = forumControl.getForumPartyTypeRoleForUpdate(forum, partyType, forumRoleType);
                    
                    if(forumPartyTypeRole != null) {
                        forumControl.deleteForumPartyTypeRole(forumPartyTypeRole, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownForumPartyTypeRole.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownForumRoleTypeName.name(), forumRoleTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
        }
        
        return null;
    }
    
}
