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
import com.echothree.control.user.forum.common.edit.ForumMimeTypeEdit;
import com.echothree.control.user.forum.common.form.EditForumMimeTypeForm;
import com.echothree.control.user.forum.common.result.EditForumMimeTypeResult;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.control.user.forum.common.spec.ForumMimeTypeSpec;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumMimeType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditForumMimeTypeCommand
        extends BaseAbstractEditCommand<ForumMimeTypeSpec, ForumMimeTypeEdit, EditForumMimeTypeResult, ForumMimeType, Forum> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditForumMimeTypeCommand */
    public EditForumMimeTypeCommand(UserVisitPK userVisitPK, EditForumMimeTypeForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditForumMimeTypeResult getResult() {
        return ForumResultFactory.getEditForumMimeTypeResult();
    }

    @Override
    public ForumMimeTypeEdit getEdit() {
        return ForumEditFactory.getForumMimeTypeEdit();
    }

    @Override
    public ForumMimeType getEntity(EditForumMimeTypeResult result) {
        var forumControl = Session.getModelController(ForumControl.class);
        ForumMimeType forumMimeType = null;
        var forumName = spec.getForumName();
        var forum = forumControl.getForumByName(forumName);

        if(forum != null) {
            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
            var mimeTypeName = spec.getMimeTypeName();
            var mimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);

            if(mimeType != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    forumMimeType = forumControl.getForumMimeType(forum, mimeType);
                } else { // EditMode.UPDATE
                    forumMimeType = forumControl.getForumMimeTypeForUpdate(forum, mimeType);
                }

                if(forumMimeType == null) {
                    addExecutionError(ExecutionErrors.UnknownForumMimeType.name(), forumName, mimeTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
        }

        return forumMimeType;
    }

    @Override
    public Forum getLockEntity(ForumMimeType forumMimeType) {
        return forumMimeType.getForum();
    }

    @Override
    public void fillInResult(EditForumMimeTypeResult result, ForumMimeType forumMimeType) {
        var forumControl = Session.getModelController(ForumControl.class);

        result.setForumMimeType(forumControl.getForumMimeTypeTransfer(getUserVisit(), forumMimeType));
    }

    @Override
    public void doLock(ForumMimeTypeEdit edit, ForumMimeType forumMimeType) {
        edit.setIsDefault(forumMimeType.getIsDefault().toString());
        edit.setSortOrder(forumMimeType.getSortOrder().toString());
    }

    @Override
    public void doUpdate(ForumMimeType forumMimeType) {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumMimeTypeValue = forumControl.getForumMimeTypeValue(forumMimeType);

        forumMimeTypeValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        forumMimeTypeValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        forumControl.updateForumMimeTypeFromValue(forumMimeTypeValue, getPartyPK());
    }

}
