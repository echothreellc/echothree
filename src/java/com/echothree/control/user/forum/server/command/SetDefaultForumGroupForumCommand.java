// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.forum.common.form.SetDefaultForumGroupForumForm;
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

public class SetDefaultForumGroupForumCommand
        extends BaseSimpleCommand<SetDefaultForumGroupForumForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetDefaultForumGroupForumCommand */
    public SetDefaultForumGroupForumCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumGroupName = form.getForumGroupName();
        var forumGroup = forumControl.getForumGroupByName(forumGroupName);
        
        if(forumGroup != null) {
            var forumName = form.getForumName();
            var forum = forumControl.getForumByName(forumName);
            
            if(forum != null) {
                var forumGroupForumValue = forumControl.getForumGroupForumValueForUpdate(forumGroup,
                        forum);
                
                if(forumGroupForumValue != null) {
                    forumGroupForumValue.setIsDefault(true);
                    forumControl.updateForumGroupForumFromValue(forumGroupForumValue, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownForumGroupForum.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumGroupName.name(), forumGroupName);
        }
        
        return null;
    }
    
}
