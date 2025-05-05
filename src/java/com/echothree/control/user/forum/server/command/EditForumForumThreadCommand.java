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
import com.echothree.control.user.forum.common.edit.ForumForumThreadEdit;
import com.echothree.control.user.forum.common.form.EditForumForumThreadForm;
import com.echothree.control.user.forum.common.result.EditForumForumThreadResult;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.control.user.forum.common.spec.ForumForumThreadSpec;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumForumThread;
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

public class EditForumForumThreadCommand
        extends BaseAbstractEditCommand<ForumForumThreadSpec, ForumForumThreadEdit, EditForumForumThreadResult, ForumForumThread, Forum> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ForumThreadName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditForumForumThreadCommand */
    public EditForumForumThreadCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditForumForumThreadResult getResult() {
        return ForumResultFactory.getEditForumForumThreadResult();
    }

    @Override
    public ForumForumThreadEdit getEdit() {
        return ForumEditFactory.getForumForumThreadEdit();
    }

    @Override
    public ForumForumThread getEntity(EditForumForumThreadResult result) {
        var forumControl = Session.getModelController(ForumControl.class);
        ForumForumThread forumForumThread = null;
        var forumName = spec.getForumName();
        var forum = forumControl.getForumByName(forumName);

        if(forum != null) {
            var forumThreadName = spec.getForumThreadName();
            var forumThread = forumControl.getForumThreadByName(forumThreadName);

            if(forumThread != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    forumForumThread = forumControl.getForumForumThread(forum, forumThread);
                } else { // EditMode.UPDATE
                    forumForumThread = forumControl.getForumForumThreadForUpdate(forum, forumThread);
                }

                if(forumForumThread == null) {
                    addExecutionError(ExecutionErrors.UnknownForumForumThread.name(), forumName, forumThreadName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownForumThreadName.name(), forumThreadName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
        }

        return forumForumThread;
    }

    @Override
    public Forum getLockEntity(ForumForumThread forumForumThread) {
        return forumForumThread.getForum();
    }

    @Override
    public void fillInResult(EditForumForumThreadResult result, ForumForumThread forumForumThread) {
        var forumControl = Session.getModelController(ForumControl.class);

        result.setForumForumThread(forumControl.getForumForumThreadTransfer(getUserVisit(), forumForumThread));
    }

    @Override
    public void doLock(ForumForumThreadEdit edit, ForumForumThread forumForumThread) {
        edit.setIsDefault(forumForumThread.getIsDefault().toString());
        edit.setSortOrder(forumForumThread.getSortOrder().toString());
    }

    @Override
    public void doUpdate(ForumForumThread forumForumThread) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumForumThreadValue = forumControl.getForumForumThreadValue(forumForumThread);

        forumForumThreadValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        forumForumThreadValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        forumControl.updateForumForumThreadFromValue(forumForumThreadValue, getPartyPK());
    }
    
}
