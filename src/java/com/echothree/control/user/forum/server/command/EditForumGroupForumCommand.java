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

import com.echothree.control.user.forum.common.edit.ForumEditFactory;
import com.echothree.control.user.forum.common.edit.ForumGroupForumEdit;
import com.echothree.control.user.forum.common.form.EditForumGroupForumForm;
import com.echothree.control.user.forum.common.result.EditForumGroupForumResult;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.control.user.forum.common.spec.ForumGroupForumSpec;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.model.data.forum.server.entity.ForumGroupForum;
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

public class EditForumGroupForumCommand
        extends BaseAbstractEditCommand<ForumGroupForumSpec, ForumGroupForumEdit, EditForumGroupForumResult, ForumGroupForum, ForumGroup> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditForumGroupForumCommand */
    public EditForumGroupForumCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditForumGroupForumResult getResult() {
        return ForumResultFactory.getEditForumGroupForumResult();
    }

    @Override
    public ForumGroupForumEdit getEdit() {
        return ForumEditFactory.getForumGroupForumEdit();
    }

    @Override
    public ForumGroupForum getEntity(EditForumGroupForumResult result) {
        var forumControl = Session.getModelController(ForumControl.class);
        ForumGroupForum forumGroupForum = null;
        var forumGroupName = spec.getForumGroupName();
        var forumGroup = forumControl.getForumGroupByName(forumGroupName);

        if(forumGroup != null) {
            var forumName = spec.getForumName();
            var forum = forumControl.getForumByName(forumName);

            if(forum != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    forumGroupForum = forumControl.getForumGroupForum(forumGroup, forum);
                } else { // EditMode.UPDATE
                    forumGroupForum = forumControl.getForumGroupForumForUpdate(forumGroup, forum);
                }

                if(forumGroupForum == null) {
                    addExecutionError(ExecutionErrors.UnknownForumGroupForum.name(), forumGroupName, forumName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumGroupName.name(), forumGroupName);
        }

        return forumGroupForum;
    }

    @Override
    public ForumGroup getLockEntity(ForumGroupForum forumGroupForum) {
        return forumGroupForum.getForumGroup();
    }

    @Override
    public void fillInResult(EditForumGroupForumResult result, ForumGroupForum forumGroupForum) {
        var forumControl = Session.getModelController(ForumControl.class);

        result.setForumGroupForum(forumControl.getForumGroupForumTransfer(getUserVisit(), forumGroupForum));
    }

    @Override
    public void doLock(ForumGroupForumEdit edit, ForumGroupForum forumGroupForum) {
        edit.setIsDefault(forumGroupForum.getIsDefault().toString());
        edit.setSortOrder(forumGroupForum.getSortOrder().toString());
    }

    @Override
    public void doUpdate(ForumGroupForum forumGroupForum) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumGroupForumValue = forumControl.getForumGroupForumValue(forumGroupForum);

        forumGroupForumValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        forumGroupForumValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        forumControl.updateForumGroupForumFromValue(forumGroupForumValue, getPartyPK());
    }
    
}
