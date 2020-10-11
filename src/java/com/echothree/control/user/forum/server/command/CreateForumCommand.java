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

import com.echothree.control.user.forum.common.form.CreateForumForm;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.server.IconControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumType;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.icon.server.entity.IconUsage;
import com.echothree.model.data.icon.server.entity.IconUsageType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
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

public class CreateForumCommand
        extends BaseSimpleCommand<CreateForumForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ForumTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IconName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ForumThreadSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ForumMessageSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateForumCommand */
    public CreateForumCommand(UserVisitPK userVisitPK, CreateForumForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        String forumName = form.getForumName();
        Forum forum = forumControl.getForumByName(forumName);
        
        if(forum == null) {
            String forumTypeName = form.getForumTypeName();
            ForumType forumType = forumControl.getForumTypeByName(forumTypeName);
            
            if(forumType != null) {
                var iconControl = (IconControl)Session.getModelController(IconControl.class);
                String iconName = form.getIconName();
                Icon icon = iconName == null? null: iconControl.getIconByName(iconName);
                
                if(iconName == null || icon != null) {
                    if(icon != null) {
                        IconUsageType iconUsageType = iconControl.getIconUsageTypeByName(IconConstants.IconUsageType_FORUM);
                        IconUsage iconUsage = iconControl.getIconUsage(iconUsageType, icon);
                        
                        if(iconUsage == null) {
                            addExecutionError(ExecutionErrors.UnknownIconUsage.name());
                        }
                    }
                    
                    if(!hasExecutionErrors()) {
                        SequenceControl sequenceControl = null;
                        String forumThreadSequenceName = form.getForumThreadSequenceName();
                        Sequence forumThreadSequence = null;
                        String forumMessageSequenceName = form.getForumMessageSequenceName();
                        
                        if(forumThreadSequenceName != null || forumMessageSequenceName != null) {
                            sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
                            
                            if(forumThreadSequenceName != null) {
                                SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.FORUM_THREAD.name());
                                forumThreadSequence = sequenceControl.getSequenceByName(sequenceType, forumThreadSequenceName);
                            }
                        }
                        
                        if(forumThreadSequenceName == null || forumThreadSequence != null) {
                            Sequence forumMessageSequence = null;
                            
                            if(forumMessageSequenceName != null) {
                                SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.FORUM_MESSAGE.name());
                                forumMessageSequence = sequenceControl.getSequenceByName(sequenceType, forumMessageSequenceName);
                            }
                            
                            if(forumMessageSequenceName == null || forumMessageSequence != null) {
                                var partyPK = getPartyPK();
                                var sortOrder = Integer.valueOf(form.getSortOrder());
                                var description = form.getDescription();
                                
                                forum = forumControl.createForum(forumName, forumType, icon, forumThreadSequence, forumMessageSequence,
                                        sortOrder, partyPK);
                                
                                if(description != null) {
                                    forumControl.createForumDescription(forum, getPreferredLanguage(), description, partyPK);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownForumMessageSequenceName.name(), forumMessageSequenceName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownForumThreadSequenceName.name(), forumThreadSequenceName);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownIconName.name(), iconName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownForumTypeName.name(), forumTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateForumName.name(), forumName);
        }
        
        return null;
    }
    
}
