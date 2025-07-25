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

import com.echothree.control.user.forum.common.form.SetDefaultForumForumThreadForm;
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

public class SetDefaultForumForumThreadCommand
        extends BaseSimpleCommand<SetDefaultForumForumThreadForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ForumThreadName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetDefaultForumForumThreadCommand */
    public SetDefaultForumForumThreadCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumName = form.getForumName();
        var forum = forumControl.getForumByName(forumName);
        
        if(forum != null) {
            var forumThreadName = form.getForumThreadName();
            var forumThread = forumControl.getForumThreadByName(forumThreadName);
            
            if(forumThread != null) {
                var forumForumThreadValue = forumControl.getForumForumThreadValueForUpdate(forum, forumThread);
                
                if(forumForumThreadValue != null) {
                    forumForumThreadValue.setIsDefault(true);
                    forumControl.updateForumForumThreadFromValue(forumForumThreadValue, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownForumForumThread.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownForumThreadName.name(), forumThreadName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
        }
        
        return null;
    }
    
}
