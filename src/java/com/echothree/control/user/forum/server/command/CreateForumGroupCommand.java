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

import com.echothree.control.user.forum.common.form.CreateForumGroupForm;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.server.control.IconControl;
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

public class CreateForumGroupCommand
        extends BaseSimpleCommand<CreateForumGroupForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IconName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateForumGroupCommand */
    public CreateForumGroupCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumGroupName = form.getForumGroupName();
        var forumGroup = forumControl.getForumGroupByName(forumGroupName);
        
        if(forumGroup == null) {
            var iconControl = Session.getModelController(IconControl.class);
            var iconName = form.getIconName();
            var icon = iconName == null? null: iconControl.getIconByName(iconName);
            
            if(iconName == null || icon != null) {
                if(icon != null) {
                    var iconUsageType = iconControl.getIconUsageTypeByName(IconConstants.IconUsageType_FORUM_GROUP);
                    var iconUsage = iconControl.getIconUsage(iconUsageType, icon);
                    
                    if(iconUsage == null) {
                        addExecutionError(ExecutionErrors.UnknownIconUsage.name());
                    }
                }
                
                if(!hasExecutionErrors()) {
                    var partyPK = getPartyPK();
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    var description = form.getDescription();
                    
                    forumGroup = forumControl.createForumGroup(forumGroupName, icon, sortOrder, partyPK);
                    
                    if(description != null) {
                        forumControl.createForumGroupDescription(forumGroup, getPreferredLanguage(), description, partyPK);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownIconName.name(), iconName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateForumGroupName.name(), forumGroupName);
        }
        
        return null;
    }
    
}
