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

import com.echothree.control.user.forum.common.form.CreateForumTypeMessageTypeForm;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumMessageType;
import com.echothree.model.data.forum.server.entity.ForumType;
import com.echothree.model.data.forum.server.entity.ForumTypeMessageType;
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

public class CreateForumTypeMessageTypeCommand
        extends BaseSimpleCommand<CreateForumTypeMessageTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ForumTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ForumMessageTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        ));
    }
    
    /** Creates a new instance of CreateForumTypeMessageTypeCommand */
    public CreateForumTypeMessageTypeCommand(UserVisitPK userVisitPK, CreateForumTypeMessageTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        String forumTypeName = form.getForumTypeName();
        ForumType forumType = forumControl.getForumTypeByName(forumTypeName);
        
        if(forumType != null) {
            String forumMessageTypeName = form.getForumMessageTypeName();
            ForumMessageType forumMessageType = forumControl.getForumMessageTypeByName(forumMessageTypeName);
            
            if(forumMessageType != null) {
                ForumTypeMessageType forumTypeMessageType = forumControl.getForumTypeMessageType(forumType, forumMessageType);
                
                if(forumTypeMessageType == null) {
                    Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                    Integer sortOrder = Integer.valueOf(form.getSortOrder());
                    
                    forumControl.createForumTypeMessageType(forumType, forumMessageType, isDefault, sortOrder);
                } else {
                    addExecutionError(ExecutionErrors.DuplicateForumTypeMessageType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownForumMessageTypeName.name(), forumMessageTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumTypeName.name(), forumTypeName);
        }
        
        return null;
    }
    
}
