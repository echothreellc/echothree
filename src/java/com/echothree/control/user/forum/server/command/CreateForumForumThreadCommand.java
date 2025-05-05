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

import com.echothree.control.user.forum.common.form.CreateForumForumThreadForm;
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

public class CreateForumForumThreadCommand
        extends BaseSimpleCommand<CreateForumForumThreadForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ForumThreadName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateForumForumThreadCommand */
    public CreateForumForumThreadCommand() {
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
                var defaultForumForumThread = forumControl.getDefaultForumForumThread(forumThread);
                var forumType = forum.getLastDetail().getForumType();

                if(defaultForumForumThread.getForum().getLastDetail().getForumType().equals(forumType)) {
                    var forumForumThread = forumControl.getForumForumThread(forum, forumThread);

                    if(forumForumThread == null) {
                        var isDefault = Boolean.valueOf(form.getIsDefault());
                        var sortOrder = Integer.valueOf(form.getSortOrder());

                        forumControl.createForumForumThread(forum, forumThread, isDefault, sortOrder, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateForumForumThread.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidForumType.name(), forumType.getForumTypeName());
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
