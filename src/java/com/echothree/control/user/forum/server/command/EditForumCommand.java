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

import com.echothree.control.user.forum.common.edit.ForumEdit;
import com.echothree.control.user.forum.common.edit.ForumEditFactory;
import com.echothree.control.user.forum.common.form.EditForumForm;
import com.echothree.control.user.forum.common.result.EditForumResult;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.control.user.forum.common.spec.ForumSpec;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditForumCommand
        extends BaseAbstractEditCommand<ForumSpec, ForumEdit, EditForumResult, Forum, Forum> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IconName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ForumThreadSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ForumMessageSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditForumCommand */
    public EditForumCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditForumResult getResult() {
        return ForumResultFactory.getEditForumResult();
    }

    @Override
    public ForumEdit getEdit() {
        return ForumEditFactory.getForumEdit();
    }

    @Override
    public Forum getEntity(EditForumResult result) {
        var forumControl = Session.getModelController(ForumControl.class);
        Forum forum;
        var forumName = spec.getForumName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            forum = forumControl.getForumByName(forumName);
        } else { // EditMode.UPDATE
            forum = forumControl.getForumByNameForUpdate(forumName);
        }

        if(forum == null) {
            addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
        }

        return forum;
    }

    @Override
    public Forum getLockEntity(Forum forum) {
        return forum;
    }

    @Override
    public void fillInResult(EditForumResult result, Forum forum) {
        var forumControl = Session.getModelController(ForumControl.class);

        result.setForum(forumControl.getForumTransfer(getUserVisit(), forum));
    }

    @Override
    public void doLock(ForumEdit edit, Forum forum) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumDescription = forumControl.getForumDescription(forum, getPreferredLanguage());
        var forumDetail = forum.getLastDetail();

        icon = forumDetail.getIcon();
        forumMessageSequence = forumDetail.getForumMessageSequence();
        forumThreadSequence = forumDetail.getForumThreadSequence();

        edit.setForumName(forumDetail.getForumName());
        edit.setIconName(icon == null? null: icon.getLastDetail().getIconName());
        edit.setForumThreadSequenceName(forumThreadSequence == null? null: forumThreadSequence.getLastDetail().getSequenceName());
        edit.setForumMessageSequenceName(forumMessageSequence == null? null: forumMessageSequence.getLastDetail().getSequenceName());
        edit.setSortOrder(forumDetail.getSortOrder().toString());

        if(forumDescription != null) {
            edit.setDescription(forumDescription.getDescription());
        }
    }

    Icon icon = null;
    Sequence forumThreadSequence = null;
    Sequence forumMessageSequence = null;

    @Override
    public void canUpdate(Forum forum) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumName = edit.getForumName();
        var duplicateForum = forumControl.getForumByName(forumName);

        if(duplicateForum == null || forum.equals(duplicateForum)) {
            var iconControl = Session.getModelController(IconControl.class);
            var iconName = edit.getIconName();

            icon = iconName == null? null: iconControl.getIconByName(iconName);

            if(iconName == null || icon != null) {
                SequenceControl sequenceControl = null;
                var forumThreadSequenceName = edit.getForumThreadSequenceName();
                var forumMessageSequenceName = edit.getForumMessageSequenceName();

                if(forumThreadSequenceName != null || forumMessageSequenceName != null) {
                    sequenceControl = Session.getModelController(SequenceControl.class);

                    if(forumThreadSequenceName != null) {
                        var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.FORUM_THREAD.name());
                        forumThreadSequence = sequenceControl.getSequenceByName(sequenceType, forumThreadSequenceName);
                    }
                }

                if(forumThreadSequenceName == null || forumThreadSequence != null) {

                    if(forumMessageSequenceName != null) {
                        var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.FORUM_MESSAGE.name());
                        forumMessageSequence = sequenceControl.getSequenceByName(sequenceType, forumMessageSequenceName);
                    }

                    if(forumMessageSequenceName != null && forumMessageSequence == null) {
                        addExecutionError(ExecutionErrors.UnknownForumMessageSequenceName.name(), forumMessageSequenceName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownForumThreadSequenceName.name(), forumThreadSequenceName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownIconName.name(), iconName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateForumName.name(), forumName);
        }
    }

    @Override
    public void doUpdate(Forum forum) {
        var forumControl = Session.getModelController(ForumControl.class);
        var partyPK = getPartyPK();
        var forumDetailValue = forumControl.getForumDetailValueForUpdate(forum);
        var forumDescription = forumControl.getForumDescriptionForUpdate(forum, getPreferredLanguage());
        var description = edit.getDescription();

        forumDetailValue.setForumName(edit.getForumName());
        forumDetailValue.setIconPK(icon == null? null: icon.getPrimaryKey());
        forumDetailValue.setForumThreadSequencePK(forumThreadSequence == null? null: forumThreadSequence.getPrimaryKey());
        forumDetailValue.setForumMessageSequencePK(forumMessageSequence == null? null: forumMessageSequence.getPrimaryKey());
        forumDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        forumControl.updateForumFromValue(forumDetailValue, partyPK);

        if(forumDescription == null && description != null) {
            forumControl.createForumDescription(forum, getPreferredLanguage(), description, partyPK);
        } else if(forumDescription != null && description == null) {
            forumControl.deleteForumDescription(forumDescription, partyPK);
        } else if(forumDescription != null && description != null) {
            var forumDescriptionValue = forumControl.getForumDescriptionValue(forumDescription);

            forumDescriptionValue.setDescription(description);
            forumControl.updateForumDescriptionFromValue(forumDescriptionValue, partyPK);
        }
    }
    
}
